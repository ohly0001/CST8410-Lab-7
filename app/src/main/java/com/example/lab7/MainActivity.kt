package com.example.lab7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                ChatWindow()
            }
        }
    }
}

@Composable
fun ChatEntry(mirror: Boolean, pfpId: Int, message: String, sentOn: Instant) {
    val timestamp = DateTimeFormatter
        .ofPattern("yyyy-MM-dd HH:mm:ss")
        .withZone(ZoneOffset.UTC)
        .format(sentOn)

    val elements = listOf<@Composable () -> Unit>(
        { Image(painterResource(pfpId), "Profile Picture") },
        {
            Column {
                Text(message)
                Text(timestamp, style = MaterialTheme.typography.labelSmall)
            }
        }
    )

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        if (!mirror) {
            elements.forEach { it() }
        } else {
            elements.reversed().forEach { it() }
        }
    }
}

@Composable
fun ChatWindow() {
    Column {

    }
}