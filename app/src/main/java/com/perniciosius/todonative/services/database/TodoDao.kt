package com.perniciosius.todonative.services.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.perniciosius.todonative.models.Category
import com.perniciosius.todonative.models.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAllTodos(): LiveData<List<Todo>>

    @Query("SELECT * FROM todo WHERE id = :id")
    fun getTodo(id: Long): Todo

    @Query("SELECT COUNT(*) FROM todo WHERE category = :category")
    fun getCount(category: Category): LiveData<Int>

    @Query("SELECT COUNT(*) FROM todo WHERE category = :category AND done")
    fun getCompletedCount(category: Category): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)
}