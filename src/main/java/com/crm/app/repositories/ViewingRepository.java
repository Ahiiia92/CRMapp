package com.crm.app.repositories;

import com.crm.app.models.Viewing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("viewingRepository")
public interface ViewingRepository extends JpaRepository<Viewing, Integer> {
}
