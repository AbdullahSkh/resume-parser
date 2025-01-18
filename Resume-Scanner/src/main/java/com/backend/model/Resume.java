package com.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "resume_details")
@AllArgsConstructor
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String candidateName;

    @Column(nullable = false)
    private String email;

    @Column(name = "github")
    private String github;

    @Column(name = "linkedin")
    private String linkedIn;

    @Column(name = "employment_details", columnDefinition = "TEXT")
    private String employmentDetails;

    @ManyToMany
    @JoinTable(
            name = "soft_skills",
            joinColumns = @JoinColumn(name = "resume_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<SoftSkills> softSkills;

    @ManyToMany
    @JoinTable(
            name = "technical_skills",
            joinColumns = @JoinColumn(name = "resume_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills;

    @Column(name = "experience_years")
    private int experience;

    @Column
    private LocalDateTime uploadTime;

    public Resume() {
        this.uploadTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        if (this.skills != null) {
            this.skills.clear();
            this.skills.addAll(skills);
        } else {
            this.skills = skills;
        }
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getEmploymentDetails() {
        return employmentDetails;
    }

    public void setEmploymentDetails(String employmentDetails) {
        this.employmentDetails = employmentDetails;
    }

    public List<SoftSkills> getSoftSkills() {
        return softSkills;
    }

    public void setSoftSkills(List<SoftSkills> softSkills) {
        this.softSkills = softSkills;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }
}
