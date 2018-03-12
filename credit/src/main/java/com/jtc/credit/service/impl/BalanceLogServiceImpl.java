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
import com.jtc.credit.dao.BalanceLogDao;
import com.jtc.credit.dto.TadminUser;
import com.jtc.credit.dto.TbalanceLog;
import com.jtc.credit.model.DataTableParamter;
import com.jtc.credit.model.PagingData;
import com.jtc.credit.service.BalanceLogService;

@Service
public class BalanceLogServiceImpl implements BalanceLogService{
	@Autowired
	private BalanceLogDao balanceLogDao;
	@Autowired
	private AdminUserDao adminUserDao;

	public TbalanceLog getBalanceLogById(int id) {
		// TODO Auto-generated method stub
		return balanceLogDao.get(id);
	}
	

	public void updateBalanceLog(TbalanceLog balanceLog) {
		// TODO Auto-generated method stub
		balanceLogDao.update(balanceLog);
	}

	public void createBalanceLog(TbalanceLog balanceLog) {
		// TODO Auto-generated method stub
		balanceLogDao.create(balanceLog);
	}
	
	public void createBalanceLog(TbalanceLog balanceLog,TadminUser user){
		//如果充值记录对象的ID字段有值，则表示是用户申请充值记录的ID
		Integer checkId=balanceLog.getId();
		if(checkId!=null){							
			balanceLog.setId(null);
			TbalanceLog checkRecord=balanceLogDao.get(checkId);
			//先关闭用户申请充值记录
			checkRecord.setStatus(false);
			balanceLogDao.update(checkRecord);
		}
		balanceLogDao.create(balanceLog);
		user.setBalance(user.getBalance()+balanceLog.getAmount());
		adminUserDao.update(user);
		
	}
		
	public void deleteBalanceLog(TbalanceLog balanceLog) {
		balanceLogDao.delete(balanceLog);
	}

	public void deleteBalanceLogById(int id) {
		balanceLogDao.delete(id);
		
	}

	@SuppressWarnings("unchecked")
	public List<TbalanceLog> loadAllLogByUser(String userId) {		
		Criteria criteria=balanceLogDao.createCriteria();
		criteria.add(Restrictions.eq("userId", userId));
		criteria.addOrder(Order.desc("createdTime"));
		return criteria.list();
	}

	/**
	 * 用户充值总金额
	 */
	public double getTotalAmountByUser(String userId) {
		Criteria criteria=balanceLogDao.createCriteria();
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.ge("type", false));
		Double total=(Double)criteria.setProjection(Projections.sum("amount")).uniqueResult();	
		if(total==null)total=0.0;
		return total;	
	}

	/**
	 * 用户在指定时间后充值总金额
	 */
	public double getTotalAmountByUser(String userId, Long startTime) {
		Criteria criteria=balanceLogDao.createCriteria();
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.ge("type", false));
		criteria.add(Restrictions.ge("createdTime", startTime));		
		Double total=(Double)criteria.setProjection(Projections.sum("amount")).uniqueResult();	
		if(total==null)total=0.0;
		return total;	
	}

	/**
	 * 在指定时间后充值总次数
	 */
	public long getTotalUser(Long startTime) {
		Criteria criteria=balanceLogDao.createCriteria();	
		criteria.add(Restrictions.ge("type", false));
		criteria.add(Restrictions.ge("createdTime", startTime));
		Long total=(Long)criteria.setProjection(Projections.count("userId")).uniqueResult();
		if(total==null)total=0L;
		return total;	
	}

	/**
	 * 充值总金额
	 */
	public double getTotalAmount() {
		Criteria criteria=balanceLogDao.createCriteria();	
		criteria.add(Restrictions.ge("type", false));
		Double total=(Double)criteria.setProjection(Projections.sum("amount")).uniqueResult();
		if(total==null)total=0.0;
		return total;	
	}

	/**
	 * 在指定时间后充值总金额
	 */
	public double getTotalAmount(Long startTime) {
		Criteria criteria=balanceLogDao.createCriteria();	
		criteria.add(Restrictions.ge("type", false));
		criteria.add(Restrictions.ge("createdTime", startTime));
		Double total=(Double)criteria.setProjection(Projections.sum("amount")).uniqueResult();	
		if(total==null)total=0.0;
		return total;	
	}


	public PagingData loadAll(DataTableParamter rdtp){
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy/MM/dd");
		String searchJsonStr=rdtp.getsSearch();	
		List<Criterion> criterionsList=new ArrayList<Criterion>();
		if(searchJsonStr!=null&&!searchJsonStr.isEmpty()){			
			JSONObject jsonObj= (JSONObject)JSON.parse(searchJsonStr);
			Set<String> keys=jsonObj.keySet();						
			for (String key : keys) {
				String val=jsonObj.getString(key);
				if(val!=null&&!val.isEmpty()){
					if(key=="startTime"){
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
					else if(key=="type"){	
						Boolean type=Boolean.valueOf(val);
						criterionsList.add(Restrictions.eq("type", type));
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
			return balanceLogDao.findPage("createdTime",false,criterions,rdtp.iDisplayStart, rdtp.iDisplayLength);
		}
		
		return balanceLogDao.findPage("createdTime",false,rdtp.iDisplayStart, rdtp.iDisplayLength);
	}

	/**
	 * 导出excel报表所用查询
	 */
	@SuppressWarnings("unchecked")
	public List<TbalanceLog> loadLog(String userId, String startTime, String endTime,String type) {
		Criteria criteria=balanceLogDao.createCriteria();
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
		if(type!=null&&!type.isEmpty()){
			criteria.add(Restrictions.eq("type", new Boolean(type)));
		}
		criteria.addOrder(Order.desc("createdTime"));
		return criteria.list();
	}
    
		
}
