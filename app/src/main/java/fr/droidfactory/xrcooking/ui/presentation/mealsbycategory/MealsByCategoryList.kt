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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.xr.compose.platform.LocalSession
import androidx.xr.compose.spatial.Subspace
import androidx.xr.compose.subspace.SpatialPanel
import androidx.xr.compose.subspace.layout.SubspaceModifier
import androidx.xr.compose.subspace.layout.height
import androidx.xr.compose.subspace.layout.movable
import androidx.xr.compose.subspace.layout.resizable
import androidx.xr.compose.subspace.layout.width
import fr.droidfactory.xrcooking.R
import fr.droidfactory.xrcooking.domain.models.CategoryMealDTO
import fr.droidfactory.xrcooking.domain.models.ResultState
import fr.droidfactory.xrcooking.ui.components.BackOrbiter
import fr.droidfactory.xrcooking.ui.components.ItemCard
import fr.droidfactory.xrcooking.ui.components.Loader
import fr.droidfactory.xrcooking.ui.components.TitleOrbiter

@Composable
internal fun MealsByCategoryStateful(
    categoryName: String,
    viewModel: MealsByCategoryViewModel = hiltViewModel(),
    navigateToMealDetails: (mealId: Int) -> Unit,
    onBackClicked: () -> Unit
) {
    val session = LocalSession.current
    val mealsByCategory = viewModel.meals.collectAsState()

    Subspace {
        SpatialPanel(
            modifier = SubspaceModifier.width(dimensionResource(R.dimen.spatial_panel_width)).height(dimensionResource(R.dimen.spatial_panel_height)).resizable().movable()
        ) {
            when (mealsByCategory.value) {
                ResultState.Uninitialized, ResultState.Loading -> Loader()
                is ResultState.Failure -> {}
                is ResultState.Success -> MealsByCategoryList(
                    categoryName = categoryName,
                    meals = (mealsByCategory.value as ResultState.Success).data,
                    onMealClicked = {
                        navigateToMealDetails(it)
                    }, onBackClicked = onBackClicked
                )
            }
        }
    }
}

@Composable
private fun MealsByCategoryScreen(
    isSpatialUiEnabled: Boolean,

) {

}

@Composable
private fun MealsByCategoryList(
    meals: List<CategoryMealDTO>,
    categoryName: String,
    onMealClicked: (mealId: Int) -> Unit,
    onBackClicked: () -> Unit
) {
    BackOrbiter(onBackClicked = onBackClicked)
    TitleOrbiter(title = categoryName, onRequestHomeModeClicked = {}, onNavigationBackClicked = null)

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        columns = GridCells.Adaptive(400.dp), contentPadding = PaddingValues(48.dp)
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