package com.example.RentalAdsBoard.controller;

import com.example.RentalAdsBoard.service.ChatService;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
    @Autowired
    ChatService chatService;
    @GetMapping("/chat/get/history")
    public ResultVo getHistoryList(@RequestParam("username")String username,
                                   @RequestParam("user_to") String targetUsername) throws Exception {
        return chatService.getHistoryList(username,targetUsername);
    }
}
