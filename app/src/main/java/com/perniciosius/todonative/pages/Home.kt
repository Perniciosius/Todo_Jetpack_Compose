package com.perniciosius.todonative.pages

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.perniciosius.todonative.models.Category
import com.perniciosius.todonative.services.database.TodoViewModel
import com.perniciosius.todonative.services.database.TodoViewModelFactory
import com.perniciosius.todonative.widgets.CategoryCard
import com.perniciosius.todonative.widgets.TodoListItem

@ExperimentalMaterialApi
@Composable
fun Home(navController: NavController) {
    Scaffold(
        floatingActionButton = { FloatingActionButton(
            onClick = { navController.navigate("create") },
        ) {
            Row(
                modifier = Modifier.padding(18.dp),
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Create todo")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "New")
            }
        } }
    ) { innerPadding ->
        Surface(modifier = Modifier
            .padding(innerPadding)) {
            TodoPage(navController)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun TodoPage(navController: NavController) {
    val todoViewModel: TodoViewModel = viewModel(
        factory = TodoViewModelFactory(LocalContext.current.applicationContext as Application)
    )
    val todos by todoViewModel.todos.observeAsState(listOf())
    var selectedCategory: Category? by remember {
        mutableStateOf(null)
    }

    Column {
        TopAppBar {
            Text(text = "What's Up")
        }
        LazyColumn(
            contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item { Text(
                text = "CATEGORIES",
                style = MaterialTheme.typography.subtitle2,
                modifier = Modifier.padding(vertical = 10.dp)
                    .padding(horizontal = 10.dp),
            ) }
            item {
                CategoryCard(selectedCategory) {
                    selectedCategory = if (selectedCategory != it) it else null
                }
            }
            item {
                Text(
                    text = "TODAY'S TASKS",
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
                        .padding(horizontal = 10.dp)
                )
            }
            if (todos.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Tasks finished", modifier = Modifier.align(Alignment.Center))
                    }
                }
            } else {
                items(
                    items = if (selectedCategory != null) todos.filter { it.category == selectedCategory } else todos,
                    key = { it.id }
                ) { todo ->
                    TodoListItem(
                        todo = todo,
                        onClick = {
                            navController.navigate("edit/${todo.id}")
                        })
                }
            }
        }
    }
}
