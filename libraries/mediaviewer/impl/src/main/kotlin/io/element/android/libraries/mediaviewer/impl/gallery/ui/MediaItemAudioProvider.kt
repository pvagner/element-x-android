/*
 * Copyright 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only
 * Please see LICENSE in the repository root for full details.
 */

package io.element.android.libraries.mediaviewer.impl.gallery.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.element.android.libraries.core.preview.loremIpsum
import io.element.android.libraries.designsystem.components.media.aWaveForm
import io.element.android.libraries.matrix.api.core.UniqueId
import io.element.android.libraries.matrix.api.media.MediaSource
import io.element.android.libraries.mediaviewer.api.anAudioMediaInfo
import io.element.android.libraries.mediaviewer.impl.gallery.MediaItem
import kotlinx.collections.immutable.toImmutableList

class MediaItemAudioProvider : PreviewParameterProvider<MediaItem.Audio> {
    override val values: Sequence<MediaItem.Audio>
        get() = sequenceOf(
            aMediaItemAudio(),
            aMediaItemAudio(
                filename = "A long filename that should be truncated.mp3",
                caption = "A caption",
            ),
            aMediaItemAudio(
                caption = loremIpsum,
            ),
            aMediaItemAudio(
                waveform = aWaveForm(),
            ),
        )
}

fun aMediaItemAudio(
    id: UniqueId = UniqueId("fileId"),
    filename: String = "filename",
    caption: String? = null,
    duration: String? = "1:23",
    waveform: List<Float>? = null,
): MediaItem.Audio {
    return MediaItem.Audio(
        id = id,
        eventId = null,
        mediaInfo = anAudioMediaInfo(
            filename = filename,
            caption = caption,
        ),
        mediaSource = MediaSource(""),
        duration = duration,
        waveform = waveform?.toImmutableList(),
    )
}
