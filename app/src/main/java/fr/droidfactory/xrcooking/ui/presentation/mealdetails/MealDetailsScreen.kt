package fr.droidfactory.xrcooking.ui.presentation.mealdetails

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.xr.compose.platform.LocalSession
import androidx.xr.compose.platform.LocalSpatialCapabilities
import androidx.xr.compose.spatial.Subspace
import androidx.xr.compose.subspace.SpatialPanel
import androidx.xr.compose.subspace.SpatialRow
import androidx.xr.compose.subspace.layout.SubspaceModifier
import androidx.xr.compose.subspace.layout.fillMaxHeight
import androidx.xr.compose.subspace.layout.height
import androidx.xr.compose.subspace.layout.movable
import androidx.xr.compose.subspace.layout.resizable
import androidx.xr.compose.subspace.layout.width
import fr.droidfactory.xrcooking.R
import fr.droidfactory.xrcooking.domain.models.MealDetailsDTO
import fr.droidfactory.xrcooking.domain.models.ResultState
import fr.droidfactory.xrcooking.ui.components.ErrorScreen
import fr.droidfactory.xrcooking.ui.components.Loader
import fr.droidfactory.xrcooking.ui.components.TitleOrbiter
import fr.droidfactory.xrcooking.ui.components.TitleTopAppBar

@Composable
internal fun MealDetailsStateful(
    viewModel: MealDetailsViewModel = hiltViewModel(),
    onBackClicked: () -> Unit
) {
    val session = LocalSession.current
    val mealState = viewModel.mealState.collectAsState()
    val title = (mealState.value as? ResultState.Success)?.data?.name ?: ""

    if (LocalSpatialCapabilities.current.isSpatialUiEnabled) {
        SpatialStateful(
            state = mealState.value,
            title = title,
            onNavigationBackClicked = onBackClicked,
            onRequestHomeSpaceMode = {
                session?.requestHomeSpaceMode()
            }, onRetryClicked = {
                viewModel.getMealDetails()
            }
        )
    } else {
        HomeStateful(
            state = mealState.value,
            title = title,
            onNavigationBackClicked = onBackClicked,
            onRequestFullSpaceMode = {
                session?.requestFullSpaceMode()
            }, onRetryClicked = {
                viewModel.getMealDetails()
            }
        )
    }
}

