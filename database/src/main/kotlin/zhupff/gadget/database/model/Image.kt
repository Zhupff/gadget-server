package zhupff.gadget.database.model

abstract class Image(
    val id: String,
    val name: String,
    val width: Int,
    val height: Int,
    val url: String,
    val users: Array<User>,
    val tags: Array<Tag>,
) {
}