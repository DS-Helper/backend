package com.project.ds_helper.domain.post.repository;

import com.project.ds_helper.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, String> {


    Page<Post> findAllByUser_Id(String userId, Pageable pageable);
}
