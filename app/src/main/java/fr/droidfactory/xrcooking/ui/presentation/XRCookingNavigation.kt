package fr.droidfactory.xrcooking.ui.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import fr.droidfactory.xrcooking.ui.presentation.categorysearch.CategorySearchStateful
import fr.droidfactory.xrcooking.ui.presentation.mealdetails.MealDetailsScreen
import kotlinx.serialization.Serializable

@Composable
internal fun XRCookingNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = CategorySearch) {
        composable<CategorySearch> {
            CategorySearchStateful(onNextClicked = {
                navController.navigate(MealDetails(55))
            })
        }

        composable<MealDetails> { navBackStackEntry ->
            val mealDetails = navBackStackEntry.toRoute<MealDetails>()
            MealDetailsScreen(idMeal = mealDetails.mealId) { navController.popBackStack() }
        }
    }
}

@Serializable
private object CategorySearch

@Serializable
private data class MealDetails(val mealId: Int)