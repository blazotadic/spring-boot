package com.academy.demo.controller;

import com.academy.demo.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/document")
public class DocumentController {

    private final DocumentService documentService;


    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestParam(value = "file") MultipartFile multipartFile)
    {
        // java.nio (Files, Paths)
        // multipartFile.getOriginalFilename(); //
        return null; // TODO: continue...
    }
}
