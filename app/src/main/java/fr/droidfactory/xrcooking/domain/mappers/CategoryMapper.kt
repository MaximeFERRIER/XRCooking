package fr.droidfactory.xrcooking.domain.mappers

import fr.droidfactory.xrcooking.data.models.MealCategoriesResponse
import fr.droidfactory.xrcooking.domain.models.CategoryDTO

internal fun MealCategoriesResponse.toDomain(): List<CategoryDTO> {
    return this.categories.mapNotNull { category ->
        if(category.idCategory == null) return@mapNotNull null
        CategoryDTO(
            id = category.idCategory.toInt(),
            name = category.strCategory.orEmpty(),
            imageUrl = category.strCategoryThumb.orEmpty()
        )
    }
}