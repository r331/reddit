package dev.ivanov.reddit.controller;

import java.util.List;
import java.util.UUID;

public record DiscussionDto(UUID id, String topic, List<UUID> comments) {
}
