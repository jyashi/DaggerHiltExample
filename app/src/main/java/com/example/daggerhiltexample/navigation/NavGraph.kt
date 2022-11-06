package com.example.daggerhiltexample.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.daggerhiltexample.AppNavType
import com.example.daggerhiltexample.MyViewModel
import com.example.daggerhiltexample.ui.theme.components.DetailPage
import com.example.daggerhiltexample.ui.theme.components.MainScreen
import com.example.daggerhiltexample.ui.theme.components.SearchPage

private val _tag = "NavGraph"
//enum class AppNavType {
//    HOME, SEARCH
//}
@Composable
fun NavGraph(viewModel: MyViewModel,navController: NavHostController,modifier: Modifier) {
    val appNavItemState = rememberSaveable { mutableStateOf(AppNavType.HOME) }
//    val navController = rememberNavController()
    println("log1 : $_tag $viewModel")

    NavHost(navController = navController, startDestination = NavModel.MainPage.route) {
        composable(route = NavModel.MainPage.route ) {
            MainScreen(navController = navController, viewModel = viewModel, appNavItemState = appNavItemState)
        }
        composable(route = NavModel.DetailPage.route + "/{id}/{nameAnswer}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                    defaultValue = 1
                    nullable = false
                },
                navArgument("nameAnswer") {
                    type = NavType.StringType
                    defaultValue = "No Input"
                    nullable = false
                }
            )

        ) { entry ->
            DetailPage(
                id = entry.arguments!!.getInt("id"),
                nameAnswer = entry.arguments!!.getString("nameAnswer")!!,
                viewModel = viewModel,
                navController = navController
            )

        }
        composable(route = NavModel.SearchPage.route){
            SearchPage()
        }
    }

}