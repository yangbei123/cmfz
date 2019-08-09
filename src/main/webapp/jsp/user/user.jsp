<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<html>
<head>
<script>
    $(function () {
        $("#ustable").jqGrid({
            url:"${path}/user/selectUsers",
            editurl:"${path}/user/updateUsers",
            datatype:"json",
            rowNum:2,
            autowidth:true,
            height:"auto",
            styleUI:"Bootstrap",
            rowList:[2,4,6,8],
            pager:"#uspager",
            viewrecords:true, //是否展示总条数
            colNames:["id","头像","名字","昵称","密码","性别","状态","手机号","注册时间"],
            colModel:[
                {name:'id',align:'center'},
                {name:'pic_img',align:'center',editable:true,edittype:"file",
                    width:180,height:80,fixed:true,
                    formatter:function (cellvalue) {
                        return "<img src='${path}/img/"+cellvalue+"' style='width:150px;height:50px'/>";
                    }
                },
                {name:'name',align:'center',editable:true},
                {name:'ahama',align:'center',editable:true},
                {name:'password',align:'center',editable:true},
                {name:'sex',align:'center',editable:true},
                {name:'status',align:'center',
                    formatter:function (cellvalue,option,row) {
                        if(cellvalue==1){
                            return "<button class='btn btn-success' onclick='change(\""+row.id+"\",\""+cellvalue+"\")'>正常</button>";
                        }else{
                            return "<button class='btn btn-warning' onclick='change(\""+row.id+"\",\""+cellvalue+"\")'>冻结</button>";
                        }
                    }
                
                
                },
                {name:'phone',align:'center',editable:true},
                {name:'reg_date',align:'center'}
                ]
        });
        $("#ustable").jqGrid('navGrid','#uspager',
            {edit:true,add:false,del:false,edittext:"编辑"},
            {
                 //提交之后，弹窗关闭
                afterSubmit:function (data) {  //提交之后的操作   返回的数据是
                    //文件上传
                    $.ajaxFileUpload({
                        url:"${path}/user/uploadimg",
                        type:"post",
                        //dataType:"json",
                        fileElementId:"pic_img",  //文件上传的路径
                        data:{id:data.responseText},  //数据返回的内容
                        success:function() {
                            //刷新表单
                            $("#ustable").trigger("reloadGrid");
                        }
                    });
                    return "hello";
                },
                closeAfterEdit:true
            }

            )


        $("#exbutton").click(function () {
            $.ajax({
                url:"${path}/user/exportUsers",
                success:function() {}
            })
        })
    })
    //修改状态
    function change(id,status) {
        if(status==1){
            $.ajax({
                url:"${path}/user/updateStatus",
                dataType:"text",
                data:{"id":id,"status":2},
                type:"post",
                success:function(data) {
                    //页面的刷新
                    $("#ustable").trigger("reloadGrid");
                    //提示框添加信息
                    $("#showMsgu").html(data.message);
                    //展示错误信息
                    $("#showu").show();

                    //设置提示框时间
                    setTimeout(function () {
                        //关闭提示框
                        $("#showu").hide();
                    }, 3000);

                }
            })
        }else{
            $.ajax({
                url:"${path}/user/updateStatus",
                //dataType:"JSON",
                data:{"id":id,"status":1},
                type:"post",
                success:function(data) {
                    //页面的刷新
                    $("#ustable").trigger("reloadGrid");
                    //提示框添加信息
                    $("#showMsgu").html(data.message);
                    //展示错误信息
                    $("#showu").show();

                    //设置提示框时间
                    setTimeout(function () {
                        //关闭提示框
                        $("#showu").hide();
                    }, 3000);
                }
            })

        }
    }
</script>
</head>
<body>

<%--初始化面板--%>
<div class="panel panel-info">
    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>用户信息</h2>
    </div>

    <%--标签页--%>
    <div>
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
             <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">用户管理</a></li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="home">

                <%--按钮--%>
                <br/> &nbsp;&nbsp;&nbsp;&nbsp;
                <button class="btn btn-success" id="exbutton">导出用户信息</button>
                <button class="btn btn-info" id="delbutton">导入用户</button>
                <button class="btn btn-warning" id="updatebutton">长测按钮</button>
                <hr/>
                <%--警告框--%>
                <div id="showu" class="alert alert-warning alert-dismissible" role="alert"  style="width:200px;display: none">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong id="showMsgu"></strong>
                </div>
                <%--表格--%>
                <%--初始化表单--%>
                <table id="ustable"/>
                <%--分页--%>
                <div id="uspager"></div>

            </div>
        </div>

    </div>

</div>
</body>
</html>