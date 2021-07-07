package com.jetbrains.life_science.user.group.view

import com.jetbrains.life_science.approach.view.PublicApproachCategoryViewMapper
import com.jetbrains.life_science.protocol.view.ProtocolView
import com.jetbrains.life_science.section.view.SectionView
import com.jetbrains.life_science.user.group.entity.FavoriteGroup
import org.springframework.stereotype.Component

@Component
class FavoriteGroupViewMapper(
    val approachCategoryViewMapper: PublicApproachCategoryViewMapper
) {
    // TODO:: разобраться с ProtocolViewMapper
    fun createView(favoriteGroup: FavoriteGroup): FavoriteGroupView {
        val subgroupsView = favoriteGroup.subGroups.map { SubgroupView(it.id, it.name) }
        val approachesView = favoriteGroup.approaches
            .map { approachCategoryViewMapper.createView(it) }
        val protocolsView = favoriteGroup.protocols.map { protocol ->
            ProtocolView(
                protocol.id, protocol.name, protocol.approach.id,
                protocol.sections.map { SectionView(it.id, it.name, null, it.order, it.visible) }
            )
        }
        return FavoriteGroupView(
            id = favoriteGroup.id,
            name = favoriteGroup.name,
            subgroups = subgroupsView,
            approaches = approachesView,
            protocols = protocolsView
        )
    }
}
