package com.example.projmagtool.services;

import com.example.projmagtool.domain.Backlog;
import com.example.projmagtool.domain.Project;
import com.example.projmagtool.domain.User;
import com.example.projmagtool.exceptions.ProjectIdExcep;
import com.example.projmagtool.exceptions.ProjectNotFoundException;
import com.example.projmagtool.repo.BacklogRepo;
import com.example.projmagtool.repo.ProjectRepo;
import com.example.projmagtool.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private BacklogRepo backlogRepo;
    @Autowired
    private UserRepo userRepo;

   public Project saveOrUpdateProject(Project project, String username){
       //logic

       if(project.getId() != null){
           Project existingProject = projectRepo.findByProjectIdentifier(project.getProjectIdentifier());
           if(existingProject !=null &&(!existingProject.getProjectLeader().equals(username))){
               throw new ProjectNotFoundException("Project not found in your account");
           }else if(existingProject == null){
               throw new ProjectNotFoundException("Project with ID: '"+project.getProjectIdentifier()+"' cannot be updated because it doesn't exist");
           }
       }
       try{
           User user = userRepo.findByUsername(username);
           project.setUser(user);
           project.setProjectLeader(user.getUsername());
           project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

           if(project.getId() == null){
               var backlog = new Backlog();
               project.setBacklog(backlog);
               backlog.setProject(project);
               backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
           }
           if(project.getId() != null){
               project.setBacklog(backlogRepo.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
           }
           return projectRepo.save(project);
       }catch (Exception e){
           // this wont work unless i make my own crud repo
//           if(!project.getUser().getUsername().equals(projectRepo.findByProjectIdentifier(project.getProjectIdentifier()).getUser().getUsername())){
//               System.out.println("-------------------------------------------------");
//               System.out.println("new project: "+ project.getUser().getUsername());
//               System.out.println("old project: " + projectRepo.findByProjectIdentifier(project.getProjectIdentifier()).getUser().getUsername());
//               return projectRepo.save(project);
//           }
          throw new ProjectIdExcep("Project Id '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
       }
   }

   public Project findProjectByIdentifier(String projectId, String username){
       var project = projectRepo.findByProjectIdentifier(projectId.toUpperCase());
       if(project == null){
           throw new ProjectIdExcep("Project Id '"+projectId+"' does not exists");
       }
       if(project.getProjectLeader() == null || !project.getProjectLeader().equals(username)){
           throw new ProjectNotFoundException("Project not found in your account");
       }
       return project;
   }

   public Iterable<Project> findAllProject(String username){
       return projectRepo.findAllByProjectLeader(username);
   }

   public void deleteProjectByIdentifier(String projectId, String username){
       projectRepo.delete(findProjectByIdentifier(projectId.toUpperCase(), username));
   }
}
