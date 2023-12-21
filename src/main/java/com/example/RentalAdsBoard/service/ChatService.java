package com.example.RentalAdsBoard.service;

import com.example.RentalAdsBoard.controller.exception.DataBaseException;
import com.example.RentalAdsBoard.vo.ChatVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.stereotype.Service;

@Service
public interface ChatService {
    ResultVo getHistoryList(Integer userId, String targetName) throws Exception;

    ResultVo getHistoryChatters(Integer userId) throws DataBaseException;

    ResultVo getLatestChat(Integer userId, String targetUsername) throws DataBaseException;

    void saveMessage(ChatVo chatVo) throws DataBaseException;
}
