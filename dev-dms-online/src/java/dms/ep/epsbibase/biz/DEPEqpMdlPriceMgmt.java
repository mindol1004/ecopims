package dms.ep.epsbibase.biz;

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
 * <li>업무 그룹명 : dms/에코폰</li>
 * <li>단위업무명: [DU]단말기가격관리</li>
 * <li>설  명 : <pre>단말기가격관리</pre></li>
 * <li>작성일 : 2015-08-20 14:27:24</li>
 * <li>작성자 : 박민정 (dangnagu2)</li>
 * </ul>
 *
 * @author 박민정 (dangnagu2)
 */
public class DEPEqpMdlPriceMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DEPEqpMdlPriceMgmt(){
		super();
	}

	/**
	 * <pre>단말기별단가조회총건수</pre>
	 *
	 * @author 박민정 (dangnagu2)
	 * @since 2015-08-20 14:27:53
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSfInqEqpMdlPriceLstTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
        // 1.쿼리문 호출     
 		IRecord record = dbSelectSingle("SfInqEqpMdlPriceLstTotCnt", requestData, onlineCtx);
 		
 		// 2.결과값 셋팅     
 		responseData.putFieldMap(record); 
 	
	
	    return responseData;
	}

	/**
	 * <pre>단말기별단가조회목록</pre>
	 *
	 * @author 박민정 (dangnagu2)
	 * @since 2015-08-20 14:28:12
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSfInqEqpMdlPriceLstPaging(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
		IRecordSet rsReturn = dbSelect("SfInqEqpMdlPriceLstPaging", requestData, onlineCtx);
		
		// 2.결과값 셋팅
		responseData.putRecordSet("RS_EQP_PRICE_LIST", rsReturn);
	
	    return responseData;
	}

	/**
	 * <pre>구성품단가조회</pre>
	 *
	 * @author 박민정 (dangnagu2)
	 * @since 2015-08-21 10:42:41
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSfInqCmptPriceLst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        
        try {       
            // 1.쿼리문 호출
            IRecordSet rsReturn = dbSelect("SfInqCmptPriceLst", requestData, onlineCtx);
            // 2.결과값 셋팅
            responseData.putRecordSet("RS_CMPT_PRICE_LIST", rsReturn);
        }
        catch ( BizRuntimeException e ) {
            throw e;
        } 
    
        return responseData;
	}
  
}
