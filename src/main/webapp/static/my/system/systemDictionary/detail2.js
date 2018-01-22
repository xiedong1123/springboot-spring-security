

var jsonData;

/*初始化*/
$(function() {
	/*请求后台初始化表格*/
	asyncRequest();
	
	$("#toAdd").click("click", function() {
		toAdd();
		return false;
	});
	
	$("#toSearch").click("click", function() {
		toSearch();
		return false;
	});
});

/*搜索*/
function toSearch(){
	var searchParams = $("#searchForm").serializeJson();
	asyncRequest(searchParams);
}


/*数据请求*/
function asyncRequest(data){
	var layer_index;
	try {
		layer_index = parent.layer.msg('加载中', {
			icon: 16
			,shade: 0.01,
			time: 0 //不自动关闭
		});
		$.ajax({
			async : true,
			type : "POST",
			data : data,
			url : rootPath + "/systemDictionaryItem/pageList/"+code,
			dataType : 'json',
			success : function(data) {
				jsonData = data;
				getHtml();
				parent.layer.close(layer_index);
			},
			error : function(msg) {
				if(console) console.debug("远程数据获取失败");
				parent.layer.close(layer_index);
			}
	
		});
	} catch (e) {
		if(console) console.debug(e);
		parent.layer.close(layer_index);
	}
}


/*添加,追加html*/
function toAdd(){
	var html = "<tr class='myclass' style='background-color:#f2f2f2'>"
			+"<td></td>"
			+"<td><input style='border-style:none;background-color:transparent;text-align: center;' name='dic.code' type='text'  value='"+code+"' readonly='readonly'></td>"
			+"<td><input style='border-style:none;text-align: center;' class='layui-input' name='name' type='text'  value='' /></td>"
			+"<td><input style='border-style:none;text-align: center;' class='layui-input' name='value' type='text'  value='' /></td>"
			+"<td><input style='border-style:none;text-align: center;' class='layui-input' name='description' type='text'  value='' /></td>"
			+"<td><div class='layui-btn-group'><button type='reset'style='padding:0px 10px 0px 10px !important;' class='layui-btn layui-btn-primary  layui-btn-small' onclick='save(this);'>保存</button>"
			+"<button style='padding:0px 10px 0px 10px !important;' class='layui-btn layui-btn-small' onclick='del(this);'>删除</button></td><div>";
	$(".myTbody").prepend(html);
}


/*编辑*/
function edit(obj){
	$(obj).closest('tr').find('td').each(function(){
		$(this).find('input[readonly="readonly"]').css("background-color","");
		$(this).find('input[readonly="readonly"]').removeAttr("readonly");
	});
	$(obj).closest('tr').css("background-color","#f2f2f2"); 
	var html = "<div class='layui-btn-group'><button style='padding:0px 10px 0px 10px !important;' type='reset' class='layui-btn layui-btn-primary  layui-btn-small'  onclick='save(this);'>保存</button>"
	+"<button style='padding:0px 10px 0px 10px !important;' class='layui-btn   layui-btn-small'  onclick='cancel(this);'>取消</button></td></div>"
	$(obj).closest('td').html(html);
}


function cancel(obj){
	$(obj).closest('tr').find('td').each(function(){
		$(this).find('input').css("background-color","transparent");
		$(this).find('input').attr("readonly","readonly");
	});
	$(obj).closest('tr').css('background-color','');
	var html = "<div class='layui-btn-group'><button style='padding:0px 10px 0px 10px !important;' type='reset' class='layui-btn layui-btn-primary  layui-btn-small' onclick='edit(this);' >编辑</button>"
		+"<button style='padding:0px 10px 0px 10px !important;' class='layui-btn   layui-btn-small'  onclick='del(this);'>删除</button></td></div>"
	$(obj).closest('td').html(html);
}

/*保存*/
function save(obj){
	var jsonParms = "{";
	var b = true;
	$(obj).closest('tr').find('td').each(function(){
		if($(this).find('input').length > 0){
			var name = $(this).find('input').prop("name");
			var val = $(this).find('input').val();
			if(b){
				b =false;
				jsonParms += '"'+name+'":"'+val+'"';
			}else{
				jsonParms += ',"'+name+'":"'+val+'"';
			}
		}
			
	});
	jsonParms += "}";
	var jsonObject = $.parseJSON(jsonParms);
	//验证
	var b = verification(jsonObject.name,jsonObject.value,jsonObject.id);
	if(b.success){
		parent.layer.msg(b.msg);
		return;
	}
	
	var url = jsonObject.id ? "/systemDictionaryItem/edit" : "/systemDictionaryItem/save";
	
	$.post(rootPath+url, jsonObject, function(data) {
		data = $.parseJSON(data);
		if (data.success) {
			window.location.reload();//刷新当前页
			parent.layer.msg("操作成功");
			$(obj).closest('tr').css('background-color','');
			$(obj).closest('tr').find('td').each(function(){
				$(this).find('input').attr("readonly","readonly");
			});
			var html = "<div class='layui-btn-group'><button style='padding:0px 10px 0px 10px !important;' type='reset' class='layui-btn layui-btn-primary  layui-btn-small' onclick='edit(this);' >编辑</button>"
				+"<button style='padding:0px 10px 0px 10px !important;' class='layui-btn   layui-btn-small'  onclick='del(this);'>删除</button></td></div>"
				$(obj).closest('td').html(html);
		}else {
			parent.layer.msg("操作失败!");
		}
	});
	
}

