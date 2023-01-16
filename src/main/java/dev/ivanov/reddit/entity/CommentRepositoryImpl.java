package dev.ivanov.reddit.entity;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
    private final List<Comment> comments = new ArrayList<>();

    @Override
    public Stream<Comment> getById(List<UUID> ids) {
        return comments.stream().filter(comment -> ids.contains(comment.getId()));
    }

    @Override
    public void save(Comment comment) {
        comments.add(comment);
    }
}
