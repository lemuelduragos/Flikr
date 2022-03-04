package com.example.flikr.repositories

import android.content.Context
import android.util.Log
import com.example.flikr.models.Photos
import com.example.flikr.network.DataManager
import org.json.JSONObject


/*
*Singleton ImageRepository
*/
class ImageRepository(context: Context) {
    private var dataManager: DataManager? = null

    interface DataObserver {
        fun onChange(list: List<Photos>)
    }

    companion object {
        private var instance: ImageRepository? = null
        fun getInstance(context: Context): ImageRepository {
            if (instance == null) {
                instance = ImageRepository(context)
            }

            return instance as ImageRepository
        }
    }

    init {
        dataManager = DataManager(context)
    }

    fun getImagesData(callback: DataObserver, applicationContext: Context) {
        dataManager?.sendVolleyRequest(object : DataManager.VolleyRequestCallback {
            override fun finish(result: JSONObject?) {
                callback.onChange(setData(result, applicationContext))
            }

            override fun error(errorMessage: String?) {
                Log.d("Error", errorMessage.toString())
            }
        })
    }

    //create data List<Photos> from the data returned by api
    private fun setData(result: JSONObject?, applicationContext: Context): List<Photos> {
        val preferences =
            applicationContext.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE)
        val favorites = preferences.getStringSet("favorites", HashSet())

        var data: ArrayList<Photos> = ArrayList()
        if (result == null) return data

        val photos = result.optJSONObject("photos")
        val photoArray = photos?.optJSONArray("photo") ?: return data

        for (x in 0 until photoArray.length()) {
            val obj = photoArray.optJSONObject(x)
            val id = obj.optString("id")
            val title = obj.optString("title")
            val url = obj.optString("url_m")
            val owner = obj.optString("ownername")
            val favorite = favorites?.contains(id) == true
            data.add(Photos(id, title, owner, url, favorite))
        }

        return data
    }
}