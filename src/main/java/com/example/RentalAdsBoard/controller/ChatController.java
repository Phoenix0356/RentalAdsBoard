package com.example.RentalAdsBoard.controller;

import com.example.RentalAdsBoard.controller.exception.DataBaseException;
import com.example.RentalAdsBoard.service.ChatService;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ChatController {
    @Autowired
    ChatService chatService;
    @GetMapping("/chat/history/message")
    public ResultVo getHistoryList(HttpServletRequest request,
                                   @RequestParam("user_to") String userTo) throws Exception {
        Integer userId=(Integer) request.getAttribute("userId");
        return chatService.getHistoryList(userId,userTo);
    }

    @GetMapping("/chat/history/chatter")
    public ResultVo getHistoryChatter(HttpServletRequest request) throws DataBaseException {
        Integer userId=(Integer) request.getAttribute("userId");
        return chatService.getHistoryChatters(userId);
    }

    @GetMapping("/chat/message/last")
    public ResultVo getLatestChat(HttpServletRequest request,
                                  @RequestParam("user_to") String userTo) throws Exception {
        Integer userId=(Integer) request.getAttribute("userId");
        return chatService.getLatestChat(userId,userTo);
    }
}
