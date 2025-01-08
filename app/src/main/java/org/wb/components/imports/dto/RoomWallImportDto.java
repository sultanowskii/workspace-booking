package org.wb.components.imports.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomWallImportDto {
    @NotNull
    private double x1;

    @NotNull
    private double y1;

    @NotNull
    private double x2;

    @NotNull
    private double y2;
}
