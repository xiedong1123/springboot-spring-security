<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改用户状态</title>
<%@include file="/static/common/common.jsp"%>
<script type="text/javascript">
	function toCancel(){
		parent.layer.close(parent.layer_form);
	}
	function loginOut(){
		$.ajax({
			url:rootPath + '/logout',
			success:function(){
				top.location.reload();
			}
		})
	}
</script>
</head>
<body>
	<div class="popup-container ad-credit">
		<form id="userStatusForm" class="layui-form" action="">
			<input type="hidden" value="${id}" name="id" />
			<input type="hidden" value="${status}" name="status" />
			<div class="popup-div-content">
				<img src="${ctx}/static/my/image/warn/tishi2.png">
			</div>
			<div class="popup-div-content">是否确认退出</div>
			<div class="layui-form-item" >
				<div class="popup-div-content">
					<button type="button" onclick="loginOut()" class="layui-btn layui-btn-small">确认</button>
					<button type="button" onclick="toCancel()"
						class="layui-btn layui-btn-primary layui-btn-small">取消</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>