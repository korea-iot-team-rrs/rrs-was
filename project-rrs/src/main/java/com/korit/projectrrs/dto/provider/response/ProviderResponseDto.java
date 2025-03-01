package com.korit.projectrrs.dto.provider.response;

import com.korit.projectrrs.dto.availableDateOfWeek.responseDto.AvailableDateOfWeekResponseDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class ProviderResponseDto {
    private List<AvailableDateOfWeekResponseDto> availableDate;
    private String providerIntroduction;

    public ProviderResponseDto(List<AvailableDateOfWeekResponseDto> availableDateOfWeek, String providerIntroduction) {
        this.availableDate = availableDateOfWeek;
        this.providerIntroduction = providerIntroduction;
    }
}