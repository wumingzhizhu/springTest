package com.zeromk.study.service;

import java.util.List;
import java.util.Map;


import com.zeromk.study.entity.StudyLogin;

public interface StudyLoginService {
	
	public List<StudyLogin> selectByMap(Map<String, String> map);
	
	public StudyLogin selectById(int id);

}
