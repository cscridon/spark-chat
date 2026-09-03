package com.csc.sparkchat.presentation.chat.detail.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.csc.sparkchat.R
import com.csc.sparkchat.core.designsystem.components.GradientIconButton
import com.csc.sparkchat.core.designsystem.theme.SparkChatTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatTopBar(
    userName: String?,
    avatarId: String?,
    sendAsOtherUser: Boolean,
    onSendAsOtherUserChange: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    showShadow: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shadowElevation = if (showShadow) 5.dp else 0.dp,
        color = Color.White
    ) {
        TopAppBar(
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (userName != null && avatarId != null) {
                        AsyncImage(
                            model = "file:///android_asset/$avatarId",
                            contentDescription = null,
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape),
                            error = if (LocalInspectionMode.current) {
                                painterResource(R.drawable.img_avatar_mock)
                            } else {
                                ColorPainter(MaterialTheme.colorScheme.surface)
                            }
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = userName,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                scrolledContainerColor = Color.White
            ),
            navigationIcon = {
                GradientIconButton(
                    onClick = onBackClick,
                    icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = stringResource(R.string.back)
                )
            },
            actions = {
                FilterChip(
                    selected = sendAsOtherUser,
                    onClick = { onSendAsOtherUserChange(!sendAsOtherUser) },
                    label = {
                        Text(
                            if (sendAsOtherUser) {
                                stringResource(R.string.replying)
                            } else {
                                stringResource(R.string.me)
                            }
                        )
                    },
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ChatTopBarPreview() {
    SparkChatTheme {
        ChatTopBar(
            userName = "Sarah",
            avatarId = "avatar_1",
            sendAsOtherUser = false,
            onSendAsOtherUserChange = {},
            onBackClick = {},
            showShadow = true
        )
    }
}
