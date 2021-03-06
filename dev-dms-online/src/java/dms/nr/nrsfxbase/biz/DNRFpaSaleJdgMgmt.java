package dms.nr.nrsfxbase.biz;

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
 * <li>단위업무명: [DU]FPA판매감정관리</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2016-01-26 11:23:30</li>
 * <li>작성자 : 안진갑 (bella21cjk)</li>
 * </ul>
 *
 * @author 안진갑 (bella21cjk)
 */
public class DNRFpaSaleJdgMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DNRFpaSaleJdgMgmt(){
		super();
	}

	/**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2016-01-26 11:29:25
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSSaleJdgDtlLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SSaleJdgDtlLst", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_SALE_GRADE", rsReturn); 
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2016-01-26 11:23:30
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSSaleJdgLstPaging(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SSaleJdgLstPaging", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_JDG_LST", rsReturn); 
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2016-01-26 11:23:30
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dISaveSaleJdgDtl(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        dbInsert("ISaveSaleJdgDtl", requestData.getFieldMap(), onlineCtx);  //판매감정테이블등록
        dbInsert("IEqpUsedDspsl", requestData.getFieldMap(), onlineCtx);    //이관대상테이블등록
    
        return responseData;
    }

    /**
	 *
	 *
	 * @author 진병수 (greatjin)
	 * @since 2016-02-15 16:20:10
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dISaveEqpBkagAmt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        dbInsert("ISaveEqpBkagAmt", requestData.getFieldMap(), onlineCtx);  //판매감정테이블등록
    
        return responseData;
    }

	/**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2016-01-26 11:23:30
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSIFPAEqpInfo01(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    IRecord rsReturn = dbSelectSingle("SIFPAEqpInfo01", requestData, onlineCtx);
	    
	    responseData.putFieldMap(rsReturn);
	    
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2016-02-26 14:03:13
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSSaleJdgTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출	    
 		IRecord record = dbSelectSingle("SSaleJdgTotCnt", requestData, onlineCtx);
 		// 2.결과값 셋팅	    
 		responseData.putFieldMap(record); 
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2016-03-02 10:47:23
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSFpaMORExcel(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
        // 1.쿼리문 호출     
        IRecordSet rsReturn = dbSelect("SFpaMORExcel", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putRecordSet("RS_FPA_EXCEL", rsReturn);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2016-03-02 10:48:00
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSFpaMASRExcel(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
        // 1.쿼리문 호출     
        IRecordSet rsReturn = dbSelect("SFpaMASRExcel", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putRecordSet("RS_FPA_EXCEL", rsReturn);
    
        return responseData;
	}

	/**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2016-03-02 10:48:28
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSFpaSRExcel(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
        // 1.쿼리문 호출     
        IRecordSet rsReturn = dbSelect("SFpaSRExcel", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putRecordSet("RS_FPA_EXCEL", rsReturn);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2016-03-02 10:49:05
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSFpaWARRExcel(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
        // 1.쿼리문 호출     
        IRecordSet rsReturn = dbSelect("SFpaWARRExcel", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putRecordSet("RS_FPA_EXCEL", rsReturn);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2016-03-02 16:03:12
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUEqpAssetSt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    dbInsert("UEqpAssetSt", requestData.getFieldMap(), onlineCtx);  //재고상테업데이트(판매감정완료)
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2016-03-15 13:11:48
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSEqpRemPrcCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    IRecord rsReturn = dbSelectSingle("SEqpRemPrcCnt", requestData, onlineCtx);
	    
	    responseData.putFieldMap(rsReturn); 
	
	    return responseData;
	}
  
}
