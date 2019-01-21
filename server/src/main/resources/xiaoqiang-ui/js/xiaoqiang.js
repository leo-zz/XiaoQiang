function initDisplay() {
// 指定图表的配置项和数据
    cpuOption = {
        title: {
            subtext: 'CPU核心数：' + availableProcessors + '核'
        },
        //提示内容,参考配置项文档http://www.echartsjs.com/option.html#title
        tooltip: {
            trigger: 'axis',
            //使用图标默认的展示样式
//            formatter: function (params) {
//                params = params[0];
//                var date = new Date(params.name);
//                return date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds() + ' , ' + params.value[1]+'%';
//            },
            axisPointer: {
                animation: false
            }
        },
        legend: {
            data: ['系统CPU使用率', 'JVM CPU使用率']
        },
        xAxis: {
            type: 'time',
            splitLine: {
                show: false
            }
        },
        yAxis: {
//            name:'系统CPU使用率%',
            type: 'value',
            boundaryGap: [0, '100%'],
            min: 0,
            max: 100,
            splitLine: {
                show: false
            }
        },
        //通过设置series，可以让多个同类型数据显示到一个表中
        series: [{
            name: '系统CPU使用率',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: cpuData
        }, {
            name: 'JVM CPU使用率',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: jvmCpuData
        }]
    };
    hostMemOption = {
        title: {
            subtext: '物理内存容量：' + physicalMemory + 'GB,虚拟内存容量：' + swapSpace + 'GB'
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                animation: false
            }
        },
        legend: {
            data: ['物理内存使用率', '虚拟内存使用率']
        },
        xAxis: {
            type: 'time',
            splitLine: {
                show: false
            }
        },
        yAxis: {
            type: 'value',
            boundaryGap: [0, '100%'],
            min: 0,
            max: 100,
            splitLine: {
                show: false
            }
        },
        //通过设置series，可以让多个同类型数据显示到一个表中
        series: [{
            name: '物理内存使用率',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: physicalMemoryRatio
        }, {
            name: '虚拟内存使用率',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: swapSpaceRatio
        }]
    };
    jvmMemOption = {
        title: {},
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                animation: false
            }
        },
        legend: {
            data: ['堆区使用', '堆区申请', '非堆区使用', '非堆区申请', '虚拟机申请']
        },
        xAxis: {
            type: 'time',
            splitLine: {
                show: false
            }
        },
        yAxis: {
            type: 'value',
            boundaryGap: [0, '100%'],
            splitLine: {
                show: false
            }
        },
        //通过设置series，可以让多个同类型数据显示到一个表中
        series: [{
            name: '堆区使用',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: heapUsedMemory
        }, {
            name: '堆区申请',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: heapCommittedMemory
        }, {
            name: '非堆区使用',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: nonHeapUsedMemory
        }, {
            name: '非堆区申请',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: nonHeapCommittedMemory
        }, {
            name: '虚拟机申请',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: committedVirtualMemory
        }]
    };
    heapOption = {
        title: {},
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                animation: false
            }
        },
        legend: {
            data: ['eden区使用', 'survivor区使用', 'oldGen区使用']
        },
        xAxis: {
            type: 'time',
            splitLine: {
                show: false
            }
        },
        yAxis: {
            type: 'value',
            boundaryGap: [0, '100%'],
            splitLine: {
                show: false
            }
        },
        //通过设置series，可以让多个同类型数据显示到一个表中
        series: [{
            name: 'eden区使用',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: edenSpaceUsedMemory
        }, {
            name: 'survivor区使用',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: survivorSpaceUsedMemory
        }, {
            name: 'oldGen区使用',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: oldGenUsedMemory
        }]
    };
    nonHeapOption = {
        title: {},
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                animation: false
            }
        },
        legend: {
            data: ['codeCache区使用', 'metaspace区使用', 'compressedClassSpace区使用']
        },
        xAxis: {
            type: 'time',
            splitLine: {
                show: false
            }
        },
        yAxis: {
            type: 'value',
            boundaryGap: [0, '100%'],
            splitLine: {
                show: false
            }
        },
        //通过设置series，可以让多个同类型数据显示到一个表中
        series: [{
            name: 'codeCache区使用',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: codeCacheUsedMemory
        }, {
            name: 'metaspace区使用',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: metaspaceUsedMemory
        }, {
            name: 'compressedClassSpace区使用',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: compressedClassSpaceUsedMemory
        }]
    };
    threadOption = {
        title: {
            //TODO 如何让副标题能动态更新？
            subtext: '线程峰值数：' + peakThreadCount + '个，线程总启动数:' + totalStartedThreadCount + '个'
        },
        //提示内容,参考配置项文档http://www.echartsjs.com/option.html#title
        //使用图标默认的展示样式
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                animation: false
            }
        },
        legend: {
            data: ['当前线程数', '死锁线程数', 'monitor死锁线程数']
        },
        xAxis: {
            type: 'time',
            splitLine: {
                show: false
            }
        },
        yAxis: {
            type: 'value',
            boundaryGap: [0, '100%'],
            splitLine: {
                show: false
            }
        },
        //通过设置series，可以让多个同类型数据显示到一个表中
        series: [{
            name: '当前线程数',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: threadCount
        }, {
            name: '死锁线程数',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: deadlockedThreadsNum
        }, {
            name: 'monitor死锁线程数',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: monitorDeadlockedThreadsNum
        }]
    };
    classOption = {
        title: {
            subtext: '类加载总数量：' + totalLoadedClassCount + ',类卸载数量:' + unloadedClassCount
        },
        //提示内容,参考配置项文档http://www.echartsjs.com/option.html#title
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                animation: false
            }
        },
        legend: {
            data: ['类加载数量']
        },
        xAxis: {
            type: 'time',
            splitLine: {
                show: false
            }
        },
        yAxis: {
//            name:'系统CPU使用率%',
            type: 'value',
            boundaryGap: [0, '100%'],
            splitLine: {
                show: false
            }
        },
        //通过设置series，可以让多个同类型数据显示到一个表中
        series: [{
            name: '类加载数量',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: loadedClassCount
        }]
    };
    gcCountOption = {
        title: {
            //TODO 副标题显示不完全，如何换行
            subtext: '年轻代收集器名称：' + ygcName + ',年轻代GC内存区域:' + ygcMemoryPoolNames + ',老年代收集器名称:' + ogcName + ',老年代GC内存区域:' + ogcMemoryPoolNames
        },
        //提示内容,参考配置项文档http://www.echartsjs.com/option.html#title
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                animation: false
            }
        },
        legend: {
            data: ['年轻代gc次数', '老年代gc次数']
        },
        xAxis: {
            type: 'time',
            splitLine: {
                show: false
            }
        },
        yAxis: {
//            name:'系统CPU使用率%',
            type: 'value',
            boundaryGap: [0, '100%'],
            splitLine: {
                show: false
            }
        },
        //通过设置series，可以让多个同类型数据显示到一个表中
        series: [{
            name: '年轻代gc次数',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: ygcCount
        }, {
            name: '老年代gc次数',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: ogcCount
        }]
    };
    gcTimeOption = {
        title: {},
        //提示内容,参考配置项文档http://www.echartsjs.com/option.html#title
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                animation: false
            }
        },
        legend: {
            data: ['年轻代gc耗时', '老年代gc耗时']
        },
        xAxis: {
            type: 'time',
            splitLine: {
                show: false
            }
        },
        yAxis: {
//            name:'系统CPU使用率%',
            type: 'value',
            boundaryGap: [0, '100%'],
            splitLine: {
                show: false
            }
        },
        //通过设置series，可以让多个同类型数据显示到一个表中
        series: [{
            name: '年轻代gc耗时',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: ygcTime
        }, {
            name: '老年代gc耗时',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: ogcTime
        }]
    };
}

