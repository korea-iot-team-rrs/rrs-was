package com.korit.projectrrs.controller;

import com.korit.projectrrs.common.constant.ApiMappingPattern;
import com.korit.projectrrs.dto.ResponseDto;
import com.korit.projectrrs.dto.provider.request.ProviderRequestDto;
import com.korit.projectrrs.dto.provider.response.GetOneProviderInfoResponseDto;
import com.korit.projectrrs.dto.provider.response.ProviderResponseDto;
import com.korit.projectrrs.security.PrincipalUser;
import com.korit.projectrrs.service.ProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.PROVIDER)
@RequiredArgsConstructor
public class ProviderController {
    private final ProviderService providerService;

    private static final String PROVIDER_PUT = "/me";
    private static final String PROVIDER_GET = "/me";
    private static final String ONE_PROVIDER_INFO = "/{providerId}";

    @PutMapping(PROVIDER_PUT)
    public ResponseEntity<ResponseDto<ProviderResponseDto>> updateProvider(
            @AuthenticationPrincipal PrincipalUser principalUser,
            @RequestBody ProviderRequestDto dto
    ) {
        Long userId = principalUser.getUser().getUserId();
        ResponseDto<ProviderResponseDto> response = providerService.updateProvider(userId, dto);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping(PROVIDER_GET)
    public ResponseEntity<ResponseDto<ProviderResponseDto>> getProviderInfo(
            @AuthenticationPrincipal PrincipalUser principalUser
    ) {
        Long userId = principalUser.getUser().getUserId();
        ResponseDto<ProviderResponseDto> response = providerService.getProviderInfo(userId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping(ONE_PROVIDER_INFO)
    public ResponseEntity<ResponseDto<GetOneProviderInfoResponseDto>> getOneProviderInfo (
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable Long providerId
    ){
        ResponseDto<GetOneProviderInfoResponseDto> response = providerService.getOneProviderInfoById(providerId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(response);
    }
}