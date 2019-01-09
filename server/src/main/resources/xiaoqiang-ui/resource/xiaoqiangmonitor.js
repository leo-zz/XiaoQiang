
function initDisplay() {
// 指定图表的配置项和数据
    cpuOption = {
        title: {
            text: 'CPU使用率%',
            subtext: 'CPU核心数：' + availableProcessors
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
            text: '主机内存使用率%',
            subtext: '物理内存容量：' + physicalMemory + ',虚拟内存容量：' + swapSpace
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
        title: {
            text: 'jvm内存信息'
        },
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
        title: {
            text: 'jvm堆内存信息'
//            subtext: '物理内存容量：' + physicalMemory+',虚拟内存容量：'+swapSpace
        },
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
        title: {
            text: 'jvm非堆内存信息'
//            subtext: '物理内存容量：' + physicalMemory+',虚拟内存容量：'+swapSpace
        },
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
            text: 'jvm线程信息',
            //如何让副标题能动态更新？
            subtext: '线程峰值数：' + peakThreadCount + '，线程总启动数:' + totalStartedThreadCount
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
            text: 'jvm类信息',
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
            text: 'gc次数信息',
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
            data: ['年轻代gc次数','老年代gc次数']
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
        },{
            name: '老年代gc次数',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: ogcCount
        }]
    };
    gcTimeOption = {
        title: {
            text: 'gc耗时信息',
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
            data: ['年轻代gc耗时','老年代gc耗时']
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
        },{
            name: '老年代gc耗时',
            type: 'line',
            showSymbol: false,
            hoverAnimation: false,
            data: ogcTime
        }]
    };
}

function beginDisplay(){
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
    $.get("http://localhost:8080/monitor/cpu", function (data, status) {
        if (status == "success") {
            result = data.availableProcessors;
        }
    }, "json");
    $.ajaxSettings.async = true;
    return result;
}
function requesthostMem() {
    var result = 0;
    $.ajaxSettings.async = false;
    //ajax默认是异步通信，result的结果不会立刻返回。此处应改成同步
    $.get("http://localhost:8080/monitor/memory", function (data, status) {
        if (status == "success") {
            result = [data.hostMemoryInfo.totalPhysicalMemorySize, data.hostMemoryInfo.totalSwapSpaceSize];
        }
    }, "json");
    $.ajaxSettings.async = true;
    return result;
}
function requesttotalLoadedandUnloadedClassInfo() {
    var result;
    $.ajaxSettings.async = false;
    //ajax默认是异步通信，result的结果不会立刻返回。此处应改成同步
    $.get("http://localhost:8080/monitor/class", function (data, status) {
        if (status == "success") {
            result = [(data.totalLoadedClassCount / 1024 / 1024).toFixed(2) +'MB',(data.unloadedClassCount / 1024 / 1024).toFixed(2) +'MB', data.bootClassPath, data.classPath];
        }
    }, "json");
    $.ajaxSettings.async = true;
    return result;
}
function requestPeakandtotalStartedThreadInfo() {
    var result;
    $.ajaxSettings.async = false;
    //ajax默认是异步通信，result的结果不会立刻返回。此处应改成同步
    $.get("http://localhost:8080/monitor/thread", function (data, status) {
        if (status == "success") {
            result = [data.peakThreadCount, data.totalStartedThreadCount];
        }
    }, "json");
    $.ajaxSettings.async = true;
    return result;
}
function requestCollectorInfo() {
    var result;
    $.ajaxSettings.async = false;
    //ajax默认是异步通信，result的结果不会立刻返回。此处应改成同步
    $.get("http://localhost:8080/monitor/gc", function (data, status) {
        if (status == "success") {
            result = [data[0].name, data[0].memoryPoolNames,data[1].name, data[1].memoryPoolNames];
        }
    }, "json");
    $.ajaxSettings.async = true;
    return result;
}
function requestRuntimeInfo() {
    var result;
    $.ajaxSettings.async = false;
    //ajax默认是异步通信，result的结果不会立刻返回。此处应改成同步
    $.get("http://localhost:8080/monitor/runtime", function (data, status) {
        if (status == "success") {
            result = [data.jvmName,data.osName,data.compilerName,data.inputArguments,new Date(data.jvmStartTime)];
        }
    }, "json");
    $.ajaxSettings.async = true;
    return result;
}


