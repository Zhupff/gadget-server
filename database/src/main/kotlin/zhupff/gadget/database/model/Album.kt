package zhupff.gadget.database.model

open class Album(
    val id: String,
    val name: String,
    val users: Array<User>,
    val tags: Array<Tag>,
) {
    open val content: Array<Any> = emptyArray()
}