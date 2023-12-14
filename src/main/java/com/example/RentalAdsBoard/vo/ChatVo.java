package com.example.RentalAdsBoard.vo;

import com.example.RentalAdsBoard.entity.Chat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatVo {

    String userFrom;
    String userTo;
    String message;
    boolean isRead;

    public void setChatVo(Chat chat){
        this.userFrom = chat.getUserFrom();
        this.userTo = chat.getUserTo();
        this.message = chat.getMessage();
        this.isRead= chat.isRead();
    }
}
