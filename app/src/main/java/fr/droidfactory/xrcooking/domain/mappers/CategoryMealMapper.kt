package fr.droidfactory.xrcooking.domain.mappers

import fr.droidfactory.xrcooking.data.models.MealsByCategoryResponse
import fr.droidfactory.xrcooking.domain.models.CategoryMealDTO

internal fun MealsByCategoryResponse.toDomain(): List<CategoryMealDTO> {
    return this.meals.mapNotNull { meal ->
        if(meal.idMeal == null) return@mapNotNull null
        CategoryMealDTO(
            id = meal.idMeal.toInt(),
            name = meal.strMeal.orEmpty(),
            imageUrl = meal.strMealThumb.orEmpty()
        )
    }
}