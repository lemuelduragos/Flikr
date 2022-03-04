package com.example.flikr

import android.app.Application
import android.graphics.Bitmap
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        val imagePipelineConfig = ImagePipelineConfig.newBuilder(this)
            .setDownsampleEnabled(true)
            .setResizeAndRotateEnabledForNetwork(true)
            .setBitmapsConfig(Bitmap.Config.RGB_565)
            .build()
        Fresco.initialize(this, imagePipelineConfig)
    }
}