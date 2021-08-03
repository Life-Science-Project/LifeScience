package com.jetbrains.life_science.controller.replica

import com.jetbrains.life_science.replicator.deserializer.ReplicatorDeserializerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/replica")
class ReplicaController(
    val replicatorDeserializerService: ReplicatorDeserializerService
) {

    @GetMapping
    fun performAction() {
        replicatorDeserializerService.replicateData()
    }
}
