package com.example.atipw5.web.dto;

import com.example.atipw5.domain.Post;

import java.time.format.DateTimeFormatter;

public final class PostDtos {
    private PostDtos() {}

    public record PostListItem(Long id, String title, String createdAt, Long authorId, String authorUsername) {
        public static PostListItem from(Post p) {
            return new PostListItem(
                    p.getId(),
                    p.getTitle(),
                    p.getCreatedAt() == null ? null : DateTimeFormatter.ISO_INSTANT.format(p.getCreatedAt()),
                    p.getAuthor() == null ? null : p.getAuthor().getId(),
                    p.getAuthor() == null ? null : p.getAuthor().getUsername()
            );
        }
    }

    public record PageResponse<T>(java.util.List<T> items, int page, int totalPages, long totalElements) {}
}


