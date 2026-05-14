package com.littleapp.randomimggenerating.Unit

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import com.littleapp.randomimggenerating.R
import com.bumptech.glide.Glide

object VOID {
    fun Intent1(context: Context, c: Class<*>?) {
        val intent = Intent(context, c)
        context.startActivity(intent)
    }

    fun Glide(context: Context?, Url: String?, Image: ImageView) {
        try {
            Glide.with(context!!).load(Url).placeholder(R.color.image_profile).into(Image)
        } catch (e: Exception) {
            Image.setImageResource(R.color.image_profile)
        }
    }
}