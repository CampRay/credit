package com.jtc.credit.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtc.credit.dao.AdminUserDao;
import com.jtc.credit.dao.QueryLogDao;
import com.jtc.credit.dto.TadminUser;
import com.jtc.credit.dto.TqueryLog;
import com.jtc.credit.model.DataTableParamter;
import com.jtc.credit.model.PagingData;
import com.jtc.credit.service.QueryLogService;

@Service
public class QueryLogServiceImpl implements QueryLogService{
	@Autowired
	private QueryLogDao queryLogDao;
	@Autowired
	private AdminUserDao adminUserDao;

	public TqueryLog getQueryLogById(int id) {
		// TODO Auto-generated method stub
		return queryLogDao.get(id);
	}
	

	public void updateQueryLog(TqueryLog queryLog) {
		// TODO Auto-generated method stub
		queryLogDao.update(queryLog);
	}

	public void createQueryLog(TqueryLog queryLog) {
		// TODO Auto-generated method stub
		queryLogDao.create(queryLog);
	}
	
	public void createQueryLog(TqueryLog queryLog,TadminUser user) {
		// TODO Auto-generated method stub		
		queryLogDao.create(queryLog);
		user.setBalance(user.getBalance()-queryLog.getAmount());
		adminUserDao.update(user);
	}
    
	@SuppressWarnings("unchecked")
	public List<TqueryLog> loadAllLogByUser(String userId) {		
		Criteria criteria=queryLogDao.createCriteria();
		criteria.add(Restrictions.eq("userId", userId));
		criteria.addOrder(Order.desc("createdTime"));
		return criteria.list();
	}
	
	public PagingData loadAll(DataTableParamter rdtp){
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy/MM/dd");
		String searchJsonStr=rdtp.getsSearch();				
		if(searchJsonStr!=null&&!searchJsonStr.isEmpty()){
			List<Criterion> criterionsList=new ArrayList<Criterion>();
			JSONObject jsonObj= (JSONObject)JSON.parse(searchJsonStr);
			Set<String> keys=jsonObj.keySet();						
			for (String key : keys) {
				String val=jsonObj.getString(key);
				if(val!=null&&!val.isEmpty()){
					if(key=="apiId"){
						criterionsList.add(Restrictions.eq("apiId", jsonObj.getInteger(key)));
					}
					else if(key=="startTime"){
						Date sdate = null;
						try {
							sdate = simpleDateFormat.parse(val);							
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Long startLong=sdate.getTime();						
						criterionsList.add(Restrictions.ge("createdTime", startLong));
					}
					else if(key=="endTime"){
						Date edate = null;
						try {
							edate = simpleDateFormat.parse(val);							
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Long endLong=edate.getTime();						
						criterionsList.add(Restrictions.le("createdTime", endLong));
					}
					else{
						criterionsList.add(Restrictions.eq(key, jsonObj.get(key)));
					}
				}
			}
			Criterion[] criterions=new Criterion[criterionsList.size()];
			int i=0;
			for (Criterion criterion : criterionsList) {
				criterions[i]=criterion;	
				i++;
			}
			return queryLogDao.findPage("createdTime",false,criterions,rdtp.iDisplayStart, rdtp.iDisplayLength);
		}
		
		return queryLogDao.findPage("createdTime",false,rdtp.iDisplayStart, rdtp.iDisplayLength);
	}
	
	@SuppressWarnings("unchecked")
	public List<TqueryLog> loadAllLogByUser(String userId,int page,Long startDate,Long endDate) {		
		Criteria criteria=queryLogDao.createCriteria();
		criteria.add(Restrictions.eq("userId", userId));
		if(startDate!=null){
			criteria.add(Restrictions.ge("createdTime", startDate));
		}
		if(endDate!=null){
			criteria.add(Restrictions.le("createdTime", endDate));
		}
		criteria.addOrder(Order.desc("createdTime"));
		criteria.setFirstResult((page-1)*20+1).setMaxResults(20);
		return criteria.list();
	}

	/**
	 * 指定用户在某一时间内的查询总次数
	 */
	public int getCountByUser(String userId, Long startDate, Long endDate) {
		Criteria criteria=queryLogDao.createCriteria();
		criteria.add(Restrictions.eq("userId", userId));
		if(startDate!=null){
			criteria.add(Restrictions.ge("createdTime", startDate));
		}
		if(endDate!=null){
			criteria.add(Restrictions.le("createdTime", endDate));
		}
		      
        int totalCount = ((Long)criteria.setProjection(
            Projections.rowCount()).uniqueResult()).intValue();
        
        return totalCount;
	}
	
	/**
	 * 在某一时间后的查询总次数
	 */
	public long getTotalUser(Long startTime) {
		Criteria criteria=queryLogDao.createCriteria();		
		if(startTime!=null){
			criteria.add(Restrictions.ge("createdTime", startTime));
		}
		Long total=(Long)criteria.setProjection(Projections.count("userId")).uniqueResult();	
		if(total==null)total=0L;
		return total;	
	}
	/**
	 * 指定用户的查询总金额
	 */
	public double getTotalAmountByUser(String userId) {
		Criteria criteria=queryLogDao.createCriteria();
		criteria.add(Restrictions.eq("userId", userId));
		Double total=(Double)criteria.setProjection(Projections.sum("amount")).uniqueResult();	
		if(total==null)total=0.0;
		return total;	
	}

	/**
	 * 指定用户在某一时间后的查询总金额
	 */
	public double getTotalAmountByUser(String userId, Long startTime) {
		Criteria criteria=queryLogDao.createCriteria();
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.ge("createdTime", startTime));
		Double total=(Double)criteria.setProjection(Projections.sum("amount")).uniqueResult();	
		if(total==null)total=0.0;
		return total;	
	}
	
	/**
	 * 查询总金额
	 */
	public double getTotalAmount() {
		Criteria criteria=queryLogDao.createCriteria();		
		Double total=(Double)criteria.setProjection(Projections.sum("amount")).uniqueResult();	
		if(total==null)total=0.0;
		return total;	
	}

	/**
	 * 在某一时间后的查询总金额
	 */
	public double getTotalAmount(Long startTime) {
		Criteria criteria=queryLogDao.createCriteria();		
		criteria.add(Restrictions.ge("createdTime", startTime));
		Double total=(Double)criteria.setProjection(Projections.sum("amount")).uniqueResult();	
		if(total==null)total=0.0;
		return total;	
	}
	
	@SuppressWarnings("unchecked")
	public List<TqueryLog> loadLog(String userId, String startTime, String endTime, String apiId) {
		Criteria criteria=queryLogDao.createCriteria();
		if(userId!=null&&!userId.isEmpty()){
			criteria.add(Restrictions.eq("userId", userId));
		}
		if(startTime!=null&&!startTime.isEmpty()){
			SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy/MM/dd");
			Date sdate = null;
			try {
				sdate = simpleDateFormat.parse(startTime);							
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Long startLong=sdate.getTime();						
			criteria.add(Restrictions.ge("createdTime", startLong));
		}
		if(endTime!=null&&!endTime.isEmpty()){
			SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy/MM/dd");
			Date sdate = null;
			try {
				sdate = simpleDateFormat.parse(endTime);							
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Long endLong=sdate.getTime();						
			criteria.add(Restrictions.le("createdTime", endLong));
		}
		if(apiId!=null&&!apiId.isEmpty()){
			Integer api=Integer.parseInt(apiId);
			criteria.add(Restrictions.eq("apiId", api));
		}
		criteria.addOrder(Order.desc("createdTime"));
		return criteria.list();
	}
		
}
