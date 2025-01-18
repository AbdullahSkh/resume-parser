package com.backend.repository;

import com.backend.model.SoftSkills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SoftSkillRepository extends JpaRepository<SoftSkills, Long> {
    Optional<SoftSkills> findBySkillName(String skillName);
}
