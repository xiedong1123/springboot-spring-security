$(function() {
	layui.use('form', function(){
		  var form = layui.form();
		  
		  //自定义验证规则  
		  form.verify({
			  resName :function(value){
				  if(value.trim().length > 10){  
			            return '长度不能超过10个字符';  
		          } 
			  },
			  resKey: function(value){
				  var reslult;
				  $.ajax({
						  cache: true,
						  type: "get",
						  url:rootPath+'/resource/isResKeyExist?resKey=' + value +'&resId='+$("#id").val(),
						  async: false,
						  success: function(data) {
							  data = $.parseJSON(data);
							  if (data.success) {
								  reslult =  '唯一标识已存在';
							  }
						  }
				  });
				  if(value.trim().length > 20 || value.trim().length<4){  
					  reslult = '长度为4~20个字符!';  
		          }
				 return reslult;
			  }, 
			  btnHtml: function(value){  
				 var typeVal = $("input[name='type']:checked").val();
				 if(typeVal=='button'){
					 if(value == null || value == ''){  
						 return '请选择以上功能按钮!';  
					 }  
				 }
		        }, 
	         description :function(value){
	        	if(value.trim().length > 180){  
	        		return '长度不能超过180个字符';  
	        	} 
	        },
		       
		  });
		  
		  // 注册表单提交事件
		  form.on('submit(go)', function(data) {
			  submitHandler();
			  return false;//防止重复提交
		  });
	});
	
	//初始化_功能按钮
  	$.get(rootPath+"/button/getAll",
  			function(data){
				data = $.parseJSON(data);
				if (data.success) {
					var btnView = $("#btnView");
					var htmlEl = '&nbsp;';
					btnView.html('');
					for ( var i = 0; i < data.resultData.length; i++) {
						htmlEl+= '&nbsp;'+data.resultData[i].description+'<span style="display:none;" >'+ data.resultData[i].button+'</span>';
					}
					btnView.html(htmlEl);
				}
	});
	//----------------------注册事件-------------------
});
/******************************具体方法****************************/
/*表单提交*/
function submitHandler() {
	$("#myParentId").val($("#parentId").val());//select-->input赋值，防止添加时，parentId无法提交
	var jsonParms = $("#resform").serializeJson();
	var url = jsonParms.id ?"/resource/edit":"/resource/add";
	$.post(rootPath+url, jsonParms, function(data) {
		data = $.parseJSON(data);
		if (data.success) {
			//刷新grid
			parent.grid.loadData();
			parent.layer.close(parent.layer_form);
			//刷新树形列表
			parent.resTree.reAsyncChildNodes(null, "refresh");
			parent.layer.msg(data.msg?data.msg:"操作成功");
		} else {
			parent.layer.msg(data.msg?data.msg:"操作失败!");
		}
	});
}

/*菜单类型_值改变事件*/	
function onTypeChange(obj){
	
	if(obj){
		if(obj && obj=='button'){
			$("#funcBtn").css("display","");
		}else{
			var val = $(obj).find("input[name='type']").val();
			if(val && val=="button"){
				$("#funcBtn").css("display","");
			}else{
				$("#funcBtn").css("display","none");
			}
		}
		if(obj && obj=='menu1'){
			menuSelect();
			$("#parentId").attr("disabled",'disabled');
			$("#parentId ~ .layui-unselect").addClass('layui-select-disabled');
			$("#parentId ~ .layui-unselect > div").removeClass('layui-select-title');
			$("#parentId ~ .layui-unselect").find(".layui-input").addClass('layui-disabled');
			//默认值（------顶级目录------）
		/*	$("#parentId ~ .layui-unselect").find("dd").removeClass("layui-this"); 
			$("#parentId ~ .layui-unselect").find("dd[lay-value='0']").addClass("layui-this"); 
			$("#parentId ~ .layui-unselect").find(".layui-input").val('------顶级目录------');*/
			$("#resUrl").val('#');
			$("#resUrl").addClass('layui-btn-disabled');
			//刷新select
			layui.use('form', function(){
				layui.form().render('select');
		   });
			
		}else{
			var typeVal = $(obj).find("input[name='type']").val();
			if(typeVal && typeVal =='menu1'){
				menuSelect();
				$("#parentId").attr("disabled",'disabled');
				$("#parentId ~ .layui-unselect").addClass('layui-select-disabled');
				$("#parentId ~ .layui-unselect > div").removeClass('layui-select-title');
				$("#parentId ~ .layui-unselect").find(".layui-input").addClass('layui-disabled');
				//默认值（------顶级目录------）
			/*	$("#parentId ~ .layui-unselect").find("dd").removeClass("layui-this"); 
				$("#parentId ~ .layui-unselect").find("dd[lay-value='0']").addClass("layui-this"); 
				$("#parentId ~ .layui-unselect").find(".layui-input").val('------顶级目录------');*/
				$("#resUrl").val('#')
				$("#resUrl").addClass('layui-btn-disabled');
			}else{
				$("#parentId").attr("disabled",null);
				$("#parentId ~ .layui-unselect").removeClass('layui-select-disabled');
				$("#parentId ~ .layui-unselect > div").attr('class','layui-select-title');
				$("#parentId ~ .layui-unselect").find(".layui-input").removeClass('layui-disabled');
				$("#resUrl").val()?$("#resUrl").val():$("#resUrl").val(null);
				$("#resUrl").removeClass('layui-btn-disabled');
			}
			//刷新select
			layui.use('form', function(){
				layui.form().render('select');
		   });
		}
	}else{
		$("#funcBtn").css("display","none");
	}
	 
};
/*btn点击事件*/
function toBtn(b){
	//var btnHtml= $("#"+b.id + " > span").html();
	var btnHtml= b.nextSibling.innerHTML;
	$("#btnHtml").val(btnHtml);
};
/*上级菜单*/
function menuSelect(parentId){
	layui.use('layer', function(){
		var url = rootPath + '/resource/treeList';
		var data = CommnUtil.ajax(url, null,"json");
		
		if (data != null) {
			var h = "<option value='0'>------顶级目录------</option>";
			for ( var i = 0; i < data.length; i++) {
				if(parentId == data[i].id){
					h+="<option value='" + data[i].id + "' selected>&#10148"+ data[i].name + "</option>";
				}else{
					h+="<option value='" + data[i].id + "' >&#10148"+ data[i].name + "</option>";
				}
				for(var j =0;j<data[i].children.length;j++){
					if(parentId == data[i].children[j].id ){
						h+="<option value='" + data[i].children[j].id + "' selected >&#10163"+ data[i].children[j].name + "</option>";
					}else{
						h+="<option value='" + data[i].children[j].id + "' >&#10163"+ data[i].children[j].name + "</option>";
					}
				}
			}
			$("#parentId").html(h);
		} else {
			layer.msg("获取菜单信息错误，请联系管理员！");
		}
	});
}