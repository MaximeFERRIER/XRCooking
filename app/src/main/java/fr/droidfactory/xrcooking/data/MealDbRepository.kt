package fr.droidfactory.xrcooking.data

import fr.droidfactory.xrcooking.domain.mappers.toDomain
import fr.droidfactory.xrcooking.domain.models.CategoryDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okio.IOException
import javax.inject.Inject

internal interface MealDbRepository {
    suspend fun getMealById(mealId: Int)
    suspend fun getCategories(): Result<List<CategoryDTO>>
    suspend fun getMealsByCategory(categoryId: Int)
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

    override suspend fun getMealsByCategory(categoryId: Int) {
        TODO("Not yet implemented")
    }

}