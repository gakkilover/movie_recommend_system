package com.zwk.movie_recommend.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zwk.common.constant.Final;
import com.zwk.common.utils.GenerateNameUtils;
import com.zwk.common.utils.Md5Cryption;
import com.zwk.movie_recommend.common.ResultView;
import com.zwk.movie_recommend.dao.CollectRecordDao;
import com.zwk.movie_recommend.dao.UserDao;
import com.zwk.movie_recommend.entity.CollectRecordEntity;
import com.zwk.movie_recommend.entity.UserEntity;
import com.zwk.movie_recommend.service.UserService;
import com.zwk.movie_recommend.utils.LocalCacheUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-08 19:51
 * @description:
 **/
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CollectRecordDao collectRecordDao;

    @Override
    public UserEntity getUserById(Long userId) {
        UserEntity user = userDao.selectById(userId);
        return user;
    }

    @Override
    public ResultView updateUser(UserEntity userEntity) {
//        String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
       // String md5Pass = Md5Cryption.encrypt(password + Final.MD5_SALT);
        // user.setUserPassword(md5Pass);
        ResultView resultView = new ResultView();
        UserEntity user = userDao.selectById(userEntity.getUserId());
        List<UserEntity> userList = userDao.getUser("");
        boolean flag =true;
        for (UserEntity userEach: userList) {
            if(!userEach.getUserId().equals(userEntity.getUserId()) && userEach.getUserName().equals(userEntity.getUserName())){
                flag = false;
                break;
            }
        }
        if(flag = true){
            user.setUserEmail(userEntity.getUserEmail());
            user.setUserSex(userEntity.getUserSex());
            user.setUserAge(userEntity.getUserAge());
            user.setUserName(userEntity.getUserName());
            user.setUserPhone(userEntity.getUserPhone());
            userDao.updateById(user);
            return ResultView.ok(user);
        }else {
            return ResultView.build(400, "用户名已存在，更新失败");
        }
    }

    @Override
    public ResultView userLogin(String username, String password) {
        // 判断用户和密码是否正确
        List<UserEntity> list = userDao.login(username);
        // 执行查询
        if (list == null || list.size() == 0) {
            // 返回登录失败
            return ResultView.build(400, "用户名或密码错误");
        }
        // 取用户信息
        UserEntity user = list.get(0);
        // 判断密码是否正确
//        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
        if (!Md5Cryption.encrypt(password + Final.MD5_SALT).equals(user.getUserPassword())) {
            // 2、如果不正确，返回登录失败
            return ResultView.build(400, "用户名或密码错误");
        } else {
            return ResultView.ok(user);
        }
    }

    @Override
    public ResultView checkData(String param, int type) {
        // 根据不同的type生成不同的查询条件
        StringBuffer sql = new StringBuffer();
        // 1：用户名 2：手机号 3：邮箱
        if (type == 1) {
            sql.append(" and user_name = '"+param+"'");
        }
        else if (type == 3){
            sql.append(" and user_email = '"+param+"'");
        }
        else{
            return ResultView.build(400, "数据类型错误");
        }
        // 执行查询
        List<UserEntity> list = userDao.getUser(sql.toString());
        // 判断结果中是否包含数据
        if (list != null && list.size() > 0) {
            // 如果有数据返回false
            return ResultView.ok(false);
        }
        // 如果没有数据返回true
        return ResultView.ok(true);
    }

    @Override
    public ResultView register(UserEntity user) {
        // 数据有效性校验
        if (StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getUserPassword())|| StringUtils.isBlank(user.getUserEmail())) {
            return ResultView.build(400, "用户数据不完整，注册失败");
        }
        // 补全pojo的属性
        user.setUserId(userDao.getMaxId());
        user.setUserRegisterDate(new Date());
        user.setLastLoginDate(new Date());
        user.setStatusCd(Final.USER_STATUS_EFF);
        user.setPhoneCode(null);
        // 对密码进行md5加密
//        String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        String md5Pass = Md5Cryption.encrypt(user.getUserPassword() + Final.MD5_SALT);
        user.setUserPassword(md5Pass);
        // 把用户数据插入到数据库中
        userDao.insert(user);//新增3.18
        Long userId = user.getUserId();
        // 返回添加成功
        return ResultView.ok(userId);
    }

    @Override
    public void selectFavorite(CollectRecordEntity collectRecordEntity) {
        collectRecordEntity.setCollectRecordId(collectRecordDao.getMaxId());
        collectRecordEntity.setCollectDate(new Date());
        collectRecordDao.insert(collectRecordEntity);
    }

    @Override
    public ResultView checkDataBoth(String paramName, String paramEmail, Integer type) {
        // 根据不同的type生成不同的查询条件
        StringBuffer sqlName = new StringBuffer();
        StringBuffer sqlEmail = new StringBuffer();
        // 1：用户名 2：手机号 3：邮箱 4:用户名and邮箱
        if (type == 4 ) {
            sqlName.append(" and user_name = '"+paramName+"'");
            sqlEmail.append(" and user_email = '"+paramEmail+"'");
        }
        else{
            return ResultView.build(400, "数据类型错误");
        }
        // 执行查询
        List<UserEntity> listName = userDao.getUser(sqlName.toString());
        List<UserEntity> listEmail = userDao.getUser(sqlEmail.toString());
        // 判断结果中是否包含数据
        if (listName != null && listName.size() > 0 || listEmail != null && listEmail.size() > 0) {
            // 如果有数据返回false
            return ResultView.ok(false);
        }
        // 如果没有数据返回true
        return ResultView.ok(true);
    }

    @Override
    public int generateUser(String ip) {
        UserEntity user = new UserEntity();
        user.setUserId(userDao.getMaxId());
        //状态设置为1001
        user.setStatusCd(Final.USER_STATUS_EXP);
        user.setUserName(GenerateNameUtils.getName());
        user.setUserPassword(ip);

        int count = userDao.insert(user);
        //将游客信息暂存在本地缓存中
        LocalCacheUtils.set("user", user);
        return count;
    }

    @Override
    public UserEntity getUserByIp(String ip) {
        StringBuffer sql = new StringBuffer();
        sql.append(" and user_password= '"+ip+"'");
        List<UserEntity> userList = userDao.getUser(sql.toString());
        if(userList != null && userList.size()>0){
            return userList.get(0);
        }
        return null;
    }
}
