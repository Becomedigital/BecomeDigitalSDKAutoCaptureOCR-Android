package com.becomedigital.blinkidintegration

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

@Composable
fun VerificationScreen(
    status: String,
    onStart: (String) -> Unit,
    onMessage: (String) -> Unit
) {
    val context = LocalContext.current
    var userId by rememberSaveable { mutableStateOf("") }
    var pendingUserId by rememberSaveable { mutableStateOf("") }
    val cameraPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        val id = pendingUserId
        pendingUserId = ""
        if (granted && id.isNotEmpty()) onStart(id)
        else if (!granted) onMessage(context.getString(R.string.camera_permission_required))
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(stringResource(R.string.host_app_name), style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(
            value = userId,
            onValueChange = { userId = it },
            label = { Text(stringResource(R.string.user_id_label)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Button(onClick = {
            val id = userId.trim()
            if (id.isEmpty()) {
                onMessage(context.getString(R.string.user_id_required))
            } else if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                onStart(id)
            } else {
                pendingUserId = id
                cameraPermission.launch(Manifest.permission.CAMERA)
            }
        }) {
            Text(stringResource(R.string.start_onboarding))
        }
        Text(status)
    }
}