function beginDisplay() {
    //更新图标信息
    setInterval(function () {
        //使用ajax调用接口实现数据获取
        //使用jsonp解决跨域问题，需要接口处理jsonp参数 参考https://blog.csdn.net/m_jack/article/details/80497617
        //设置响应头 response.setHeader("Access-Control-Allow-Origin", "*");  参考https://blog.csdn.net/idomyway/article/details/79572973
        requestCPUInfo();
        requestClassInfo();
        requestThreadInfo();
        requestMemoryInfo();
        requestGCInfo();
        exDisplay();

        // 使用刚指定的配置项和数据显示图表。
        cpuChart.setOption(cpuOption);
        threadChart.setOption(threadOption);
        classChart.setOption(classOption);
        hostMemChart.setOption(hostMemOption);
        jvmChart.setOption(jvmMemOption);
        heapChart.setOption(heapOption);
        nonHeapChart.setOption(nonHeapOption);
        gcCountChart.setOption(gcCountOption);
        gcTimeChart.setOption(gcTimeOption);
    }, 2000);
}


function requestAvailableProcessors() {
    var result = 0;
    $.ajaxSettings.async = false;
    //ajax默认是异步通信，result的结果不会立刻返回。此处应改成同步
    $.get(clientInet + "monitor/cpu", function (data) {
        if (data.result == true) {
            result = data.value.availableProcessors;
        }
    }, "json");
    $.ajaxSettings.async = true;
    return result;
}

