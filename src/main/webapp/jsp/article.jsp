<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<script charset="utf-8" src="${path}/kindeditor/kindeditor-all.js"></script>
<script charset="utf-8" src="${path}/kindeditor/lang/zh-CN.js"></script>
<html>
<head>
    <script>
        KindEditor.create('#editor_id', {  //创建符文本编辑器，显示出来
            //上传图片
            uploadJson:"${path}/kindedit/upload",  //删除路径
            filePostName:"photo",                //上传文件对应的变量名
            //本地库
            allowFileManager:true,               //显示本地库
            fileManagerJson:"${path}/kindedit/queryAllPhoto",   //显示路径
            afterBlur:function (){  //编辑器失去焦点(blur)时执行的回调函数。
                this.sync();  //将编辑器中的内容同步到表单
            }
        });
    </script>
    <script>
        $(function () {
            $("#artable").jqGrid({
                url:"${path}/article/selectArticles",  //获取后台具体的数据
                editurl:"${path}/article/exitArticle",
                datatype:"json",    //设置后台响应给前台的具体格式
                rowNum:2,          //传递给后台每页显示的条数
                rowList:[2,4,6,8],   //一页可以展示的数据（选择）
                cellEdit:true,      //开启表格可编辑状态
                pager:"#arpager",     //分页效果
                viewrecords:true,   //是否展示总条数
                //caption:"banner管理", //指定表格名称
                autowidth:true,//自适应宽度，占满整个浏览器
                height:"auto", //高度自定义
                styleUI:"Bootstrap",  //设置表格样式

                //指定对应的列名
                colNames:["id","封面","标题","内容","创建时间","上传时间","状态","上师id","操作"],
                //给列渲染数据
                colModel:[
                    {name:"id",hidden:true},
                    {name:"insert_img",align:"center",editable:true,edittype:"file",
                        width:180,height:80,fixed:true,
                        formatter:function (cellvalue) {
                            return "<img src='${path}/img/"+cellvalue+"' style='width:150px;height:50px'/>";
                        }},
                    {name:"title",align:"center",editable:true},
                    {name:"content",align:"center"},
                    {name:"up_date",align:"center",formatter:"date", formatoptions: {newformat:'Y-m-d'}},
                    {name:"create_date",align:"center",formatter:"date", formatoptions: {newformat:'Y-m-d'}},
                    
                    {name:"status",align:"center",
                        formatter:function(cellvalue,option,row){  //该列值/jqgrid对象/该行对象
                            if(cellvalue=="1"){
                                //展示状态
                                return "<button class='btn btn-success' onclick='change(\""+row.id+"\",\""+cellvalue+"\")'  >展示</button>";
                            }else{
                                //不展示状态
                                return "<button class='btn btn-danger' onclick='change(\""+row.id+"\",\""+cellvalue+"\")'  >不展示</button>";
                            }
                        },
                    },
                    {name:"guruId",align:"center",editable:true},
                    {name:"guruId",align:"center"},

                ]
            });
            $("#artable").jqGrid("navGrid","#arpager",{edit:true,add:true,del:true,})

            $("#contentbutton").click(function () { //添加
                //选中一行，获取行id
                var rowId = $("#artable").jqGrid("getGridParam","selrow");
                //判断是否选中行
                if(rowId!=null){
                    //根据行id选中行数据
                    var row = $("#artable").jqGrid("getRowData",rowId);
                    //展示模态框
                    $("#modal").modal("show");
                    //给input框设置内容
                    $("#title").val(row.title);
                    //给KindEditor框设置内容
                    KindEditor.html("#editor_id",row.content);
                    //添加按钮
                    $("#modalFooter").html("<button type='button' onclick='updateArticle(\""+rowId+"\")' class='btn btn-default'>提交</button><button type='button' class='btn btn-primary' data-dismiss='modal'>关闭</button>")
                }else{
                    alert("请选中一行数据");
                }
                });

            $("#addbutton").click(function () {
                //给表单置空
                $("#form")[0].reset();
                //给KindEditor框置空
                KindEditor.html("#editor_id","");
                //展示模态框
                $("#modal").modal("show");
                //添加按钮
                $("#modalFooter").html("<button type='button' onclick='addArticle()' class='btn btn-default'>提交" +
                    "</button><button type='button' class='btn btn-primary' data-dismiss='modal'>关闭</button>");

            })
            $("#delbutton").click(function () { //添加
                //选中一行，获取行id
                var rowId = $("#artable").jqGrid("getGridParam","selrow");
                //判断是否选中行
                if(rowId!=null){
                    $.ajax({
                        url:"${path}/article/del?id="+rowId,
                        type:"post",
                        dateType:"json",
                        data:$("#form").serialize(),
                        success:function () {
                            $("#artable").trigger("reloadGrid");//刷新表单
                        }
                    })
                }else{
                    alert("请选中一行数据");
                }
            });

        })
    /*修改信息向后抬提交*/
    function updateArticle(rowId) {
        $.ajax({
            url:"${path}/article/upload?id="+rowId,
            type:"post",
            dateType:"json",
            data:$("#form").serialize(),
            success:function () {
                $("#modal").modal('hide');//隐藏模态框
                $("#artable").trigger("reloadGrid");//刷新表单
            }
        })
    }
    /*添加*/
        function addArticle() {
            $.ajax({
                url:"${path}/article/add?",
                type:"post",
                dateType:"json",
                data:$("#form").serialize(),
                success:function () {
                    $("#modal").modal('hide');//隐藏模态框
                    $("#artable").trigger("reloadGrid");//刷新表单
                }
            })
        }
    
        function change(rowId,status){
            if(status==1){
                $.ajax({
                    url:"${path}/article/updatestatus",
                    type:"post",
                    dateType:"json",
                    data:"id="+rowId+"&status=2",
                    success:function (data) {
                        $("#artable").trigger("reloadGrid");//刷新表单
                    }
                })
            }else{
                $.ajax({
                    url:"${path}/article/updatestatus",
                    type:"post",
                    dateType:"json",
                    data:"id="+rowId+"&status=1",
                    success:function (data) {
                        $("#artable").trigger("reloadGrid");//刷新表单
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
        <h2>文章管理</h2>
    </div>

    <%--标签页--%>
        <div>

            <!-- Nav tabs -->
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">文章信息</a></li>
            </ul>

            <!-- Tab panes -->
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="home">

                    <%--按钮--%>
                    <br/> &nbsp;&nbsp;&nbsp;&nbsp;
                    <button class="btn btn-success" id="contentbutton">文章信息</button>
                    <button class="btn btn-info" id="addbutton">添加文章</button>
                    <button class="btn btn-warning" id="delbutton">删除文章</button>
                    <hr/>
                        <%--警告框--%>
                        <div id="show" class="alert alert-warning alert-dismissible" role="alert"  style="width:200px;display: none">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <strong id="showMsg"></strong>
                        </div>
                    <%--表格--%>
                        <%--初始化表单--%>
                    <table id="artable"/>
                        <%--分页--%>
                    <div id="arpager"></div>

                    <%--模态框--%>

                    <div id="modal" class="modal fade" role="dialog">
                        <div class="modal-dialog" role="document" style="width: 730px">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title">修改文章</h4>
                                </div>
                                <div class="modal-body">
                                    <%--表单--%>
                                        <form id="form" method="post" enctype="multipart/form-data">
                                            <div class="form-group">
                                                <label>Title</label>
                                                <input class="form-control" id="title" name="title"/>
                                                <label>Cover</label>
                                                <input class="form-control" type="file" id="insert_img" name="insert_img"/>
                                            </div>

                                            <%--富文本编辑器--%>
                                            <textarea id="editor_id" name="content" style="width:700px;height:300px;">

                                            </textarea>
                                        </form>

                                </div>
                                <div class="modal-footer" id="modalFooter">
                                    <%--<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                    <button type="button" class="btn btn-primary">保存</button>--%>
                                </div>
                            </div><!-- /.modal-content -->
                        </div><!-- /.modal-dialog -->
                    </div><!-- /.modal -->

                </div>
            </div>

        </div>

</div>




</body>
</html>