package dms.pr.prbimbase.biz;

import java.util.Map;

import fwk.common.CommonArea;
import fwk.constants.DmsConstants;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.IResultMessage;
import nexcore.framework.core.data.ResultMessage;
import nexcore.framework.core.exception.BizRuntimeException;
import nexcore.framework.core.util.StringUtils;


/**
 * <ul>
 * <li>업무 그룹명 : dms/임대R</li>
 * <li>단위업무명: [PU]구성품재고조회</li>
 * <li>설  명 : <pre>[PU]구성품재고조회</pre></li>
 * <li>작성일 : 2015-07-14 20:45:43</li>
 * <li>작성자 : 이준우 (elmsliw)</li>
 * </ul>
 *
 * @author 이준우 (elmsliw)
 */
public class PPRConstInveMgmt extends fwk.base.ProcessUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public PPRConstInveMgmt(){
		super();
	}

	/**
	 * <pre>[PM]구성품재고조회</pre>
	 *
	 * @author 이준우 (elmsliw)
	 * @since 2015-09-14 16:37:51
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : ST_DT [시작일]
	 *	- field : ED_DT [종료일]
	 *	- field : EQP_MDL_CD [모델코드]
	 *	- field : EQP_MDL_NM [모델명]
	 *	- field : CMPT_CD [구성품코드]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_CIM_LIST
	 *		- field : CMPT_IN_DT [입출고날짜]
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : EQP_MDL_NM [단말기모델명]
	 *		- field : CMPT_CD [구성품코드]
	 *		- field : IN_QTY [입고재고]
	 *		- field : OUT_QTY [출고재고]
	 *		- field : TOT__QTY [총재고]
	 * </pre>
	 */
    public IDataSet pInqConstInveMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
    	try {
    		responseData = callSharedBizComponentByDirect("pr.PRSIMBase", "fInqConstInveLst", requestData, onlineCtx);
    	} catch ( BizRuntimeException e ) {
    		throw e;
    	} catch ( Exception e ) {
    		throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
    	}		
        return responseData;
    }

}
