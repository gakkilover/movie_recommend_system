package com.zwk.movie_recommend.service;

import com.baomidou.mybatisplus.service.IService;
import com.zwk.movie_recommend.common.R;
import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.entity.CollectRecordEntity;
import com.zwk.movie_recommend.entity.UserEntity;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-08 19:40
 * @description:
 **/
public interface UserService extends IService<UserEntity> {

    // 获取用户信息
    public UserEntity getUserById(Long userId);

    // 更新用户信息用于修改密码
    public ResultView updateUser(UserEntity user);

    //用户登录
    ResultView userLogin(String username, String password);

    //检查输入是否合法实时检测
    public ResultView checkData(String param, int type);

    //注册用户
    public ResultView register(UserEntity user);

    public void selectFavorite(CollectRecordEntity collectRecordEntity);//注册时候写入用户喜欢的电影

    //点击注册按钮时候检查合法性
    ResultView checkDataBoth(String paramName, String paramEmail, Integer type);

    //为游客生成新用户
    int generateUser(String ip);

    //通过游客ip查询游客是否已有记录
    UserEntity getUserByIp(String ip);
}
