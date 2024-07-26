package zhupff.gadget.common.model

import zhupff.gadget.common.api.GsonApi

open class QrCodeAction(
    val action: Action,
) : MutableMap<String, Any> by HashMap() {

    enum class Action(
        val code: Int,
        val description: String,
    ) {
        SERVER_URL(1, "server url"),
    }

    init {
        put("code", action.code)
        put("description", action.description)
    }

    override fun toString(): String = GsonApi.toJson(this)
}


class ServerUrlAction(val url: String) : QrCodeAction(Action.SERVER_URL) {
    init {
        put("url", url)
    }
}