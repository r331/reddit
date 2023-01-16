package dev.ivanov.reddit.controller;

import dev.ivanov.reddit.DiscussionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class DiscussionController {
    @Autowired
    DiscussionService discussionService;

    @GetMapping("/topics")
    @Operation(summary = "Get all topics")
    List<DiscussionDto> getAllTopics() {
        return discussionService.getAllDiscussions();
    }

    @PostMapping("/comments")
    @Operation(summary = "Get all comments by ids")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CommentDto.class)))}),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content)})
    List<CommentDto> getComments(@RequestBody List<UUID> ids) {
        return discussionService.getComments(ids);
    }

    @Operation(summary = "Start new topic with message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success saved topic",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid start message",
                    content = @Content)})
    @PutMapping("/start")
    void startDiscussion(@Parameter(description = "Topic message") @RequestBody String topic) {
        discussionService.startDiscussion(topic);
    }

    @Operation(summary = "Add comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success saved comment",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Invalid request",
                    content = @Content)})
    @PutMapping("/addComment/{id}/{isDiscussion}")
    void addComment(@Parameter(description = "String with comment text") @RequestBody String comment,
                    @Parameter(description = "id of parent - another comment or discussion") @PathVariable UUID id,
                    @Parameter(description = "flag - do we add comment to discussion") @PathVariable boolean isDiscussion) {
        discussionService.addComment(id, comment, isDiscussion);
    }
}
