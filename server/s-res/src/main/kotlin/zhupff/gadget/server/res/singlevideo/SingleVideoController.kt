package zhupff.gadget.server.res.singlevideo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import zhupff.gadget.server.basic.json.gson

@RestController
class SingleVideoController {

    @GetMapping("/singlevideos")
    fun requestSingleVideos(
        @RequestParam("last_video_id", required = false) lastVideoID: String?,
        @RequestParam("size", required = false, defaultValue = "10") size: Int): String {
        if (lastVideoID.isNullOrEmpty() || lastVideoID == "0") {
            SingleVideoDB.doShuffle()
        }
        val videos = SingleVideoDB.getItems(lastVideoID, size)
        return gson.toJson(videos)
    }

    @GetMapping("/reportsinglevideo")
    fun reportSingleVideo(
        @RequestParam("video_id") videoID: String,
    ) {
        SingleVideoDB.record(videoID)
    }
}