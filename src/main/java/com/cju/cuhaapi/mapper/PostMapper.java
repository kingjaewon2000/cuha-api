package com.cju.cuhaapi.mapper;

import com.cju.cuhaapi.domain.member.entity.Member;
import com.cju.cuhaapi.domain.post.dto.PostDto.CreateRequest;
import com.cju.cuhaapi.domain.post.dto.PostDto.PostResponse;
import com.cju.cuhaapi.domain.post.dto.PostDto.UpdateRequest;
import com.cju.cuhaapi.domain.post.entity.Category;
import com.cju.cuhaapi.domain.post.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mappings({
            @Mapping(source = "member.username", target = "username"),
            @Mapping(source = "member.name", target = "name"),
            @Mapping(source = "baseTime.createdAt", target = "createdAt")
    })
    PostResponse entityToPostResponse(Post post);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source= "request.title", target = "title"),
            @Mapping(source= "request.body", target = "body"),
            @Mapping(source = "member", target = "member"),
            @Mapping(source = "category", target = "category"),
            @Mapping(target = "baseTime", ignore = true)
    })
    Post createRequestToEntity(CreateRequest request, Member member, Category category);

    @Mappings({
            @Mapping(source= "request.title", target = "title"),
            @Mapping(source= "request.body", target = "body")
    })
    Post updateRequestToEntity(UpdateRequest request, Post post);
}
