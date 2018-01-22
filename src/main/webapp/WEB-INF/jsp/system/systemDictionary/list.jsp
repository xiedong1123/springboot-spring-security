<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="/static/common/common.jsp"%>
<script type="text/javascript" src="${ctx}/static/my/system/systemDictionary/list.js"></script>
</head>
<body>
	<div class="cont-contnet">
		<div class="layui-item-new-form">
			<h3></h3>
			<form id="searchForm" class="layui-form label-auto" action="" method="POST">
				<div class="layui-newBox-form">
					<div class="layui-form-item">
						<div class="layui-inline">
							<label class="layui-form-label">名称</label>
							<div class="layui-input-inline">
								<input type="text" name="name" lay-verify="name"
									autocomplete="off" class="layui-input" placeholder="模糊查询">
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
			<h3></h3>
			<div class="layui-table-btn">
                <div class='layui-btn-group layui-btn-left' >
                    <c:forEach items="${btnHtmls}" var="btn">
                         ${btn}
                    </c:forEach>
                </div>
            </div>
			<div class="layui-form lay-data-table" id="tableGrid">
			     <table class="layui-table" id="systemDictionaryTable" lay-filter="systemDictionary"></table>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" id="buttons">
        {{json.systemDictionary_edit ? json.systemDictionary_edit : ''}}
        {{json.systemDictionary_detail ? json.systemDictionary_detail : ''}}
    </script>
</body>
</html>