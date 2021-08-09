package com.jetbrains.life_science.ftp.service

import com.jetbrains.life_science.config.FTPConfig
import org.apache.commons.net.ftp.FTPClient
import org.springframework.stereotype.Service
import java.io.InputStream
import java.io.OutputStream

@Service
class FTPServiceImpl(
    val config: FTPConfig
) : FTPService {

    lateinit var client: FTPClient

    override fun open(): Boolean {
        client = FTPClient()
        client.connect(config.server, config.port)
        client.login(config.username, config.password)
        client.setFileType(FTPClient.BINARY_FILE_TYPE)
        return true
    }

    override fun close(): Boolean {
        return if (this::client.isInitialized) {
            client.logout()
            client.disconnect()
            true
        } else {
            false
        }
    }

    override fun getFile(remotePath: String, outputStream: OutputStream): Boolean {
        open()
        return client.retrieveFile(remotePath, outputStream).also { close() }
    }

    override fun saveFile(destPath: String, inputStream: InputStream): Boolean {
        open()
        return client.storeFile(destPath, inputStream).also { close() }
    }
}
