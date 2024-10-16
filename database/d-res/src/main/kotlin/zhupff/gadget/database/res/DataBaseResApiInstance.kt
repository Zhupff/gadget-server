package zhupff.gadget.database.res

import com.google.auto.service.AutoService
import zhupff.gadget.basic.logger.logI
import zhupff.gadget.database.DataBaseResApi
import zhupff.gadget.database.model.statics.*

@AutoService(DataBaseResApi::class)
class DataBaseResApiInstance : DataBaseResApi {

    init {
        StaticTag
        StaticUser
        StaticImage
        StaticVideo
        StaticAlbum

        logI("StaticTag count: ${StaticTag.size / 2}")
        logI("StaticUser count: ${StaticUser.size / 2}")
        logI("StaticImage count: ${StaticImage.size / 2}")
        logI("StaticVideo count: ${StaticVideo.size / 2}")
        logI("StaticAlbum count: ${StaticAlbum.size / 2}")
    }
}