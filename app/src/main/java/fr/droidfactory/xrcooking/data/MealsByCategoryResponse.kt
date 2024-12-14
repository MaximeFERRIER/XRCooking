package fr.droidfactory.xrcooking.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealsByCategoryResponse(
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