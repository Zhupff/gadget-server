package zhupff.gadget.server.model.basic

import java.io.Serializable

abstract class BasicImage : Serializable {

    abstract val id: String
    abstract val title: String
    abstract val width: Int
    abstract val height: Int
    abstract val url: String
}