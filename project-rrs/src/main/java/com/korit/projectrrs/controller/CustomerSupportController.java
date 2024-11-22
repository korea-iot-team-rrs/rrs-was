package com.korit.projectrrs.controller;

import com.korit.projectrrs.common.ApiMappingPattern;
import com.korit.projectrrs.dto.ResponseDto;
import com.korit.projectrrs.dto.customerSupportController.request.CustomerSupportCreateRequestDto;
import com.korit.projectrrs.dto.customerSupportController.request.CustomerSupportUpdateRequestDto;
import com.korit.projectrrs.dto.customerSupportController.response.CustomerSupportCreateResponseDto;
import com.korit.projectrrs.dto.customerSupportController.response.CustomerSupportGetResponseDto;
import com.korit.projectrrs.dto.customerSupportController.response.CustomerSupportUpdateResponseDto;
import com.korit.projectrrs.repositoiry.CustomerSupportRepository;
import com.korit.projectrrs.service.CustomerSupportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.CUSTOMER_SUPPORT)
@RequiredArgsConstructor
public class CustomerSupportController {
//    private final CustomerSupportService customerSupportService;
//
//    private final String CUSTOMER_SUPPORT_GET = "/{”customer_support_Id”}";
//    private final String CUSTOMER_SUPPORT_UPDATE = "/{”customer_support_Id”}";
//    private final String CUSTOMER_SUPPORT_DELETE = "/{”customer_support_Id”}";
//
//    @PostMapping
//    private ResponseEntity<ResponseDto<CustomerSupportCreateResponseDto>> createCustomerSupport (
//            @AuthenticationPrincipal String userId,
//            @RequestBody CustomerSupportCreateRequestDto dto
//            ) {
//        ResponseDto<CustomerSupportCreateResponseDto> response = customerSupportService.createCustomerSupport(dto);
//        HttpStatus status = response.isResult()? HttpStatus.OK : HttpStatus.BAD_REQUEST;
//        return ResponseEntity.status(status).body(response);
//    }
//
//    @GetMapping
//    private ResponseEntity<ResponseDto<CustomerSupportGetResponseDto>> getCustomerSupportByUserId (
//            @AuthenticationPrincipal String userId,
//            @PathVariable Long customerSupportId
//    ) {
//        ResponseDto<CustomerSupportGetResponseDto> response = customerSupportService.getCustomerSupportByUserId(userId, customerSupportId);
//        HttpStatus status = response.isResult()? HttpStatus.OK : HttpStatus.BAD_REQUEST;
//        return ResponseEntity.status(status).body(response);
//    }
//
//    @GetMapping
//    private ResponseEntity<ResponseDto<List<CustomerSupportGetResponseDto>>> getAllCustomerSupportByUserId (
//            @AuthenticationPrincipal String userId,
//            @PathVariable Long customerSupportId
//    ) {
//        ResponseDto<List<CustomerSupportGetResponseDto>> response = customerSupportService.getAllCustomerSupportByUserId(userId, customerSupportId);
//        HttpStatus status = response.isResult()? HttpStatus.OK : HttpStatus.BAD_REQUEST;
//        return ResponseEntity.status(status).body(response);
//    }
//
//    @PutMapping
//    private ResponseEntity<ResponseDto<CustomerSupportUpdateResponseDto>> createCustomerSupport (
//            @AuthenticationPrincipal String userId,
//            @PathVariable Long customerSupportId,
//            @RequestBody CustomerSupportUpdateRequestDto dto
//    ) {
//        ResponseDto<CustomerSupportUpdateResponseDto> response = customerSupportService.updateCustomerSupport(userId, customerSupportId, dto);
//        HttpStatus status = response.isResult()? HttpStatus.OK : HttpStatus.BAD_REQUEST;
//        return ResponseEntity.status(status).body(response);
//    }
//
//    @DeleteMapping
//    private ResponseEntity<ResponseDto<Void>> deleteCustomerSupport(
//            @AuthenticationPrincipal String userId,
//            @PathVariable Long customerSupportId
//    ) {
//        ResponseDto<Void> response = customerSupportService.deleteCustomerService(customerSupportId);
//        HttpStatus status = response.isResult() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
//        return ResponseEntity.status(status).body(response);
//    }
}
