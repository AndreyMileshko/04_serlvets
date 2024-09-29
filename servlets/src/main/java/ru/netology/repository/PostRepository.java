package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {

  private final CopyOnWriteArrayList<Post> posts = new CopyOnWriteArrayList<>();
  private final AtomicLong postId = new AtomicLong(1);

  public List<Post> all() {
    return posts;
  }

  public Optional<Post> getById(long id) {
    return  posts.stream()
            .filter(post -> post.getId() == id)
            .findFirst();
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      post.setId(postId.getAndIncrement());
      posts.add(post);
      return post;
    }
    if (posts.stream().anyMatch(searchedPost -> searchedPost.getId() == post.getId())) {
      posts.stream()
              .filter(searchedPost -> searchedPost.getId() == post.getId())
              .findFirst()
              .orElseThrow()
              .setContent(post.getContent());
      return post;
    }
    throw new NotFoundException("Post with this id was not found, operation interrupted.");
  }

  public void removeById(long id) {
    posts.stream()
            .filter(post -> post.getId() == id)
            .findFirst()
            .ifPresent(posts::remove);
  }
}
