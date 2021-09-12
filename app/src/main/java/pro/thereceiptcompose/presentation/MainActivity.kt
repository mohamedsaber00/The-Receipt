package pro.thereceiptcompose.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import pro.thereceiptcompose.navigation.Screen
import pro.thereceiptcompose.presentation.ui.recipe.RecipeDetailScreen
import pro.thereceiptcompose.presentation.ui.recipe.RecipeDetailViewModel
import pro.thereceiptcompose.presentation.ui.recipe_list.RecipeListScreen
import pro.thereceiptcompose.presentation.ui.recipe_list.RecipeListViewModel

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Screen.RecipeList.route) {
                composable(route = Screen.RecipeList.route) { navBackStackEntry ->
                    val viewModel = hiltViewModel<RecipeListViewModel>()

                    RecipeListScreen(
                        isDarkTheme = (application as BaseApplication).isDark.value,
                        onToggleTheme = (application as BaseApplication)::toggleLightTheme,
                        onNavigateToRecipeDetailScreen = {
                            navController.navigate(it)
                        } ,
                        viewModel = viewModel
                    )


                }

                composable(route = Screen.RecipeDetails.route + "/{recipeId}",
                    arguments = listOf(navArgument("recipeId") {
                      //The type of the argument
                      type = NavType.IntType
                    })
                ) { navBackStackEntry ->
                    val viewModel = hiltViewModel<RecipeDetailViewModel>()

                    RecipeDetailScreen(
                        isDarkTheme = (application as BaseApplication).isDark.value,
                        recipeId = navBackStackEntry.arguments?.getInt("recipeId"),
                        viewModel = viewModel
                    )
                }


            }
        }
    }


}


