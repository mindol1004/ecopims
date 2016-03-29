package dms.nr.nrseabase.biz;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.RecordHeader;


/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [DU]단말자산현황</li>
 * <li>설  명 : <pre>단말자산현황DU</pre></li>
 * <li>작성일 : 2015-07-17 15:54:51</li>
 * <li>작성자 : 이민재 (mindol1004)</li>
 * </ul>
 *
 * @author 이민재 (mindol1004)
 */
public class DNRInveInfoMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DNRInveInfoMgmt(){
		super();
	}

	/**
	 * <pre>단말자산현황조회</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-07-17 15:54:51
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInveInfoLstPaging(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
 		IRecordSet rsReturn = dbSelect("SInveInfoLstPaging", requestData, onlineCtx);
 		// 2.결과값 셋팅
 		responseData.putRecordSet("RS_REQ_IN", rsReturn);
	
	    return responseData;
	}

	/**
	 * <pre>단말자산현황총건수</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-07-17 15:54:51
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	@SuppressWarnings("unchecked")
	public IDataSet dSInveInfoTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출	    
	  	IRecord record = dbSelectSingle("SInveInfoTotCnt", requestData, onlineCtx);
	  	// 2.결과값 셋팅	    
	  	responseData.putFieldMap(record); 
	
	    return responseData;
	}

	/**
	 * <pre>단말자산현황상세조회</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-07-17 15:54:51
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInveInfoDtl(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	 	IRecordSet rsReturn = dbSelect("SInveInfoDtl", requestData, onlineCtx);
	 	// 2.결과값 셋팅
	 	responseData.putRecordSet("RS_REQ_IN_DTL", rsReturn); 
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-07-17 15:54:51
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dIControllSlipInfo(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    IRecordSet rs = requestData.getRecordSet("ERP_SLIP_LIST");
	    
	    Map userPlus = null;
	    for(int i=0; i<rs.getRecordCount(); i++)
	    {
	    	userPlus = rs.getRecordMap(i);
	    	userPlus.put("USER_NO", requestData.getField("USER_NO"));
		    // 1.쿼리문 호출
	 		dbInsert("IControllSlipInfo", userPlus, onlineCtx);
	    }
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-07-17 15:54:51
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUControllSlipInfo(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    	    
	    IRecordSet rs = requestData.getRecordSet("RS_SLIP_DELETE");
	    rs.addHeader(new RecordHeader("USER_NO"));
	    Map userPlus = null;
	    
	    for(int i=0; i<rs.getRecordCount(); i++)
	    {
	    	userPlus = rs.getRecord(i);
	    	userPlus.put("USER_NO",requestData.getField("USER_NO") );
		    
	    	 dbUpdate("UControllSlipInfo", userPlus, onlineCtx);
	    }
	   	    responseData.putRecordSet(rs);
	   	    
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-07-17 15:54:51
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInveInfoLstPagingAll(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
  		IRecordSet rsReturn = dbSelect("SInveInfoLstPagingAll", requestData, onlineCtx);
  		// 2.결과값 셋팅
  		responseData.putRecordSet("RS_REQ_IN", rsReturn);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-07-17 15:54:51
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInveInfoConfirm(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    
	    // 1.쿼리문 호출	    
	    IRecordSet rsReturn = dbSelect("SInveInfoConfirm", requestData, onlineCtx);
	   
	    // 2.결과값 셋팅	 
	    responseData.putRecordSet("RS_CONFIRM", rsReturn);
	    
	    
	
	    return responseData;
	}

    /**
	 *
	 *
	 * @author 문재웅 (jaiwoong)
	 * @since 2015-07-17 15:54:51
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqInveAllExcel(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();

        //1. 쿼리문호출
        IRecordSet rsReturn = dbSelect("SInqInveAllExcel", requestData.getFieldMap(), onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_ALL_EXCEL_LIST", rsReturn);
    
    
        return responseData;
    }
	
}
	
