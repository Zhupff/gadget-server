package zhupff.gadget.common.api

import java.util.ServiceLoader

interface ServerApi {
    companion object : ServerApi by ServiceLoader.load(ServerApi::class.java).first()
}