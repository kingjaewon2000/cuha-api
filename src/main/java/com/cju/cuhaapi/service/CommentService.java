package com.cju.cuhaapi.service;

import com.cju.cuhaapi.repository.entity.member.Member;
import com.cju.cuhaapi.controller.dto.CommentDto.SaveRequest;
import com.cju.cuhaapi.controller.dto.CommentDto.UpdateRequest;
import com.cju.cuhaapi.repository.entity.post.Comment;
import com.cju.cuhaapi.repository.entity.post.CommentLike;
import com.cju.cuhaapi.repository.entity.post.Post;
import com.cju.cuhaapi.repository.CommentLikeRepository;
import com.cju.cuhaapi.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cju.cuhaapi.repository.entity.member.Member.isSameMember;

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

        commentRepository.save(Comment.saveComment(request, post, member));
    }

    public void updateComment(String name, Long postId, Long commentId, UpdateRequest request, Member authMember) {
        Comment comment = getComment(name, postId, commentId);
        Member member = comment.getMember();

        if (!isSameMember(member, authMember)) {
            throw new IllegalArgumentException("댓글은 작성한 유저가 아닙니다.");
        }

        commentRepository.save(Comment.updateComment(request, comment));
    }

    public void deleteComment(String name, Long postId, Long commentId, Member writeMember) {
        Comment comment = getComment(name, postId, commentId);
        Member member = comment.getMember();

        if (!Member.isSameMember(member, writeMember)) {
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
}
