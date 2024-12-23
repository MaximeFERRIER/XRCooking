@file:OptIn(ExperimentalFoundationApi::class)

package fr.droidfactory.xrcooking.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun AssociationCard(
    title: String,
    associations: List<Pair<String, String>>
) {
    LazyColumn(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        stickyHeader(
            key = "key_association_title"
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f))
                    .padding(horizontal = 16.dp),
                text = title,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Center,
                fontSize = 48.sp,
            )
        }

        items(items = associations, key = { "key_association_${it.first}"}) { association ->
            Row(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = association.first,
                    textAlign = TextAlign.Justify,
                    fontSize = 36.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = association.second,
                    textAlign = TextAlign.Justify,
                    fontSize = 36.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    lineHeight = 36.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}