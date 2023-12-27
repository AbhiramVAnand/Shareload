package com.abhiram.shareload.responses

data class YoutubeResponse(
    var author: String?,
    var duration: Int?,
    var durationText: String?,
    var links: List<Link?>?,
    var message: Any?,
    var picture: String?,
    var published: Int?,
    var publishedText: String?,
    var src_url: String?,
    var success: Boolean?,
    var title: String?
)