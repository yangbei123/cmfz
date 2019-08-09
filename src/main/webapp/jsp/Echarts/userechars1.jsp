<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />


    <script src="${path}/js/jquery.min.js"></script>
    <%--javascript方式获取SDK--%>
    <script src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
    <%--引入echarts文件--%>
    <script src="${path}/js/echarts.js"></script>



    <script>

        $(document).ready(function() {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            var goEasy = new GoEasy({
                appkey: "BC-d703e907d2dc49108764102bd125c436" //你自己的appkeys
            });
            goEasy.subscribe({
                channel: "myChannel158", //管道标识
                onMessage: function (message) {
                     var data = JSON.parse(message.content);
                     console.log(data);
                    //因为下面需要的是一个集合。所以只需返回一个map集合即可
                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            text: '用户注册量展示', //标题
                            link: "/main/main.jsp",
                            target: "self",
                            subtext: "用户信息"
                        },
                        tooltip: {},  //鼠标的提示
                        legend: {
                            // show:false,  是否展示 选项
                            data: ['小男孩', "小女孩"]  //选项
                        },
                        xAxis: {
                            data: data.month  //横坐标
                        },
                        yAxis: {},  //纵坐标   自适应
                        series: [{
                            name: '小男孩',
                            type: 'bar',
                            data: data.boys
                        }, {
                            name: '小女孩',
                            type: 'line',
                            data: data.girls
                        }]
                    };
                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }
            })
        })

    </script>
    <script type="application/javascript">
        $(function () {
            //触发
            /*$.ajax({
                 url: "/user/changUserCount",
                 type: "post",
                 success: function () {
                     alert("触发了")
                 }
             })*/
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));
            //使用ajax来获取后面的数据
            $.ajax({
                url:"${path}/user/selectUsersByMonth",
                type:"post",
                dataType:"json",
                success:function(data) {
                    alert("ajax返回")
                    //因为下面需要的是一个集合。所以只需返回一个map集合即可
                    // 指定图表的配置项和数据
                    //console.log(data);
                    var option = {
                        title: {
                            text: '用户注册量展示', //标题
                            link:"/main/main.jsp",
                            target:"self",
                            subtext:"用户信息"
                        },
                        tooltip: {},  //鼠标的提示
                        legend: {
                            // show:false,  是否展示 选项
                            data:['小男孩',"小女孩"]  //选项
                        },
                        xAxis: {
                            data: data.month  //横坐标
                        },
                        yAxis: {},  //纵坐标   自适应
                        series: [{
                            name: '小男孩',
                            type: 'bar',
                            data: data.boys
                        },{
                            name: '小女孩',
                            type: 'line',
                            data: data.girls
                        }]
                    };
                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }
            })
            $("#button").click(function () {

                $.ajax({
                    url: "${path}/user/changUserCount",
                    type: "post",
                    success: function () {
                    }
                })



            })
        })
    </script>


<%--为echarts准备一个具备大小（宽高）的DOM--%>
<div id="main" style="width: 600px;height: 400px;"></div>

<input type="button" id="button" name="change">
</html>