<%--
  Created by IntelliJ IDEA.
  User: 510教师机
  Date: 2020/9/24
  Time: 9:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<script>
    $(function(){
        pageInit();
    });
    function pageInit(){
        jQuery("#tb").jqGrid(
            {
                url :'${pageContext.request.contextPath}/category/queryall',
                datatype : "json",
                styleUI: 'Bootstrap',
                height : 400,
                colNames : [ 'id', '类别名称', '级别'],
                colModel : [
                    {name : 'id',index : 'id',  width : 55},
                    {name : 'name',index : 'name',width : 90,editable: true},
                    {name : 'level',index : 'level',width : 100},
                ],
                rowNum : 3,
                autowidth:true,
                rowList : [ 3, 2, 10 ],
                pager : '#pg',
                sortname : 'id',
                viewrecords : true,
                editurl: '${path}/category/edit',
                sortorder : "desc",
                multiselect : false,
                subGrid : true,
                caption : "",
                subGridRowExpanded : function(subgrid_id, row_id) {
                    var subgrid_table_id, pager_id;
                    subgrid_table_id = subgrid_id + "_t";
                    pager_id = "p_" + subgrid_table_id;
                    $("#" + subgrid_id).html(
                        "<table id='" + subgrid_table_id
                        + "' class='scroll'></table><div id='"
                        + pager_id + "' class='scroll'></div>");
                    jQuery("#" + subgrid_table_id).jqGrid(
                        {
                            url : "${pageContext.request.contextPath}/category/querytwo?qId=" + row_id,
                            datatype : "json",
                            colNames : [ 'id', '类别名称', '类别'],
                            colModel : [
                                {name : "id",  index : "id",key : true},
                                {name : "name",index : "name",editable: true},
                                {name : "level",index : "level",align : "right"},
                            ],
                            rowNum : 6,
                            pager : pager_id,
                            sortname : 'num',
                            sortorder : "asc",
                            editurl: '${path}/category/edittwo?qId=' + row_id,
                            styleUI: 'Bootstrap',
                            height : '100%',
                            width:'900',
                        });
                    jQuery("#" + subgrid_table_id).navGrid("#" + pager_id, {
                            edit : true,
                            add : true,
                            del : true
                        },{closeAfterEdit:true},{closeAfterAdd:true},{closeAfterDel:true});
                },
                subGridRowColapsed : function(subgrid_id, row_id) {
                }
            });
        jQuery("#tb").navGrid('#pg', {
            edit : true,
            add : true,
            del : true,
        },{closeAfterEdit:true},{closeAfterAdd:true},{closeAfterDel:true});
    }
</script>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>分类信息</h2>
    </div>
    <table id="tb"></table>
    <div id="pg"></div>

</div>



