package com.example.flikr.network

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class VolleyHelper(context: Context) {

    private var queue: RequestQueue? = null

    init {
        queue = getRequestQueue(context)
    }

    companion object {
        private var volley: VolleyHelper? = null
        fun getInstance (context: Context): VolleyHelper? {
            if (volley == null) {
                volley = VolleyHelper(context)
            }

            return volley
        }
    }

    private fun getRequestQueue(context: Context): RequestQueue? {
        if (queue == null) {
            queue = Volley.newRequestQueue(context)
        }

        return queue
    }

    fun addToRequestQueue(request: JsonObjectRequest) {
        queue?.add(request)
    }
}