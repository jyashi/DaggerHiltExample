package com.example.daggerhiltexample.ui.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.daggerhiltexample.MyViewModel
import com.example.daggerhiltexample.navigation.NavModel

private val _tag = "Main Page"

@Composable
fun MainScreen(viewModel: MyViewModel, navController: NavController) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (viewModel.isLoading.value) {
                LoadingBar(showing = true, text = "Fetching data...", modifier = Modifier)
            } else {
                LazyGridComponent(navController = navController, viewModel = viewModel)
            }

        }
    }

}

@Composable
fun LazyGridComponent(viewModel: MyViewModel, navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 120.dp), modifier = Modifier.fillMaxWidth()
    ) {
        items(viewModel.listData.size) { id ->
            ImageComponent(
                id = id, viewModel = viewModel, navController, modifier = Modifier
                    .size(100.dp)
                    .fillMaxSize()
            )
        }
    }


}

@Composable
fun ImageComponent(
    id: Int,
    viewModel: MyViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val imageUrl = viewModel.listData[id].sprites["front_default"]
    val showDialog = remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        AsyncImage(
            ImageRequest.Builder(LocalContext.current)
                .data(imageUrl).crossfade(true)
                .transformations(CircleCropTransformation()).build(),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(100.dp)
                .fillMaxSize()
                .border(
                    BorderStroke(1.dp, Color.Gray)
                )
                .clip(RectangleShape)

        )
        TextButton(modifier = Modifier
            .size(100.dp)
            .fillMaxSize(), onClick = { showDialog.value = true }) {
            Text("")

        }
        MyAlertDialog(
            showDialog = showDialog,
            navController = navController,
            imageUrl = imageUrl,
            viewModel = viewModel,
            id = id
        )

    }

}

@Composable
fun MyAlertDialog(
    showDialog: MutableState<Boolean>,
    navController: NavController,
    imageUrl: Any?,
    viewModel: MyViewModel,
    id: Int
) {
    var text by remember { mutableStateOf("") }
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Test your Pokemon knowledge")
                    val img = AsyncImage(
                        ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl).crossfade(true)
                            .transformations(CircleCropTransformation()).build(),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(100.dp)
                            .fillMaxSize()
                            .clip(RectangleShape)

                    )
                }
            },
            text = {
                Column {
                    Text("Name of Pokemon?")
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        singleLine = true,
                        label = { Text("Enter Name") }
                    )

                }
            },


            buttons = {

                TextButton(onClick = {
                    viewModel.updateId(newId = id)
                    viewModel.updateName(text)
                    showDialog.value = false
                    navController.navigate(
                        route = NavModel.DetailPage.route,

                    )

                }) {
                    Text(text = "Submit")
                }
            },
        )
    }

}

//@Composable
//fun MyTextField() {
//
//
//    OutlinedTextField(
//        value = text,
//        onValueChange = { text = it },
//        label = { Text("Enter Name") }
//    )
//}