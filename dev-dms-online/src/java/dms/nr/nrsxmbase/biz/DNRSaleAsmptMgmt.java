package dms.nr.nrsxmbase.biz;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;


/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [DU]매출추정정보조회</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-08-03 10:22:06</li>
 * <li>작성자 : 김혁범 (kezmain1)</li>
 * </ul>
 *
 * @author 김혁범 (kezmain1)
 */
public class DNRSaleAsmptMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DNRSaleAsmptMgmt(){
		super();
	}

	/**
	 *매출추정정보총건수
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-08-03 10:22:06
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSsaleAsmptTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출	    
  		IRecord record = dbSelectSingle("SsaleAsmptTotCnt", requestData, onlineCtx);
  		// 2.결과값 셋팅	    
  		responseData.putFieldMap(record); 
	
	    return responseData;
	}

	/**
	 *매출추정정보조회
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-08-03 10:22:06
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSsaleAsmptLstPaging(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
 		IRecordSet rsReturn = dbSelect("SsaleAsmptLstPaging", requestData, onlineCtx);
 		// 2.결과값 셋팅
 		responseData.putRecordSet("RS_SALE_ASMPT_LIST", rsReturn);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-08-03 10:22:06
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSsaleAsmptLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
 		IRecordSet rsReturn = dbSelect("SsaleAsmptLst", requestData, onlineCtx);
 		responseData.putRecordSet("RS_SALE_SUM", rsReturn);
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-08-03 10:22:06
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSsaleAsmptDtlLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
  		IRecordSet rsReturn = dbSelect("SsaleAsmptDtlLst", requestData, onlineCtx);
  		// 2.결과값 셋팅
  		responseData.putRecordSet("RS_SALE_ASMPT_DTL_LIST", rsReturn);
	
	    return responseData;
	}

	/**
	 *상세 기준일자리스트 조회
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-08-05 11:11:37
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSsaleYyAsmptDtlLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	  	IRecordSet rsReturn = dbSelect("SsaleAsmptYyLst", requestData, onlineCtx);
	  	responseData.putRecordSet("RS_SALE_SUM", rsReturn);
	  	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-09-15 16:28:24
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSsaleAsmptPriLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	  	IRecordSet rsReturn = dbSelect("SsaleAsmptPriLst", requestData, onlineCtx);
	  	responseData.putRecordSet("RS_SALE_ASMPT_PRI_LIST", rsReturn);
	
	    return responseData;
	}

    /**
	 *
	 *
	 * @author 문재웅 (jaiwoong)
	 * @since 2015-08-03 10:22:06
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSsaleAsmptAllExcel(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();

        //1. 쿼리문호출
        IRecordSet rsReturn = dbSelect("SsaleAsmptAllExcel", requestData.getFieldMap(), onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_ALL_EXCEL_LIST", rsReturn);
    
        return responseData;
    }
  
}
