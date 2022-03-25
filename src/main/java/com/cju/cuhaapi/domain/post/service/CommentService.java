package com.cju.cuhaapi.domain.post.service;

import com.cju.cuhaapi.domain.member.entity.Member;
import com.cju.cuhaapi.domain.post.dto.CommentDto;
import com.cju.cuhaapi.domain.post.dto.CommentDto.SaveRequest;
import com.cju.cuhaapi.domain.post.dto.CommentDto.UpdateRequest;
import com.cju.cuhaapi.domain.post.entity.Comment;
import com.cju.cuhaapi.domain.post.entity.CommentLike;
import com.cju.cuhaapi.domain.post.entity.Post;
import com.cju.cuhaapi.domain.post.repository.CommentLikeRepository;
import com.cju.cuhaapi.domain.post.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostService postService;

    public Comment getComment(String name, Long postId, Long commentId)  {
        return commentRepository.findByPostCategoryNameAndPostIdAndId(name, postId, commentId);
    }

    public Page<Comment> getComments(Integer start, Integer end) {
        PageRequest pageRequest = PageRequest.of(start, end, Sort.by("id").descending());

        return commentRepository.findAll(pageRequest);
    }

    public List<Comment> getComments(String name, Long postId, Integer start, Integer end) {
        PageRequest pageRequest = PageRequest.of(start, end, Sort.by("id").descending());

        return commentRepository.findAllByPostCategoryNameAndPostId(name, postId, pageRequest);
    }

    public void saveComment(String name, Long postId, SaveRequest request, Member member) {
        Post post = postService.getPost(name, postId);

        commentRepository.save(Comment.save(request, post, member));
    }

    public void updateComment(String name, Long postId, Long commentId, UpdateRequest request, Member member) {
        Comment comment = getComment(name, postId, commentId);
        if (!isCheckOwner(comment.getMember(), member)) {
            throw new IllegalArgumentException("댓글은 작성한 유저가 아닙니다.");
        }

        commentRepository.save(Comment.update(request, comment));
    }

    public void deleteComment(String name, Long postId, Long commentId, Member member) {
        Comment comment = getComment(name, postId, commentId);

        if (!isCheckOwner(comment.getMember(), member)) {
            throw new IllegalArgumentException("댓글은 작성한 유저가 아닙니다.");
        }
        commentRepository.delete(comment);
    }

    public void likeComment(String name, Long postId, Long commentId, Member member) {
        Comment comment = getComment(name, postId, commentId);

        if (commentLikeRepository.existsByCommentIdAndMemberId(comment.getId(), member.getId())) {
            throw new IllegalArgumentException("이미 추천하신 댓글 입니다.");
        }

        CommentLike like = CommentLike.builder()
                .member(member)
                .comment(comment)
                .build();

        commentLikeRepository.save(like);
    }

    public Long likeCount(Long commentId) {
        return commentLikeRepository.countByCommentId(commentId);
    }

    private boolean isCheckOwner(Member owner, Member member) {
        if (owner.getUsername().equals(member.getUsername())
                && owner.getId().equals(member.getId())) {
            return true;
        }

        return false;
    }
}
