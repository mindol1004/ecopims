package dms.pr.prsimbase.biz;

import java.util.Map;

import fwk.utils.PagenateUtils;
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
 * <li>단위업무명: [FU]임대폰감정등록</li>
 * <li>설  명 : <pre>[FU]임대폰감정등록</pre></li>
 * <li>작성일 : 2015-07-21 16:44:58</li>
 * <li>작성자 : 이영진 (newnofixing)</li>
 * </ul>
 *
 * @author 이영진 (newnofixing)
 */
public class FPRReqpJdgRgst extends fwk.base.FunctionUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public FPRReqpJdgRgst(){
		super();
	}

    /**
	 * <pre>[FM]임대폰감정등록조회</pre>
	 *
	 * @author 이영진 (newnofixing)
	 * @since 2015-07-23 12:47:03
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
    public IDataSet fInqReqpJdgRgstLst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet dsCnt = new DataSet();
        IRecordSet rsList = null;
        int intTotalCnt = 0;
        try {
            // 1. DU lookup
            DPRReqpJdgRgst dPRReqpJdgRgst = (DPRReqpJdgRgst) lookupDataUnit(DPRReqpJdgRgst.class);
            // 2. LISTDM호출
            responseData = dPRReqpJdgRgst.dSReqpJdgRgstLst(requestData, onlineCtx);
    
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }       
        return responseData;
    }

    /**
	 * <pre>[FM]임대폰감정등록</pre>
	 *
	 * @author 이영진 (newnofixing)
	 * @since 2015-07-23 16:21:42
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fRegReqpJdg(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        
        try {
            // 1. DU lookup
            DPRReqpJdgRgst dPRReqpJdgRgst = (DPRReqpJdgRgst) lookupDataUnit(DPRReqpJdgRgst.class);
            
            responseData = dPRReqpJdgRgst.dUReqpJdg(requestData, onlineCtx);

        } catch ( BizRuntimeException e ) {
            throw e;
        } 
    
        return responseData;
    }

    /**
	 * <pre>[FM]임대폰감정상세등록</pre>
	 *
	 * @author 이영진 (newnofixing)
	 * @since 2015-07-23 12:47:03
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
    public IDataSet fRegReqpJdgDtl(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        
        try {
            // 1. DU lookup
            DPRReqpJdgRgst dPRReqpJdgRgst = (DPRReqpJdgRgst) lookupDataUnit(DPRReqpJdgRgst.class);
            
            responseData = dPRReqpJdgRgst.dUReqpJdgDtl(requestData, onlineCtx);       
    
        } catch ( BizRuntimeException e ) {
            throw e;
        } 
    
        return responseData;
    }
  
}
