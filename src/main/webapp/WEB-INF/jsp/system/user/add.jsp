<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员编辑</title>
<%@include file="/static/common/common.jsp"%>
<script type="text/javascript"
	src="${ctx}/static/my/system/user/add.js"></script>
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
		<form id="userAddform" class="layui-form">
			<div class="layui-form-item">
				<label class="layui-form-label">登录账号</label>
				<div class="layui-input-block">
					<input type="text" name="username" id="username" class="layui-input"
						value=""
						placeholder="必填,长度5-11位,字母数字或下划线,不能以下划线开头或结尾">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">姓名</label>
				<div class="layui-input-block">
					<input type="text" id="name" name="name" value=""
						class="layui-input" placeholder="必填,员工真实姓名">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">手机号</label>
				<div class="layui-input-block">
					<input type="text" id="mobile" name="mobile" autocomplete="off"
						maxlength="11" value="" class="layui-input"
						placeholder="">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">角色</label>
				<div class="layui-input-block">
					<c:forEach items="${roleAlls}" var="r">
						<input type="checkbox" name="roleId" value="${r.id}"
							title="${r.name}" lay-skin="primary">
					</c:forEach>

					<%-- <select name="roleIds" id="roleIds" lay-filter="roles">
						<option value="">-----请选择-----</option>
						<c:forEach items="${roleAlls}" var="r">
								<option value="${r.id}" >${r.name}</option>
						</c:forEach>
					</select> --%>
				</div>
			</div>
			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label">备注</label>
				<div class="layui-input-block">
					<textarea id="remark" name="remark" placeholder="最多输入20个中文字符"
						maxlength="20" class="layui-textarea"></textarea>
				</div>
			</div>
			<!-- <div class="layui-form-item layui-form-text">
				<label class="layui-form-label">wangEditor富文本</label>
				<div class="layui-input-block">
					<div id="wangEditor"></div>
				</div>
			</div> -->
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
</body>
</html>