layui.use('table', function() {
	var table = layui.table;
	parent.tableRender = table.render({
		elem : '#systemDictionaryItemTable',
		url : rootPath + '/systemDictionaryItem/pageList/'+code,
		method : 'post',
		cols : [ [ {
			type : 'numbers',
			title : '序号'
		}, {
			unresize : true,//列宽是否禁止拖动(默认  false)
			field : 'code',
			title : '类型',
			//templet : '<div>{{d.dic.code}}</div>'
		}, {
			unresize : true,//列宽是否禁止拖动(默认  false)
			field : 'name',
			edit: 'text',
			title : '名称',
		}, {
			unresize : true,//列宽是否禁止拖动(默认  false)
			field : 'value',
			title : '唯一标识',
		}, {
			unresize : false,//列宽是否禁止拖动(默认  false)
			field : 'description',
			edit: 'text',
			title : '描述',
		},/*{
			unresize : true,//列宽是否禁止拖动(默认  false)
			field : 'createTime',
			title : '创建时间',
		},{
			unresize : true,//列宽是否禁止拖动(默认  false)
			field : 'updateTime',
			title : '修改时间',
		}, */{
			unresize : true,//列宽是否禁止拖动(默认  false)
			fixed : 'right',
			align : 'center',
			title : '操作',
			toolbar : "<div><button class='layui-btn layui-btn-primary layui-btn-xs'  lay-event='save' >保存</button><button class='layui-btn layui-btn-primary layui-btn-xs'  lay-event='del' >删除</button></div>"
		} ] ],
		id : 'systemDictionaryItemReload',
		page : pageObj(), // 自定义分页对象
		done : function(res, curr, count) {// 表格渲染完成回调
			lastLoad();		//表格渲染完成后操作	
		}
	});
	
	
	table.on('sort(systemDictionaryItem)', function(obj) { // 注：tool是工具条事件名，test是table原始容器的属性
		var searchParams = $("#searchForm").serializeJson();
		// 把排序字段赋值给json对象
		searchParams.column = obj.field;
		searchParams.sort = obj.type;
		table.reload('systemDictionaryItemReload', {
			initSort : obj // 记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
			,
			where : searchParams
		});
	});
	
	
	// 监听工具条
	table.on('tool(systemDictionaryItem)', function(obj) {
		var data = obj.data;
		 if (obj.event === 'del') {
			 layer.confirm('是否删除？', function(index) {
				 $.post(rootPath+"/systemDictionaryItem/delete/"+data.id, function(data) {
					 if (data.success) {
						 layer.close(index);
						 parent.layer.msg("操作成功");
						 obj.del();
					 }else {
						 parent.layer.msg("操作失败!");
					 }
				 });
			 });
			 
		} else if (obj.event === 'save') {
			var url = rootPath + "/systemDictionaryItem/edit";
			//验证
			var b = verification(data.name,data.value,data.id);
			if(b.success){
				parent.layer.msg(b.msg);
				return;
			}
			
			$.post(url, data, function(data) {
				if (data.success) {
					parent.my_reload();//刷新当前页
					parent.layer.msg("操作成功");
				}else {
					parent.layer.msg("操作失败!");
				}
			});
		}
	});
	
	
	// 条件搜索
	$('#toSearch').on('click', function() {
		parent.my_reload();
		return false;// 不加return false 会重复加载页面
	});
	
	// 添加
	$("#toAdd").click("click", function() {
		toAdd();
	});
});


//带条件重新加载页面数据
parent.my_reload = function() {
	var searchParams = $("#searchForm").serializeJson();
	parent.tableRender.reload({
		page: pageObj(),
		where : searchParams
	});
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


//追加tbody
function toAdd() {
	var html = '<tr data-index="" class="">'
				+'<td ><div class="layui-table-cell laytable-cell-1-0 laytable-cell-numbers"></div></td>'
				+'<td ><input readonly="readonly" style="border-style:none;background-color:transparent;text-align: center;" name="code" value="'+code+'"></td>'
				+'<td ><div></div><input name="name" class="layui-input mylayui-table-edit"></td>'
				+'<td ><input name="value" class="layui-input mylayui-table-edit"></td>'
				+'<td ><input name="description" class="layui-input mylayui-table-edit"></div></td>'
				+'<td data-field="5" align="center" data-off="true">'
				+'<div class="layui-table-cell laytable-cell-1-5">'
				+'<button class="layui-btn layui-btn-primary layui-btn-xs" onclick="save(this);">保存</button>'
				+'<button class="layui-btn layui-btn-primary layui-btn-xs" onclick="del(this);">删除</button>'
				+'</div></td>'
				+'</tr>';
	
	$("table tbody").prepend(html);
}

//新增
function save(obj){
	var data ={};
	$(obj).closest('tr').find('td').each(function(){
		if($(this).find('input').length > 0){
			var name = $(this).find('input').prop("name");
			var val = $(this).find('input').val();
			data[name] = val;
		}
			
	});
	//验证
	var b = verification(data.name,data.value,data.id);
	if(b.success){
		parent.layer.msg(b.msg);
		return;
	}
	var url = rootPath + "/systemDictionaryItem/save";
	
	$.post(url, data, function(data) {
		if (data.success) {
			parent.my_reload();//刷新当前页
			parent.layer.msg("操作成功");
		}else {
			parent.layer.msg("操作失败!");
		}
	});
	
}
//删除
function del(obj){
	$(obj).closest('tr').remove();
}