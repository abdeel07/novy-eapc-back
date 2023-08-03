package io.novelis.novyeapc.repositories;

import io.novelis.novyeapc.entities.Collaborator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator,Long> {

    Page<Collaborator> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable);
}
