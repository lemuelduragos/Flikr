package com.example.flikr.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.flikr.models.Photos
import com.example.flikr.repositories.ImageRepository

class SharedViewModel(application: Application): AndroidViewModel(application) {

    private var photosLiveData: MutableLiveData<List<Photos>>?  = MutableLiveData()
    private var context: Context? = null

    private var repo: ImageRepository? = ImageRepository.getInstance(application.applicationContext)

    init {
        context = application.applicationContext
        repo?.getImagesData(object: ImageRepository.DataObserver{
            override fun onChange(list: List<Photos>) {
                photosLiveData?.value = list
            }
        }, application.applicationContext)
    }

    fun getImageData(): LiveData<List<Photos>> {
        return photosLiveData ?: MutableLiveData()
    }

    fun setFavorite(id: String, isFavorite: Boolean) {
        val preferences = context?.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)

        //Retrieve the values
        val set = preferences?.getStringSet("favorites", HashSet()) ?: HashSet()

        val newStrSet: MutableSet<String> = HashSet()
        newStrSet.addAll(set)
        if (isFavorite) {
            newStrSet.add(id)
        } else {
            if (newStrSet.contains(id)) newStrSet.remove(id)
        }

        //save ids of favorites to preferences manager
        val editor = preferences?.edit()
        editor?.putStringSet("favorites", newStrSet)
        editor?.apply()
    }
}
