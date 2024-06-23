package com.sid.gl.dto;

import lombok.Builder;

@Builder
public record StudentResponse(
        String id,
        String firstName,
        String lastName,

        String code,
        String programId, String photo) {
}
