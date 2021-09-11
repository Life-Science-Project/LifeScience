package com.jetbrains.life_science.user.credentials.factory

import com.jetbrains.life_science.replicator.enities.CredentialsStorageEntity
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.credentials.entity.Role
import com.jetbrains.life_science.user.credentials.service.NewUserInfo
import com.jetbrains.life_science.user.data.factory.UserPersonalDataFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class CredentialsFactory(
    val userPersonalDataFactory: UserPersonalDataFactory,
) {
    val encoder: PasswordEncoder = BCryptPasswordEncoder()

    fun createUser(info: NewUserInfo, roles: MutableCollection<Role>): Credentials {
        val password = encoder.encode(info.password)
        val credentials = Credentials(0, info.email, password, roles)
        credentials.userPersonalData = userPersonalDataFactory.create(info, credentials)
        return credentials
    }

    fun copyUser(storageEntity: CredentialsStorageEntity, roles: MutableCollection<Role>): Credentials {
        val credentials = Credentials(0, storageEntity.email, storageEntity.password, roles)
        if (storageEntity.userData != null) {
            credentials.userPersonalData = userPersonalDataFactory.create(storageEntity.userData, credentials)
        }
        return credentials
    }
}
