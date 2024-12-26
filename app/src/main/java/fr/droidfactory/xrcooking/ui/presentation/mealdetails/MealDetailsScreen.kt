package fr.droidfactory.xrcooking.ui.presentation.mealdetails

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import fr.droidfactory.xrcooking.R
import fr.droidfactory.xrcooking.domain.models.MealDetailsDTO
import fr.droidfactory.xrcooking.domain.models.ResultState
import fr.droidfactory.xrcooking.ui.components.ErrorScreen
import fr.droidfactory.xrcooking.ui.components.Loader
import fr.droidfactory.xrcooking.ui.components.MultipleRowFeature

@Composable
internal fun MealDetailsStateful(
    viewModel: MealDetailsViewModel = hiltViewModel(),
    onBackClicked: () -> Unit
) {
    val context = LocalContext.current
    val mealState = viewModel.mealState.collectAsState()

    MultipleRowFeature(
        title = (mealState.value as? ResultState.Success)?.data?.name ?: "",
        onNavigationClicked = onBackClicked,
        mainContent = {
            when (val state = mealState.value) {
                ResultState.Uninitialized, ResultState.Loading -> {
                    item(key = "key_spatial_main_content_loading") {
                        Loader(modifier = it)
                    }
                }

                is ResultState.Failure -> {
                    item(key = "key_spatial_main_content_error") {
                        ErrorScreen(
                            modifier = it,
                            message = state.exception.message
                                ?: context.getString(R.string.error_unknown),
                            onRetryClicked = {
                                viewModel.getMealDetails()
                            }
                        )
                    }
                }

                is ResultState.Success -> youtubeVideoPlayer(modifier = it, youtubeUrl = state.data.youtubeUrl)
            }
        },
        leftContent = {
            when (val state = mealState.value) {
                ResultState.Uninitialized, ResultState.Loading -> {
                    item(key = "key_home_main_content_loading") {
                        Loader(modifier = it)
                    }
                }

                is ResultState.Failure -> {
                    item(key = "key_home_main_content_error") {
                        ErrorScreen(
                            modifier = it,
                            message = state.exception.message
                                ?: context.getString(R.string.error_unknown),
                            onRetryClicked = {
                                viewModel.getMealDetails()
                            })
                    }
                }

                is ResultState.Success -> stepsScreen(
                    ingredients = state.data.ingredients,
                    steps = state.data.ingredients
                )
            }
        }
    )
}

private fun LazyListScope.stepsScreen(
    ingredients: List<MealDetailsDTO.Ingredient>,
    steps: List<MealDetailsDTO.Ingredient>
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

    items(items = ingredients, key = { item -> "key_${item.name}_${item.measure}" }) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "- ${it.name} ${it.measure}",
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
private fun LazyListScope.youtubeVideoPlayer(modifier: Modifier = Modifier, youtubeUrl: String) {
    item(key = "key_spatial_meal_details_video_$youtubeUrl") {
        BoxWithConstraints(
            modifier = modifier.fillParentMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            val (width, height) = if(this.maxWidth < this.maxHeight) Pair(this.maxHeight, this.maxWidth) else Pair(this.maxWidth, this.maxHeight)
            val videoPlayerScript = getYoutubeVideoPlayerScript(
                youtubeUrl = youtubeUrl,
                width = width,
                height = height
            )

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
    }
}