/*
 * Copyright 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only
 * Please see LICENSE in the repository root for full details.
 */

package io.element.android.libraries.mediaviewer.impl.gallery.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import io.element.android.compound.theme.ElementTheme
import io.element.android.compound.tokens.generated.CompoundIcons
import io.element.android.libraries.core.extensions.withBrackets
import io.element.android.libraries.designsystem.components.media.WaveformPlaybackView
import io.element.android.libraries.designsystem.preview.ElementPreview
import io.element.android.libraries.designsystem.preview.PreviewsDayNight
import io.element.android.libraries.designsystem.theme.components.HorizontalDivider
import io.element.android.libraries.designsystem.theme.components.Icon
import io.element.android.libraries.designsystem.theme.components.IconButton
import io.element.android.libraries.designsystem.theme.components.Text
import io.element.android.libraries.mediaviewer.impl.gallery.MediaItem
import kotlinx.collections.immutable.toPersistentList

@Composable
fun AudioItemView(
    audio: MediaItem.Audio,
    onClick: () -> Unit,
    onShareClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onInfoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 16.dp, end = 16.dp),
    ) {
        FilenameRow(
            audio = audio,
            onClick = onClick,
        )
        val caption = audio.mediaInfo.caption
        if (caption != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Caption(caption)
        }
        Spacer(modifier = Modifier.height(16.dp))
        ActionIconsRow(
            onShareClick = onShareClick,
            onDownloadClick = onDownloadClick,
            onInfoClick = onInfoClick,
        )
        HorizontalDivider()
    }
}

@Composable
private fun FilenameRow(
    audio: MediaItem.Audio,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                color = ElementTheme.colors.bgSubtleSecondary,
                shape = RoundedCornerShape(12.dp),
            )
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(start = 12.dp, end = 36.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier
                .background(
                    color = ElementTheme.colors.bgCanvasDefault,
                    shape = CircleShape,
                )
                .border(
                    width = 1.dp,
                    color = ElementTheme.colors.borderInteractiveSecondary,
                    shape = CircleShape,
                )
                .size(36.dp)
                .padding(6.dp),
            imageVector = CompoundIcons.PlaySolid(),
            tint = ElementTheme.colors.iconSecondary,
            contentDescription = null,
        )
        audio.duration?.let {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = audio.duration,
                style = ElementTheme.typography.fontBodyMdMedium,
                color = ElementTheme.colors.textSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        val waveform = audio.waveform
        if (waveform == null) {
            Text(
                text = audio.mediaInfo.filename,
                modifier = Modifier.weight(1f),
                style = ElementTheme.typography.fontBodyLgRegular,
                color = ElementTheme.colors.textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            val formattedSize = audio.mediaInfo.formattedFileSize
            if (formattedSize.isNotEmpty()) {
                Text(
                    text = formattedSize.withBrackets(),
                    style = ElementTheme.typography.fontBodyLgRegular,
                    color = ElementTheme.colors.textPrimary,
                )
            }
        } else {
            WaveformPlaybackView(
                modifier = Modifier
                    .weight(1f)
                    .height(34.dp),
                playbackProgress = 0f,
                showCursor = false,
                waveform = waveform.toPersistentList(),
                onSeek = {},
                seekEnabled = false,
            )
        }
    }
}

@Composable
private fun Caption(caption: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = caption,
        maxLines = 5,
        overflow = TextOverflow.Ellipsis,
        style = ElementTheme.typography.fontBodyLgRegular,
        color = ElementTheme.colors.textPrimary,
    )
}

@Composable
private fun ActionIconsRow(
    onShareClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onInfoClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(
            onClick = onShareClick,
        ) {
            Icon(
                imageVector = CompoundIcons.ShareAndroid(),
                contentDescription = null,
            )
        }
        IconButton(
            onClick = onDownloadClick,
        ) {
            Icon(
                imageVector = CompoundIcons.Download(),
                contentDescription = null,
            )
        }
        IconButton(
            onClick = onInfoClick,
        ) {
            Icon(
                imageVector = CompoundIcons.Info(),
                contentDescription = null,
            )
        }
    }
}

@PreviewsDayNight
@Composable
internal fun AudioItemViewPreview(
    @PreviewParameter(MediaItemAudioProvider::class) audio: MediaItem.Audio,
) = ElementPreview {
    AudioItemView(
        audio = audio,
        onClick = {},
        onShareClick = {},
        onDownloadClick = {},
        onInfoClick = {},
    )
}
