package com.hva.ewa.team2.backend.rest.project;

import com.fasterxml.jackson.databind.JsonNode;
import com.hva.ewa.team2.backend.data.hourregistration.HourRegistrationRepository;
import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.project.ProjectParticipant;
import com.hva.ewa.team2.backend.domain.models.project.ProjectReport;
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
import java.util.Optional;

@RestController
@ResponseBody
@RequestMapping("/projects")
public class ProjectController {


    private final ProjectBusinessLogic projectBusinessLogic;
    private final HourRegistrationRepository hourRegistrationRepo;

    @Autowired
    public ProjectController(ProjectBusinessLogic projectBusinessLogic, HourRegistrationRepository hourRegistrationRepo) {
        this.projectBusinessLogic = projectBusinessLogic;
        this.hourRegistrationRepo = hourRegistrationRepo;
    }

    // General

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Project>> getAllProjects(@RequestParam("query") Optional<String> searchQuery) {
        return ResponseEntity.ok(projectBusinessLogic.getAllProjects(searchQuery));
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

    // Reports (summaries)

    @PostMapping(path = "/{id}/reports")
    public ResponseEntity<List<ProjectReport>> getReports(@PathVariable int id, @RequestBody JsonNode body) {
        return ResponseEntity.ok(projectBusinessLogic.getProjectReports(id, body));
    }

}
