layui.use('table', function() {
	var table = layui.table;
	parent.tableRender = table.render({
		elem : '#resourceTable',
		url : rootPath + '/resource/pagelist',
		method : 'post',
		cols : [ [  {
			type : 'numbers',
			title : '序号'
		}, {
			unresize : false,//列宽是否禁止拖动(默认  false)
			field : 'name',
			title : '资源名称',
		}, {
			unresize : false,//列宽是否禁止拖动(默认  false)
			field : 'parentName',
			title : '上级资源名称',
		}, {
			unresize : true,//列宽是否禁止拖动(默认  false)
			field : 'key',
			title : '唯一标识',
		}, {
			unresize : true,//列宽是否禁止拖动(默认  false)
			field : 'url',
			title : 'URL',
		},  {
			unresize : true,//列宽是否禁止拖动(默认  false)
			field : 'description',
			title : '描述',
		},  {
			unresize : true,//列宽是否禁止拖动(默认  false)
			field : 'createTime',
			title : '创建时间',
			sort : true
		}] ],
		id : 'resourceReload',
		cellMinWidth : 80, // 用于全局定义所有常规单元格的最小宽度（默认 60）
		page : pageObj(), // 自定义分页对象
		limit : 20, // 每页默认显示的数量--默认(10)  优先级低于 page 参数的 laypage 参数
		done : function(res, curr, count) {// 表格渲染完成回调
			lastLoad();		//表格渲染完成后操作	
		}
	});
});