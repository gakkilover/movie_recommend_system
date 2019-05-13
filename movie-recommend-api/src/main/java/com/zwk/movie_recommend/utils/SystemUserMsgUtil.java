package com.zwk.movie_recommend.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zwk.movie_recommend.entity.UserEntity;
import com.zwk.movie_recommend.redis.SessionCookie;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-04-13 10:27
 * @ Description：
 */
public class SystemUserMsgUtil {
    /**
     * 用户信息集合
     */
    private static UserEntity userMessageBO;

    // sessionid
    private final static String userMessageStr = "sysUserInfo";

    /**
     * 用户信息集合
     *
     * @return
     * @throws JsonParseException
     */
    public static UserEntity getUserMessageBO() {

		/*Object obj = HttpContextUtils.getSessionAttribute(userMessageStr,UserMessageBO.class); if
		 (obj != null) { userMessageBO = (UserMessageBO) obj; } else {
		 userMessageBO = null; }*/


        String sessionkey = SessionCookie.getUserJSESSIONId();
        UserEntity userMessageBO = null;
        userMessageBO = HttpContextUtils.getSessionAttribute(sessionkey,
                UserEntity.class);
        return userMessageBO;
    }

    public static void setUserMessageBO(UserEntity userMessageBO,
                                        HttpServletResponse response) throws JsonProcessingException {
        if (userMessageBO != null) {
            String ticket = "TICKET"
                    + DigestUtils.md5Hex(System.currentTimeMillis()
                    + userMessageBO.getUserName()); //todo
            HttpContextUtils.setFirstSessionAttribute(ticket, userMessageBO);
            HttpServletRequest request = HttpContextUtils.getRequest();
            CookieUtils.setCookie(request, response, "SESSION_TICKET",
                    ticket); //todo
        }
        /*HttpContextUtils.setSessionAttribute(userMessageStr, userMessageBO);*/
    }
//
//    /**
//     * 系统用户
//     *
//     * @return
//     * @throws IOException
//     * @throws JsonMappingException
//     * @throws JsonParseException
//     */
//    public static UserEntity getSystemUserBO() {
//        userMessageBO = getUserMessageBO();
//        if (userMessageBO != null) {
//            return userMessageBO.getSystemUserBO();
//        } else {
//            return null;
//        }
//    }
//
//    /**
//     * 员工
//     *
//     * @return
//     * @throws IOException
//     * @throws JsonMappingException
//     * @throws JsonParseException
//     */
//    public static UserEntity getStaffBO() {
//        userMessageBO = getUserMessageBO();
//        if (userMessageBO != null) {
//            return userMessageBO.getStaffBO();
//        } else {
//            return null;
//        }
//    }

//    /**
//     * 系统组织集合
//     *
//     * @return
//     * @throws IOException
//     * @throws JsonMappingException
//     * @throws JsonParseException
//     */
//    public static List<SysOrgBO> getSysOrgBOs() {
//        userMessageBO = getUserMessageBO();
//        if (userMessageBO != null) {
//            return userMessageBO.getSysOrgBOs();
//        } else {
//            return null;
//        }
//    }

//    /**
//     * 系统岗位集合
//     *
//     * @return
//     * @throws IOException
//     * @throws JsonMappingException
//     * @throws JsonParseException
//     */
//    public static List<SysPostEntity> getSysPostBOs() {
//        try {
//            userMessageBO = getUserMessageBO();
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//        if (userMessageBO != null) {
//            return userMessageBO.getSysPostBOs();
//        } else {
//            return null;
//        }
//    }
//
//    /**
//     * 系统用户系统岗位集合
//     *
//     * @return
//     * @throws IOException
//     * @throws JsonMappingException
//     * @throws JsonParseException
//     */
//    public static List<SysUserPostRelBO> getSysUserPostRelBOs() {
//        userMessageBO = getUserMessageBO();
//        if (userMessageBO != null) {
//            return userMessageBO.getSysUserPostRelBOs();
//        } else {
//            return null;
//        }
//    }

//    /**
//     * 判断当前登录用户是否超级管理员
//     *
//     * @return
//     * @throws IOException
//     * @throws JsonMappingException
//     * @throws JsonParseException
//     */
//    public static boolean isSuperUser() {
//        userMessageBO = getUserMessageBO();
//        if (userMessageBO != null) {
//            return userMessageBO.isSuperUser();
//        } else {
//            return false;
//        }
//    }
}
