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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
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

                //val data by mainActivityViewModel.uiState.collectAsState()
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
        val listDat: SnapshotStateList<List<Int>> = remember {
            mutableStateListOf()
        }
        val data by viewModel.uiState.collectAsState()
        //val remember by remember { mutableStateListOf(listOf()) }
        //var listOfNum by remember { mutableStateOf(data) }
        val onClick = { viewModel.incrementByOne() }
        listDat.add(data.data)

        Column {
            DataHolder(data = listDat.last(), onClick = onClick)
            HelloScreen()
        }
    }

    @Composable
    private fun DataHolder(data: List<Int>, onClick: () -> Unit) {
        //val remember: SnapshotStateList<List<Int>> = remember { mutableStateListOf(data) }
        Column {
            Button(onClick = onClick) { Text(text = "Add") }
            Greeting(data)//remember.toList().last())
        }
    }
}

@Composable
fun Greeting(name: List<Int>, listState: LazyListState = rememberLazyListState()) {
    // val listState: LazyListState = rememberLazyListState()
    // val remember by remember { mutableStateOf(name) }
    if (name.isEmpty()) {
        Text(text = "You not have chats")
    } else {
        LazyColumn(state = listState) {
            items(name) {
                Text(
                    text = it.toString(),
                    modifier = Modifier.fillMaxWidth(),
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
        Greeting(listOf(1, 2, 3, 4))
    }
}

@Composable
fun HelloScreen() {
    var name by rememberSaveable { mutableStateOf("") }

    HelloContent(name = name, onNameChange = { name = it })
}

@Composable
fun HelloContent(name: String, onNameChange: (String) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Hello, $name",
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.h5
        )
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Name") }
        )
    }
}

