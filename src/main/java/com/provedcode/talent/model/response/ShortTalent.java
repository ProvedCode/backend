package com.provedcode.talent.model.response;

public record ShortTalent(
        long id,
        String image,
        String firstName,
        String lastName,
        String specialization,
        String shortDescription
) {
}
