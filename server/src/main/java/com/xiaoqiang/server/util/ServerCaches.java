package com.xiaoqiang.server.util;

import com.xiaoqiang.server.eneity.ClientAdd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerCaches {
    public static Map<String,ClientAdd> clientsCaches=new ConcurrentHashMap<>(30);
    public static Map<String,ClientAdd> exceptionCaches=new ConcurrentHashMap<>(500);
}
