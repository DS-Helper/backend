package com.project.ds_helper.domain.inquiry.entity;

import com.project.ds_helper.common.base.entity.BaseTime;
import com.project.ds_helper.domain.inquiry.enums.InquiryStatus;
import com.project.ds_helper.domain.reply.entity.Reply;
import com.project.ds_helper.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_inquiry")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Inquiry extends BaseTime {

    @PrePersist
    private void perPersistGenerateId(){
        this.id = String.valueOf(UUID.randomUUID());
    }

    @Id
    @Column(name = "inquiry_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    @Enumerated(EnumType.STRING)
//    private InquiryType inquiryType = InquiryType.?;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;


    @Enumerated(EnumType.STRING)
    private InquiryStatus status = InquiryStatus.UNANSWERED;

    // 답변
    @OneToOne(fetch = FetchType.LAZY, optional = true, cascade = {}, orphanRemoval = false)
    private Reply reply;
}
