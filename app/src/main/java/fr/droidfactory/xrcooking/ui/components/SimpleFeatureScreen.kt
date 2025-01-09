package fr.droidfactory.xrcooking.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.xr.compose.platform.LocalSession
import androidx.xr.compose.platform.LocalSpatialCapabilities
import androidx.xr.compose.spatial.Subspace
import androidx.xr.compose.subspace.SpatialPanel
import androidx.xr.compose.subspace.layout.SubspaceModifier
import androidx.xr.compose.subspace.layout.height
import androidx.xr.compose.subspace.layout.movable
import androidx.xr.compose.subspace.layout.offset
import androidx.xr.compose.subspace.layout.resizable
import androidx.xr.compose.subspace.layout.width
import dev.chrisbanes.haze.HazeState
import fr.droidfactory.xrcooking.R

@Composable
internal fun SimpleFeatureScreen(
    title: String,
    onNavigationClicked: (() -> Unit)? = null,
    content: @Composable (Modifier) -> Unit
) {
    val session = LocalSession.current
    var doesAnimationShouldBePlayed by remember { mutableStateOf(false) }

    if (LocalSpatialCapabilities.current.isSpatialUiEnabled) {
        val hazeState = remember { HazeState() }
        var playAnimation by remember { mutableStateOf(false) }

        val depth by depthAnimation(
            playAnimation = playAnimation,
            onFinishAnimation = { doesAnimationShouldBePlayed = false }
        )

        LaunchedEffect(Unit) {
            playAnimation = doesAnimationShouldBePlayed
        }

        Subspace {
            SpatialPanel(
                modifier = SubspaceModifier.width(dimensionResource(R.dimen.spatial_panel_width))
                    .height(dimensionResource(R.dimen.spatial_panel_height))
                    .resizable()
                    .movable()
                    .offset(
                        z = if (doesAnimationShouldBePlayed) {
                            depth
                        } else {
                            dimensionResource(R.dimen.spatial_panel_depth_final)
                        }
                    ),
                name = "CategorySearchStatefulSpatialPanel"
            ) {
                TitleOrbiter(
                    title = title,
                    onNavigationBackClicked = onNavigationClicked,
                    onRequestHomeModeClicked = {
                        session?.requestHomeSpaceMode()
                    }
                )

                content(Modifier.blur(hazeState = hazeState))
            }
        }
    } else {
        Scaffold(
            topBar = {
                TitleTopAppBar(
                    title = title,
                    requestFullSpaceMode = {
                        session?.requestFullSpaceMode()
                        doesAnimationShouldBePlayed = true
                    }, onNavigationClicked = onNavigationClicked
                )
            }
        ) { paddings ->
            content(
                Modifier
                    .padding(paddings)
                    .background(color = MaterialTheme.colorScheme.background)
            )
        }
    }
}