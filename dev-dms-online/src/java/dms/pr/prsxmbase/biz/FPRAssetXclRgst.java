package dms.pr.prsxmbase.biz;

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
 * <li>업무 그룹명 : dms/임대R</li>
 * <li>단위업무명: [FU]재고정산등록</li>
 * <li>설  명 : <pre>[FU]재고정산등록</pre></li>
 * <li>작성일 : 2015-10-06 15:09:58</li>
 * <li>작성자 : 김상오 (myneti99)</li>
 * </ul>
 *
 * @author 김상오 (myneti99)
 */
public class FPRAssetXclRgst extends fwk.base.FunctionUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public FPRAssetXclRgst(){
		super();
	}

	/**
	 * <pre>[FM]재고정산대상조회</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-10-08 11:21:49
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqAssetXclList(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    try {
	        // 1. DU lookup
	    	DPRAssetXclRgst dPRAssetXclRgst = (DPRAssetXclRgst) lookupDataUnit(DPRAssetXclRgst.class);
	        // 2. 업무 DM호출
	        responseData = dPRAssetXclRgst.dSInqAssetXclList(requestData, onlineCtx);
	    } catch ( BizRuntimeException e ) {
	        throw e;
	    }
	    return responseData;
	}

	/**
	 * <pre>[FM]재고정산등록엑셀리스트</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-10-08 11:21:49
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqAssetXclExcelList(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    try {
	        // 1. DU lookup
	    	DPRAssetXclRgst dPRAssetXclRgst = (DPRAssetXclRgst) lookupDataUnit(DPRAssetXclRgst.class);
	        // 2. 업무 DM호출
	        responseData = dPRAssetXclRgst.dSInqAssetXclExcelList(requestData, onlineCtx);
	    } catch ( BizRuntimeException e ) {
	        throw e;
	    }
	    return responseData;
	}

	/**
	 * <pre>[FM]재고정산 전표진행 체크</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-10-08 11:21:49
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqAssetXclSlipCheck(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    try {
            // 1. DU lookup
	    	DPRAssetXclRgst dPRAssetXclRgst = (DPRAssetXclRgst) lookupDataUnit(DPRAssetXclRgst.class);
            // 2. 업무 DM호출
            responseData = dPRAssetXclRgst.dSAssetXclSlipCheck(requestData, onlineCtx);
            
        } catch ( BizRuntimeException e ) {
            throw e;
        }
	
	    return responseData;
	}

	/**
	 * <pre>[FM]재고정산대상건수체크</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-11-04 10:03:39
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqAssetXclCntCheck(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    try {
            // 1. DU lookup
	    	DPRAssetXclRgst dPRAssetXclRgst = (DPRAssetXclRgst) lookupDataUnit(DPRAssetXclRgst.class);
            // 2. 업무 DM호출
            responseData = dPRAssetXclRgst.dSAssetXclCntCheck(requestData, onlineCtx);
            
        } catch ( BizRuntimeException e ) {
            throw e;
        }
	
	    return responseData;
	}
  
}
