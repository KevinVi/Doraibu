package com.kevinvi.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@Composable
fun NotificationPermissionDialog(
	onConfirmation: () -> Unit,
) {
	AlertDialog(
		icon = {
			Icon(
				painter = painterResource(id = R.drawable.baseline_notifications_active_24),
				contentDescription = null,
				modifier = Modifier.size(48.dp),
			)
		},
		title = {
			Text(
				text = "Autoriser les notifications",
				textAlign = TextAlign.Center,
			)
		},
		text = {
			Text(
				text = "Rester notifier des mis à jour de vos favoris",
				textAlign = TextAlign.Center,
			)
		},
		onDismissRequest = {},
		confirmButton = {
			TextButton(
				onClick = { onConfirmation() },
			) {
				Text(text = "Ok")
			}
		},
		shape = MaterialTheme.shapes.large,
		properties = DialogProperties(
			dismissOnBackPress = false,
			dismissOnClickOutside = false,
		),
	)
}

@Preview
@Composable
private fun NotificationPermissionDialogPreview() {
	NotificationPermissionDialog(
		onConfirmation = {},
	)
}
