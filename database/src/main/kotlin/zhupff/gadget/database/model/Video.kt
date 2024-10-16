package zhupff.gadget.database.model

open class Video(
    val id: String,
    val name: String,
    val width: Int,
    val height: Int,
    val duration: Long,
    val cover: String,
    val url: String,
    val users: Array<User>,
    val tags: Array<Tag>,
    val albumId: String,
) {
}