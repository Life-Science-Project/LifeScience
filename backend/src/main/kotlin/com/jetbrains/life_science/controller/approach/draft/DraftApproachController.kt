package com.jetbrains.life_science.controller.approach.draft

import com.jetbrains.life_science.approach.draft.service.DraftApproachService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/approaches/draft")
class DraftApproachController(
    val draftApproachService: DraftApproachService
) {

    @GetMapping("/{approachId}")
    fun getApproach(@PathVariable approachId: Long) {
        draftApproachService.
    }


}





