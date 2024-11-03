package zhupff.gadget.server.model.single

import java.io.Serializable

class SingleVideo(
    val id: String,
    val width: Int,
    val height: Int,
    val duration: Long,
    val url: String,
    val cover: String,
) : Serializable