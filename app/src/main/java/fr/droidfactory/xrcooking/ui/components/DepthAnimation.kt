package fr.droidfactory.xrcooking.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import fr.droidfactory.xrcooking.R

@Composable
internal fun depthAnimation(playAnimation: Boolean, onFinishAnimation: () -> Unit): State<Dp> {
    return animateDpAsState(
        targetValue = if (playAnimation) {
            dimensionResource(R.dimen.spatial_panel_depth_final)
        } else {
            dimensionResource(R.dimen.spatial_panel_depth_initial)
        },
        animationSpec = tween(durationMillis = 3000),
        finishedListener = {
            onFinishAnimation()
        }
    )
}