function requestCPUInfo() {
    $.get("http://localhost:8080/monitor/cpu", function (data, status) {
        if (status == "success") {
            //通常来说，数据用一个二维数组表示。如下，每一列被称为一个『维度』。
            //注意 new Date() 和 Date()的区别
            var now = new Date();
            var aData = {
                name: now,
                value: [
                    now,
                    //格式化CPU数据
                    (data.systemCpuLoad * 100).toFixed(2)
                ]
            };
            var bData = {
                name: now,
                value: [
                    now,
                    (data.jvmCpuLoad * 100).toFixed(2)
                ]
            };
            cpuData.push(aData);
            jvmCpuData.push(bData);
        }
    }, "json");
}
function requestClassInfo() {
    $.get("http://localhost:8080/monitor/class", function (data, status) {
        if (status == "success") {
            //通常来说，数据用一个二维数组表示。如下，每一列被称为一个『维度』。
            //注意 new Date() 和 Date()的区别
            var now = new Date();
            var aData = {
                name: now,
                value: [
                    now,
                    data.loadedClassCount
                ]
            };
            loadedClassCount.push(aData);
        }
    }, "json");
}
function requestMemoryInfo() {
    $.get("http://localhost:8080/monitor/memory", function (data, status) {
        if (status == "success") {
            //通常来说，数据用一个二维数组表示。如下，每一列被称为一个『维度』。
            //注意 new Date() 和 Date()的区别
            var now = new Date();
            var a1 = data.hostMemoryInfo.totalPhysicalMemorySize;
            var a2 = data.hostMemoryInfo.freePhysicalMemorySize;
            var a3 = data.hostMemoryInfo.totalSwapSpaceSize;
            var a4 = data.hostMemoryInfo.freeSwapSpaceSize;

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

            var b1 = data.jvmMemory.heapUsedMemory;
            var b2 = data.jvmMemory.heapCommittedMemory;
            var b3 = data.jvmMemory.nonHeapUsedMemory;
            var b4 = data.jvmMemory.nonHeapCommittedMemory;
            var b5 = data.jvmMemory.committedVirtualMemorySize;


            var heapUsedMemoryData = {
                name: now,
                value: [
                    //单位不要写，value要求是纯数字
                    now, (b1 / 1024 / 1024).toFixed(2) // + "MB"
                ]
            };
            console.log(heapUsedMemoryData);
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

            var c1 = data.memoryPools[3].usage.used;
            var c2 = data.memoryPools[4].usage.used;
            var c3 = data.memoryPools[5].usage.used;
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

            var d1 = data.memoryPools[0].usage.used;
            var d2 = data.memoryPools[1].usage.used;
            var d3 = data.memoryPools[2].usage.used;
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
    $.get("http://localhost:8080/monitor/thread", function (data, status) {
        if (status == "success") {
            //通常来说，数据用一个二维数组表示。如下，每一列被称为一个『维度』。
            //注意 new Date() 和 Date()的区别
            var now = new Date();
            var aData = {
                name: now,
                value: [
                    now,
                    data.threadCount
                ]
            };
            var bData = {
                name: now,
                value: [
                    now,
                    data.deadlockedThreadsNum
                ]
            };
            var cData = {
                name: now,
                value: [
                    now,
                    data.monitorDeadlockedThreadsNum
                ]
            };
            threadCount.push(aData);
            deadlockedThreadsNum.push(bData);
            monitorDeadlockedThreadsNum.push(cData);
        }
    }, "json");
}
function requestGCInfo() {
    $.get("http://localhost:8080/monitor/gc", function (data, status) {
        if (status == "success") {
            //通常来说，数据用一个二维数组表示。如下，每一列被称为一个『维度』。
            //注意 new Date() 和 Date()的区别
            var now = new Date();
            var aData = {
                name: now,
                value: [
                    now,
                    data[0].collectionCount
                ]
            };
            var bData = {
                name: now,
                value: [
                    now,
                    data[0].collectionTime
                ]
            };
            ygcCount.push(aData);
            ygcTime.push(bData);
            var cData = {
                name: now,
                value: [
                    now,
                    data[1].collectionCount
                ]
            };
            var dData = {
                name: now,
                value: [
                    now,
                    data[1].collectionTime
                ]
            };
            ogcCount.push(cData);
            ogcTime.push(dData);
        }
    }, "json");
}