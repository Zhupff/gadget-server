package zhupff.gadget.server.model

import zhupff.gadget.server.model.basic.BasicUser

class User(
    id: String,
    name: String,
    avatar: String,
) : BasicUser() {

    override val id: String = id
    override var name: String = name
    override var avatar: String = avatar
}