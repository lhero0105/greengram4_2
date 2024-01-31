package com.green.greengram4.openApi.model;

import lombok.Data;

@Data
public class ApartmentTransactionDetailDto {
    private String dealYm; // 언제
    private int lawdCd; // 어느지역
    private int pageNo;
    private int numOfRows;
}
