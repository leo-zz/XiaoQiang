package com.xiaoqiang.client.controller;

import com.xiaoqiang.client.entity.EchartTreeNode;
import com.xiaoqiang.client.entity.ExceptionInfoEntity;
import com.xiaoqiang.client.entity.ExceptionInfos;
import com.xiaoqiang.client.entity.HttpResult;
import com.xiaoqiang.client.util.CacheManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@ResponseBody
@RequestMapping("/exception")
public class ExceptionController {

    @RequestMapping("query")
    public HttpResult<EchartTreeNode> queryExceptionInfos() {

        List<ExceptionInfos> lists = CacheManager.query(System.currentTimeMillis());
        EchartTreeNode root = generateEchartTreeNode(lists);
        return new HttpResult(true, root);
    }

    /**
     * rootNode
     * firstLevelNode  不同方法
     * secondlevelNode 方法信息
     * threelevelNode 不同异常
     * fourlevelNode 异常信息
     *
     * @param lists
     * @return
     */
    private EchartTreeNode generateEchartTreeNode(List<ExceptionInfos> lists) {
        //根节点
        int size = lists.size();//此次新增的异常数，也就是二级节点的个数
        EchartTreeNode root = new EchartTreeNode("异常信息个数：" + size);
        //根节点value
        ArrayList<EchartTreeNode> exceptionInfos = new ArrayList<>(size);

        if (size > 0) {
            //Lambda
            lists.forEach(ex -> {
                List<ExceptionInfoEntity> exceptionInfoEntitys = ex.getExceptionInfoEntities();
                int exSize = exceptionInfoEntitys.size();
                //一级节点，方法执行信息
                EchartTreeNode firstLevelNode = new EchartTreeNode("方法：" + ex.getMethodName() + "共出现" + exSize + "种异常");
                //一级节点value
                ArrayList<EchartTreeNode> firstLists = new ArrayList<>();
                EchartTreeNode secondlevelRootNode = generateExListsNode(exceptionInfoEntitys, "方法异常个数：");

                //一级节点添加value
                firstLists.add(new EchartTreeNode("线程名称：" + ex.getThreadName()));
                firstLists.add(new EchartTreeNode("重试次数：" + ex.getRetryCount()));
                firstLists.add(new EchartTreeNode("执行结果：" + (ex.isResult() ? "成功" : "失败")));
                firstLists.add(new EchartTreeNode("执行时间：" + ex.getExTime()));
                firstLists.add(new EchartTreeNode("执行耗时：" + ex.getSpendTime() + "ms"));
                firstLists.add(secondlevelRootNode);
                firstLevelNode.setChildren(firstLists.toArray(new EchartTreeNode[firstLists.size()]));
                exceptionInfos.add(firstLevelNode);
            });
            //根节点添加value
            root.setChildren(exceptionInfos.toArray(new EchartTreeNode[size]));
        }
        return root;
    }

    private EchartTreeNode generateExListsNode(List<ExceptionInfoEntity> exceptionInfoEntitys, String str) {
        int exSize = exceptionInfoEntitys.size();
        //二级节点的父节点
        EchartTreeNode secondlevelRootNode = new EchartTreeNode(str + exSize + "种");
        //二级节点的value
        ArrayList<EchartTreeNode> secondlevelLists = new ArrayList<>(exSize);
        if (exSize > 0) {
            exceptionInfoEntitys.forEach(exEntity -> {
                //三级节点的父节点
                EchartTreeNode threelevelRootNode = generateExNode(exEntity, "异常信息：");
                //添加三级节点到父节点的value
                secondlevelLists.add(threelevelRootNode);
            });
            //二级节点添加value
            secondlevelRootNode.setChildren(secondlevelLists.toArray(new EchartTreeNode[secondlevelLists.size()]));
        }
        return secondlevelRootNode;
    }

    private EchartTreeNode generateExNode(ExceptionInfoEntity exEntity, String exPrefix) {

        EchartTreeNode threelevelRootNode = new EchartTreeNode(exPrefix + exEntity.getExceptionMessage());
        //三级节点的value
        ArrayList<EchartTreeNode> threeLists = new ArrayList<>(6);
        //三级节点，方法异常信息
        threeLists.add(new EchartTreeNode("异常类型：" + exEntity.getExceptionClassName()));
        //栈打印的处理
        StackTraceElement[] stacks = exEntity.getSimplileStackElements();
        if (stacks.length > 0) {
            EchartTreeNode[] stackNodes = new EchartTreeNode[stacks.length];
            int i=0;
            for (StackTraceElement traceElement : stacks) {
                EchartTreeNode echartTreeNode = new EchartTreeNode("\tat " + traceElement);
                stackNodes[i++]=echartTreeNode;
            }
            EchartTreeNode stacksNode = new EchartTreeNode("异常栈：" + stacks.length + "个");
            stacksNode.setChildren(stackNodes);
            threeLists.add(stacksNode);
        }
        threeLists.add(generateExNode(exEntity.getCauseException(), "异常原因："));
        List<ExceptionInfoEntity> suppressedExceptions = exEntity.getSuppressedExceptions();
        if (suppressedExceptions.size() > 0) {
            threeLists.add(generateExListsNode(suppressedExceptions, "屏蔽异常："));
        }
        threelevelRootNode.setChildren(threeLists.toArray(new EchartTreeNode[threeLists.size()]));
        return threelevelRootNode;
    }

}
