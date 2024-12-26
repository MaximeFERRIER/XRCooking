package fr.droidfactory.xrcooking.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.xr.compose.platform.LocalSession
import androidx.xr.compose.platform.LocalSpatialCapabilities
import androidx.xr.compose.spatial.Subspace
import androidx.xr.compose.subspace.SpatialPanel
import androidx.xr.compose.subspace.SpatialRow
import androidx.xr.compose.subspace.layout.SubspaceModifier
import androidx.xr.compose.subspace.layout.fillMaxHeight
import androidx.xr.compose.subspace.layout.height
import androidx.xr.compose.subspace.layout.movable
import androidx.xr.compose.subspace.layout.padding
import androidx.xr.compose.subspace.layout.resizable
import androidx.xr.compose.subspace.layout.width
import fr.droidfactory.xrcooking.R

@Composable
internal fun MultipleRowFeature(
    title: String,
    onNavigationClicked: (() -> Unit)? = null,
    mainContent: LazyListScope.(Modifier) -> Unit,
    leftContent: (LazyListScope.(Modifier) -> Unit)? = null,
    rightContent: (LazyListScope.(Modifier) -> Unit)? = null,
) {
    val session = LocalSession.current

    if (LocalSpatialCapabilities.current.isSpatialUiEnabled) {
        val dimensions = getDimensions(
            isLeftPanelPresent = leftContent != null,
            isRightPanelPresent = rightContent != null
        )

        Subspace {
            SpatialRow(
                modifier = SubspaceModifier
                    .width(dimensionResource(R.dimen.spatial_panel_width))
                    .height(dimensionResource(R.dimen.spatial_panel_height))
            ) {
                leftContent?.let { content ->
                    SpatialPanel(
                        modifier = SubspaceModifier
                            .width(dimensions.left)
                            .fillMaxHeight()
                            .resizable()
                            .movable()
                            .padding(horizontal = 16.dp),
                        name = "MultipleRowFeature_Left_$title"
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            content(Modifier)
                        }
                    }
                }

                SpatialPanel(
                    modifier = SubspaceModifier
                        .width(dimensions.main)
                        .fillMaxHeight()
                        .resizable()
                        .movable(),
                    name = "MultipleRowFeature_Main_$title"
                ) {
                    TitleOrbiter(
                        title = title,
                        onNavigationBackClicked = onNavigationClicked,
                        onRequestHomeModeClicked = {
                            session?.requestHomeSpaceMode()
                        }
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        mainContent(Modifier)
                    }
                }

                rightContent?.let { content ->
                    SpatialPanel(
                        modifier = SubspaceModifier
                            .width(dimensions.right)
                            .fillMaxHeight()
                            .resizable()
                            .movable()
                            .padding(horizontal = 16.dp),
                        name = "MultipleRowFeature_Right_$title"
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            content(Modifier)
                        }
                    }
                }
            }
        }
    } else {
        Scaffold(
            topBar = {
                TitleTopAppBar(
                    title = title,
                    onNavigationClicked = onNavigationClicked,
                    requestFullSpaceMode = {
                        session?.requestFullSpaceMode()
                    }
                )
            }
        ) { paddings ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddings)
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
                    .fillMaxWidth()
            ) {
                leftContent?.let { content ->
                    content(Modifier.padding(paddings))
                }

                mainContent(Modifier.padding(paddings))

                rightContent?.let { content ->
                    content(Modifier.padding(paddings))
                }
            }
        }
    }
}

@Composable
private fun getDimensions(
    isLeftPanelPresent: Boolean,
    isRightPanelPresent: Boolean
): PanelDimensions {
    return when {
        isLeftPanelPresent && isRightPanelPresent -> PanelDimensions(
            left = dimensionResource(R.dimen.spatial_panel_side_column_width),
            main = dimensionResource(R.dimen.spatial_panel_main_column_width),
            right = dimensionResource(R.dimen.spatial_panel_side_column_width),
        )

        isLeftPanelPresent && !isRightPanelPresent -> PanelDimensions(
            left = dimensionResource(R.dimen.spatial_panel_side_column_width) + (dimensionResource(R.dimen.spatial_panel_side_column_width) / 2),
            main = dimensionResource(R.dimen.spatial_panel_main_column_width) + (dimensionResource(R.dimen.spatial_panel_side_column_width) / 2),
            right = 0.dp
        )

        else -> PanelDimensions(
            right = dimensionResource(R.dimen.spatial_panel_side_column_width) + (dimensionResource(
                R.dimen.spatial_panel_side_column_width
            ) / 2),
            main = dimensionResource(R.dimen.spatial_panel_main_column_width) + (dimensionResource(R.dimen.spatial_panel_side_column_width) / 2),
            left = 0.dp
        )
    }
}

private data class PanelDimensions(
    val left: Dp,
    val main: Dp,
    val right: Dp
)
