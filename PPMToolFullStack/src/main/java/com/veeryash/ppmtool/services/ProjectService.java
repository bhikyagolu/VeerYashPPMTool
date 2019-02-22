package com.veeryash.ppmtool.services;

import com.veeryash.ppmtool.domain.Project;
import com.veeryash.ppmtool.exceptions.ProjectIdException;
import com.veeryash.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;


    public Project saveOrUpdate(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);

        } catch (Exception e) {
            throw new ProjectIdException("Porject ID "+project.getProjectIdentifier().toUpperCase() + " already exists.");
        }
    }

    public Project findProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Project ID "+projectId.toUpperCase() +" does not exists");
        }
        return project;
    }

    public Iterable<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    public void deleteProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Project ID "+projectId.toUpperCase() +" does not exists");
        }
        projectRepository.delete(project);
    }
}
