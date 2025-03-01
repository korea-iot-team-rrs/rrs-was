package com.korit.projectrrs.service;

import com.korit.projectrrs.dto.ResponseDto;
import com.korit.projectrrs.dto.reservation.request.*;
import com.korit.projectrrs.dto.reservation.response.*;

import java.util.List;
import java.util.Set;

public interface ReservationService {
    ResponseDto<CreateReservationResponseDto> createReservation(Long userId, CreateReservationRequestDto dto);
    ResponseDto<List<GetReservationResponseDto>> getAllReservationByUserId(Long userId);
    ResponseDto<GetReservationResponseDto> getReservationByReservationId(Long userId);
    ResponseDto<UpdateReservationResponseDto> updateReservationByReservationId(Long reservationId,ReservationUpdateRequestDto dto);
    ResponseDto<Set<GetProviderByDateResponseDto>> findProviderByDate(Long userId, GetProviderByDateRequestDto dto);
    ResponseDto<UpdateStatusResponseDto> updateReservationStatus(UpdateStatusRequestDto dto);
    ResponseDto<HasReview> hasReview(Long reservationId);
}