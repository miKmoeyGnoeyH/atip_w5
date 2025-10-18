package com.example.atipw5.bootstrap;

import com.example.atipw5.domain.Post;
import com.example.atipw5.domain.User;
import com.example.atipw5.repository.PostRepository;
import com.example.atipw5.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) return;

        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            User u = User.builder()
                    .username("user" + i)
                    .displayName("User " + i)
                    .createdAt(Instant.now().minusSeconds(86400L * (6 - i)))
                    .build();
            users.add(u);
        }
        userRepository.saveAll(users);

        Random random = new Random(42);
        List<Post> posts = new ArrayList<>();
        for (User u : users) {
            for (int j = 1; j <= 12; j++) {
                Post p = Post.builder()
                        .title("Post " + j + " by " + u.getUsername())
                        .content("Content for post " + j + " by " + u.getUsername())
                        .createdAt(Instant.now().minusSeconds(random.nextInt(86400 * 14)))
                        .author(u)
                        .build();
                posts.add(p);
            }
        }
        postRepository.saveAll(posts);
    }
}


