package com.example.atipw5.config;

import com.example.atipw5.domain.Post;
import com.example.atipw5.repository.PostRepository;
import org.dataloader.BatchLoader;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderFactory;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Configuration
public class DataLoaderConfig {
    public static final String POSTS_BY_AUTHOR_ID = "POSTS_BY_AUTHOR_ID";
    // NOTE: DataLoaderRegistryCustomizer는 사용하지 않습니다. BatchMapping으로 N+1을 해소합니다.
}


