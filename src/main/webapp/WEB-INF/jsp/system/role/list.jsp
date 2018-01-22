<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色列表</title>
<%@include file="/static/common/common.jsp"%>
<script type="text/javascript"
	src="${ctx}/static/my/system/role/list.js"></script>

<script type="text/javascript">
	var roleKeys = '${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.roleKeys}';
	var roleKeyArr = roleKeys.split(",");
	var flag = false;
	for (var i = 0; i < roleKeyArr.length; i++) {
		if (roleKeyArr[i] === 'admin') {
			flag = true;
			break;
		}
	}
</script>

</head>
<body>
	<div class="cont-contnet">
		<div class="layui-item-new-form">
			<h3>角色管理</h3>
			<form id="searchForm" class="layui-form label-auto" action=""
				method="POST">
				<input type="hidden" name="searchSn" id="searchSn"> <input
					type="hidden" name="searchSnId" id="searchSnId">
				<div class="layui-newBox-form">
					<div class="layui-form-item">
						<div class="layui-inline">
							<label class="layui-form-label">角色名称</label>
							<div class="layui-input-inline">
								<input type="text" name="name" autocomplete="off"
									placeholder="模糊查询" class="layui-input">
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
			<h3>角色列表</h3>
			<div class="layui-table-btn">
				<div class='layui-btn-group layui-btn-left'>
					<c:forEach items="${btnHtmls}" var="btn">
                         ${btn}
                    </c:forEach>
				</div>
			</div>
			<div class="layui-form lay-data-table" id="tableGrid">
				<table class="layui-table" id="roleTable" lay-filter="role"></table>
			</div>
		</div>


	</div>
	</div>
	<script type="text/javascript" id="buttons">
		{{# if(flag){ }}
			{{json.role_edit ? json.role_edit : ''}}
			{{json.role_details ? json.role_details : ''}}
	
		{{#  } else { }}
			{{json.role_details ? json.role_details : ''}}
		{{#  } }}
	</script>

</body>

</html>
