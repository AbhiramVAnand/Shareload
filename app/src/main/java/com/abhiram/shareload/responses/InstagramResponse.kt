package com.abhiram.shareload.responses

data class InstagramResponse(
    var links: List<LinkX>?,
    var picture: String?,
    var src_url: String?,
    var success: Boolean?,
    var title: String?
)