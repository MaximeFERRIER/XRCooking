package fr.droidfactory.xrcooking.ui.presentation

import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import fr.droidfactory.xrcooking.ui.presentation.categorysearch.CategorySearchStateful
import fr.droidfactory.xrcooking.ui.presentation.mealdetails.MealDetailsStateful
import fr.droidfactory.xrcooking.ui.presentation.mealsbycategory.MealsByCategoryStateful
import kotlinx.serialization.Serializable

@Composable
internal fun XRCookingNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = CategorySearch,
        enterTransition = { scaleIn(animationSpec = tween(1000)) },
        popEnterTransition = { scaleIn(animationSpec = tween(1000)) },
        exitTransition = { scaleOut(animationSpec = tween(1000)) },
        popExitTransition = { scaleOut(animationSpec = tween(1000)) }
    ) {
        composable<CategorySearch> {
            CategorySearchStateful(
                onNavigateToCategoryMeals = {
                    navController.navigate(MealsByCategory(categoryName = it))
                })
        }

        composable<MealsByCategory> { navBackStackEntry ->
            val mealsByCategoryArguments = navBackStackEntry.toRoute<MealsByCategory>()

            MealsByCategoryStateful(
                categoryName = mealsByCategoryArguments.categoryName,
                navigateToMealDetails = { mealId ->
                    navController.navigate(MealDetails(mealId = mealId))
                }, onBackClicked = {
                    navController.popBackStack()
                }
            )
        }

        composable<MealDetails> { navBackStackEntry ->
            MealDetailsStateful { navController.popBackStack() }
        }
    }
}

@Serializable
private object CategorySearch

@Serializable
internal data class MealsByCategory(val categoryName: String)

@Serializable
internal data class MealDetails(val mealId: Int)