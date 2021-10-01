package com.jetbrains.life_science.controller.protocol.draft.view

import com.jetbrains.life_science.container.protocol.entity.DraftProtocol
import com.jetbrains.life_science.controller.approach.view.ApproachViewMapper
import com.jetbrains.life_science.controller.section.view.SectionViewMapper
import com.jetbrains.life_science.controller.user.view.UserViewMapper
import com.jetbrains.life_science.user.data.entity.UserPersonalData
import org.springframework.stereotype.Component

@Component
class DraftProtocolViewMapper(
    val approachViewMapper: ApproachViewMapper,
    val sectionViewMapper: SectionViewMapper,
    val userViewMapper: UserViewMapper
) {
    fun toView(draftProtocol: DraftProtocol, usersData: List<UserPersonalData?>): DraftProtocolView {
        return DraftProtocolView(
            id = draftProtocol.id,
            name = draftProtocol.name,
            approach = approachViewMapper.toViewShort(draftProtocol.approach),
            sections = sectionViewMapper.toViewShortAll(draftProtocol.sections),
            participants = userViewMapper.toShortViewAll(usersData)
        )
    }
}
