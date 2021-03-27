package com.jetbrains.life_science.section

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/section")
class SectionController(
    val repository: SectionRepository
) {

    @GetMapping("/test")
    fun getTestData() {
        repository.save(Section(1, "sasha2"))
    }
}
