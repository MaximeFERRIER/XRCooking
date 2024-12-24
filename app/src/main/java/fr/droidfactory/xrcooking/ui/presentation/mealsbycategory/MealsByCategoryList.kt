package fr.droidfactory.xrcooking.ui.presentation.mealsbycategory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fr.droidfactory.xrcooking.R
import fr.droidfactory.xrcooking.domain.models.CategoryMealDTO
import fr.droidfactory.xrcooking.domain.models.ResultState
import fr.droidfactory.xrcooking.ui.components.ErrorScreen
import fr.droidfactory.xrcooking.ui.components.SimpleFeatureScreen
import fr.droidfactory.xrcooking.ui.components.ItemCard
import fr.droidfactory.xrcooking.ui.components.Loader

@Composable
internal fun MealsByCategoryStateful(
    categoryName: String,
    viewModel: MealsByCategoryViewModel = hiltViewModel(),
    navigateToMealDetails: (mealId: Int) -> Unit,
    onBackClicked: () -> Unit
) {
    val context = LocalContext.current
    val mealsByCategory = viewModel.meals.collectAsState()

    SimpleFeatureScreen(
        title = categoryName,
        onNavigationClicked = onBackClicked
    ) { modifier ->
        when (val state = mealsByCategory.value) {
            ResultState.Uninitialized, ResultState.Loading -> Loader(modifier = modifier)
            is ResultState.Failure -> ErrorScreen(
                modifier = modifier,
                message = state.exception.message ?: context.getString(R.string.error_unknown),
                onRetryClicked = { viewModel.getMealsByCategory() })

            is ResultState.Success -> MealsByCategoryList(
                modifier = modifier,
                meals = state.data,
                onMealClicked = {
                    navigateToMealDetails(it)
                }
            )
        }
    }
}

@Composable
private fun MealsByCategoryList(
    modifier: Modifier = Modifier,
    meals: List<CategoryMealDTO>,
    onMealClicked: (mealId: Int) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer),
        columns = GridCells.Adaptive(dimensionResource(R.dimen.grid_cell_width)), contentPadding = PaddingValues(48.dp)
    ) {
        items(items = meals, key = { item -> "key_meal_by_category_${item.id}" }) {
            ItemCard(
                title = it.name,
                imageUrl = it.imageUrl,
                onCardClicked = {
                    onMealClicked(it.id)
                }
            )
        }
    }
}