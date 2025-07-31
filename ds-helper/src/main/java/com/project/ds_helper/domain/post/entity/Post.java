package com.project.ds_helper.domain.post.entity;

import com.project.ds_helper.domain.base.entity.BaseTime;
import com.project.ds_helper.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post extends BaseTime {

    @PrePersist
    public void prePersist(){
        this.id = String.valueOf(UUID.randomUUID());
    }

    @Id @Column(name = "post_id")
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "image_path")
    private List<String> imagePath;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {})
    @JoinColumn(name = "writer_id")
    private User writer;

}
