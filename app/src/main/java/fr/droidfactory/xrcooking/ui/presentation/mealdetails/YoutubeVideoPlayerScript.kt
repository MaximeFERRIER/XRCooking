package fr.droidfactory.xrcooking.ui.presentation.mealdetails

internal fun getYoutubeVideoPlayerScript(youtubeUrl: String): String {
    val videoId = youtubeUrl.substringAfter("v=")

    return """
        <iframe width="1488" height="816" src="https://www.youtube.com/embed/$videoId" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>
    """.trimIndent()
}