package com.example.lab7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.CallReceived
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class ChatEntry(
    val pfpId: Int,
    var message: String,
    val mirror: Boolean = false,
    val sentOn: Instant = Instant.now()
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                ChatWindow()
            }
        }
    }
}

@Composable
fun Details(
    entry: ChatEntry,
    onDelete: () -> Unit,
    onClose: (Boolean) -> Unit
) {
    var canEdit by remember { mutableStateOf(false) }
    var editedMessage by remember { mutableStateOf(entry.message) }
    var performedEdit by remember { mutableStateOf(false) }

    Surface(
        color = Color(0xFFF7F7F7),
        tonalElevation = 6.dp,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Message Details", fontSize = 20.sp)

            TextField(
                value = editedMessage,
                onValueChange = {
                    editedMessage = it
                    performedEdit = true
                },
                readOnly = !canEdit,
                label = { Text("Message") },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                "Sent On: ${
                    DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss")
                        .withZone(ZoneId.systemDefault())
                        .format(entry.sentOn)
                }"
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { canEdit = !canEdit }) {
                    Icon(
                        if (canEdit) Icons.Filled.Save else Icons.Filled.Edit,
                        contentDescription = if (canEdit) "Save" else "Edit"
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(if (canEdit) "Save" else "Edit")
                }

                Button(onClick = { onDelete() }) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete")
                    Spacer(Modifier.width(4.dp))
                    Text("Delete")
                }

                Button(onClick = {
                    if (performedEdit) {
                        entry.message = editedMessage
                    }
                    onClose(performedEdit)
                }) {
                    Icon(Icons.Filled.Close, contentDescription = "Close")
                    Spacer(Modifier.width(4.dp))
                    Text("Close")
                }
            }
        }
    }
}

@Composable
fun ChatEntryView(entry: ChatEntry, onClick: () -> Unit) {
    val messageColor = if (entry.mirror) Color.Black else Color.White
    val backgroundColor = if (entry.mirror) Color(0xFFE5DFE8) else Color(0xFF6750A3)
    val horizontalArrangement = if (entry.mirror) Arrangement.Start else Arrangement.End

    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = horizontalArrangement,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick() }
    ) {
        if (entry.mirror) {
            Image(
                painter = painterResource(entry.pfpId),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(60.dp)
            )
            Spacer(Modifier.width(8.dp))
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = if (entry.mirror) Alignment.Start else Alignment.End
        ) {
            Box(
                modifier = Modifier
                    .background(backgroundColor, RoundedCornerShape(20.dp))
                    .padding(8.dp)
            ) {
                Text(entry.message, color = messageColor, fontSize = 18.sp)
            }

            Text(
                DateTimeFormatter.ofPattern("HH:mm")
                    .withZone(ZoneId.systemDefault())
                    .format(entry.sentOn),
                color = Color.Gray,
                fontSize = 10.sp
            )
        }

        if (!entry.mirror) {
            Spacer(Modifier.width(8.dp))
            Image(
                painter = painterResource(entry.pfpId),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(60.dp)
            )
        }
    }
}

@Composable
fun ChatWindow() {
    val typedMessage = remember { mutableStateOf("") }
    val sentMessages = remember { mutableStateListOf<ChatEntry>() }
    var selectedEntry by remember { mutableStateOf<ChatEntry?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(sentMessages.size) { index ->
                    ChatEntryView(sentMessages[index]) {
                        selectedEntry = sentMessages[index]
                    }
                }
            }

            Row(
                modifier = Modifier
                    .shadow(8.dp, RoundedCornerShape(12.dp))
                    .background(Color.White, RoundedCornerShape(12.dp))
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        sentMessages.add(ChatEntry(R.drawable.cat_pfp, typedMessage.value))
                        typedMessage.value = ""
                    },
                    enabled = typedMessage.value.isNotEmpty(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
                }

                TextField(
                    value = typedMessage.value,
                    onValueChange = { typedMessage.value = it },
                    placeholder = { Text("Start Typing...") },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f)
                )

                Button(
                    onClick = {
                        sentMessages.add(
                            ChatEntry(
                                R.drawable.dog_pfp,
                                typedMessage.value,
                                mirror = true
                            )
                        )
                        typedMessage.value = ""
                    },
                    enabled = typedMessage.value.isNotEmpty(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.CallReceived, contentDescription = "Receive")
                }
            }
        }

        // Overlay the details panel when selected
        selectedEntry?.let { entry ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xAA000000))
                    .clickable(enabled = false) {}
            ) {
                Details(
                    entry = entry,
                    onDelete = {
                        sentMessages.remove(entry)
                        selectedEntry = null
                    },
                    onClose = { _ -> selectedEntry = null }
                )
            }
        }
    }
}
