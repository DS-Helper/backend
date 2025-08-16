package com.project.ds_helper.domain.inquiry.service;

import com.project.ds_helper.common.dto.request.S3ImageUploadReqDto;
import com.project.ds_helper.common.util.UserUtil;
import com.project.ds_helper.domain.inquiry.dto.request.CreateInquiryReqDto;
import com.project.ds_helper.domain.inquiry.dto.response.GetAllInquiriesOfUserResDto;
import com.project.ds_helper.domain.inquiry.entity.Inquiry;
import com.project.ds_helper.domain.inquiry.repository.InquiryRepository;
import com.project.ds_helper.domain.post.entity.Image;
import com.project.ds_helper.domain.post.repository.ImageRepository;
import com.project.ds_helper.domain.post.util.ImageUtil;
import com.project.ds_helper.domain.post.util.S3Util;
import com.project.ds_helper.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final ImageRepository imageRepository;
    private final UserUtil userUtil;
    private final S3Util s3Util;
    private final ImageUtil imageUtil;

    /**
     * 유저의 전체 문의글 조회
     * **/
    public Object getAllInquiresOfUser(Authentication authentication) {
       return GetAllInquiriesOfUserResDto.toDtoList(inquiryRepository.findAllByUser_Id(userUtil.extractUserId(authentication)));
    }

    /**
     * 문의 생성
     * 이미지 생성
     * s3 저장
     * inquiry에 url 저장
     * **/
    public void createInquiry(Authentication authentication, CreateInquiryReqDto dto) throws IOException {
            User user = userUtil.findUserById(userUtil.extractUserId(authentication));
            s3Util.uploadImages(dto.getIamges().stream().map(image -> {
                try {
                    return new S3ImageUploadReqDto(imageUtil.toStoredFilename(image), image.getContentType(), image.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException("S3 Upload Fail");
                }
            }).toList());

            List<Image> images = imageUtil.toImages(dto.getIamges());

            Inquiry inquiry = dto.toInquiry(dto, user, images.stream().map(Image::getUrl).toList());
            imageRepository.saveAll(images);
            log.info("Image Saved Successfully");
            inquiryRepository.save(inquiry);
            log.info("Inquiry Saved Successfully");
    }
}
