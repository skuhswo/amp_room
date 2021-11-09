package com.hs_worms.android.roomrepositoryexample.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hs_worms.android.roomrepositoryexample.db.ElementDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ElementListViewModel(application: Application) : AndroidViewModel(application) {

    private val elementRepository: ElementRepository
    val elements: LiveData<List<Element>>

    init {
        val elementDao = ElementDB.getDatabase(application, viewModelScope).elementDao()
        elementRepository = ElementRepository(elementDao)
        elements = elementRepository.elements
    }

    fun addElement(element: Element) = viewModelScope.launch(Dispatchers.IO) {
        elementRepository.addElement(element)
    }



}
