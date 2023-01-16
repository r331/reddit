package dev.ivanov.reddit;

import dev.ivanov.reddit.controller.CommentDto;
import dev.ivanov.reddit.controller.DiscussionDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiscussionService {
    List<DiscussionDto> getAllDiscussions();

    Optional<DiscussionDto> getDiscussion(UUID id);

    void startDiscussion(String topic);

    List<CommentDto> getComments(List<UUID> ids);

    void addComment(UUID parentId, String commentText, boolean isdDiscussion);
}
