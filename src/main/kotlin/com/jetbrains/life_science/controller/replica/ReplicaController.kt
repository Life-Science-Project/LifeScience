package com.jetbrains.life_science.controller.replica

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jetbrains.life_science.replicator.deserializer.ReplicatorService
import com.jetbrains.life_science.replicator.serializer.ReplicatorStorageMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/replica")
class ReplicaController(
    val replicatorDeserializerService: ReplicatorService,
    val replicatorSerializer: ReplicatorStorageMapper
) {

    private val objectMapper = jacksonObjectMapper()

    @GetMapping("/replicate")
    fun performAction() {
        replicatorDeserializerService.replicateData()
    }

    @GetMapping("/dump")
    fun getDump(): String {
        val result = replicatorSerializer.serialize()
        return objectMapper.writeValueAsString(result)
    }
}
