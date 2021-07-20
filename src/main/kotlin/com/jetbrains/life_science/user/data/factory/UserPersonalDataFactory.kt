package com.jetbrains.life_science.user.data.factory

import com.jetbrains.life_science.user.credentials.entity.Credentials
import com.jetbrains.life_science.user.data.entity.UserPersonalData
import com.jetbrains.life_science.user.data.service.UserPersonalDataInfo
import com.jetbrains.life_science.user.credentials.service.NewUserInfo
import com.jetbrains.life_science.user.organisation.entity.Organisation
import org.springframework.stereotype.Component

@Component
class UserPersonalDataFactory {

    fun create(info: NewUserInfo, credentials: Credentials): UserPersonalData {
        return UserPersonalData(
            id = 0,
            firstName = info.firstName,
            lastName = info.lastName,
            organisations = mutableListOf(),
            credentials = credentials
        )
    }

    fun setParams(updateInfo: UserPersonalDataInfo, organisations: List<Organisation>, userPersonalData: UserPersonalData): UserPersonalData {
        userPersonalData.academicDegree = updateInfo.academicDegree
        userPersonalData.doctorDegree = updateInfo.doctorDegree
        userPersonalData.orcid = updateInfo.orcid
        userPersonalData.researchId = updateInfo.researchId
        userPersonalData.organisations = organisations.toMutableList()
        return userPersonalData
    }
}
