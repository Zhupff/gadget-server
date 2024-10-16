package zhupff.gadget.basic.media

import org.jcodec.api.awt.AWTFrameGrab
import org.jcodec.common.io.NIOUtils
import zhupff.gadget.basic.file.STATIC_RES_DIR
import zhupff.gadget.basic.json.JsonUtil
import zhupff.gadget.database.model.Video
import javax.imageio.ImageIO

object MediaUtil {

    fun parseVideo(path: String) {
        val file = STATIC_RES_DIR.resolve(path)
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
            cover = path.removeRange(path.lastIndexOf('.'), path.length) + "_cover.png",
            url = path,
            users = emptyArray(),
            tags = emptyArray(),
            albumId = ""
        ) {}
        ImageIO.write(frame, "png", STATIC_RES_DIR.resolve(video.cover).also { println(it.path) })
        println(JsonUtil.toJson(video))
    }
}