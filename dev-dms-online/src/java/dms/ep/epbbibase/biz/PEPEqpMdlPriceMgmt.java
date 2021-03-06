package dms.ep.epbbibase.biz;

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
 * <li>단위업무명: [PU]단말기가격관리</li>
 * <li>설  명 : <pre>단말기가격관리</pre></li>
 * <li>작성일 : 2015-08-20 14:25:43</li>
 * <li>작성자 : 박민정 (dangnagu2)</li>
 * </ul>
 *
 * @author 박민정 (dangnagu2)
 */
public class PEPEqpMdlPriceMgmt extends fwk.base.ProcessUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public PEPEqpMdlPriceMgmt(){
		super();
	}

	/**
	 * <pre>단말기별단가조회</pre>
	 *
	 * @author 박민정 (dangnagu2)
	 * @since 2015-08-20 14:25:43
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : APLY_DT [기준일자]
	 *	- field : EQP_MDL_CD [모델코드]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_EQP_PRICE_LIST
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : EQP_MDL_NM [단말기모델명]
	 *		- field : SEQ [순번]
	 *		- field : N_GR_ PRCE [N등급가격]
	 *		- field : A_GR_ PRCE [A등급가격]
	 *		- field : B_GR_ PRCE [B등급가격]
	 *		- field : C_GR_ PRCE [C등급가격]
	 *		- field : D_GR_ PRCE [D등급가격]
	 *		- field : E_GR_ PRCE [E등급가격]
	 *		- field : UPRC [단가]
	 *		- field : FRDT [시작일]
	 *		- field : TODT [종료일]
	 *		- field : PRCLST_MGMT_NO [가격표 관리 번호]
	 *		- field : STRD_INCEN [기준 인센티브]
	 *		- field : RMK [비고]
	 *		- field : UPDATE_COUNT [UPDATE_COUNT]
	 * </pre>
	 */
	public IDataSet pInqEqpMdlPriceLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    try {
			// FM 호출
			responseData = callSharedBizComponentByDirect("ep.EPSBIBase", "fInqEqpMdlPriceLst", requestData, onlineCtx);
		} catch ( BizRuntimeException e ) {
			throw e;
		} catch ( Exception e ) {
			throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
		}
	    
	    return responseData;
	}

	/**
	 * <pre>구성품단가조회</pre>
	 *
	 * @author 박민정 (dangnagu2)
	 * @since 2015-08-20 14:25:43
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : EQP_MDL_CD [단말기 모델 코드]
	 *	- field : APLY_DT [기준일자]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_CMPT_PRICE_LIST
	 *		- field : EQP_MDL_CD [단말기 모델 코드]
	 *		- field : SEQ [순번]
	 *		- field : OUTS_MCN_CL [주변 기기 구분]
	 *		- field : BYGRD [등급별]
	 *		- field : QTY [수량]
	 *		- field : UPRC [단가]
	 *		- field : SALE_UPRC [매출 단가]
	 *		- field : NECES_CMPT_YN [필수 구성품 여부]
	 *		- field : BATR_CD [배터리 코드]
	 *		- field : FRDT [시작일]
	 *		- field : TODT [종료일]
	 *		- field : RMK [비고]
	 * </pre>
	 */
	public IDataSet pInqCmptPriceLst(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
	    
        try {
        	responseData = callSharedBizComponentByDirect("ep.EPSBIBase", "fInqCmptPriceLst", requestData, onlineCtx);
            //  결과값 리턴
            responseData.setOkResultMessage("DMS00001", null); //정상 조회되었습니다.
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); //시스템 오류
        }
    
        return responseData;
	}
}
