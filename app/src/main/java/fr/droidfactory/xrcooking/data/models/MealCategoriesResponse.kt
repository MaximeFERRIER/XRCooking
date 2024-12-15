package fr.droidfactory.xrcooking.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MealCategoriesResponse(
    @SerialName("categories")
    val categories: List<Category> = emptyList()
) {
    @Serializable
    data class Category(
        @SerialName("idCategory")
        val idCategory: String?,
        @SerialName("strCategory")
        val strCategory: String?,
        @SerialName("strCategoryDescription")
        val strCategoryDescription: String?,
        @SerialName("strCategoryThumb")
        val strCategoryThumb: String?
    )
}