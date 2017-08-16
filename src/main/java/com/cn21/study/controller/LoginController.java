package com.cn21.study.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn21.study.entity.StudyLogin;
import com.cn21.study.service.StudyLoginService;

@Controller
public class LoginController {
	
	@Autowired
	private StudyLoginService studyLoginService;
	
	@RequestMapping("/admin")
	public String getList(HttpServletRequest request){
		return "/view/login/list";
	}
	
	@RequestMapping("/admin/login")
	@ResponseBody
	public String Login(HttpServletRequest request){
		String name = request.getParameter( "name" );
		String passwrod = request.getParameter( "password" );
		StudyLogin studyLogin = studyLoginService.selectById( 1 );
		System.out.println( studyLogin.getName() );
		Map<String, String> map = new HashMap<String, String>();
		map.put( "name", name );
		map.put( "password", passwrod );
		List<StudyLogin> list = studyLoginService.selectByMap( map );
		for(StudyLogin temp : list){
			System.out.println( temp.getId() );
		}
		return "success";
	}
	
	

}
