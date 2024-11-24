package com.korit.projectrrs.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateUserRequestDto {
    private String userName;
    private String userPassword;
    private String confirmPassword;
    private String userPhone;
    private String userAddress;
    private String userAddressDetail;
    @NotBlank
    private String userProfileImageUrl;
}
