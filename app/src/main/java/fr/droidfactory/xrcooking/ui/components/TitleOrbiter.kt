package fr.droidfactory.xrcooking.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.xr.compose.spatial.Orbiter
import androidx.xr.compose.spatial.OrbiterEdge

@Composable
internal fun TitleOrbiter(title: String) {
    Orbiter(
        position = OrbiterEdge.Top,
        offset = 96.dp,
        alignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background, shape = CircleShape)
                .height(100.dp)
                .width(600.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 48.sp
            )
        }
    }
}