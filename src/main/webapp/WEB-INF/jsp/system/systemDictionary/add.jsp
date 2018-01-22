<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加字典</title>
<%@include file="/static/common/common.jsp"%>
<script type="text/javascript"
	src="${ctx}/static/my/system/systemDictionary/add.js"></script>
<script type="text/javascript">
function wclose(){
	parent.layer.close(parent.layer_form);
}
</script>
</head>
<body>
	<div class="popup-container ad-credit">
		<form id="systemDictionaryForm" class="layui-form">
			<!-- 提示：如果你不想用form，你可以换成div等任何一个普通元素 -->
			<input id="status" type="hidden" name="status"  value="0" />

			<div class="layui-form-item">
				<label class="layui-form-label"><span class="font-red">*</span>类型</label>
				<div class="layui-input-block">
					<input type="text" id="code" name="code"
						value="" placeholder="请输入" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"><span class="font-red">*</span>名称</label>
				<div class="layui-input-block">
					<input type="text" id="name" name="name"
						value="" placeholder="请输入" autocomplete="off"
						class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">描述</label>
				<textarea name="description" placeholder="最多输入20个中文字符"
					rows="8" cols="60" maxlength='20'></textarea>
			</div>

		 <div class="layui-form-item" style= "margin-top:50px">
                <div class="popup-div-content">
                <button class="layui-btn layui-btn-sm ">确认</button>
                     <button type="reset" onclick="wclose()" class="layui-btn layui-btn-primary layui-btn-sm">取消</button> 
                </div>
            </div>
		</form>
	</div>
</body>
</html>