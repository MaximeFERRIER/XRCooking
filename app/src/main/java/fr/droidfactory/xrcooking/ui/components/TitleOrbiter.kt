package fr.droidfactory.xrcooking.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.xr.compose.spatial.Orbiter
import androidx.xr.compose.spatial.OrbiterEdge
import fr.droidfactory.xrcooking.R

@Composable
internal fun TitleOrbiter(
    title: String,
    onRequestHomeModeClicked: () -> Unit,
    onNavigationBackClicked: (() -> Unit)?
) {
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
                            .fillMaxHeight()
                            .aspectRatio(1f),
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(R.string.switch_to_home_space_mode)
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(color = MaterialTheme.colorScheme.secondaryContainer, shape = CircleShape)
                    .height(100.dp)
                    .width(600.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontSize = 48.sp
                )
            }

            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                FilledTonalIconButton(
                    modifier = Modifier.size(96.dp),
                    onClick = onRequestHomeModeClicked,
                    colors = IconButtonDefaults.filledTonalIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
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

@Composable
internal fun NavigationTitleOrbiter(
    title: String,
    onNavigationBackClicked: () -> Unit,
    onRequestHomeModeClicked: () -> Unit
) {
    Orbiter(
        position = OrbiterEdge.Top,
        offset = 96.dp,
        alignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FilledTonalIconButton(
                onClick = onNavigationBackClicked,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f),
                colors = IconButtonDefaults.filledTonalIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(R.string.switch_to_home_space_mode)
                )
            }

            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = CircleShape
                    )
                    .height(100.dp)
                    .width(600.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontSize = 48.sp
                )
            }

            FilledTonalIconButton(
                onClick = onRequestHomeModeClicked, modifier = Modifier.size(96.dp),
                colors = IconButtonDefaults.filledTonalIconButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
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