//下面用于图片上传预览功能

function setImagePreview(fileId, imgId,fileName,uploadBut,type) {
	var docObj = document.getElementById(fileId);
	var docObjValue = docObj.value;
	if(docObjValue){
		//$('#'+uploadBut).css("display","inline");
		newFiles = docObj.files[0];
		newFileValue = docObjValue;;
	}else{
		//$('#'+uploadBut).css("display","none");
		docObj.files[0] = newFiles;
		docObjValue = newFileValue;
	}
	
	var imgObjPreview = document.getElementById(imgId);
	if(type == 1){
		if (!/\.(mp4|MP4)$/.test(docObjValue.substring(docObjValue.lastIndexOf('.'))) && docObjValue) {  
			//$('#'+uploadBut).css("display","none");
			layer.msg("视频格式错误！请重新选择（MP4）格式视频");  
			return false;  
		}
	}else{
		if (!/\.(gif|jpg|jpeg|png|JPG|PNG|GIF)$/.test(docObjValue.substring(docObjValue.lastIndexOf('.'))) && docObjValue) {  
			//$('#'+uploadBut).css("display","none");
			layer.msg("图片类型错误！请重新选择（gif，jpg，jpeg，png，JPG，PNG，GIF）格式图片");  
			return false;  
		}
	}
	
	if(fileName && fileName.trim()){
		$("#"+fileName).val(docObj.files[0].lastModified + docObj.files[0].name);
	}
	
	if (docObj.files && docObj.files[0]) {
		// 火狐下，直接设img属性
		imgObjPreview.style.display = 'block';
		/*imgObjPreview.style.width = '150px';
		imgObjPreview.style.height = '180px';*/
		// imgObjPreview.src = docObj.files[0].getAsDataURL();

		// 火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
		if(type==1){
		    var videoUrl = window.URL.createObjectURL(docObj.files[0]);
			document.getElementById("my-video").src=videoUrl;
            document.getElementById("my-video").play();
		}else{
			
			imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
		}
		 
	} else {
		// IE下，使用滤镜
		docObj.select();
		var imgSrc = document.selection.createRange().text;
		//var localImagId = document.getElementById("localImag");
		// 必须设置初始大小
		/*localImagId.style.width = "150px";
		localImagId.style.height = "180px";*/
		// 图片异常的捕捉，防止用户修改后缀来伪造图片
		try {
			localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
			localImagId.filters
					.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
		} catch (e) {
			layer.msg("您上传的图片格式不正确，请重新选择!");
			return false;
		}
		imgObjPreview.style.display = 'none';
		document.selection.empty();
	}
	return true;
}