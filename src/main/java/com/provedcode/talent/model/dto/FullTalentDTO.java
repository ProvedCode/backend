package com.provedcode.talent.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.provedcode.util.annotations.UrlList;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record FullTalentDTO(
        Long id,
        @NotEmpty
        @JsonProperty("first_name")
        String firstName,
        @NotEmpty
        @JsonProperty("last_name")
        String lastName,
        String image,
        @NotEmpty
        String specialization,
        @JsonProperty("additional_info")
        String additionalInfo,
        String bio,
        List<String> talents,
        @UrlList
        List<String> links,
        List<String> contacts,
        @UrlList
        @JsonProperty("attached_files")
        List<String> attachedFiles
) {
}