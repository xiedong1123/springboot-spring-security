/**
 * 工具组件 对原有的工具进行封装，自定义某方法统一处理
 * 
 * @author lanyuan 2014-12-12
 * @Email: mmm333zzz520@163.com
 * @version 3.0v
 */

$(function(){
	$(document.documentElement).bind("keydown",function(e){
		if(e.keyCode==116 || e.ctrlKey && e.key==82){//回车键  == 116-F5
			if(top==window){
				 window.parent.frames["myiframe"].location.reload(true);
			}else{
				location.reload(true);
			}
	        e.preventDefault(); //组织默认刷新
		}
	}); 
});
(function() {
	ly = {};
	ly.ajax = (function(params) {
		var pp = {
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.open({
					type : 1,
					title : "出错啦！",
					area : [ '95%', '95%' ],
					content : "<div id='layerError' style='color:red'>"
							+ XMLHttpRequest.responseText + "</div>"
				});
			}
		};
		$.extend(pp, params);
		$.ajax(pp);
	});
	ly.ajaxSubmit = (function(form, params) {// form 表单ID. params ajax参数
		var pp = {
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				layer.open({
					type : 1,
					title : "出错啦！",
					area : [ '95%', '95%' ],
					content : "<div id='layerError' style='color:red'>"
							+ XMLHttpRequest.responseText + "</div>"
				});
			}
		};
		$.extend(pp, params);
		$(form).ajaxSubmit(pp);
	});
	CommnUtil = {
		/**
		 * ajax同步请求 返回一个html内容 dataType=html. 默认为html格式 如果想返回json.
		 * CommnUtil.ajax(url, data,"json")
		 * 
		 */
		ajax : function(url, data, dataType) {
			if (!CommnUtil.notNull(dataType)) {
				dataType = "html";
			}
			var html = '没有结果!';
			// 所以AJAX都必须使用ly.ajax..这里经过再次封装,统一处理..同时继承ajax所有属性
			if (url.indexOf("?") > -1) {
				url = url + "&_t=" + new Date();
			} else {
				url = url + "?_t=" + new Date();
			}
			ly.ajax({
				type : "post",
				data : data,
				url : url,
				dataType : dataType,// 这里的dataType就是返回回来的数据格式了html,xml,json
				async : false,
				cache : false,// 设置是否缓存，默认设置成为true，当需要每次刷新都需要执行数据库操作的话，需要设置成为false
				success : function(data) {
					html = data;
				},
			    error:function(XMLHttpRequest, textStatus, errorThrown){
			    	console.debug(textStatus);
			    	console.debug(errorThrown);
			    	console.debug(this);
			    	//通常情况下textStatus和errorThrown只有其中一个包含信息
			    	//this调用本次ajax请求时传递的options参数
			    }
			});
			return html;
		},
		/**
		 * 判断某对象不为空..返回true 否则 false
		 */
		notNull : function(obj) {
			if (obj === null) {
				return false;
			} else if (obj === undefined) {
				return false;
			} else if (obj === "undefined") {
				return false;
			} else if (obj === "") {
				return false;
			} else if (obj === "[]") {
				return false;
			} else if (obj === "{}") {
				return false;
			} else {
				return true;
			}

		},

		/**
		 * 判断某对象不为空..返回obj 否则 ""
		 */
		notEmpty : function(obj) {
			if (obj === null) {
				return "";
			} else if (obj === undefined) {
				return "";
			} else if (obj === "undefined") {
				return "";
			} else if (obj === "") {
				return "";
			} else if (obj === "[]") {
				return "";
			} else if (obj === "{}") {
				return "";
			} else {
				return obj;
			}

		},
		loadingImg : function() {
			var html = '<div class="alert alert-warning">'
					+ '<button type="button" class="close" data-dismiss="alert">'
					+ '<i class="ace-icon fa fa-times"></i></button><div style="text-align:center">'
					+ '<img src="' + rootPath + '/images/loading.gif"/><div>'
					+ '</div>';
			return html;
		},
		/**
		 * html标签转义
		 */
		htmlspecialchars : function(str) {
			var s = "";
			if (str.length == 0)
				return "";
			for (var i = 0; i < str.length; i++) {
				switch (str.substr(i, 1)) {
				case "<":
					s += "&lt;";
					break;
				case ">":
					s += "&gt;";
					break;
				case "&":
					s += "&amp;";
					break;
				case " ":
					if (str.substr(i + 1, 1) == " ") {
						s += " &nbsp;";
						i++;
					} else
						s += " ";
					break;
				case "\"":
					s += "&quot;";
					break;
				case "\n":
					s += "";
					break;
				default:
					s += str.substr(i, 1);
					break;
				}
			}
		},
		/**
		 * in_array判断一个值是否在数组中
		 */
		in_array : function(array, string) {
			for (var s = 0; s < array.length; s++) {
				thisEntry = array[s].toString();
				if (thisEntry == string) {
					return true;
				}
			}
			return false;
		},
		
	};
})();
// 表单json格式化方法……不使用&拼接
(function($) {
	$.fn.serializeJson = function() {
		var serializeObj = {};
		var array = this.serializeArray();
		$(array).each(
				function() {
					if (serializeObj[this.name]) {
						if ($.isArray(serializeObj[this.name])) {
							serializeObj[this.name].push(this.value);
						} else {
							serializeObj[this.name] = [
									serializeObj[this.name], this.value ];
						}
					} else {
						serializeObj[this.name] = this.value;
					}
				});
		return serializeObj;
	};
	Date.prototype.format = function(format) {
		var o = {
			"M+" : this.getMonth() + 1, // month
			"d+" : this.getDate(), // day
			"h+" : this.getHours(), // hour
			"m+" : this.getMinutes(), // minute
			"s+" : this.getSeconds(), // second
			"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
			"S" : this.getMilliseconds()// millisecond
		}
		if (/(y+)/.test(format)) {
			format = format.replace(RegExp.$1, (this.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		}
		for ( var k in o) {
			if (new RegExp("(" + k + ")").test(format)) {
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
						: ("00" + o[k]).substr(("" + o[k]).length));
			}
		}
		return format;
	}
	
	///集合取交集
/*    Array.intersect = function () {
        var result = new Array();
        var obj = {};
        for (var i = 0; i < arguments.length; i++) {
            for (var j = 0; j < arguments[i].length; j++) {
                var str = arguments[i][j];
                if (!obj[str]) {
                    obj[str] = 1;
                }
                else {
                    obj[str]++;
                    if (obj[str] == arguments.length)
                    {
                        result.push(str);
                    }
                }//end else
            }//end for j
        }//end for i
        return result;
    }

    //集合去掉重复
    Array.prototype.uniquelize = function () {
        var tmp = {},
            ret = [];
        for (var i = 0, j = this.length; i < j; i++) {
            if (!tmp[this[i]]) {
                tmp[this[i]] = 1;
                ret.push(this[i]);
            }
        }

        return ret;
    }
    //并集
    Array.union = function () {
        var arr = new Array();
        var obj = {};
        for (var i = 0; i < arguments.length; i++) {
            for (var j = 0; j < arguments[i].length; j++)
            {
                var str=arguments[i][j];
                if (!obj[str])
                {
                    obj[str] = 1;
                    arr.push(str);
                }
            }//end for j
        }//end for i
        return arr;
    }*/

    //2个集合的差集 在arr不存在
    minus = function (arr1,arr) {
        var result = new Array();
        var obj = {};
        for (var i = 0; i < arr.length; i++) {
            obj[arr[i]] = 1;
        }
        for (var j = 0; j < arr1.length; j++) {
            if (!obj[arr1[j]])
            {
                obj[arr1[j]] = 1;
                result.push(arr1[j]);
            }
        }
        return result;
    };
//    数组案例：
//    Array.intersect(["1", "2", "3"], ["2", "3", "4", "5", "6"]);//交集 ---->[2,3]
//    [1, 2, 3, 2, 3, 4, 5, 6].uniquelize());去重---->[1,2,3,4,5,6]
//    Array.union(["1", "2", "3"], ["2", "3", "4", "5", "6"], ["5", "6", "7", "8", "9"])并集
//    ["2", "3", "4", "5", "6"].minus(["1", "2", "3"]);//差集 ---->[4,5,6]
	
})(jQuery);

//添加统一标题
$(function(){
	try {
		var menu2 = top.$(".layui-this > a").text().replace("","");
		var pass = $("div [class='layui-item-new-form'] > h3").html();
		if(menu2 == ""){
			menu2 = "管理员管理";
		}
		if(pass == "" || pass == null){
			pass = "管理员查询";
		}
		if(pass == "修改密码")return;
		if(menu2.indexOf("修改密码") != -1)return;
		$("div [class='layui-item-new-form'] > h3").html(menu2+"查询");
		$("div [class='layui-item-new-form clear-border'] > h3").html(menu2+"列表");
	} catch (e) {
		// TODO: handle exception
	}
})

/* 功能描述：显示导出下载列表
 * method：请求方式 默认post
 * viewUrl：请求URL_渲染下载列表
 * data:请求参数 
 * downUrl:请求URL_点击下载
 * fromId：表单ID选择器
 * 
 * */
function toExportListView (method,viewUrl,data,downUrl,fromId) {
	   if(!viewUrl)return;
	   if(!downUrl)return;
	   if(!fromId)return;
	   if(!data && data.length > 0){
		   for(var key in data){
			   data.key = encodeURI(JSON.stringify(data.key));
		   }
	   }
		var idx = layer.load(2); 
		$.ajax({
				type: method ? method : "POST",
				url:viewUrl,
				data:data,
				success:function(result){
					layer.close(idx);
					//result = $.parseJSON(result);
					if (result.success) {
						var list = result.resultData.list; 		//结果集
						var totle = result.resultData.totle; 	//总条数
						/*if(totle < list[0].pageSize){
						toReport(list[0].pageSize, 1, list[0].fileName);
						return ;
					}*/
						var html = "<div class='popup-container downloadList-container'>";
						for (var index in list) {
							html += "<div class='layui-form-item layout-bikeInfo-downloadList'>";
							html += "<label class='layui-form-downloadList'>"+list[index].fileName+"<span style='color:gray'> ["+list[index].scope+"]</span></label>";
							html += "<div style='line-height:40px;float:right;margin-right:10px'>";
							html += "<a herf='#' class='downloadBtn' onclick = toExcelReport('"+fromId+"','"+downUrl+"',"+ list[index].pageSize +","+ list[index].pageNow +",'" + list[index].fileName +"');>点击下载</a>";
							html += "</div>";
							html += "</div>";
						}
						html += "</div>";
						
						layer.open({
							type : 1,
							area : [ '550px', '400px' ],
							title : '下载列表',
							closeBtn : 1,
							shadeClose : true,
							skin : 'yourclass',
							content : html
						});
					} else if(result.errorCode == '1001'){
						layer.msg(result.msg);
					} else {
						layer.msg(result.msg);
					}
				},
				error:function (XMLHttpRequest, textStatus, errorThrown) {
					layer.close(index);
					console.debug(XMLHttpRequest);
					layer.msg('操作失败');
				}})
				
}
//点击下载（导出excel文件）
function toExcelReport(fromId,downUrl,limit, page, fileName) {
	$(fromId).attr("action",rootPath + downUrl+'?limit =' + limit + '&page=' + page + '&fileName=' + encodeURI(fileName));
	$(fromId).submit();
}			

//创建完表格后执行操作
var lastLoad = function(){
	$(".layui-table-btn").css('display','block');
	// 判断按钮个数 添加圆角样式
	if ($('.layui-btn-group').find('button').length <= 1) {
		$('.layui-btn-group').find('button').addClass('mt-round')
	}
} 



/**
 * jQuery-lazyload
 * 图片懒加载
 */
var lazyloadimg = function(){
	$("img.lazy").lazyload({
		  placeholder : rootPath+'/static/plugin/jquery-lazyload/img/loading4.gif', //用图片提前占位
		    // placeholder,值为某一图片路径.此图片用来占据将要加载的图片的位置,待图片加载时,占位图则会隐藏
		  effect: "fadeIn", // 载入使用何种效果
		    // effect(特效),值有show(直接显示),fadeIn(淡入),slideDown(下拉)等,常用fadeIn
		 // threshold: 200, // 提前开始加载
		    // threshold,值为数字,代表页面高度.如设置为200,表示滚动条在离目标位置还有200的高度时就开始加载图片,可以做到不让用户察觉
		  //event: 'click',  // 事件触发时才加载
		    // event,值有click(点击),mouseover(鼠标划过),sporty(运动的),foobar(…).可以实现鼠标莫过或点击图片才开始加载,后两个值未测试…
		  //container: $("#container"),  // 对某容器中的图片实现效果
		    // container,值为某容器.lazyload默认在拉动浏览器滚动条时生效,这个参数可以让你在拉动某DIV的滚动条时依次加载其中的图片
		 // failurelimit : 10 // 图片排序混乱时
		     // failurelimit,值为数字.lazyload默认在找到第一张不在可见区域里的图片时则不再继续加载,但当HTML容器混乱的时候可能出现可见区域内图片并没加载出来的情况,failurelimit意在加载N张可见区域外的图片,以避免出现这个问题.
		});
}


/**
 * 
 * 统一 设置layui 分页对象
 * 
 */
var pageObj = function(){
	var pageObj = {};
	pageObj.layout = ['count', 'prev', 'page', 'next', 'limit', 'skip'] //自定义分页布局
	pageObj.curr = 1 //初始页码
	pageObj.groups = 9 //只显示 9个连续页码
	pageObj.first = '首页' //设置是否显示首页---false--不显示首页
	pageObj.last = '尾页' //设置是否显示尾页----false---不显示尾页
	pageObj.prev = '上一页'  //自定义上一页显示
	pageObj.next = '下一页'  //自定义下一个显示
	pageObj.limit = 20	  //每页显示数量
	pageObjlimits = [10, 20, 30, 40, 50] //每页条数的选择项 --默认([10, 20, 30, 40, 50])
	
	return pageObj;
}
/**
 * 签名
 */
var doSign = function(obj){
	if(!obj){
		obj = {};
	}
	var dataSign = JSON.stringify(objKeySort(obj));
	console.log(dataSign);
	var privateKey = '-----BEGIN PRIVATE KEY-----'
		+'MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOO15l7NHbBNJLvTd6l+3xsvKnx5P+C0ncvy2hg4//ZsMLRZyhOD+hVbcqLKx6+oa5UerFB7N/SFYkuG+uGEQRB4apgRCGIrwg+hqs8lJtR6pk8MWuDt7+HE/TKC7cPvn/aenJLCucPo3TGPtane5pvTjW+T0y4dc0ZjF3so0ZGFAgMBAAECgYEAhTeU199+P/dRx0TXG+Uql1fHrlytH0PrkTa5mzQ1oy/2anyhOTub4kTG0zB5FG4eynXQb6E2wioKsI7+VVMvUF11u5BzcvRZ4JH850zSGQ8dXXsL6OUx/wf7U3nFEi1Z2x3LHVGcmSJavoPdcEcW+hzdvalaYHi15Yb86nRlkZ0CQQD/+Fiy1Hf/77c1UqhIUG/KTQhlHtLH3Nt4ZKzAfbh17T1wMVpbFt5r66piqSkCQS3gh6WfKVmxEPRwi77x5+wnAkEA47y1XINw3bq+NQlEyKaFQ6AiXIYcBBieTK/XibGKBTSiGEiFBIgA4/ZqmfFw2pFnbwVRZzgaVj2s8Py2Z0skcwJALe5yfCSEI/jv9zGN4OwOI08PYpXVXOfUuhXWqfPlVcPscmVowU+pOdRgDrQsF6t6f//XSGgzIALa2hc5fE8RoQJAL330CFX03JiL//1t1bY8Rk0HvWnOP+Buaqmk9jcLBGjkgNHmw3olTWTe+DmMglgeTN28Cx19CI0WGq9ozoOyFwJBAI6ldii2Tc6RBXAtYXE5FQPso2r5GozDOh+cGgFUTfH1i1/xBU0qHlX9Y1DVNLYPCWqSAmgcjXWhQwJe691m7Ts='
		+'-----END PRIVATE KEY-----';
	
         var prvKey = KEYUTIL.getRSAKeyFromPlainPKCS8PEM(privateKey);
         var sig = new KJUR.crypto.Signature({"alg": "MD5withRSA", "prov": "cryptojs/jsrsa"}); // alg为MD5WithRSA，这个还有个常见的是SHA1WithRSA，不过貌似支付宝是用的MD5，所以我们公司用的也是MD5，也许java默认的就是这个格式。  
         sig.initSign(prvKey);  // 设置key  
         var hSig = sig.signString(dataSign);  // 签名  
         hSig = hex2b64(hSig);
         console.log(hSig);
     return hSig;
}

//排序的函数
function objKeySort(arys) { 
    //先用Object内置类的keys方法获取要排序对象的属性名，再利用Array原型上的sort方法对获取的属性名进行排序，newkey是一个数组
    var newkey = Object.keys(arys).sort();　　 
    //console.log('newkey='+newkey);
    var newObj = {}; //创建一个新的对象，用于存放排好序的键值对
    for(var i = 0; i < newkey.length; i++) {
        //遍历newkey数组
        newObj[newkey[i]] = arys[newkey[i]].toString(); 
        //向新创建的对象中按照排好的顺序依次增加键值对

    }
    return newObj; //返回排好序的新对象
}