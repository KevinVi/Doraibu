package com.kevinvi.doraibu.app.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kevinvi.anime.ui.AnimeSearchResult
import com.kevinvi.doraibu.app.MainActivityViewModel
import com.kevinvi.doraibu.app.navigation.navigateToAnimeDetails
import com.kevinvi.doraibu.app.navigation.navigateToScanDetails
import com.kevinvi.scan.ui.ScanSearchResult
import com.kevinvi.tome.ui.TomeSearchResult

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
	navController: NavHostController = rememberNavController()) {

	val viewModel: MainActivityViewModel = hiltViewModel()
	var text by remember { mutableStateOf("") }
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
					Log.d("TAG", "MainScreen: $text")
					viewModel.search(text)
					focusManager.clearFocus()
					keyboardController?.hide()

				},
				active = false,
				onActiveChange = {
				},
				placeholder = {
					Text(text = "search")
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

			if (search.list.isNotEmpty()) Text(text = "Scan ", modifier = Modifier.padding(horizontal = 20.dp))
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
							navController.navigateToScanDetails(it)
						}
					)
				}
			}

			if (search.listAnime.isNotEmpty()) Text(text = "Anime ", modifier = Modifier.padding(start = 20.dp))
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
							navController.navigateToAnimeDetails(it)
						}
					)
				}
			}
			if (search.listTome.isNotEmpty()) Text(text = "Tome ", modifier = Modifier.padding(start = 20.dp))
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
}
