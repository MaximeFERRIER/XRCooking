package fr.droidfactory.xrcooking.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.xr.compose.platform.LocalHasXrSpatialFeature
import fr.droidfactory.xrcooking.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TitleTopAppBar(
    title: String,
    requestFullSpaceMode: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge
            )
        }, actions = {
            if (LocalHasXrSpatialFeature.current) {
                IconButton(
                    onClick = requestFullSpaceMode
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_full_space_mode_switch),
                        contentDescription = null
                    )
                }
            }
        }
    )
}