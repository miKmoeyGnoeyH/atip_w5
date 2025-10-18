package com.example.atipw5.web;

import com.example.atipw5.domain.Post;
import com.example.atipw5.repository.PostRepository;
import com.example.atipw5.web.dto.PostDtos.PageResponse;
import com.example.atipw5.web.dto.PostDtos.PostListItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;

    @GetMapping
    public PageResponse<PostListItem> list(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String order,
            @RequestParam(required = false) String q
    ) {
        int pageIndex = Math.max(page - 1, 0);
        Sort.Direction direction = "asc".equalsIgnoreCase(order) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageIndex, limit, Sort.by(direction, sortBy));
        Page<Post> pageResult = (q != null && !q.isBlank())
                ? postRepository.findByTitleContainingIgnoreCase(q, pageable)
                : postRepository.findAll(pageable);
        return new PageResponse<>(
                pageResult.getContent().stream().map(PostListItem::from).collect(Collectors.toList()),
                page,
                pageResult.getTotalPages(),
                pageResult.getTotalElements()
        );
    }
}


