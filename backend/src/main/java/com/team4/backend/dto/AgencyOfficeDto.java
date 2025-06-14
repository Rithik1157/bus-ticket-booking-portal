package com.team4.backend.dto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;


@Value
public class AgencyOfficeDto implements Serializable {
    Integer id;
    @Size(max = 100)
    String officeMail;
    @Size(max = 50)
    String officeContactPersonName;
    @Size(max = 10)
    String officeContactNumber;
}