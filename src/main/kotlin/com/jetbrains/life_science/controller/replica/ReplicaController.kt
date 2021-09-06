package com.jetbrains.life_science.controller.replica

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jetbrains.life_science.replicator.deserializer.ReplicatorService
import com.jetbrains.life_science.replicator.serializer.ReplicatorStorageMapper
import io.swagger.v3.oas.annotations.Operation
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/api/replica")
class ReplicaController(
    val replicatorDeserializerService: ReplicatorService,
    val replicatorSerializer: ReplicatorStorageMapper
) {

    private val objectMapper = jacksonObjectMapper()

    @Operation(summary = "Warning: only for data replication. Don't use without discussion with backend.")
    @PostMapping("/replicate")
    fun performAction() {
        replicatorDeserializerService.replicateData()
    }

    @Operation(summary = "Returns all data from the database in JSON format.")
    @GetMapping("/dump")
    fun getDump(): String {
        val result = replicatorSerializer.serialize()
        return objectMapper.writeValueAsString(result)
    }
}
