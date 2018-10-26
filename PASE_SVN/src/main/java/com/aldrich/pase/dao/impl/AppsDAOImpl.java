package com.aldrich.pase.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aldrich.pase.dao.AppsInfoDAO;
import com.aldrich.pase.dao.ExceptionDAO;
import com.aldrich.pase.dao.ServiceStatusDAO;
import com.aldrich.pase.entity.APSSocialMedialLink;
import com.aldrich.pase.entity.ExceptionDetails;
import com.aldrich.pase.service.PaseGenericService;
import com.aldrich.pase.util.PASEConstants;
import com.aldrich.pase.vo.ExceptionVO;

public class AppsDAOImpl implements AppsInfoDAO {

	@Autowired
	private ExceptionDAO exceptionDAO;

	@Autowired
	private ServiceStatusDAO serviceStatusDAO;

	@Autowired
	private PaseGenericService paseGenericService;

	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings({ "boxing", "nls" })
	@Override
	public int saveGoogleApps(APSSocialMedialLink objSML, ExceptionDetails objDetails) {
		Integer ID = 0;
		try {
			ID = (Integer) this.sessionFactory.openSession().save(objSML);
			System.out.println(ID);
			if (objDetails.getFkCompanyId() != 0L) {
				this.exceptionDAO.appsDeleteSucessfullEntries(objDetails, "appsDAO_saveGoogleApps");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ID;
	}

	@SuppressWarnings({ "nls", "boxing" })
	@Override
	public int updateGoogleApps(APSSocialMedialLink objSML, ExceptionDetails objDetails) {
		int rowCount = 0;
		try {
			Query query = this.sessionFactory.openSession().createQuery(
					"update APSSocialMedialLink model set model.exceptionCode = ? , model.exceptionCodes = ? , model.relevant = ? , model.fkLinkTypeInfoId = ? , model.uniqueName = ? , model.fkSourceId =?, model.activityDateTime=? where model.uniqueId = ? and model.fkCompanyId =?");
			query.setParameter(0, objSML.getExceptionCode());
			query.setParameter(1, objSML.getExceptionCodes());
			query.setParameter(2, objSML.getRelevant());
			query.setParameter(3, objSML.getFkLinkTypeInfoId());
			query.setParameter(4, objSML.getUniqueName());
			query.setParameter(5, objSML.getFkSourceId());
			query.setParameter(6, new Date());
			query.setParameter(7, objSML.getUniqueId());
			query.setParameter(8, objSML.getFkCompanyId());
			rowCount = query.executeUpdate();
			if (objDetails.getFkCompanyId() != 0L) {
				this.exceptionDAO.appsDeleteSucessfullEntries(objDetails, "appsDAO_updateGoogleApps");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowCount;
	}

	@Override
	@SuppressWarnings({ "unchecked", "nls" })
	public List<APSSocialMedialLink> CheckForExistance(Long companyID, Long linkTypeInfoID) {
		List<APSSocialMedialLink> smlList = null;
		try {
			Query query = this.sessionFactory.openSession()
					.createQuery("select model from APSSocialMedialLink model where model.fkCompanyId = :type");
			query.setParameter("type", companyID);
			query.setParameter("fkLinkTypeInfoId", linkTypeInfoID);
			smlList = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smlList;
	}

	@Override
	@SuppressWarnings({ "unchecked", "nls" })
	public List<APSSocialMedialLink> ITunesCheckForExistance(APSSocialMedialLink SML) {
		List<APSSocialMedialLink> smlList = null;
		try {
			Query query = this.sessionFactory.openSession().createQuery(
					"select model from APSSocialMedialLink model where model.fkCompanyId = :type and model.fkLinkTypeInfoId = :linkTypeId");
			query.setParameter("type", SML.getFkCompanyId());
			query.setParameter("linkTypeId", SML.getFkLinkTypeInfoId());
			smlList = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smlList;
	}

	@Override
	@SuppressWarnings({ "unchecked", "nls", "boxing" })
	public List<APSSocialMedialLink> getSocialMediaData() {
		List<APSSocialMedialLink> smlList = null;
		try {
			Query query = this.sessionFactory.openSession().createQuery(
					"select model from APSSocialMedialLink model where model.exceptionCodes is NULL and model.fkLinkTypeInfoId =:linkTypeId");
			query.setParameter("linkTypeId", PASEConstants.LINK_TYPE_GOOGLE_PLAY_STORE.intValue());
			smlList = query.list();
		} catch (Exception e) {
			long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			this.paseGenericService.saveException(new ExceptionVO(0L, PASEConstants.LINK_TYPE_GOOGLE_PLAY_STORE,
					"getSocialMediaData", lineNumber, e.getMessage(), ""));
		}
		return smlList;
	}

	@Override
	@SuppressWarnings({ "unchecked", "nls", "boxing" })
	public List<APSSocialMedialLink> getSocialMediaData(Long companyId) {
		List<APSSocialMedialLink> smlList = null;
		try {
			Query query = this.sessionFactory.openSession().createQuery(
					"select model from APSSocialMedialLink model where model.exceptionCodes is NULL and model.fkLinkTypeInfoId =:linkTypeId and model.fkCompanyId =:companyID");
			query.setParameter("linkTypeId", PASEConstants.LINK_TYPE_GOOGLE_PLAY_STORE.intValue());
			query.setParameter("companyID", companyId);
			smlList = query.list();
		} catch (Exception e) {
			long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			this.paseGenericService.saveException(new ExceptionVO(0L, PASEConstants.LINK_TYPE_GOOGLE_PLAY_STORE,
					"getSocialMediaData_arguments", lineNumber, e.getMessage(), ""));
		}
		return smlList;
	}

	@Override
	@SuppressWarnings({ "unchecked", "nls", "boxing" })
	public List<APSSocialMedialLink> getITunesSocialMediaData() {
		List<APSSocialMedialLink> smlList = null;
		try {
			Query query = this.sessionFactory.openSession().createQuery(
					"select model from APSSocialMedialLink model where model.exceptionCodes is NULL and model.fkLinkTypeInfoId =:linkTypeId");
			query.setParameter("linkTypeId", PASEConstants.LINK_TYPE_FB_APPS_PLAY_STORE.intValue());
			smlList = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			this.paseGenericService.saveException(new ExceptionVO(0L, PASEConstants.LINK_TYPE_FB_APPS_PLAY_STORE,
					"getITunesSocialMediaData", lineNumber, e.getMessage(), ""));
		}
		return smlList;
	}

	@Override
	@SuppressWarnings({ "unchecked", "nls", "boxing" })
	public List<APSSocialMedialLink> getITunesSocialMediaData(Long companyId) {
		List<APSSocialMedialLink> smlList = null;
		try {
			Query query = this.sessionFactory.openSession().createQuery(
					"select model from APSSocialMedialLink model where model.exceptionCodes is NULL and model.fkLinkTypeInfoId =:linkTypeId");
			query.setParameter("linkTypeId", PASEConstants.LINK_TYPE_FB_APPS_PLAY_STORE.intValue());
			query.setParameter("companyID", companyId);
			smlList = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			long lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
			this.paseGenericService.saveException(new ExceptionVO(0L, PASEConstants.LINK_TYPE_FB_APPS_PLAY_STORE,
					"getITunesSocialMediaData_arguments", lineNumber, e.getMessage(), ""));
		}
		return smlList;
	}
}
