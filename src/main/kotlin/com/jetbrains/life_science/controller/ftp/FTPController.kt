package com.jetbrains.life_science.controller.ftp

import com.jetbrains.life_science.ftp.service.FTPService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayOutputStream

@RestController
@RequestMapping("/api/ftp")
class FTPController(
    val service: FTPService
) {
    @GetMapping(
        value = ["/{path}"],
        produces = [MediaType.IMAGE_JPEG_VALUE]
    )
    fun getFile(@PathVariable path: String): ByteArray {
        val output = ByteArrayOutputStream()
        service.getFile(path, output)
        return output.toByteArray()
    }

    @PostMapping(value = ["/{path}"])
    fun saveFile(@PathVariable path: String, @RequestParam("file") file: MultipartFile) {
        service.saveFile(path, file.inputStream)
    }
}
