package com.example.lab7

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChatWindowTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSendAndReceiveMessages() {
        // Set the content
        composeTestRule.setContent {
            ChatWindow()
        }

        val testMessage = "Hello Test"

        composeTestRule.onNodeWithText("Start Typing...").performTextInput(testMessage)

        composeTestRule.onNodeWithContentDescription("Send").performClick()

        composeTestRule.onNodeWithText(testMessage).assertIsDisplayed()

        val receiveMessage = "Received Test"
        composeTestRule.onNodeWithText("Start Typing...").performTextInput(receiveMessage)

        composeTestRule.onNodeWithContentDescription("Receive").performClick()

        composeTestRule.onNodeWithText(receiveMessage).assertIsDisplayed()
    }
}
