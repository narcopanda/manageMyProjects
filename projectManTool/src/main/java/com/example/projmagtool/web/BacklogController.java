package com.example.projmagtool.web;

import com.example.projmagtool.domain.ProjectTask;
import com.example.projmagtool.services.MapValidateErrorService;
import com.example.projmagtool.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/backlog")
public class BacklogController {

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private MapValidateErrorService mapValidateErrorService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask projectTask,
                                                     BindingResult result, @PathVariable String backlog_id, Principal principal){
        ResponseEntity<?> errMap = mapValidateErrorService.MapValidateService(result);
        if(errMap != null) {
            return errMap;
        }
        return new ResponseEntity<ProjectTask>(projectTaskService.addProjectTask(backlog_id,projectTask, principal.getName()), HttpStatus.CREATED);
    }

    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id, Principal principal){
       return projectTaskService.findBackLogById(backlog_id, principal.getName());
    }

    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal){
        return new ResponseEntity<ProjectTask>(projectTaskService.findPtByProjectSeq(backlog_id.toUpperCase(),pt_id, principal.getName()), HttpStatus.OK);
    }

    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
                                               @PathVariable String backlog_id, @PathVariable String pt_id, Principal principal){
        ResponseEntity<?> errMap = mapValidateErrorService.MapValidateService(result);
        if(errMap != null) {
            return errMap;
        }
        return new ResponseEntity<ProjectTask>(projectTaskService.updateByProjectSeq(projectTask,backlog_id.toUpperCase(),pt_id, principal.getName()), HttpStatus.OK);
    }

    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id, Principal principal){
        projectTaskService.deletePTByProjectSequence(backlog_id, pt_id, principal.getName());

        return new ResponseEntity<String>("Project Task "+pt_id+" was deleted successfully", HttpStatus.OK);
    }
}
