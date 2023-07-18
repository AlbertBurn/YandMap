package ru.netology.yandmap.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import ru.netology.yandmap.dto.Place

interface PlaceRepository {
    fun getAll(): LiveData<List<Place>>
    fun insert(place: Place)
    fun deleteById(id: Long)
}