package com.clementecastillo.people.client

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.clementecastillo.people.R
import com.clementecastillo.people.extension.toPx


object ImageLoader {

    fun load(uri: Uri, target: ImageView, width: Int = 0, height: Int = 0, rounded: Boolean = false, errorDrawable: Drawable? = null) {
        Glide.with(target.context)
            .load(uri)
            .apply(getGlideOptions(target, width, height, rounded, errorDrawable))
            .into(target)
    }

    @SuppressLint("CheckResult")
    private fun getGlideOptions(target: ImageView, width: Int = 0, height: Int = 0, rounded: Boolean = false, errorDrawable: Drawable? = null): RequestOptions {
        val options = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .downsample(DownsampleStrategy.AT_MOST)
            .priority(Priority.HIGH)

        val transformations: MutableList<BitmapTransformation> = mutableListOf()

        if (width > 0 || height > 0) {
            options.override(width, height)
            transformations.add(CenterCrop())
        }

        if (rounded) {
            transformations.add(CircleCrop())
        } else

            if (errorDrawable != null) {
                options.error(errorDrawable)
            }

        options.placeholder(CircularProgressDrawable(target.context).apply {
            strokeWidth = 5f.toPx()
            centerRadius = minOf(width, height).let {
                if (it > 0) {
                    it * 0.10f
                } else {
                    10f.toPx()
                }
            }
            setColorSchemeColors(ContextCompat.getColor(target.context, R.color.colorAccent))
            start()
        })
        if (transformations.isNotEmpty()) {
            options.transforms(*transformations.toTypedArray())
        }
        return options
    }

}