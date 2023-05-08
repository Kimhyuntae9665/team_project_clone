package com.job.back.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.job.back.common.constant.ApiPattern;
import com.job.back.dto.Applicant_Content_Dto;
import com.job.back.dto.Applicant_Total_Score_Dto;
import com.job.back.dto.response.ResponseDto;
import com.job.back.dto.response.applicant.ApplicantPercentileResponseDto;
import com.job.back.dto.response.applicant.ApplicantScoreResponseDto;
import com.job.back.service.ApplicantService;

@RestController
@RequestMapping(ApiPattern.APPLICANT)
public class ApplicantController {

    private final String APPLICANT_SCORE_PER_COMPANY = "/score/{company_Tel_Number}";
    private final String APPLICANT_PERCENTILE = "/percentile/{company_Tel_Number}";

    @Autowired
    ApplicantService applicantService;


    @PostMapping(APPLICANT_SCORE_PER_COMPANY)
    public ResponseDto<ApplicantScoreResponseDto> showApplicantTotalScore(
        @AuthenticationPrincipal String email,
        // ! Applicant_Score_Dto에 User의 select_component(final_education,carrer,license)가 담겨서 보내진다
        @Valid @RequestBody Applicant_Content_Dto dto,
        @PathVariable String company_Tel_Number
    ){

        
        ResponseDto<ApplicantScoreResponseDto> response = applicantService.show_Applicant_Total_Score(company_Tel_Number,dto);

        return response;


    }

    @PostMapping(APPLICANT_PERCENTILE)
    public ResponseDto<ApplicantPercentileResponseDto> showApplicantPercentile(
        @AuthenticationPrincipal String email,
        @Valid @RequestBody Applicant_Total_Score_Dto dto,
        @PathVariable String company_Tel_Number
    ){

        ResponseDto<ApplicantPercentileResponseDto> response = applicantService.show_Applicant_Percentile(company_Tel_Number,dto);

        return response;

    }


    
}
