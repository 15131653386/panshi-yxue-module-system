<%@page contentType="text/html; UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="static/bs/css/bootstrap.css" type="text/css">
    <link rel="stylesheet" href="static/jqgrid/css/trirand/ui.jqgrid-bootstrap.css" type="text/css">

    <script type="text/javascript" src="static/bs/js/jquery-3.3.1.min.js"></script>
    <script type="text/javascript" src="static/bs/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="static/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="static/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>

    <script>
        $(function () {
            $('#tt').jqGrid({
                /*加入一个整合的属性*/
                styleUI: 'Bootstrap',
                url: '/supp/findByPage',
                datatype: 'json',
                colNames: ['供应商ID', '供应商名称', '负责人', '联系电话'],
                cellEdit: false,
                editurl: '/supp/edit',
                colModel: [
                    {name: 'id'},
                    {name: 'name', editable: true},
                    {name: 'leader', editable: true},
                    {name: 'phone', editable: true},
                ],
                autowidth: true,
                pager: '#tool',
                rowList: [1, 2, 3, 4, 5],
                rowNum: 3,
                page: 1,
                toolbar: [true, 'both'],
                multiselect: true
            }).navGrid('#tool', {}, {}, {
                    // 提交额外的数据在添加的时候
                    editData: {id: ''}
                }, {closeAfterEdit: true},
                {closeAfterAdd: true});
        })
    </script>

</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-lg-3">
            <ul class="list-group">
                <li class="list-group-item">供应商信息管理</li>
                <li class="list-group-item">客户信息管理</li>
                <li class="list-group-item">货物信息管理</li>
                <li class="list-group-item">仓库信息管理</li>
            </ul>
        </div>
        <div class="col-lg-9">
            <table id="tt" class="table-hover"></table>
            <div id="tool"></div>
        </div>
    </div>
</div>


</body>
</html>