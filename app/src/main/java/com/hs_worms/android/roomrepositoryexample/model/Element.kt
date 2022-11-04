package com.hs_worms.android.roomrepositoryexample.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Element(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var number: Int = 0,
    @ColumnInfo(name = "title") var title: String = "",
    var data: Date = Date()
)
