package dms.nr.nrsisbase.biz;

import java.util.Map;

//import org.apache.commons.logging.Log;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.IResultMessage;
import nexcore.framework.core.data.ResultMessage;
import nexcore.framework.core.exception.BizRuntimeException;
//import nexcore.framework.core.log.LogManager;


/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [DU]보증보험료정산관리</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-10-13 16:43:49</li>
 * <li>작성자 : 장미진 (kuramotojin)</li>
 * </ul>
 *
 * @author 장미진 (kuramotojin)
 */
public class DNRInsuFeeMgmt extends fwk.base.DataUnit {
	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DNRInsuFeeMgmt(){
		super();
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-10-13 16:43:49
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInsuScrbFeeLstDtl(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	    IRecordSet rsReturn = dbSelect("SInsuScrbFeeLstDtl", requestData, onlineCtx);
	    // 2.결과값 셋팅

	    //Log logger = LogManager.getFwkLog();
	    //log.debug("******************************************************************************************************");
	    //log.debug(rsReturn);
	    responseData.putRecordSet("RS_INSU_FEE_LIST", rsReturn);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-10-13 16:43:49
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInsuScrbFeeLstSlip(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	    IRecordSet rsReturn = dbSelect("SInsuScrbFeeLstSlip", requestData, onlineCtx);
	    // 2.결과값 셋팅
	    responseData.putRecordSet("RS_SLIP_LIST", rsReturn);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-10-13 16:43:49
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInsuScrbFeeSlipLstTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	  	IRecordSet rsReturn = dbSelect("SInsuScrbFeeSlipLstTotCnt", requestData, onlineCtx);
	    //IRecordSet rsReturn = dbSelect("SInsuScrbFeeSlipLstTotCnt121212", requestData, onlineCtx);
	  	// 2.결과값 셋팅
	  	responseData.putRecordSet("RS_SUM_LIST", rsReturn); 	
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-10-13 16:43:49
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInsuScrbFeeAllExcel(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	  	IRecordSet rsReturn = dbSelect("SInsuScrbFeeAllExcel", requestData, onlineCtx);
	  	// 2.결과값 셋팅
	  	responseData.putRecordSet("RS_ALL_EXCEL_LIST", rsReturn); 	
	
	    return responseData;
	}

    /**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2015-10-19 14:11:28
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInsuTermFeeLstDtl(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 처리 결과값을 responseData에 넣어서 리턴하십시요 
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SInsuTermFeeLstDtl", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_INSU_FEE_LIST", rsReturn);
        
        return responseData;
    }

    /**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2015-10-19 14:12:20
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInsuTermFeeAllExcel(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 처리 결과값을 responseData에 넣어서 리턴하십시요 
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SInsuTermFeeAllExcel", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_ALL_EXCEL_LIST", rsReturn);
        
        return responseData;
    }

    /**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2015-10-19 14:13:16
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInsuTermFeeLstSlip(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 처리 결과값을 responseData에 넣어서 리턴하십시요 
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SInsuTermFeeLstSlip", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_SLIP_LIST", rsReturn);
        
        return responseData;
    }

    /**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2015-10-13 16:43:49
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInsuTermFeeSlipLstTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 처리 결과값을 responseData에 넣어서 리턴하십시요 
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SInsuTermFeeSlipLstTotCnt", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_SUM_LIST", rsReturn);
        
        return responseData;
    }

    /**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2015-10-22 16:28:07
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */             
	public IDataSet dSInsuScrbFeeSlipDtlLstTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        IRecordSet rsReturn = dbSelect("SInsuScrbFeeLstDtlTotCnt", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_SUM_DTL_LIST", rsReturn);
    
        return responseData;
    }

    /**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2015-10-13 16:43:49
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInsuTermFeeDtlLstTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        IRecordSet rsReturn = dbSelect("SInsuTermFeeDtlLstTotCnt", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_SUM_DTL_LIST", rsReturn);
    
        return responseData;
    }
  
}