function requesthostMem() {
    var result = 0;
    $.ajaxSettings.async = false;
    //ajax默认是异步通信，result的结果不会立刻返回。此处应改成同步
    $.get(clientInet + "monitor/memory", function (data) {
        if (data.result == true) {
            result = [data.value.hostMemoryInfo.totalPhysicalMemorySize, data.value.hostMemoryInfo.totalSwapSpaceSize];
        }
    }, "json");
    $.ajaxSettings.async = true;
    return result;
}

function requesttotalLoadedandUnloadedClassInfo() {
    var result;
    $.ajaxSettings.async = false;
    //ajax默认是异步通信，result的结果不会立刻返回。此处应改成同步
    $.get(clientInet + "monitor/class", function (data) {
        if (data.result == true) {
            result = [data.value.totalLoadedClassCount, data.value, data.value.bootClassPath, data.value.classPath];
        }
    }, "json");
    $.ajaxSettings.async = true;
    return result;
}

function requestPeakandtotalStartedThreadInfo() {
    var result;
    $.ajaxSettings.async = false;
    //ajax默认是异步通信，result的结果不会立刻返回。此处应改成同步
    $.get(clientInet + "monitor/thread", function (data) {
        if (data.result == true) {
            result = [data.value.peakThreadCount, data.value.totalStartedThreadCount];
        }
    }, "json");
    $.ajaxSettings.async = true;
    return result;
}

function requestCollectorInfo() {
    var result;
    $.ajaxSettings.async = false;
    //ajax默认是异步通信，result的结果不会立刻返回。此处应改成同步
    $.get(clientInet + "monitor/gc", function (data) {
        if (data.result == true) {
            result = [data.value[0].name, data.value[0].memoryPoolNames, data.value[1].name, data.value[1].memoryPoolNames];
        }
    }, "json");
    $.ajaxSettings.async = true;
    return result;
}

function requestRuntimeInfo() {
    var result;
    $.ajaxSettings.async = false;
    //ajax默认是异步通信，result的结果不会立刻返回。此处应改成同步
    $.get(clientInet + "monitor/runtime", function (data) {
        if (data.result == true) {
            result = [data.value.jvmName, data.value.osName, data.value.compilerName, data.value.inputArguments, new Date(data.value.jvmStartTime)];
        }
    }, "json");
    $.ajaxSettings.async = true;
    return result;
}


function requestCPUInfo() {
    $.get(clientInet + "monitor/cpu", function (data) {
        if (data.result == true) {
            //通常来说，数据用一个二维数组表示。如下，每一列被称为一个『维度』。
            //注意 new Date() 和 Date()的区别
            var now = new Date();
            var aData = {
                name: now,
                value: [
                    now,
                    //格式化CPU数据
                    (data.value.systemCpuLoad * 100).toFixed(2)
                ]
            };
            var bData = {
                name: now,
                value: [
                    now,
                    (data.value.jvmCpuLoad * 100).toFixed(2)
                ]
            };
            cpuData.push(aData);
            jvmCpuData.push(bData);
        }
    }, "json");
}

function requestClassInfo() {
    $.get(clientInet + "monitor/class", function (data) {
        if (data.result == true) {
            //通常来说，数据用一个二维数组表示。如下，每一列被称为一个『维度』。
            //注意 new Date() 和 Date()的区别
            var now = new Date();
            var aData = {
                name: now,
                value: [
                    now,
                    data.value.loadedClassCount
                ]
            };
            loadedClassCount.push(aData);
        }
    }, "json");
}

