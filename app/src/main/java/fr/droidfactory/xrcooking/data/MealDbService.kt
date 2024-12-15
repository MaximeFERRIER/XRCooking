package fr.droidfactory.xrcooking.data

import fr.droidfactory.xrcooking.data.models.MealCategoriesResponse
import fr.droidfactory.xrcooking.data.models.MealDetailsResponse
import fr.droidfactory.xrcooking.data.models.MealsByCategoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MealDbService {
    @GET("lookup.php")
    suspend fun getMealById(
        @Query("i") mealId: Int
    ): Response<MealDetailsResponse>

    @GET("categories.php")
    suspend fun getCategories(): Response<MealCategoriesResponse>

    @GET("filter.php")
    suspend fun getMealsByCategory(
        @Query("c") categoryName: String
    ): Response<MealsByCategoryResponse>
}