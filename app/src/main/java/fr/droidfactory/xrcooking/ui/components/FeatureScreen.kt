package fr.droidfactory.xrcooking.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.xr.compose.spatial.Subspace
import androidx.xr.compose.subspace.SpatialPanel
import androidx.xr.compose.subspace.layout.SubspaceModifier
import androidx.xr.compose.subspace.layout.height
import androidx.xr.compose.subspace.layout.movable
import androidx.xr.compose.subspace.layout.resizable
import androidx.xr.compose.subspace.layout.width
import fr.droidfactory.xrcooking.R

@Composable
internal fun FeatureScreen(
    isSpatialUiEnabled: Boolean,
    onRequestHomeModeClicked: () -> Unit,
    requestFullSpaceMode: () -> Unit,
    onNavigationClicked: (() -> Unit)? = null,
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
                    onNavigationBackClicked = onNavigationClicked,
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