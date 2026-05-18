package com.example.newsviewer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {

            var apiKey by remember {
                mutableStateOf("")
            }

            var newsList by remember {
                mutableStateOf<List<Article>>(emptyList())
            }

            var isLoading by remember {
                mutableStateOf(false)
            }

            fun loadNews() {

                isLoading = true

                lifecycleScope.launch(Dispatchers.IO) {

                    try {

                        val retrofit = RetrofitClient
                            .getInstance()
                            .create(ApiInterface::class.java)

                        val response = retrofit.getNews(
                            "us",
                            apiKey
                        )

                        launch(Dispatchers.Main) {

                            if (response.isSuccessful) {

                                newsList =
                                    response.body()?.articles ?: emptyList()

                            } else {

                                Toast.makeText(
                                    this@MainActivity,
                                    "Error loading news",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            isLoading = false
                        }

                    } catch (e: Exception) {

                        launch(Dispatchers.Main) {

                            Toast.makeText(
                                this@MainActivity,
                                e.message,
                                Toast.LENGTH_LONG
                            ).show()

                            isLoading = false
                        }
                    }
                }
            }

            Scaffold(

                topBar = {

                    TopAppBar(

                        title = {
                            Text("News Client")
                        }
                    )
                }

            ) { paddingValues ->

                Column(

                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .padding(16.dp)

                ) {

                    OutlinedTextField(

                        value = apiKey,

                        onValueChange = {
                            apiKey = it
                        },

                        label = {
                            Text("Enter NewsAPI Key")
                        },

                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(

                        onClick = {

                            if (apiKey.isNotEmpty()) {

                                loadNews()

                            } else {

                                Toast.makeText(
                                    this@MainActivity,
                                    "Enter API key",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },

                        modifier = Modifier.fillMaxWidth()

                    ) {

                        Text("Load News")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    if (isLoading) {

                        CircularProgressIndicator()

                    } else {

                        LazyColumn(

                            modifier = Modifier.fillMaxSize()

                        ) {

                            items(newsList) { article ->

                                NewsCard(article)
                            }
                        }
                    }
                }
            }
        }
    }
}