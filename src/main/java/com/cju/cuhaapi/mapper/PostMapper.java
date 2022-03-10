package com.cju.cuhaapi.mapper;

import com.cju.cuhaapi.member.Member;
import com.cju.cuhaapi.post.Post;
import com.cju.cuhaapi.post.PostDto.CreateRequest;
import com.cju.cuhaapi.post.PostDto.PostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostResponse toPostResponse(Post post);
    Post createRequestToEntity(CreateRequest createRequest, Member member);
}
