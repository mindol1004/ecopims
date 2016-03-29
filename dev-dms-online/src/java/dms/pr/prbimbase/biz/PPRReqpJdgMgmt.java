package dms.pr.prbimbase.biz;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.exception.BizRuntimeException;
import fwk.common.CommonArea;


/**
 * <ul>
 * <li>업무 그룹명 : dms/임대R</li>
 * <li>단위업무명: [PU]임대폰감정관리</li>
 * <li>설  명 : <pre>[PU]임대폰감정관리</pre></li>
 * <li>작성일 : 2015-07-21 16:26:56</li>
 * <li>작성자 : 이영진 (newnofixing)</li>
 * </ul>
 *
 * @author 이영진 (newnofixing)
 */
public class PPRReqpJdgMgmt extends fwk.base.ProcessUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public PPRReqpJdgMgmt(){
		super();
	}

    /**
	 * <pre>[PM]임대폰감정목록조회</pre>
	 *
	 * @author 이영진 (newnofixing)
	 * @since 2015-07-21 16:26:56
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : IN_FR_DT [입고시작일]
	 *	- field : IN_TO_DT [입고종료일]
	 *	- field : EQP_SER_NO [단말기일련번호]
	 *	- field : EQP_MDL_CD [단말기모델코드]
	 *	- field : EQP_MDL_NM [단말기모델명]
	 *	- field : BOX_NO [박스번호]
	 *	- field : EQP_JDG_RSLT_CD [단말기감정결과코드]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_REQP_JDG_LIST
	 *		- field : IN_DT [입고일]
	 *		- field : ASSET_NO [자산번호]
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : EQP_MDL_NM [단말기모델명]
	 *		- field : EQP_COLOR_CD [단말기칼라코드]
	 *		- field : EQP_SER_NO [단말기일련번호]
	 *		- field : EQP_IMEI_NO [단말기IMEI번호]
	 *		- field : BOX_NO [필드1]
	 *		- field : PRCH_AMT [매입가]
	 *		- field : EQP_JDG_DT [단말기감정일]
	 *		- field : EQP_JDG_RSLT_CD [단말기감정결과코드]
	 *		- field : JDG_APRV_YN [감정승인여부]
	 *		- field : JDG_CHRGR_NO [감정담당자번호]
	 *		- field : JDG_CHRGR_NM [감정담당자명]
	 *		- field : IN_DEALCO_CD [매입처]
	 *		- field : DEALCO_NM [매입처명]
	 *		- field : CMPHS_CNTRT_NM [계약명]
	 *		- field : CMPHS_CNTRT_STA_DT [포괄계약시작일자]
	 *		- field : CMPHS_CNTRT_END_DT [포괄계약종료일자]
	 *		- field : IN_OBJ_DTL_CD [회수사유코드]
	 *		- field : REMPRC [잔존가]
	 *		- field : CMPT_INDF_BACK_AMT [구성품변상반환금액]
	 * </pre>
	 */
	public IDataSet pInqReqpJdgLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    
        try {
            responseData = callSharedBizComponentByDirect("pr.PRSIMBase", "fInqReqpJdgLst", requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }       
        return responseData;
    }
  
}
