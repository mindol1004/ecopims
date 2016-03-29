package dms.nr.nrseabase.biz;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;


/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [DU]고정자산감가상각</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-08-03 09:48:37</li>
 * <li>작성자 : 장미진 (kuramotojin)</li>
 * </ul>
 *
 * @author 장미진 (kuramotojin)
 */
public class DNRDeprMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DNRDeprMgmt(){
		super();
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-08-03 09:48:37
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSDeprDtlLstPaging(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	  	IRecordSet rsReturn = dbSelect("SDeprDtlLstPaging", requestData, onlineCtx);
	  	// 2.결과값 셋팅
	  	responseData.putRecordSet("RS_DEPR_LIST", rsReturn);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-08-03 09:50:32
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSDeprTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	  	IRecordSet rsReturn = dbSelect("SDeprTotCnt", requestData, onlineCtx);
	  	// 2.결과값 셋팅
	  	responseData.putRecordSet("RS_SUM_LIST", rsReturn);  
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-11-02 16:03:55
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dInqDeprAllExcel(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    //1. 쿼리문호출
        IRecordSet rsReturn = dbSelect("SDeprAllExcel", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_ALL_EXCEL_LIST", rsReturn);
	
	    return responseData;
	}

    /**
	 *
	 *
	 * @author 진병수 (greatjin)
	 * @since 2016-01-18 14:36:54
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSCalDepr(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        //1. 쿼리문호출
        IRecordSet rsReturn = dbSelect("SCalDepr", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_LIST", rsReturn);
        return responseData;
    }

	/**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2016-01-29 14:36:37
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSDeprSlipLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
        //1. 쿼리문호출
        IRecordSet rsReturn = dbSelect("SDeprSlipLst", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_SLIP_LIST", rsReturn);	
        
	    return responseData;
	}
  
}
