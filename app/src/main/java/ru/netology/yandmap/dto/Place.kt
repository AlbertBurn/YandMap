package ru.netology.yandmap.dto

data class Place(
    val id: Long = 0,
    val lat: Double,
    val long: Double,
    val name: String,
)