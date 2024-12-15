package fr.droidfactory.xrcooking.domain.mappers

import fr.droidfactory.xrcooking.data.models.MealDetailsResponse
import fr.droidfactory.xrcooking.domain.models.MealDetailsDTO

internal fun MealDetailsResponse.toDomain(): MealDetailsDTO {
    return this.meals.first().let { meal ->
        MealDetailsDTO(
            name = meal.strMeal.orEmpty(),
            instructions = meal.strInstructions.orEmpty().replace("\r", "").replace("\n\n", "\n").split("\n"),
            imageUrl = meal.strMealThumb.orEmpty(),
            youtubeUrl = meal.strYoutube.orEmpty(),
            ingredients = buildList {
                if (meal.strIngredient1?.isNotEmpty() == true && meal.strMeasure1?.isNotEmpty() == true)
                   add(MealDetailsDTO.Ingredient(meal.strIngredient1, meal.strMeasure1))
                
                if (meal.strIngredient2?.isNotEmpty() == true && meal.strMeasure2?.isNotEmpty() == true)
                   add(MealDetailsDTO.Ingredient(meal.strIngredient2, meal.strMeasure2))
                
                if (meal.strIngredient3?.isNotEmpty() == true && meal.strMeasure3?.isNotEmpty() == true)
                   add(MealDetailsDTO.Ingredient(meal.strIngredient3, meal.strMeasure3))
                
                if (meal.strIngredient4?.isNotEmpty() == true && meal.strMeasure4?.isNotEmpty() == true)
                   add(MealDetailsDTO.Ingredient(meal.strIngredient4, meal.strMeasure4))
                
                if (meal.strIngredient5?.isNotEmpty() == true && meal.strMeasure5?.isNotEmpty() == true)
                   add(MealDetailsDTO.Ingredient(meal.strIngredient5, meal.strMeasure5))
                
                if (meal.strIngredient6?.isNotEmpty() == true && meal.strMeasure6?.isNotEmpty() == true)
                   add(MealDetailsDTO.Ingredient(meal.strIngredient6, meal.strMeasure6))
                
                if (meal.strIngredient7?.isNotEmpty() == true && meal.strMeasure7?.isNotEmpty() == true)
                   add(MealDetailsDTO.Ingredient(meal.strIngredient7, meal.strMeasure7))
                
                if (meal.strIngredient8?.isNotEmpty() == true && meal.strMeasure8?.isNotEmpty() == true)
                   add(MealDetailsDTO.Ingredient(meal.strIngredient8, meal.strMeasure8))
                
                if (meal.strIngredient9?.isNotEmpty() == true && meal.strMeasure9?.isNotEmpty() == true)
                   add(MealDetailsDTO.Ingredient(meal.strIngredient9, meal.strMeasure9))
                
                if (meal.strIngredient10?.isNotEmpty() == true && meal.strMeasure10?.isNotEmpty() == true)
                   add(MealDetailsDTO.Ingredient(meal.strIngredient10, meal.strMeasure10))
                
                if (meal.strIngredient11?.isNotEmpty() == true && meal.strMeasure11?.isNotEmpty() == true)
                   add(MealDetailsDTO.Ingredient(meal.strIngredient11, meal.strMeasure11))
                
                if (meal.strIngredient12?.isNotEmpty() == true && meal.strMeasure12?.isNotEmpty() == true)
                   add(MealDetailsDTO.Ingredient(meal.strIngredient12, meal.strMeasure12))
                
                if (meal.strIngredient13?.isNotEmpty() == true && meal.strMeasure13?.isNotEmpty() == true)
                   add(MealDetailsDTO.Ingredient(meal.strIngredient13, meal.strMeasure13))
                
                if (meal.strIngredient14?.isNotEmpty() == true && meal.strMeasure14?.isNotEmpty() == true)
                   add(MealDetailsDTO.Ingredient(meal.strIngredient14, meal.strMeasure14))
                
                if (meal.strIngredient15?.isNotEmpty() == true && meal.strMeasure15?.isNotEmpty() == true)
                   add(MealDetailsDTO.Ingredient(meal.strIngredient15, meal.strMeasure15))
                
                if (meal.strIngredient16?.isNotEmpty() == true && meal.strMeasure16?.isNotEmpty() == true)
                   add(MealDetailsDTO.Ingredient(meal.strIngredient16, meal.strMeasure16))
                
                if (meal.strIngredient17?.isNotEmpty() == true && meal.strMeasure17?.isNotEmpty() == true)
                   add(MealDetailsDTO.Ingredient(meal.strIngredient17, meal.strMeasure17))
                
                if (meal.strIngredient18?.isNotEmpty() == true && meal.strMeasure18?.isNotEmpty() == true)
                   add(MealDetailsDTO.Ingredient(meal.strIngredient18, meal.strMeasure18))
                
                if (meal.strIngredient19?.isNotEmpty() == true && meal.strMeasure19?.isNotEmpty() == true)
                   add(MealDetailsDTO.Ingredient(meal.strIngredient19, meal.strMeasure19))
                
                if (meal.strIngredient20?.isNotEmpty() == true && meal.strMeasure20?.isNotEmpty() == true)
                   add(MealDetailsDTO.Ingredient(meal.strIngredient20, meal.strMeasure20))
            }
        )
    }
}