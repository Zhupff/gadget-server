package zhupff.gadget.basic.json

import com.google.gson.GsonBuilder
import org.json.JSONObject

object JsonUtil {

    private val gson = GsonBuilder()
        .create()

    fun toJson(obj: Any): String = gson.toJson(obj)

    fun <T> fromJson(json: JSONObject, cls: Class<T>): T? = gson.fromJson(json.toString(), cls)
}