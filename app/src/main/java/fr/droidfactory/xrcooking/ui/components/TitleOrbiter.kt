package fr.droidfactory.xrcooking.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.xr.compose.spatial.Orbiter
import androidx.xr.compose.spatial.OrbiterEdge
import dev.chrisbanes.haze.HazeState
import fr.droidfactory.xrcooking.R

@Composable
internal fun TitleOrbiter(
    title: String,
    onRequestHomeModeClicked: () -> Unit,
    onNavigationBackClicked: (() -> Unit)?
) {
    val hazeState = remember { HazeState() }
    Orbiter(
        position = OrbiterEdge.Top,
        offset = 96.dp,
        alignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.weight(1f)) {
                onNavigationBackClicked?.let {
                    FilledTonalIconButton(
                        onClick = it,
                        modifier = Modifier
                            .size(96.dp).clip(CircleShape).blur(hazeState),
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = Color.Transparent,
                            contentColor = MaterialTheme.colorScheme.onBackground
                        )
                    ) {
                        Icon(
                            modifier = Modifier.size(56.dp),
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(R.string.switch_to_home_space_mode)
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(100.dp)
                    .width(600.dp)
                    .clip(CircleShape)
                    .blur(hazeState),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = title,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 36.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
            }

            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                FilledTonalIconButton(
                    modifier = Modifier.size(96.dp).clip(CircleShape).blur(hazeState),
                    onClick = onRequestHomeModeClicked,
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Icon(
                        modifier = Modifier.size(56.dp),
                        painter = painterResource(id = R.drawable.ic_home_space_mode_switch),
                        contentDescription = stringResource(R.string.switch_to_home_space_mode)
                    )
                }
            }
        }
    }
}