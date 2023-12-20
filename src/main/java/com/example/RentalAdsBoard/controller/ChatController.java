package com.example.RentalAdsBoard.controller;

import com.example.RentalAdsBoard.controller.exception.DataBaseException;
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
    @GetMapping("/chat/history/message")
    public ResultVo getHistoryList(@RequestParam("user_from")String userFrom,
                                   @RequestParam("user_to") String userTo) throws Exception {
        return chatService.getHistoryList(userFrom,userTo);
    }

    @GetMapping("/chat/history/chatter")
    public ResultVo getHistoryChatter(@RequestParam("username") String username) throws DataBaseException {
        return chatService.getHistoryChatters(username);
    }

    @GetMapping("/chat/message/last")
    public ResultVo getLatestChat(@RequestParam("user_from")String userFrom,
                                  @RequestParam("user_to") String userTo) throws Exception {
        return chatService.getLatestChat(userFrom,userTo);
    }
}
