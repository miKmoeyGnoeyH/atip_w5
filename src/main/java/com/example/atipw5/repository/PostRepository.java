package com.example.atipw5.repository;

import com.example.atipw5.domain.Post;
import com.example.atipw5.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
    List<Post> findByAuthorIn(Collection<User> authors);
    List<Post> findByAuthorIdIn(Collection<Long> authorIds);
    List<Post> findByAuthorId(Long authorId, org.springframework.data.domain.Sort sort);
}


