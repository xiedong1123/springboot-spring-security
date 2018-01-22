<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户详情</title>
<%@include file="/static/common/common.jsp"%>
<script type="text/javascript">
	$(function() {
		layui.use('form', function(){
			  var form = layui.form;
		});
	});
</script>
<script type="text/javascript" src="${ctx}/static/common/dropdown.js"></script>
<style type="text/css">
.layui-form-checkbox span {
	height: auto !important;
}
</style>
</head>
<body>
	<div class="popup-container ad-credit" style="width: 50% px;">
		<form id="adminform" class="layui-form">
			<!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
			<div class="layui-form-item">
				<label class="layui-form-label">登录账号</label>
				<div class="layui-input-block">
					<input type="text" name="username" id="username" class="layui-input" disabled="disabled"
						value="${user.username}" >
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">姓名</label>
				<div class="layui-input-block">
					<input type="text" id="name" name="name" value="${user.name}" disabled="disabled" class="layui-input" >
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">手机号</label>
				<div class="layui-input-block">
					<input type="text" id="mobile" name="mobile" autocomplete="off" disabled="disabled"
						maxlength="11" value="${user.mobile}" class="layui-input" >
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">角色</label>
				<div class="layui-input-block">
					<c:forEach items="${roleAlls}" var="r">
						<input type="checkbox" name="roleIds" value="${r.id}" title="${r.name}" disabled="disabled" lay-skin="primary"> 
					</c:forEach>
					<c:if test="${not empty user.id}">
						<c:forEach items="${user.roles}" var="role" > 
						    <input type="text" name="rds" style="display:none;" value="${role.id}" />
						</c:forEach>
					</c:if>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">状态</label>
				<div class="layui-input-block">
					<input type="radio" name="isEnabled" disabled="disabled" value="0" title="启用"  ${user.isEnabled == 1 ? "checked":''} lay-skin="primary"> 
					<input type="radio" name="isEnabled" disabled="disabled" value="1" title="禁用"  ${user.isEnabled == 0 ? "checked":''} lay-skin="primary">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">备注</label>
				<div class="layui-input-block">
					<textarea name="remark" id="remark" disabled="disabled" class="layui-textarea">${user.remark}</textarea>
				</div>
			</div>
		</form>
		<!-- 更多表单结构排版请移步文档左侧【页面元素-表单】一项阅览 -->
	</div>
	<script>
		<c:if test="${not empty user.id}">
		$("input[name='rds']").each(function() {
			var rdsId = $(this).val();
			$("input[name='roleIds']").each(function() {
				var roleId = $(this).val();
				if (rdsId == roleId) {
					$(this).attr("checked", true);
				}
				;
			});
		});
		</c:if>;
	</script>
</body>
</html>