package com.example.flikr.models

class Photos(id: String, title: String, photographer: String, imageUrl: String, isFavorite: Boolean = false) {
    var id = id
    var title = title
    var photographer = photographer
    var imageUrl = imageUrl
    var isFavorite = isFavorite
}