package com.jtc.credit.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.jtc.credit.dao.base.BaseDao;
import com.jtc.credit.dto.TapiPrice;

@Repository
public class ApiPriceDao extends BaseDao<TapiPrice> {
	public void activate(Integer[] ids){
		for(int i=0;i<ids.length;i++){
			Query   query= currentSession().createQuery("update TapiPrice set status=? where id= ?");
			query.setParameter(1, ids[i]);
			query.setParameter(0, true);
			query.executeUpdate();
			//currentSession().flush();   
		}
		
	}
	public void deactivate(Integer[] ids){
		for(int i=0;i<ids.length;i++){
			Query   query= currentSession().createQuery("update TapiPrice set status=? where id= ?");
			query.setParameter(1, ids[i]);
			query.setParameter(0, false);
			query.executeUpdate();
			//currentSession().flush();   
		}
	}			
}
