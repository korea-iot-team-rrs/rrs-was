package com.korit.projectrrs.service.implement;

import com.korit.projectrrs.common.constant.ResponseMessage;
import com.korit.projectrrs.dto.ResponseDto;
import com.korit.projectrrs.dto.healthrecord.request.HealthRecordCreateRequestDto;
import com.korit.projectrrs.dto.healthrecord.request.HealthRecordUpdateRequestDto;
import com.korit.projectrrs.dto.healthrecord.response.HealthRecordAllResponseDto;
import com.korit.projectrrs.dto.healthrecord.response.HealthRecordResponseDto;
import com.korit.projectrrs.entity.HealthRecord;
import com.korit.projectrrs.entity.HealthRecordAttachment;
import com.korit.projectrrs.entity.Pet;
import com.korit.projectrrs.repositoiry.HealthRecordAttachmentRepository;
import com.korit.projectrrs.repositoiry.HealthRecordRepository;
import com.korit.projectrrs.repositoiry.PetRepository;
import com.korit.projectrrs.service.FileService;
import com.korit.projectrrs.service.HealthRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HealthRecordServiceImpl implements HealthRecordService {

    private final HealthRecordRepository healthRecordRepository;
    private final PetRepository petRepository;
    private final HealthRecordAttachmentRepository healthRecordAttachmentRepository;
    private final FileService fileService;

    @Override
    @Transactional
    public ResponseDto<HealthRecordResponseDto> createHealthRecord(Long userId, Long petId, HealthRecordCreateRequestDto requestDto) {
        String abnormalSymptoms = requestDto.getAbnormalSymptoms();
        Double weight = requestDto.getWeight();
        short petAge = requestDto.getPetAge();

        if (weight == null || weight <= 0) {
            return ResponseDto.setFailed(ResponseMessage.INVALID_WEIGHT);
        }
        if (petAge <= 0) {
            return ResponseDto.setFailed(ResponseMessage.INVALID_PET_AGE);
        }

        try {
            Pet pet = petRepository.findById(petId)
                    .orElseThrow(() -> new RuntimeException(ResponseMessage.PET_INFO_NOT_FOUND));
            if (!pet.getUser().getUserId().equals(userId)) {
                return ResponseDto.setFailed(ResponseMessage.NOT_AUTHORIZED);
            }

            HealthRecord healthRecord = HealthRecord.builder()
                    .pet(pet)
                    .weight(weight)
                    .petAge(petAge)
                    .abnormalSymptoms(abnormalSymptoms)
                    .memo(requestDto.getMemo())
                    .createdAt(requestDto.getCreatedAt() != null ? requestDto.getCreatedAt() : LocalDate.now())
                    .build();
            healthRecordRepository.save(healthRecord);

            List<MultipartFile> files = requestDto.getFiles();
            List<String> fileNames = new ArrayList<>();
            if (files != null && !files.isEmpty()) {
                for (MultipartFile file : files) {
                    String fileName = fileService.uploadFile(file, "health-record");
                    if (fileName != null) {
                        fileNames.add(fileName);
                        HealthRecordAttachment attachment = new HealthRecordAttachment();
                        attachment.setHealthRecord(healthRecord);
                        attachment.setHealthRecordAttachmentFile(fileName);
                        healthRecordAttachmentRepository.save(attachment);
                    }
                }
            }

            HealthRecordResponseDto responseDto = new HealthRecordResponseDto(healthRecord);
            responseDto.setAttachments(fileNames);

            return ResponseDto.setSuccess(ResponseMessage.RECORD_CREATED_SUCCESSFULLY, responseDto);
        } catch (Exception e) {
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseDto<HealthRecordResponseDto> updateHealthRecord(Long userId, Long petId, Long healthRecordId, HealthRecordUpdateRequestDto requestDto) {
        System.out.println(userId);
        System.out.println(healthRecordId);
        System.out.println(petId);
        return healthRecordRepository.findByHealthRecordIdAndPet_PetIdAndPet_User_UserId(healthRecordId, petId, userId)
                .map(existingRecord -> {

                    existingRecord.setWeight(requestDto.getWeight());
                    existingRecord.setPetAge(requestDto.getPetAge());
                    existingRecord.setAbnormalSymptoms(requestDto.getAbnormalSymptoms());
                    existingRecord.setMemo(requestDto.getMemo());

                    if (requestDto.getFiles() != null && !requestDto.getFiles().isEmpty()) {
                        List<HealthRecordAttachment> newAttachments = new ArrayList<>();
                        for (MultipartFile file : requestDto.getFiles()) {
                            String filePath = fileService.uploadFile(file, "health-record");
                            if (filePath != null) {
                                HealthRecordAttachment newAttachment = HealthRecordAttachment.builder()
                                        .healthRecord(existingRecord)
                                        .healthRecordAttachmentFile(filePath)
                                        .build();
                                healthRecordAttachmentRepository.save(newAttachment);
                                newAttachments.add(newAttachment);
                            }
                        }

                        existingRecord.getAttachments().addAll(newAttachments);
                    }

                    healthRecordRepository.save(existingRecord);

                    HealthRecordResponseDto responseDto = new HealthRecordResponseDto(existingRecord);
                    responseDto.setAttachments(existingRecord.getAttachments().stream()
                            .map(HealthRecordAttachment::getHealthRecordAttachmentFile)
                            .collect(Collectors.toList()));

                    return ResponseDto.setSuccess(ResponseMessage.RECORD_UPDATED_SUCCESSFULLY, responseDto);
                })
                .orElseGet(() -> ResponseDto.setFailed(ResponseMessage.RECORD_NOT_FOUND));
    }

    @Override
    @Transactional
    public ResponseDto<Void> deleteHealthRecord(Long userId, Long petId, Long healthRecordId) {
        try {
            HealthRecord record = healthRecordRepository.findById(healthRecordId)
                    .orElseThrow(() -> new RuntimeException(ResponseMessage.RECORD_NOT_FOUND));
            if (!record.getPet().getUser().getUserId().equals(userId)) {
                return ResponseDto.setFailed(ResponseMessage.NOT_AUTHORIZED);
            }

            List<HealthRecordAttachment> attachments = healthRecordAttachmentRepository.findByHealthRecordId(healthRecordId);
            for (HealthRecordAttachment attachment : attachments) {
                fileService.removeFile(attachment.getHealthRecordAttachmentFile());
                healthRecordAttachmentRepository.delete(attachment);
            }

            healthRecordRepository.delete(record);
            return ResponseDto.setSuccess(ResponseMessage.RECORD_DELETED_SUCCESSFULLY, null);
        } catch (Exception e) {
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<List<HealthRecordAllResponseDto>> getAllHealthRecords(Long userId, Long petId) {
        List<HealthRecord> records = healthRecordRepository.findAllByPet_User_UserIdAndPet_PetId(userId, petId);

        List<HealthRecordAllResponseDto> dtos = records.stream()
                .map(HealthRecordAllResponseDto::new)
                .collect(Collectors.toList());

        return ResponseDto.setSuccess(ResponseMessage.RECORD_FETCHED_SUCCESSFULLY, dtos);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<List<HealthRecordAllResponseDto>> getAllHealthRecordsByUserId(Long userId) {

        List<HealthRecord> records = healthRecordRepository.findAllByPet_User_UserId(userId);

        List<HealthRecordAllResponseDto> dtos = records.stream()
                .map(HealthRecordAllResponseDto::new)
                .collect(Collectors.toList());

        return ResponseDto.setSuccess(ResponseMessage.RECORD_FETCHED_SUCCESSFULLY, dtos);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<HealthRecordResponseDto> getHealthRecord(Long userId, Long petId, Long healthRecordId) {
        try {
            HealthRecord record = healthRecordRepository.findById(healthRecordId)
                    .orElseThrow(() -> new RuntimeException(ResponseMessage.RECORD_NOT_FOUND));
            if (!record.getPet().getUser().getUserId().equals(userId)) {
                return ResponseDto.setFailed(ResponseMessage.NOT_AUTHORIZED);
            }

            HealthRecordResponseDto responseDto = new HealthRecordResponseDto(record);
            List<String> fileNames = record.getAttachments().stream()
                    .map(HealthRecordAttachment::getHealthRecordAttachmentFile)
                    .collect(Collectors.toList());
            responseDto.setAttachments(fileNames);

            return ResponseDto.setSuccess(ResponseMessage.RECORD_FETCHED_SUCCESSFULLY, responseDto);
        } catch (Exception e) {
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
    }
}
