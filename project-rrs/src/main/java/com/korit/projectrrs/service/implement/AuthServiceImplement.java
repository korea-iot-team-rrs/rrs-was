package com.korit.projectrrs.service.implement;

import com.korit.projectrrs.common.ResponseMessage;
import com.korit.projectrrs.dto.ResponseDto;
import com.korit.projectrrs.dto.auth.reponse.LoginResponseDto;
import com.korit.projectrrs.dto.auth.reponse.SignUpResponseDto;
import com.korit.projectrrs.dto.auth.request.LoginRequestDto;
import com.korit.projectrrs.dto.auth.request.SignUpRequestDto;
import com.korit.projectrrs.entity.User;
import com.korit.projectrrs.provider.JwtProvider;
import com.korit.projectrrs.repositoiry.UserRepository;
import com.korit.projectrrs.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptpasswordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public ResponseDto<SignUpResponseDto> singUp(@Valid SignUpRequestDto dto) {
        String userName = dto.getUserName();
        String userId = dto.getUserId();
        String userPassword = dto.getUserPassword();
        String confirmPassword = dto.getConfirmUserPassword();
        String userNickName = dto.getUserNickName();
        String userPhone = dto.getUserPhone();
        String userAddress = dto.getUserAddress();
        String userAddressDetail = dto.getUserAddressDetail();
        String userEmail = dto.getUserEmail();
        String userProfileImageUrl = dto.getUserProfileImageUrl();


        SignUpResponseDto data = null;

        // 1. 유효성 검사 //
        if (userName == null || userName.isEmpty() || !userName.matches("^[가-힣]+$")) {
            return ResponseDto.setFailed(ResponseMessage.INVALID_USER_NAME);
        }

        if ( userId == null || userId.isEmpty() || !userId.matches("^[a-zA-Z0-9]{5,15}$")) {
            return ResponseDto.setFailed(ResponseMessage.INVALID_USER_ID);
        }

        if (userPassword == null || userPassword.isEmpty() || confirmPassword == null || confirmPassword.isEmpty()) {
            // INVALID_PASSWORD
            return ResponseDto.setFailed(ResponseMessage.INVALID_USER_PASSWORD);
        }

        if (!userPassword.equals(confirmPassword)) {
            return ResponseDto.setFailed(ResponseMessage.INVALID_CONFIRM_PASSWORD);
        }

        if (userPassword.length() < 8 ||
            !userPassword.matches("(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_\\-+=])[A-Za-z\\d!@#$%^&*()_\\-+=]{8,15}$")) {
            return ResponseDto.setFailed(ResponseMessage.INVALID_USER_PASSWORD);
        }

        if ( userNickName == null || userNickName.isEmpty() || !userNickName.matches("^[a-zA-Z0-9]{2,10}$")) {
            return ResponseDto.setFailed(ResponseMessage.INVALID_USER_NICKNAME);
        }

        if ( userPhone == null || userPhone.isEmpty() || !userPhone.matches("^\\d{3}-\\d{3,4}-\\d{4}$")) {
            return ResponseDto.setFailed(ResponseMessage.INVALID_USER_PHONE);
        }

        if ( userAddress == null || userAddress.isEmpty()) {
            return ResponseDto.setFailed(ResponseMessage.INVALID_USER_ADDRESS);
        }

        if ( userAddressDetail == null || userAddressDetail.isEmpty()) {
            return ResponseDto.setFailed(ResponseMessage.INVALID_USER_ADDRESS_DETAIL);
        }

        if ( userEmail == null || userEmail.isEmpty() || !EmailValidator.getInstance().isValid(userEmail)
            || !userEmail.matches("^[A-Za-z0-9][A-Za-z0-9._%+-]*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            return ResponseDto.setFailed((ResponseMessage.INVALID_USER_EMAIL));
        }

        if (!userProfileImageUrl.matches("\\.(jpg|jpeg|png|gif|bmp|webp)$")) {
            return ResponseDto.setFailed(ResponseMessage.INVALID_USER_PROFILE);
        }

        // 2. 중복 체크 //
        if (userRepository.existsByUserId(userId)) {
            return ResponseDto.setFailed(ResponseMessage.EXIST_USER_ID);
        }

        if (userRepository.existsByUserNickName(userNickName)) {
            return ResponseDto.setFailed(ResponseMessage.EXIST_USER_NICKNAME);
        }

        if (userRepository.existsBuUserPhone(userPhone)) {
            return ResponseDto.setFailed(ResponseMessage.EXIST_USER_PHONE);
        }

        if (userRepository.existsByUserEmail(userEmail)) {
            return ResponseDto.setFailed(ResponseMessage.EXIST_USER_EMAIL);
        }

        try {
            String encodedUserPassword = bCryptpasswordEncoder.encode(userPassword);
            User user = User.builder()
                    .userName(userName)
                    .userId(userId)
                    .userPassword(encodedUserPassword)
                    .userNickName(userNickName)
                    .userPhone(userPhone)
                    .userAddress(userAddress)
                    .userAddressDetail(userAddressDetail)
                    .userEmail(userEmail)
                    .userProfileImageUrl(userProfileImageUrl)
                    .build();

            userRepository.save(user);

            data = new SignUpResponseDto(user);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<LoginResponseDto> login(LoginRequestDto dto) {
        String userId = dto.getUserId();
        String userPassword = dto.getUserPassword();

        LoginResponseDto data = null;

        // 1. 유효성 검사 //
        if (userId == null || userId.isEmpty()) {
            return ResponseDto.setFailed(ResponseMessage.VALIDATION_FAIL);
        }

        if (userPassword == null || userPassword.isEmpty()) {
            return ResponseDto.setFailed(ResponseMessage.VALIDATION_FAIL);
        }

        try {
            User user = userRepository.findByUserId(userId)
                    .orElse(null);

            if (user == null) {
                return ResponseDto.setFailed(ResponseMessage.VALIDATION_FAIL);
            }

            if (!bCryptpasswordEncoder.matches(userPassword, user.getUserPassword())) {
                return ResponseDto.setFailed((ResponseMessage.NOT_MATCH_PASSWORD));
            }

            String token = jwtProvider.generateJwtToken(userId);
            int exprTime = jwtProvider.getExpiration();

            data = new LoginResponseDto(user, token, exprTime);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }
}
