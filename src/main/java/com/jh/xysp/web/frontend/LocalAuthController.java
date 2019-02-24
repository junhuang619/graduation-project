package com.jh.xysp.web.frontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jh.xysp.dto.LocalAuthExecution;
import com.jh.xysp.entity.LocalAuth;
import com.jh.xysp.entity.PersonInfo;
import com.jh.xysp.enums.LocalAuthStateEnum;
import com.jh.xysp.service.LocalAuthService;
import com.jh.xysp.util.CodeUtil;
import com.jh.xysp.util.HttpServletRequestUtil;
import com.jh.xysp.util.MD5;

@Controller
@RequestMapping(value = "/frontend", method = { RequestMethod.GET, RequestMethod.POST })
public class LocalAuthController {
	@Autowired
	private LocalAuthService localAuthService;

	@RequestMapping(value="localauthlogincheck")
	@ResponseBody
	public Map<String, Object> localAuthLoginCheck(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();

		// 是否需要校验的标志
		boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");

		// 验证码校验
		if (needVerify && !CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码不正确,请重新输入");
			return modelMap;
		}
		// 获取用户输入的用户名+密码
		String username = HttpServletRequestUtil.getString(request, "username");
		String password = HttpServletRequestUtil.getString(request, "password");

		if (username != null && password != null) {
			// 数据库中的密码是MD加密的，所以需要先将密码加密，然后再调用后台的接口
			password = MD5.getMd5(password);
			LocalAuth localAuth = localAuthService.queryLocalAuthByUserNameAndPwd(username, password);
			if (localAuth != null) {
				// 将personInfo写入session中
				request.getSession().setAttribute("user", localAuth.getPersonInfo());
				modelMap.put("success", true);
				modelMap.put("errMsg", "登录成功");
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户名或密码不正确");
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名和密码不能为空");
		}
		return modelMap;
	}

	@RequestMapping(value="/changelocalpwd",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changeLocalPwd(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 验证码校验
		if (!CodeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码不正确,请重新输入");
			return modelMap;
		}
		String username = HttpServletRequestUtil.getString(request, "userName");
		String password = HttpServletRequestUtil.getString(request, "password");
		String newpassword = HttpServletRequestUtil.getString(request, "newPassword");
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		if (username != null && password != null && newpassword != null && user != null && user.getUserId() != null
				&& !password.equals(newpassword)) {
			try {
				LocalAuth localAuth =localAuthService.queryLocalByUserId(user.getUserId());
				if (localAuth ==null || localAuth.getLocalUserName().equals(username)) {
					modelMap.put("success", false);
					modelMap.put("errMsg", "账号不一致，请重新输入");
					return modelMap;
				}
				LocalAuthExecution le=localAuthService.modifyLocalAuth(user.getUserId(), username, password, newpassword);
				if (le.getState()==LocalAuthStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", le.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入密码");
		}
		return modelMap;
	}
	@RequestMapping(value="/logout",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> logout(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		request.getSession().setAttribute("user", null);
		modelMap.put("success", true);
		return modelMap;
	}
}