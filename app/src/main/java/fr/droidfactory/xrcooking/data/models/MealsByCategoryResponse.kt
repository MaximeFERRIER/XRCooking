package fr.droidfactory.xrcooking.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MealsByCategoryResponse(
    @SerialName("meals")
    val meals: List<Meal> = emptyList()
) {
    @Serializable
    data class Meal(
        @SerialName("idMeal")
        val idMeal: String?,
        @SerialName("strMeal")
        val strMeal: String?,
        @SerialName("strMealThumb")
        val strMealThumb: String?
    )
}