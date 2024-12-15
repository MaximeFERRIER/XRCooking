package fr.droidfactory.xrcooking.data

import fr.droidfactory.xrcooking.domain.mappers.toDomain
import fr.droidfactory.xrcooking.domain.models.CategoryDTO
import fr.droidfactory.xrcooking.domain.models.CategoryMealDTO
import fr.droidfactory.xrcooking.domain.models.MealDetailsDTO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

internal interface MealDbRepository {
    suspend fun getMealById(mealId: Int): Result<MealDetailsDTO>
    suspend fun getCategories(): Result<List<CategoryDTO>>
    suspend fun getMealsByCategory(categoryName: String): Result<List<CategoryMealDTO>>
}

internal class MealDbRepositoryImpl @Inject constructor(
    private val dispatcherIo: CoroutineDispatcher,
    private val mealDbService: MealDbService
): MealDbRepository {
    override suspend fun getMealById(mealId: Int): Result<MealDetailsDTO> = runSafe {
        mealDbService.getMealById(mealId = mealId)
    }.fold(onSuccess = {
        Result.success(it.toDomain())
    }, onFailure = {
        Result.failure(Exception(it))
    })

    override suspend fun getCategories(): Result<List<CategoryDTO>> = runSafe {
        mealDbService.getCategories()
    }.fold(onSuccess = {
        Result.success(it.toDomain())
    }, onFailure = {
        Result.failure(Exception(it))
    })

    override suspend fun getMealsByCategory(categoryName: String): Result<List<CategoryMealDTO>> = runSafe {
        mealDbService.getMealsByCategory(categoryName = categoryName)
    }.fold(
        onSuccess = {
            Result.success(it.toDomain())
        }, onFailure = {
            Result.failure(Exception(it))
        }
    )

    private suspend fun <T> runSafe(call: suspend () -> Response<T>): Result<T> = withContext(dispatcherIo) {
        return@withContext try {
            val response = call()
            if(response.isSuccessful && response.body() != null) {
                 Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}