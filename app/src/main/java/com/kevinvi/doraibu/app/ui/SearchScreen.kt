package com.kevinvi.doraibu.app.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kevinvi.anime.mapper.AnimeItemMapper
import com.kevinvi.anime.ui.AnimeSearchResult
import com.kevinvi.doraibu.app.SearchViewModel
import com.kevinvi.doraibu.app.navigation.navigateToDetails
import com.kevinvi.scan.mapper.ScanItemMapper
import com.kevinvi.scan.ui.ScanSearchResult
import com.kevinvi.tome.ui.TomeSearchResult
import com.kevinvi.ui.Empty
import com.kevinvi.ui.Loader

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
	navController: NavHostController = rememberNavController(),
) {

	val viewModel: SearchViewModel = hiltViewModel()
	var text by rememberSaveable { mutableStateOf("") }
	var searchLauched by rememberSaveable { mutableStateOf(false) }
	val search by viewModel.stateData.collectAsStateWithLifecycle()
	val keyboardController = LocalSoftwareKeyboardController.current
	val focusManager = LocalFocusManager.current

	Scaffold {
		Column(
			modifier = Modifier.verticalScroll(rememberScrollState())
		) {
			SearchBar(
				modifier = Modifier
					.fillMaxWidth()
					.padding(8.dp),
				query = text,
				onQueryChange = {
					text = it
				},
				onSearch = {
					searchLauched = true
					viewModel.search(text)
					focusManager.clearFocus()
					keyboardController?.hide()
				},
				active = false,
				onActiveChange = {
				},
				placeholder = {
					Text(text = "Rechercher")
				},
				leadingIcon = {
					Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
				},
				trailingIcon = {
					Icon(
						modifier = Modifier.clickable {
							if (text.isNotEmpty()) {
								text = ""
							}
						},
						imageVector = Icons.Default.Close,
						contentDescription = "Close item"
					)
				}
			) {

			}

			if (searchLauched) {
				Text(text = "Scan ", modifier = Modifier.padding(horizontal = 20.dp))
				if (search.isScanLoading && searchLauched) {
					Loader(true)
				} else {
					Loader(false)
					if (search.list.isEmpty()) {
						Empty(true)
					} else {
						Empty(false)
						LazyRow(
							modifier = Modifier
								.fillMaxWidth(),
							contentPadding = PaddingValues(8.dp),
						) {
							items(search.list) { it ->
								// Search result
								ScanSearchResult(
									it,
									onItemClick = {
										navController.navigateToDetails(ScanItemMapper.mapToDetail(it))
									}
								)
							}
						}
					}
				}

				Text(text = "Anime ", modifier = Modifier.padding(start = 20.dp))
				if (search.isAnimeLoading && searchLauched) {
					Loader(true)
				} else {
					Loader(false)
					if (search.listAnime.isEmpty()) {
						Empty(true)
					} else {
						Empty(false)
						LazyRow(
							modifier = Modifier
								.fillMaxWidth(),
							contentPadding = PaddingValues(8.dp),
						) {
							items(search.listAnime) { it ->
								// Search result
								AnimeSearchResult(
									it,
									onItemClick = {
										Log.d("TAG", "MainScreen: data $it")
										navController.navigateToDetails(AnimeItemMapper.mapToDetail(it))
									}
								)
							}
						}
					}
				}
				Text(text = "Tome ", modifier = Modifier.padding(start = 20.dp))
				if (search.isTomeLoading && searchLauched) {
					Loader(true)
				} else {
					Loader(false)
					if (search.listTome.isEmpty()) {
						Empty(true)
					} else {
						Empty(false)
						LazyRow(
							modifier = Modifier
								.fillMaxWidth(),
							contentPadding = PaddingValues(8.dp),
						) {
							items(search.listTome) { it ->
								// Search result
								Log.d("TAG", "MainScreen: $it")
								TomeSearchResult(

									it
								)
							}
						}
					}
				}

			} else {
				Text(
					text = "Faire une recherche !",
					textAlign = TextAlign.Center,
					modifier = Modifier
						.fillMaxWidth()
						.fillMaxHeight()
						.wrapContentHeight()
				)
			}
		}

	}
}
