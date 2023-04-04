package com.provedcode.talent.model.response;

import java.util.List;

public record ShortTalent(
        long id,
        String image,
        String firstName,
        String lastName,
        String specialization,
        List<String> shortDescription
) {
}
