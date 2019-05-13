package com.zwk.common.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ Author     ：zwk
 * @ Email      ：zwk0@qq.com
 * @ Date       ：Created in 2019-01-05 15:09
 * @ Description： 生成随机数字和id的工具类
 */
public class UUIDGenerateUtils {
    private static final Long baseNumber=0L;
    private static final Long base=10000L;
    private static List<Long> numberList=new ArrayList<>();
    private static Lock lock=new ReentrantLock();


    public static Long getNmber(){
        lock.lock();
        Long number;
        Long newNumber;
        try{
            if(numberList.size()==0){
                numberList.add(baseNumber);
            }
            int size=numberList.size();
            Date date=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddmmss");
            String numberStr=sdf.format(date);
            number=Long.parseLong(numberStr)*base;
            newNumber=numberList.get(size-1)+1L;
            numberList.add(newNumber);
            return number+newNumber;
        }finally {
            lock.unlock();
        }

    }

}
