package com.example.projmagtool.services;

import com.example.projmagtool.domain.Backlog;
import com.example.projmagtool.domain.ProjectTask;
import com.example.projmagtool.exceptions.ProjectNotFoundException;
import com.example.projmagtool.repo.BacklogRepo;
import com.example.projmagtool.repo.ProjectRepo;
import com.example.projmagtool.repo.ProjectTaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepo backlogRepo;
    @Autowired
    private ProjectTaskRepo projectTaskRepo;
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username){
        Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier.toUpperCase(), username).getBacklog();
        projectTask.setBacklog(backlog);
        Integer backlogSeq = backlog.getPTSeq();
        backlogSeq++;

        backlog.setPTSeq(backlogSeq);
        projectTask.setProjectSeq(projectIdentifier+"-"+backlogSeq);
        projectTask.setProjectIdentifier(projectIdentifier);

        if(projectTask.getPriority() == null || projectTask.getPriority() == 0){
            projectTask.setPriority(3);
        }
        if(projectTask.getStatus() == null || projectTask.getStatus().equals("")){
            projectTask.setStatus("TODO");
        }
        return projectTaskRepo.save(projectTask);

    }

    public Iterable<ProjectTask> findBackLogById(String backlog_id, String username) {
        projectService.findProjectByIdentifier(backlog_id, username);
        return projectTaskRepo.findByProjectIdentifierOrderByPriority(backlog_id);
    }

    public ProjectTask findPtByProjectSeq(String backlog_id, String pt_id, String username){
//        if(backlogRepo.findByProjectIdentifier(backlog_id) == null){
//            throw new ProjectNotFoundException("Project with ID: '"+backlog_id+"' does not exist");
//        }
        projectService.findProjectByIdentifier(backlog_id, username);
        ProjectTask projectTask = projectTaskRepo.findByProjectSeq(pt_id);
        if(projectTask == null){
            throw new ProjectNotFoundException("Project Task: '"+pt_id+"' not found");
        }
        if (!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("Project Task: '"+pt_id+"' does not exist in project: '"+ backlog_id);
        }
        return projectTask;
    }

    public ProjectTask updateByProjectSeq(ProjectTask updatedTask, String backlog_id, String pt_id, String username){
        ProjectTask projectTask = findPtByProjectSeq(backlog_id, pt_id, username);
        projectTask = updatedTask;
        return projectTaskRepo.save(projectTask);
    }


    public void deletePTByProjectSequence(String backlog_id, String pt_id, String username) {
        ProjectTask projectTask = findPtByProjectSeq(backlog_id, pt_id, username);
        projectTaskRepo.delete(projectTask);

    }
}
