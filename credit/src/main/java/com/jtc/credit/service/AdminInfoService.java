package com.jtc.credit.service;

import com.jtc.credit.dto.TadminInfo;

public interface AdminInfoService{
	TadminInfo getAdminInfoById(String adminId);
	void updateAdminInfo(TadminInfo adminInfo);	
	void createAdminInfo(TadminInfo adminInfo);
}
