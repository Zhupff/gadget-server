package zhupff.gadget.common.api

import com.google.gson.Gson

object GsonApi {

    private val gson = Gson()

    fun toJson(obj: Any): String = gson.toJson(obj)
}