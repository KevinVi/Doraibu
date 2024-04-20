package com.kevinvi.doraibu.app.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kevinvi.doraibu.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
	navController: NavHostController = rememberNavController(),
	viewModel: SettingsViewModel = hiltViewModel(),
) {

	val getTheme = viewModel.settingsUiState.collectAsStateWithLifecycle(
		initialValue = 0,
	)
	var checked by remember { mutableStateOf(true) }
	Column(
		Modifier
			.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally
	) {

		Image(
			painterResource(R.drawable.logo_app),
			contentDescription = null,
			contentScale = ContentScale.Crop,
			modifier = Modifier
				.height(200.dp)
				.width(200.dp)
		)
		val options = listOf("Système", "Clair", "Sombre")
		SingleChoiceSegmentedButtonRow(
			Modifier
				.fillMaxWidth()
				.padding(18.dp)
		) {
			options.forEachIndexed { index, label ->
				SegmentedButton(
					shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
					onClick = {
						viewModel.updateDarkModeConfig(index)
					},
					selected = getTheme.value == index
				) {
					Text(label)
				}
			}
		}
		HorizontalDivider()
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(18.dp),
			verticalAlignment = Alignment.CenterVertically,
		) {
			Text(text = "Activer les notifications")
			Spacer(modifier = Modifier.weight(1f))
			Switch(
				checked = checked,
				onCheckedChange = {
					checked = it
				},
				thumbContent = if (checked) {
					{
						Icon(
							imageVector = Icons.Filled.Check,
							contentDescription = null,
							modifier = Modifier.size(SwitchDefaults.IconSize),
						)
					}
				} else {
					null
				}
			)
		}
	}

}