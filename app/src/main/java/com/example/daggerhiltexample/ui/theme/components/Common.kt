package com.example.daggerhiltexample.ui.theme.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingBar(showing: Boolean, text: String, modifier: Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        if (showing) {
            CircularProgressIndicator(modifier = modifier)
        }
        Text(text, modifier = Modifier.padding(12.dp))
    }

}