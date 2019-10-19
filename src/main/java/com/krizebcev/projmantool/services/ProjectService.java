package com.krizebcev.projmantool.services;

import com.krizebcev.projmantool.domain.Project;
import com.krizebcev.projmantool.exceptions.ProjectIdException;
import com.krizebcev.projmantool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception ex) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier() + "' already exists");
        }
    }

    public Project findProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Project ID '" + projectId.toUpperCase() + "' does not exist");
        }
        return project;
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Can not delete project with ID '" + projectId.toUpperCase() + "'. This project does not exist");
        }
        projectRepository.delete(project);
    }
}
