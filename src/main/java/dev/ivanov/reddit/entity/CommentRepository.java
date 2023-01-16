package dev.ivanov.reddit.entity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public interface CommentRepository {
    Stream<Comment> getById(List<UUID> ids);

    void save(Comment comment);
}
