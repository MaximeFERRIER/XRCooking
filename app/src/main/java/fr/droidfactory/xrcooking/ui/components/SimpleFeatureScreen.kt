package fr.droidfactory.xrcooking.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
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

@Composable
internal fun SimpleFeatureScreen(
    title: String,
    onNavigationClicked: (() -> Unit)? = null,
    content: @Composable (Modifier) -> Unit
) {
    val session = LocalSession.current

    if (LocalSpatialCapabilities.current.isSpatialUiEnabled) {
        Subspace {
            SpatialPanel(
                modifier = SubspaceModifier.width(dimensionResource(R.dimen.spatial_panel_width))
                    .height(dimensionResource(R.dimen.spatial_panel_height)).resizable().movable(),
                name = "CategorySearchStatefulSpatialPanel"
            ) {
                TitleOrbiter(
                    title = title,
                    onNavigationBackClicked = onNavigationClicked,
                    onRequestHomeModeClicked = {
                        session?.requestHomeSpaceMode()
                    }
                )

                content(Modifier)
            }
        }
    } else {
        Scaffold(
            topBar = {
                TitleTopAppBar(
                    title = title,
                    requestFullSpaceMode = {
                        session?.requestFullSpaceMode()
                    }
                )
            }
        ) { paddings ->
            content(Modifier.padding(paddings))
        }
    }
}