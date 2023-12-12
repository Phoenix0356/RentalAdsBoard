package com.example.RentalAdsBoard.service;

import com.example.RentalAdsBoard.vo.ChatVo;
import com.example.RentalAdsBoard.vo.ResultVo;

public interface ChatService {
    ResultVo getHistoryList(String username, String targetName) throws Exception;

    void saveMessage(ChatVo chatVo);
}
