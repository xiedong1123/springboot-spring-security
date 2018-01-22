<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资源编辑</title>
<%@include file="/static/common/common.jsp"%>
 <script type="text/javascript" src="${ctx}/static/my/system/resource/edit.js"></script>
 <script type="text/javascript" >
    $(function(){
    	layui.use("layer");
    	onTypeChange('${resource.type}');   //菜单类型
    	menuSelect('${resource.parentId}'); //上级菜单
    })
 </script>
</head>
<body>
<div class="cont-contnet" >
	<form id="resform" class="layui-form">
		<!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
		<input id="id" type="hidden" name="id" value="${resource.id}" />
		<input id="myParentId" type="hidden" name="parentId" value="${resource.parentId}" />
		<div class="layui-form-item">
			<label class="layui-form-label"><span class="font-red">* </span>资源名称</label>
			<div class="layui-input-block">
				<input type="text" lay-verify="required|resName" name="resName" value="${resource.resName}" placeholder="长度不能超过10个字符" autocomplete="off"
					class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"><span class="font-red">* </span>唯一标识</label>
			<div class="layui-input-block">
				<input type="text" name="resKey" lay-verify="required|resKey" value="${resource.resKey}" placeholder="长度为4~20个字符/必须以大小写英文字母开头(英文或与下划线组成)" autocomplete="off" class="layui-input ${not empty resource.resKey? 'layui-btn-disabled':''}" onkeyup="value=value.replace(/[^a-zA-Z_-]+/g, '')">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"><span class="font-red">* </span>菜单类型</label>
			<div class="layui-input-block">
				<a href="#" onclick="onTypeChange(this);"><input type="radio" name="type" lay-verify="required"  value="menu1" title="一级菜单" ${resource.type=='menu1'?'checked':''} ${empty resource.type?'checked':''}></a>
      			<a href="#" onclick="onTypeChange(this);;"><input type="radio" name="type" lay-verify="required" value="menu2" title="二级菜单" ${resource.type=='menu2'?'checked':''}></a>
      			<a href="#" onclick="onTypeChange(this);;"><input type="radio" name="type" lay-verify="required" value="button" title="菜单按钮" ${resource.type=='button'?'checked':''}></a>
			</div>
		</div>
		<div id="funcBtn" class="layui-form-item">
			<label class="layui-form-label"><span class="font-red">* </span>功能按钮</label>
			<div id="btnView" class="layui-input-block"></div>
			<div class="layui-input-block">
				<textarea id="btnHtml" name="btnHtml" type="textarea" lay-verify="btnHtml" placeholder="选择功能按钮生成html代码" class="layui-textarea layui-btn-disabled" >${resource.btnHtml}</textarea>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"><span class="font-red">* </span>父级菜单</label>
			<div class="layui-input-block">
				<select id="parentId" lay-verify="required" lay-search ></select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label"><span class="font-red">* </span>URL</label>
			<div class="layui-input-block">
				<input id="resUrl" type="text" name="resUrl" value="${not empty resource.resUrl?resource.resUrl:'#'}" lay-verify="required" placeholder="请输入" autocomplete="off" class="layui-input">
			</div>
		</div>
		<%-- <div class="layui-form-item">
			<label class="layui-form-label">图标</label>
			<div class="layui-input-block">
				<input type="text" name="icon" value="${resource.icon}" lay-verify="required" placeholder="请输入" autocomplete="off"
					class="layui-input">
			</div>
		</div> --%>
		<div class="layui-form-item">
			<label class="layui-form-label"><span class="font-red">* </span>等级</label>
			<div class="layui-input-block">
				<input type="text" name="level" value="${resource.level}" lay-verify="required|number" placeholder="必须为数字" autocomplete="off"
					class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">是否隐藏</label>
			<div class="layui-input-block">
				<select name="ishide" lay-filter="aihao">
					<option value="0" ${resource.ishide==false?'checked':''}>否</option>
					<option value="1" ${resource.ishide==true?'checked':''}>是</option>
				</select>
			</div>
		</div>
		<div class="layui-form-item layui-form-text">
			<label class="layui-form-label">描述</label>
			<div class="layui-input-block">
				<textarea id="description" name="description" lay-verify="rdescription"  placeholder="长度不能超过180个字符" class="layui-textarea">${resource.description}</textarea>
			</div>
		</div>
		<div class="layui-form-item" style="float: right;padding-right: 8px;padding-bottom: 2px;">
			<div class="layui-input-block">
				<button class="layui-btn layui-btn-small" lay-submit lay-filter="go" >提交</button>
				<button type="reset" class="layui-btn layui-btn-small layui-btn-primary">重置</button>
			</div>
		</div>
		<!-- 更多表单结构排版请移步文档左侧【页面元素-表单】一项阅览 -->
	</form>
</div>
</body>
</html>