package fr.droidfactory.xrcooking.ui.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.xr.compose.spatial.Orbiter
import androidx.xr.compose.spatial.OrbiterEdge

@Composable
internal fun BackOrbiter(onBackClicked: () -> Unit) {
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