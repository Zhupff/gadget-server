package zhupff.gadget.common.api

import com.google.gson.GsonBuilder

object GsonApi {

    private val gson = GsonBuilder()
        .create()

    fun toJson(obj: Any): String = gson.toJson(obj)
}