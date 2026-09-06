package com.littleapp.randomimggenerating.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.littleapp.randomimggenerating.R
import com.littleapp.randomimggenerating.databinding.ActivityMainBinding
import com.littleapp.randomimggenerating.utils.DATA
import com.littleapp.randomimggenerating.utils.THEME
import com.littleapp.randomimggenerating.utils.intent1
import com.littleapp.randomimggenerating.utils.loadImage
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.toolbar.nameSpace.text = getString(R.string.random_img_generating)
        getImage(DATA.API_RANDOM_IMAGE)

        binding.refreshBtn.setOnClickListener { getImage(DATA.API_RANDOM_IMAGE) }
    }

    fun getImage(url: String?) {
        if (url == null) return

        val queue = Volley.newRequestQueue(this)
        val arrayRequest = JsonArrayRequest(Request.Method.GET, url, null, { response ->
            try {
                val kittyData = response.getJSONObject(0)
                val catUrl = kittyData.getString(DATA.JSON_URL)

                binding.kittyImage.loadImage(catUrl)

                binding.downloadBtn.setOnClickListener {
                    val browser = Intent(Intent.ACTION_VIEW, Uri.parse(catUrl))
                    startActivity(browser)
                }

                binding.infoBtn.setOnClickListener {
                    val breedsInfo = kittyData.optJSONArray(DATA.JSON_BREEDS)
                    if (breedsInfo != null && breedsInfo.length() > 0) {
                        val breedsData = breedsInfo.getJSONObject(0)

                        val name = breedsData.optString(DATA.JSON_NAME, DATA.EMPTY)
                        val origin = breedsData.optString(DATA.JSON_ORIGIN, DATA.EMPTY)
                        val desc = breedsData.optString(DATA.JSON_DESCRIPTION, DATA.EMPTY)
                        val temp = breedsData.optString(DATA.JSON_TEMPERAMENT, DATA.EMPTY)
                        val wikiUrl = breedsData.optString(DATA.JSON_WIKIPEDIA_URL, DATA.EMPTY)
                        val moreLink = breedsData.optString(DATA.JSON_VCA_HOSPITALS_URL, DATA.EMPTY)

                        intent1(ImageInfoActivity::class.java) {
                            putExtra(DATA.KEY_NAME, name)
                            putExtra(DATA.KEY_ORIGIN, origin)
                            putExtra(DATA.KEY_DESC, desc)
                            putExtra(DATA.KEY_TEMP, temp)
                            putExtra(DATA.KEY_WIKI_URL, wikiUrl)
                            putExtra(DATA.KEY_MORE_LINK, moreLink)
                            putExtra(DATA.KEY_IMAGE_URL, catUrl)
                        }
                    } else {
                        Toast.makeText(
                            this, "No information available for this image", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, { error ->
            val message = error.message ?: getString(R.string.unknown_error)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        })
        queue.add(arrayRequest)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}