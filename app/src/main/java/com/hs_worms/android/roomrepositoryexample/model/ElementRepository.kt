package com.hs_worms.android.roomrepositoryexample.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.hs_worms.android.roomrepositoryexample.db.ElementDao
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "elements-database"

class ElementRepository (private val elementDao: ElementDao) {

    val elements: LiveData<List<Element>> = elementDao.getElements()

    fun getElement(id: UUID): LiveData<Element?> = elementDao.getElement(id)

    suspend fun updateElement(element: Element) {
            elementDao.updateElement(element)
    }

    suspend fun addElement(element: Element) {
            elementDao.addElement(element)
    }

    suspend fun deleteElements() {
        elementDao.deleteAll()
    }

}
