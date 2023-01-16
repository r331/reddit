package dev.ivanov.reddit.entity;

import dev.ivanov.reddit.Commentable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class Comment implements Commentable {
    private final UUID id;
    private final String text;
    private final List<UUID> replies;

    public Comment(String text) {
        this.id = UUID.randomUUID();
        this.text = text;
        this.replies = new ArrayList<>();
    }

    public String getText() {
        return text;
    }

    public Stream<UUID> getReplies() {
        return replies.stream();
    }

    public UUID getId() {
        return id;
    }

    @Override
    public void addComment(UUID id) {
        replies.add(id);
    }
}
