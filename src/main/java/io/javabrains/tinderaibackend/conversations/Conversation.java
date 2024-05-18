package io.javabrains.tinderaibackend.conversations;

import java.util.List;

public record Conversation(
        String id,
        String profileId,
        List<ChatMessage> messages
) {
}
