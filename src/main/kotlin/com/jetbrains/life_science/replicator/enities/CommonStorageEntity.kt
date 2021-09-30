package com.jetbrains.life_science.replicator.enities

data class CommonStorageEntity(
    val users: List<CredentialsStorageEntity>,
    val category: List<CategoryStorageEntity>,
    val publicApproaches: List<ApproachStorageEntity>,
    val draftApproaches: List<ApproachStorageEntity>,
    val draftProtocols: List<ProtocolStorageEntity>
)
