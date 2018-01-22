<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="/static/common/common.jsp"%>
<script type="text/javascript"
	src="${ctx}/static/my/system/systemDictionary/detail.js"></script>
<script type="text/javascript"
	src="${ctx}/static/common/permission_button.js"></script>
<script type="text/javascript">
	var code = "${code}";
</script>
</head>
<body>
	<div class="pop-content ad-credit">
		<form id="searchForm" class="layui-form" action="" method="POST">
			<div class="layui-form-item">
				<div class="layui-inline">
					<label class="layui-form-label">唯一标识</label>
					<div class="layui-input-inline">
						<input type="text" name="value" autocomplete="off"
							class="layui-input" placeholder="">
					</div>
				</div>
				<div class="layui-inline layout-mgf-40">
					<div class="layui-input-block">
						<button class="layui-btn layui-btn-sm" id="toSearch">搜索</button>
						<button type="reset"
							class="layui-btn layui-btn-primary layui-btn-sm">重置</button>
					</div>
				</div>
			</div>
		</form>
		<div class="layui-item-new-form layui-popup-item clear-border"
			style="">
			<div class="layui-form lay-data-table" id="tableGrid">
				<div align="right">
					<button class="layui-btn layui-btn-sm"id="toAdd">添加</button>
				</div>
				<table class="layui-table" id="systemDictionaryItemTable"
					lay-filter="systemDictionaryItem"></table>
			</div>
		</div>
	</div>

</body>
</html>