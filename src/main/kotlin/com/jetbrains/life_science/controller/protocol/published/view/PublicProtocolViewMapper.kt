package com.jetbrains.life_science.controller.protocol.published.view

import com.jetbrains.life_science.container.protocol.entity.PublicProtocol
import com.jetbrains.life_science.controller.approach.view.ApproachViewMapper
import com.jetbrains.life_science.controller.section.view.SectionViewMapper
import com.jetbrains.life_science.controller.user.view.UserViewMapper
import com.jetbrains.life_science.user.data.entity.UserPersonalData
import org.springframework.stereotype.Component

@Component
class PublicProtocolViewMapper(
    val approachViewMapper: ApproachViewMapper,
    val sectionViewMapper: SectionViewMapper,
    val userViewMapper: UserViewMapper
) {
    fun toView(publicProtocol: PublicProtocol): PublicProtocolView {
        return PublicProtocolView(
            id = publicProtocol.id,
            name = publicProtocol.name,
            approach = approachViewMapper.toViewShort(publicProtocol.approach),
            sections = sectionViewMapper.toViewShortAll(publicProtocol.sections),
            coAuthors = userViewMapper.toShortViewAll(extractUsers(publicProtocol))
        )
    }

    fun extractUsers(publicProtocol: PublicProtocol): List<UserPersonalData> {
        return publicProtocol.coAuthors.mapNotNull { it.userPersonalData }
    }
}
