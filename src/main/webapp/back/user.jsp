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
            url: '${path}/user/queryall',
            datatype: 'json',
            colNames: ['编号', '用户名', '手机号','签名','头像','注册时间','学分','用户状态'],
            styleUI: 'Bootstrap',
            colModel: [
                {name: 'id', editable: false},
                {name: 'username', editable: true},
                {name: 'mobile', editable: true},
                {name: 'sign', editable: true},
                {
                    name: 'headShow',
                    edittype: "file",
                    editable: true,
                    index: 'name asc, invdate',
                    width: 100,
                    align: "center",
                    //参数：各子的值,操作,行对象
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img src='${path}/unload/" + cellvalue + "' width='64px' height='64px'   >"
                    }
                },
                {name: 'regTime', editable: true,edittype: 'date'},
                {name: 'score', editable: true},
                {
                    name: 'status', width: 150, sortable: false,editable: true,edittype:'select',editoptions:{value:"f:冻结;t:正常"},
                    formatter: function (cellvalue, options, rowObject) {
                        //三个参数  列的值 ，操作 ，行对象
                        if (cellvalue == "t") {
                            return "<a onclick='updateStatus(\"" + cellvalue + "\",\"" + rowObject.id + "\")' class='btn btn-success'>正常</a>"
                        } else {
                            return "<a onclick='updateStatus(\"" + cellvalue + "\",\"" + rowObject.id + "\")' class='btn btn-danger'>冻结</a>"
                        }
                    }
                },
            ],
            autowidth: true,
            mtype: 'get',
            pager: '#user-pager',
            rowList: [3, 5, 10],
            rowNum:3,
            viewrecords: true,
            editurl: '${path}/user/edit', // 编辑表单提交的路径
            multiselect: true,
            height: '300px',
            rownumbers: true,
            page: 1,
            surl:'${path}/user/queryall',
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
                afterSubmit: function (data) {
                    console.log(data.responseJSON.rows.id)
                    let id = data.responseJSON.rows.id;
                    $.ajaxFileUpload({
                        url: "${path}/user/headUp",
                        type: "post",
                        /**
                         * 需要在上传文件的时候，提交一个新添加数据的id,
                         *  由于我们是在信息添加成功后处理文件上传 ，所以需要根据id
                         *  修改一些文件在服务器的保存路径
                         */
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
            },
            {closeAfterDel: true,}, //删除之后的额外操作
            {closeAfterSearch: true,}//查询之后关闭搜索框

        );
    });

    function updateStatus(status, id) {
        //修改状态
        if (status == "t") {
            //发送请求
            $.ajax({
                url: "${path}/user/edit",
                type: "post",
                data: {"id": id, "status": "f", "oper": "edit"},
                success: function () {
                    //刷新表单
                    $("#user-tt").trigger("reloadGrid");
        }
            })
        } else {
            //发送请求
            $.ajax({
                url: "${path}/user/edit",
                type: "post",
                data: {"id": id, "status": "t", "oper": "edit"},
                success: function () {
                    //刷新表单
                    $("#user-tt").trigger("reloadGrid");
                }
            })
        }
    }
</script>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>用户信息</h2>
    </div>
    <div class="panel-body">
        <a class="btn btn-success btn-sm" href="${path}/user/exportUtil" download=""
           title="下载"  mce_href="#"><i class="fa fa-arrow-circle-down"></i>excel导出</a>
    </div>
    <table id="user-tt"></table>
    <div id="user-pager"></div>

</div>



