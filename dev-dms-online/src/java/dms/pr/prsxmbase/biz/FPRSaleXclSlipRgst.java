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
 * <li>단위업무명: [FU]매출정산전표등록</li>
 * <li>설  명 : <pre>[FU]매출정산전표등록</pre></li>
 * <li>작성일 : 2015-10-01 10:19:32</li>
 * <li>작성자 : 김상오 (myneti99)</li>
 * </ul>
 *
 * @author 김상오 (myneti99)
 */
public class FPRSaleXclSlipRgst extends fwk.base.FunctionUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public FPRSaleXclSlipRgst(){
		super();
	}

	/**
	 * <pre>[FM]매출정산전표등록리스트</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-10-01 10:20:46
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqSaleXclSlipList(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        try {
            // 1. DU lookup
        	DPRSaleXclSlipRgst dPRSaleXclSlipRgst = (DPRSaleXclSlipRgst) lookupDataUnit(DPRSaleXclSlipRgst.class);
            // 2. 업무 DM호출
            responseData = dPRSaleXclSlipRgst.dInqSaleXclSlipList(requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        }
    
	    
	    return responseData;
	}

	/**
	 * <pre>[FM]매출정산전표등록엑셀리스트</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-10-05 09:43:44
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqSaleXclSlipExcelList(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    try {
            // 1. DU lookup
	    	DPRSaleXclSlipRgst dPRSaleXclSlipRgst = (DPRSaleXclSlipRgst) lookupDataUnit(DPRSaleXclSlipRgst.class);
            // 2. 업무 DM호출
            responseData = dPRSaleXclSlipRgst.dInqSaleXclSlipExcelList(requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        }
	
	    return responseData;
	}
  
}
