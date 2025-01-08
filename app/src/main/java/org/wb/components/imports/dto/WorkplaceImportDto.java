package org.wb.components.imports.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkplaceImportDto {
    @NotNull
    private long numberOfMonitors;

    @NotNull
    private double x;

    @NotNull
    private double y;

    @NotNull
    private double width;

    @NotNull
    private double height;
}
