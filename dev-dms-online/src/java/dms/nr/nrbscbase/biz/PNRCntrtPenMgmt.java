package dms.nr.nrbscbase.biz;

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
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [PU]계약위약금관리</li>
 * <li>설  명 : <pre>계약위약금관리PU</pre></li>
 * <li>작성일 : 2015-07-17 10:27:42</li>
 * <li>작성자 : 이민재 (mindol1004)</li>
 * </ul>
 *
 * @author 이민재 (mindol1004)
 */
public class PNRCntrtPenMgmt extends fwk.base.ProcessUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public PNRCntrtPenMgmt(){
		super();
	}

	/**
	 * <pre>계약위약금조회</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-07-17 10:27:42
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : SVC_MGMT_NO [서비스관리번호]
	 *	- field : OP_FR_DT [해지시작일자]
	 *	- field : OP_TO_DT [해지종료일자]
	 *	- field : PEN_POLY_CL [위약금구분]
	 *	- field : NEW_CNTRTR_NM [고객명]
	 *	- field : SVC_NO [이동전화번호]
	 *	- field : SALE_AGN_CD [대리점코드]
	 *	- field : AGN_NM [대리점명]
	 *	- field : SVC_NO_ENPT [이동전화번호암호화]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_PEN_LIST
	 *		- field : SVC_MGMT_NO [서비스관리번호]
	 *		- field : NEW_CNTRTR_NM [고객명]
	 *		- field : LINE_NO [이동전화번호]
	 *		- field : PEN_POLY_CL [위약금유형]
	 *		- field : DMS_PEN_AMT [위약금]
	 *		- field : EQP_MDL_NM [모델명]
	 *		- field : PET_NM [펫네임]
	 *		- field : CNTRT_PRD [계약기간]
	 *		- field : RENTAL_CNTRT_STA_DT [계약시작일]
	 *		- field : RENTAL_CNTRT_END_DT [계약종료일]
	 *		- field : OP_PROC_DT [업무처리일자]
	 *		- field : ORG_NM [대리점명]
	 *		- field : OP_TYP_CD [계약상태]
	 *		- field : EQP_STAT [단말상태]
	 *		- field : PEN_GUBUN_NM [위약금유형명]
	 *		- field : CNTRT_NO [계약번호]
	 *		- field : CNTRT_PRD_NM [계약기간명]
	 *		- field : EQP_STAT_NM [단말상태명]
	 *		- field : CNTRT_ST_NM [계약상태명]
	 *		- field : SVC_NO_ENPT [이동전화번호암호화]
	 *		- field : PEN_OCCR_DT [위약금발생일자]
	 *		- field : RENTAL_FEE [월렌탈료]
	 *		- field : REM_RENTAL_FEE [잔여렌탈료]
	 *		- field : RTN_DEALY_NOD [반납지연일수]
	 * </pre>
	 */
	public IDataSet pInqCntrtPenLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
		
	    try {
			// 1. FM 호출
			responseData = callSharedBizComponentByDirect("nr.NRSSCBase", "fInqCntrtPenLst", requestData, onlineCtx);
		} catch ( BizRuntimeException e ) {
			throw e;
		} catch ( Exception e ) {
			throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
		}
		// 2. 결과값 리턴
		responseData.setOkResultMessage("DMS00001", null); // 정상 조회되었습니다.
	
	
	    return responseData;
	}

	/**
	 * <pre>계약위약금상세조회</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-07-17 10:27:42
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNTRT_NO [계약번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_CNTRT_PEN_DTL
	 *		- field : SVC_MGMT_NO [서비스관리번호]
	 *		- field : NEW_CNTRTR_NM [고객명]
	 *		- field : SVC_NO [이동전화번호]
	 *		- field : PEN_POLY_CL [위약금유형]
	 *		- field : DMS_PEN_AMT [위약금]
	 *		- field : PET_NM [펫]
	 *		- field : CNTRT_PRD [계약기간]
	 *		- field : RENTAL_CNTRT_STA_DT [계약시작일]
	 *		- field : RENTAL_CNTRT_END_DT [계약종료일]
	 *		- field : ORG_NM [대리점명]
	 *		- field : CNTRT_NO [계약번호]
	 *		- field : OP_PROC_DT [업무처리일자]
	 *		- field : AGN_CD [대리점코드]
	 *		- field : EQP_MDL_NM [모델명]
	 *		- field : SVC_NO_ENPT [이동전화번호암호화]
	 *		- field : RENTAL_FEE [월렌탈료]
	 *		- field : REM_RENTAL_FEE [잔여렌탈료]
	 *		- field : RTN_DEALY_NOD [반납지연일수]
	 * </pre>
	 */
	public IDataSet pInqCntrtPenDtl(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    try {
			// 1. FM 호출
			responseData = callSharedBizComponentByDirect("nr.NRSSCBase", "fInqCntrtPenDtl", requestData, onlineCtx);
		} catch ( BizRuntimeException e ) {
			throw e;
		} catch ( Exception e ) {
			throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
		}
		// 3. 결과값 리턴
		responseData.setOkResultMessage("DMS00001", null); // 정상 조회되었습니다.
	
	    return responseData;
	}
  
}
