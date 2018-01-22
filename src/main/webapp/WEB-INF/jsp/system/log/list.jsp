<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>操作日志</title>
<%@include file="/static/common/common.jsp"%>
<script type="text/javascript" src="${ctx}/static/my/system/log/list.js"></script>
</head>
<body>
	<div class="cont-contnet">
		<div class="layui-item-new-form">
			<h3>操作日志</h3>
			<form id="searchForm" class="layui-form label-auto" action="">

				<div class="layui-newBox-form">
					<div class="layui-form-item">
						<div class="layui-inline">
							<label class="layui-form-label">操作位置</label>
							<div class="layui-input-inline">
								<input type="text" name="operatAddress" placeholder="模糊搜索"
									autocomplete="off" class="layui-input">
							</div>
							<div class="layui-inline">
								<label class="layui-form-label">日期范围</label>
								<div class="layui-input-inline">
									<input type="text" name="operatTime" style="width: 240px !important" class="layui-input" name="" id="operatTime"
										placeholder="">
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="layui-btn-box">
					<button id="toSearch" class="layui-btn layui-btn-sm">查询</button>
					<button class="layui-btn layui-btn-primary layui-btn-sm"
						type="reset">重置</button>
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
				<table class="layui-table" id="logTable" lay-filter="log"></table>
			</div>
		</div>
	</div>
</body>
</html>
