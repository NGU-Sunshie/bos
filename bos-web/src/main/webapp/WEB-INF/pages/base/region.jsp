<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>区域设置</title>
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
<script
	src="${pageContext.request.contextPath }/js/jquery.ocupload-1.1.2.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function doAdd() {
		$('#addRegionWindow').window("open");
	}

	function doView() {
		var select = $('#grid').datagrid("getSelected");
		if(select == null){
			$.messager.alert("提示信息","请选中修改的区域","warning");
		}else{
			$("#editRegionWindow").window("open");
			$("#editRegionForm").form("load",select);
		}
	}

	function doDelete() {
		var select = $('#grid').datagrid("getSelected");
		if(select != null){
			$.messager.confirm("删除确认","你确定要删除该区域吗?",function(r){
				//确认删除
				if(r){
					location.href = "regionAction_delete.action?id=" + select.id;
				}
			});
		}else{
			$.messager.alert("提示信息","请选中删除的区域","warning");
		}
		
	}

	//工具栏
	var toolbar = [ {
		id : 'button-edit',
		text : '修改',
		iconCls : 'icon-edit',
		handler : doView
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	}, {
		id : 'button-import',
		text : '导入',
		iconCls : 'icon-redo'
	} ];
	// 定义列
	var columns = [ [ {
		field : "field",
		checkbox : true,
	}, {
		field : 'id',
		title : '区域编号',
		width : 120,
		align : 'center'
		
	},{
		field : 'province',
		title : '省',
		width : 120,
		align : 'center'
	}, {
		field : 'city',
		title : '市',
		width : 120,
		align : 'center'
	}, {
		field : 'district',
		title : '区',
		width : 120,
		align : 'center'
	}, {
		field : 'postcode',
		title : '邮编',
		width : 120,
		align : 'center'
	}, {
		field : 'shortcode',
		title : '简码',
		width : 120,
		align : 'center'
	}, {
		field : 'citycode',
		title : '城市编码',
		width : 200,
		align : 'center'
	} ] ];

	$(function() {
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({
			visibility : "visible"
		});

		// 收派标准数据表格
		$('#grid').datagrid({
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pageList : [ 30, 50, 100 ],
			pagination : true,
			singleSelect:true,
			toolbar : toolbar,
			url : "regionAction_pageQuery.action",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		//页面加载完成后，调用OCUpload插件的方法
		$("#button-import").upload({
			action:'regionAction_importXls.action',
			name:'regionFile'
		});
		// 添加、修改区域窗口
		$('#addRegionWindow').window({
			title : '添加修改区域',
			width : 400,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});
		// 修改区域窗口
		$('#editRegionWindow').window({
			title : '修改区域',
			width : 400,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});

	});

	function doDblClickRow(rowIndex, rowData) {
		$("#editRegionWindow").window("open");
		$("#editRegionForm").form("load",rowData);
	}
</script>
</head>
<body class="easyui-layout" style="visibility: hidden;">
	<div region="center" border="false">
		<table id="grid"></table>
	</div>
	<!-- 区域添加窗口 -->
	<div class="easyui-window" title="区域添加修改" id="addRegionWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top: 20px; left: 200px">
		<div region="north" style="height: 31px; overflow: hidden;"
			split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton"
					plain="true">保存</a>
			</div>
			<script type="text/javascript">
				//页面加载完毕后,给保存按钮绑定事件
				$(function(){
					$("#save").click(function(){
						var v = $("#addRegionForm").form("validate");
						if (v){
							//发送ajax请求查看数据库是否存在该区域编号
							var URL = "regionAction_checkIdExists.action?";
							$.post(URL,{id:$("#regionId").val()},function(data){
								//该区域编号可以使用
								if(data){
									$("#addRegionForm").submit();
								}else{
									$.messager.alert("提示信息","该区域编号已经存在!","warning");
								}
							},"json");
						}
					});
				});
			</script>
		</div>

		<div region="center" style="overflow: auto; padding: 5px;"
			border="false">
			<form id="addRegionForm" action="regionAction_add.action" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">区域信息</td>
					</tr>
					<tr>
						<td>区域编号</td>
						<td><input type="text" name="id" id="regionId"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>省</td>
						<td><input type="text" name="province"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="city" class="easyui-validatebox"
							required="true" /></td>
					</tr>
					<tr>
						<td>区</td>
						<td><input type="text" name="district"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>邮编</td>
						<td><input type="text" name="postcode"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>简码</td>
						<td><input type="text" name="shortcode"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>城市编码</td>
						<td><input type="text" name="citycode"
							class="easyui-validatebox" required="true" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<!-- 区域修改窗口 -->
	<div class="easyui-window" title="修改" id="editRegionWindow"
		collapsible="false" minimizable="false" maximizable="false"
		style="top: 20px; left: 200px">
		<div region="north" style="height: 31px; overflow: hidden;"
			split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="edit" icon="icon-edit" href="#" class="easyui-linkbutton"
					plain="true">修改</a>
			</div>
			<script type="text/javascript">
				//页面加载完毕后,给保存按钮绑定事件
				$(function(){
					$("#edit").click(function(){
						var v = $("#editRegionForm").form("validate");
						if (v){
							$("#editRegionForm").submit();
						}
					});
				});
			</script>
		</div>

		<div region="center" style="overflow: auto; padding: 5px;"
			border="false">
			<form id="editRegionForm" action="regionAction_edit.action" method="post">
				<input type="hidden" name="id">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">区域信息</td>
					</tr>
					<tr>
						<td>省</td>
						
						<td><input type="text" name="province"
							class="easyui-validatebox" required="true" />
						</td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="city" class="easyui-validatebox"
							required="true" /></td>
					</tr>
					<tr>
						<td>区</td>
						<td><input type="text" name="district"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>邮编</td>
						<td><input type="text" name="postcode"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>简码</td>
						<td><input type="text" name="shortcode"
							class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>城市编码</td>
						<td><input type="text" name="citycode"
							class="easyui-validatebox" required="true" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>