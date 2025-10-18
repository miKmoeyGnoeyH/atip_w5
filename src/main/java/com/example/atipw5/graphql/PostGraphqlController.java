package com.example.atipw5.graphql;

import com.example.atipw5.domain.Post;
import com.example.atipw5.domain.User;
import com.example.atipw5.repository.PostRepository;
import com.example.atipw5.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoader;
import org.springframework.data.domain.*;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import graphql.schema.DataFetchingEnvironment;

import java.util.Map;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Controller
@Slf4j
@RequiredArgsConstructor
public class PostGraphqlController {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    record PostPageDto(List<Post> content, int page, int totalPages, int totalElements) {}

    @QueryMapping
    public PostPageDto posts(@Argument Integer page,
                             @Argument Integer limit,
                             @Argument String sortBy,
                             @Argument(name = "order") SortOrder order,
                             @Argument String q) {
        int p = page == null ? 1 : page;
        int l = limit == null ? 10 : limit;
        String sort = (sortBy == null || sortBy.isBlank()) ? "createdAt" : sortBy;
        Sort.Direction direction = (order == null || order == SortOrder.DESC) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(Math.max(p - 1, 0), l, Sort.by(direction, sort));
        Page<Post> pageResult = (q != null && !q.isBlank())
                ? postRepository.findByTitleContainingIgnoreCase(q, pageable)
                : postRepository.findAll(pageable);
        return new PostPageDto(pageResult.getContent(), p, pageResult.getTotalPages(), (int) pageResult.getTotalElements());
    }

    @QueryMapping
    public List<User> users(@Argument Integer limit) {
        int l = limit == null ? 50 : limit;
        Page<User> page = userRepository.findAll(PageRequest.of(0, l));
        return page.getContent();
    }

    enum SortOrder { ASC, DESC }

    @SchemaMapping(typeName = "Post", field = "author")
    public User author(Post post) { return post.getAuthor(); }

    @SchemaMapping(typeName = "User", field = "posts")
    public List<Post> posts(User user, @Argument Integer limit) {
        int l = limit == null ? 10 : limit;
        List<Post> all = postRepository.findByAuthorId(user.getId(), Sort.by(Sort.Direction.DESC, "createdAt"));
        return all.stream().limit(l).toList();
    }
}


