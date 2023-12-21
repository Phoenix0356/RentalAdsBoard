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

import javax.persistence.criteria.CriteriaBuilder;
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
    public ResultVo getHistoryList(Integer userId, String targetUsername) throws Exception {
        List<ChatVo> voList = new ArrayList<>();
        try {
            User userTo=userDao.getByUsername(targetUsername);
            List<Chat> list=chatDao.getHistoryMessageList(userId, userTo.getUserId());
            DataUtil.sortById(list);

            for (Chat c : list) {
                ChatVo chatVo = new ChatVo();
                chatVo.setChatVo(userDao.getById(userId).getUsername(),targetUsername,c);
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
    public ResultVo getHistoryChatters(Integer userId) throws DataBaseException {
        List<UserVo> voList=new ArrayList<>();
        try {
            HashSet<User> set=new HashSet<>();


            List<Chat> listSend = chatDao.getHistorySendChats(userId);
            for (Chat c:listSend){
                User cur=userDao.getById(c.getUserTo());
                if (set.add(cur)){
                    voList.add(new UserVo().setUserVo(cur));
                }
            }

            List<Chat> listReceive = chatDao.getHistoryReceiveChats(userId);
            for (Chat c:listReceive){
                User cur=userDao.getById(c.getUserFrom());
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
    public ResultVo getLatestChat(Integer userId, String targetUsername) throws DataBaseException {
        ChatVo chatVo=new ChatVo();
        try {
            User userTo=userDao.getByUsername(targetUsername);
            Chat chat=chatDao.getLatestCHat(userId,userTo.getUserId());
            chatVo.setChatVo(userDao.getById(userId).getUsername(),targetUsername,chat);

        } catch (Exception e) {
            throw new DataBaseException("get last chat failed");
        }
        return new ResultVo().success(chatVo);
    }

    @Override
    public void saveMessage(ChatVo chatVo) throws DataBaseException {
        try {
            Chat chat=new Chat();

            chat.setUserFrom(userDao.getByUsername(chatVo.getUserFrom()).getUserId());
            chat.setUserTo(userDao.getByUsername(chatVo.getUserTo()).getUserId());
            chat.setMessage(chatVo.getMessage());
            chat.setRead(chatVo.isRead());

            baseDao.save(chat);
        }catch (Exception e){
            throw new DataBaseException("save message failed");
        }
        new ResultVo().success();
    }
}
