package com.jtc.credit.service;

import java.util.List;

import com.jtc.credit.dto.TadminUser;
import com.jtc.credit.dto.TbalanceLog;
import com.jtc.credit.model.DataTableParamter;
import com.jtc.credit.model.PagingData;

public interface BalanceLogService{
	TbalanceLog getBalanceLogById(int id);
	void updateBalanceLog(TbalanceLog balanceLog);	
	void createBalanceLog(TbalanceLog balanceLog);
	void createBalanceLog(TbalanceLog balanceLog,TadminUser user);
	
	public void deleteBalanceLog(TbalanceLog balanceLog);

	public void deleteBalanceLogById(int id);
	
	public PagingData loadAll(DataTableParamter rdtp);
	public List<TbalanceLog> loadAllLogByUser(String userId);
	
	public long getTotalUser(Long startTime);
	
	public double getTotalAmount();
	public double getTotalAmount(Long startTime);
	
	public double getTotalAmountByUser(String userId);
	public double getTotalAmountByUser(String userId,Long startTime);
	
	public List<TbalanceLog> loadLog(String userId,String startTime,String endTime,String type);
}
