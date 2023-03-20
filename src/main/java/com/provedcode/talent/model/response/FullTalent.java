package com.provedcode.talent.model.response;

import java.util.List;

public record FullTalent(
        String image,
        String firstName,
        String lastName,
        String specialization,
        List<String> fullSkills,
        String bio,
        List<String> contacts,
        List<String> links,
        List<String> attachedFiles
) {
}
