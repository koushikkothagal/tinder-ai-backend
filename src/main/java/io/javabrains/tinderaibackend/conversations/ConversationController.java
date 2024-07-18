package io.javabrains.tinderaibackend.conversations;

import io.javabrains.tinderaibackend.profiles.Profile;
import io.javabrains.tinderaibackend.profiles.ProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
public class ConversationController {

    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;
    private final ConversationService conversationService;

    public ConversationController(ConversationRepository conversationRepository, ProfileRepository profileRepository, ConversationService conversationService) {
        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
        this.conversationService = conversationService;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/conversations/{conversationId}")
    public Conversation getConversation(
            @PathVariable String conversationId
    ) {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find conversation with the ID " + conversationId
                ));
    }


    @CrossOrigin(origins = "*")
    @PostMapping("/conversations/{conversationId}")
    public Conversation addMessageToConversation(
            @PathVariable String conversationId,
            @RequestBody ChatMessage chatMessage
    ) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find conversation with the ID " + conversationId
                ));
        String matchProfileId = conversation.profileId();

        Profile profile = profileRepository.findById(matchProfileId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find a profile with ID " + matchProfileId
                ));
        Profile user = profileRepository.findById(chatMessage.authorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find a profile with ID " + chatMessage.authorId()
                ));

        // TODO: Need to validate that the author of a message happens to be only the profile associated with the message or the user ONLY

        ChatMessage messageWithTime = new ChatMessage(
                chatMessage.messageText(),
                chatMessage.authorId(),
                LocalDateTime.now()
        );
        conversation.messages().add(messageWithTime);
        conversationService.generateProfileResponse(conversation, profile, user);
        conversationRepository.save(conversation);
        return conversation;

    }
}
