package com.jtc.credit.service;

import java.util.List;

import com.jtc.credit.dto.TapiPrice;
import com.jtc.credit.model.DataTableParamter;
import com.jtc.credit.model.PagingData;

public interface ApiPriceService{
	TapiPrice getApiPriceById(int id);
	void updateApiPrice(TapiPrice apiPrice);	
	void createApiPrice(TapiPrice apiPrice);
	public PagingData loadAll(DataTableParamter rdtp);
	public List<TapiPrice> loadAll();
}
