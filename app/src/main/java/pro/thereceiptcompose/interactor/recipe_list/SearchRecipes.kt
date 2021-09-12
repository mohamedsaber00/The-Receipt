package pro.thereceiptcompose.interactor.recipe_list

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pro.thereceiptcompose.cache.RecipeDao
import pro.thereceiptcompose.cache.model.RecipeEntityMapper
import pro.thereceiptcompose.domain.data.DataState
import pro.thereceiptcompose.domain.model.Recipe
import pro.thereceiptcompose.network.RecipeService
import pro.thereceiptcompose.network.model.RecipeDtoMapper
import pro.thereceiptcompose.util.RECIPE_PAGINATION_PAGE_SIZE

class SearchRecipes(
    private val recipeDao: RecipeDao,
    private val recipeService: RecipeService,
    private val entityMapper: RecipeEntityMapper,
    private val dtoMapper: RecipeDtoMapper
) {


    fun execute(
        token: String,
        page: Int,
        query: String
    ): Flow<DataState<List<Recipe>>> = flow {
        try {
            emit(DataState.loading())

            // just to show pagination, api is fast
            delay(1000)

            val recipes = getRecipesFromNetwork(token, page, query)

            recipeDao.insertRecipes(entityMapper.toEntityList(recipes))

            // query the cache
            val cacheResult = if (query.isBlank()) {
                recipeDao.getAllRecipes(
                    pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                    page = page
                )
            } else {
                recipeDao.searchRecipes(
                    query = query,
                    pageSize = RECIPE_PAGINATION_PAGE_SIZE,
                    page = page
                )
            }
            val list = entityMapper.fromEntityList(cacheResult)

            emit(DataState.success(list))

        } catch (e: Exception) {

        }
    }


    private suspend fun getRecipesFromNetwork(
        token: String,
        page: Int,
        query: String
    ): List<Recipe> {
        return dtoMapper.toDomainList(
            recipeService.search(
            token, page, query
            ).recipes
        )
    }

}