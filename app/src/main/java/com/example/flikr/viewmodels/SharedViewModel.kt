package com.example.flikr.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flikr.models.Photos
import com.example.flikr.repositories.ImageRepository

class SharedViewModel(application: Application): AndroidViewModel(application) {

    private var photosLiveData: MutableLiveData<List<Photos>>?  = MutableLiveData()

    private var repo: ImageRepository? = ImageRepository.getInstance(application.applicationContext)

    init {
        repo?.getImagesData(object: ImageRepository.DataObserver{
            override fun onChange(list: List<Photos>) {
                photosLiveData?.value = list
            }
        }, application.applicationContext)
    }

    fun getImageData(): LiveData<List<Photos>> {
        return photosLiveData ?: MutableLiveData()
    }
}
