package com.jetbrains.life_science.ftp.service

import com.jetbrains.life_science.exception.ftp.FTPFileAlreadyExistsException
import com.jetbrains.life_science.ftp.entity.FTPFile
import com.jetbrains.life_science.ftp.factory.FTPFileFactory
import com.jetbrains.life_science.ftp.repository.FTPFileRepository
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException

@Service
class FTPFileServiceImpl(
    val repository: FTPFileRepository,
    val factory: FTPFileFactory,
    val ftpService: FTPService
) : FTPFileService {
    override fun create(info: FTPFileInfo): FTPFile {
        if (repository.existsByFilePath(info.path)) {
            throw FTPFileAlreadyExistsException("FTPFile with path ${info.path} already exists")
        }
        val file = factory.create(info)
        val input = info.file.inputStream
        ftpService.saveFile(file.filePath, input)
        return repository.save(file)
    }

    override fun getInfo(id: Long): FTPFile {
        return repository.findById(id).orElseThrow {
            FileNotFoundException("File with id $id is not found")
        }
    }

    override fun get(id: Long): ByteArray {
        val ftpFile = getInfo(id)
        val output = ByteArrayOutputStream()
        ftpService.getFile(ftpFile.filePath, output)
        return output.toByteArray()
    }
}
