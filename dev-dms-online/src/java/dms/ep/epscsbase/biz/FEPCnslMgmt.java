package dms.ep.epscsbase.biz;

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
 * <li>단위업무명: [FU]일반감정상담관리</li>
 * <li>설  명 : <pre>[FU]일반감정상담관리</pre></li>
 * <li>작성일 : 2015-08-31 10:59:42</li>
 * <li>작성자 : 김상오 (myneti99)</li>
 * </ul>
 *
 * @author 김상오 (myneti99)
 */
public class FEPCnslMgmt extends fwk.base.FunctionUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public FEPCnslMgmt(){
		super();
	}

	/**
	 * <pre>[FM]상담대상목록조회</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-08-31 11:00:17
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqCnslObjList(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        try {
            // 1. DU lookup
        	DEPCnslMgmt du = (DEPCnslMgmt) lookupDataUnit(DEPCnslMgmt.class);
            // 2. 업무 DM호출
            responseData = du.dInqCnslObjList(requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        }
	    
	    return responseData; 


	}

	/**
	 * <pre>[FM]상담등록내역조회</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-08-31 10:59:42
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqPrchsCustRmksNo(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
        try {
            // 1. DU lookup
        	DEPCnslMgmt du = (DEPCnslMgmt) lookupDataUnit(DEPCnslMgmt.class);
            // 2. 업무 DM호출
            responseData = du.dInqPrchsCustRmksNo(requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        }
	    
	    return responseData; 
	}

	/**
	 * <pre>[FM]상담등록</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-09-07 18:50:06
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fRegEqpCnsl(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
        try {
            // 1. DU lookup
        	DEPCnslMgmt du = (DEPCnslMgmt) lookupDataUnit(DEPCnslMgmt.class);
            // 2. 업무 DM호출
            responseData = du.dMRegEqpCnsl(requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        }
	    
	    return responseData; 
	}
  
}
