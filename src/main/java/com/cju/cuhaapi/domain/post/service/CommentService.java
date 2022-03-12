package com.cju.cuhaapi.domain.post.service;

import com.cju.cuhaapi.domain.post.entity.Comment;
import com.cju.cuhaapi.domain.post.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment findComment(String name, Long postId, Long commentId)  {
        return commentRepository.findByPostCategoryNameAndPostIdAndId(name, postId, commentId);
    }

    public Page<Comment> findComments(Integer start, Integer end) {
        PageRequest pageRequest = PageRequest.of(start, end);

        return commentRepository.findAll(pageRequest);
    }

    public List<Comment> findComments(String category, Long postId, Integer start, Integer end) {
        PageRequest pageRequest = PageRequest.of(start, end);

        return commentRepository.findAllByPostCategoryNameAndPostId(category, postId, pageRequest);
    }

    public Comment saveComment(Comment comment) {
        Comment savedComment = commentRepository.save(comment);

        return savedComment;
    }

    public Comment updateComment(Comment comment) {
        Comment updatedComment = commentRepository.save(comment);

        return updatedComment;
    }

    public Comment deleteComment(String name, Long postId, Long commentId) {
        Comment comment = findComment(name, postId, commentId);
        commentRepository.delete(comment);

        return comment;
    }
}
