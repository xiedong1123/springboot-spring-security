<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员编辑</title>
<%@include file="/static/common/common.jsp"%>
<script type="text/javascript"
	src="${ctx}/static/my/system/user/edit.js"></script>
<!-- 引入弹框 -->
<script type="text/javascript" src="${ctx}/static/common/dropdown.js"></script>
<style type="text/css">
.layui-form-checkbox span {
	height: auto !important;
}
</style>
</head>
<body>
	<div class="popup-container ad-credit">
		<form id="userEditform" class="layui-form">
			<!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
			<input id="id" type="hidden" name="id" value="${user.id}" /> 
			<div class="layui-form-item">
				<label class="layui-form-label">登录账号</label>
				<div class="layui-input-block">
					<input type="text" name="username" id="username" class="layui-input"
						value="${user.username}" 
						placeholder="必填,长度5-11位,字母数字或下划线,不能以下划线开头或结尾">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">姓名</label>
				<div class="layui-input-block">
					<input type="text" id="name" name="name" value="${user.name}"
						class="layui-input" placeholder="必填,员工真实姓名">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">手机号</label>
				<div class="layui-input-block">
					<input type="text" id="mobile" name="mobile" autocomplete="off"
						maxlength="11" value="${user.mobile}" class="layui-input"
						placeholder="">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">角色</label>
				<div class="layui-input-block" >
					<c:forEach items="${roleAlls}" var="r">
						<input type="checkbox" name="roleId" value="${r.id}" title="${r.name}" lay-skin="primary"> 
					</c:forEach>
					<c:if test="${not empty user.id}">
						<c:forEach items="${user.roles}" var="role" > 
						    <input type="text" name="rds" style="display:none;" value="${role.id}" />
						</c:forEach>
					</c:if>
				</div>
			</div>


			<%-- <div class="layui-form-item layui-form-text">
				<label class="layui-form-label">状态</label>
				<div class="layui-input-block">
					<c:if test="${admin.status == 0}" var="r">
						<input type="radio" lay-verify="required" name="status" checked="checked"  value="0" title="启动" lay-skin="primary"> 
						<input type="radio" lay-verify="required" name="status"  value="1" title="禁止" lay-skin="primary"> 
					</c:if>
					<c:if test="${admin.status == 1}" var="r">
						<input type="radio" lay-verify="required" name="status"  value="0" title="启动" lay-skin="primary"> 
						<input type="radio" lay-verify="required" name="status" checked="checked" value="1" title="禁止" lay-skin="primary"> 
					</c:if>
				</div>
			</div> --%>
			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label">备注</label>
				<div class="layui-input-block">
					<textarea id="remark" name="remark" placeholder="最多输入20个中文字符"
						maxlength="20" class="layui-textarea">${user.remark}</textarea>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="popup-div-content">
					<button type="submit" class="layui-btn layui-btn-sm">确认</button>
					<button type="button" id="toCancel"
						class="layui-btn layui-btn-primary layui-btn-sm">取消</button>
				</div>
			</div>
			<!-- 更多表单结构排版请移步文档左侧【页面元素-表单】一项阅览 -->
		</form>
	</div>
	<script>
		<c:if test="${not empty user.id}">
			$("input[name='rds']").each(function() {
				var rdsId = $(this).val();
				$("input[name='roleId']").each(function() {
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