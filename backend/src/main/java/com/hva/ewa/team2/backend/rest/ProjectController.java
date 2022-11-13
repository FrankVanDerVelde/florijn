package com.hva.ewa.team2.backend.rest;

import com.hva.ewa.team2.backend.domain.models.Project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/projects")
public class ProjectController {


    @Autowired


    @GetMapping()
    public ResponseEntity<List<Project>> getAllProjects() {

    }

}
