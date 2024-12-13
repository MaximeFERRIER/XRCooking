package fr.droidfactory.xrcooking.ui.presentation.categorysearch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.xr.compose.spatial.Subspace
import androidx.xr.compose.subspace.SpatialBox
import androidx.xr.compose.subspace.SpatialPanel
import androidx.xr.compose.subspace.layout.SubspaceModifier
import androidx.xr.compose.subspace.layout.fillMaxSize
import androidx.xr.compose.subspace.layout.movable
import androidx.xr.compose.subspace.layout.resizable
import androidx.xr.compose.subspace.layout.size

@Composable
internal fun CategorySearchStateful(
    onNextClicked: () -> Unit
) {
    Subspace {
        SpatialBox(modifier = SubspaceModifier.size(816.dp).resizable().movable(), name = "CategorySearchStateful") {
            SpatialPanel(modifier = SubspaceModifier.fillMaxSize()) {
                Surface {
                    Box(Modifier.height(56.dp).width(72.dp)) {
                        Button(
                            onClick = onNextClicked
                        ) {
                            Text("Suivant")
                        }
                    }
                }
            }
        }
    }
}