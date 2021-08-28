package com.perniciosius.todonative.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true) var id: Long = 0L,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "category") var category: Category,
    @ColumnInfo(name = "done") var done: Boolean = false
) : Parcelable
