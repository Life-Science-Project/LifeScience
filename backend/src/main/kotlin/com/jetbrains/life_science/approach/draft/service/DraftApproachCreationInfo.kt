package com.jetbrains.life_science.approach.draft.service

import com.jetbrains.life_science.category.entity.Category
import com.jetbrains.life_science.user.credentials.entity.Credentials

interface DraftApproachCreationInfo {

    val name: String

    val parentCategory: Category

    val creator: Credentials
}
