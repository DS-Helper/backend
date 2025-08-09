package com.project.ds_helper.domain.post.repository;

import com.project.ds_helper.domain.post.dto.response.GetPostResDto;
import com.project.ds_helper.domain.post.entity.Post;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {

    @Query(value = """
            SELECT new com.project.ds_helper.domain.post.dto.response.GetPostResDto
            FROM Post p JOIN p.user u
            WHERE u.id = :userId
            """, nativeQuery = false)
    List<GetPostResDto> findAllByUser_Id(@Param("userId") String userId);
}
