package com.example.RentalAdsBoard.service.serviceImpl;

import com.example.RentalAdsBoard.controller.exception.DataBaseException;
import com.example.RentalAdsBoard.dao.BaseDao;
import com.example.RentalAdsBoard.dao.ChatDao;
import com.example.RentalAdsBoard.dao.UserDao;
import com.example.RentalAdsBoard.entity.User;
import com.example.RentalAdsBoard.service.ChatService;
import com.example.RentalAdsBoard.util.DataUtil;
import com.example.RentalAdsBoard.vo.ChatVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import com.example.RentalAdsBoard.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.RentalAdsBoard.entity.Chat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    BaseDao<Chat> baseDao;
    @Autowired
    ChatDao chatDao;
    @Autowired
    UserDao userDao;

    @Override
    public ResultVo getHistoryList(String username, String targetName) throws Exception {
        List<ChatVo> voList = new ArrayList<>();
        try {
            List<Chat> list=chatDao.getHistoryMessageList(username, targetName);
            DataUtil.sortById(list);

            for (Chat c : list) {
                ChatVo chatVo = new ChatVo();
                chatVo.setChatVo(c);
                voList.add(chatVo);
                //update the state of the chat to "has been read"
                c.setRead(true);
                baseDao.update(c);
            }
        } catch (Exception e) {
            throw new DataBaseException("get history chats failed");
        }
        return new ResultVo().success(voList);
    }

    @Override
    public ResultVo getHistoryChatters(String username) throws DataBaseException {
        List<UserVo> voList=new ArrayList<>();
        try {
            HashSet<User> set=new HashSet<>();

            List<Chat> listSend = chatDao.getHistorySendChats(username);
            for (Chat c:listSend){
                User cur=userDao.getByUsername(c.getUserTo());
                if (set.add(cur)){
                    voList.add(new UserVo().setUserVo(cur));
                }
            }

            List<Chat> listReceive = chatDao.getHistoryReceiveChats(username);
            for (Chat c:listReceive){
                User cur=userDao.getByUsername(c.getUserFrom());
                if (set.add(cur)){
                    voList.add(new UserVo().setUserVo(cur));
                }
            }

        }catch (Exception e){
            throw new DataBaseException("get history chatters failed");
        }
        return new ResultVo().success(voList);
    }
    @Override
    public ResultVo getLatestChat(String username, String targetUsername) throws DataBaseException {
        ChatVo chatVo=new ChatVo();
        try {
            Chat chat=chatDao.getLatestCHat(username,targetUsername);
            chatVo.setChatVo(chat);

        } catch (Exception e) {
            throw new DataBaseException("get last chat failed");
        }
        return new ResultVo().success(chatVo);
    }

    @Override
    public void saveMessage(ChatVo chatVo) throws DataBaseException {
        try {
            Chat chat=new Chat();

            chat.setUserFrom(chatVo.getUserFrom());
            chat.setUserTo(chatVo.getUserTo());
            chat.setMessage(chatVo.getMessage());
            chat.setRead(chatVo.isRead());

            baseDao.save(chat);
        }catch (Exception e){
            throw new DataBaseException("save message failed");
        }
        new ResultVo().success();
    }
}
