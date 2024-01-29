package com.appweaver.ricknroll.ui.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlin.math.log


private const val TAG = "HomeScreen"

@Composable
fun CharacterListScreen(
    modifier: Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val pokemonList by remember { viewModel.characterList }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }

    Log.d(TAG, "CharacterList: $pokemonList")

}