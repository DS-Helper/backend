package com.project.ds_helper.domain.post.repository;

import com.project.ds_helper.domain.post.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {

}