function requestMemoryInfo() {
    $.get(clientInet + "monitor/memory", function (data) {
        if (data.result == true) {
            //通常来说，数据用一个二维数组表示。如下，每一列被称为一个『维度』。
            //注意 new Date() 和 Date()的区别
            var now = new Date();
            var a1 = data.value.hostMemoryInfo.totalPhysicalMemorySize;
            var a2 = data.value.hostMemoryInfo.freePhysicalMemorySize;
            var a3 = data.value.hostMemoryInfo.totalSwapSpaceSize;
            var a4 = data.value.hostMemoryInfo.freeSwapSpaceSize;

            var physicalMemoryRatioData = {
                name: now,
                value: [
                    now, ((1 - a2 / a1) * 100).toFixed(2)
                ]
            };
            var swapSpaceRatioData = {
                name: now,
                value: [
                    now, ((1 - a4 / a3) * 100).toFixed(2)
                ]
            };
            physicalMemoryRatio.push(physicalMemoryRatioData);
            swapSpaceRatio.push(swapSpaceRatioData);

            var b1 = data.value.jvmMemory.heapUsedMemory;
            var b2 = data.value.jvmMemory.heapCommittedMemory;
            var b3 = data.value.jvmMemory.nonHeapUsedMemory;
            var b4 = data.value.jvmMemory.nonHeapCommittedMemory;
            var b5 = data.value.jvmMemory.committedVirtualMemorySize;


            var heapUsedMemoryData = {
                name: now,
                value: [
                    //单位不要写，value要求是纯数字
                    now, (b1 / 1024 / 1024).toFixed(2) // + "MB"
                ]
            };
            var heapCommittedMemoryData = {
                name: now,
                value: [
                    now, (b2 / 1024 / 1024).toFixed(2)
                ]
            };
            var nonHeapUsedMemoryData = {
                name: now,
                value: [
                    now, (b3 / 1024 / 1024).toFixed(2)
                ]
            };
            var nonHeapCommittedMemoryData = {
                name: now,
                value: [
                    now, (b4 / 1024 / 1024).toFixed(2)
                ]
            };
            var committedVirtualMemoryData = {
                name: now,
                value: [
                    now, (b5 / 1024 / 1024).toFixed(2)
                ]
            };

            heapUsedMemory.push(heapUsedMemoryData);
            heapCommittedMemory.push(heapCommittedMemoryData);
            nonHeapUsedMemory.push(nonHeapUsedMemoryData);
            nonHeapCommittedMemory.push(nonHeapCommittedMemoryData);
            committedVirtualMemory.push(committedVirtualMemoryData);

            var c1 = data.value.memoryPools[3].usage.used;
            var c2 = data.value.memoryPools[4].usage.used;
            var c3 = data.value.memoryPools[5].usage.used;
            var edenSpaceUsedMemoryData = {
                name: now,
                value: [
                    now, (c1 / 1024 / 1024 ).toFixed(2)
                ]
            };
            var survivorSpaceUsedMemoryData = {
                name: now,
                value: [
                    now, (c2 / 1024 / 1024 ).toFixed(2)
                ]
            };
            var oldGenUsedMemoryData = {
                name: now,
                value: [
                    now, (c3 / 1024 / 1024 ).toFixed(2)
                ]
            };


            edenSpaceUsedMemory.push(edenSpaceUsedMemoryData);
            survivorSpaceUsedMemory.push(survivorSpaceUsedMemoryData);
            oldGenUsedMemory.push(oldGenUsedMemoryData);

            var d1 = data.value.memoryPools[0].usage.used;
            var d2 = data.value.memoryPools[1].usage.used;
            var d3 = data.value.memoryPools[2].usage.used;
            var codeCacheUsedMemoryData = {
                name: now,
                value: [
                    now, (d1 / 1024 / 1024 ).toFixed(2)
                ]
            };
            var metaspaceUsedMemoryData = {
                name: now,
                value: [
                    now, (d2 / 1024 / 1024 ).toFixed(2)
                ]
            };
            var compressedClassSpaceUsedMemoryData = {
                name: now,
                value: [
                    now, (d3 / 1024 / 1024 ).toFixed(2)
                ]
            };
            codeCacheUsedMemory.push(codeCacheUsedMemoryData);
            metaspaceUsedMemory.push(metaspaceUsedMemoryData);
            compressedClassSpaceUsedMemory.push(compressedClassSpaceUsedMemoryData);

        }
    }, "json");
}

