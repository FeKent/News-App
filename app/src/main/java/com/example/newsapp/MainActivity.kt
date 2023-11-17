@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.newsapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.networking.NewsApi
import com.example.newsapp.networking.NewsApiNewsArticleSearchResult
import com.example.newsapp.networking.NewsApiNewsArticleSearchResults
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.viewmodel.NewsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NewsApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun NewsApp(newsViewModel: NewsViewModel = viewModel()) {
    val valueState = newsViewModel.value.collectAsState()
    val key by rememberUpdatedState(newValue = valueState.value)
    val articleScope = CoroutineScope(Dispatchers.Main)
    val articlesState by newsViewModel.articles.collectAsState()

    LaunchedEffect(key) {
        articleScope.launch {
            try {
                val response: NewsApiNewsArticleSearchResults = NewsApi.service.getNewsArticles(
                    apiKey = BuildConfig.API_KEY,
                    term = valueState.value.toString()
                )
                val articles = response.results.map { it?.toNewsArticles() }
                newsViewModel.updateArticles(articles)
                Log.i("Response Raw", response.results.toString())

            } catch (e: Exception) {
                Log.i("Error:", e.toString() )
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        NewsAppTopBar()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.verticalScroll(
                rememberScrollState()
            )
        ) {
            TextField(
                value = valueState.value ?: "",
                onValueChange = { newsViewModel.value.value = it
                    Log.i("TextField Value:", it)},
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Done
                )
            )
            Spacer(modifier = Modifier.size(8.dp))
            Log.i("API CHECK", newsViewModel.articles.value.toString())
            articlesState.forEach { article ->
                Column {
                    ArticleCard(
                        claim = article?.claim.toString(),
                        summary = article?.summary.toString(),
                        source = article?.url.toString()
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                }
                
            }
        }
    }
}

@Composable
fun ArticleCard(claim: String, summary: String, source: String) {
    val uriHandler = LocalUriHandler.current

    Card(modifier = Modifier.shadow(4.dp, RoundedCornerShape(32.dp)).padding(horizontal = 8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = claim,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().clickable { uriHandler.openUri(source) }
            )
            Spacer(modifier = Modifier.size(4.dp))
            Divider()
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = summary,
                fontWeight = FontWeight.Light,
                maxLines = 3,
                overflow = TextOverflow.Clip,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleCardExample() {
    ArticleCard(
        claim = "Air Traffic Control Staff Shortages Cause Flight Disruptions at London's Gatwick Airport",
        summary = "Thousands of passengers face flight disruptions at London's Gatwick Airport due to air traffic control staff shortages, causing delays, diversions, and cancellations.",
        source = "https://www.independent.co.uk/travel/news-and-advice/gatwick-flights-diverted-staff-shortage-b2411736.html"
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsAppTopBar() {
    CenterAlignedTopAppBar(
        title = { Text(text = "News App") },
        navigationIcon = {
            Icon(
                Icons.Filled.Menu,
                null,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clickable { /*TODO*/ }
            )
        },
        modifier = Modifier
            .padding(8.dp)
            .shadow(4.dp, RoundedCornerShape(8.dp))
    )
}

fun NewsApiNewsArticleSearchResult.toNewsArticles(): NewsApiNewsArticleSearchResult {
    return NewsApiNewsArticleSearchResult(
        claim = this.claim.orEmpty(),
        summary = this.summary.orEmpty(),
        url = this.url.orEmpty()
    )
}