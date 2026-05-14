package com.littleapp.randomimggenerating.Activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.littleapp.randomimggenerating.R
import com.littleapp.randomimggenerating.Unit.THEME
import com.littleapp.randomimggenerating.Unit.VOID
import com.littleapp.randomimggenerating.databinding.ActivityImageInfoBinding

class ImageInfoActivity : AppCompatActivity() {

    private var binding: ActivityImageInfoBinding? = null
    var context: Context = this@ImageInfoActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)
        binding = ActivityImageInfoBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)

        binding!!.toolbar.nameSpace.text = getString(R.string.image_info)
        val data = intent

        // set the data to ui
        binding!!.catName.text = data.getStringExtra("name")
        binding!!.catOrigin.text = data.getStringExtra("origin")
        binding!!.catDescription.text = data.getStringExtra("desc")
        binding!!.catTemperament.text = data.getStringExtra("temp")

        VOID.Glide(context, data.getStringExtra("imageUrl"), binding!!.catImage)

        binding!!.wikiBtn.setOnClickListener {
            val catUri = Uri.parse(data.getStringExtra("wikiUrl"))
            val browser = Intent(Intent.ACTION_VIEW, catUri)
            startActivity(browser)
        }
        binding!!.moreInfoBtn.setOnClickListener {
            val catUri = Uri.parse(data.getStringExtra("moreLink"))
            val browser = Intent(Intent.ACTION_VIEW, catUri)
            startActivity(browser)
        }
    }
}