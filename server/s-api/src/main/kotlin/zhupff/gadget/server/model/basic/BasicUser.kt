package zhupff.gadget.server.model.basic

import java.io.Serializable

abstract class BasicUser : Serializable {

    abstract val id: String
    abstract val name: String
    abstract val avatar: String
}