package zhupff.gadget.server.model.basic

import java.io.Serializable

abstract class BasicVideo : Serializable {

    abstract val id: String
    abstract val title: String
    abstract val width: Int
    abstract val height: Int
    abstract val duration: Long
    abstract val url: String
}