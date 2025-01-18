package com.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ResumeDTO {
    private String fullName;
    private String email;
    private String github;
    private String linkedIn;
    private String employmentDetails;
    private Integer experience;
    private List<String> skills;
    private List<String> softSkills;

    private String fileAddr;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getSoftSkills() {
        return softSkills;
    }

    public void setSoftSkills(List<String> softSkills) {
        this.softSkills = softSkills;
    }

    @Override
    public String toString() {
        return "ResumeDTO{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", github='" + github + '\'' +
                ", linkedIn='" + linkedIn + '\'' +
                ", employmentDetails='" + employmentDetails + '\'' +
                ", experience=" + experience +
                ", skills=" + skills +
                ", softSkills=" + softSkills +
                '}';
    }
}
