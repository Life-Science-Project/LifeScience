package com.jetbrains.life_science.controller.approach.published

import com.jetbrains.life_science.container.approach.service.PublicApproachService
import com.jetbrains.life_science.category.service.CategoryService
import com.jetbrains.life_science.controller.approach.published.view.PublicApproachView
import com.jetbrains.life_science.controller.approach.published.view.PublicApproachViewMapper
import com.jetbrains.life_science.user.credentials.service.CredentialsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/approaches/public")
class PublicApproachController(
    val publicApproachService: PublicApproachService,
    val categoryService: CategoryService,
    val credentialsService: CredentialsService,
    val viewMapper: PublicApproachViewMapper
) {

    @GetMapping("/{approachId}")
    fun getApproach(@PathVariable approachId: Long): PublicApproachView {
        val approach = publicApproachService.get(approachId)
        return viewMapper.toView(approach)
    }
}
