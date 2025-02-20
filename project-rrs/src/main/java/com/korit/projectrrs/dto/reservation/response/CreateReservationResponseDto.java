package com.korit.projectrrs.dto.reservation.response;

import com.korit.projectrrs.dto.pet.response.PetReservationDto;
import com.korit.projectrrs.entity.Reservation;
import com.korit.projectrrs.entity.ReservationStatus;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
public class CreateReservationResponseDto {
    private GetChosenProviderInfoResponseDto providerInfo;
    private Long reservationId;
    private LocalDate reservationStartDate;
    private LocalDate reservationEndDate;
    private List<PetReservationDto> pets;
    private String phone;
    private String reservationMemo;
    private ReservationStatus reservationStatus;

    public CreateReservationResponseDto(Reservation reservation, double avgReviewScore){
        this.providerInfo = new GetChosenProviderInfoResponseDto(reservation.getProvider(), avgReviewScore);
        this.reservationId = reservation.getReservationId();
        this.reservationStartDate = reservation.getReservationStartDate();
        this.reservationEndDate = reservation.getReservationEndDate();
        this.pets = reservation.getUser().getPet().stream()
                .map(pet -> new PetReservationDto(pet))
                .collect(Collectors.toList());
        this.phone = reservation.getUser().getPhone();
        this.reservationMemo = reservation.getReservationMemo();
    }
}