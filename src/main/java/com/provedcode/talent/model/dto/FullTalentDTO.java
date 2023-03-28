package com.provedcode.talent.model.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record FullTalentDTO (
    Long id,
    String firstName,
    String lastName,
    String image,
    String specialization,
    String additionalInfo,
    String bio,
    List<String> skills,
    List<String> links,
    List<String> contacts,
    List<String> attachedFiles
) {}
