package dms.nr.nrsxmbase.biz;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;


/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [DU]대리점판매수수료정산관리</li>
 * <li>설  명 : <pre>대리점판매수수료정산관리</pre></li>
 * <li>작성일 : 2015-08-05 10:43:34</li>
 * <li>작성자 : 정동현 (jjddhh123)</li>
 * </ul>
 *
 * @author 정동현 (jjddhh123)
 */
public class DNRAgnSellComMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DNRAgnSellComMgmt(){
		super();
	}

	/**
	 * <pre>대리점판매수수료정산정보조회</pre>
	 *
	 * @author 정동현 (jjddhh123)
	 * @since 2015-08-05 10:43:34
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSAgnSellComLstPaging(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
		
	    // 1.쿼리문 호출
 		IRecordSet rsReturn = dbSelect("SAgnSellComLstPaging", requestData, onlineCtx);
 		// 2.결과값 셋팅
 		responseData.putRecordSet("RS_SLIP_LIST", rsReturn);
 		
 		return responseData;
	}

	/**
	 * <pre>대리점판매수수료정산정보조회 총 건수 조회</pre>
	 *
	 * @author 정동현 (jjddhh123)
	 * @since 2015-08-05 10:43:34
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSAgnSellComLstTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
		
	    // 1.쿼리문 호출	    
  		IRecord record = dbSelectSingle("SAgnSellComLstTotCnt", requestData, onlineCtx);
  		// 2.결과값 셋팅	    
  		responseData.putFieldMap(record); 
	
	    return responseData;
	}

	/**
	 * <pre>대리점판매수수료정산정보상세 조회</pre>
	 *
	 * @author 정동현 (jjddhh123)
	 * @since 2015-08-05 10:43:34
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSAgnSellComDtl(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
		
	    // 1.쿼리문 호출
 		IRecordSet rsReturn = dbSelect("SAgnSellComDtl", requestData, onlineCtx);
 		// 2.결과값 셋팅
 		responseData.putRecordSet("RS_AGN_SELL_COM_DTL", rsReturn);
 		
 		return responseData;
	}

	/**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2015-08-05 10:43:34
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSAgnSellAllExcel(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
  
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SAgnSellAllExcel", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_AGN_SELL_ALL_EXCEL", rsReturn);
        return responseData;
    }

	/**
	 *
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-10-06 13:25:04
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSAgnSellComTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	  	IRecordSet rsReturn = dbSelect("SAgnSellComTotCnt", requestData, onlineCtx);
	  	// 2.결과값 셋팅
	  	responseData.putRecordSet("RS_SUM_LIST", rsReturn);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-10-21 10:24:11
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSAgnSellComDtlSumTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	  	IRecordSet rsReturn = dbSelect("SAgnSellComDtlSumTotCnt", requestData, onlineCtx);
	  	// 2.결과값 셋팅
	  	responseData.putRecordSet("RS_SUM_LIST", rsReturn);
	
	    return responseData;
	}
  
}
