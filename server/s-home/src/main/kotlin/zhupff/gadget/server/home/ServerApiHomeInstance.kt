package zhupff.gadget.server.home

import com.google.auto.service.AutoService
import zhupff.gadget.basic.json.JsonUtil
import zhupff.gadget.database.model.statics.StaticImage
import zhupff.gadget.database.model.statics.StaticVideo
import zhupff.gadget.server.ServerApi
import kotlin.math.min

@AutoService(ServerApi.Home::class)
class ServerApiHomeInstance : ServerApi.Home {

    override fun home(): String {
        val res = ArrayList<Any>()
        res.addAll(StaticImage)
        res.addAll(StaticVideo)
        val list = res.toList().shuffled().let {
            it.subList(0, min(10, it.size))
        }
        return JsonUtil.toJson(list)
    }
}