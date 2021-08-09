package com.jetbrains.life_science.controller.ftp

import com.jetbrains.life_science.ftp.service.FTPService
import com.jetbrains.life_science.user.credentials.entity.Credentials
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayOutputStream
import javax.validation.constraints.NotBlank

@RestController
@RequestMapping("/api/ftp")
class FTPController(
    val service: FTPService
) {
    @GetMapping(
        value = ["/{fileName}"],
        produces = [MediaType.ALL_VALUE]
    )
    fun getFile(
        @PathVariable fileName: String,
        @AuthenticationPrincipal credentials: Credentials
    ): ByteArray {
        val output = ByteArrayOutputStream()
        service.getFile(fileName, output)
        return output.toByteArray()
    }

    @PostMapping(value = ["/{fileName}"])
    @Validated
    fun saveFile(
        @PathVariable @NotBlank fileName: String,
        @RequestParam("file") file: MultipartFile,
        @AuthenticationPrincipal credentials: Credentials
    ) {
        service.saveFile(fileName, file.inputStream)
    }
}