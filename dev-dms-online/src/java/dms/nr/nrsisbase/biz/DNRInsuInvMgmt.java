package dms.nr.nrsisbase.biz;

import java.util.Map;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.IResultMessage;
import nexcore.framework.core.data.ResultMessage;
import nexcore.framework.core.exception.BizRuntimeException;


/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [DU]보증보험청구정보관리</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-10-08 11:05:46</li>
 * <li>작성자 : 장미진 (kuramotojin)</li>
 * </ul>
 *
 * @author 장미진 (kuramotojin)
 */
public class DNRInsuInvMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DNRInsuInvMgmt(){
		super();
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-10-08 11:05:46
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInsuInvInfoLstPaging(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SInsuInvInfoLstPaging", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_INSU_INV_LIST", rsReturn);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-10-08 11:11:26
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInsuInvInfoTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    
	    // 1.쿼리문 호출
	  	IRecordSet rsReturn = dbSelect("SInsuInvInfoTotCnt", requestData, onlineCtx);
	  	// 2.결과값 셋팅
	  	responseData.putRecordSet("RS_SUM_LIST", rsReturn); 
	
	  	return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-10-12 13:04:02
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInsuInvInfoLstPagingDtl(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	    IRecordSet rsReturn = dbSelect("SInsuInvInfoLstPagingDtl", requestData, onlineCtx);
	    // 2.결과값 셋팅
	    responseData.putRecordSet("RS_INSU_INV_DTL_LIST", rsReturn);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-10-12 13:44:01
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUpdateInsuInvRsv(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    
	    IRecordSet updateInsu = requestData.getRecordSet("RS_RSV_LIST");
	    
	    Map userPlus = null;
	    for(int i=0; i<updateInsu.getRecordCount(); i++)
	    {
	    	userPlus = updateInsu.getRecordMap(i);
	    	userPlus.put("USER_NO", requestData.getField("USER_NO"));
		    // 1.쿼리문 호출
	    	dbUpdate("UpdateInsuInvRsv", userPlus, onlineCtx);
	    	dbInsertAndReturnPK("InsertInsuInvclaimHst", userPlus, onlineCtx);		//history 기록
	    }
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-10-12 17:10:40
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUpdateInsuInvclaim(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    
	    IRecordSet updateInsu = requestData.getRecordSet("RS_INSU_INV_LIST");
	    
	    Map userPlus = null;
	    for(int i=0; i<updateInsu.getRecordCount(); i++)
	    {
	    	userPlus = updateInsu.getRecordMap(i);
	    	userPlus.put("USER_NO", requestData.getField("USER_NO"));
		    // 1.쿼리문 호출
	    	dbUpdate("UpdateInsuInvclaim", userPlus, onlineCtx);		//상태 update
	    	dbInsertAndReturnPK("InsertInsuInvclaimHst", userPlus, onlineCtx);		//history 기록
	    }
	    
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-10-12 17:11:37
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUpdateInsuInvclaimCancle(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    
	    IRecordSet updateInsu = requestData.getRecordSet("RS_INSU_INV_LIST");
	    
	    Map userPlus = null;
	    for(int i=0; i<updateInsu.getRecordCount(); i++)
	    {
	    	userPlus = updateInsu.getRecordMap(i);
	    	userPlus.put("USER_NO", requestData.getField("USER_NO"));
		    // 1.쿼리문 호출
	    	dbUpdate("UpdateInsuInvclaimCancle", userPlus, onlineCtx);
	    	dbInsertAndReturnPK("InsertInsuInvclaimHst", userPlus, onlineCtx);			//history 기록
	    }
	
	    return responseData;
	}

    /**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-12-23 17:43:18
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInsuInvStSync(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SInsuInvStSync", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_RESTATE_LIST", rsReturn);
    
        return responseData;
    }
  
}
