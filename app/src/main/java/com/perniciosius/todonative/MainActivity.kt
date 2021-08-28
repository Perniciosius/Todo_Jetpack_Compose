package com.perniciosius.todonative

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.perniciosius.todonative.pages.CreateEditPage
import com.perniciosius.todonative.pages.Home
import com.perniciosius.todonative.ui.theme.ToDoNativeTheme

@ExperimentalFoundationApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun App() {
    val navController = rememberNavController()
    ToDoNativeTheme {
        NavHost(navController = navController, startDestination = AppRoutes.Home.route) {
            composable(AppRoutes.Home.route) {
                Home(navController = navController)
            }
            composable(AppRoutes.CreateTask.route) {
                CreateEditPage(navController = navController)
            }
            composable(
                route = AppRoutes.EditTask.route.plus("/{todoId}"),
                arguments = listOf(navArgument("todoId") { type = NavType.LongType })
            ) {
                val todoId = it.arguments?.getLong("todoId")
                CreateEditPage(navController = navController, todoId = todoId)
            }
        }
    }
}


sealed class AppRoutes(val route: String) {
    object Home : AppRoutes("home")
    object CreateTask : AppRoutes("create")
    object EditTask : AppRoutes("edit")
}
