package org.wb.components.auth.dto;

import org.wb.components.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthResponseDto {
    private User user;
    private String token;
}
