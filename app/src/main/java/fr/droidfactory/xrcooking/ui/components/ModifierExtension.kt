package fr.droidfactory.xrcooking.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
internal fun Modifier.blur(hazeState: HazeState) = this.hazeChild(state = hazeState, style = HazeMaterials.ultraThin(containerColor = MaterialTheme.colorScheme.background))