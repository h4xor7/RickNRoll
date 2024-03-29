package com.appweaver.ricknroll.ui.home

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.appweaver.ricknroll.R
import com.appweaver.ricknroll.model.Result
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

private const val TAG = "HomeScreen"

@Composable
fun CharacterListScreen(
    modifier: Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val characterList by remember { viewModel.characterList }
    val endReached by remember { viewModel.endReached }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }

    Scaffold(
        topBar = {
            TopAppBar()
        }

    ) {it->

        Log.d(TAG, "CharacterList: $characterList")
        LazyVerticalGrid(
            contentPadding = it,
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.padding(8.dp)
        ) {

            items(characterList.size) {

                if (it >= characterList.size - 1 && !endReached && !isLoading) {
                    viewModel.loadCharacters()
                }
                Character(item = characterList[it])
            }
        }


        Box(
            contentAlignment = Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = colorScheme.primary)
            }
            if (loadError.isNotEmpty()) {
                /* RetrySection(error = loadError) {
                     viewModel.loadPokemonPaginated()
                 }*/
            }
        }


    }




}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Character(
    modifier: Modifier = Modifier,
    item: Result,
    viewModel: HomeViewModel = hiltViewModel<HomeViewModel>()
){

    val defaultDominantColor = colorScheme.surface
    val defaultOnDominantColor = colorScheme.onSurface

    var onDominantColor by remember {
        mutableStateOf(defaultOnDominantColor)
    }
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    Box(
        contentAlignment = Center,
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(dominantColor)
            .clickable {
            }
    ) {
        Column {
            GlideImage(
                model = item.image,
                contentDescription =item.name,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .size(120.dp)
            ) { builder ->

                builder
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.ic_broken_image)
                    .placeholder(R.drawable.loading_img)
                    .transition(withCrossFade())

                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            if (resource != null) {
                                viewModel.calcDominantColor(resource) { color ->
                                    dominantColor = color
                                }
                                viewModel.calcOnDominantColor(resource) { color ->
                                    onDominantColor = color
                                }
                            }
                            return false
                        }

                    })


            }


            Text(
                text = item.name,
                color =  onDominantColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                softWrap = true,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelSmall,

                )

        }



    }


    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(modifier: Modifier = Modifier) {

    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) { Image(
                    modifier = Modifier
                        .size(64.dp)
                        .padding(8.dp),
                    painter = painterResource(R.drawable.rick_svg),

                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.displayLarge
                )
            }
        },
        modifier = modifier
    )
}


@Preview(showBackground = true)
@Composable
fun CharacterPreview() {

}
