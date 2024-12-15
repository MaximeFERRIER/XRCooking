package fr.droidfactory.xrcooking.ui.presentation.mealdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.xr.compose.spatial.Subspace
import androidx.xr.compose.subspace.SpatialColumn
import androidx.xr.compose.subspace.SpatialPanel
import androidx.xr.compose.subspace.SpatialRow
import androidx.xr.compose.subspace.layout.SubspaceModifier
import androidx.xr.compose.subspace.layout.fillMaxSize
import androidx.xr.compose.subspace.layout.height
import androidx.xr.compose.subspace.layout.movable
import androidx.xr.compose.subspace.layout.resizable
import androidx.xr.compose.subspace.layout.size
import androidx.xr.compose.subspace.layout.width
import fr.droidfactory.xrcooking.domain.models.MealDetailsDTO
import fr.droidfactory.xrcooking.domain.models.ResultState
import fr.droidfactory.xrcooking.ui.components.AssociationCard
import fr.droidfactory.xrcooking.ui.components.BackOrbiter
import fr.droidfactory.xrcooking.ui.components.DescriptionCard
import fr.droidfactory.xrcooking.ui.components.Loader
import fr.droidfactory.xrcooking.ui.components.TitleOrbiter

@Composable
internal fun MealDetailsStateful(
    viewModel: MealDetailsViewModel = hiltViewModel(),
    onBackClicked: () -> Unit
) {

    val mealState = viewModel.mealState.collectAsState()

    Subspace {
        when (mealState.value) {
            ResultState.Uninitialized, ResultState.Loading -> {
                SpatialPanel(
                    modifier = SubspaceModifier.width(1488.dp).height(816.dp).resizable().movable()
                ) {
                    Loader()
                }
            }

            is ResultState.Failure -> {}
            is ResultState.Success -> MealDetailsScreen(
                mealDetails = (mealState.value as ResultState.Success).data,
                onBackClicked = onBackClicked
            )
        }
    }
}

@Composable
private fun MealDetailsScreen(mealDetails: MealDetailsDTO, onBackClicked: () -> Unit) {
    SpatialRow(modifier = SubspaceModifier.fillMaxSize()) {
        SpatialColumn {
            SpatialPanel(
                modifier = SubspaceModifier.size(744.dp).resizable().movable(),
                name = "IngredientPanel"
            ) {
                BackOrbiter(onBackClicked = onBackClicked)

                DescriptionCard(
                    title = "Steps",
                    description = mealDetails.instructions
                )
            }

            SpatialPanel(
                modifier = SubspaceModifier.size(744.dp).resizable().movable(),
                name = "InstructionsPanel"
            ) {
                AssociationCard(
                    title = "Ingredients",
                    associations = mealDetails.ingredients.map { ingredient ->
                        Pair(
                            ingredient.name,
                            ingredient.measure
                        )
                    }
                )
            }
        }

        SpatialPanel(
            modifier = SubspaceModifier.width(1488.dp).height(816.dp).resizable().movable()
        ) {
            TitleOrbiter(title = mealDetails.name)

            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.primaryContainer),
                text = mealDetails.name
            )
        }
    }
}