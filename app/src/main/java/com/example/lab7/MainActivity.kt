package com.example.lab7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    val message: String,
    val mirror: Boolean = false,
    val sentOn: Instant = Instant.now())

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(
                modifier = Modifier.padding(8.dp)
            ) {
                ChatWindow()
            }
        }
    }
}

@Composable
fun ChatEntryView(entry: ChatEntry) {
    val messageColor = if (entry.mirror) Color.Black else Color.White
    val backgroundColor = if (entry.mirror) Color(0xFFE5DFE8) else Color(0xFF6750A3)
    val horizontalArrangement = if (entry.mirror) Arrangement.Start else Arrangement.End

    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = horizontalArrangement,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        if (entry.mirror) {
            Image(
                painter = painterResource(entry.pfpId),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(80.dp)
            )
            Spacer(Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(backgroundColor, RoundedCornerShape(20.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        entry.message,
                        color = messageColor,
                        fontSize = 20.sp
                    )
                }
                Text(
                    DateTimeFormatter.ofPattern("HH:mm")
                        .withZone(ZoneId.systemDefault())
                        .format(entry.sentOn),
                    color = Color.Black,
                    fontSize = 10.sp
                )
            }
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.End
            ) {
                Box(
                    modifier = Modifier
                        .background(backgroundColor, RoundedCornerShape(20.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        entry.message,
                        color = messageColor,
                        fontSize = 20.sp
                    )
                }
                Text(
                    DateTimeFormatter.ofPattern("HH:mm")
                        .withZone(ZoneId.systemDefault())
                        .format(entry.sentOn),
                    color = Color.Black,
                    fontSize = 10.sp
                )
            }
            Spacer(Modifier.width(8.dp))
            Image(
                painter = painterResource(entry.pfpId),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(80.dp)
            )
        }
    }
}

@Composable
fun ChatWindow() {
    val typedMessage = remember { mutableStateOf("") }
    val sentMessages = remember { mutableStateListOf<ChatEntry>() }

    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(sentMessages.size) { index ->
                ChatEntryView(sentMessages[index])
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .shadow(8.dp, RoundedCornerShape(12.dp), clip = false)
                .background(Color.White, RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    sentMessages.add(ChatEntry(
                        R.drawable.cat_pfp,
                        typedMessage.value
                    ))
                    typedMessage.value = ""
                },
                enabled = !typedMessage.value.isEmpty(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send"
                )
            }
            TextField(
                value = typedMessage.value,
                onValueChange = {
                    typedMessage.value = it
                },
                placeholder = {
                    Text("Start Typing...")
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
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
                enabled = !typedMessage.value.isEmpty(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.CallReceived,
                    contentDescription = "Receive"
                )
            }
        }
    }
}

//TODO fix formatting (chat messages arent visible and
//buttons are cramped when using full size text label