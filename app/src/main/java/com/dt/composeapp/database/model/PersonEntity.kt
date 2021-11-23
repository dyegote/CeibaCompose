package com.dt.composeapp.database.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Person")
class PersonEntity (
    @NonNull
    @PrimaryKey
    val identification: String,
    var name: String
)