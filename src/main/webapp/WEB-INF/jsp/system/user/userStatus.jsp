<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改用户状态</title>
<%@include file="/static/common/common.jsp"%>
<script type="text/javascript"
	src="${ctx}/static/my/system/user/userStatus.js"></script>
</head>
<body>
	<div class="popup-container ad-credit">
		<input type="hidden" id="id" value="${id}" name="id" />
		<input type="hidden" id="isEnabled" value="${isEnabled}" name="isEnabled" />
		<div class="popup-div-content">
			<img src="${ctx}/static/my/image/warn/tishi2.png">
		</div>
		<div class="popup-div-content">是否${isEnabled ==1 ? '启用' : '禁用'}该用户</div>
		<div class="layui-form-item" >
			<div class="popup-div-content">
				<button id="toSubmit" type="button" class="layui-btn layui-btn-sm">确认</button>
				<button id="toCancel" type="button"
					class="layui-btn layui-btn-primary layui-btn-sm">取消</button>
			</div>
		</div>
	</div>
</body>
</html>