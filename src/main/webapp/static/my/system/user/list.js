layui.use('table', function() {
	var table = layui.table;
	parent.tableRender = table.render({
		elem : '#userTable',
		url : rootPath + '/user/pagelist',
		method : 'post',
		cols : [ [ {
			type:'checkbox'
		}, {
			type : 'numbers',
			title : '序号'
		}, {
			unresize : true,//列宽是否禁止拖动(默认  false)
			field : 'id',
			title : 'ID',
			width : 80,
		}, {
			unresize : true,//列宽是否禁止拖动(默认  false)
			field : 'username',
			title : '登录账号',
		}, {
			unresize : false,//列宽是否禁止拖动(默认  false)
			field : 'name',
			title : '姓名',
			width : 80,
		}, {
			unresize : true,//列宽是否禁止拖动(默认  false)
			field : 'roleNames',
			title : '角色',
		}, {
			unresize : true,//列宽是否禁止拖动(默认  false)
			field : 'isEnabled',
			title : '状态',
			width : 60,
			templet : '<div>{{d.isEnabled==1 ? "启用": "禁用"}}</div>'
		}, {
			unresize : true,//列宽是否禁止拖动(默认  false)
			field : 'createTime',
			title : '创建时间',
			sort : true
		}, {
			/*field : 'updateTime',
			title : '修改时间',
		}, {*/
			unresize : true,//列宽是否禁止拖动(默认  false)
			title : 'jQuery-lazyload图片',
			width : 110,
			templet : '<div><img class="lazy" style="max-width: 550px;max-height: 450px;" data-original="'+rootPath+'/static/my/image/main/56-441647.jpg" ></div>'
		}, {
			unresize : true,//列宽是否禁止拖动(默认  false)
			fixed : 'right',
			align : 'center',
			title : '操作',
			width : 200,
			toolbar : '#buttons'// '<div>'+buttons1+'</div>'
		} ] ],
		id : 'userReload',
		//size : 'lg', //sm （小尺寸） lg （大尺寸）
		cellMinWidth : 40, // 用于全局定义所有常规单元格的最小宽度（默认 60）
		page : pageObj(), // 自定义分页对象
		limit : 20, // 每页默认显示的数量--默认(10)  优先级低于 page 参数的 laypage 参数
		//limits: [10,20,30,40,50,70,80,90],每页条数的选择项，默认：[10,20,30,40,50,70,80,90]。优先级低于 page 参数的 laypage 参数。
//========================================以下代码可以不写  后台参数与一下参数一致===START=======================================================		
		/*request : {
			pageName : 'pageNow' // 页码的参数名称，默认：page
			,
			limitName : 'pageSize' // 每页数据量的参数名，默认：limit
		},
		response : {
			statusName : 'status' // 数据状态的字段名称，默认：code
			,
			statusCode : 200 // 成功的状态码，默认：0
			// ,msgName: 'hint' //状态信息的字段名称，默认：msg
			,
			countName : 'rowCount' // 数据总数的字段名称，默认：count
			,
			dataName : 'records' // 数据列表的字段名称，默认：data
		},*/
//===================================END==========================================================================================		
		initSort : {
			field : 'createTime' // 排序字段，对应 cols 设定的各字段名
			,
			type : 'desc' // 排序方式 asc: 升序、desc: 降序、null: 默认排序
		},
		done : function(res, curr, count) {// 表格渲染完成回调
			lastLoad();		//表格渲染完成后操作	
			lazyloadimg();	//渲染完成后加载图片（ps:表格中有图片的调用此方法）
		},
		where : where()
	});
function where(){
	var whereObj = new Object();
	//获取当前时间戳
	var timestamp = Date.parse(new Date()); 
	whereObj.timestamp = timestamp
	whereObj.sign = doSign(whereObj);
	return whereObj;
}
	table.on('sort(user)', function(obj) { // 注：tool是工具条事件名，test是table原始容器的属性
		var xx = table.limit;
		// lay-filter="对应的值"
		// 尽管我们的 table 自带排序功能，但并没有请求服务端。
		// 有些时候，你可能需要根据当前排序的字段，重新向服务端发送请求
		// 获取页面搜索框条件
		var searchParams = $("#searchForm").serializeJson();
		//获取当前时间戳
		var timestamp = Date.parse(new Date()); 
		searchParams.timestamp = timestamp;
		// 把排序字段赋值给json对象
		searchParams.column = obj.field;
		searchParams.sort = obj.type;
		var sign = doSign(searchParams);
		searchParams.sign = sign;
		console.log(searchParams);
		table.reload('userReload', {
			initSort : obj // 记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
			,
			where : searchParams
		/*
		 * { // 请求参数 column : obj.field //
		 * 排序字段当前排序类型：desc（降序）、asc（升序）、null（空对象，默认排序） , sort : obj.type // 排序方式 }
		 */
		});
	});

	// 监听工具条
	table.on('tool(user)', function(obj) {
		var data = obj.data;
		if (obj.event === 'detail') {
			parent.layer_form = layer_form = parent.layer.open({
				title : "用户详情",
				type : 2,
				area : [ "700px", "85%" ],
				content : rootPath + '/user/getDetails?id=' + data.id
			});

		} else if (obj.event === 'del') {
			layer.confirm('真的删除行么', function(index) {
				obj.del();
				layer.close(index);
				alert("删除ID为 : " + data.id + " 数据");
			});
		} else if (obj.event === 'edit') {
			parent.layer_form = layer_form = parent.layer.open({
				title : "编辑管理员",
				type : 2,
				area : [ "600px", "80%" ],
				content : rootPath + '/user/editUI/' + data.id
			});
		} else if (obj.event === 'resetPassword') {
			parent.layer_form = layer_form = parent.layer.open({
				title : "重置密码",
				type : 2,
				area : [ "550px", "330px" ],
				content : rootPath + '/user/resetPassUI?id=' + data.id
						+ "&account=" + data.account
			});
		} else if (obj.event === 'enabled' || obj.event === 'forbid') {
			var isEnabled = data.isEnabled == 1 ? 0 : 1;
			parent.layer_form = layer_form = parent.layer.open({
				title : "更改用户状态",
				type : 2,
				area : [ "550px", "330px" ],
				content : rootPath + '/user/updateStatusUI?id=' + data.id
						+ '&isEnabled=' + isEnabled
			});
		}
	});
	// 条件搜索
	/*
	 * var $ = layui.$, active = { reload : function() { var demoReload =
	 * $('#account'); var searchParams = $("#searchForm").serializeJson();
	 * table.reload('userReload', { where : searchParams }); } };
	 */

	// 条件搜索
	$('#toSearch').on('click', function() {
		parent.my_reload();
		return false;// 不加return false 会重复加载页面
	});

	// 添加
	$("#toAdd").click("click", function() {
		toAdd();
	});

	// 导出
	$("#toExport").on("click", function() {
		toExportView();
		return false;
	});

	// 批量删除
	$("#toBatchDel").on("click", function() {
		toBatchDel(table);
	})

});

