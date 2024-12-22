package fr.droidfactory.xrcooking.ui.presentation.categorysearch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.xr.compose.platform.LocalSession
import androidx.xr.compose.platform.LocalSpatialCapabilities
import androidx.xr.compose.spatial.Subspace
import androidx.xr.compose.subspace.SpatialPanel
import androidx.xr.compose.subspace.layout.SubspaceModifier
import androidx.xr.compose.subspace.layout.height
import androidx.xr.compose.subspace.layout.movable
import androidx.xr.compose.subspace.layout.resizable
import androidx.xr.compose.subspace.layout.width
import fr.droidfactory.xrcooking.R
import fr.droidfactory.xrcooking.domain.models.CategoryDTO
import fr.droidfactory.xrcooking.domain.models.ResultState
import fr.droidfactory.xrcooking.ui.components.ErrorScreen
import fr.droidfactory.xrcooking.ui.components.ItemCard
import fr.droidfactory.xrcooking.ui.components.Loader
import fr.droidfactory.xrcooking.ui.components.TitleOrbiter
import fr.droidfactory.xrcooking.ui.components.TitleTopAppBar

@Composable
internal fun CategorySearchStateful(
    viewModel: CategorySearchViewModel = hiltViewModel(),
    onNavigateToCategoryMeals: (categoryName: String) -> Unit
) {
    val context = LocalContext.current
    val session = LocalSession.current
    val categoriesState = viewModel.categories.collectAsState()

    CategorySearchScreen(
        isSpatialUiEnabled = LocalSpatialCapabilities.current.isSpatialUiEnabled,
        onRequestHomeModeClicked = {
            session?.requestHomeSpaceMode()
        }, requestFullSpaceMode = {
            session?.requestFullSpaceMode()
        }
    ) { modifier ->
        when (val state = categoriesState.value) {
            ResultState.Loading, ResultState.Uninitialized -> Loader(modifier = modifier)
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
private fun CategorySearchScreen(
    isSpatialUiEnabled: Boolean,
    onRequestHomeModeClicked: () -> Unit,
    requestFullSpaceMode: () -> Unit,
    content: @Composable (Modifier) -> Unit
) {
    if (isSpatialUiEnabled) {
        Subspace {
            SpatialPanel(
                modifier = SubspaceModifier.width(dimensionResource(R.dimen.spatial_panel_width))
                    .height(dimensionResource(R.dimen.spatial_panel_height)).resizable().movable(),
                name = "CategorySearchStatefulSpatialPanel"
            ) {
                TitleOrbiter(
                    title = stringResource(R.string.title_categories),
                    onRequestHomeModeClicked = onRequestHomeModeClicked
                )

                content(Modifier)
            }
        }
    } else {
        Scaffold(
            topBar = {
                TitleTopAppBar(
                    title = stringResource(R.string.title_categories),
                    requestFullSpaceMode = requestFullSpaceMode
                )
            }
        ) { paddings ->
            content(Modifier.padding(paddings))
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
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primaryContainer),
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