package com.zeromk.study.dao;

import java.util.List;
import java.util.Map;

import com.zeromk.study.entity.StudyLogin;
import org.springframework.stereotype.Service;

@Service("studyLoginDao")
public interface StudyLoginDao {
	
	public List<StudyLogin> selectByMap(Map<String, String> map);
	
	public StudyLogin selectById(int id);

}
