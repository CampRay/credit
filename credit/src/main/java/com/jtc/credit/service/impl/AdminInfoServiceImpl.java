package com.jtc.credit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jtc.credit.dao.AdminInfoDao;
import com.jtc.credit.dto.TadminInfo;
import com.jtc.credit.service.AdminInfoService;

@Service
public class AdminInfoServiceImpl implements AdminInfoService{
	@Autowired
	private AdminInfoDao adminInfoDao;

	public TadminInfo getAdminInfoById(String adminId) {
		// TODO Auto-generated method stub
		return adminInfoDao.get(adminId);
	}
	

	public void updateAdminInfo(TadminInfo adminInfo) {
		// TODO Auto-generated method stub
		adminInfoDao.update(adminInfo);
	}

	public void createAdminInfo(TadminInfo adminInfo) {
		// TODO Auto-generated method stub
		adminInfoDao.create(adminInfo);
	}
    
		
}
