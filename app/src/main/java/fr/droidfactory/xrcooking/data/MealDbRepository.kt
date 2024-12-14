package fr.droidfactory.xrcooking.data

import fr.droidfactory.xrcooking.domain.mappers.toDomain
import fr.droidfactory.xrcooking.domain.models.CategoryDTO
import fr.droidfactory.xrcooking.domain.models.CategoryMealDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.collections.orEmpty

internal interface MealDbRepository {
    suspend fun getMealById(mealId: Int)
    suspend fun getCategories(): Result<List<CategoryDTO>>
    suspend fun getMealsByCategory(categoryName: String): Result<List<CategoryMealDTO>>
}

internal class MealDbRepositoryImpl @Inject constructor(
    private val dispatcherIo: CoroutineDispatcher,
    private val mealDbService: MealDbService
): MealDbRepository {
    override suspend fun getMealById(mealId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getCategories(): Result<List<CategoryDTO>> = withContext(dispatcherIo) {
        try {
            val categoriesResponse = mealDbService.getCategories()
            if(categoriesResponse.isSuccessful) {
                return@withContext Result.success(categoriesResponse.body()?.toDomain().orEmpty())
            }

            Result.failure(Exception(categoriesResponse.errorBody()?.string()))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMealsByCategory(categoryName: String): Result<List<CategoryMealDTO>> = withContext(dispatcherIo) {
        try {
            val mealsByCategoryResponse = mealDbService.getMealsByCategory(categoryName = categoryName)

            if(mealsByCategoryResponse.isSuccessful) {
                return@withContext Result.success(mealsByCategoryResponse.body()?.toDomain().orEmpty())
            }

            Result.failure(Exception(mealsByCategoryResponse.errorBody()?.string()))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}