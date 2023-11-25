package com.example.RentalAdsBoard.util;

import org.springframework.stereotype.Component;

@Component
public class DataUtil {
    private Integer toInteger(String s){
        return Integer.parseInt(s);
    }
}
