package pro.thereceiptcompose.di

import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pro.thereceiptcompose.cache.RecipeDao
import pro.thereceiptcompose.cache.database.AppDatabase
import pro.thereceiptcompose.cache.model.RecipeEntityMapper
import pro.thereceiptcompose.presentation.BaseApplication
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDb(app: BaseApplication): AppDatabase {
        return Room
            .databaseBuilder(app, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideRecipeDao(db: AppDatabase): RecipeDao {
        return db.recipeDao()
    }


    @Singleton
    @Provides
    fun provideCacheRecipeMapper(): RecipeEntityMapper{
        return RecipeEntityMapper()
    }

}