
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<script>
    $(function () {
        // 初始化用户数据表格
        $('#log-tt').jqGrid({
            url: '${path}/log/queryall',
            datatype: 'json',
            colNames: ['编号', '用户名', '操作日期','表名','操作','方法','数据id','数据','恢复数据'],
            styleUI: 'Bootstrap',
            colModel: [
                {name: 'id', editable: false},
                {name: 'username', editable: true},
                {name: 'operationAt', editable: true,edittype: 'date'},
                {name: 'tableName', editable: true},
                {
                    name: 'operationMethod',
                    editable: true,
                },
                {name: 'methodName', editable: true},
                {name: 'dataId', editable: true},
                {name: 'dataInfo', editable: true},
                {
                    name: 'history',
                    edittype: "file",
                    editable: true,
                    index: 'name asc, invdate',
                    search:false,
                    align: "center",
                    //参数：各子的值,操作,行对象
                    formatter: function (cellvalue, options, rowObject) {
                        //三个参数  列的值 ，操作 ，行对象
                        return "<a onclick='up(\"" + cellvalue + "\",\"" + rowObject.id + "\")' class='btn btn-success'>恢复数据</a>"
                    }
                },
            ],
            autowidth: true,
            mtype: 'get',
            pager: '#log-pager',
            rowList: [20, 50, 100],
            rowNum: 20,
            viewrecords: true,
            editurl: '${path}/log/edit', // 编辑表单提交的路径
            multiselect: true,
            height: '300px',
            rownumbers: true,
            page: 1,
            surl:'${path}/log/show',
            subgrid:true,
        }).navGrid('#log-pager', {
                edit: false,
                add: false,
                del: true,
                search: true,
                edittext: "修改",
                addtext: "添加",
                deltext: "删除",
                search: '搜索'
            },
            {
                closeAfterEdit:true
            },  //修改之后的额外操作
            {//添加之后的额外操作
                // 添加的同时提交额外参数
                editData:{id:''},
                closeAfterAdd: true,//关闭添加框
            },
            {},   //删除之后的额外操作
            {closeAfterSearch: true}
        );
    });
    function up(history, id) {

        //发送请求
        $.ajax({
            url: "${path}/log/huifu",
            type: "post",
            data: {"id": id},
            success: function () {
                    $("#log-tt").trigger("reloadGrid");
            }
        })
    }

</script>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>日志信息</h2>
    </div>

    <table id="log-tt"></table>
    <div id="log-pager"></div>

</div>