@Composable
private fun SpatialStateful(
    state: ResultState<MealDetailsDTO>,
    title: String,
    onNavigationBackClicked: () -> Unit,
    onRequestHomeSpaceMode: () -> Unit,
    onRetryClicked: () -> Unit
) {
    val context = LocalContext.current
    val localDensity = LocalDensity.current

    Subspace {
        SpatialRow(
            modifier = SubspaceModifier
                .width(dimensionResource(R.dimen.spatial_panel_width))
                .height(dimensionResource(R.dimen.spatial_panel_height))
        ) {
            SpatialPanel(
                modifier = SubspaceModifier
                    .width(dimensionResource(R.dimen.spatial_panel_side_column_width))
                    .fillMaxHeight()
                    .resizable()
                    .movable(),
                name = "MealDetailsStateful_Left"
            ) {
                when (state) {
                    ResultState.Uninitialized, ResultState.Loading -> Loader(modifier = Modifier.fillMaxSize())
                    is ResultState.Failure -> ErrorScreen(
                        modifier = Modifier.fillMaxSize(),
                        message = state.exception.message
                            ?: context.getString(R.string.error_unknown),
                        onRetryClicked = onRetryClicked
                    )

                    is ResultState.Success -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            stepsScreen(
                                ingredients = state.data.ingredients,
                                steps = state.data.instructions
                            )
                        }
                    }
                }
            }

            SpatialPanel(
                modifier = SubspaceModifier
                    .width(dimensionResource(R.dimen.spatial_panel_main_column_width))
                    .fillMaxHeight()
                    .resizable()
                    .movable(),
                name = "MealDetailsStateful_Main"
            ) {
                TitleOrbiter(
                    title = title,
                    onNavigationBackClicked = onNavigationBackClicked,
                    onRequestHomeModeClicked = onRequestHomeSpaceMode
                )

                when (state) {
                    ResultState.Uninitialized, ResultState.Loading -> Loader(modifier = Modifier.fillMaxSize())
                    is ResultState.Failure -> ErrorScreen(
                        modifier = Modifier.fillMaxSize(),
                        message = state.exception.message
                            ?: context.getString(R.string.error_unknown),
                        onRetryClicked = onRetryClicked
                    )

                    is ResultState.Success -> {
                        BoxWithConstraints(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        ) {
                            val videoPlayerScript = getYoutubeVideoPlayerScript(
                                youtubeUrl = state.data.youtubeUrl,
                                width = with(localDensity) { this@BoxWithConstraints.maxWidth.toPx() },
                                height = with(localDensity) { this@BoxWithConstraints.maxHeight.toPx() }
                            )

                            YoutubePlayer(videoPlayerScript)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeStateful(
    title: String,
    state: ResultState<MealDetailsDTO>,
    onNavigationBackClicked: () -> Unit,
    onRequestFullSpaceMode: () -> Unit,
    onRetryClicked: () -> Unit
) {
    val context = LocalContext.current
    val localDensity = LocalDensity.current

    Scaffold(
        topBar = {
            TitleTopAppBar(
                title = title,
                onNavigationClicked = onNavigationBackClicked,
                requestFullSpaceMode = onRequestFullSpaceMode
            )
        }
    ) { paddings ->
        when (state) {
            ResultState.Uninitialized, ResultState.Loading -> Loader(
                modifier = Modifier
                    .padding(paddings)
            )

            is ResultState.Failure -> ErrorScreen(
                modifier = Modifier
                    .padding(paddings),
                message = state.exception.message ?: context.getString(R.string.error_unknown),
                onRetryClicked = onRetryClicked
            )

            is ResultState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(paddings)
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    stepsScreen(
                        ingredients = state.data.ingredients,
                        steps = state.data.instructions
                    )

                    item(key = "key_spatial_meal_details_video_${state.data.youtubeUrl}") {
                        BoxWithConstraints(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .aspectRatio(1f)
                        ) {
                            val videoPlayerScript = getYoutubeVideoPlayerScript(
                                youtubeUrl = state.data.youtubeUrl,
                                width = with(localDensity) { this@BoxWithConstraints.maxWidth.toPx() },
                                height = with(localDensity) { this@BoxWithConstraints.maxWidth.toPx() }
                            )

                            YoutubePlayer(videoPlayerScript)
                        }
                    }
                }
            }
        }
    }
}

private fun LazyListScope.stepsScreen(
    ingredients: List<MealDetailsDTO.Ingredient>,
    steps: List<String>
) {
    item(key = "key_ingredients_title") {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f))
                .padding(horizontal = 16.dp),
            text = stringResource(R.string.title_ingredients),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            textAlign = TextAlign.Start,
            fontSize = 48.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
    }

    itemsIndexed(items = ingredients, key = { index, item -> "key_${index}_${item.name}_${item.measure}" }) { index, item ->
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "- ${item.name} ${item.measure}",
            fontSize = 36.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            lineHeight = 36.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

    item(key = "key_steps_title") {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.8f))
                .padding(horizontal = 16.dp),
            text = stringResource(R.string.title_steps),
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            textAlign = TextAlign.Start,
            fontSize = 48.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
    }

    items(items = steps, key = { item -> "item_$item}" }) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "- $it",
            fontSize = 36.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            lineHeight = 36.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun YoutubePlayer(videoPlayerScript: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                with(settings) {
                    javaScriptEnabled = true
                    loadsImagesAutomatically = true
                    loadWithOverviewMode = true
                    useWideViewPort = true
                }
                webViewClient = WebViewClient()
                loadData(videoPlayerScript, "*", null)
            }
        }, update = { view ->
            view.loadDataWithBaseURL(
                "https://www.youtube.com",
                videoPlayerScript,
                "text/html",
                "UTF-8",
                null
            )
        }
    )
}