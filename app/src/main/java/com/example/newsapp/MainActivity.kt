@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.viewmodel.NewsViewModel
import java.time.format.TextStyle

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

@Composable
fun NewsApp(newsViewModel: NewsViewModel = viewModel()) {
    val valueState = newsViewModel.value.collectAsState()

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        NewsAppTopBar()
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                value = valueState.value ?: "",
                onValueChange = { newsViewModel.value.value = it },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Done
                )
            )
        }
    }
}

@Composable
fun ArticleCard(claim: String, summary: String) {
    Card(modifier = Modifier.shadow(4.dp, RoundedCornerShape(32.dp))) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = claim,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
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
        summary = "Thousands of passengers face flight disruptions at London's Gatwick Airport due to air traffic control staff shortages, causing delays, diversions, and cancellations."
    )
}

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