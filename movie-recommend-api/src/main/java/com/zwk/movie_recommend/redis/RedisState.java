package com.zwk.movie_recommend.redis;

import org.springframework.stereotype.Component;

/**
 * @author: zwk
 * @email : zwk0@qq.com
 * @create: 2019-04-14 15:43
 * @description:
 **/
@Component
public class RedisState {
    public static final String SESSION_TICKET = "TT_TICKET";

    public String state="on";

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
