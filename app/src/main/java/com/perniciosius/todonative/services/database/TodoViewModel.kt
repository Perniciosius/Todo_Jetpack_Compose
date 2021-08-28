package com.perniciosius.todonative.services.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.perniciosius.todonative.models.Category
import com.perniciosius.todonative.models.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application): ViewModel() {
    private val todoDao: TodoDao = TodoDatabase.getInstance(application).todoDao()
    val todos: LiveData<List<Todo>> = todoDao.getAllTodos()

    fun getTodo(id: Long): Todo {
        return todoDao.getTodo(id)
    }

    fun getCount(category: Category): LiveData<Int> {
            return todoDao.getCount(category)
    }

    fun getCompletedCount(category: Category): LiveData<Int> {
        return todoDao.getCompletedCount(category)
    }

    fun insertTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.insertTodo(todo)
        }
    }

    fun updateTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.updateTodo(todo)
        }
    }

    fun deleteTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            todoDao.deleteTodo(todo)
        }
    }
}

class TodoViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if(modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            return TodoViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}
