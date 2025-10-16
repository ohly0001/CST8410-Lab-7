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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

data class ChatEntry(
    val pfpId: Int,
    val message: String,
    val mirror: Boolean = false,
    val sentOn: Instant = Instant.now())

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface() {
                ChatWindow()
            }
        }
    }
}

@Composable
fun ChatEntryView(entry: ChatEntry) {
    val layoutDirection = if (entry.mirror) LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(entry.pfpId),
                contentDescription = "Profile Picture",
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(entry.message)
                Text(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                        .withZone(ZoneOffset.UTC)
                        .format(entry.sentOn),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Composable
fun ChatWindow() {
    val typedMessage = remember { mutableStateOf("") }
    val sentMessages = remember { mutableListOf<ChatEntry>() }

    Column(
        verticalArrangement = Arrangement.Top
    ) {
        LazyColumn {
            items(sentMessages.size) { index ->
                ChatEntryView(sentMessages[index])
            }
        }
        Row{
            Button(
                onClick = {
                    sentMessages.add(ChatEntry(
                        R.drawable.cat_pfp,
                        typedMessage.value
                    ))
                    typedMessage.value = ""
                },
                enabled = !typedMessage.value.isEmpty()
            ) {
                Text("S")
            }
            TextField(
                value = typedMessage.value,
                onValueChange = {
                    typedMessage.value = it
                },
                placeholder = {
                    Text("Type Here...")
                }
            )
            Button(
                onClick = {
                    sentMessages.add(ChatEntry(
                        R.drawable.dog_pfp,
                        typedMessage.value,
                        true
                    ))
                    typedMessage.value = ""
                },
                enabled = !typedMessage.value.isEmpty()
            ) {
                Text("R")
            }
        }
    }
}

//TODO fix formatting (chat messages arent visible and
//buttons are cramped when using full size text label