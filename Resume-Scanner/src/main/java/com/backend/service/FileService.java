package com.backend.service;

import com.backend.dto.ResumeDTO;
import com.backend.model.Resume;
import com.backend.model.Skill;
import com.backend.model.SoftSkills;
import com.backend.repository.ResumeRepository;
import com.backend.repository.SkillRepository;
import com.backend.repository.SoftSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FileService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private SoftSkillRepository softSkillRepository;

    public Long storeFileData(ResumeDTO resumeDTO) {
        try {
            Resume resume = new Resume();
            resume.setCandidateName(resumeDTO.getFullName());
            resume.setEmail(resumeDTO.getEmail());
            resume.setGithub(resumeDTO.getGithub());
            resume.setLinkedIn(resumeDTO.getLinkedIn());
            resume.setEmploymentDetails(resumeDTO.getEmploymentDetails());
            resume.setExperience(resumeDTO.getExperience());


            List<Skill> technicalSkills = resumeDTO.getSkills().stream()
                    .map(skillName -> skillRepository.findBySkillName(skillName)
                            .orElseGet(() -> skillRepository.save(new Skill(skillName))))
                    .collect(Collectors.toList());

            List<SoftSkills> softSkillEntities = resumeDTO.getSoftSkills().stream()
                    .map(skillName -> softSkillRepository.findBySkillName(skillName)
                            .orElseGet(() -> softSkillRepository.save(new SoftSkills(skillName))))
                    .collect(Collectors.toList());

            resume.setSkills(technicalSkills);
            resume.setSoftSkills(softSkillEntities);

            Resume savedResume = resumeRepository.save(resume);
            return savedResume.getId();

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload to database", e);
        }
    }
}
