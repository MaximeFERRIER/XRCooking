package fr.droidfactory.xrcooking.ui.presentation.categorysearch

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.xr.compose.spatial.Subspace
import androidx.xr.compose.subspace.SpatialPanel
import androidx.xr.compose.subspace.layout.SubspaceModifier
import androidx.xr.compose.subspace.layout.height
import androidx.xr.compose.subspace.layout.movable
import androidx.xr.compose.subspace.layout.resizable
import androidx.xr.compose.subspace.layout.width
import fr.droidfactory.xrcooking.domain.models.CategoryDTO
import fr.droidfactory.xrcooking.domain.models.ResultState
import fr.droidfactory.xrcooking.ui.components.ItemCard
import fr.droidfactory.xrcooking.ui.components.Loader

@Composable
internal fun CategorySearchStateful(
    viewModel: CategorySearchViewModel = hiltViewModel(),
    onNavigateToCategoryMeals: (categoryName: String) -> Unit
) {
    val categoriesState = viewModel.categories.collectAsState()

    Subspace {
        SpatialPanel(
            modifier = SubspaceModifier.width(1488.dp).height(816.dp).resizable().movable()
        ) {
            when (categoriesState.value) {
                ResultState.Loading, ResultState.Uninitialized -> Loader()
                is ResultState.Failure -> {}
                is ResultState.Success -> {
                    CategorySearchScreen(
                        categories = (categoriesState.value as ResultState.Success).data,
                        onCategoryClicked = { categoryName ->
                            onNavigateToCategoryMeals(categoryName)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CategorySearchScreen(
    categories: List<CategoryDTO>,
    onCategoryClicked: (categoryName: String) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        columns = GridCells.Adaptive(400.dp), contentPadding = PaddingValues(48.dp)
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