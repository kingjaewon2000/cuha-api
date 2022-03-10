package com.cju.cuhaapi.mapper;

import com.cju.cuhaapi.member.Member;
import com.cju.cuhaapi.post.Category;
import com.cju.cuhaapi.post.Post;
import com.cju.cuhaapi.post.PostDto.CreateRequest;
import com.cju.cuhaapi.post.PostDto.PostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mappings({
            @Mapping(source = "member.username", target = "username"),
            @Mapping(source = "baseTime.createdAt", target = "createdAt")
    })
    PostResponse toPostResponse(Post post);

//    @Mappings({
//            @Mapping(target = "id", ignore = true),
//            @Mapping(source = "member", target = "member"),
//            @Mapping(target = "baseTime.createdAt", expression = "java(java.time.LocalDateTime.now())"),
//            @Mapping(target = "baseTime.updatedAt", expression = "java(java.time.LocalDateTime.now())")
//    })
//    Post createRequestToEntity(CreateRequest createRequest, Member member);

//    @Mappings({
//            @Mapping(target = "id", ignore = true),
//            @Mapping(source = "member", target = "member"),
//            @Mapping(target = "baseTime.createdAt", expression = "java(java.time.LocalDateTime.now())"),
//            @Mapping(target = "baseTime.updatedAt", expression = "java(java.time.LocalDateTime.now())")
//    })
//    Post copyEntity(Post post, Category category);
}
