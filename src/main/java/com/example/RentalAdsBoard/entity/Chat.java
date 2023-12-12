package com.example.RentalAdsBoard.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "chat_id")
    private Integer chatId;

    @Column(name = "user_from")
    String userFrom;

    @Column(name = "user_to")
    String userTo;

    @Column(name = "message")
    String message;
}
