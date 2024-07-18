package io.javabrains.tinderaibackend.matches;

import io.javabrains.tinderaibackend.profiles.Profile;

public record Match (String id, Profile profile, String conversationId) {
}
