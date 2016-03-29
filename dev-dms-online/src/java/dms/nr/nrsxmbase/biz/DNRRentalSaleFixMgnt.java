package dms.nr.nrsxmbase.biz;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;


/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [DU]렌탈매출확정정보관리</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-08-07 13:13:57</li>
 * <li>작성자 : 김혁범 (kezmain1)</li>
 * </ul>
 *
 * @author 김혁범 (kezmain1)
 */
public class DNRRentalSaleFixMgnt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DNRRentalSaleFixMgnt(){
		super();
	}

	/**
	 *렌탈매출확정조회
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-08-07 13:13:57
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSRentalSaleLstPaging(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
 		IRecordSet rsReturn = dbSelect("SRentalSaleLstPaging", requestData, onlineCtx);
 		// 2.결과값 셋팅
 		responseData.putRecordSet("RS_RENTAL_SALE_FIX_LIST", rsReturn);
	
	    return responseData;
	}

	/**
	 *렌탈매출확정조회총건수
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-08-07 13:13:57
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSRentalSaleTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출	    
  		IRecord record = dbSelectSingle("SRentalSaleTotCnt", requestData, onlineCtx);
  		// 2.결과값 셋팅	    
  		responseData.putFieldMap(record); 
	
	    return responseData;
	}

	/**
	 *렌탈매출확정상세
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-08-07 14:41:27
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSRentalSaleDtlLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출	    
	    IRecordSet rsReturn = dbSelect("SRentalSaleDtlLst", requestData, onlineCtx);
  		// 2.결과값 셋팅	    
  		
  		responseData.putRecordSet("RS_RENTAL_SALE_FIX_DTL_LIST", rsReturn);
  		
  		
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-08-07 13:13:57
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSrentalSaleAsmptLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출	    
	    IRecordSet rsReturn = dbSelect("SrentalSaleAsmptLst", requestData, onlineCtx);
  		// 2.결과값 셋팅	    
  		
  		responseData.putRecordSet("RS_SALE_SUM", rsReturn);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-08-07 13:13:57
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSrentalSaleAsmptDtlLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출	    
	    IRecordSet rsReturn = dbSelect("SrentalSaleAsmptDtlLst", requestData, onlineCtx);
  		// 2.결과값 셋팅	    
  		
  		responseData.putRecordSet("RS_SALE_SUM", rsReturn);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-09-21 13:17:30
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSRentalSaletPriLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	  	IRecordSet rsReturn = dbSelect("SRentalSaletPriLst", requestData, onlineCtx);
	  	responseData.putRecordSet("RS_SALE_ASMPT_PRI_LIST", rsReturn);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-11-02 13:24:07
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSRentalSaleFixAllExcel(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    //1. 쿼리문호출
        IRecordSet rsReturn = dbSelect("SRentalSaleFixAllExcel", requestData.getFieldMap(), onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_RENTAL_SALE_FIX_ALL_EXCEL_LIST", rsReturn);
	
	    return responseData;
	}
  
}
