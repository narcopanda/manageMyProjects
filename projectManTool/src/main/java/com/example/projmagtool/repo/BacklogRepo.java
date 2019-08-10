package com.example.projmagtool.repo;

import com.example.projmagtool.domain.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepo extends CrudRepository<Backlog, Long> {

    Backlog findByProjectIdentifier(String identifier);
}
