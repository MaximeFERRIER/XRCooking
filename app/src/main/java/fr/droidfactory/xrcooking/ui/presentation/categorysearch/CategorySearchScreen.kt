@file:OptIn(ExperimentalMaterial3Api::class)

package fr.droidfactory.xrcooking.ui.presentation.categorysearch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.xr.compose.platform.LocalHasXrSpatialFeature
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

@Composable
internal fun CategorySearchStateful(
    viewModel: CategorySearchViewModel = hiltViewModel(),
    onNavigateToCategoryMeals: (categoryName: String) -> Unit
) {
    val context = LocalContext.current
    val session = LocalSession.current
    val categoriesState = viewModel.categories.collectAsState()

    if (LocalSpatialCapabilities.current.isSpatialUiEnabled) {
        Subspace {
            SpatialPanel(
                modifier = SubspaceModifier.width(dimensionResource(R.dimen.spatial_panel_width)).height(dimensionResource(R.dimen.spatial_panel_height)).resizable().movable(),
                name = "CategorySearchStatefulSpatialPanel"
            ) {
                TitleOrbiter(
                    title = stringResource(R.string.title_categories),
                    onRequestHomeModeClicked = {
                        session?.requestHomeSpaceMode()
                    }
                )
                when (val state = categoriesState.value) {
                    ResultState.Loading, ResultState.Uninitialized -> Loader()
                    is ResultState.Failure -> {
                        ErrorScreen(
                            message = state.exception.message ?: context.getString(R.string.error_unknown),
                            onRetryClicked = {
                                viewModel.loadCategories()
                            }
                        )
                    }

                    is ResultState.Success -> {
                        CategorySearchScreen(
                            categories = state.data,
                            onCategoryClicked = { categoryName ->
                                onNavigateToCategoryMeals(categoryName)
                            }
                        )
                    }
                }
            }
        }
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    title = {
                        Text(
                            text = stringResource(R.string.title_categories),
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }, actions = {
                        if (LocalHasXrSpatialFeature.current) {
                            IconButton(
                                onClick = {
                                    session?.requestFullSpaceMode()
                                }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_full_space_mode_switch),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                )
            }
        ) { paddings ->
            when (val state = categoriesState.value) {
                ResultState.Loading, ResultState.Uninitialized -> Loader()
                is ResultState.Failure -> {
                    ErrorScreen(
                        message = state.exception.message ?: context.getString(R.string.error_unknown),
                        onRetryClicked = {
                            viewModel.loadCategories()
                        }
                    )
                }

                is ResultState.Success -> {
                    CategorySearchScreen(
                        modifier = Modifier.padding(paddings),
                        categories = state.data,
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