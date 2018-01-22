layui.use(['table','flow','laydate'], function() {
	var table = layui.table,
	laydate = layui.laydate;
	
	//日期范围
	  laydate.render({
	    elem: '#operatTime'
	    ,range: '~'
	  });
	
	parent.tableRender = table.render({
		elem : '#logTable',
		url : rootPath + '/log/pagelist',
		method : 'post',
		cols : [ [ {
			type : 'numbers',
			title : '序号'
		},  {
			unresize : true,//列宽是否禁止拖动(默认  false)
			field : 'operatAddress',
			title : '操作位置',
		}, {
			unresize : false,//列宽是否禁止拖动(默认  false)
			field : 'content',
			title : '操作内容',
		}, {
			unresize : true,//列宽是否禁止拖动(默认  false)
			field : 'username',
			title : '操作账号',
		}, {
			unresize : true,//列宽是否禁止拖动(默认  false)
			field : 'name',
			title : '操作人',
		},{
			unresize : true,//列宽是否禁止拖动(默认  false)
			field : 'createTime',
			title : '创建时间',
			sort : true,
		} ] ],
		id : 'logReload',
		cellMinWidth : 10, // 用于全局定义所有常规单元格的最小宽度（默认 60）
		page : pageObj(), // 自定义分页对象
		limit : 20, // 每页默认显示的数量--默认(10)  优先级低于 page 参数的 laypage 参数
		initSort : {
			field : 'createTime' // 排序字段，对应 cols 设定的各字段名
			,
			type : 'desc' // 排序方式 asc: 升序、desc: 降序、null: 默认排序
		},
		done : function(res, curr, count) {// 表格渲染完成回调
			lastLoad();		//表格渲染完成后操作	
		}
	});
	
	

	table.on('sort(log)', function(obj) { // 注：tool是工具条事件名，test是table原始容器的属性
		// lay-filter="对应的值"
		// 尽管我们的 table 自带排序功能，但并没有请求服务端。
		// 有些时候，你可能需要根据当前排序的字段，重新向服务端发送请求
		// 获取页面搜索框条件
		var searchParams = $("#searchForm").serializeJson();
		// 把排序字段赋值给json对象
		searchParams.column = obj.field;
		searchParams.sort = obj.type;
		table.reload('logReload', {
			initSort : obj // 记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
			,
			where : searchParams
		/*
		 * { // 请求参数 column : obj.field //
		 * 排序字段当前排序类型：desc（降序）、asc（升序）、null（空对象，默认排序） , sort : obj.type // 排序方式 }
		 */
		});
	});
	
	// 条件搜索
	$('#toSearch').on('click', function() {
		parent.my_reload();
		return false;// 不加return false 会重复加载页面
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