// 带条件重新加载页面数据
parent.my_reload = function() {
	var searchParams = $("#searchForm").serializeJson();
	parent.tableRender.reload({
		page: pageObj(),
		where : searchParams
	});
}

// 添加页面弹出框
function toAdd() {
	parent.layer_form = layer_form = parent.layer.open({
		title : "添加管理员",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/user/addUI'
	});
}

/* 显示导出下载列表 */
function toExportView() {
	var viewUrl = rootPath + "/user/getCount";
	var downUrl = '/user/export';
	var data = $("#searchForm").serializeJson();
	toExportListView("post", viewUrl, data, downUrl, "#searchForm");
}

/* 批量删除 */
function toBatchDel(table) {
	var checkStatus = table.checkStatus('userReload'); // test即为基础参数id对应的值
	var data = checkStatus.data;
	var arrId = [];
	for (var i = 0; i < data.length; i++) {
		arrId.push(data[i].id);
	}
	alert("arrId : " + arrId);
	alert("获取选中行的数据 : " + checkStatus.data) // 获取选中行的数据
	alert("length : " + checkStatus.data.length) // 获取选中行数量，可作为是否有选中行的条件
	alert("是否全选 : " + checkStatus.isAll) // 表格是否全选
}

 
/* 查看大图 */
function big(){
	parent.layer.open({
		title : false,
		type : 1,//
		move :true,
		resize :false,
		area : [ "auto", "auto" ],
		content : '<img alt="暂无" style="max-width: 900px;max-height: 500px;" src="http://img1.imgtn.bdimg.com/it/u=3387611966,1108989621&fm=27&gp=0.jpg" />'
	});
	
	 
}