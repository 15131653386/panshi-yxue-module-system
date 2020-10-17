<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>
<script>
    $(function () {
        // 初始化视频数据表格
        $('#video-tt').jqGrid({
            url: '${path}/quera/queryall',
            datatype: 'json',
            colNames: ['id', '视频标题','简介','视频','发布时间','发布者','所属类别名称','分组名称','点赞数量','播放量'],
            styleUI: 'Bootstrap',
            colModel: [
                {'name': 'id',width: 300,editable:false},
                {'name': 'title', editable: true,width: 100},
                {'name': 'intro', editable: true,width: 200},
                {name: 'videoUrl',
                    editable: true,
                    edittype: "file",
                    width: 350,
                    height:200,
                    formatter: function (cellvalue, options, rowObject) {
                        return "<video width='200px' height='120px' src=\"" + cellvalue + "\" controls poster='${path}/unload/" + rowObject.coverUrl + "'/>";
                    }
                },
                {name: 'createAt', editable: true,edittype: 'date'},
                {name: 'user.username', editable: true,edittype:'select',editoptions:{value:"265956673ce0f78ae2c7df9f36fddf14:小明;9e31b938a082b0ae308be9ee4691ad49:小蓝;a2d85a23fde78545c22b5ec2ac0b02bf:小刚;c67deb4f12aa694c8ad27019c3e52838:小天;f0357c7855212cf6d9cb2415ffcb9c5d:小智;e1b22807c7a0fee60b6b22d7077e9aab:小紫"}},
                {name: 'category.name', editable: true,edittype:'select',editoptions:{value:"237b53a2857c377e51e2505cb94afec3:java;3618dbee735e95fa0e81cfb4c2d041e4:c++;aca785562b30021293dde24b49b28ef5:动画片"}},
                {name: 'group.name', editable: true,edittype:'select',editoptions:{value:"0d0ffe8118df79ec7f954442a03d9049:视频"}},
                {name: 'like', editable: true},
                {name: 'play.playNum', editable: true},



            ],
            autowidth: true,
            mtype: 'get',
            pager: '#video-pager',
            rowList: [3, 5, 10],
            rowNum:3,
            viewrecords: true,
            editurl: '${path}/quera/edit',
            surl:'${path}/quera/queryall',
            multiselect: true,
            height: '400px',
            rownumbers: true,
            page: 1,
        }).navGrid('#video-pager', {
            edit: true,
            add: true,
            del: true,
            Search:true,
            edittext: "修改",
            addtext: "添加",
            deltext: "删除",
            Searchtext:"查询"
        },{ closeAfterEdit: true},
            {//添加之后的额外操作
                // 添加的同时提交额外参数

                editData:{id:''},
                closeAfterAdd: true,//关闭添加框
                afterSubmit: function (data) {
                    console.log(data.responseJSON.rows.id)
                    let id = data.responseJSON.rows.id;
                    $.ajaxFileUpload({
                        url: "${path}/quera/filevideo",
                        type: "post",
                        /**
                         * 需要在上传文件的时候，提交一个新添加数据的id,
                         *  由于我们是在信息添加成功后处理文件上传 ，所以需要根据id
                         *  修改一些文件在服务器的保存路径
                         */
                        data: {"id": id},
                        fileElementId: "videoUrl", //指定文件上传表单元素input的name属性值
                        success: function () {
                            //上传成功 所作的事情
                            //刷新页面
                            $("video-tt").trigger("reloadGrid");
                        }
                    });
                    return ['ok'];
                }
            },
        )
    });

</script>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>视频信息</h2>
    </div>

    <table id="video-tt"></table>
    <div id="video-pager"></div>

</div>
