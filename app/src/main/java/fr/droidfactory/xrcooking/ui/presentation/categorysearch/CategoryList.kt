package fr.droidfactory.xrcooking.ui.presentation.categorysearch

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import fr.droidfactory.xrcooking.R
import fr.droidfactory.xrcooking.domain.models.CategoryDTO
import fr.droidfactory.xrcooking.domain.models.ResultState
import fr.droidfactory.xrcooking.ui.components.ErrorScreen
import fr.droidfactory.xrcooking.ui.components.ItemCard
import fr.droidfactory.xrcooking.ui.components.Loader
import fr.droidfactory.xrcooking.ui.components.SimpleFeatureScreen

@Composable
internal fun CategorySearchStateful(
    viewModel: CategorySearchViewModel = hiltViewModel(),
    onNavigateToCategoryMeals: (categoryName: String) -> Unit
) {
    val context = LocalContext.current
    val categoriesState = viewModel.categories.collectAsState()

    SimpleFeatureScreen(
       title = stringResource(R.string.title_categories)
    ) { modifier ->
        when (val state = categoriesState.value) {
            ResultState.Loading, ResultState.Uninitialized -> {
                Loader(modifier = modifier)
            }
            is ResultState.Failure -> {
                ErrorScreen(
                    modifier = modifier,
                    message = state.exception.message ?: context.getString(R.string.error_unknown),
                    onRetryClicked = {
                        viewModel.loadCategories()
                    }
                )
            }

            is ResultState.Success -> {
                CategoryList(
                    modifier = modifier,
                    categories = state.data,
                    onCategoryClicked = { categoryName ->
                        onNavigateToCategoryMeals(categoryName)
                    }
                )
            }
        }
    }
}

@Composable
private fun CategoryList(
    modifier: Modifier = Modifier,
    categories: List<CategoryDTO>,
    onCategoryClicked: (categoryName: String) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize(),
        columns = GridCells.Adaptive(dimensionResource(R.dimen.grid_cell_width)), contentPadding = PaddingValues(48.dp)
    ) {
        items(items = categories, key = { item -> "key_category_search_${item.id}" }) {
            ItemCard(
                title = it.name,
                imageUrl = it.imageUrl,
                onCardClicked = {
                    onCategoryClicked(it.name)
                }
            )
        }
    }
}