<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单列表</title>
<%@include file="/static/common/common.jsp"%>
<script type="text/javascript" src="${ctx}/static/my/system/resource/list.js"></script>
</head>
<body>
	<div class="cont-contnet">
		<div class="layui-item-new-form">
			<h3>资源信息</h3>
			<form id="searchForm" class="layui-form label-auto" action=""
				style="margin: 10px 0px; display: inline;">
				<div class="layui-newBox-form">
					<div class="layui-form-item">
						<div class="layui-inline">
							<label class="layui-form-label" style="width: 120px;">资源名称：</label>
							<div class="layui-input-inline">
								<input type="text" name="name" autocomplete="off"
									class="layui-input">
							</div>
							<label class="layui-form-label" style="width: 120px;">唯一标识：</label>
							<div class="layui-input-inline">
								<input type="text" name="key" autocomplete="off"
									class="layui-input">
							</div>
						</div>
					</div>
				</div>
				<div class="layui-btn-box">
					<button id="toSearch" class="layui-btn layui-btn-sm">查询</button>
					<button type="reset"
						class="layui-btn layui-btn-primary layui-btn-sm">重置</button>
				</div>

			</form>
		</div>
		<!-- Grid列表 -->
		<div class="layui-item-new-form clear-border">
			<h3>管理员列表</h3>
			<div class="layui-table-btn">
				<div class='layui-btn-group layui-btn-left'>
					<c:forEach items="${btnHtmls}" var="btn">
                         ${btn}
                    </c:forEach>
				</div>
			</div>
			<div class="layui-form lay-data-table" id="tableGrid">
				<table class="layui-table" id="resourceTable" lay-filter="resource"></table>
			</div>
		</div>
	</div>
</body>
</html>
