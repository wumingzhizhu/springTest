package com.zeromk.study.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zeromk.study.dao.StudyLoginDao;
import com.zeromk.study.entity.StudyLogin;
import com.zeromk.study.service.StudyLoginService;

@Service("StudyLoginService")
public class StudyLoginServiceImpl implements StudyLoginService{
	
	@Autowired
	private StudyLoginDao studyLoginDao;

	public List<StudyLogin> selectByMap( Map<String, String> map) {
		// TODO Auto-generated method stub
		return studyLoginDao.selectByMap( map );
	}

	public StudyLogin selectById( int id ) {
		// TODO Auto-generated method stub
		return studyLoginDao.selectById( id );
	}

}
