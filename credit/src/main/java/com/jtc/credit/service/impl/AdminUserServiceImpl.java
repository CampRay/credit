/**   
 * @Title: AdminUserServiceImpl.java 
 * @Package com.uswop.service 
 *
 * @Description: User Points Management System
 * 
 * @date Sep 11, 2014 7:21:25 PM
 * @version V1.0   
 */ 
package com.jtc.credit.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtc.credit.dao.AdminUserDao;
import com.jtc.credit.dto.TadminUser;
import com.jtc.credit.model.DataTableParamter;
import com.jtc.credit.model.PagingData;
import com.jtc.credit.service.AdminUserService;

/** 
 * <p>Types Description</p>
 * @ClassName: AdminUserServiceImpl 
 * @author Phills Li 
 * 
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {
	
	@Autowired
	private AdminUserDao adminUserDao;
				

	/**
	 * (non-Javadoc)
	 * <p>Title: getAdminUserById</p> 
	 * <p>Description: </p> 
	 * @param userId
	 * @return 
	 * @see com.bps.service.AdminUserService#getAdminUserById(java.lang.String) 
	 */
	public TadminUser getAdminUserById(String userId) {
		return adminUserDao.get(userId);	
	}
	
	
	/**
	 * (non-Javadoc)
	 * <p>Title: getAdminUserByIdOrEmail</p> 
	 * <p>Description: </p> 
	 * @param userId
	 * @return TadminUser
	 * @see com.bps.service.AdminUserService#getAdminUserByIdOrEmail(java.lang.String) 
	 */
	public TadminUser getAdminUserByIdOrEmail(String userId) {
		Criteria criteria=adminUserDao.createCriteria();
		criteria.add(Restrictions.or(Restrictions.eq("adminId", userId),Restrictions.eq("email", userId)));		
		return adminUserDao.findUnique(criteria);	
	}
	
	/**
	 * (non-Javadoc)
	 * <p>Title: getAdminUserByIdOrEmail</p> 
	 * <p>Description: </p> 
	 * @param userId
	 * @param email
	 * @return TadminUser
	 * @see com.bps.service.AdminUserService#getAdminUserByIdOrEmail(java.lang.String) 
	 */
	public TadminUser getAdminUserByIdOrEmail(String userId,String email) {
		Criteria criteria=adminUserDao.createCriteria();
		criteria.add(Restrictions.or(Restrictions.eq("adminId", userId),Restrictions.eq("email", userId),Restrictions.eq("adminId", email),Restrictions.eq("email", email)));		
		return adminUserDao.findUnique(criteria);	
	}
	

	/**
	 * (non-Javadoc)
	 * <p>Title: createAdminUser</p> 
	 * <p>Description: </p> 
	 * @param adminUser 
	 * @see com.bps.service.AdminUserService#createAdminUser(com.bps.dto.TadminUser) 
	 */
	public void createAdminUser(TadminUser adminUser) {		
		adminUserDao.create(adminUser);		
	}

	/**
	 * (non-Javadoc)
	 * <p>Title: updateAdminUser</p> 
	 * <p>Description: </p> 
	 * @param adminUser 
	 * @see com.bps.service.AdminUserService#updateAdminUser(com.bps.dto.TadminUser) 
	 */
	public void updateAdminUser(TadminUser adminUser) {
	
		adminUserDao.update(adminUser);

	}

	/**
	 * (non-Javadoc)
	 * <p>Title: deleteAdminUser</p> 
	 * <p>Description: </p> 
	 * @param adminUser 
	 * @see com.bps.service.AdminUserService#deleteAdminUser(com.bps.dto.TadminUser) 
	 */
	public void deleteAdminUser(TadminUser adminUser) {
		adminUserDao.delete(adminUser);
	}

	public void deleteAdminUserById(int id) {
		adminUserDao.delete(id);
		
	}

	public void deleteAdminUserByIds(String[] ids) {
		adminUserDao.deleteAll(ids);				
	}

	public PagingData loadAdminUserList(DataTableParamter rdtp) {
		String searchJsonStr=rdtp.getsSearch();
		List<Criterion> criterionsList=new ArrayList<Criterion>();
		criterionsList.add(Restrictions.eq("roleId", new Integer(2)));
		if(searchJsonStr!=null&&!searchJsonStr.isEmpty()){			
			JSONObject jsonObj= (JSONObject)JSON.parse(searchJsonStr);
			Set<String> keys=jsonObj.keySet();						
			for (String key : keys) {
				String val=jsonObj.getString(key);
				if(val!=null&&!val.isEmpty()){
					if(key=="status"){
						criterionsList.add(Restrictions.eq(key, jsonObj.getBoolean(key)));
					}					
					else{
						criterionsList.add(Restrictions.eq(key, jsonObj.get(key)));
					}
				}
			}						
		}
		Criterion[] criterions=new Criterion[criterionsList.size()];
		int i=0;
		for (Criterion criterion : criterionsList) {
			criterions[i]=criterion;	
			i++;
		}
		return adminUserDao.findPage(criterions,rdtp.iDisplayStart, rdtp.iDisplayLength);
	}
	
   public int getAdminUserAmount() {
		// TODO Auto-generated method stub
		return adminUserDao.getCount();
	}


   public void updateAdminUserPassword(TadminUser adminUser) {
		// TODO Auto-generated method stub
		adminUserDao.update(adminUser);
	}
	
   public void activateUsersByIds(String[] ids) {
		adminUserDao.activateusers(ids);	
	}
	
   public void deactivateUsersByIds(String[] ids) {
		adminUserDao.deactivateusers(ids);	
	}
	
	public TadminUser getTadminUsersByEmail(String email) {
		return adminUserDao.findUnique("email", email);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TadminUser> loadAllAdminUserList() {
		Criteria criteria=adminUserDao.createCriteria();
		criteria.add(Restrictions.eq("roleId", new Integer(2)));
		return criteria.list();
	}


	public int getUserCount() {
		Criteria criteria=adminUserDao.createCriteria();
		criteria.add(Restrictions.eq("roleId", new Integer(2)));
		int totalCount = ((Long)criteria.setProjection(
	            Projections.rowCount()).uniqueResult()).intValue();
		return totalCount;
	}


	public int getUserCount(Long startTime) {
		Criteria criteria=adminUserDao.createCriteria();
		criteria.add(Restrictions.eq("roleId", new Integer(2)));
		criteria.add(Restrictions.ge("createdTime", startTime));
		int totalCount = ((Long)criteria.setProjection(
	            Projections.rowCount()).uniqueResult()).intValue();
		return totalCount;		
	}

}
