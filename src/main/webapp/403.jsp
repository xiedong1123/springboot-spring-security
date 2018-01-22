<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@include file="/static/common/common.jsp"%>
<title>未授权</title>
<style type="text/css">
/*样式： 注意、警告框 */
#attention {
	background: #FFFBCC;
	border: 1px #E6DB55 solid;
	color: #333;
	margin: 10px;
	padding: 8px 8px 8px 35px;
	line-height: 22px;
	font-size: 12px;
}
</style>
</head>
<body>
	<div id="attention">
		对不起！你没该权限，请联系管理员！！ 
		<a href="javascript:void(0)" style="float: right;" onclick="back();">返回</a>
	</div>
	<script>
		function back() {
			location.href = history.go(-1);
		}
	</script>
</body>
</html>
