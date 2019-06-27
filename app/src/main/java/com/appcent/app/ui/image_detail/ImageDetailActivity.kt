package com.appcent.app.ui.image_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.appcent.app.R
import com.bumptech.glide.Glide
import com.appcent.app.di.scope.ActivityScope
import kotlinx.android.synthetic.main.activity_image_detail.*

@ActivityScope
class ImageDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)
        title = intent.extras.getString("imageName")
        try {
            Glide.with(this)
                .load(intent.extras.getString("imageUrl"))
                .into(imageView)
        } catch (e: IllegalArgumentException) {
        }

    }
}
