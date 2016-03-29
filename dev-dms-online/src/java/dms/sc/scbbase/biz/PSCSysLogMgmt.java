package dms.sc.scbbase.biz;

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
 * <li>업무 그룹명 : HPC/서비스공통</li>
 * <li>단위업무명: [PU]시스템로그조회</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2014-09-05 16:41:45</li>
 * <li>작성자 : 심상준 (simair)</li>
 * </ul>
 *
 * @author 심상준 (simair)
 */
public class PSCSysLogMgmt extends fwk.base.ProcessUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public PSCSysLogMgmt() {
		super();
	}

	/**
	 * <pre>시스템로그 목록조회</pre>
	 *
	 * @author admin (admin)
	 * @since 2015-06-22 16:59:55
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CL_CD [구분코드]
	 *	- field : LOG_BZOP_DT [로그영업일자]
	 *	- field : TRN_CD [거래코드]
	 *	- field : SCRN_NO [화면번호]
	 *	- field : XTIS_CD [대외기관코드]
	 *	- field : OTSD_MESG_CD [대외전문코드]
	 *	- field : TRTM_RSLT_CD [처리결과코드]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_SYS_LOG_LIST
	 *		- field : LOG_BZOP_DT [로그영업일자]
	 *		- field : GLOB_ID [글로벌ID]
	 *		- field : SEQ_NO [시퀀스NO]
	 *		- field : PRGS_SRNO [진행일련번호]
	 *		- field : ENV_DVCD [환경구분코드]
	 *		- field : TRTM_RSLT_CD [처리결과코드]
	 *		- field : TRN_CD [거래코드]
	 *		- field : SCRN_NO [화면번호]
	 *		- field : TRN_PTRN_DVCD [거래유형구분코드]
	 *		- field : MESG_VRSN_DVCD [버전구분코드]
	 *		- field : MESG_TYCD [전문유형코드]
	 *		- field : MESG_CNTY_SRNO [전문연속일련번호]
	 *		- field : MESG_DVCD [전문구분코드]
	 *		- field : CMPG_RELM_USE_DVCD [캠패인영역사용구분코드]
	 *		- field : XTIS_CD [대외기관코드]
	 *		- field : BZWR_SVR_CD [업무서버코드]
	 *		- field : OTSD_MESG_CD [대외전문코드]
	 *		- field : OTSD_MESG_TRTM_CD [대외전문처리코드]
	 *		- field : OTSD_TRN_UNQ_NO [대외거래고유번호]
	 *		- field : OTSD_RESP_TRN_CD [대외응답거래코드]
	 *		- field : CHNL_MSG_CD [채널메시지코드]
	 *		- field : EAI_GLOB_ID [EAI Global ID]
	 *		- field : EAI_INTF_ID [EAI 인터페이스ID]
	 *		- field : EAI_RCEV_SVCID [EAI 결과수신서비스ID]
	 *		- field : MSG_CCNT [메시지컬럼갯수]
	 *		- field : MSG_CD1 [메시지코드1]
	 *		- field : MSG_CNTN1 [메시지내용1]
	 *		- field : EROR_OCRN_PRRM_LINE1 [오류발생 LINE 1]
	 *		- field : EROR_OCRN_PRRM_NM1 [오류발생 APP명 1]
	 *		- field : MSG_CD2 [메시지코드 2]
	 *		- field : MSG_CNTN2 [메시지내용 2]
	 *		- field : EROR_OCRN_PRRM_LINE2 [오류발생 LINE 2]
	 *		- field : EROR_OCRN_PRRM_NM2 [오류발생 APP명 2]
	 *		- field : MSG_CD3 [메시지코드 3]
	 *		- field : MSG_CNTN3 [메시지내용 3]
	 *		- field : EROR_OCRN_PRRM_LINE3 [오류발생 LINE 3]
	 *		- field : EROR_OCRN_PRRM_NM3 [오류발생 APP명 3]
	 *		- field : MSG_CD4 [메시지코드 4]
	 *		- field : MSG_CNTN4 [메시지내용 4]
	 *		- field : EROR_OCRN_PRRM_LINE4 [오류발생 LINE 4]
	 *		- field : EROR_OCRN_PRRM_NM4 [오류발생 APP명 4]
	 *		- field : MSG_CD5 [메시지코드 5]
	 *		- field : MSG_CNTN5 [메시지내용 5]
	 *		- field : EROR_OCRN_PRRM_LINE5 [오류발생 LINE5]
	 *		- field : EROR_OCRN_PRRM_NM5 [오류발생 APP명 5]
	 *		- field : MSG_CD6 [메시지코드 6]
	 *		- field : MSG_CNTN6 [메시지내용 6]
	 *		- field : EROR_OCRN_PRRM_LINE6 [오류발생 LINE 6]
	 *		- field : EROR_OCRN_PRRM_NM6 [오류발생 APP명 6]
	 *		- field : MSG_CD7 [메시지코드 7]
	 *		- field : MSG_CNTN7 [메시지내용 7]
	 *		- field : EROR_OCRN_PRRM_LINE7 [오류발생 LINE 7]
	 *		- field : EROR_OCRN_PRRM_NM7 [오류발생 APP명 7]
	 *		- field : MSG_CD8 [메시지코드 8]
	 *		- field : MSG_CNTN8 [메시지내용 8]
	 *		- field : EROR_OCRN_PRRM_LINE8 [오류발생 LINE 8]
	 *		- field : EROR_OCRN_PRRM_NM8 [오류발생 APP명 8]
	 *		- field : MSG_CD9 [메시지코드 9]
	 *		- field : MSG_CNTN9 [메시지내용 9]
	 *		- field : EROR_OCRN_PRRM_LINE9 [오류발생 LINE 9]
	 *		- field : EROR_OCRN_PRRM_NM9 [오류발생 APP명 9]
	 *		- field : MSG_CD10 [메시지코드 10]
	 *		- field : MSG_CNTN10 [메시지내용 10]
	 *		- field : EROR_OCRN_PRRM_LINE10 [오류발생 LINE 10]
	 *		- field : EROR_OCRN_PRRM_NM10 [오류발생 APP명 10]
	 *		- field : SPR_CHRS_CNTN [예비문자열내용]
	 *		- field : IPAD [IP주소]
	 *		- field : PRCM_MAC [PC MAC주소]
	 *		- field : SSO_SESN_KEY [SSO 세션 KEY]
	 *		- field : FRST_TRNM_CHNL_CD [최초전송채널코드]
	 *		- field : TRNM_CHNL_CD [전송채널코드]
	 *		- field : TRNM_NODE_NO [전송노드번호]
	 *		- field : MCI_TRNM_NODE_NO [MCI전송노드번호]
	 *		- field : TRN_TRNM_NO [거래단말번호]
	 *		- field : ITLK_DPTH [연동깊이]
	 *		- field : FRST_TRN_CD [최초거래코드]
	 *		- field : MV_TRN_CD [기동거래코드]
	 *		- field : SYNC_DV [Sync/Async구분]
	 *		- field : MESG_DMND_DTTM [전문요청일시]
	 *		- field : MESG_RESP_DTTM [전문응답일시]
	 *		- field : TRN_DT [거래일자]
	 *		- field : SVC_STRN_DTTM [서비스시작일시]
	 *		- field : SVC_END_DTTM [서비스종료일시]
	 *		- field : COMP_CD [회사코드]
	 *		- field : DEPT_CD [부서코드]
	 *		- field : USER_NO [사용자번호]
	 *		- field : DEPT_DVCD [부서구분코드]
	 *		- field : BR_CD [부점코드]
	 *		- field : USER_DSTC_CD [사용자구분코드]
	 *		- field : CTI_YN [CTI 여부]
	 *		- field : USER_LOCALE [사용자 로케일]
	 *		- field : CSHN_OCRN_YN [시제발생여부]
	 *		- field : CASH_AMT [결제금액]
	 *		- field : POINT_AMT [포인트금액]
	 *		- field : SVC_DUR_TM [거래의 Elapse시간]
	 *		- field : WAS_INSTANCE_ID [WAS인스턴스]
	 *		- field : RGSTRN_DT [로그등록일]
	 *		- field : USER_CL_CD [사용자구분코드]
	 *		- field : JOBR_CD [직급코드]
	 *		- field : JTIL_CD [직책코드]
	 *		- field : CO_CD [회사코드]
	 *		- field : BRND_CD [브랜드코드]
	 *		- field : MCHT_NO [가맹점번호]
	 *		- field : EMP_NO [필드1]
	 * </pre>
	 */
	public IDataSet pInqSysLogLst(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
		try {
			// 1. FU lookup
		    responseData = callSharedBizComponentByDirect("fw.FWSBase", "fInqSysLogLst", requestData, onlineCtx);
	
			// 3. 결과값 리턴
			responseData.setOkResultMessage("DMS00001", null); //정상 조회되었습니다.
		} catch ( BizRuntimeException e ) {
			throw e;
		} catch ( Exception e ) {
			throw new BizRuntimeException("DMS00009", e); //시스템 오류
		}
		return responseData;
	}

}
