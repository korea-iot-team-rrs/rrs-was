package com.korit.projectrrs.service.implement;

import com.korit.projectrrs.common.ResponseMessage;
import com.korit.projectrrs.dto.ResponseDto;
import com.korit.projectrrs.dto.petProfile.response.PetProfileResponseDto;
import com.korit.projectrrs.dto.walkingRecord.request.UpdateWalkingRecordRequestDto;
import com.korit.projectrrs.dto.walkingRecord.request.WalkingRecordRequestDto;
import com.korit.projectrrs.dto.walkingRecord.response.WalkingRecordListResponseDto;
import com.korit.projectrrs.dto.walkingRecord.response.WalkingRecordResponseDto;
import com.korit.projectrrs.entity.*;
import com.korit.projectrrs.repositoiry.PetProfileRepository;
import com.korit.projectrrs.repositoiry.WalkingRecordAttachmentRepository;
import com.korit.projectrrs.repositoiry.WalkingRecordRepository;
import com.korit.projectrrs.service.WalkingRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalkingRecordServiceImpl implements WalkingRecordService {

    private final PetProfileRepository petProfileRepository;
    private final WalkingRecordRepository walkingRecordRepository;
    private final WalkingRecordAttachmentRepository walkingRecordAttachmentRepository;

    @Override
    public ResponseDto<WalkingRecordResponseDto> createWalkingRecord(String userId, long petProfileId, WalkingRecordRequestDto dto) {
        WalkingRecordWeatherState walkingRecordWeatherState = dto.getWalkingRecordWeatherState() != null
                ? dto.getWalkingRecordWeatherState()
                : WalkingRecordWeatherState.SUNNY;
        Integer walkingRecordDistance = dto.getWalkingRecordDistance();
        Integer hours = dto.getWalkingRecordWalkingHours();
        Integer minutes = dto.getWalkingRecordWalkingMinutes();
        LocalDate walkingRecordCreateAt = dto.getWalkingRecordCreateAt();
        String walkingRecordMemo = dto.getWalkingRecordMemo();
        List<WalkingRecordAttachment> walkingRecordAttachments = dto.getWalkingRecordAttachments();

        WalkingRecordResponseDto data = null;

        List<WalkingRecordWeatherState> validWeatherStates = Arrays.asList(WalkingRecordWeatherState.values());

        if (!validWeatherStates.contains(walkingRecordWeatherState)) {
            return ResponseDto.setFailed(ResponseMessage.INVALID_WALKING_RECORD_WEATHER_STATE);
        }

        if (walkingRecordDistance == null || walkingRecordDistance <= 0) {
            return ResponseDto.setFailed(ResponseMessage.INVALID_WALKING_RECORD_DISTANCE);
        }

        if (minutes == null || minutes < 0 || minutes >= 60) {
            return ResponseDto.setFailed(ResponseMessage.INVALID_WALKING_RECORD_TIME);
        }

        int totalMinutes = (hours != null ? hours : 0) * 60 + minutes;

        if (walkingRecordCreateAt == null) {
            return ResponseDto.setFailed(ResponseMessage.INVALID_WALKING_RECORD_CREATE_AT);
        }

        LocalDate now = LocalDate.now();
        if (walkingRecordCreateAt.isAfter(now)) {
            return ResponseDto.setFailed(ResponseMessage.TIME_IN_FUTURE_NOT_ALLOWED);
        }

        try {
            Optional<PetProfile> optionalPetProfile = petProfileRepository.findPetByUserId(userId, petProfileId);

            if (optionalPetProfile.isEmpty()) {
                return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_PET_ID);
            }

            PetProfile petProfile = optionalPetProfile.get();

            WalkingRecord walkingRecord = WalkingRecord.builder()
                    .petProfile(petProfile)
                    .walkingRecordWeatherState(walkingRecordWeatherState != null ? walkingRecordWeatherState : WalkingRecordWeatherState.SUNNY)
                    .walkingRecordDistance(walkingRecordDistance)
                    .walkingRecordWalkingTime(totalMinutes)
                    .walkingRecordCreateAt(walkingRecordCreateAt)
                    .walkingRecordMemo(walkingRecordMemo)
                    .walkingRecordAttachments(walkingRecordAttachments)
                    .build();

            walkingRecordRepository.save(walkingRecord);

            data = new WalkingRecordResponseDto(walkingRecord);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<List<WalkingRecordListResponseDto>> getWalkingRecordList(String userId, long petProfileId, LocalDate walkingRecordCreateAt) {
        List<WalkingRecordListResponseDto> data = new ArrayList<>();

        try {
            List<WalkingRecord> walkingRecords = walkingRecordRepository.findAllWalkingReccrdByCreateAt(petProfileId, walkingRecordCreateAt);

            if (walkingRecords.isEmpty()) {
                return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_WALKING_RECORD_ID);
            }

            data = walkingRecords.stream()
                    .map(WalkingRecordListResponseDto::new)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<WalkingRecordResponseDto> getWalkingRecord(String userId, long petProfileId, long walkingRecordId) {
        WalkingRecordResponseDto data = null;

        try {
            Optional<WalkingRecord> optionalWalkingRecord = walkingRecordRepository.findWalkingRecordBytProfileIdAndWalkingRecordId(userId, petProfileId, walkingRecordId);

            if (optionalWalkingRecord.isEmpty()) {
                return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_WALKING_RECORD_ID);
            }

            WalkingRecord walkingRecord = optionalWalkingRecord.get();

            data = new WalkingRecordResponseDto(walkingRecord);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<WalkingRecordResponseDto> updateWalkingRecord(String userId, long petProfileId, long walkingRecordId, UpdateWalkingRecordRequestDto dto) {
        WalkingRecordWeatherState walkingRecordWeatherState = dto.getWalkingRecordWeatherState();
        Integer walkingRecordDistance = dto.getWalkingRecordDistance();
        Integer hours = dto.getWalkingRecordWalkingHours();
        Integer minutes = dto.getWalkingRecordWalkingMinutes();
        LocalDate walkingRecordCreateAt = dto.getWalkingRecordCreateAt();
        String walkingRecordMemo = dto.getWalkingRecordMemo();
        List<WalkingRecordAttachment> walkingRecordAttachments = dto.getWalkingRecordAttachments();

        WalkingRecordResponseDto data = null;

        if (walkingRecordDistance != null && walkingRecordDistance <= 0) {
            return ResponseDto.setFailed(ResponseMessage.INVALID_WALKING_RECORD_DISTANCE);
        }

        if (minutes != null && minutes < 0 || minutes >= 60) {
            return ResponseDto.setFailed(ResponseMessage.INVALID_WALKING_RECORD_TIME);
        }

        int totalMinutes = (hours != null ? hours : 0) * 60 + minutes;

        if (walkingRecordCreateAt != null && walkingRecordCreateAt.isAfter(LocalDate.now())) {
            return ResponseDto.setFailed(ResponseMessage.INVALID_PET_BIRTH_DATE);
        }

        try {
            Optional<WalkingRecord> optionalWalkingRecord = walkingRecordRepository.findWalkingRecordBytProfileIdAndWalkingRecordId(userId, petProfileId, walkingRecordId);

            if (optionalWalkingRecord.isEmpty()) {
                return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_WALKING_RECORD_ID);
            }

            WalkingRecord walkingRecord = optionalWalkingRecord.get();

            System.out.println("Received Weather State: " + walkingRecordWeatherState);

            walkingRecord = walkingRecord.toBuilder()
                    .walkingRecordWeatherState(walkingRecordWeatherState != null ? walkingRecordWeatherState : WalkingRecordWeatherState.SUNNY)
                    .walkingRecordDistance(walkingRecordDistance != null ? walkingRecordDistance : walkingRecord.getWalkingRecordDistance())
                    .walkingRecordWalkingTime(totalMinutes)
                    .walkingRecordCreateAt(walkingRecordCreateAt != null ? walkingRecordCreateAt : walkingRecord.getWalkingRecordCreateAt())
                    .walkingRecordMemo(walkingRecordMemo != null ? walkingRecordMemo : walkingRecord.getWalkingRecordMemo())
                    .walkingRecordAttachments(walkingRecordAttachments != null ? walkingRecordAttachments :walkingRecord.getWalkingRecordAttachments())
                    .build();

            walkingRecordRepository.save(walkingRecord);

            data = new WalkingRecordResponseDto(walkingRecord);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<Void> deleteWalkingRecord(String userId, long petProfileId, long walkingRecordId) {
        try {
            Optional<WalkingRecord> optionalWalkingRecord = walkingRecordRepository.findWalkingRecordBytProfileIdAndWalkingRecordId(userId, petProfileId, walkingRecordId);

            if (optionalWalkingRecord.isEmpty()) {
                return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_WALKING_RECORD_ID);
            }

            WalkingRecord walkingRecord = optionalWalkingRecord.get();
            walkingRecordRepository.delete(walkingRecord);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, null);
    }
}
