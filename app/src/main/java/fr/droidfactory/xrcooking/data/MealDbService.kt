package fr.droidfactory.xrcooking.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealDbService {
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