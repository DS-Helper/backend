package com.project.ds_helper.domain.inquiry.entity;

import com.project.ds_helper.domain.base.entity.BaseTime;
import com.project.ds_helper.domain.inquiry.enums.InquiryStatus;
import com.project.ds_helper.domain.inquiry.enums.InquiryType;
import com.project.ds_helper.domain.post.entity.Image;
import com.project.ds_helper.domain.reply.entity.Reply;
import com.project.ds_helper.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_inquiry")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Inquiry extends BaseTime {

    @PrePersist
    private void perPersistGenerateId(){
        this.id = String.valueOf(UUID.randomUUID());
    }

    @Id
    @Column(name = "inquiry_id")
    private String id;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content; // 내용

    @Column(name = "image_url")
    private List<String> imagesUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private InquiryType type = InquiryType.OTHER; // 유형

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private InquiryStatus status = InquiryStatus.UNANSWERED; // 답변 상태

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {})
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 작성한 유저

    @OneToOne(fetch = FetchType.LAZY, optional = true, cascade = {}, orphanRemoval = false)
    private Reply reply; // 답변
}
