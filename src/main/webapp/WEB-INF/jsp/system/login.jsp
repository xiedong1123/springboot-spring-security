<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页面</title>
<%@include file="/static/common/common.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/plugin/verify-master/css/verify.css">
<script type="text/javascript"
	src="${ctx}/static/plugin/verify-master/js/verify.js"></script>
<script type="text/javascript" src="${ctx}/static/plugin/rsa/jsencrypt.js"></script>

<script type="text/javascript">
	//解决session失效,iframe中显示登录页面的问题.
	if (window != top) {
		top.location.href = "${ctx}/login.do";
	}
</script>

<!-- 回车事件 -->
<script type="text/javascript">
	$(function() {
		$(document.documentElement).bind("keydown", function(event) {
			/* if(event.keyCode==13){//回车键  == 13
				submitForm("#loginForm");
			} */
		});

		$('#mpanel4').slideVerify({
			type : 2, //类型
			vOffset : 5, //误差量，根据需求自行调整
			vSpace : 5, //间隔
			imgUrl : rootPath + '/static/plugin/verify-master/images/',
			imgName : [ '1.jpg', '2.jpg', '3.jpg' ],
			imgSize : {
				width : '265px',
				height : '100px',
			},
			blockSize : {
				width : '30px',
				height : '30px',
			},
			barSize : {
				width : '265px',
				height : '20px',
			},
			ready : function() {
			},
			success : function(obj) {
				$.ajax({
					type : "POST",
					async : true,
					url : rootPath + '/validateCode',
					success : function(data) {
						//data = $.parseJSON(data);
						$("#validateCode").val(data);
						$("#submit").removeClass("my-but-dis");
						$("#submit").removeAttr("disabled");
					},
					error : function() {
					}
				});
			},
			error : function() {
				//              alert('验证失败！');
			}

		});
	});

	function changeImg() {
		var img = document.getElementById("img");
		img.src = "${ctx}/authImage?date=" + new Date().getTime();
	}
	function resetFrom() {
		$("#username").val("");
		$("#pwd").val("");
	}
	
//=============================================================================	
	var publicKey;
    $.ajax({
        type : "POST",
        async : true,
        url : rootPath + '/getPublicKey',
        success : function(data) {
            publicKey = data;
        },
        error : function() {
        }
    });
    
    function loginSubmit(){
        var pwd = $("#pwd").val();
        var encrypt = new JSEncrypt();
        encrypt.setPublicKey(publicKey);
        var newPwd = encrypt.encrypt(pwd)
        $("#password").val(newPwd);
        $("#login").submit();
    }
</script>

<style>
.error {
	color: red;
}

.info {
	color: red;
}

#login-extra {
	width: 100%;
	text-align: center;
	position: fixed;
	bottom: 20px;
	z-index: 10;
}

#login-extra p, span, span a {
	font-size: 14px;
	color: #fff;
}

.my-but-dis{
	background: #e7e7e7 !important; 
}
</style>

</head>
<body>
	<div class="login-dbox">
		<div class="login-box" style="height: 333px;">
			<div class="dt"></div>
			<div class="loginbox" style="height: 299px;">
				<form class="layui-form" action="${ctx}/login" role="form" id="login"
					method="post">
					<div class="layui-form-item">
						<div class="layui-input-block">
							<input type="text" name="username" id="username"
								lay-verify="required" autocomplete="off" class="layui-input"
								value="admin" placeholder="账号">
						</div>
					</div>
					<div class="layui-form-item">
						<div class="layui-input-block">
							<input type="password" id="pwd"
								lay-verify="required" autocomplete="off" class="layui-input"
								value="123456" placeholder="密码">
							<input type="hidden" name="password" id="password"
								lay-verify="required" autocomplete="off" class="layui-input"
								value="123456" placeholder="密码">
						</div>
					</div>
					<div class="layui-inline">
						<div class="layui-form-item">
							<div class="layui-input-block yz-box">
								<div id="mpanel4"></div>
								<input type="hidden" id="validateCode" name="validate_code"
									value="">
								<%--  <input type="text" name="validateCode" style="width: 95px;" maxlength="4" size="10" autocomplete="off" placeholder="验证码" class="layui-input">
					  <div style="margin-top:0px;" class="code"><img id="img" src="${ctx}/authImage" />
	                    	<a href='javascript:;' onclick="changeImg()"><label style="color:#fdc288;text-decoration: underline">看不清?</label></a>
	                  </div> --%>

							</div>
						</div>
					</div>


					<div class="layui-form-item" style="margin-top: 10px;">
						<div class="layui-input-block">
							<button style="width: 45%;" onclick="loginSubmit()" class="my-but-dis layui-btn" id="submit" disabled="disabled"
								type="submit">登录</button>
							<button style="width: 45%;" onclick="resetFrom()"
								class="layui-btn" type="button">重置</button>
						</div>
					</div>
					<p style="cursor: pointer;">
						<%-- <a href="javascript:;" style="color:#fdc288;text-decoration: underline;cursor: pointer;">忘记密码?<a>&nbsp;&nbsp;--%>
						<span class="error"> ${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message }</span>
					</p>
				</form>
			</div>

		</div>
		<div id="login-extra">
			<p style="color: #979696;">【推荐使用Chrome内核浏览器或ie11以上版本的浏览器】</p>
			<span style="color: #979696;">谷歌Chrome浏览器点击立即下载： <a
				href="http://www.google.cn/chrome/browser/desktop/index.html"
				style="color: #fdc288; text-decoration: underline" target="_blank">下载地址1</a>
				<a
				href="https://www.baidu.com/link?url=z8xXZhgqTjHr95gdk-kuKRGY7OgA9wJMzpaPpz91PPuRvolBcE6nFzOujdZTgvVAmrEfDDpYS6i1l8N6Ji8t_D30Tp6p7y8hWpBhkP9La-m&wd=&eqid=bd4bd794000a1599000000055849061d"
				style="color: #fdc288; text-decoration: underline" target="_blank">下载地址2</a>
			</span>
		</div>
	</div>
</body>
</html>