function requestThreadInfo() {
    $.get(clientInet + "monitor/thread", function (data) {
        if (data.result == true) {
            //通常来说，数据用一个二维数组表示。如下，每一列被称为一个『维度』。
            //注意 new Date() 和 Date()的区别
            var now = new Date();
            var aData = {
                name: now,
                value: [
                    now,
                    data.value.threadCount
                ]
            };
            var bData = {
                name: now,
                value: [
                    now,
                    data.value.deadlockedThreadsNum
                ]
            };
            var cData = {
                name: now,
                value: [
                    now,
                    data.value.monitorDeadlockedThreadsNum
                ]
            };
            threadCount.push(aData);
            deadlockedThreadsNum.push(bData);
            monitorDeadlockedThreadsNum.push(cData);
        }
    }, "json");
}

function requestGCInfo() {
    $.get(clientInet + "monitor/gc", function (data) {
        if (data.result == true) {
            //通常来说，数据用一个二维数组表示。如下，每一列被称为一个『维度』。
            //注意 new Date() 和 Date()的区别
            var now = new Date();
            var aData = {
                name: now,
                value: [
                    now,
                    data.value[0].collectionCount
                ]
            };
            var bData = {
                name: now,
                value: [
                    now,
                    data.value[0].collectionTime
                ]
            };
            ygcCount.push(aData);
            ygcTime.push(bData);
            var cData = {
                name: now,
                value: [
                    now,
                    data.value[1].collectionCount
                ]
            };
            var dData = {
                name: now,
                value: [
                    now,
                    data.value[1].collectionTime
                ]
            };
            ogcCount.push(cData);
            ogcTime.push(dData);
        }
    }, "json");
}

function format(d) {
    var reuslt = "";
    reuslt += d.getFullYear() + "-";
    reuslt += addZeroIfLess10(d.getMonth() + 1) + "-";
    reuslt += addZeroIfLess10(d.getDate());
    reuslt += " " + addZeroIfLess10(d.getHours()) + ":";
    reuslt += addZeroIfLess10(d.getMinutes()) + ":";
    reuslt += addZeroIfLess10(d.getSeconds());
    return reuslt;
}

function addZeroIfLess10(a) {
    var b = "";
    if (a < 10) {
        b += "0";
    }
    return b += a;
}

function fillJVMInfo() {
    var time = format(new Date(jvmStartTime));
    var jvmContent = "<tr ><td title='" + jvmName + "'>"
        + jvmName
        + "</td><td title='" + osName + "'>"
        + osName + "</td><td title='" + compilerName + "'>"
        + compilerName
        + "</td><td title='" + time + "'>";
    jvmContent += time
        + "</td><td title='" + inputArguments + "'>"
        + inputArguments
        + "</td><td title='" + bootClassPath + "'>"
        + bootClassPath
        + "</td><td title='" + classPath + "'>"
        + classPath + "</td><tr>";
    return jvmContent;
}

function fillClientInfo() {
    var content = "";
    $.ajaxSettings.async = false;
    $.post("http://localhost:5140/xq/clientlists", function (data) {
        if (data.result == true) {
            for (var index in data.value) {
                content += "<tr ><td>"
                    + data.value[index].instanceName
                    + "</td><td>"
                    + data.value[index].address + "</td><td>"
                    + data.value[index].port
                    + "</td><td>";
                var time = format(new Date(data.value[index].lastConnTime));
                content += time;
                content += "</td><td>";
                var active = "在线";
                if (!data.value[index].activeFlag) {
                    active = "离线";
                }
                content += active;
                content += "</td></tr>";
            }
        }
    }, "json");
    $.ajaxSettings.async = true;
    return content;
}

