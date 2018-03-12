package com.jtc.credit.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.jtc.credit.dto.Tsetting;

public interface SystemSettingService {
	
	Tsetting getSystemsettingById(Integer Id);
	
	void createSystemsetting(Tsetting setting);
	
	void updateSystemsetting(Tsetting setting);		
	
	List<Tsetting> getAllSystemSetting();
	
	Tsetting getSystemSettingByName(String name);
	
	public void cachedSystemSettingData() throws UnsupportedEncodingException, IOException;

}
