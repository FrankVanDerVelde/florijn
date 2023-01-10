package com.hva.ewa.team2.backend.rest.project;

import com.hva.ewa.team2.backend.domain.models.project.Project;
import com.hva.ewa.team2.backend.domain.models.project.ProjectParticipant;
import com.hva.ewa.team2.backend.domain.models.project.ProjectReport;
import com.hva.ewa.team2.backend.domain.usecases.project.ProjectInteractor;
import com.hva.ewa.team2.backend.rest.project.request.ProjectEditVerificationRequest;
import com.hva.ewa.team2.backend.rest.project.request.ProjectInfoRequest;
import com.hva.ewa.team2.backend.rest.project.request.ProjectParticipantAddInfoRequest;
import com.hva.ewa.team2.backend.rest.project.request.ProjectTransferRequest;
import com.hva.ewa.team2.backend.security.JWToken;
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
    public ResponseEntity<List<Project>> getAllProjects(@RequestParam(name = "query", required = false) Optional<String> searchQuery,
                                                        @RequestParam(name = "filter", required = false) Optional<String> filter,
                                                        @RequestAttribute(JWToken.JWT_ATTRIBUTE_NAME) JWToken jwtToken) {
        return ResponseEntity.ok(projectInteractor.getAllProjects(searchQuery, filter, jwtToken.getUserId()));
    }

    // Project CRUD

    @PostMapping(path = "/create")
    public ResponseEntity<Project> createProject(@ModelAttribute ProjectInfoRequest project, @RequestAttribute(JWToken.JWT_ATTRIBUTE_NAME) JWToken jwtToken) throws IOException {
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri();

        return ResponseEntity.created(uri).body(projectInteractor.createProject(project, jwtToken.getUserId()));
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Project> getProjectInformation(@PathVariable int id, @RequestAttribute(JWToken.JWT_ATTRIBUTE_NAME) JWToken jwtToken) {
        return ResponseEntity.ok(projectInteractor.getProjectInformation(id, jwtToken.getUserId()));
    }

    @PostMapping(path = "/{id}/archive")
    public ResponseEntity<Project> archiveProject(@PathVariable int id,
                                                  @RequestBody ProjectEditVerificationRequest body,
                                                  @RequestAttribute(JWToken.JWT_ATTRIBUTE_NAME) JWToken jwtToken) {
        return ResponseEntity.ok(projectInteractor.archiveProject(id, body, false, jwtToken.getUserId()));
    }

    @PostMapping(path = "/{id}/unarchive")
    public ResponseEntity<Project> unarchiveProject(@PathVariable int id,
                                                    @RequestBody ProjectEditVerificationRequest body,
                                                    @RequestAttribute(JWToken.JWT_ATTRIBUTE_NAME) JWToken jwtToken) {
        return ResponseEntity.ok(projectInteractor.archiveProject(id, body, true, jwtToken.getUserId()));
    }

    @PostMapping(path = "/{id}/transfer-ownership")
    public ResponseEntity<Project> transferOwnership(@PathVariable int id,
                                                     @RequestBody ProjectTransferRequest body,
                                                     @RequestAttribute(JWToken.JWT_ATTRIBUTE_NAME) JWToken jwtToken) {
        return ResponseEntity.ok(projectInteractor.transferOwnership(id, body, jwtToken.getUserId()));
    }


    @DeleteMapping(path = "/{id}/delete")
    public ResponseEntity<Boolean> deleteProject(@PathVariable int id, @RequestAttribute(JWToken.JWT_ATTRIBUTE_NAME) JWToken jwtToken) {
        return ResponseEntity.ok(projectInteractor.deleteProject(id, jwtToken.getUserId()));
    }

    @PutMapping(path = "/{id}/update")
    @ResponseBody
    public ResponseEntity<Project> updateProject(@PathVariable int id,
                                                 @ModelAttribute ProjectInfoRequest project,
                                                 @RequestAttribute(JWToken.JWT_ATTRIBUTE_NAME) JWToken jwtToken) throws IOException {
        return ResponseEntity.ok(projectInteractor.updateProjectInformation(id, project, jwtToken.getUserId()));
    }

    // Participants

    @GetMapping(path = "/{id}/participants", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProjectParticipant>> getParticipants(@PathVariable int id, @RequestAttribute(JWToken.JWT_ATTRIBUTE_NAME) JWToken jwtToken) {
        return ResponseEntity.ok(projectInteractor.getProjectParticipants(id, jwtToken.getUserId()));
    }

    @PostMapping(path = "/{id}/participants/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectParticipant> addParticipant(@PathVariable int id,
                                                             @RequestBody ProjectParticipantAddInfoRequest body,
                                                             @RequestAttribute(JWToken.JWT_ATTRIBUTE_NAME) JWToken jwtToken) {
        return ResponseEntity.ok(projectInteractor.addProjectParticipant(id, jwtToken.getUserId(), body));
    }

    @GetMapping(path = "/{id}/participants/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectParticipant> getParticipant(@PathVariable int id,
                                                             @PathVariable int userId,
                                                             @RequestAttribute(JWToken.JWT_ATTRIBUTE_NAME) JWToken jwtToken) {
        return ResponseEntity.ok(projectInteractor.getProjectParticipant(id, jwtToken.getUserId(), userId));
    }

    @DeleteMapping(path = "/{id}/participants/{userId}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectParticipant> removeParticipant(@PathVariable int id,
                                                                @PathVariable int userId,
                                                                @RequestAttribute(JWToken.JWT_ATTRIBUTE_NAME) JWToken jwtToken) {
        return ResponseEntity.ok(projectInteractor.removeProjectParticipant(id, jwtToken.getUserId(), userId));
    }

    // Reports (summaries)

    @GetMapping(path = "/{id}/reports")
    public ResponseEntity<List<ProjectReport>> getReports(@PathVariable int id, @RequestAttribute(JWToken.JWT_ATTRIBUTE_NAME) JWToken jwtToken) {
        return ResponseEntity.ok(projectInteractor.getProjectReports(id, jwtToken.getUserId()));
    }

    @GetMapping(path = "/total")
    public ResponseEntity<Integer> getTotalProjects(@RequestAttribute(JWToken.JWT_ATTRIBUTE_NAME) JWToken jwtToken) {
        return ResponseEntity.ok(projectInteractor.getProjectCount(jwtToken.getUserId()));
    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<List<Project>> getProjectsByUser(@PathVariable int id, @RequestAttribute(JWToken.JWT_ATTRIBUTE_NAME) JWToken jwtToken) {
        return ResponseEntity.ok(projectInteractor.getProjectsByUser(id, jwtToken.getUserId()));
    }

    @GetMapping(path = "/earnings")
    public ResponseEntity<Double> getEarnings(@RequestAttribute(JWToken.JWT_ATTRIBUTE_NAME) JWToken jwtToken) {
        return ResponseEntity.ok(projectInteractor.getEarnings(jwtToken.getUserId()));
    }

    @GetMapping(path = "/hours")
    public ResponseEntity<Double> getHours(@RequestAttribute(JWToken.JWT_ATTRIBUTE_NAME) JWToken jwtToken) {
        return ResponseEntity.ok(projectInteractor.getHours(jwtToken.getUserId()));
    }

}