function initParams() {
    var memParams = requesthostMem();
    availableProcessors = requestAvailableProcessors();
    physicalMemory = (memParams[0] / 1024 / 1024 / 1024).toFixed(2);
    swapSpace = (memParams[1] / 1024 / 1024 / 1024).toFixed(2);

    var classParams = requesttotalLoadedandUnloadedClassInfo();
    totalLoadedClassCount = classParams[0];
    unloadedClassCount = classParams[1];

    var threadParams = requestPeakandtotalStartedThreadInfo();
    peakThreadCount = threadParams[0];
    totalStartedThreadCount = threadParams[1];

    var gcParams = requestCollectorInfo();
    ygcName = gcParams[0];
    ygcMemoryPoolNames = gcParams[1];
    ogcName = gcParams[2];
    ogcMemoryPoolNames = gcParams[3];


    var otherParams = requestRuntimeInfo();
    bootClassPath = classParams[2];
    classPath = classParams[3];
    jvmName = otherParams[0];
    osName = otherParams[1];
    compilerName = otherParams[2];
    inputArguments = otherParams[3];
    jvmStartTime = otherParams[4];
}
function chartsInit() {
    cpuChart = echarts.init(document.getElementById('cpu'));
    hostMemChart = echarts.init(document.getElementById('hostMemRatio'));
    jvmChart = echarts.init(document.getElementById('jvmMem'));
    heapChart = echarts.init(document.getElementById('heapMem'));
    nonHeapChart = echarts.init(document.getElementById('nonHeapMem'));
    classChart = echarts.init(document.getElementById('class'));
    threadChart = echarts.init(document.getElementById('thread'));
    gcCountChart = echarts.init(document.getElementById('gcCount'));
    gcTimeChart = echarts.init(document.getElementById('gcTime'));
    exChart = echarts.init(document.getElementById('exChart'));
}

function chartsDispose() {
    cpuChart.dispose();
    hostMemChart.dispose();
    jvmChart.dispose();
    heapChart.dispose();
    nonHeapChart.dispose();
    classChart.dispose();
    threadChart.dispose();
    gcCountChart.dispose();
    gcTimeChart.dispose();
    exChart.dispose();
}

function clearArrayContent() {
    cpuData =[];
    jvmCpuData =[];
    physicalMemoryRatio =[];
    swapSpaceRatio =[];
    heapUsedMemory =[];
    heapCommittedMemory =[];
    nonHeapUsedMemory =[];
    nonHeapCommittedMemory =[];
    committedVirtualMemory =[];
    edenSpaceUsedMemory =[];
    survivorSpaceUsedMemory =[];
    oldGenUsedMemory =[];
    codeCacheUsedMemory =[];
    metaspaceUsedMemory =[];
    compressedClassSpaceUsedMemory =[];
    loadedClassCount =[];
    threadCount =[];
    deadlockedThreadsNum =[];
    monitorDeadlockedThreadsNum =[];
    ygcCount =[];
    ygcTime =[];
    ogcCount =[];
    ogcTime =[];
}
function xqDisplay(b) {
    //初始化参数
    initParams();
    //拼接JVM信息的tbody
    var jvmContent = fillJVMInfo();
    b.html(jvmContent);
    //如果已经运行过，那么先销毁之前的echarts
    if(displatFlag){
        chartsDispose();
        clearArrayContent();
        displatFlag=false;
    }
    chartsInit();
    initDisplay();
    beginDisplay();
}

//异常信息展示
function exDisplay() {
    exChart.showLoading();
    $.post("/xq/exceptions", {
        instanceName: instanceName
    }, function (data) {
        //所有节点都隐藏
        exChart.hideLoading();
        if (data.result == true & data.value != null) {
            echarts.util.each(data.value.children, function (datum, index) {
                datum.collapsed = true;
            });
            exChart.setOption(option = {
                tooltip: {
                    trigger: 'item',
                    triggerOn: 'mousemove'
                },
                series: [
                    {
                        type: 'tree',
                        data: [data.value],
                        top: '1%',
                        left: '7%',
                        bottom: '1%',
                        right: '20%',
                        //设置图标大小
                        symbolSize: 7,
                        //设置未展开时图标的标签名称
                        label: {
                            normal: {
                                position: 'left',
                                verticalAlign: 'middle',
                                align: 'right',
                                fontSize: 15
                            }
                        },
                        //设置展开时图标的标签名称
                        leaves: {
                            label: {
                                normal: {
                                    position: 'right',
                                    verticalAlign: 'middle',
                                    align: 'left'
                                }
                            }
                        },
                        expandAndCollapse: true,
                        //树状图显示节点的时间
                        animationDuration: 550,
                        //树状图展开或缩回的时间
                        animationDurationUpdate: 750
                    }
                ]
            });
        }
    }, "json");
}