package com.perniciosius.todonative.widgets

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.perniciosius.todonative.models.Todo
import com.perniciosius.todonative.models.getCategoryColor
import com.perniciosius.todonative.services.database.TodoViewModel
import com.perniciosius.todonative.services.database.TodoViewModelFactory

@ExperimentalMaterialApi
@Composable
fun TodoListItem(
    todo: Todo,
    onClick: () -> Unit,
    todoViewModel: TodoViewModel = viewModel(
    factory = TodoViewModelFactory(LocalContext.current.applicationContext as Application)
    )
) {
    val dismissState = rememberDismissState(
        confirmStateChange = {
            when (it) {
                DismissValue.DismissedToStart -> {
                    todoViewModel.deleteTodo(todo)
                    true
                }
                else -> false
            }
        }
    )
    SwipeToDismiss(
        state = dismissState,
        background = {},
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
    Card(
        elevation = 10.dp,
        backgroundColor = MaterialTheme.colors.primaryVariant,
    ) {
            ListItem(
                icon = {
                    Checkbox(
                        checked = todo.done,
                        colors = CheckboxDefaults.colors(
                            checkedColor = todo.category.getCategoryColor(),
                            uncheckedColor = todo.category.getCategoryColor(),
                            checkmarkColor = Color.White
                        ),
                        onCheckedChange = { todoViewModel.updateTodo(todo.copy(done = !todo.done)) },
                    )
                },
                modifier = Modifier.clickable(onClick = onClick)
            ) {
                Text(
                    text = todo.title,
                    textDecoration = if (todo.done)
                        TextDecoration.LineThrough
                    else
                        TextDecoration.None)
            }
        }
    }
}