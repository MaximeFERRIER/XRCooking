package fr.droidfactory.xrcooking.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.xr.compose.platform.LocalSession
import androidx.xr.compose.platform.LocalSpatialCapabilities
import androidx.xr.compose.spatial.Subspace
import androidx.xr.compose.subspace.SpatialPanel
import androidx.xr.compose.subspace.SpatialRow
import androidx.xr.compose.subspace.layout.SubspaceModifier
import androidx.xr.compose.subspace.layout.depth
import androidx.xr.compose.subspace.layout.fillMaxSize
import androidx.xr.compose.subspace.layout.height
import androidx.xr.compose.subspace.layout.movable
import androidx.xr.compose.subspace.layout.resizable
import androidx.xr.compose.subspace.layout.width
import fr.droidfactory.xrcooking.R

@Composable
internal fun MultipleRowFeature(
    title: String,
    onNavigationClicked: (() -> Unit)? = null,
    mainContent: @Composable (Modifier) -> Unit,
    leftContent: (@Composable (Modifier) -> Unit)? = null,
    rightContent: (@Composable (Modifier) -> Unit)? = null,
) {
    val session = LocalSession.current

    if (LocalSpatialCapabilities.current.isSpatialUiEnabled) {
        Subspace {
            SpatialRow(modifier = SubspaceModifier.fillMaxSize().depth(75.dp)) {
                leftContent?.let { content ->
                    SpatialPanel(
                        modifier = SubspaceModifier.width(dimensionResource(R.dimen.spatial_panel_column_width))
                            .height(dimensionResource(R.dimen.spatial_panel_height))
                            .resizable()
                            .movable()
                        ,
                        name = "MultipleRowFeature_Left_$title"
                    ) {
                        content(Modifier)
                    }
                }

                SpatialPanel(
                    modifier = SubspaceModifier.width(dimensionResource(R.dimen.spatial_panel_width))
                        .height(dimensionResource(R.dimen.spatial_panel_height)).resizable()
                        .movable(),
                    name = "CategorySearchStatefulSpatialPanel"
                ) {
                    TitleOrbiter(
                        title = title,
                        onNavigationBackClicked = onNavigationClicked,
                        onRequestHomeModeClicked = {
                            session?.requestHomeSpaceMode()
                        }
                    )

                    mainContent(Modifier)
                }

                rightContent?.let { content ->
                    SpatialPanel(
                        modifier = SubspaceModifier.width(dimensionResource(R.dimen.spatial_panel_column_width))
                            .height(dimensionResource(R.dimen.spatial_panel_height)).resizable()
                            .movable(),
                        name = "MultipleRowFeature_Right_$title"
                    ) {
                        content(Modifier)
                    }
                }
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
            leftContent?.let { content -> content(Modifier.padding(paddings)) }
            mainContent(Modifier.padding(paddings))
            rightContent?.let { content -> content(Modifier.padding(paddings)) }
        }
    }
}