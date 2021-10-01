package com.jetbrains.life_science.replicator.serializer.user

import com.jetbrains.life_science.replicator.enities.UserPersonalDataStorageEntity
import com.jetbrains.life_science.user.data.entity.UserPersonalData
import org.springframework.stereotype.Component

@Component
class UserDataToStorageEntityMapper {
    fun getStorageEntity(data: UserPersonalData?): UserPersonalDataStorageEntity? {
        if (data == null) {
            return null
        }
        return UserPersonalDataStorageEntity(
            firstName = data.firstName,
            lastName = data.lastName,
            doctorDegree = data.doctorDegree ?: false,
            about = data.about,
            academicDegree = data.academicDegree.ordinal,
            orcid = data.orcid,
            researchId = data.researchId
        )
    }
}
