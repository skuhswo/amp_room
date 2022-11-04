package com.hs_worms.android.roomrepositoryexample.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hs_worms.android.roomrepositoryexample.model.Element
import java.util.*

@Dao
interface ElementDao {

    @Query("SELECT * FROM element")
    fun getElements(): LiveData<List<Element>>

    @Query("SELECT * FROM element WHERE id=(:id)")
    fun getElement(id: UUID): LiveData<Element?>

    @Update
    suspend fun updateElement(element: Element)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addElement(element: Element)

    @Query("DELETE FROM element")
    suspend fun deleteAll()

}