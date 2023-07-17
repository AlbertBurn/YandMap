package ru.netology.yandmap.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.netology.yandmap.dao.PlaceDao
import ru.netology.yandmap.dto.Place
import ru.netology.yandmap.entity.PlaceEntity

class PlaceRepositoryImpl(private val dao: PlaceDao) : PlaceRepository {
    override fun getAll(): Flow<List<Place>> =
        dao.getAll().map { list ->
            list.map { placeEntity ->
                placeEntity.toDto()
            }
        }


    override fun insert(place: Place) {
        dao.insert(PlaceEntity.fromDto(place))
    }

    override fun deleteById(id: Long) {
        dao.deleteById(id)
    }

}
