package com.cn21.study.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cn21.study.entity.StudyLogin;

@Service("studyLoginDao")
public interface StudyLoginDao {
	
	public List<StudyLogin> selectByMap(Map<String, String> map);
	
	public StudyLogin selectById(int id);

}
