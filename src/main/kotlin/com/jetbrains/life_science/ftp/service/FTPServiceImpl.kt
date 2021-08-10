package com.jetbrains.life_science.ftp.service

import com.jetbrains.life_science.config.FTPConfig
import org.apache.commons.net.ftp.FTPClient
import org.springframework.stereotype.Service
import java.io.InputStream
import java.io.OutputStream
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Service
class FTPServiceImpl(
    val config: FTPConfig
) : FTPService {

    val client = FTPClient()

    @PostConstruct
    override fun open(): Boolean {
        with(client) {
            connect(config.server, config.port)
            return login(config.username, config.password).also {
                setFileType(FTPClient.BINARY_FILE_TYPE)
            }
        }
    }

    @PreDestroy
    override fun close(): Boolean {
        with(client) {
            return logout().also {
                disconnect()
            }
        }
    }

    override fun getFile(remotePath: String, outputStream: OutputStream): Boolean {
        return client.retrieveFile(remotePath, outputStream)
    }

    override fun saveFile(destPath: String, inputStream: InputStream): Boolean {
        return client.storeFile(destPath, inputStream)
    }

    override fun deleteFile(remotePath: String): Boolean {
        return client.deleteFile(remotePath)
    }
}
