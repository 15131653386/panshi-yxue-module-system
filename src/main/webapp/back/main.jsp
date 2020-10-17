<%--
  Created by IntelliJ IDEA.
  User: 510教师机
  Date: 2020/9/23
  Time: 10:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"></c:set>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>应学后台管理系统</title>
    <link rel="shortcut icon" href="${path}/static/img/yx-icon.png" type="images/x-icon">
    <!--引入样式文件-->
    <link rel="stylesheet" href="${path}/static/bs/css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="${path}/static/jqgrid/css/trirand/ui.jqgrid-bootstrap.css" type="text/css">
    <link rel="stylesheet" href="${path}/static/yxue/yxue.css" type="text/css">

    <!--引入js功能文件-->
    <script src="${path}/static/bs/js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="${path}/static/bs/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="${path}/static/jqgrid/js/trirand/i18n/grid.locale-cn.js" type="text/javascript"></script>
    <script src="${path}/static/jqgrid/js/trirand/jquery.jqGrid.min.js" type="text/javascript"></script>
    <script src="${path}/static/yxue/yxue.js" type="text/javascript"></script>
    <%--引入jQuery文件上传插件--%>
    <script src="${path}/static/yxue/js/ajaxfileupload.js" type="text/javascript"></script>



    <script>
        $(function () {

        })
    </script>

    <style>

    </style>
</head>
<body>
<%--顶部导航--%>
<div class="navbar navbar-default" id="navbar-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a href="" class="navbar-left">
                <img src="${path}/static/img/yx-icon.png" alt="" width="48px" height="48px">
            </a>
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#menu-1">
                <span class="caret"></span>
            </button>
        </div>
        <div class="navbar-collapse collapse" id="menu-1">
            <ul class="nav navbar-nav">
                <li><a href="javascript:void(0)">应学视频APP后台管理系统</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="#">
                        欢迎：<span class="text text-primary">管理员</span>
                        &nbsp;<img src="static/img/4.gif" class="img-circle" alt="" width="24px" height="24px">
                    </a>
                </li>
                <li class="dropdown">
                    <a href="${path}/admin/logout" >退出 <span class="glyphicon glyphicon-log-out"></span></a>
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="container-fluid yx-nav-menu">
    <div class="row ">
        <!--导航菜单-->
        <div class="col-xs-2 col-md-2">
            <div class="panel panel-info ">
                <div class="panel-heading">
                    <a href="javascript:void(0)"> <span class="glyphicon glyphicon-menu-up"></span> 首页</a>
                </div>
                <div class="panel-body yx-nav-panel">

                    <div class="panel-group" id="acc">
                        <!--用户管理-->
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <a href="#user-panel-body" class="panel-title" data-toggle="collapse"
                                   data-parent="#acc"><span class="glyphicon glyphicon-user"></span> &nbsp;用户管理</a>
                            </div>

                            <div class="panel-collapse collapse" id="user-panel-body">
                                <div class="panel-body">
                                    <ul class="list-group">
                                        <li class="list-group-item">
                                            <a id="user-btn" href="javascript:$('#centerLayout').load('${path}/back/user.jsp')" class="btn btn-default yx-nav-menu-btn">
                                                &nbsp;查看用户
                                            </a>
                                        </li><br>
                                        <li class="list-group-item">
                                            <a id="user-btn2" href="javascript:$('#centerLayout').load('${path}/test/testEchartsGoEasy.jsp')" class="btn btn-default yx-nav-menu-btn">
                                                &nbsp;用户统计
                                            </a>
                                        </li><br>
                                        <li class="list-group-item">
                                            <a id="user-btn3" href="javascript:$('#centerLayout').load('${path}/test/testMap.jsp')" class="btn btn-default yx-nav-menu-btn">
                                                &nbsp;用户分布
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!--分类管理-->
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <a href="#category-panel-body" class="panel-title" data-toggle="collapse"
                                   data-parent="#acc"><span class="glyphicon glyphicon-tasks"></span> &nbsp;分类管理</a>
                            </div>

                            <div class="panel-collapse collapse" id="category-panel-body">
                                <div class="panel-body">
                                    <ul class="list-group">
                                        <li class="list-group-item">
                                            <a id="category-btn" href="javascript:$('#centerLayout').load('${path}/back/category.jsp')" class="btn btn-default yx-nav-menu-btn">
                                                &nbsp;分类
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!--视频管理-->

                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <a href="#video-panel-body" class="panel-title" data-toggle="collapse"
                                   data-parent="#acc"><span class="glyphicon glyphicon-film"></span>&nbsp;视频管理</a>
                            </div>

                            <div class="panel-collapse collapse" id="video-panel-body">
                                <div class="panel-body">
                                    <ul class="list-group">
                                        <li class="list-group-item">
                                            <a id="video-btn" href="javascript:$('#centerLayout').load('${path}/back/video.jsp')" class="btn btn-default yx-nav-menu-btn">  &nbsp;视频管理 </a>
                                        </li>
                                        <li class="list-group-item">
                                            <a id="video-btn2" href="javascript:$('#centerLayout').load('${path}/video/searchVideo.jsp')" class="btn btn-default yx-nav-menu-btn">  &nbsp;视频检索 </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!--评论管理-->
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <a href="#comment-panel-body" class="panel-title" data-toggle="collapse"
                                   data-parent="#acc"><span class="glyphicon glyphicon-comment"></span> &nbsp;评论管理</a>
                            </div>

                            <div class="panel-collapse collapse" id="comment-panel-body">
                                <div class="panel-body">
                                    <ul class="list-group">
                                        <li class="list-group-item">
                                            <a href="javascript:$('#centerLayout').load('${path}/back/comment.jsp')" class="btn btn-default yx-nav-menu-btn">  &nbsp;查看评论 </a>
                                        </li>

                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!--反馈信息管理-->
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <a href="#feedback-panel-body" class="panel-title" data-toggle="collapse"
                                   data-parent="#acc"><span class="glyphicon glyphicon-list-alt"></span> &nbsp;反馈信息管理</a>
                            </div>

                            <div class="panel-collapse collapse" id="feedback-panel-body">
                                <div class="panel-body">
                                    <ul class="list-group">
                                        <a href="javascript:$('#centerLayout').load('${path}/back/feedback.jsp')" class="btn btn-default yx-nav-menu-btn">  &nbsp;查看反馈 </a>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!--日志管理-->
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <a href="#log-panel-body" class="panel-title" data-toggle="collapse"
                                   data-parent="#acc"><span class="glyphicon glyphicon-list-alt"></span> &nbsp;日志管理</a>
                            </div>

                            <div class="panel-collapse collapse" id="log-panel-body">
                                <div class="panel-body">
                                    <a id="log-btn" href="javascript:$('#centerLayout').load('${path}/back/log.jsp')" class="btn btn-default yx-nav-menu-btn">  &nbsp;视频管理 </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>

        </div>
        <div class="col-xs-10 col-md-10" id="centerLayout">
            <div class="panel panel-info ">
                <div class="panel-heading">
                    <a class="">应学视频APP后台管理系统</a>
                </div>
                <div class="panel-body yx-nav-panel" id="yx-content">
                    <img src="${path}/static/img/echars.jpg" alt="">
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>






































