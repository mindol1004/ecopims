package dms.nr.nrsplbase.biz;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;


/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [DU]단말기정책관리</li>
 * <li>설  명 : <pre>단말기정책관리[DU]</pre></li>
 * <li>작성일 : 2015-07-10 10:23:30</li>
 * <li>작성자 : 장미진 (kuramotojin)</li>
 * </ul>
 *
 * @author 장미진 (kuramotojin)
 */
public class DNREqpPolyMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DNREqpPolyMgmt(){
		super();
	}

	/**
	 * <pre>단말기정책정보조회</pre>
	 *
	 * @author 박홍서 (uni9401)
	 * @since 2015-07-10 11:29:52
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSEqpPolyLstPaging(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
 		IRecordSet rsReturn = dbSelect("SEqpPolyLstPaging", requestData, onlineCtx);
 		// 2.결과값 셋팅
 		responseData.putRecordSet("RS_EQP_POLY_LIST", rsReturn);
	
	    return responseData;
	}

	/**
	 * <pre>단말기정책정보등록</pre>
	 *
	 * @author 박홍서 (uni9401)
	 * @since 2015-07-10 11:29:52
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dIEqpPoly(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
 		dbInsert("IEqpPoly", requestData, onlineCtx);
 		
	    return responseData;
	}

	/**
	 * <pre>단말기정책정보수정</pre>
	 *
	 * @author 박홍서 (uni9401)
	 * @since 2015-07-16 14:15:56
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUEqpPoly(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
 		dbInsert("UEqpPoly", requestData, onlineCtx);
	
	    return responseData;
	}

	/**
	 * <pre>단말기정책정보총건수</pre>
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-07-16 10:32:32
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	@SuppressWarnings("unchecked")
	public IDataSet dSEqpPolyTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출	    
 		IRecord record = dbSelectSingle("SEqpPolyTotCnt", requestData, onlineCtx);
 		// 2.결과값 셋팅	    
 		responseData.putFieldMap(record); 
	
	    return responseData;
	}

	/**
	 * <pre>단말기정책 유효기간종료일을 신규 유효기간시작일의 전일로 자동 업데이트한다.</pre>
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-07-17 11:30:12
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUEqpPolyDate(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    dbUpdate("UEqpPolyDate", requestData, onlineCtx);
	
	    return responseData;
	}

	/**
	 * <pre>신규 입력시 단말기 정책 유효기간의 유효성을 검사한다.</pre>
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-07-16 14:15:56
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	@SuppressWarnings("unchecked")
	public IDataSet dSEqpPolyDateIChk(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출	    
 		IRecord record = dbSelectSingle("SEqpPolyDateIChk", requestData, onlineCtx);
 		// 2.결과값 셋팅	    
 		responseData.putFieldMap(record); 
	
	    return responseData;
	}

	/**
	 * <pre>수정시 단말기정책기간 유효성 검사를 한다.</pre>
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-07-20 14:07:00
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	@SuppressWarnings("unchecked")
	public IDataSet dSEqpPolyDateUChk(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출	    
	    IRecord record = dbSelectSingle("SEqpPolyDateUChk", requestData, onlineCtx);
	  	// 2.결과값 셋팅	    
	  	responseData.putFieldMap(record); 
	
	    return responseData;
	}
  
}
