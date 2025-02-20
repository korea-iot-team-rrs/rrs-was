package com.korit.projectrrs.controller;

import com.korit.projectrrs.common.constant.ApiMappingPattern;
import com.korit.projectrrs.dto.ResponseDto;
import com.korit.projectrrs.dto.role.requestDto.RoleRequestDto;
import com.korit.projectrrs.dto.role.responseDto.RoleResponseDto;
import com.korit.projectrrs.security.PrincipalUser;
import com.korit.projectrrs.service.implement.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.PROVIDER)
@RequiredArgsConstructor
public class RoleController {
    private final RoleServiceImpl roleService;

    private static final String ROLE_PUT = "/role/me";
    private static final String ROLE_GET = "/role/me";

    @PutMapping(ROLE_PUT)
    public ResponseEntity<ResponseDto<RoleResponseDto>> updateProviderRole(
            @AuthenticationPrincipal PrincipalUser principalUser,
            @RequestBody RoleRequestDto dto
    ) {
        Long userId = principalUser.getUser().getUserId();
        ResponseDto<RoleResponseDto> response = roleService.updateProviderRole(userId, dto);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping(ROLE_GET)
    public ResponseEntity<ResponseDto<RoleResponseDto>> getProviderRole(
            @AuthenticationPrincipal PrincipalUser principalUser
    ) {
        Long userId = principalUser.getUser().getUserId();
        ResponseDto<RoleResponseDto> response = roleService.getProviderRole(userId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(response);
    }
}