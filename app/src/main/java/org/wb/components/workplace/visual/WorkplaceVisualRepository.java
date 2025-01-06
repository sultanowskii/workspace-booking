package org.wb.components.workplace.visual;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkplaceVisualRepository extends JpaRepository<WorkplaceVisual, Long> {

}
