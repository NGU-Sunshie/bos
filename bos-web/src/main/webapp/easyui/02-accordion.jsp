<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>accordion</title>
<link rel="stylesheet" type="text/css"href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout">        
	<div data-options="region:'north'" style="height: 100px" title="sunshine管理系统">北部</div>
	<div data-options="region:'south'" style="height: 100px">南部</div>
	<div data-options="region:'west'" style="width: 200px">
		<div class="easyui-accordion" data-options="fit:true">
			<div title="面板1">111</div>
			<div title="面板2">222</div>
			<div title="面板3">333</div>
			<div title="面板4">444</div>
		</div>
	
	</div>
	<div data-options="region:'east'"style="width: 200px">东部</div>
	<div data-options="region:'center'">中心</div>
</body>
</html>