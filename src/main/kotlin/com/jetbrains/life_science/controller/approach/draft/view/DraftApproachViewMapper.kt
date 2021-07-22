package com.jetbrains.life_science.controller.approach.draft.view

import com.jetbrains.life_science.container.approach.entity.DraftApproach
import com.jetbrains.life_science.controller.category.view.CategoryViewMapper
import com.jetbrains.life_science.controller.section.view.SectionViewMapper
import com.jetbrains.life_science.controller.user.UserViewMapper
import com.jetbrains.life_science.user.data.entity.UserPersonalData
import org.springframework.stereotype.Component

@Component
class DraftApproachViewMapper(
    val sectionViewMapper: SectionViewMapper,
    val userViewMapper: UserViewMapper,
    val categoryViewMapper: CategoryViewMapper
) {

    fun toView(draftApproach: DraftApproach): DraftApproachView {
        val users = userViewMapper.toViewShortAll(extractUsers(draftApproach))
        val sections = sectionViewMapper.toViewShortAll(draftApproach.sections)
        val categories = categoryViewMapper.toViewsShort(draftApproach.categories)
        return DraftApproachView(
            id = draftApproach.id,
            name = draftApproach.name,
            sections = sections,
            participants = users,
            categories = categories
        )
    }

    fun extractUsers(draftApproach: DraftApproach): List<UserPersonalData> {
        return draftApproach.participants.mapNotNull { it.userPersonalData }
    }
}
