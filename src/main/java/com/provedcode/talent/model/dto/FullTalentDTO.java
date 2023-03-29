package com.provedcode.talent.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.provedcode.annotations.UrlList;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.URL;

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
        List<String> skills,
        @UrlList
        List<String> links,
        List<String> contacts,
        @UrlList
        @JsonProperty("attached_files")
        List<String> attachedFiles
) {
}
