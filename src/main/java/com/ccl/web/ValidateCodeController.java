package com.ccl.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccl.base.utils.Const;
import com.ccl.base.utils.crypt.RSAUtils;
import com.ccl.base.utils.file.ValidateCode;

/**
 * 
 * @ClassName：ValidateCodeController
 * @Description：生成验证码
 * @author：xiedong
 * @Date：2017年12月14日下午5:51:44 @version：1.0.0
 */
@Controller
public class ValidateCodeController {

	@RequestMapping("/authImage")
	public void authImage(HttpServletResponse resp) throws IOException {
		OutputStream sos = resp.getOutputStream();
		ValidateCode vCode = new ValidateCode(110, 34, 4, 150);

		// ShiroUtils.setValidateCode(vCode.getCode());

		vCode.write(sos);
	}

	@RequestMapping("/validateCode")
	@ResponseBody
	public String validateCode(HttpServletRequest request) throws IOException {
		String code = UUID.randomUUID().toString().replace("-", "");
		//验证码放入session中
		request.getSession().setAttribute(Const.VALIDATECODE_IN_SESSION, code);
		return code;
	}

	@RequestMapping("/getPublicKey")
	@ResponseBody
	public String getPublicKey(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> initKey = RSAUtils.getInstance().initKey();
		String publicKey = null;
		try {
			publicKey = RSAUtils.getInstance().getPublicKey(initKey);
			String privateKey = RSAUtils.getInstance().getPrivateKey(initKey);
			request.getSession().setAttribute(Const.PRIVATEKEY_IN_SESSION, privateKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return publicKey;
	}
}
