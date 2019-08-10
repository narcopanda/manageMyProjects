package com.example.projmagtool.repo;

import com.example.projmagtool.domain.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepo extends CrudRepository<Project, Long> {

    Project findByProjectIdentifier(String projectId);
    Iterable<Project> findAllByProjectLeader(String username);

    @Override
    Iterable<Project> findAll();
}
