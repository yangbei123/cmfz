<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script charset="UTF-8" src="${path}/kindeditor/kindeditor-all.js"></script>
<script charset="UTF-8" src="${path}/kindeditor/lang/zh-CN.js"></script>
<script>
    KindEditor.ready(function (K) {
        K.create("#editor_id",{
            width : '900px',
            height:"600px",
            minWidth:"300",
            minHeight:"500",
            uploadJson:"${path}/editor/upload",
            filePostName:"photo",
            allowFileManager:true,
            fileManagerJson:"${path}/editor/queryAllPhoto",
        })
        K.create("#editor_id2",{
            width : '900px',
            height:"600px",
            minWidth:"300",
            minHeight:"500",
            uploadJson:"${path}/editor/upload",
            filePostName:"photo",
            allowFileManager:true,
            fileManagerJson:"${path}/editor/queryAllPhoto",
        })
    })


</script>
<textarea id="editor_id" name="content">
    html
</textarea>
<textarea id="editor_id2" name="content">
    html
</textarea>

