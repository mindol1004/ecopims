package dms.pr.prsimbase.biz;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.IResultMessage;
import nexcore.framework.core.data.ResultMessage;
import nexcore.framework.core.exception.BizRuntimeException;
import nexcore.framework.core.log.LogManager;


/**
 * <ul>
 * <li>업무 그룹명 : dms/임대R</li>
 * <li>단위업무명: [DU]회수대상임대폰조회</li>
 * <li>설  명 : <pre>회수대상임대폰조회</pre></li>
 * <li>작성일 : 2015-07-14 20:50:04</li>
 * <li>작성자 : 이준우 (elmsliw)</li>
 * </ul>
 *
 * @author 이준우 (elmsliw)
 */
public class DPRReqpClctInRgst extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DPRReqpClctInRgst(){
		super();
	}

	/**
	 * <pre>회수대상임대폰조회총건수</pre>
	 *
	 * @author 이준우 (elmsliw)
	 * @since 2015-07-16 13:34:42
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- field : TOTAL_CNT [필드1]
	 * </pre>
	 */
	public IDataSet dSReqpClctLstTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    
	    // 1.쿼리문 호출     
		IRecord record = dbSelectSingle("SResqpClctLstTotCnt", requestData, onlineCtx);

		// 2.결과값 셋팅     
		responseData.putFieldMap(record);

	    return responseData;
	}

	/**
	 * <pre>회수대상임대폰조회목록</pre>
	 *
	 * @author 이준우 (elmsliw)
	 * @since 2015-07-14 20:50:04
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSReqpClctLstPaging(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();

	    // 1.쿼리문 호출
 		IRecordSet rsReturn = dbSelect("SResqpClctLstPaging", requestData, onlineCtx);
 		
 		// 2.결과값 셋팅
 		responseData.putRecordSet("RS_EQP_LIST", rsReturn);

	    return responseData;
	}

	/**
	 * <pre>임대폰회수입고상태수정</pre>
	 *
	 * @author 이준우 (elmsliw)
	 * @since 2015-07-17 14:04:59
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUReqpClct(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	 	dbUpdate("UResqpClctTbEqpClctObj", requestData, onlineCtx);
	
	    return responseData;
	}

	/**
	 * <pre>단말기자산정보수정</pre>
	 *
	 * @author 이준우 (elmsliw)
	 * @since 2015-07-17 17:40:24
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUReqpClctTbEqpAsset(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	 	dbUpdate("UResqpClctTbEqpAsset", requestData, onlineCtx);
	
	    return responseData; //자산수정완료
	}

	/**
	 * <pre>단말기입고정보수정</pre>
	 *
	 * @author 이준우 (elmsliw)
	 * @since 2015-07-17 17:42:02
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUReqpClctTbEqpIn(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	 	dbUpdate("UResqpClctTbEqpIn", requestData, onlineCtx);
	
	    return responseData;
	}

	/**
	 * <pre>회수대상임대폰정보입력</pre>
	 *
	 * @author 이준우 (elmsliw)
	 * @since 2015-07-20 13:04:37
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dIReqpClct(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	    dbInsert("IResqpClctTbEqpIn", requestData, onlineCtx);
	
	    return responseData;	//입고 입력완료
	}

	/**
	 * <pre>입고번호채번</pre>
	 *
	 * @author 이준우 (elmsliw)
	 * @since 2015-07-16 13:34:42
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSReqpClctLstSeq(IDataSet requestData, IOnlineContext onlineCtx) {
		
	    IDataSet responseData = new DataSet();
	    IRecord record = dbSelectSingle("SResqpClctLstSeq", requestData, onlineCtx);
	    
		if ( record != null ) {
			responseData.putFieldMap(record);
		}
	    return responseData; //입고채번완료

	}
}
