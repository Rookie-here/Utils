package org.example.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    DAO_DATA_NOT_FOUND("2002","Data not found"),
    DATE_FORMAT_ERROR("2003","Date format error"),
    AES_IV_ERROR("2004","aes encrypt error iv input"),
    AES_KEY_ERROR("2005","aes encrypt error key input"),
    PARAM_ERROR("1001","Invalid parameter ");

    private String errCode;
    private String errMsg;



}
