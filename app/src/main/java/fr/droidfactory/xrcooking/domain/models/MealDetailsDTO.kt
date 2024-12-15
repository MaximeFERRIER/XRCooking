package fr.droidfactory.xrcooking.domain.models

internal data class MealDetailsDTO(
    val name: String,
    val instructions: List<String>,
    val imageUrl: String,
    val youtubeUrl: String,
    val ingredients: List<Ingredient>
) {
    data class Ingredient(val name: String, val measure: String)
}
