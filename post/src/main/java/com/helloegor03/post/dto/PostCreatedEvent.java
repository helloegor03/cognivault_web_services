package com.helloegor03.post.dto;

public class PostCreatedEvent {
    private Long postId;
    private String name;
    private String description;
    private String tag;
    private String author;

    public PostCreatedEvent() {}

    public PostCreatedEvent(Long postId, String name, String description, String tag, String author) {
        this.postId = postId;
        this.name = name;
        this.description = description;
        this.tag = tag;
        this.author = author;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
