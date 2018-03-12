package com.jtc.credit.service;

import com.jtc.credit.dto.TadminUser;
import com.jtc.credit.model.DataTableParamter;
import com.jtc.credit.model.PagingData;

public interface AdminUserService {
	
	TadminUser getAdminUserById(String userId);
		
	TadminUser getAdminUserByIdOrEmail(String userId);
	
	TadminUser getAdminUserByIdOrEmail(String userId,String email);
	
	void createAdminUser(TadminUser adminUser);
	
	void updateAdminUser(TadminUser adminUser);
	
	void updateAdminUserPassword(TadminUser adminUser);
	
	void deleteAdminUser(TadminUser adminUser);
	
	void deleteAdminUserById(int id);
	
	void deleteAdminUserByIds(String[] ids);
	
	void activateUsersByIds(String[] ids);
	
	void deactivateUsersByIds(String[] ids);
	
	public PagingData loadAdminUserList(DataTableParamter rdtp);
	
	public int getAdminUserAmount();
	
	public TadminUser getTadminUsersByEmail(String email);
	
	public int getUserCount();
	public int getUserCount(Long startTime);
		
}
