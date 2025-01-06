package org.wb.components.meetingroom.visual;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRoomVisualRepository extends JpaRepository<MeetingRoomVisual, Long> {

}
