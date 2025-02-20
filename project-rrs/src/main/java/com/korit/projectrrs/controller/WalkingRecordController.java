package com.korit.projectrrs.controller;

import com.korit.projectrrs.common.constant.ApiMappingPattern;
import com.korit.projectrrs.dto.ResponseDto;
import com.korit.projectrrs.dto.walkingRecord.request.UpdateWalkingRecordRequestDto;
import com.korit.projectrrs.dto.walkingRecord.request.WalkingRecordRequestDto;
import com.korit.projectrrs.dto.walkingRecord.response.WalkingRecordListResponseDto;
import com.korit.projectrrs.dto.walkingRecord.response.WalkingRecordResponseDto;
import com.korit.projectrrs.security.PrincipalUser;
import com.korit.projectrrs.service.WalkingRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.WALKING_RECORD)
@RequiredArgsConstructor
public class WalkingRecordController {
    private final WalkingRecordService walkingRecordService;

    private static final String WALKING_RECORD_POST= "/{petId}";
    private static final String WALKING_RECORD_GET_LIST = "/{petId}";
    private static final String WALKING_RECORD_GET_BY_ID = "/{petId}/{walkingRecordId}";
    private static final String WALKING_RECORD_PUT = "/{petId}/{walkingRecordId}";
    private static final String WALKING_RECORD_DELETE = "/{petId}/{walkingRecordId}";

    @PostMapping(WALKING_RECORD_POST)
    public ResponseEntity<ResponseDto<WalkingRecordResponseDto>> createWalkingRecord(
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable Long petId,
            @Valid @ModelAttribute WalkingRecordRequestDto dto
    ) {
        Long userId = principalUser.getUser().getUserId();
        ResponseDto<WalkingRecordResponseDto> response = walkingRecordService.createWalkingRecord(userId, petId, dto);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping(WALKING_RECORD_GET_LIST)
    public ResponseEntity<ResponseDto<List<WalkingRecordListResponseDto>>> getWalkingRecordList(
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable Long petId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        Long userId = principalUser.getUser().getUserId();
        ResponseDto<List<WalkingRecordListResponseDto>> response = walkingRecordService.getWalkingRecordList(userId, petId, date);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping(WALKING_RECORD_GET_BY_ID)
    public ResponseEntity<ResponseDto<WalkingRecordResponseDto>> getWalkingRecordInfo(
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable Long petId,
            @PathVariable Long walkingRecordId
    ) {
        Long userId = principalUser.getUser().getUserId();
        ResponseDto<WalkingRecordResponseDto> response = walkingRecordService.getWalkingRecordInfo(userId, petId, walkingRecordId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(response);
    }

    @PutMapping(WALKING_RECORD_PUT)
    public ResponseEntity<ResponseDto<WalkingRecordResponseDto>> updateWalkingRecord(
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable Long petId,
            @PathVariable Long walkingRecordId,
            @Valid @ModelAttribute UpdateWalkingRecordRequestDto dto
    ) {
        Long userId = principalUser.getUser().getUserId();
        ResponseDto<WalkingRecordResponseDto> response = walkingRecordService.updateWalkingRecord(userId, petId, walkingRecordId, dto);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(response);
    }

    @DeleteMapping(WALKING_RECORD_DELETE)
    public ResponseEntity<ResponseDto<Void>> deleteWalkingRecord(
            @AuthenticationPrincipal PrincipalUser principalUser,
            @PathVariable Long petId,
            @PathVariable Long walkingRecordId
    ) {
        Long userId = principalUser.getUser().getUserId();
        ResponseDto<Void> response = walkingRecordService.deleteWalkingRecord(userId, petId, walkingRecordId);
        HttpStatus status = response.isResult() ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<WalkingRecordListResponseDto>>> getWalkingRecordAll(
            @AuthenticationPrincipal PrincipalUser principalUser
    ) {
        Long userId = principalUser.getUser().getUserId();
        ResponseDto<List<WalkingRecordListResponseDto>> response = walkingRecordService.getWalkingRecordAll(userId);
        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).body(response);
    }
}