package io.javabrains.tinderaibackend.conversations;

import io.javabrains.tinderaibackend.profiles.Profile;

import java.util.List;

public record Conversation(
        String id,
        String profileId,
        List<ChatMessage> messages
) {
}
