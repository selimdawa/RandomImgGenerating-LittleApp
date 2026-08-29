package com.littleapp.randomimggenerating.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.littleapp.randomimggenerating.R
import com.littleapp.randomimggenerating.Unit.DATA
import com.littleapp.randomimggenerating.Unit.THEME
import com.littleapp.randomimggenerating.Unit.VOID
import com.littleapp.randomimggenerating.databinding.ActivityImageInfoBinding

class ImageInfoActivity : AppCompatActivity() {

    private var _binding: ActivityImageInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(this)
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        _binding = ActivityImageInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.toolbar.nameSpace.text = getString(R.string.image_info)

        binding.catName.text = intent.getStringExtra(DATA.KEY_NAME) ?: DATA.UNKNOWN
        binding.catOrigin.text = intent.getStringExtra(DATA.KEY_ORIGIN) ?: DATA.UNKNOWN
        binding.catDescription.text = intent.getStringExtra(DATA.KEY_DESC) ?: DATA.UNKNOWN
        binding.catTemperament.text = intent.getStringExtra(DATA.KEY_TEMP) ?: DATA.UNKNOWN

        VOID.Glide(this, intent.getStringExtra(DATA.KEY_IMAGE_URL), binding.catImage)

        binding.wikiBtn.setOnClickListener {
            val wikiUrl = intent.getStringExtra(DATA.KEY_WIKI_URL)
            if (!wikiUrl.isNullOrEmpty()) {
                val browser = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl))
                startActivity(browser)
            }
        }

        binding.moreInfoBtn.setOnClickListener {
            val moreLink = intent.getStringExtra(DATA.KEY_MORE_LINK)
            if (!moreLink.isNullOrEmpty()) {
                val browser = Intent(Intent.ACTION_VIEW, Uri.parse(moreLink))
                startActivity(browser)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}