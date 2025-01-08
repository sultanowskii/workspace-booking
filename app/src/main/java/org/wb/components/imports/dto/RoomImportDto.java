package org.wb.components.imports.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomImportDto {
    @NotNull
    private long officeId;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private List<RoomWallImportDto> walls;

    @NotNull
    private List<WorkplaceImportDto> workplaces;

    @NotNull
    private List<MeetingRoomImportDto> meetingRooms;
}
