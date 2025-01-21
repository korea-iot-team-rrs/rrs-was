package com.korit.projectrrs.controller;

import com.korit.projectrrs.common.constant.ApiMappingPattern;
import com.korit.projectrrs.dto.ResponseDto;
import com.korit.projectrrs.dto.communityLike.response.CommunityLikeResponseDto;
import com.korit.projectrrs.security.PrincipalUser;
import com.korit.projectrrs.service.CommunityLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(ApiMappingPattern.COMMUNITY_LIKE)
@RequiredArgsConstructor
public class CommunityLikeController {

    private final CommunityLikeService communityLikeService;

    @PostMapping("/{communityId}")
    public ResponseEntity<ResponseDto<Map<String, Object>>> toggleLike(
            @PathVariable Long communityId,
            @AuthenticationPrincipal PrincipalUser principalUser) {

        ResponseDto<Map<String, Object>> response = communityLikeService.toggleLike(principalUser.getUser(), communityId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }

    @GetMapping("/{communityId}/likes")
    public ResponseEntity<ResponseDto<List<CommunityLikeResponseDto>>> getUsersWhoLikedCommunity(
            @PathVariable Long communityId,
            @AuthenticationPrincipal PrincipalUser principalUser) {

        ResponseDto<List<CommunityLikeResponseDto>> response = communityLikeService.getUsersWhoLikedCommunity(communityId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(response, status);
    }
}
