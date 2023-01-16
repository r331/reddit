package dev.ivanov.reddit;

import dev.ivanov.reddit.controller.CommentDto;
import dev.ivanov.reddit.controller.DiscussionDto;
import dev.ivanov.reddit.entity.Comment;
import dev.ivanov.reddit.entity.CommentRepository;
import dev.ivanov.reddit.entity.Discussion;
import dev.ivanov.reddit.entity.DiscussionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DiscussionServiceImpl implements DiscussionService {
    private final DiscussionRepository discussionRepository;
    private final CommentRepository commentRepository;

    public DiscussionServiceImpl(DiscussionRepository discussionRepository, CommentRepository commentRepository) {
        this.discussionRepository = discussionRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<DiscussionDto> getAllDiscussions() {
        return discussionRepository.getAll()
                .map(this::discussionToDiscussionDto)
                .toList();
    }

    @Override
    public Optional<DiscussionDto> getDiscussion(UUID id) {
        return discussionRepository.getById(id).map(this::discussionToDiscussionDto);
    }

    @Override
    public void startDiscussion(String topic) {
        Discussion discussion = new Discussion(topic);
        discussionRepository.save(discussion);
    }

    @Override
    public void addComment(UUID parentId, String commentText, boolean isdDiscussion) {
        Commentable parent = null;
        if (isdDiscussion)
            parent = discussionRepository.getById(parentId).orElseThrow();
        else
            parent = commentRepository.getById(List.of(parentId)).findAny().orElseThrow();
        Comment newComment = new Comment(commentText);
        parent.addComment(newComment.getId());
        commentRepository.save(newComment);
    }

    public List<CommentDto> getComments(List<UUID> ids) {
        return commentRepository.getById(ids).map(this::commentToCommentDto).toList();
    }

    private DiscussionDto discussionToDiscussionDto(Discussion discussion) {
        return new DiscussionDto(discussion.getId(), discussion.getTopic(), discussion.getComments());
    }

    private CommentDto commentToCommentDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(), comment.getReplies().toList());
    }
}
