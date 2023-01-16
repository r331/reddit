package dev.ivanov.reddit.entity;

import dev.ivanov.reddit.Commentable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Discussion implements Commentable {
    private final UUID id;
    private final String topic;
    private final List<UUID> comments;

    public Discussion(String topic) {
        this.id = UUID.randomUUID();
        this.topic = topic;
        this.comments = new ArrayList<>();
    }

    public String getTopic() {
        return topic;
    }

    public void addComment(UUID comment) {
        comments.add(comment);
    }

    public List<UUID> getComments() {
        return comments;
    }

    public UUID getId() {
        return id;
    }
}
