<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ztree</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north'" style="height: 100px"
		title="sunshine管理系统">北部</div>
	<div data-options="region:'south'" style="height: 100px">南部</div>
	<div data-options="region:'west'" style="width: 200px">
		<div class="easyui-accordion" data-options="fit:true" >
			<div title="面板1" data-options="iconCls:'icon-ok'">
				<%--使用ztree --%>
				<ul class="ztree" id="ztree1"></ul>
				<script type="text/javascript">
					$(function(){
						var setting ={
								data: {
									simpleData: {
										enable: true//使用简单json数据构造ztree节点
									}
								}
						};
						var zNodes = [
							              {"id":"1","pId":"2","name":"节点一"},//每个json对象表示一个节点数据
							              {"id":"2","pId":"3","name":"节点二"},
							              {"id":"3","pId":"0","name":"节点三"}
						              ];
						//调用API初始化ztree
						$.fn.zTree.init($("#ztree1"), setting, zNodes);
					});
				</script>

			</div>
			<div title="面板2" data-options="selected:true">
				<%--使用ztree --%>
				<ul class="ztree" id="ztree2"></ul>
				<script type="text/javascript">
					$(function(){
						var setting2 ={
								data: {
									simpleData: {
										enable: true//使用简单json数据构造ztree节点
									}
								},
								callback: {
									onClick:function (event, treeId, treeNode) {
										if(treeNode.page!=undefined){
											var e = $("#mytabs").tabs("exists",treeNode.name);
											if(e){
												$("#mytabs").tabs("select",treeNode.name)
											}else{
												$("#mytabs").tabs("add",{
													title:treeNode.name,
													iconCls:'icon-ok',
													closable:true,
													content:'<iframe frameborder="0" height="100%" width="100%" src="'+treeNode.page+'"></iframe>'
												});
											}
											
										}
										
									}
								}
						};
						var url = "${pageContext.request.contextPath}/json/menu.json";
						$.post(url,{},function(data){
							//调用API初始化ztree
							$.fn.zTree.init($("#ztree2"), setting2, data);
						},'json');
						
					});
				</script>
			
			</div>
			<div title="面板3">333</div>
			<div title="面板4">444</div>
		</div>
	</div>
	<div data-options="region:'east'" style="width: 200px">东部</div>
	<div data-options="region:'center'">
		<div class="easyui-tabs" data-options="fit:true" id="mytabs">
			<div title="面板1" data-options="iconCls:'icon-ok',closable:true">111</div>
			<div title="面板2">222</div>
			<div title="面板3">333</div>
			<div title="面板4">444</div>
		</div>
	</div>
</body>
</html>