package dev.ivanov.reddit.entity;


import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;


public interface DiscussionRepository {
    Stream<Discussion> getAll();

    Optional<Discussion> getById(UUID id);

    void save(Discussion discussion);
}
