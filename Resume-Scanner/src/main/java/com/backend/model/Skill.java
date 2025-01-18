package com.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = "technical_skills")
@NoArgsConstructor
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "skill_name", nullable = false)
    private String skillName;

    @ManyToMany(mappedBy = "skills")
    private List<Resume> resumes;

    public Skill(String skillName) {
        this.skillName = skillName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public List<Resume> getResumes() {
        return resumes;
    }

    public void setResumes(List<Resume> resumes) {
        this.resumes = resumes;
    }
}
