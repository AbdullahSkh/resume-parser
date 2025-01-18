package com.backend.controller;

import com.backend.dto.ResumeDTO;
import com.backend.service.FileService;
import com.backend.service.SearchResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.io.File;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("api/files")
public class ResumeController {

    @Autowired
    private SearchResumeService searchResumeService;

    @Autowired
    private FileService fileService;

    @PostMapping(value = "/upload", consumes ="application/json")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> uploadFile(@RequestBody ResumeDTO resumeDTO){

        try {
            fileService.storeFileData(resumeDTO);
            return ResponseEntity.ok("Resume processed and stored successfully.");
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the resume: " + ex.getMessage());
        }

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResumeDTO> getResume(@PathVariable Long id) {
        ResumeDTO resumeDTO = searchResumeService.getResumeById(id);
        if (resumeDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resumeDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ResumeDTO>> searchResumesBySkill(@RequestParam("skill") String skill) {
        List<ResumeDTO> resumes = searchResumeService.getResumesBySkill(skill);
        if (resumes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resumes);
    }

    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        file.transferTo(convFile);
        return convFile;
    }
}
