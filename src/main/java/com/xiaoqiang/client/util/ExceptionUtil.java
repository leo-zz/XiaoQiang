package com.xiaoqiang.client.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 异常工具类
 * TODO 没有加锁，是否会在打印日志时出现问题？
 */
public class ExceptionUtil {

    //ctrl+shift+u 切换大小写
    private final static String[] IGNOREDSTACKPREFIX = {"java.lang.reflect", "sun.reflect"};
    private final static String[] FILTERSTACKPREFIX = {"com.xiaoq.testclient"};
    private static boolean isFilter=true;

    /**
     * 用于比较两个异常内容是否一致
     * 判定因素:异常类的类别、异常类的文本信息、异常类的原因、异常类的栈帧执行的位置。
     * 此处传入的都是同一个线程的错误
     *
     * @return 返回true，则表示两个异常内容一致。
     */
    public static boolean ExceptionCompare(Throwable exA, Throwable exB) {

        //先判断错误信息是否一致
        String exAMessage = exA.getMessage();
        String exBMessage = exB.getMessage();
        //使用 && ||，短路与 短路或，即使前面取反短路效果也还有。
        if (!(exAMessage == null && exBMessage == null || exAMessage != null && exBMessage != null && exAMessage.equals(exBMessage)))
         return false;

        //再判断异常的栈帧个数是否一致
        StackTraceElement[] exAstackTrace = exA.getStackTrace();
        StackTraceElement[] exBstackTrace = exB.getStackTrace();
        int exALength = exAstackTrace.length;
        int exBLength = exBstackTrace.length;
        if (exALength != exBLength) return false;

        //再判断异常类是否一致，一个类在堆中只有一个class对象，因此同一个类的实例，它们getClass后的对象是同一个，也就是相等的。
        Class<? extends Throwable> exAClass = exA.getClass();
        Class<? extends Throwable> exBClass = exB.getClass();
        if (exAClass != exBClass) return false;

        //再判断异常的Cause类是否一致
        Class<? extends Throwable> exACauseClass = exA.getCause().getClass();
        Class<? extends Throwable> exBCauseClass = exB.getCause().getClass();
        if (exACauseClass != exBCauseClass) return false;

        //最后判断栈帧信息是否一致，通过所在文件和行号来判断
        for (int i = 0; i < exALength; i++) {
            String exAFileName = exAstackTrace[i].getFileName();
            String exBFileName = exBstackTrace[i].getFileName();
            if (!exAFileName.equals(exBFileName)) return false;

            int exALineNumber = exAstackTrace[i].getLineNumber();
            int exBLineNumber = exBstackTrace[i].getLineNumber();
            if (exALineNumber != exBLineNumber) return false;
        }
        return true;
    }

    //打印异常信息：错误文本信息+精简后的栈帧+异常的suppressed+异常的cause
    public static void PrintExceptionInfo(Throwable ex) {
        Set<Throwable> sets=new HashSet<>();
        Set<StackTraceElement> stacks=new HashSet<>();
        sets.add(ex);

        //打印文本信息
        String message = ex.getMessage();
        if (message == null || message.equals("")) message="无";
        System.out.println("线程："+Thread.currentThread().getName()+"出现异常，异常类为："+ex.getClass().getName() + "，异常信息: " + message);

        //打印精简的栈帧，把动态代理相关的栈帧给去掉。
        StackTraceElement[] stackElements = ExceptionSimplify(ex.getStackTrace(),stacks);
        if (stackElements != null) {
            for (StackTraceElement traceElement : stackElements)
                System.out.println("\tat " + traceElement);
        }

        //打印异常的suppressed
        Throwable[] exSuppressed = ex.getSuppressed();
        for(Throwable exT:exSuppressed){
            PrintCauseAndSuppressedInfo(exT,sets,"Suppressed:  ",stacks);
        }

        //打印异常的cause
        Throwable cause = ex.getCause();
        if(cause!=null){
            PrintCauseAndSuppressedInfo(cause,sets,"Caused by:  ",stacks);
        }
    }

