package com.ninidze.domain.model

data class Note(
    val id: String,
    val title: String,
    val content: String,
    val lastModified: Long = System.currentTimeMillis()
)

data class CreateNote(
    val title: String,
    val content: String
)
