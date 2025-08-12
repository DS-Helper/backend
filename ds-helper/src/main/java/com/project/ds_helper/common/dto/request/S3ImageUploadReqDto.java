package com.project.ds_helper.common.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class S3ImageUploadReqDto {

    /**
     * @Validated 없이는 동작하지 않음 (특히 Service 레이어 지원x)
     * **/

    @NotBlank
    private String storedFilename;

    @NotBlank
    private String contentType;

    @NotNull
    private byte[] bytes;
}
