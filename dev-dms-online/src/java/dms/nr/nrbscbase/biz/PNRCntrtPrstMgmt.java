package dms.nr.nrbscbase.biz;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.exception.BizRuntimeException;
import nexcore.framework.core.util.StringUtils;

import org.apache.commons.logging.Log;

import fwk.common.CommonArea;
import fwk.constants.DmsConstants;


/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [PU]계약현황관리</li>
 * <li>설  명 : <pre>계약현황관리PU</pre></li>
 * <li>작성일 : 2015-07-17 10:04:20</li>
 * <li>작성자 : 이민재 (mindol1004)</li>
 * </ul>
 *
 * @author 이민재 (mindol1004)
 */
public class PNRCntrtPrstMgmt extends fwk.base.ProcessUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public PNRCntrtPrstMgmt(){
		super();
	}

	/**
	 * <pre>계약현황조회</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-07-17 10:04:20
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : SVC_MGMT_NO [서비스관리번호]
	 *	- field : RENTAL_CNTRT_STA_DT [계약시작일]
	 *	- field : RENTAL_CNTRT_END_DT [계약종료일]
	 *	- field : CNTRT_PRD [계약기간]
	 *	- field : OP_STA_DT [업무처리시작일]
	 *	- field : OP_END_DT [업무처리종료일]
	 *	- field : NEW_CNTRTR_NM [고객명]
	 *	- field : SVC_NO [서비스번호]
	 *	- field : AGN_CD [대리점코드]
	 *	- field : OP_TYP_CD [계약상태]
	 *	- field : EQP_RTN_YN [단말반납여부]
	 *	- field : EQP_SER_NO [단말기일련번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_CNTRT_PRST_LIST
	 *		- field : CNTRT_NO [계약번호]
	 *		- field : OP_CL_CD [업무구분코드]
	 *		- field : OP_PROC_DT [업무처리일자]
	 *		- field : OP_TYP_CD [계약상태(업무유형)]
	 *		- field : RENTAL_POLY_NO [렌탈정책번호]
	 *		- field : POLY_NM [정책명]
	 *		- field : DCP_NO [손해배상금정책번호]
	 *		- field : PEN_POLY_NO [위약금정책번호]
	 *		- field : EQP_SER_NO [단말기일련번호]
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : EQP_MDL_NM [단말기모델명]
	 *		- field : PET_NM [펫네임]
	 *		- field : COLOR_CD [색상코드]
	 *		- field : SVC_NO [서비스번호]
	 *		- field : SVC_NO_ENPT [서비스번호암오화]
	 *		- field : EQP_RTN_YN [단말반납여부코드]
	 *		- field : NEW_CNTRT_DT [계약일자]
	 *		- field : RENTAL_CNTRT_STA_DT [계약시작일자]
	 *		- field : RENTAL_CNTRT_END_DT [계약종료일자]
	 *		- field : CNTRT_PRD [계약기간]
	 *		- field : RENTAL_FEE [월렌탈료]
	 *		- field : SVC_MGMT_NO [서비스관리번호]
	 *		- field : RENTAL_SER_NO [렌탈일련번호]
	 *		- field : NEW_CNTRTR_NM [고객성명]
	 *		- field : NEW_CNTRTR_NM_ENPT [고객성명암호화]
	 *		- field : CUST_TYP [고객유형]
	 *		- field : NEW_CNTRTR_ZIPCD [우편번호]
	 *		- field : NEW_CNTRTR_INV_ADDR [청구주소]
	 *		- field : NEW_CNTRTR_INV_ADDR_DTL [청구상세주소]
	 *		- field : NEW_CNTRTR_BIRTH_YMD [생년월일]
	 *		- field : NEW_CNTRTR_CORP_REG_NO [법인등록번호]
	 *		- field : NEW_CNTRTR_BIZ_REG_NO [사업자등록번호]
	 *		- field : AGN_CD [대리점코드]
	 *		- field : AGN_NM [대리점명]
	 *		- field : LEGAL_AGENT_NM [법정대리인성명]
	 *		- field : LEGAL_AGENT_CUST_TYP [법정대리인고객유형]
	 *		- field : LEGAL_AGENT_BIZ_REG_NO [법정대리인사업자등록번호]
	 *		- field : LEGAL_AGENT_CORP_REG_NO [법정대리인법인번호]
	 *		- field : LEGAL_AGENT_BIRTH_YMD [법정대리인생년월일]
	 *		- field : LEGAL_AGENT_REL [법정대리인관계]
	 *		- field : TTUL_CHG_YN [명의변경여부]
	 *		- field : EQP_CHG_YN [단말변경여부]
	 *		- field : LEGAL_AGENT_CHG_YN [법정대리인변경여부]
	 *		- field : SVCL [내용연수]
	 *		- field : NEW_CNTRT_RMK [비고]
	 *		- field : FS_REG_USER_ID [최초등록사용자ID]
	 *		- field : FS_REG_DTM [최초등록일시]
	 *		- field : LS_CHG_USER_ID [최종변경사용자ID]
	 *		- field : LS_CHG_DTM [최종변경일시]
	 *		- field : TERM_PROC_RSLT_CD [해지처리결과]
	 *		- field : TERM_PROC_RSLT_DTL_CD [해지처리결과상세]
	 *		- field : USCAN_FNSH_YN [USCAN완료여부]
	 *		- field : ADD_USCAN_YN [USCAN추가여부]
	 * </pre>
	 */
	public IDataSet pInqCntrtPrstLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    try {
			// 1. FM 호출
			responseData = callSharedBizComponentByDirect("nr.NRSSCBase", "fInqCntrtPrstLst", requestData, onlineCtx);
		} catch ( BizRuntimeException e ) {
			throw e;
		} catch ( Exception e ) {
			throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
		}
		// 3. 결과값 리턴
		responseData.setOkResultMessage("DMS00001", null); // 정상 조회되었습니다.
	
	    return responseData;
	}

	/**
	 * <pre>계약현황상세조회</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-07-17 10:04:20
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNTRT_NO [계약번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_CNTRT_PRST_DTL
	 *		- field : CNTRT_NO [계약번호]
	 *		- field : OP_CL_CD [업무구분코드]
	 *		- field : OP_PROC_DT [업무처리일자]
	 *		- field : OP_TYP_CD [계약상태(업무유형)]
	 *		- field : RENTAL_POLY_NO [렌탈정책번호]
	 *		- field : POLY_NM [정책명]
	 *		- field : DCP_NO [손해배상금정책번호]
	 *		- field : PEN_POLY_NO [위약금정책번호]
	 *		- field : EQP_SER_NO [단말기일련번호]
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : EQP_MDL_NM [단말기모델명]
	 *		- field : PET_NM [펫네임]
	 *		- field : COLOR_CD [색상코드]
	 *		- field : SVC_NO [서비스번호]
	 *		- field : EQP_RTN_YN [단말반납여부코드]
	 *		- field : NEW_CNTRT_DT [계약일자]
	 *		- field : RENTAL_CNTRT_STA_DT [계약시작일자]
	 *		- field : RENTAL_CNTRT_END_DT [계약종료일자]
	 *		- field : CNTRT_PRD [계약기간]
	 *		- field : RENTAL_FEE [월렌탈료]
	 *		- field : SVC_MGMT_NO [서비스관리번호]
	 *		- field : RENTAL_SER_NO [렌탈일련번호]
	 *		- field : NEW_CNTRTR_NM [고객성명]
	 *		- field : CUST_TYP [고객유형]
	 *		- field : NEW_CNTRTR_ZIPCD [우편번호]
	 *		- field : NEW_CNTRTR_INV_ADDR [청구주소]
	 *		- field : NEW_CNTRTR_INV_ADDR_DTL [청구상세주소]
	 *		- field : NEW_CNTRTR_BIRTH_YMD [생년월일]
	 *		- field : NEW_CNTRTR_CORP_REG_NO [법인등록번호]
	 *		- field : NEW_CNTRTR_BIZ_REG_NO [사업자등록번호]
	 *		- field : AGN_CD [대리점코드]
	 *		- field : AGN_NM [대리점명]
	 *		- field : LEGAL_AGENT_NM [법정대리인성명]
	 *		- field : LEGAL_AGENT_CUST_TYP [법정대리인고객유형]
	 *		- field : LEGAL_AGENT_BIZ_REG_NO [법정대리인사업자등록번호]
	 *		- field : LEGAL_AGENT_CORP_REG_NO [법정대리인법인번호]
	 *		- field : LEGAL_AGENT_BIRTH_YMD [법정대리인생년월일]
	 *		- field : LEGAL_AGENT_REL [법정대리인관계]
	 *		- field : TTUL_CHG_YN [명의변경여부]
	 *		- field : EQP_CHG_YN [단말변경여부]
	 *		- field : LEGAL_AGENT_CHG_YN [법정대리인변경여부]
	 *		- field : SVCL [내용연수]
	 *		- field : NEW_CNTRT_RMK [비고]
	 *		- field : FS_REG_USER_ID [최초등록사용자ID]
	 *		- field : FS_REG_DTM [최초등록일시]
	 *		- field : LS_CHG_USER_ID [최종변경사용자ID]
	 *		- field : LS_CHG_DTM [최종변경일시]
	 *		- field : TERM_PROC_RSLT_CD [해지처리결과]
	 *		- field : TERM_PROC_RSLT_DTL_CD [해지처리결과상세]
	 *		- field : RENTAL_PRN [총렌탈료]
	 *		- field : TERM_PROC_REQS_DT [해지처리요청일자]
	 * </pre>
	 */
	public IDataSet pInqCntrtPrstDtl(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    try {
			// 1. FM 호출
			responseData = callSharedBizComponentByDirect("nr.NRSSCBase", "fInqCntrtPrstDtl", requestData, onlineCtx);
		} catch ( BizRuntimeException e ) {
			throw e;
		} catch ( Exception e ) {
			throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
		}
		// 3. 결과값 리턴
		responseData.setOkResultMessage("DMS00001", null); // 정상 조회되었습니다.
	
	    return responseData;
	}

	/**
	 * <pre>계약현황이력조회</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-07-17 10:04:20
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : SVC_MGMT_NO [서비스관리번호]
	 *	- field : OP_STA_DT [업무처리시작일]
	 *	- field : OP_END_DT [업무처리종료일]
	 *	- field : NEW_CNTRTR_NM [고객명]
	 *	- field : SVC_NO [서비스번호]
	 *	- field : AGN_CD [대리점코드]
	 *	- field : OP_TYP_CD [계약상태]
	 *	- field : TTUL_CHG_YN [명의변경여부]
	 *	- field : EQP_CHG_YN [단말변경여부]
	 *	- field : LEGAL_AGENT_CHG_YN [법정대리인변경여부]
	 *	- field : CHG_YN [변경구분상태]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_CNTRT_HIS_LIST
	 *		- field : NEW_CNTRT_HST_SEQ [계약현황이력번호]
	 *		- field : CNTRT_NO [계약번호]
	 *		- field : OP_PROC_DT [업무처리일자]
	 *		- field : OP_TYP_CD [계약상태(업무유형)]
	 *		- field : RENTAL_POLY_NO [렌탈정책번호]
	 *		- field : POLY_NM [렌탈정책명]
	 *		- field : DCP_NO [손해배상금정책번호]
	 *		- field : PEN_POLY_NO [위약금정책번호]
	 *		- field : EQP_SER_NO [단말기일련번호]
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : EQP_MDL_NM [단말기모델명]
	 *		- field : PET_NM [펫네임]
	 *		- field : COLOR_CD [색상코드]
	 *		- field : SVC_NO [서비스번호]
	 *		- field : EQP_RTN_YN [단말반납여부]
	 *		- field : NEW_CNTRT_DT [계약일자]
	 *		- field : RENTAL_CNTRT_STA_DT [계약시작일자]
	 *		- field : RENTAL_CNTRT_END_DT [계약종료일자]
	 *		- field : CNTRT_PRD [계약기간]
	 *		- field : RENTAL_FEE [월렌탈료]
	 *		- field : SVC_MGMT_NO [서비스관리번호]
	 *		- field : RENTAL_SER_NO [렌탈일련번호]
	 *		- field : NEW_CNTRTR_NM [고객성명]
	 *		- field : CUST_TYP [고객유형]
	 *		- field : NEW_CNTRTR_ZIPCD [우편번호]
	 *		- field : NEW_CNTRTR_INV_ADDR [청구주소]
	 *		- field : NEW_CNTRTR_INV_ADDR_DTL [청구주소상세]
	 *		- field : NEW_CNTRTR_BIRTH_YMD [생년월일]
	 *		- field : NEW_CNTRTR_CORP_REG_NO [법인등록번호]
	 *		- field : NEW_CNTRTR_BIZ_REG_NO [사업자등록번호]
	 *		- field : AGN_CD [대리점코드]
	 *		- field : AGN_NM [대리점명]
	 *		- field : LEGAL_AGENT_NM [법정대리인성명]
	 *		- field : LEGAL_AGENT_CUST_TYP [법정대리인고객유형]
	 *		- field : LEGAL_AGENT_BIZ_REG_NO [법정대리인사업자등록번호]
	 *		- field : LEGAL_AGENT_CORP_REG_NO [법정대리인법인번호]
	 *		- field : LEGAL_AGENT_BIRTH_YMD [법정대리인생년월일]
	 *		- field : LEGAL_AGENT_REL [법정대리인관계]
	 *		- field : CHG_YN [변경구분]
	 *		- field : TTUL_CHG_YN [명의변경여부]
	 *		- field : EQP_CHG_YN [단말변경여부]
	 *		- field : LEGAL_AGENT_CHG_YN [법정대리인변경여부]
	 *		- field : SVCL [내용연수]
	 *		- field : NEW_CNTRT_HST_RMK [메모]
	 *		- field : FS_REG_USER_ID [최초등록사용자ID]
	 *		- field : FS_REG_DTM [최초등록일시]
	 *		- field : LS_CHG_USER_ID [최종변경사용자ID]
	 *		- field : LS_CHG_DTM [최종변경일시]
	 *		- field : TERM_PROC_RSLT_CD [해지처리결과]
	 *		- field : TERM_PROC_RSLT_DTL_CD [해지처리결과상세]
	 * </pre>
	 */
	public IDataSet pInqCntrtPrstHisLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    try {
			// 1. FM 호출
			responseData = callSharedBizComponentByDirect("nr.NRSSCBase", "fInqCntrtPrstHisLst", requestData, onlineCtx);
		} catch ( BizRuntimeException e ) {
			throw e;
		} catch ( Exception e ) {
			throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
		}
		// 3. 결과값 리턴
		responseData.setOkResultMessage("DMS00001", null); // 정상 조회되었습니다.
	
	    return responseData;
	}

	/**
	 * <pre>계약현황이력상세조회</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-07-17 10:04:20
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNTRT_NO [계약번호]
	 *	- field : NEW_CNTRT_HST_SEQ [계약현황이력번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_CNTRT_HIS_DTL
	 *		- field : NEW_CNTRT_HST_SEQ [계약현황이력번호]
	 *		- field : CNTRT_NO [계약번호]
	 *		- field : OP_PROC_DT [업무처리일자]
	 *		- field : OP_TYP_CD [계약상태(업무유형)]
	 *		- field : RENTAL_POLY_NO [렌탈정책번호]
	 *		- field : POLY_NM [렌탈정책명]
	 *		- field : DCP_NO [손해배상금정책번호]
	 *		- field : PEN_POLY_NO [위약금정책번호]
	 *		- field : EQP_SER_NO [단말기일련번호]
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : EQP_MDL_NM [단말기모델명]
	 *		- field : PET_NM [펫네임]
	 *		- field : COLOR_CD [색상코드]
	 *		- field : SVC_NO [서비스번호]
	 *		- field : SVC_NO_ENPT [서비스번호암호화]
	 *		- field : EQP_RTN_YN [단말반납여부]
	 *		- field : NEW_CNTRT_DT [계약일자]
	 *		- field : RENTAL_CNTRT_STA_DT [계약시작일자]
	 *		- field : RENTAL_CNTRT_END_DT [계약종료일자]
	 *		- field : CNTRT_PRD [계약기간]
	 *		- field : RENTAL_FEE [월렌탈료]
	 *		- field : SVC_MGMT_NO [서비스관리번호]
	 *		- field : RENTAL_SER_NO [렌탈일련번호]
	 *		- field : NEW_CNTRTR_NM [고객성명]
	 *		- field : NEW_CNTRTR_NM_ENPT [고객성명암호화]
	 *		- field : CUST_TYP [고객유형]
	 *		- field : NEW_CNTRTR_ZIPCD [우편번호]
	 *		- field : NEW_CNTRTR_INV_ADDR [청구주소]
	 *		- field : NEW_CNTRTR_INV_ADDR_DTL [청구주소상세]
	 *		- field : NEW_CNTRTR_INV_ADDR_DTL_ENPT [청구주소상세암호화]
	 *		- field : NEW_CNTRTR_BIRTH_YMD [생년월일]
	 *		- field : NEW_CNTRTR_BIRTH_YMD_ENPT [생년월일암호화]
	 *		- field : NEW_CNTRTR_CORP_REG_NO [법인등록번호]
	 *		- field : NEW_CNTRTR_BIZ_REG_NO [사업자등록번호]
	 *		- field : AGN_CD [대리점코드]
	 *		- field : AGN_NM [대리점명]
	 *		- field : LEGAL_AGENT_NM [법정대리인성명]
	 *		- field : LEGAL_AGENT_NM_ENPT [법정대리인성명암호화]
	 *		- field : LEGAL_AGENT_CUST_TYP [법정대리인고객유형]
	 *		- field : LEGAL_AGENT_BIZ_REG_NO [법정대리인사업자등록번호]
	 *		- field : LEGAL_AGENT_CORP_REG_NO [법정대리인법인번호]
	 *		- field : LEGAL_AGENT_BIRTH_YMD [법정대리인생년월일]
	 *		- field : LEGAL_AGENT_BIRTH_YMD_ENPT [법정대리인생년월일암호화]
	 *		- field : LEGAL_AGENT_REL [법정대리인관계]
	 *		- field : TTUL_CHG_YN [명의변경여부]
	 *		- field : EQP_CHG_YN [단말변경여부]
	 *		- field : LEGAL_AGENT_CHG_YN [법정대리인변경여부]
	 *		- field : SVCL [내용연수]
	 *		- field : NEW_CNTRT_HST_RMK [메모]
	 *		- field : FS_REG_USER_ID [최초등록사용자ID]
	 *		- field : FS_REG_DTM [최초등록일시]
	 *		- field : LS_CHG_USER_ID [최종변경사용자ID]
	 *		- field : LS_CHG_DTM [최종변경일시]
	 *		- field : TERM_PROC_RSLT_CD [해지처리결과]
	 *		- field : TERM_PROC_RSLT_DTL_CD [해지처리결과상세]
	 * </pre>
	 */
	public IDataSet pInqCntrtPrstHisDtl(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    try {
			// 1. FM 호출
			responseData = callSharedBizComponentByDirect("nr.NRSSCBase", "fInqCntrtPrstHisDtl", requestData, onlineCtx);
		} catch ( BizRuntimeException e ) {
			throw e;
		} catch ( Exception e ) {
			throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
		}
		// 3. 결과값 리턴
		responseData.setOkResultMessage("DMS00001", null); // 정상 조회되었습니다.
	
	    return responseData;
	}

	/**
	 * <pre>계약현황이력메모저장</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-07-17 10:04:20
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_CNTRT_HIS_DTL
	 *		- field : CNTRT_NO [계약번호]
	 *		- field : NEW_CNTRT_HST_SEQ [계약이력순번]
	 *		- field : NEW_CNTRT_HST_RMK [메모]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet pSaveCntrtPrstHisRmk(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
		CommonArea ca = getCommonArea(onlineCtx);

		try {
			// 1. 입력 RS설정
			requestData.putField("USERNO", ca.getUserNo());
			requestData.putFieldMap(requestData.getRecordSet("RS_CNTRT_HIS_DTL").getRecordMap(0));
				
			// 2. 레코드셋 상태에 따른 분기
			callSharedBizComponentByDirect("nr.NRSSCBase", "fUpdCntrtPrstHisRmk", requestData, onlineCtx);
				
			// 3. 결과값 리턴
			responseData.setOkResultMessage("DMS00000", null); //정상 처리되었습니다.
		} catch ( BizRuntimeException e ) {
			throw e;
		} catch ( Exception e ) {
			throw new BizRuntimeException("DMS00009", e); //시스템 오류
		}
	
	    return responseData;
	}

    /**
	 * <pre>계약현황USCAN조회</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-07-17 10:04:20
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNTRT_NO [계약번호]
	 *	- field : APPLF_IMG_URL [이미지URL]
	 *	- field : SVC_MGMT_NO [서비스관리번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_CNTRT_USCAN_LIST
	 *		- field : SVC_MGMT_NO [서비스관리번호]
	 *		- field : CNTRT_NO [계약번호]
	 *		- field : APPLF_ST [신청서상태]
	 *		- field : APPLF_SUBMIT_DTM [신청서제출일시]
	 *		- field : DMS267_01 [이미지오류여부]
	 *		- field : DMS267_02 [이미지오류여부]
	 *		- field : DMS267_03 [이미지오류여부]
	 *		- field : DMS267_04 [이미지오류여부]
	 *		- field : DMS267_05 [이미지오류여부]
	 *		- field : DMS267_06 [이미지오류여부]
	 *		- field : DMS267_07 [이미지오류여부]
	 *		- field : DMS267_08 [이미지오류여부]
	 *		- field : DMS267_09 [이미지오류여부]
	 *		- field : DMS267_10 [이미지오류여부]
	 *		- field : DMS267_11 [이미지오류여부]
	 *		- field : DMS267_12 [이미지오류여부]
	 *		- field : DMS267_13 [이미지오류여부]
	 *		- field : DMS267_14 [이미지오류여부]
	 *		- field : DMS267_15 [이미지오류여부]
	 *		- field : DMS267_16 [이미지오류여부]
	 *		- field : DMS267_17 [이미지오류여부]
	 *		- field : DMS267_18 [이미지오류여부]
	 *		- field : DMS267_19 [이미지오류여부]
	 *		- field : DMS267_20 [이미지오류여부]
	 *		- field : DMS267_21 [이미지오류여부]
	 *		- field : DMS267_22 [이미지오류여부]
	 *		- field : DMS267_23 [이미지오류여부]
	 *		- field : DMS267_24 [이미지오류여부]
	 *		- field : DMS267_25 [이미지오류여부]
	 *		- field : DMS267_26 [이미지오류여부]
	 *		- field : APPLF_RMK [비고]
	 *		- field : APPLF_ITM [신청서항목]
	 * </pre>
	 */
	public IDataSet pInqCntrtPrstUscanLst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        try {
            // 1. FM 호출
            responseData = callSharedBizComponentByDirect("nr.NRSSCBase", "fInqCntrtPrstUscanLst", requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }
        // 3. 결과값 리턴
        responseData.setOkResultMessage("DMS00001", null); // 정상 조회되었습니다.
    
        return responseData;
    }

    /**
	 * <pre>계약현황USCAN오류항목저장</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-07-17 10:04:20
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_CNTRT_USCAN_LIST
	 *		- field : CNTRT_NO [계약번호]
	 *		- field : APPLF_ST [신청서상태]
	 *		- field : APPLF_SUBMIT_DTM [이미지제출일시]
	 *		- field : APPLF_ITM [신청서항목]
	 *		- field : APPLF_RMK [비고]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet pSaveCntrtPrstUscan(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        CommonArea ca = getCommonArea(onlineCtx);

        try {
            // 1. 입력 RS설정
            requestData.putField("USERNO", ca.getUserNo());
            requestData.putFieldMap(requestData.getRecordSet("RS_CNTRT_USCAN_LIST").getRecordMap(0));
            
            IDataSet valDS = callSharedBizComponentByDirect("nr.NRSSCBase", "fInqCntrtPrstUscanExistYN", requestData, onlineCtx);
            if ( Integer.parseInt(valDS.getField("ROW_CNT")) > 0 ) {
                callSharedBizComponentByDirect("nr.NRSSCBase", "fUpdCntrtPrstUscan", requestData, onlineCtx);
            } else {
                callSharedBizComponentByDirect("nr.NRSSCBase", "fRegCntrtPrstUscan", requestData, onlineCtx);
            }
            
            // 2. 레코드셋 상태에 따른 분기
            /*
            if ( "I".equals(ca.getTrnPtrnDvcd()) ) { // INSERT
                callSharedBizComponentByDirect("nr.NRSSCBase", "fRegCntrtPrstUscan", requestData, onlineCtx);
            } else if ( "U".equals(ca.getTrnPtrnDvcd()) ) { // UPDATE
                callSharedBizComponentByDirect("nr.NRSSCBase", "fUpdCntrtPrstUscan", requestData, onlineCtx);
            }
            */
                
            // 3. 결과값 리턴
            responseData.setOkResultMessage("DMS00000", null); //정상 처리되었습니다.
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); //시스템 오류
        }
    
        return responseData;
    }

    /**
	 *
	 *
	 * @author 이영진 (newnofixing)
	 * @since 2015-07-17 10:04:20
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_SVC_MGMT_NO_LIST
	 *		- field : SVC_MGMT_NO [서비스관리번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_CNTRT_USCAN_IMG_LIST
	 *		- field : CUST_ACNT_SVC_MGMT_NO [고객계정서비스관리번호]
	 *		- field : IF_FILE_NM [이미지파일명]
	 * </pre>
	 */
	public IDataSet pInqCntrtPrstUscanImgLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    Log log = getLog(onlineCtx);
        try {
            IRecordSet rsSvcMgmtNoList = requestData.getRecordSet("RS_SVC_MGMT_NO_LIST");
            log.debug("PNRCntrtPrstMgmt:::::RS_SVC_MGMT_NO_LIST COUNT["+rsSvcMgmtNoList.getRecordCount()+"]");
            
            // 1. FM 호출
            responseData = callSharedBizComponentByDirect("nr.NRSSCBase", "fSCntrtPrstUscanImgLst", requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }
        // 3. 결과값 리턴
        responseData.setOkResultMessage("DMS00001", null); // 정상 조회되었습니다.
    
        return responseData;
    }

    /**
	 *
	 *
	 * @author 이영진 (newnofixing)
	 * @since 2015-07-17 10:04:20
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNTRT_NO [계약번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_EQP_CMPT_RTN_LIST
	 *		- field : CMPT_CD [구성품코드]
	 *		- field : RTN_YN [반납여부]
	 * </pre>
	 */
	public IDataSet pInqEqpCmptRtnLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        try {
            // 1. FM 호출
            responseData = callSharedBizComponentByDirect("nr.NRSSCBase", "fInqEqpCmptRtnLst", requestData, onlineCtx);
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
