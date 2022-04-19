package com.cju.cuhaapi.post.service;

import com.cju.cuhaapi.member.domain.entity.Member;
import com.cju.cuhaapi.post.dto.CommentDto.SaveRequest;
import com.cju.cuhaapi.post.dto.CommentDto.UpdateRequest;
import com.cju.cuhaapi.post.domain.entity.Comment;
import com.cju.cuhaapi.post.domain.entity.CommentLike;
import com.cju.cuhaapi.post.domain.entity.Post;
import com.cju.cuhaapi.post.domain.repository.CommentLikeRepository;
import com.cju.cuhaapi.post.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cju.cuhaapi.member.domain.entity.Member.isEqualMember;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostService postService;

    public Comment getComment(String categoryName, Long postId, Long commentId)  {
        return commentRepository.findByPostCategoryNameAndPostIdAndId(categoryName, postId, commentId);
    }

    public Page<Comment> getComments(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());

        return commentRepository.findAll(pageRequest);
    }

    public List<Comment> getComments(String categoryName, Long postId, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending());

        return commentRepository.findAllByPostCategoryNameAndPostId(categoryName, postId, pageRequest);
    }

    public void saveComment(String categoryName, Long postId, SaveRequest request, Member member) {
        Post post = postService.getPost(categoryName, postId);

        commentRepository.save(Comment.saveComment(request, post, member));
    }

    public void updateComment(String categoryName, Long postId, Long commentId, UpdateRequest request, Member authMember) {
        Comment comment = getComment(categoryName, postId, commentId);
        Member member = comment.getMember();

        if (!isEqualMember(member, authMember)) {
            throw new IllegalArgumentException("댓글은 작성한 유저가 아닙니다.");
        }

        comment.updateComment(request);
        commentRepository.save(comment);
    }

    public void deleteComment(String categoryName, Long postId, Long commentId, Member writeMember) {
        Comment comment = getComment(categoryName, postId, commentId);
        Member member = comment.getMember();

        if (!Member.isEqualMember(member, writeMember)) {
            throw new IllegalArgumentException("댓글은 작성한 유저가 아닙니다.");
        }
        commentRepository.delete(comment);
    }

    public void likeComment(String categoryName, Long postId, Long commentId, Member member) {
        Comment comment = getComment(categoryName, postId, commentId);

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

    public List<Boolean> isClickLike(String categoryName, Long postId, Member authMember, Integer start, Integer end) {
        Post post = postService.getPost(categoryName, postId);
        PageRequest pageRequest = PageRequest.of(start, end);

        return commentLikeRepository.existsAllByMemberId(authMember.getId(), pageRequest);
    }
}
