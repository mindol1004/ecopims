package dms.nr.nrsisbase.biz;

import java.util.Map;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.RecordHeader;


/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [DU]보증보험청구취소정보관리</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-10-19 16:53:43</li>
 * <li>작성자 : 장미진 (kuramotojin)</li>
 * </ul>
 *
 * @author 장미진 (kuramotojin)
 */
public class DNRInsuInvCnclMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DNRInsuInvCnclMgmt(){
		super();
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-10-19 16:54:45
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInsuInvCnclLstPaging(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SInsuInvCnclLstPaging", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_INSU_INV_CNCL_LIST", rsReturn);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-10-19 16:57:34
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInsuInvCnclTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	  	IRecordSet rsReturn = dbSelect("SInsuInvCnclTotCnt", requestData, onlineCtx);
	  	IRecordSet rsCnt = dbSelect("SInsuInvCnclSum", requestData, onlineCtx);
	  	//SInsuInvCnclSum
	  	
	    IRecord ir = rsReturn.getRecord(0);

        if(rsReturn.getHeader("DMS_INV_CNT") == null){
        	rsReturn.addHeader(new RecordHeader("DMS_INV_CNT"));
	       }
	       ir.put("DMS_INV_CNT", rsCnt.get(0, "DMS_INV_CNT"));
	       	
	    if(rsReturn.getHeader("DMS_INV_AMT") == null){
	    	rsReturn.addHeader(new RecordHeader("DMS_INV_AMT"));
	       }
	       	ir.put("DMS_INV_AMT", rsCnt.get(0,"DMS_INV_AMT"));
	      
	       
	  	// 2.결과값 셋팅
	  	responseData.putRecordSet("RS_SUM_LIST", rsReturn); 
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-10-26 11:22:15
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUpdateIncuInvCnclFix(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    IRecordSet updateInsu = requestData.getRecordSet("RS_INSU_INV_LIST");
	    
	    Map userPlus = null;
	    for(int i=0; i<updateInsu.getRecordCount(); i++)
	    {
	    	userPlus = updateInsu.getRecordMap(i);
	    	userPlus.put("USER_NO", requestData.getField("USER_NO"));
		    // 1.쿼리문 호출
	    	dbUpdate("UpdateIncuInvCnclFix", userPlus, onlineCtx);		//상태 update
	    	dbInsertAndReturnPK("InsertInsuInvCnclHst", userPlus, onlineCtx);
	    }
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-10-26 11:22:40
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUpdateIncuInvCnclNotYet(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	                                                      
	    IRecordSet updateInsu = requestData.getRecordSet("RS_INSU_INV_LIST");
	    
	    Map userPlus = null;
	    for(int i=0; i<updateInsu.getRecordCount(); i++)
	    {
	    	userPlus = updateInsu.getRecordMap(i);
	    	userPlus.put("USER_NO", requestData.getField("USER_NO"));
	    	
		    // 1.쿼리문 호출
	    	dbUpdate("UpdateIncuInvCnclNotYet", userPlus, onlineCtx);		//상태 update
	    	dbInsertAndReturnPK("InsertInsuInvCnclHst", userPlus, onlineCtx);
	    }

	    return responseData;
	}
  
}