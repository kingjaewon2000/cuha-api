package com.cju.cuhaapi.mapper;

import com.cju.cuhaapi.domain.post.dto.CommentDto;
import com.cju.cuhaapi.domain.post.dto.CommentDto.UpdateRequest;
import com.cju.cuhaapi.domain.post.entity.Comment;
import com.cju.cuhaapi.domain.post.dto.CommentDto.CommentResponse;
import com.cju.cuhaapi.domain.post.dto.CommentDto.SaveRequest;
import com.cju.cuhaapi.domain.member.entity.Member;
import com.cju.cuhaapi.domain.post.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "request.body", target = "body"),
            @Mapping(source = "post", target = "post"),
            @Mapping(source = "member", target = "member"),
            @Mapping(target = "baseTime", ignore = true)
    })
    Comment saveRequestToEntity(SaveRequest request, Post post, Member member);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "request.body", target = "body"),
            @Mapping(source = "post", target = "post"),
            @Mapping(source = "member", target = "member"),
            @Mapping(target = "baseTime", ignore = true)
    })
    Comment updateRequestToEntity(UpdateRequest request, Post post, Member member);

    @Mappings({
            @Mapping(source = "comment.post.id", target = "postId")
    })
    CommentResponse entityToCommentResponse(Comment comment);
}
