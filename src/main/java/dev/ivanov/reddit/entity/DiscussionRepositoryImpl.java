package dev.ivanov.reddit.entity;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public class DiscussionRepositoryImpl implements DiscussionRepository {
    private final List<Discussion> discussions = new ArrayList<>();

    @Override
    public Stream<Discussion> getAll() {
        return discussions.stream();
    }

    @Override
    public Optional<Discussion> getById(UUID id) {
        return discussions.stream().filter(discussion -> discussion.getId().equals(id)).findAny();
    }

    @Override
    public void save(Discussion discussion) {
        discussions.add(discussion);
    }
}
