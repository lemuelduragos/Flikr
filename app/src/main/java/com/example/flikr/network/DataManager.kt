package com.example.flikr.network

import android.content.Context
import android.net.Uri
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class DataManager(private val context: Context) {
    private val baseUrl = "https://www.flickr.com/services/rest/"
    private val key = "e1a8669377c8a09f6fcb07eeccff6866"

    interface VolleyRequestCallback {
        fun finish(result: JSONObject?)
        fun error(errorMessage: String?)
    }

    //request images from flicker api
    fun sendVolleyRequest(callback: VolleyRequestCallback): JSONObject {
        var returnResult = JSONObject()
        val builtUri = Uri.parse(baseUrl).buildUpon()

        //set up parameters for get request
        builtUri.appendQueryParameter("method", "flickr.people.getPublicPhotos")
        builtUri.appendQueryParameter("api_key", key)
        builtUri.appendQueryParameter("user_id", "65789667@N06")
        builtUri.appendQueryParameter("extras", "url_m,owner_name")
        builtUri.appendQueryParameter("format", "json")
        builtUri.appendQueryParameter("nojsoncallback", "1")
        builtUri.build()

        val request = JsonObjectRequest(Request.Method.GET, builtUri.toString(), null,
            { result: JSONObject ->
                callback.finish(result)
            }) { error: VolleyError ->
            callback.error(error.message.toString())

        }


        VolleyHelper.getInstance(context)?.addToRequestQueue(request)

        return returnResult
    }
}