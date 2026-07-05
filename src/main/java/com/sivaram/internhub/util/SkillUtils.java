package com.sivaram.internhub.util;

import java.util.Arrays;
import java.util.List;

public final class SkillUtils {

    private SkillUtils() {
    }

    public static List<String> fromText(String skillsText) {
        if (skillsText == null || skillsText.isBlank()) {
            return List.of();
        }
        return Arrays.stream(skillsText.split(","))
                .map(String::trim)
                .filter(skill -> !skill.isBlank())
                .distinct()
                .toList();
    }

    public static String toText(List<String> skills) {
        if (skills == null || skills.isEmpty()) {
            return "";
        }
        return String.join(", ", skills);
    }
}
