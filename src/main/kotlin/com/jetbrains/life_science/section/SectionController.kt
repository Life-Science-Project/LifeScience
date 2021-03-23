package com.jetbrains.life_science.section

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/section")
class SectionController {

    @GetMapping("/test")
    fun getTestData() = "test"


}