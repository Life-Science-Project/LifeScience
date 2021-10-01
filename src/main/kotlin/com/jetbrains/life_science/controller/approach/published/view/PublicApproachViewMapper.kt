package com.jetbrains.life_science.controller.approach.published.view

import com.jetbrains.life_science.container.approach.entity.PublicApproach
import com.jetbrains.life_science.controller.category.view.CategoryViewMapper
import com.jetbrains.life_science.controller.section.view.SectionViewMapper
import com.jetbrains.life_science.controller.user.view.UserViewMapper
import com.jetbrains.life_science.controller.protocol.view.ProtocolViewMapper
import com.jetbrains.life_science.user.data.entity.UserPersonalData
import org.springframework.stereotype.Component

@Component
class PublicApproachViewMapper(
    val sectionViewMapper: SectionViewMapper,
    val userViewMapper: UserViewMapper,
    val categoryViewMapper: CategoryViewMapper,
    val protocolViewMapper: ProtocolViewMapper
) {

    fun toView(publicApproach: PublicApproach): PublicApproachView {
        val users = userViewMapper.toShortViewAll(extractUsers(publicApproach))
        val sections = sectionViewMapper.toViewShortAll(publicApproach.sections)
        val categories = categoryViewMapper.toViewsShort(publicApproach.categories)
        val protocols = protocolViewMapper.toViewsShort(publicApproach.protocols)
        return PublicApproachView(
            id = publicApproach.id,
            name = publicApproach.name,
            sections = sections,
            coAuthors = users,
            categories = categories,
            protocols = protocols
        )
    }

    fun extractUsers(publicApproach: PublicApproach): List<UserPersonalData> {
        return publicApproach.coAuthors.mapNotNull { it.userPersonalData }
    }
}
