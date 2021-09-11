package com.jetbrains.life_science.replicator.enities

data class CredentialsStorageEntity(
    val id: Long,
    val email: String,
    val password: String,
    val role: List<String>,
    val userData: UserPersonalDataStorageEntity?
)

data class UserPersonalDataStorageEntity(
    val firstName: String,
    val lastName: String,
    val doctorDegree: Boolean,
    val about: String?,
    val academicDegree: Int,
    val orcid: String?,
    val researchId: String?
)
