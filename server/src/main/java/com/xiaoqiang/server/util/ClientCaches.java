package com.xiaoqiang.server.util;

import com.xiaoqiang.server.eneity.ClientAdd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientCaches {
    public static Map<String,ClientAdd> clientsCaches=new ConcurrentHashMap<>();
}
