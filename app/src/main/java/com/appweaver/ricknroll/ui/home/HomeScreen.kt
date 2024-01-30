package com.appweaver.ricknroll.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appweaver.ricknroll.model.Result


private const val TAG = "HomeScreen"

@Composable
fun CharacterListScreen(
    modifier: Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val characterList by remember { viewModel.characterList }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }

    Log.d(TAG, "CharacterList: $characterList")
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.padding(8.dp)
    ) {
        items(characterList.size) {
            Character(item = characterList[0].results[it])
        }
    }


}

@Composable
fun Character(
    modifier: Modifier = Modifier,
    item: Result
){

    Row(modifier = modifier) {

    }
    Log.d(TAG, "Character: ${item.name} ")
}