/*删除*/
function del(obj){
	var id = $($(obj).closest('tr').find('td')[0]).find('input').val();
	if(id){
		layer.confirm('是否删除？', function(index) {
			$.post(rootPath+"/systemDictionaryItem/delete/"+id, function(data) {
				data = $.parseJSON(data);
				if (data.success) {
					layer.close(index);
					parent.layer.msg("操作成功");
					$(obj).closest('tr').remove();
				}else {
					parent.layer.msg("操作失败!");
				}
			});
		});
	}else{
		$(obj).closest('tr').remove();
	}
}

/*验证*/
function verification(name,value,id){
	var isValueExist;
	var b = true;
	var nameLength = name.trim().replace(/[^x00-xFF]/g, '**').length;
	if (b && (name.trim() == '' || nameLength > 40)) {
		b = false;
		isValueExist = '{"success":true,"msg":"字典名称必填,最多10个中文字符,20个英文字符"}';
	}
	if(value){
		
		if (b && !new RegExp("^[a-zA-Z]+\\w+").test(value)) {
			b = false;
			isValueExist = '{"success":true,"msg":"唯一标识必须以英文字母开头的英文字母或者(英文,数字,下划线)组成"}';
		}
		if (b && !new RegExp("(?!.*?_$)^[a-zA-Z]+\\w+").test(value)) {
			b = false;
			isValueExist = '{"success":true,"msg":"唯一标识不能以下划线结尾"}';
		}
		var pattern = /^\S+$/gi;
		if (b && !pattern.test(value)) {
			b = false;
			isValueExist = '{"success":true,"msg":"唯一标识不能输入空格"}';
		}
		if (b && value.length < 4) {
			b = false;
			isValueExist = '{"success":true,"msg":"唯一标识输入长度不少于4个字符"}';
		}
		if(b && name.trim() != ''){
			$.ajax({
				async : false,
				type : "POST",
				data : {
					id : id,
					value : value
				},
				url : rootPath + "/systemDictionaryItem/isValueExist",
				dataType : 'json',
				success : function(data) {
					if (!data) {
						isValueExist = '{"success":true,"msg":"唯一标识已存在,请重新输入!!"}';
					}
				}
	
			});
		}
	}
	if(!isValueExist){
		isValueExist = '{"success":false}';
	}
	return $.parseJSON(isValueExist);
}

/*拼接表格*/
function getHtml(){
	var	html = "<div>"
		 + " <table class='layui-table' style='table-layout: fixed;'>"
		 + "    <thead> "
		 + "        <tr>"
		 +             "<th>ID</th>"
		 +             "<th>类型</th>"
		 +             "<th>名称</th>"
		 +             "<th>唯一标识</th>"
		 +             "<th>描述</th>"
		 +             "<th>操作</th>"
		 + "        </tr>"
		 + "    </thead>"
		 + "	<tbody class='myTbody'>"
		 + 			bodyHtml()
		 + "	</tbody>"
		 + " </table>"
		 //+ getPage()
		 + "</div>";
	
	$("#detailTable").html(html);
}

/*渲染表格数据*/
function bodyHtml(){
	var bodyHtml = "";
	var records = jsonData.records
	if(records){
		
		for (var i = 0; i < records.length; i++) {
			bodyHtml += "<tr class='myclass'>";
			bodyHtml += "<td>"+records[i].id+"<input type='hidden' name='id' value='"+records[i].id+"' /></td>";
			bodyHtml += "<td>"+records[i].dic.code+"</td>";
			bodyHtml += "<td><input style='border-style:none;background-color:transparent;text-align: center;' class='layui-input' name='name' type='text'  value='"+records[i].name+"' readonly='readonly'></td>";
			bodyHtml += "<td>"+records[i].value+"<input type='hidden' name='value' value='"+records[i].value+"' /></td>";
			bodyHtml += "<td><input style='border-style:none;background-color:transparent;text-align: center;' class='layui-input' name='description' type='text'  value='"+records[i].description+"' readonly='readonly'></td>";
			bodyHtml += "<td><div class='layui-btn-group'><button style='padding:0px 10px 0px 10px !important;' type='reset' class='layui-btn layui-btn-primary  layui-btn-small' onclick='edit(this);' >编辑</button>"
						+"<button style='padding:0px 10px 0px 10px !important;' class='layui-btn   layui-btn-small'  onclick='del(this);'>删除</button></td></div>";
			bodyHtml += "</tr>"
		}
		
	}
	if(bodyHtml == ""){
		bodyHtml += "<tr><td colSpan='6'>搜索结果为空</td></tr>";
	}
	
	
	return bodyHtml;
}

/*分页*/
function getPage(){
	//分页
	var pageHtml = '<div style="float: right;"><button id="upPage" class="my-btn layui-btn-small ">上一页</button>&nbsp;'
						+jsonData.pageNow+'&nbsp;'
						+'<button id="nextPage" class="my-btn layui-btn-small">下一页</button>&nbsp;'
						+'共&nbsp;'+jsonData.pageCount+'&nbsp;页</div>';
	//上一页处理
	if (jsonData.pageNow == 1) {
		$("#upPage").addClass("layui-disabled");
		$("#upPage").attr({"disabled":"disabled","onclick":"return false"});
	}else{
		$("#upPage").attr("onclick","listData_init("+(jsonData.pageNow - 1)+",null);");
		
	}
	//下一页处理
	if (jsonData.pageNow == jsonData.pageCount) {
		$("#nextPage").addClass("layui-disabled");
		$("#nextPage").attr({"disabled":"disabled","onclick":"return false"});
	}else{
		$("#nextPage").attr("onclick","listData_init("+(jsonData.pageNow + 1)+",null);");
		
	}
	
	return pageHtml;
}