package fr.droidfactory.xrcooking.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
internal fun Modifier.blur(
    hazeState: HazeState,
    backgroundColor: Color = MaterialTheme.colorScheme.background
) = this.hazeChild(
    state = hazeState,
    style = HazeMaterials.ultraThin(containerColor = backgroundColor)
)