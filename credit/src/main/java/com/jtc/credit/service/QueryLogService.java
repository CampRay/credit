package com.jtc.credit.service;

import java.util.List;

import com.jtc.credit.dto.TadminUser;
import com.jtc.credit.dto.TqueryLog;
import com.jtc.credit.model.DataTableParamter;
import com.jtc.credit.model.PagingData;

public interface QueryLogService{
	TqueryLog getQueryLogById(int id);
	void updateQueryLog(TqueryLog queryLog);	
	void createQueryLog(TqueryLog queryLog);
	public void createQueryLog(TqueryLog queryLog,TadminUser user);
	public PagingData loadAll(DataTableParamter rdtp);
	
	public List<TqueryLog> loadAllLogByUser(String userId);
	
	public List<TqueryLog> loadAllLogByUser(String userId,int page,Long startDate,Long endDate);
	
	public int getCountByUser(String userId,Long startDate,Long endDate);
	
	public long getTotalUser(Long startTime);
	
	public double getTotalAmount();
	public double getTotalAmount(Long startTime);
	
	public double getTotalAmountByUser(String userId);
	public double getTotalAmountByUser(String userId,Long startTime);
	
	public List<TqueryLog> loadLog(String userId,String startTime,String endTime,String apiId);
}
