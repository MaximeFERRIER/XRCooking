package fr.droidfactory.xrcooking.ui.presentation.mealdetails

import androidx.compose.ui.unit.Dp

internal fun getYoutubeVideoPlayerScript(youtubeUrl: String, width: Dp, height: Dp): String {
    val videoId = youtubeUrl.substringAfter("v=")

    return """
        <iframe width="${width.value}" height="${height.value}" src="https://www.youtube.com/embed/$videoId" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>
    """.trimIndent()
}