package com.kevinvi.doraibu.app.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kevinvi.doraibu.app.MainActivityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {

	val viewModel: MainActivityViewModel = hiltViewModel()
	var text by remember { mutableStateOf("") }
	val toto by viewModel.stateData.collectAsStateWithLifecycle()

	Scaffold {
		Column {
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

			if (toto.list.isNotEmpty()) Text(text = "Scan ")
			LazyRow(
				modifier = Modifier
					.fillMaxWidth(),
				contentPadding = PaddingValues(16.dp),
				horizontalArrangement = Arrangement.spacedBy(4.dp)
			) {
				items(toto.list) { it ->
					// Search result
					ScanSearchResult(
						it
					)
				}
			}

		}

	}
}
