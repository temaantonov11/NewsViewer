package com.example.newsviewer

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Composable
fun NewsCard(article: Article) {

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {

                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(article.url)
                )

                context.startActivity(intent)
            },

        shape = RoundedCornerShape(12.dp)
    ) {

        Column {

            AsyncImage(
                model = article.urlToImage,
                contentDescription = null,

                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),

                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(12.dp)
            ) {

                Text(
                    text = article.title ?: "No title",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = article.description ?: "No description",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = article.publishedAt ?: "",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}