package com.example.framework.comm.threadlocal;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.example.framework.comm.consts.CommParameter;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈支持父子线程值传递、以及线程池复用线程〉
 *
 * @author zhaomengbin
 * @create 2022/2/8
 * @since 1.0.0
 */
public class ParameterThreadLocal {

   private static TransmittableThreadLocal<String> uid = new TransmittableThreadLocal<>();
   private static TransmittableThreadLocal<String> deviceId = new TransmittableThreadLocal<>();
   private static TransmittableThreadLocal<String> requester = new TransmittableThreadLocal<>();
   private static TransmittableThreadLocal<Integer> bizType = new TransmittableThreadLocal<>();


   public final static  Map<CommParameter,TransmittableThreadLocal> ttlMap =new HashMap<>();
    static {
        ttlMap.put(CommParameter.P_UID,uid);
        ttlMap.put(CommParameter.P_DEVICE_ID,deviceId);
        ttlMap.put(CommParameter.P_REQUESTER,requester);
        ttlMap.put(CommParameter.P_B,bizType);

    }


    public static Object getValue(String param){
        if(ttlMap.get(param)==null){
            return null;
        }
        return ttlMap.get(param).get();
    }

    /**
     * 清空threadLocal值
     */
    public static void clear(){
        if(ttlMap.size()>0){
            ttlMap.values().forEach( ttl -> ttl.remove());
        }
    }
}