package com.example.RentalAdsBoard.service;

import com.example.RentalAdsBoard.controller.exception.DataBaseException;
import com.example.RentalAdsBoard.vo.ChatVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.stereotype.Service;

@Service
public interface ChatService {
    ResultVo getHistoryList(String username, String targetName) throws Exception;

    ResultVo getHistoryChatters(String username) throws DataBaseException;

    ResultVo getLatestChat(String username, String targetUsername) throws DataBaseException;

    void saveMessage(ChatVo chatVo) throws DataBaseException;
}
