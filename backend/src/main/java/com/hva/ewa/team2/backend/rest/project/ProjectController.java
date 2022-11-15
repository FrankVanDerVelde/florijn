package com.hva.ewa.team2.backend.rest.project;

import com.hva.ewa.team2.backend.domain.models.Project.Project;
import com.hva.ewa.team2.backend.domain.models.Project.ProjectParticipant;
import com.hva.ewa.team2.backend.domain.usecases.project.ProjectBusinessLogic;
import com.hva.ewa.team2.backend.rest.project.json.JsonProjectInfo;
import com.hva.ewa.team2.backend.rest.project.json.JsonProjectParticipantAddInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    // General

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectBusinessLogic.getAllProjects()); // todo implement
    }

    // Project CRUD

    @PostMapping(path = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Project> createProject(@RequestBody JsonProjectInfo project) {
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri();

        return ResponseEntity.created(uri).body(projectBusinessLogic.createProject(project));
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Project> getProjectInformation(@PathVariable int id) {
        return ResponseEntity.ok(projectBusinessLogic.getProjectInformation(id));
    }

    @DeleteMapping(path = "/{id}/delete")
    public ResponseEntity<Boolean> deleteProject(@PathVariable int id) {
        return ResponseEntity.ok(projectBusinessLogic.deleteProject(id));
    }

    @PutMapping(path = "/{id}/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Project> updateProject(@PathVariable int id, @RequestBody JsonProjectInfo body) {
        return ResponseEntity.ok(projectBusinessLogic.updateProjectInformation(id, body));
    }

    // Participants

    @GetMapping(path = "/{id}/participants", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProjectParticipant>> getParticipants(@PathVariable int id) {
        return ResponseEntity.ok(projectBusinessLogic.getProjectParticipants(id));
    }

    @PostMapping(path = "/{id}/participants/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectParticipant> addParticipant(@PathVariable int id, @RequestBody JsonProjectParticipantAddInfo body) {
        return ResponseEntity.ok(projectBusinessLogic.addProjectParticipant(id, body));
    }

    @GetMapping(path = "/{id}/participants/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectParticipant> getParticipant(@PathVariable int id, @PathVariable int userId) {
        return ResponseEntity.ok(projectBusinessLogic.getProjectParticipant(id, userId));
    }

    @DeleteMapping(path = "/{id}/participants/{userId}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectParticipant> removeParticipant(@PathVariable int id, @PathVariable int userId) {
        return ResponseEntity.ok(projectBusinessLogic.removeProjectParticipant(id, userId));
    }

}
