package io.javabrains.tinderaibackend;

import io.javabrains.tinderaibackend.conversations.ChatMessage;
import io.javabrains.tinderaibackend.conversations.Conversation;
import io.javabrains.tinderaibackend.conversations.ConversationRepository;
import io.javabrains.tinderaibackend.profiles.Gender;
import io.javabrains.tinderaibackend.profiles.Profile;
import io.javabrains.tinderaibackend.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class TinderAiBackendApplication implements CommandLineRunner {

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private ConversationRepository conversationRepository;

	public static void main(String[] args) {
		SpringApplication.run(TinderAiBackendApplication.class, args);
	}

	public void run(String... args) {
		Profile profile = new Profile(
				"1",
				"Koushik",
				"Kothagal",
				40,
				"Indian",
				Gender.MALE,
				"Software programmer",
				"foo.jpg",
				"INTP"
		);
		profileRepository.save(profile);
		profileRepository.findAll().forEach(System.out::println);

		Conversation conversation = new Conversation(
				"1",
				profile.id(),
				List.of(
						new ChatMessage("Hello", profile.id(), LocalDateTime.now())
				)
		);

		conversationRepository.save(conversation);

		conversationRepository.findAll().forEach(System.out::println);

	}


}
