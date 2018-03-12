package com.jtc.credit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jtc.credit.dao.ApiPriceDao;
import com.jtc.credit.dto.TapiPrice;
import com.jtc.credit.model.DataTableParamter;
import com.jtc.credit.model.PagingData;
import com.jtc.credit.service.ApiPriceService;

@Service
public class ApiPriceServiceImpl implements ApiPriceService{
	@Autowired
	private ApiPriceDao apiPriceDao;

	public TapiPrice getApiPriceById(int id) {
		// TODO Auto-generated method stub
		return apiPriceDao.get(id);
	}
	

	public void updateApiPrice(TapiPrice apiPrice) {
		// TODO Auto-generated method stub
		apiPriceDao.update(apiPrice);
	}

	public void createApiPrice(TapiPrice apiPrice) {
		// TODO Auto-generated method stub
		apiPriceDao.create(apiPrice);
	}


	public PagingData loadAll(DataTableParamter rdtp) {		
		return apiPriceDao.findPage(rdtp.iDisplayStart, rdtp.iDisplayLength);
	}


	public List<TapiPrice> loadAll() {
		return apiPriceDao.LoadAll();		
	}
    
	
}
