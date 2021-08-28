package com.perniciosius.todonative.pages

import android.app.Application
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.perniciosius.todonative.models.Category
import com.perniciosius.todonative.models.Todo
import com.perniciosius.todonative.models.getCategoryImage
import com.perniciosius.todonative.models.getCategoryName
import com.perniciosius.todonative.services.database.TodoViewModel
import com.perniciosius.todonative.services.database.TodoViewModelFactory
import com.perniciosius.todonative.ui.theme.Grey700
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun CreateEditPage(
    navController: NavController,
    todoId: Long? = null,
    todoViewModel: TodoViewModel = viewModel(
        factory = TodoViewModelFactory(
            application = LocalContext.current.applicationContext as Application
        )
    )
) {
    var todoState by remember {
        mutableStateOf(Todo(title = "", category = Category.PERSONAL))
    }
    
    LaunchedEffect(key1 = todoId) {
        launch(Dispatchers.IO) {
            if (todoId != null) {
                todoState = todoViewModel.getTodo(todoId)
            }
        }
    }
   
    Scaffold(
        topBar = {
            TopAppBar {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Button")
                }
                Text(text = if (todoId == null) "Create task" else "Edit task")
            }
        }
    ) { innerPadding ->
        Surface(modifier = Modifier
            .padding(innerPadding)) {
            val focusRequester = remember {
                FocusRequester()
            }
            Column(modifier = Modifier
                .fillMaxHeight()
            ) {
                TextField(
                    value = todoState.title,
                    onValueChange = { todoState = todoState.copy(title = it) },
                    textStyle = TextStyle(fontSize = 30.sp),
                    maxLines = 3,
                    placeholder = {
                        Text(text = "Create new task", fontSize = 30.sp)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp)
                        .focusRequester(focusRequester),
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Grey700,
                        backgroundColor = MaterialTheme.colors.background,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    )
                )
                Text(
                    text = "CATEGORY",
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier.padding(start = 10.dp, top = 20.dp, bottom = 10.dp)
                )
                val categories = Category.values()
                LazyVerticalGrid(
                    cells = GridCells.Fixed(2),
                    contentPadding = PaddingValues(10.dp)
                ) {
                    items(categories) {
                        category ->
                        Box(modifier = Modifier.padding(10.dp)) {
                            Card(
                                elevation = 10.dp,
                                backgroundColor = if (todoState.category != category)
                                    MaterialTheme.colors.primaryVariant
                                else
                                    MaterialTheme.colors.secondary
                                        .copy(alpha = 0.3f)
                                        .compositeOver(MaterialTheme.colors.primaryVariant),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(MaterialTheme.shapes.medium)
                                    .clickable {
                                        todoState = todoState.copy(category = category)
                                    }
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.SpaceEvenly,
                                    modifier = Modifier.padding(20.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = category.getCategoryImage()),
                                        contentDescription = "Category Image",
                                        modifier = Modifier.size(60.dp)
                                    )
                                    Text(text = category.getCategoryName(), fontSize = 30.sp)
                                }
                            }
                        }
                    }
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                ) {
                    FloatingActionButton(
                        onClick = {
                            if (todoState.title.isEmpty()) {
                                focusRequester.requestFocus()
                                return@FloatingActionButton
                            }
                            if (todoId == null) {
                                todoViewModel.insertTodo(todoState)
                            } else {
                                todoViewModel.updateTodo(todoState)
                            }
                            navController.popBackStack()
                        },
                        shape = RoundedCornerShape(50.dp),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = if (todoId == null) "Save task" else "Update task"
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = if (todoId == null) "Save task" else "Update task")
                        }
                    }
                }
            }
        }
    }
}
