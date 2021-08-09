package com.jetbrains.life_science.user.data.service

import com.jetbrains.life_science.exception.not_found.UserFTPDataNotFoundException
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.data.entity.UserFTPData
import com.jetbrains.life_science.user.data.repository.UserFTPDataRepository
import org.springframework.stereotype.Service

@Service
class UserFTPDataServiceImpl(
    val repository: UserFTPDataRepository
) : UserFTPDataService {
    override fun getByCredentials(credentials: Credentials): UserFTPData {
        return repository.findByCredentials(credentials)
            ?: throw UserFTPDataNotFoundException("UserFTPData with credentials ${credentials.id} is not found")
    }

    override fun contains(fileName: String, credentials: Credentials): Boolean {
        val userFTPData = getByCredentials(credentials)
        return userFTPData.fileNames.contains(fileName)
    }

    override fun addFileName(credentials: Credentials, fileName: String): UserFTPData {
        return if (contains(fileName, credentials)) {
            getByCredentials(credentials)
        } else {
            val ftpData = getByCredentials(credentials)
            ftpData.fileNames.add(fileName)
            repository.save(ftpData)
        }
    }

    override fun removeFileName(credentials: Credentials, fileName: String): UserFTPData {
        return if (!contains(fileName, credentials)) {
            getByCredentials(credentials)
        } else {
            val ftpData = getByCredentials(credentials)
            ftpData.fileNames.remove(fileName)
            repository.save(ftpData)
        }
    }
}
