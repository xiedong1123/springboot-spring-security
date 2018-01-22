<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员管理</title>
<%@include file="/static/common/common.jsp"%>
<script type="text/javascript" src="${ctx}/static/my/system/user/list.js"></script>
<script type="text/javascript">
	var roleKeys = '${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.roleKeys}';
	var roleKeyArr = roleKeys.split(","); 
	var flag = false;
	for (var i = 0; i < roleKeyArr.length; i++) {
		if(roleKeyArr[i] === 'admin'){
			flag = true;
			 break;
		}
	}
</script>
</head>
<body>
	<div class="cont-contnet">
	<!-- <div class="num-contnet">
			<button class="num-but layui-btn-sm">-</button>
			<input type="text" class="num-input" min="1" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"  
    onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'0')}else{this.value=this.value.replace(/\D/g,'')}" value="1" />
			<button class="num-but layui-btn-sm">+
			</button>

		</div> -->
		<div class="layui-item-new-form">
			<h3>管理员查询</h3>
			<form id="searchForm" class="layui-form label-auto" action=""
				method="POST">
				<div class="layui-newBox-form">
					<div class="layui-form-item">
						<div class="layui-inline">
							<label class="layui-form-label">登录账号</label>
							<div class="layui-input-inline">
								<input name="username" id="username" autocomplete="off"
									class="layui-input" placeholder="模糊查询">
							</div>
						</div>
						<div class="layui-inline">
							<label class="layui-form-label">角色</label>
							<div class="layui-input-inline">
								<select name="roleId">
									<option value="">全部</option>
									<c:forEach items="${roles}" var="r">
										<option value="${r.id}">${r.name}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="layui-inline">
							<label class="layui-form-label">状态</label>
							<div class="layui-input-inline">
								<select name="isEnabled">
									<option value="">全部</option>
									<option value="1" selected="">启用</option>
									<option value="0">禁用</option>
								</select>
							</div>
						</div>
					</div>
				</div>
				<div class="layui-btn-box">
					<button id="toSearch" class="layui-btn layui-btn-sm"
						data-type="reload">查询</button>
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
				<table class="layui-table" id="userTable" lay-filter="user"></table>
			</div>
		</div>
	</div>
	<script type="text/javascript" id="buttons">
		{{# var forbid = json.user_forbid ? json.user_forbid : '';}}
		{{# var enabled = json.user_enabled ? json.user_enabled : '';}}
		{{# if(flag){ }}
			{{#  if(d.username === 'admin'){ }}
					{{json.user_edit}}
					{{json.user_details ? json.user_details : ''}}
	  		{{#  } else { }}
					{{json.user_edit ? json.user_edit : ''}}
					{{json.user_details ? json.user_details : ''}}
					{{d.isEnabled == 1 ? forbid : enabled}}
					{{json.user_del ? json.user_del : ''}} 
	  		{{#  } }}
		
		{{#  } else { }}
			{{json.user_details ? json.user_details : ''}}
		{{#  } }}
		
	
	</script>
	<!-- <script type="text/javascript" id="buttons">
		{{# var name = $("#logname").val();}}
		
		{{#  if(d.username === 'admin' && name == "admin"){ }}
				<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">编辑</a>
  		{{#  } else { }}
  		
				<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">编辑</a>
				<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">详情</a>
				{{d.status == 1 ? '<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="startUser">启用</a>' : '<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="forbidUser">禁用</a>'}}
				<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="del">删除</a>
				<a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="resetPassword">重置密码</a>
				
  		{{#  } }}
	
	</script> -->
</body>
</html>
