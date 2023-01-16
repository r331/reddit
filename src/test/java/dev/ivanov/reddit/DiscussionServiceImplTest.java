package dev.ivanov.reddit;

import dev.ivanov.reddit.entity.CommentRepository;
import dev.ivanov.reddit.entity.CommentRepositoryImpl;
import dev.ivanov.reddit.entity.DiscussionRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

class DiscussionServiceImplTest {

    CommentRepository commentRepository;
    DiscussionService discussionServiceImpl;

    @BeforeEach
    void init() {
        commentRepository = new CommentRepositoryImpl();
        discussionServiceImpl =
                new DiscussionServiceImpl(new DiscussionRepositoryImpl(), commentRepository);
    }

    @Test
    @DisplayName("Start new discussion, add comment, add comment to previous one")
    void createDiscussionAndAddComments() {
        discussionServiceImpl.startDiscussion("new topic");

        var topics = discussionServiceImpl.getAllDiscussions();
        Assertions.assertFalse(topics.isEmpty());

        var topicId = topics.get(0).id();

        discussionServiceImpl.addComment(topicId, "comment1", true);
        topics = discussionServiceImpl.getAllDiscussions();
        var comments = topics.get(0).comments();
        Assertions.assertFalse(comments.isEmpty());

        var comment = comments.get(0);
        discussionServiceImpl.addComment(comment, "comment2", false);
        Assertions.assertFalse(commentRepository.getById(List.of(comment)).toList().isEmpty());

        topics = discussionServiceImpl.getAllDiscussions();
        Assertions.assertEquals(1, topics.get(0).comments().size());
    }

    @Test
    @DisplayName("Test edge cases")
    void testEdgeCases() {
        discussionServiceImpl.startDiscussion("new topic");

        var topics = discussionServiceImpl.getAllDiscussions();
        var topicId = discussionServiceImpl.getAllDiscussions().get(0).id();

        Assertions.assertThrows(NoSuchElementException.class,
                () -> discussionServiceImpl.addComment(topicId, "comment1", false));
        discussionServiceImpl.addComment(topicId, "comment1", true);

        topics = discussionServiceImpl.getAllDiscussions();
        var comment = topics.get(0).comments().get(0);

        Assertions.assertThrows(NoSuchElementException.class,
                () -> discussionServiceImpl.addComment(comment, "comment2", true));
        Assertions.assertThrows(NoSuchElementException.class,
                () -> discussionServiceImpl.addComment(UUID.randomUUID(), "comment2", false));

    }
}