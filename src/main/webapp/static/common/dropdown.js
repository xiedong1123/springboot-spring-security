 /**
 * 下拉，基于layui样式
 * @author liuchengyong
 * @date 2017/3/7
 * @version 1.0v
 */
 
 ;
 (function(){
		//elementId:需要处理的id
		//render(container)：渲染函数,参数是容器dom对象，数据渲染，事件绑定需要在该函数中完成包括数据的回写
		dropDwon = function(elementId,render){
			var uid = null;
			
			//用于设置input的值，提供外面调用
			var  setInputValue = function(value){
				$("#" +elementId).val(value);
			};
			
			var  close = function(){				
				$("#" + uid).removeClass("my-layui-form-selected"); 
			}
			
			//创建容器id
			var createUid = function(){
				//初始化分页容器的ID
				return "uid" + new Date().getTime();
			}
			
			
			
			//隐藏原来的对象
//			$('#' + elementId).css("display","none");
			//获取提示
			/*var holder = $('#' + elementId).attr("placeholder");
			var holderValue = $('#' + elementId).attr("value");
			holder = !!holder ? holder : "直接选择";
			holderValue = !!holderValue ? holderValue : "一级部门";*/
			
			
			//获取input下拉HTML代码
			var inputHTML = $('#'+elementId).prop('outerHTML');
			
			//创建唯一标识
			uid = createUid();
			//替换原来的input
			$('#'+elementId).replaceWith('<div id = "' + uid + '" class="layui-unselect layui-form-select">'
											+	'<div class="layui-select-title">'
											+	inputHTML	+'<i class="layui-edge"></i>'
											+	'</div>'
											+	'<dl class="layui-anim layui-anim-upbit"></dl>'
											+'</div>');
			//添加input属性
			$('#'+elementId).addClass('layui-unselect');
			$('#'+elementId).attr("readonly","readonly");
			
//			
	/*		var $html = $(
			'<div id = "' + uid + '" class="layui-unselect layui-form-select">'
			+	'<div class="layui-select-title">'
			+	'<input type="text" readOnly="readOnly" placeholder="' + holder + '" value="'+holderValue+'" class="layui-input layui-unselect"><i class="layui-edge"></i>'
			+	'</div>'
			+	'<dl class="layui-anim layui-anim-upbit"></dl>'
			+'</div>');
			//渲染下拉菜单
			$html.insertAfter('#' + elementId);*/
			
			//点击input框之后展开/关闭下拉框
			$("#" + uid + " input").on("click",function(){
				if ($("#" + uid).is(".my-layui-form-selected")) {
					close();
					return;
				}
				var  $uid = $("#" + uid).addClass("my-layui-form-selected"); 
				$(".layui-form-selected").removeClass("layui-form-selected");
			});
			
			//点击其他区域关闭
			$(document).on('mouseup',function(e){
				if($(e.target).parents('#'+uid).length==0){
					close();
				}
			 })
			
			//调用render，渲染下拉的具体内容
			var dl = $('#' + uid + ' dl');
			
			render(dl[0]);
			
			return {
					setInputValue:setInputValue,
					close:close,
				   }
			
		}
		
	 
 })();