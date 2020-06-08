package com.example.one.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired(required = false)
    private UserMapper userMapper;

    public void createOrUpdate(User user) {

            // 快速回退到上一个操作页面快捷键： ctrl + alt + 左右键
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);

//        User dbUser =(User) users;
         if(users.size()!=0){

             //更新数据
             User dbUser=users.get(0);
//             dbUser.setGmtModified(System.currentTimeMillis());
//             dbUser.setAvatarUrl(user.getAvatarUrl());
//             dbUser.setName(user.getName());
//             dbUser.setToken(user.getToken());
            UserExample example =new UserExample();
            example.createCriteria()
                     .andIdEqualTo(dbUser.getId());
             User updateUser = new User();
             updateUser.setGmtModified(System.currentTimeMillis());
             updateUser.setAvatarUrl(user.getAvatarUrl());
             updateUser.setName(user.getName());
             updateUser.setToken(user.getToken());
             userMapper.updateByExampleSelective(updateUser,example);
//             userMapper.updateInfo(dbUser);
         }else{
             //插入数据
             user.setGmtModified(System.currentTimeMillis());
             user.setGmtCreate(user.getGmtCreate());
             userMapper.insert(user);
         }
    }
}
