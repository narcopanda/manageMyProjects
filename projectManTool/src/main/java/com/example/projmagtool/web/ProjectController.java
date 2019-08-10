package com.example.projmagtool.web;

import com.example.projmagtool.domain.Project;
import com.example.projmagtool.services.MapValidateErrorService;
import com.example.projmagtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MapValidateErrorService mapValidateErrorService;

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result, Principal principal){
        ResponseEntity<?> errMap = mapValidateErrorService.MapValidateService(result);
        if(errMap != null){
            return errMap;
        }
//        projectService.saveOrUpdateProject(project);
        return new ResponseEntity<Project>(projectService.saveOrUpdateProject(project, principal.getName()), HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}") // mapping a string var need to match
    public ResponseEntity<?> getProjectById(@PathVariable String projectId, Principal principal){
        var project = projectService.findProjectByIdentifier(projectId, principal.getName());
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }


    @GetMapping("/all")
    public Iterable<Project> getAllProjects(Principal principal){
        return projectService.findAllProject(principal.getName());
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId, Principal principal){
        projectService.deleteProjectByIdentifier(projectId, principal.getName());
        return new ResponseEntity<String>("Project with ID: '"+projectId+"' was deleted", HttpStatus.OK);
    }
}
