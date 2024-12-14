package fr.droidfactory.xrcooking.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import fr.droidfactory.xrcooking.domain.models.CategoryDTO
import fr.droidfactory.xrcooking.ui.theme.XRCookingTheme

@Composable
internal fun CategoryCard(categoryDTO: CategoryDTO) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .padding(16.dp)
            .height(250.dp)
            .width(400.dp)
            .background(
                color = MaterialTheme.colorScheme.onBackground,
                shape = MaterialTheme.shapes.small
            )
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            model = ImageRequest.Builder(context).data(categoryDTO.imageUrl).crossfade(true)
                .build(),
            contentDescription = categoryDTO.name,
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                )
            },
            contentScale = ContentScale.FillWidth
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f))
                .padding(16.dp),
            text = categoryDTO.name,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Preview
@Composable
private fun CategoryCardPreview() {
    XRCookingTheme {
        CategoryCard(
            CategoryDTO(
                name = "Dessert",
                id = 3,
                imageUrl = "https://www.themealdb.com/images/category/dessert.png"
            )
        )
    }
}