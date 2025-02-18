package fr.droidfactory.xrcooking.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import fr.droidfactory.xrcooking.ui.theme.XRCookingTheme

@Composable
internal fun ItemCard(title: String, imageUrl: String, onCardClicked: () -> Unit) {
    val context = LocalContext.current
    ElevatedCard(
        modifier = Modifier
            .padding(16.dp)
            .clickable(onClick = onCardClicked)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                model = ImageRequest.Builder(context).data(imageUrl).crossfade(true)
                    .build(),
                contentDescription = title,
                loading = {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp)
                        )
                    }
                },
                contentScale = ContentScale.FillWidth
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f)),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = title,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 36.sp
            )
        }
    }
}

@Preview
@Composable
private fun CategoryCardPreview() {
    XRCookingTheme {
        ItemCard(
            title = "Dessert",
            imageUrl = "https://www.themealdb.com/images/category/dessert.png",
            onCardClicked = {}
        )
    }
}