package fr.droidfactory.xrcooking.ui.presentation.mealsbycategory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.xr.compose.spatial.Orbiter
import androidx.xr.compose.spatial.OrbiterEdge
import androidx.xr.compose.spatial.Subspace
import androidx.xr.compose.subspace.SpatialPanel
import androidx.xr.compose.subspace.layout.SubspaceModifier
import androidx.xr.compose.subspace.layout.height
import androidx.xr.compose.subspace.layout.movable
import androidx.xr.compose.subspace.layout.resizable
import androidx.xr.compose.subspace.layout.width
import fr.droidfactory.xrcooking.domain.models.CategoryMealDTO
import fr.droidfactory.xrcooking.domain.models.ResultState
import fr.droidfactory.xrcooking.ui.components.ItemCard
import fr.droidfactory.xrcooking.ui.components.Loader

@Composable
internal fun MealsByCategoryStateful(
    categoryName: String,
    viewModel: MealsByCategoryViewModel = hiltViewModel(),
    navigateToMealDetails: (mealId: Int) -> Unit,
    onBackClicked: () -> Unit
) {
    val mealsByCategory = viewModel.meals.collectAsState()

    Subspace {
        SpatialPanel(
            modifier = SubspaceModifier.width(1488.dp).height(816.dp).resizable().movable()
        ) {
            when (mealsByCategory.value) {
                ResultState.Uninitialized, ResultState.Loading -> Loader()
                is ResultState.Failure -> {}
                is ResultState.Success -> MealsByCategoryScreen(
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
    meals: List<CategoryMealDTO>,
    categoryName: String,
    onMealClicked: (mealId: Int) -> Unit,
    onBackClicked: () -> Unit
) {
    BackOrbiter(onBackClicked = onBackClicked)
    TitleOrbiter(categoryName = categoryName)

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        columns = GridCells.Adaptive(400.dp), contentPadding = PaddingValues(48.dp)
    ) {
        items(items = meals, key = { item -> "key_${item.id}" }) {
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

@Composable
private fun BackOrbiter(onBackClicked: () -> Unit) {
    Orbiter(
        position = OrbiterEdge.Start,
        offset = 96.dp,
        alignment = Alignment.CenterVertically
        ) {

        IconButton(
            modifier = Modifier.fillMaxHeight(0.75f).width(75.dp),
            colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.background, contentColor = MaterialTheme.colorScheme.onBackground),
            onClick = onBackClicked
        ) {
            Icon(
                modifier = Modifier.size(48.dp),
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Back navigation"
            )
        }
    }
}

@Composable
private fun TitleOrbiter(categoryName: String) {
    Orbiter(
        position = OrbiterEdge.Top,
        offset = 96.dp,
        alignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background, shape = CircleShape)
                .height(100.dp)
                .width(600.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = categoryName,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 50.sp
            )
        }
    }
}