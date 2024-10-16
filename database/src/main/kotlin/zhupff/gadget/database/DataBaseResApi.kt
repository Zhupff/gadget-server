package zhupff.gadget.database

import java.util.ServiceLoader

interface DataBaseResApi {

    companion object : DataBaseResApi by ServiceLoader.load(DataBaseResApi::class.java).first()
}