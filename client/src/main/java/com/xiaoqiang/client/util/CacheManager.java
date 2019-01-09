package com.xiaoqiang.client.util;

import com.xiaoqiang.client.entity.ExceptionInfos;

import java.util.ArrayList;
import java.util.List;

/**
 * 能够缓存异常信息，功能点为：
 * 1、可以指定时间，以查询该时间点以后的所有异常信息
 * 2、默认最多存储500条异常信息
 * 3、默认只保留3分钟以内的信息
 * 4、当异常信息超过容量时，会按照顺序自动覆盖异常信息（要求Map是有序的）
 * 5、仿照ConcurrentHashMap 写一个固定容量（不扩容）的线程安全的有序K-V缓存。
 */
public class CacheManager {

    private static int size = 0;
    private static final int keepaliveTime = 180000;
    private static final int capacity = 500;
    private static final List<ExceptionInfos> EMPTY=new ArrayList<>(0);
    //不能创建泛型数组
    private static Entry[] caches = new Entry[500];
    //查找指定时间戳之前的异常信息
    public static List<ExceptionInfos> query(long timeMillis) {

        //如果当前无数据，返回固定的空对象，不要返回null，ajax调用时不会执行回调函数。
        if(size==0) return EMPTY;

        //使用最笨的遍历查找法，先找到目标元素的范围position
        Entry<Long, ExceptionInfos> e;
        int position=-1;
        for(int i=size-1;i>=0;i--){
            e=(Entry<Long, ExceptionInfos>)caches[i];
            if(e.key<=timeMillis){
                position=i;
                break;
            }
        }
        //返回前position+1个元素
        List<ExceptionInfos> lists = removeFrontNEntries(caches, position + 1);
        //更新size
        size=(size-position - 1);
        //TODO 使用二分查找法找到指定时间戳的位置
        return lists;
    }


    //存入指定时间戳的异常信息，插入的数据越来越新
    //TODO 未增加过期删除
    public static boolean put(ExceptionInfos exceptionInfo, long timeMillis) {
        Entry<Long, ExceptionInfos> entry = new Entry<>(timeMillis, exceptionInfo);

        //如果已经达到最大容量，则替换最早的数据，然后再存入
        if (size >= capacity) {
            //移除最早的元素
            removeFrontNEntries(caches,1);
        }
        caches[size] = entry;
        size++;
        return true;
    }

    /**
     *
     * @param caches 源数组信息
     * @param n 移除前N个元素
     * @return 移除的前N个数据
     */
    private static List<ExceptionInfos> removeFrontNEntries(Entry<Long, ExceptionInfos>[] caches,int n) {
        if(n<=0||n>size){
            throw new RuntimeException("移除元素的数量有误，应该是1至"+size+"个之间，而不应该是"+n);
        }
        List<ExceptionInfos> removes = new ArrayList<>(n);
        //遍历数组，将源数组中的前n个元素移动到返回数组中
        for (int i=0;i<size;i++){
            //0到n-1之间的n个元素移动到返回数组中
            if(i<n){
                removes.add(caches[i].value);
            }else {
                caches[i-n]=caches[i];
                //源数组移除n个元素后，空余出的后n个位置需要置空
                if(i>=size-n){
                    caches[i]=null;
                }
            }
        }
        return removes;
    }

    private static class Entry<K, V> {
        K key ;
        V value ;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

}
