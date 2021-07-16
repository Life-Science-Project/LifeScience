package com.jetbrains.life_science.user.group.repository

import com.jetbrains.life_science.user.group.entity.FavoriteGroup
import com.jetbrains.life_science.user.organisation.entity.Organisation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FavoriteGroupRepository : JpaRepository<FavoriteGroup, Long> {
    fun existsByName(name: String): Boolean

    fun findByName(name: String): Organisation?
}