    /**
     * 把动态代理、反射相关的栈帧给去掉。要能拿到用户关心的错误信息
     * 去除重复的栈帧，使用StackTraceElement自带的equals方法。
     * 去掉无意义的栈帧，如包含动态代理&&
     * 20181226-只显示用户编写代码中的异常
     *
     */
    private static StackTraceElement[] ExceptionSimplify(StackTraceElement[] stackElements,Set<StackTraceElement> stacks) {
        if (stackElements == null) return null;

        boolean isIgnored = false;
        List<StackTraceElement> lists = new ArrayList<>();

        foreach:
        for (StackTraceElement stack : stackElements) {
            isIgnored = false;
            String className = stack.getClassName();
            //NEW 1226：
            //匹配指定前缀再打印
            if(isFilter){
                for (String str : FILTERSTACKPREFIX) {
                    if (!className.startsWith(str)) {
                        isIgnored=true;
                        continue foreach;
                    }
                }
            }else {
                //根据前缀清除无意义的栈帧
                for (String str : IGNOREDSTACKPREFIX) {
                    if (className.startsWith(str)) {
                        isIgnored = true;
                        //方便直接进行下一个栈帧的循环
                        continue foreach;
                    }
                }
                // $表示内部类，外部类类名与内部类类名中间用$连接
                // $$表示动态代理的类
                if (className.contains("$$") || className.contains("$")) {
                    isIgnored = true;
                    continue foreach;
                }
            }
            //根据stacks判断是否存在重复的栈帧
            for (StackTraceElement s : stacks) {
                if (stack.equals(s)) {
                    isIgnored = true;
                    continue foreach;
                }
            }
            //如果不重复，不被忽略的话，加入到打印列表中
            if (!isIgnored) {
                lists.add(stack);
                stacks.add(stack);
            }
        }

        //注意 集合转数组的方式
        StackTraceElement[] elements = new StackTraceElement[lists.size()];
        return lists.toArray(elements);
    }


    private static void PrintCauseAndSuppressedInfo(Throwable ex,Set<Throwable> sets,String str,Set<StackTraceElement> stacks){
        if(!sets.contains(ex)){
            sets.add(ex);

            //打印文本信息
            String message = ex.getMessage();
            if (message == null || message.equals("")) message="无";
            System.out.println(str+ex.getClass().getName() + ": " + message);

            //打印精简的栈帧，把动态代理相关的栈帧给去掉。
            StackTraceElement[] stackElements = ExceptionSimplify(ex.getStackTrace(),stacks);
            if (stackElements != null) {
                for (StackTraceElement traceElement : stackElements)
                    System.out.println("\tat " + traceElement);
            }

            //打印异常的suppressed
            Throwable[] exSuppressed = ex.getSuppressed();
            for(Throwable exT:exSuppressed){
                PrintCauseAndSuppressedInfo(exT,sets,"Suppressed:  ",stacks);
            }

            //打印异常的cause
            Throwable cause = ex.getCause();
            if(cause!=null){
                PrintCauseAndSuppressedInfo(cause,sets,"Caused by:  ",stacks);
            }
        }
    }
//    private static void PrintSuppressedInfo(Throwable ex,Set<Throwable> sets,String str){
//        if(!sets.contains(ex)){
//            sets.add(ex);
//
//        }
//
//        //打印文本信息
//        String message = ex.getMessage();
//        if (message != null & !message.equals("")) {
//            System.out.println(str+ex.getClass().getName() + ": " + message);
//        }
//
//
//        //打印精简的栈帧，把动态代理相关的栈帧给去掉。
//        StackTraceElement[] stackElements = ExceptionSimplify(ex.getStackTrace());
//        if (stackElements != null) {
//            for (StackTraceElement traceElement : stackElements)
//                System.out.println("\tat " + traceElement);
//        }
//        //打印异常的suppressed
//        Throwable[] exSuppressed = ex.getSuppressed();
//        for(Throwable exT:exSuppressed){
//            PrintSuppressedInfo(exT,sets);
//        }
//
//        //打印异常的cause
//        Throwable cause = ex.getCause();
//        if(cause!=null){
//            PrintCauseInfo(cause,sets);
//        }
//    }
}
