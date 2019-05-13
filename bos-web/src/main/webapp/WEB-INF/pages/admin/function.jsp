<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		
		function doDelete() {
			//获取数据表格中所有选中的行，返回的是数组对象
			var rows = $('#grid').datagrid("getSelections");
			if (rows.length == 0) {
				$.messager.alert("提示信息", "请选择需要删除的权限", "warning");
			} else {
				//选中权限
				$.messager
						.confirm(
								"删除确认",
								"你确定要删除所选的权限吗?",
								function(r) {
									if (r) {
										//确认删除选中的权限,发送删除请求
										//获取所有权限的ID
										var array = new Array();
										for (var i = 0; i < rows.length; i++) {
											var staff = rows[i]; //json对象
											var id = staff.id;
											array.push(id);
										}
										var ids = array.join(",");//1,2,3,4
										location.href = "functionAction_deleteBatch.action?ids="
												+ ids;
									}
								});
			}
		}
		
		
		$("#grid").datagrid({
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pagination : true,
			toolbar : [
				{
					id : 'add',
					text : '添加权限',
					iconCls : 'icon-add',
					handler : function(){
						location.href='${pageContext.request.contextPath}/page_admin_function_add.action';
					}
				},{
					id : 'delete',
					text : '删除权限',
					iconCls : 'icon-cancel',
					handler : doDelete
				}           
			],
			url : 'functionAction_pageQuery.action',
			columns : [[
			  {
				  field : 'id',
				  title : '编号',
				  checkbox:true
			  },
			  {
				  field : 'name',
				  title : '名称',
				  width : 175
			  },  
			  {
				  field : 'description',
				  title : '描述',
				  width : 380
			  },  
			  {
				  field : 'generatemenu',
				  title : '是否生成菜单',
				  width : 100,
				  formatter : function(data, row, index) {
						if (data == "1") {
							return "是";
						} else {
							return "否";
						}
					}
			  },  
			  {
				  field : 'zindex',
				  title : '优先级',
				  width : 100
			  },  
			  {
				  field : 'page',
				  title : '路径',
				  width : 380
			  }
			]]
		});
	});
</script>	
</head>
<body class="easyui-layout">
<div data-options="region:'center'">
	<table id="grid"></table>
</div>
</body>
</html>