<%@page pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<html>
<head>
<script>
    $(function () {
        $("#altable").jqGrid({
            url : "${path}/album/selectAlbums",
            editurl:"${path}/album/edit",
            datatype : "json",
            height : "auto",
            autowidth: true,
            styleUI:"Bootstrap",
            colNames : [ '序号', '名字', '封面', '评分', '作者','播音员', '集数','简要','时间' ],
            colModel : [
                {name : 'id'},
                {name : 'title',editable:true},
                {name : 'cover_img',edittype:"file",editable:true,
                   /* width:180,height:80,fixed:true,
                    formatter:function (cellvalue) {
                        return "<img src='/img/"+cellvalue+"' style='width:150px;height:50px'/>";
                    },*/
                    editoptions:{enctype:"multipart/from-data"}},

                {name : 'score',editable:true},
                {name : 'author',editable:true},
                {name : 'broadcast',editable:true},
                {name : 'count',id:"count"},
                {name : 'content',editable:true},
                {name : 'pub_date'},
            ],
            rowNum : 2,
            rowList : [ 10, 20, 30 ],
            pager : '#alpager',
            sortname : 'id',
            viewrecords : true,  //是否展示总条数
            sortorder : "desc",  //降序排列
            multiselect : false,
            subGrid : true,  //开启自表格支持
            caption : "Grid as Subgrid",
            //subgrid_id:自表格id
            //row id :父表格的行id
            subGridRowExpanded :function(subgridId,rowId){
                addSubGrid(subgridId,rowId);
            },
        });
        //创建子表单
        function addSubGrid(subgridId,rowId) {
            //为了给自表格赋予表格id 以及分页id
            var subgridTableId=subgridId+"table";
            var pagerId=subgridId+"pager";
           //初始化表单，分页工具栏
            $("#" + subgridId).html("<table id='" + subgridTableId+ "'/><div id='"+ pagerId + "'/>");
            //创建表单
            $("#" + subgridTableId).jqGrid({
                    url :"${path}/chapter/selectChapters?id="+rowId,
                    editurl:"${path}/chapter/edit?albumId="+rowId,
                    datatype : "json",
                    colNames : [ '编号', '标题', '音频','大小','时长','上传时间','操作' ],
                    colModel : [
                        {name : "id"},
                        {name : "title",editable:true},
                        {name : "url",editable:true,edittype:"file",id:"url"},
                        {name : "size"},
                        {name : "duration"},
                        {name : "up_date"},
                        {name:"url",formatter:function (cellvalue) {
                                return "<span onclick='playerChapter(\""+cellvalue+"\")' class=\"glyphicon glyphicon-play-circle\"/>" +
                                 "&emsp;<span onclick='downloadChapter(\""+cellvalue+"\")'  class=\"glyphicon glyphicon-download\"/>"
                            }}
                    ],
                    rowNum : 2,
                    rowList : [ 2, 4, 6 ],
                    viewrecords : true,
                    pager : "#"+pagerId,
                    sortname : 'num',
                    sortorder : "asc",
                    autowidth:true,
                    height : 'auto',
                    styleUI: "Bootstrap"
                });
            /*子表格的增删改*/
            $("#" + subgridTableId).jqGrid('navGrid', "#" + pagerId,
                {edit: true,add : true,del : true},
                {
                    closeAfterEdit:true,
                    beforeShowForm:function(data){
                        data.find("#url").attr("disabled",true)//禁用input框
                    }
                },
                {
                    closeAfterAdd:true,
                    afterSubmit:function (data) {
                        //音频文件上传
                        $.ajaxFileUpload({
                            url:"${path}/chapter/uploadChapter",
                            type:"post",
                            dataType:"json",
                            fileElementId:"url",
                            data:{id:data.responseText},
                            success:function(data){
                                console.log(data);
                                //刷新表单
                                $("#altable").trigger("reloadGrid");
                            }
                        });
                        return "hello";
                    }
                },
                {
                    afterComplete:function (data) {
                        //alert("成功")
                        $("#altable").trigger("reloadGrid");

                    }
                }
            );
        };

        $("#altable").jqGrid('navGrid', '#alpager', {
            edit : true,
            add : true,
            del : true
        },
            {},
            {},
            {
                beforeShowForm:function () {
                    //选中的行的id
                    var rowId = $("#artable").jqGrid("getGridParam","selrow");
                    var row = $("#artable").jqGrid("getRowData",rowId);
                    alert("11")
                    alert("选中的行数据是"+row.contentEditable());

                    //获取选中行的个别属性的值
                    //alert("集数是："+$("#count").valid());
                    // if($("#count").)
                }

            }



        )

    })

    //音频下载
    function downloadChapter(fileName) {
        location.href="${path}/chapter/downloadChapter?fileName="+fileName;
    }
    //在线播放
    function playerChapter(fileName) {
        //展示模态框
        $("#audioModal").modal("show");
        //播放
        $("#playAudio").attr("src","${path}/music/"+fileName)
    }




</script>



</head>
<body>

<%--初始化面板--%>
<div class="panel panel-primary">
    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>专辑信息</h2>
    </div>

    <%--标签页--%>
    <div>

        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
             <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">专辑管理</a></li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="home">

                <%--按钮--%>
                <br/> &nbsp;&nbsp;&nbsp;&nbsp;
                <button class="btn btn-success" id="addbutton">添加专辑</button>
                <button class="btn btn-info" id="delbutton">删除专辑</button>
                <button class="btn btn-warning" id="updatebutton">修改专辑</button>
                <hr/>
                <%--警告框--%>
                <div id="show" class="alert alert-warning alert-dismissible" role="alert"  style="width:200px;display: none">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <strong id="showMsg"></strong>
                </div>
                <%--表格--%>
                <%--初始化表单--%>
                <table id="altable"/>
                <%--分页--%>
                <div id="alpager"></div>
                <%--模态框--%>
                    <div id="audioModal" class="modal fade" tabindex="-1" role="dialog">
                        <div class="modal-dialog" role="document">
                            <audio id="playAudio" src="" controls/>
                        </div>
                    </div>
            </div>
        </div>

    </div>

</div>
</body>
</html>