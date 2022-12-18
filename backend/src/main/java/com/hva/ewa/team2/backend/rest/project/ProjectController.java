package com.hva.ewa.team2.backend.rest.project;

import com.fasterxml.jackson.databind.JsonNode;
import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.project.ProjectFilter;
import com.hva.ewa.team2.backend.domain.models.project.ProjectParticipant;
import com.hva.ewa.team2.backend.domain.models.project.ProjectReport;
import com.hva.ewa.team2.backend.domain.usecases.project.ProjectInteractor;
import com.hva.ewa.team2.backend.rest.project.request.ProjectEditVerificationRequest;
import com.hva.ewa.team2.backend.rest.project.request.ProjectInfoRequest;
import com.hva.ewa.team2.backend.rest.project.request.ProjectParticipantAddInfoRequest;
import com.hva.ewa.team2.backend.rest.project.request.ProjectTransferRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@ResponseBody
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectInteractor projectInteractor;

    @Autowired
    public ProjectController(ProjectInteractor projectInteractor) {
        this.projectInteractor = projectInteractor;
    }

    // General

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Project>> getAllProjects(@RequestParam("query") Optional<String> searchQuery,
                                                        @RequestParam(name = "filter") Optional<ProjectFilter> filter) {
        return ResponseEntity.ok(projectInteractor.getAllProjects(searchQuery, filter));
    }

    // Project CRUD

    @PostMapping(path = "/create")
    public ResponseEntity<Project> createProject(@RequestBody ProjectInfoRequest project) throws IOException {
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri();

        return ResponseEntity.created(uri).body(projectInteractor.createProject(project));
    }


    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Project> getProjectInformation(@PathVariable int id) {
        return ResponseEntity.ok(projectInteractor.getProjectInformation(id));
    }

    @PostMapping(path = "/{id}/archive")
    public ResponseEntity<Project> archiveProject(@PathVariable int id, @RequestBody ProjectEditVerificationRequest body) {
        return ResponseEntity.ok(projectInteractor.archiveProject(id, body, false));
    }

    @PostMapping(path = "/{id}/unarchive")
    public ResponseEntity<Project> unarchiveProject(@PathVariable int id, @RequestBody ProjectEditVerificationRequest body) {
        return ResponseEntity.ok(projectInteractor.archiveProject(id, body, true));
    }

    @PostMapping(path = "/{id}/transfer-ownership")
    public ResponseEntity<Project> transferOwnership(@PathVariable int id, @RequestBody ProjectTransferRequest body) {
        return ResponseEntity.ok(projectInteractor.transferOwnership(id, body));
    }


    @DeleteMapping(path = "/{id}/delete")
    public ResponseEntity<Boolean> deleteProject(@PathVariable int id) {
        return ResponseEntity.ok(projectInteractor.deleteProject(id));
    }

    @PutMapping(path = "/{id}/update")
    @ResponseBody
    public ResponseEntity<Project> updateProject(@PathVariable int id,
                                                 @ModelAttribute ProjectInfoRequest project) throws IOException {
        return ResponseEntity.ok(projectInteractor.updateProjectInformation(id, project));
    }

    // Participants

    @GetMapping(path = "/{id}/participants", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProjectParticipant>> getParticipants(@PathVariable int id) {
        return ResponseEntity.ok(projectInteractor.getProjectParticipants(id));
    }

    @PostMapping(path = "/{id}/participants/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectParticipant> addParticipant(@PathVariable int id, @RequestBody ProjectParticipantAddInfoRequest body) {
        return ResponseEntity.ok(projectInteractor.addProjectParticipant(id, body));
    }

    @GetMapping(path = "/{id}/participants/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectParticipant> getParticipant(@PathVariable int id, @PathVariable int userId) {
        return ResponseEntity.ok(projectInteractor.getProjectParticipant(id, userId));
    }

    @DeleteMapping(path = "/{id}/participants/{userId}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectParticipant> removeParticipant(@PathVariable int id, @PathVariable int userId) {
        return ResponseEntity.ok(projectInteractor.removeProjectParticipant(id, userId));
    }

    // Reports (summaries)

    @GetMapping(path = "/{id}/reports")
    public ResponseEntity<List<ProjectReport>> getReports(@PathVariable int id, @RequestParam("userId") int userId) {
        return ResponseEntity.ok(projectInteractor.getProjectReports(id, userId));
    }

}
