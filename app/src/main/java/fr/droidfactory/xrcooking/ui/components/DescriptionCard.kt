@file:OptIn(ExperimentalFoundationApi::class)

package fr.droidfactory.xrcooking.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun DescriptionCard(
    title: String,
    description : List<String>
) {
    LazyColumn(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        stickyHeader(
            key = "key_description_title"
        ) {
            Text(
                modifier = Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f)).padding(horizontal = 16.dp),
                text = title,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Center,
                fontSize = 48.sp
            )
        }

        items(items = description, key = { "key_description_$it" }) { instruction ->
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "- $instruction",
                textAlign = TextAlign.Justify,
                fontSize = 36.sp,
                color = MaterialTheme.colorScheme.onBackground,
                lineHeight = 36.sp
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}