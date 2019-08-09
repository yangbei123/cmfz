<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<html>
<head>
    <style type="text/css">
        .aa{
            border: 1px solid  deepskyblue;
        }
        .bb{
            background-color: white;
        }


    </style>
    <script>
        $(function(){
            $("#btntable").jqGrid({
                url:"${path}/banner/showBanners",  //获取后台具体的数据
                editurl:"${path}/banner/edit",
                datatype:"json",    //设置后台响应给前台的具体格式
                rowNum:2,          //传递给后台每页显示的条数
                rowList:[2,4,6,8],   //一页可以展示的数据（选择）
                cellEdit:true,      //开启表格可编辑状态
                pager:"#pager",     //分页效果
                viewrecords:true,   //是否展示总条数
                //caption:"banner管理", //指定表格名称
                autowidth:true,//自适应宽度，占满整个浏览器
                height:"auto", //高度自定义
                styleUI:"Bootstrap",  //设置表格样式

                //指定对应的列名
                colNames:["序号","名称","路径","状态","上传时间","描述"],
                //给列渲染数据
                colModel:[
                    {name:"id",align:"center"},
                    {name:"title",align:"center",classes:"aa bb",editable:true},
                    {name:"img_path",align:"center",editable:true,edittype:"file",
                        width:180,height:80,fixed:true,
                        formatter:function (cellvalue) {
                            return "<img src='${path}/img/"+cellvalue+"' style='width:150px;height:50px'/>";
                        }},
                    {name:"status",align:"center",
                        formatter:function(cellvalue,option,row){  //该列值/jqgrid对象/该行对象
                            if(cellvalue=="激活"){
                                //展示状态
                                return "<button class='btn btn-success' onclick='change(\""+row.id+"\",\""+cellvalue+"\")'  >展示</button>";
                            }else{
                                //不展示状态
                                return "<button class='btn btn-danger' onclick='change(\""+row.id+"\",\""+cellvalue+"\")'  >不展示</button>";
                            }
                        },
                    },
                    {name:"up_date",align:"center",formatter:"date", formatoptions: {newformat:'Y-m-d'}},
                    {name:"description",align:"center",editable:true},
                ]
            });
            /*增删改查操作*/
            $("#btntable").jqGrid('navGrid','#pager',{
                edit:true,add:true,del:true,
                addtext:"添加",edittext:"编辑",deltext:"删除"},
                {//修改
                    closeAfterEdit:true, //提交之后，弹窗关闭
                    afterSubmit:function (data) {  //提交之后的操作
                        alert(data.responseText);
                       if(data.responseText!=""){
                            //文件上传
                            $.ajaxFileUpload({
                                url:"${path}/banner/uploadBanner",
                                type:"post",
                                //dataType:"json",
                                fileElementId:"img_path",  //文件上传的路径
                                data:{id:data.responseText},  //数据返回的内容
                                success:function() {
                                    //刷新表单
                                    $("#btntable").trigger("reloadGrid");
                                }
                            })
                           return "hello";
                       };
                    },
                },
                {//添加
                    afterSubmit:function (data) {  //提交之后的操作
                        //文件上传
                        $.ajaxFileUpload({
                            url:"${path}/banner/uploadBanner",
                            type:"post",
                            //dataType:"JSON",
                            fileElementId:"img_path",  //文件上传的路径
                            data:{id:data.responseText},  //数据返回的内容
                            success:function() {
                                //刷新表单
                                $("#btntable").trigger("reloadGrid");

                            }
                        })
                        return "hello";
                    },
                    closeAfterAdd:true  //提交之后，弹窗关闭
                },
                {
                    //删除
                }
            );

            $("#addbutton").click(function() {
                $("#btntable").jqGrid('editGridRow', "new", {
                    height : 300,
                    reloadAfterSubmit : true,
                    afterSubmit:function (data) {  //提交之后的操作
                        //文件上传
                        $.ajaxFileUpload({
                            url:"${path}/banner/uploadBanner",
                            type:"post",
                            //dataType:"JSON",
                            fileElementId:"img_path",  //文件上传的路径
                            data:{id:data.responseText},  //数据返回的内容
                            success:function() {
                                //刷新表单
                                $("#btntable").trigger("reloadGrid");

                            }
                        })
                        return "hello";
                    },
                    closeAfterAdd:true  //提交之后，弹窗关闭

                });
            });

            $("#delbutton").click(function() {
                var gr = $("#btntable").jqGrid('getGridParam', 'selrow');
                if (gr != null)
                    jQuery("#btntable").jqGrid('delGridRow', gr, {
                        reloadAfterSubmit : true
                    });
                else
                    alert("请选中一行");
            });

            $("#updatebutton").click(function() {
                var gr = jQuery("#btntable").jqGrid('getGridParam', 'selrow');
                if (gr != null)
                    jQuery("#btntable").jqGrid('editGridRow', gr, {
                        height : 300,
                        reloadAfterSubmit : true,  //添加完数据重新发送请求

                        closeAfterEdit:true, //提交之后，弹窗关闭
                        afterSubmit:function (data) {  //提交之后的操作
                            if(data.responseText!=""){
                                //文件上传
                                $.ajaxFileUpload({
                                    url:"${path}/banner/uploadBanner",
                                    type:"post",
                                    //dataType:"json",
                                    fileElementId:"img_path",  //文件上传的路径
                                    data:{id:data.responseText},  //数据返回的内容
                                    success:function() {
                                        //刷新表单
                                        $("#btntable").trigger("reloadGrid");
                                    }
                                })
                                return "hello";
                            }else{
                                return "hello111";
                            };
                        },
                    });
                else
                    alert("请选中一行");
            });



        })

        //状态改变
        function change(id,status) {
            if(status=="激活"){
                $.ajax({
                    url:"${path}/banner/updateStatus",
                    type:"post",
                    dataType:"text",
                    data:"id="+id+"&status=冻结",
                    success:function (data) {
                        //页面刷新
                        $("#btntable").trigger("reloadGrid");
                        //提示框添加信息
                        $("#showMsg").html(data);
                        //展示信息
                        $("#show").show();
                        //设置提示框时间
                        setTimeout(function () {
                            $("#show").hide();
                        },3000)
                    }
                })
            }else{
                $.ajax({
                    url:"${path}/banner/updateStatus",
                    type:"post",
                    dataType:"text",
                    data:{"id":id,"status":"激活"},
                    success:function (data) {
                        //页面刷新
                        $("#btntable").trigger("reloadGrid");
                        //提示框添加信息
                        $("#showMsg").html(data);
                        //展示信息
                        $("#show").show();
                        //设置提示框时间
                        setTimeout(function () {
                            $("#show").hide();
                        },3000)
                    }
                })

            }
        }
     </script>
</head>
<body>


<%--初始化面板--%>
<div class="panel panel-success">
    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>轮播图信息</h2>
    </div>

    <%--标签页--%>
        <div>

            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">轮播图信息</a></li>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="home">

                    <%--按钮--%>
                    <br/> &nbsp;&nbsp;&nbsp;&nbsp;
                    <button class="btn btn-success" id="addbutton">添加轮播图</button>
                    <button class="btn btn-info" id="delbutton">删除轮播图</button>
                    <button class="btn btn-warning" id="updatebutton">修改轮播图</button>
                    <hr/>
                        <%--警告框--%>
                        <div id="show" class="alert alert-warning alert-dismissible" role="alert"  style="width:200px;display: none">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <strong id="showMsg"></strong>
                        </div>
                    <%--表格--%>
                        <%--初始化表单--%>
                    <table id="btntable"/>
                        <%--分页--%>
                    <div id="pager"></div>
                </div>
            </div>

        </div>

</div>
</body>
</html>