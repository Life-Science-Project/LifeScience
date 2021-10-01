package com.jetbrains.life_science.user.data.factory

import com.jetbrains.life_science.replicator.enities.UserPersonalDataStorageEntity
import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.data.entity.UserPersonalData
import com.jetbrains.life_science.user.data.service.UserPersonalDataInfo
import com.jetbrains.life_science.user.credentials.service.NewUserInfo
import com.jetbrains.life_science.user.degree.AcademicDegree
import com.jetbrains.life_science.user.group.service.FavoriteGroupService
import com.jetbrains.life_science.user.organisation.entity.Organisation
import org.springframework.stereotype.Component

@Component
class UserPersonalDataFactory(
    val favoriteGroupService: FavoriteGroupService
) {

    fun create(info: NewUserInfo, credentials: Credentials): UserPersonalData {
        return UserPersonalData(
            id = 0,
            firstName = info.firstName,
            lastName = info.lastName,
            organisations = mutableListOf(),
            credentials = credentials,
            favoriteGroup = favoriteGroupService.createDefault()
        )
    }

    val academicDegreeMap: Map<Int, AcademicDegree> = AcademicDegree.values().associateBy { it.ordinal }

    fun create(storageEntity: UserPersonalDataStorageEntity, credentials: Credentials): UserPersonalData {
        return UserPersonalData(
            id = 0,
            firstName = storageEntity.firstName,
            lastName = storageEntity.lastName,
            doctorDegree = storageEntity.doctorDegree,
            academicDegree = academicDegreeMap[storageEntity.academicDegree] ?: AcademicDegree.NONE,
            organisations = mutableListOf(),
            orcid = storageEntity.orcid,
            researchId = storageEntity.researchId,
            about = storageEntity.about,
            favoriteGroup = favoriteGroupService.createDefault(),
            credentials = credentials
        )
    }

    fun setParams(
        updateInfo: UserPersonalDataInfo,
        organisations: List<Organisation>,
        userPersonalData: UserPersonalData
    ): UserPersonalData {
        userPersonalData.academicDegree = updateInfo.academicDegree
        userPersonalData.doctorDegree = updateInfo.doctorDegree
        userPersonalData.orcid = updateInfo.orcid
        userPersonalData.about = updateInfo.about
        userPersonalData.researchId = updateInfo.researchId
        userPersonalData.organisations = organisations.toMutableList()
        return userPersonalData
    }
}
