package com.example.quickflowtry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quickflowtry.ui.theme.QuickFlowTryTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuickFlowTryTheme {
                val scaffoldState = rememberScaffoldState()

                Scaffold(scaffoldState = scaffoldState) {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                            .fillMaxHeight(),
                        color = MaterialTheme.colors.background
                    ) {
                        ParentLayout()
                    }
                }
            }
        }
    }

    @Composable
    private fun ParentLayout(viewModel: MainActivityViewModel = viewModel()) {
        val uiStateModel by viewModel.uiState.collectAsState()
        val onClickIncrement = { viewModel.incrementByOne() }
        val modifier = Modifier
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DataHolder(
                modifier = modifier,
                data = uiStateModel,
                onClick = onClickIncrement
            )
        }
    }

    @Composable
    private fun DataHolder(modifier: Modifier = Modifier, data: List<Int>, onClick: () -> Unit) {
        Column(modifier = modifier.fillMaxWidth()) {
            Button(
                modifier = modifier
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(10.dp),
                onClick = onClick,
            ) { Text(text = "Add One more Item") }
            Greeting(modifier = modifier, data)
        }
    }
}

@Composable
fun Greeting(
    modifier: Modifier = Modifier,
    name: List<Int>,
    listState: LazyListState = rememberLazyListState()
) {
    if (name.isEmpty()) {
        Text(
            text = "You not have numbers added",
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    } else {
        LazyColumn(state = listState) {
            items(name) {
                Text(
                    text = "you have added $it",
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QuickFlowTryTheme {
        Greeting(modifier = Modifier, listOf(1, 2, 3, 4))
    }
}