package com.jetbrains.life_science.ftp.service

import java.io.InputStream
import java.io.OutputStream

interface FTPService {
    fun open(): Boolean
    fun close(): Boolean

    fun getFile(remotePath: String, outputStream: OutputStream): Boolean
    fun saveFile(destPath: String, inputStream: InputStream): Boolean
    fun deleteFile(remotePath: String): Boolean
}
