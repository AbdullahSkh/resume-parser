package com.backend.repository;

import com.backend.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    List<Resume> findBySkills_SkillNameContainingIgnoreCase(String skill);

}
