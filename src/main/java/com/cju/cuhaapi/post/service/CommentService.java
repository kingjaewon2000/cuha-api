package com.cju.cuhaapi.post.service;

import com.cju.cuhaapi.member.domain.entity.Member;
import com.cju.cuhaapi.post.domain.entity.Comment;
import com.cju.cuhaapi.post.domain.entity.CommentLike;
import com.cju.cuhaapi.post.domain.entity.Post;
import com.cju.cuhaapi.post.domain.entity.PostLike;
import com.cju.cuhaapi.post.domain.repository.CommentLikeRepository;
import com.cju.cuhaapi.post.domain.repository.CommentRepository;
import com.cju.cuhaapi.post.dto.CommentSaveRequest;
import com.cju.cuhaapi.post.dto.CommentUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cju.cuhaapi.member.domain.entity.Member.isEqualMember;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostService postService;

    public List<Comment> findComments(Pageable pageable) {
        return commentRepository.findComments(pageable);
    }

    public List<Comment> findComments(String categoryName, Long postId, Pageable pageable) {
        return commentRepository.findComments(categoryName, postId, pageable);
    }

    public Comment findComment(String categoryName, Long postId, Long commentId)  {
        return commentRepository.findComment(categoryName, postId, commentId);
    }

    @Transactional
    public void saveComment(String categoryName, Long postId, CommentSaveRequest request, Member member) {
        Post post = postService.findPost(categoryName, postId);
        Comment comment = Comment.saveComment(request, post, member);

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(String categoryName, Long postId, Long commentId, CommentUpdateRequest request, Member authMember) {
        Comment comment = findComment(categoryName, postId, commentId);
        Member member = comment.getMember();

        if (!isEqualMember(member, authMember)) {
            throw new IllegalArgumentException("댓글은 작성한 유저가 아닙니다.");
        }

        comment.updateComment(request);
    }

    @Transactional
    public void deleteComment(String categoryName, Long postId, Long commentId, Member writeMember) {
        Comment comment = findComment(categoryName, postId, commentId);
        Member member = comment.getMember();

        if (!Member.isEqualMember(member, writeMember)) {
            throw new IllegalArgumentException("댓글은 작성한 유저가 아닙니다.");
        }
        commentRepository.delete(comment);
    }

    @Transactional
    public void likeComment(String categoryName, Long postId, Long commentId, Member authMember) {
        Comment comment = findComment(categoryName, postId, commentId);

        if (isLiked(commentId, authMember)) {
            throw new IllegalArgumentException("이미 추천하신 댓글 입니다.");
        }

        CommentLike like = CommentLike.createLike(comment, authMember);

        commentLikeRepository.save(like);
    }

    public boolean isLiked(Long commentId, Member authMember) {
        return commentLikeRepository.existsByCommentIdAndMemberId(commentId, authMember.getId());
    }
}
