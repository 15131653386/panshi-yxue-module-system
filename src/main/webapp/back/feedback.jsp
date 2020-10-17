<%--
  Created by IntelliJ IDEA.
  User: 510教师机
  Date: 2020/9/24
  Time: 9:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<script>
    $(function () {
        // 初始化用户数据表格
        $('#user-tt').jqGrid({
            url: '${path}/feedback/queryall',
            datatype: 'json',
            colNames: ['编号', '反馈标题', '反馈内容','反馈时间','反馈者'],
            styleUI: 'Bootstrap',
            colModel: [
                {name: 'id', editable: false},
                {name: 'title', editable: true},
                {name: 'content', editable: true},
                {name: 'createAt', editable: true,edittype: 'date'},
                {name: 'userId', editable: true},

            ],
            autowidth: true,
            mtype: 'get',
            pager: '#user-pager',
            rowList: [3, 5, 10],
            rowNum:3,
            viewrecords: true,
            editurl: '${path}/feedback/edit', // 编辑表单提交的路径
            multiselect: true,
            height: '300px',
            rownumbers: true,
            page: 1,
            surl:'${path}/feedback/queryall',
            subgrid:true,
        }).navGrid('#user-pager', {
                edit: true,
                add: true,
                del: true,
                Search:true,
                edittext: "修改",
                addtext: "添加",
                deltext: "删除",
                Searchtext:"查询"
            },
            {
                closeAfterEdit:true,
                afterSubmit: function (data) {
                    console.log(data.responseJSON.rows.id)
                    let id = data.responseJSON.rows.id;
                    let img=data.responseJSON.rows.headShow;
                    $.ajaxFileUpload({
                        url: "${path}/user/headUpload",
                        type: "post",
                        data: {"id": id},
                        fileElementId: "headShow", //指定文件上传表单元素input的name属性值
                        success: function () {
                            //上传成功 所作的事情
                            //刷新页面
                            $("#user-tt").trigger("reloadGrid");
                        }
                    });
                    return ['ok'];
                }
            },  //修改之后的额外操作
            {//添加之后的额外操作
                // 添加的同时提交额外参数

                editData:{id:''},
                closeAfterAdd: true,//关闭添加框
            },
            {closeAfterDel: true,}, //删除之后的额外操作
            {closeAfterSearch: true,}//查询之后关闭搜索框

        );
    });
    $(function () {

        $('#upfile_btn').click(function () {
            // 获取上传文件的名字
            let upFileName = $('#upFile').val();
            // 获取上传文件的后缀
            let extention = upFileName.substring(upFileName.lastIndexOf('.') + 1);
            console.log(extention);
             if (extention == 'xls') {
                 let formData = new FormData($('#fom')[0]);
                 // ajax实现文件上传
                 $.ajax({
                     url: '${path}/feedback/import',
                     type: 'POST',
                     contentType: false,// 不处理请求头的类型，使用表单上书写的multipart/form-data
                     processData: false, // 不使用传统的方式提交数据，以multipart/form-data 声明的方式提交数据
                     data: formData,
                     success: function (data) {
                         $("#user-tt").trigger("reloadGrid");
                     },
                 })
            } else {
                alert('文件格式不对！~')
            }

        })
    })
</script>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>用户信息</h2>
    </div>
    <div class="panel-body">
        <a class="btn btn-success btn-sm" href="${path}/feedback/exportUtil" download=""
           title="下载"  mce_href="#"><i class="fa fa-arrow-circle-down"></i>excel导出</a>
        <form id="fom" method="post" enctype="multipart/form-data">
            <input type="file" name="multipartFile"  id="upFile">
            <input class="btn btn-success btn-sm" type="submit" value="导入" id="upfile_btn">
        </form>
    </div>
    <table id="user-tt"></table>
    <div id="user-pager"></div>

</div>



