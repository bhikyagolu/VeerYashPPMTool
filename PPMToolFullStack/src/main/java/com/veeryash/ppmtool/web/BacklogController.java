package com.veeryash.ppmtool.web;

import com.veeryash.ppmtool.domain.Project;
import com.veeryash.ppmtool.domain.ProjectTask;
import com.veeryash.ppmtool.services.MapValidationErrorService;
import com.veeryash.ppmtool.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {
    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;
    @PostMapping("/{backlogId}")
    public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult result, @PathVariable String backlogId) {
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        ProjectTask projectTask1 = projectTaskService.addProjectTask(backlogId, projectTask);

        return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
    }

    @GetMapping("/{backlogId}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlogId) {
        return projectTaskService.findBacklogById(backlogId);
    }

    @GetMapping("/{backlogId}/{psId}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlogId, @PathVariable String psId) {
        ProjectTask projectTask = projectTaskService.findPTByProjectSequence(backlogId, psId);

        return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
    }

    @PatchMapping("/{backlogId}/{psId}")
    public ResponseEntity<?> updateProjectTask(@Valid ProjectTask updateProjectTask, BindingResult result, @PathVariable String backlogId, @PathVariable String psId) {

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if (errorMap != null) {
            return errorMap;
        }

        ProjectTask projectTask = projectTaskService.updateByProjectSequence(updateProjectTask, backlogId, psId);

        return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
    }

    @DeleteMapping("/{backlogId}/{psId}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlogId, @PathVariable String psId) {
        projectTaskService.deleteByProjectSequence(backlogId, psId);
        return new ResponseEntity<String>("Project task "+psId+ " was deleted successfully.", HttpStatus.OK);
    }
}
