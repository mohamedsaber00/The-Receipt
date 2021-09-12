package pro.thereceiptcompose.navigation

sealed class Screen(
    val route:String,
){
    object  RecipeList : Screen("recipeList")
    object RecipeDetails: Screen("recipeDetail")
}
