package com.jetbrains.life_science.replicator.deserializer.favoriteGroup

import com.jetbrains.life_science.user.group.repository.FavoriteGroupRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Component
class FavoriteGroupReplicator(
    private val favoriteGroupRepository: FavoriteGroupRepository,
    private val entityManager: EntityManager
) {

    @Transactional
    fun deleteAll() {
        entityManager.createNativeQuery("alter sequence favorite_group_seq restart with 1;")
            .executeUpdate()
        favoriteGroupRepository.deleteAll()
    }
}
