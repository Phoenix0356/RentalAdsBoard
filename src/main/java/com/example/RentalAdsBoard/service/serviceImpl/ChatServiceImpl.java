package com.example.RentalAdsBoard.service.serviceImpl;

import com.example.RentalAdsBoard.controller.exception.DataBaseException;
import com.example.RentalAdsBoard.dao.BaseDao;
import com.example.RentalAdsBoard.dao.ChatDao;
import com.example.RentalAdsBoard.service.ChatService;
import com.example.RentalAdsBoard.util.DataUtil;
import com.example.RentalAdsBoard.vo.ChatVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.RentalAdsBoard.entity.Chat;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    BaseDao<Chat> baseDao;
    @Autowired
    ChatDao chatDao;

    @Override
    public ResultVo getHistoryList(String username, String targetName) throws Exception {
        List<ChatVo> voList = new ArrayList<>();
        try {
            List<Chat> list = chatDao.getHistoryList(username, targetName);

            DataUtil.sortById(list);

            for (Chat c : list) {
                ChatVo chatVo = new ChatVo();
                chatVo.setChatVo(c);
                voList.add(chatVo);
            }
        } catch (Exception e) {
            throw new DataBaseException("get history chats failed");
        }
        return new ResultVo().success(voList);
    }

    @Override
    public void saveMessage(ChatVo chatVo) throws DataBaseException {
        try {
            Chat chat=new Chat();

            chat.setUserFrom(chatVo.getUserFrom());
            chat.setUserTo(chatVo.getUserTo());
            chat.setMessage(chatVo.getMessage());

            baseDao.save(chat);
        }catch (Exception e){
            throw new DataBaseException("save message failed");
        }
        new ResultVo().success();
    }
}
