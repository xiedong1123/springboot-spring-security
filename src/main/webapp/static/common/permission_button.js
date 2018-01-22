//页面功能按钮控制
$(function() {
	//resetButton();
});
function resetButton() {
	var permissionEls = $.find('.permission');
	if (buttons instanceof Array) {
		buttons = buttons.join('","');
		buttons = '["' + buttons + '"]';
	}
	if (!buttons || buttons == '${buttons}') {
		if (permissionEls) {
			$(permissionEls).each(function(i, obj) {
				$(obj).remove();
			});
		}
		return;
	}

	buttons = $.parseJSON(buttons);
	if (permissionEls) {
		$(permissionEls).each(function(i, obj) {
			//$(obj).css("display", "none");
		});
	}
	if (buttons) {
		for (var i = 0; i < buttons.length; i++) {
			var index = '.operation_' + buttons[i];
			 var operationEls = $.find(index);
			if (operationEls) {
				$(operationEls).each(function(i, obj) {
					//$(obj).css("display", "block");
					remove(permissionEls,obj);
				});
			}
		}
	}
	//删除
	if (permissionEls) {
		$(permissionEls).each(function(i, obj) {
			$(obj).remove();
		});
	}
	
	// 判断按钮个数 添加圆角样式
	if ($('.layui-btn-group').find('button').length <= 1) {
		$('.layui-btn-group').find('button').addClass('mt-round')
	}
	console.log(permissionEls);
}

indexOf  = function(arr,val) { //prototype 给数组添加属性
      for (var i = 0; i < arr.length; i++) { //this是指向数组，this.length指的数组类元素的数量
        if (arr[i] == val){
        	return i; //数组中元素等于传入的参数，i是下标，如果存在，就将i返回
        }
      }
      return -1; 
    };
remove = function(arr,val) {  //prototype 给数组添加属性
  var index = indexOf(arr,val); //调用index()函数获取查找的返回值
  if (index > -1) {
	  arr.splice(index,1); //利用splice()函数删除指定元素，splice() 方法用于插入、删除或替换数组的元素
  }
};
