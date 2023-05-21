package com.job.back.service.implementation;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.job.back.common.constant.ResponseMessage;
import com.job.back.dto.request.company.CompanyInfoDto;
import com.job.back.dto.request.company.PatchCompanyProfileDto;
import com.job.back.dto.request.company.ValidateCompanyEmailDto;
import com.job.back.dto.request.company.ValidateCompanyTelNumberDto;
import com.job.back.dto.response.ResponseDto;
import com.job.back.dto.response.company.GetCompanyListMainResponseDto;
import com.job.back.dto.response.company.GetCompanyResponseDto;
import com.job.back.dto.response.company.CompanyInfoResponseDto;
import com.job.back.dto.response.company.GetCompanyInfoResponseDto;
import com.job.back.dto.response.company.ListUpApplicantResponseDto;
import com.job.back.dto.response.company.PatchCompanyProfileResponseDto;
import com.job.back.dto.response.company.ValidateCompanyEmailResponseDto;
import com.job.back.dto.response.company.ValidateCompanyTelNumberResponseDto;
import com.job.back.entity.ApplicantEntity;
import com.job.back.entity.CompanyEntity;
import com.job.back.entity.CompanyInformationEntity;
import com.job.back.repository.ApplicantRepositroy;
import com.job.back.repository.CompanyInformationRepository;
import com.job.back.repository.CompanyReposiotry;
import com.job.back.service.CompanyService;

@Service
public class CompanyServiceImplements implements CompanyService {
    @Autowired 
    CompanyReposiotry companyRepository;
    @Autowired
    ApplicantRepositroy applicantRepositroy;
    @Autowired
    CompanyInformationRepository companyInformationRepository;

     

   
    @Override
    public ResponseDto<ValidateCompanyEmailResponseDto> validateCompanyEmail(ValidateCompanyEmailDto dto) {
        ValidateCompanyEmailResponseDto data = null;

        String companyEmail = dto.getCompanyEmail();

        try {
            boolean hasEmail = companyRepository.existsByCompanyEmail(companyEmail);
            data = new ValidateCompanyEmailResponseDto(!hasEmail);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
        
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);

    }

    @Override
    public ResponseDto<ValidateCompanyTelNumberResponseDto> validateCompanyTelNumber(ValidateCompanyTelNumberDto dto) {
        ValidateCompanyTelNumberResponseDto data = null;

        String companyTelNumber = dto.getCompanyTelNumber();

        try {
            boolean hasTelNumber = companyRepository.existsByCompanyTelNumber(companyTelNumber);
            data = new ValidateCompanyTelNumberResponseDto(!hasTelNumber);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
        
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
        
    }
    
    @Override
    public ResponseDto<GetCompanyResponseDto> getCompany(String companyEmail) {
        GetCompanyResponseDto data = null;
        
        try {
            CompanyEntity companyEntity = companyRepository.findByCompanyEmail(companyEmail);
            if(companyEntity == null) return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_COMPANY);

            data = new GetCompanyResponseDto(companyEntity);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);

    }

    @Override
    public ResponseDto<ListUpApplicantResponseDto> ListUpApplicant(String companyTelNumber){

        ListUpApplicantResponseDto data =null;

        try{
            

            List<ApplicantEntity> applicant_List = applicantRepositroy.findByApplicantCompanyTelNumber(companyTelNumber);




            data = new ListUpApplicantResponseDto(applicant_List);
            

        }catch(Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);

    }

    @Override
    public ResponseDto<PatchCompanyProfileResponseDto> patchCompanyProfile(String companyEmail,PatchCompanyProfileDto dto){

        PatchCompanyProfileResponseDto data = null;

        try{

            CompanyEntity companyEntity = companyRepository.findByCompanyEmail(companyEmail);
            if(companyEntity == null) return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_COMPANY);
            companyEntity.setCompanyProfileUrl(dto.getCompanyProfileUrl());
            companyRepository.save(companyEntity);
            
            data = new PatchCompanyProfileResponseDto(companyEntity);
        
        }catch(Exception exception){
            exception.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }

    @Override
    public ResponseDto<GetCompanyListMainResponseDto[]> getCompanyListMain(String companyEmail){  
        
        GetCompanyListMainResponseDto[] data = null;

        try{

            List<CompanyEntity> companyEntities = companyRepository.findAll();
            data = new GetCompanyListMainResponseDto[companyEntities.size()];

        for (int i = 0; i < companyEntities.size(); i++) {

            CompanyEntity companyEntity = companyEntities.get(i);
            GetCompanyListMainResponseDto responseDto = new GetCompanyListMainResponseDto();

            responseDto.setCompanyAddress(companyEntity.getCompanyAddress());
            responseDto.setCompanyCategory(companyEntity.getCompanyCategory());
            responseDto.setCompanyName(companyEntity.getCompanyName());
            responseDto.setCompanyPassword(companyEntity.getCompanyPassword());
            responseDto.setCompanyProfileUrl(companyEntity.getCompanyProfileUrl());
            responseDto.setCompanyTelNumber(companyEntity.getCompanyTelNumber());

            data[i] = responseDto;
        }
        }catch(Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);     
    }

    @Override
    public ResponseDto<CompanyInfoResponseDto>insertCompanyInfo( CompanyInfoDto requestBody) {
        CompanyInfoResponseDto data = null;

        CompanyInformationEntity companyInformationEntity = new CompanyInformationEntity(requestBody);

        try {
            companyInformationRepository.save(companyInformationEntity);
 
            data = new CompanyInfoResponseDto(true);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS,data);
    }

    @Override
    public ResponseDto<CompanyInfoResponseDto> updataCompanyInfo(CompanyInfoDto requestBody) {
        CompanyInfoResponseDto data = null;

        CompanyInformationEntity companyInformationEntity = new CompanyInformationEntity(requestBody);

        try {
            companyInformationEntity.patchCompany(requestBody); 
            
            companyInformationRepository.save(companyInformationEntity);
 
            data = new CompanyInfoResponseDto(true);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }

        return ResponseDto.setSuccess(ResponseMessage.SUCCESS,data);
    }

    @Override
    public ResponseDto<GetCompanyInfoResponseDto> getComapnyInfo( String compantTelNumber) {
        
        GetCompanyInfoResponseDto data = null;

        try{
            CompanyInformationEntity companyInformationEntity = companyInformationRepository.findByCompanyTelNumber(compantTelNumber);
            if(companyInformationEntity == null) return ResponseDto.setFailed(ResponseMessage.NOT_EXIST_COMPANY);

            companyInformationRepository.save(companyInformationEntity);

            data = new GetCompanyInfoResponseDto(companyInformationEntity);

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed(ResponseMessage.DATABASE_ERROR);
        }
        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
    }
}
