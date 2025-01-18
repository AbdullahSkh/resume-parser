package com.backend.service;

import com.backend.dto.ResumeDTO;
import com.backend.model.Resume;
import com.backend.model.Skill;
import com.backend.repository.ResumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    public ResumeDTO getResumeById(Long id) {
        Resume resume = resumeRepository.findById(id).orElse(null);
        if (resume != null) {
            List<String> skillNames = new ArrayList<>();
            for (Skill skill : resume.getSkills()) {
                skillNames.add(skill.getSkillName());
            }
            return new ResumeDTO();
        }
        return null;
    }

    public List<ResumeDTO> getResumesBySkill(String skill) {
        List<Resume> resumes = resumeRepository.findBySkills_SkillNameContainingIgnoreCase(skill); // Assuming a method like this in your repository
        List<ResumeDTO> resumeDTOList = new ArrayList<>();

        for (Resume resume : resumes) {
            List<String> skillNames = new ArrayList<>();
            for (Skill s : resume.getSkills()) {
                skillNames.add(s.getSkillName());
            }
            resumeDTOList.add(new ResumeDTO());
        }
        return resumeDTOList;
    }
}
