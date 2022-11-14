package com.hva.ewa.team2.backend.rest;

import com.hva.ewa.team2.backend.domain.models.Project.Project;
import com.hva.ewa.team2.backend.domain.usecases.project.ProjectBusinessLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/projects")
public class ProjectController {


    private final ProjectBusinessLogic projectBusinessLogic;

    @Autowired
    public ProjectController(ProjectBusinessLogic projectBusinessLogic) {
        this.projectBusinessLogic = projectBusinessLogic;
    }

    @GetMapping()
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(new ArrayList<>()); // todo implement
    }

}
