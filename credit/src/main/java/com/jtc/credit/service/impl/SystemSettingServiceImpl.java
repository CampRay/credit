package com.jtc.credit.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jtc.credit.commons.SystemConstants;
import com.jtc.credit.dao.SettingDao;
import com.jtc.credit.dto.Tsetting;
import com.jtc.credit.service.SystemSettingService;
@Service
public class SystemSettingServiceImpl implements SystemSettingService {

	
	@Autowired
	private SettingDao settingDao;

	
	public Tsetting getSystemsettingById(Integer Id) {
		// TODO Auto-generated method stub
		return settingDao.get(Id);
	}

	
	public void createSystemsetting(Tsetting setting) {
		// TODO Auto-generated method stub
		settingDao.create(setting);
	}

	
	public void updateSystemsetting(Tsetting setting) {
		// TODO Auto-generated method stub
		settingDao.update(setting);
		try {
			this.cachedSystemSettingData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		

	public List<Tsetting> getAllSystemSetting() {
		// TODO Auto-generated method stub
		return settingDao.LoadAll();
	}			

	public Tsetting getSystemSettingByName(String name) {
		// TODO Auto-generated method stub
		return settingDao.findUnique("name", name);
	}

	public void cachedSystemSettingData() throws IOException {
		// TODO Auto-generated method stub
		List <Tsetting> setingList = getAllSystemSetting();				
		SystemConstants.Admin_Setting_Map.clear();		
		
		for(Tsetting setting:setingList){
			SystemConstants.Admin_Setting_Map.put(setting.getName(),new String(setting.getValue(),"UTF-8"));			
		}
		
	}

}
