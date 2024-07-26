package zhupff.gadget.client

import org.jcodec.api.awt.AWTFrameGrab
import org.jcodec.common.io.NIOUtils
import zhupff.gadget.common.api.GsonApi
import zhupff.gadget.common.api.RES_STATIC_DIR
import zhupff.gadget.common.model.Video
import javax.imageio.ImageIO

object MediaHelper {

    fun parseVideo(path: String) {
        val file = RES_STATIC_DIR.resolve(path)
        if (!file.exists()) {
            throw RuntimeException("${file.path} not exists!")
        }
        val grap = AWTFrameGrab.createAWTFrameGrab(NIOUtils.readableChannel(file))
        val frame = grap.frame
        val video = object : Video(
            id = "",
            name = "",
            width = frame.width,
            height = frame.height,
            duration = (grap.videoTrack.meta.totalDuration * 1000).toLong(),
            cover = path.replace('.', '_') + "_cover.png",
            url = path,
            users = emptyArray(),
            tags = emptyArray(),
        ) {}
        ImageIO.write(frame, "png", RES_STATIC_DIR.resolve(video.cover).also { println(it.path) })
        println(GsonApi.toJson(video))
    }
}