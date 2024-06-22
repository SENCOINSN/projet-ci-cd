package com.sid.gl.dto;

public record StudentResponse(
        String id,
        String firstName,
        String lastName,

        String code,
        String programId, String photo) {
}
