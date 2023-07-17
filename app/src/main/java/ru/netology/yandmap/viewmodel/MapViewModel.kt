package ru.netology.yandmap.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.netology.yandmap.db.PlaceDatabase
import ru.netology.yandmap.dto.Place
import ru.netology.yandmap.entity.PlaceEntity
import ru.netology.yandmap.repository.PlaceRepository
import ru.netology.yandmap.repository.PlaceRepositoryImpl

class MapViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PlaceRepository = PlaceRepositoryImpl(
        PlaceDatabase.getInstance(application).placeDao
    )
    val data: Flow<List<Place>> = repository.getAll()
    fun insert(mapPoint: Place) = repository.insert(mapPoint)
    fun deleteById(id: Long) = repository.deleteById(id)

    var selectedPoint: Place? = null
    var currentPosition: Point = Point(45.043395, 41.969185)

}