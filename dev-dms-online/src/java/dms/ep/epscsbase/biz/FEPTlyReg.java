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
 * <li>단위업무명: [FU]검수등록</li>
 * <li>설  명 : <pre>[FU]검수등록</pre></li>
 * <li>작성일 : 2015-09-01 13:57:21</li>
 * <li>작성자 : 김윤환 (kyh0904)</li>
 * </ul>
 *
 * @author 김윤환 (kyh0904)
 */
public class FEPTlyReg extends fwk.base.FunctionUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public FEPTlyReg(){
		super();
	}

    /**
	 * <pre>[FM]일반감정검수등록목록조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNSL_MGMT_NO [상담관리번호]
	 *	- field : EQP_MDL_CD [단말기모델코드]
	 *	- field : SCRB_MTHD [가입방법]
	 *	- field : PRG_GB [검수등록대상구분]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_TLY_REG_LIST
	 *		- field : CNSL_MGMT_NO [상담 관리 번호]
	 *		- field : EQP_MDL_CD [단말기 모델 코드]
	 *		- field : EQP_MDL_NM [단말기 모델명]
	 *		- field : EQP_SER_NO [단말기 일련 번호]
	 *		- field : CNSL_DT [상담 일자]
	 *		- field : DEAL_CO_NM [상담 거래처명]
	 *		- field : PRCHS_CNSL_DEALCO_NM [매입상담거래처명]
	 *		- field : AGN_ORG_CD [대리점 조직 코드]
	 *		- field : AFFIL_AGN [소속 대리점]
	 *		- field : CNSL_DEALCO_CD [상담 거래처코드]
	 *		- field : CNSL_DEALCO_NM [거래처 명]
	 *		- field : EQP_COLOR_CD [단말기 색상 코드]
	 *		- field : ONING_DT [개통 일자]
	 *		- field : EQP_ST [단말기 상태]
	 *		- field : EQP_ST_DTAIL [단말기 상태 세부]
	 *		- field : EQP_ST_TXT [단말기 상태 내용]
	 *		- field : USE_PRD_DDCT_AMT [사용 기간 차감 금액]
	 *		- field : INR_CORR_YN [내부 부식 여부]
	 *		- field : PWR_SYS [전원 시스템]
	 *		- field : CALL_QULT_TEST [통화 품질 테스트]
	 *		- field : DATA_PRFM [DATA 성능]
	 *		- field : SCRN_TEST [화면 테스트]
	 *		- field : FUNC_TEST [기능 테스트]
	 *		- field : LOSS_EQP_YN [분실 단말기 여부]
	 *		- field : RWD_RTN_OBJ_YN [보상 반납 대상 여부]
	 *		- field : RENTP_YN [임대폰 여부]
	 *		- field : WET_YN [침수 여부]
	 *		- field : USIM_EQPCHG_YN [USIM 기변 여부]
	 *		- field : OTHRCO_FORGN_EQP_YN [타사 해외 단말기 여부]
	 *		- field : SUGG [상담의견]
	 *		- field : EVALCNSLR_AUTCT_YN [평가원 인증 여부]
	 *		- field : FULLSET_YN [FULLSET 여부]
	 *		- field : CON_AMT [매입 금액]
	 *		- field : CNSL_AMT [상담 금액]
	 *		- field : PRCH_CL [매입 구]
	 *		- field : CTZ_NO [주민 번호]
	 *		- field : DPSTR [예금주]
	 *		- field : BANK_CD [은행 코드]
	 *		- field : ACCO_NO [계좌 번호]
	 *		- field : TEL_NO [전화 번호]
	 *		- field : ZIPCD [우편번호]
	 *		- field : ADDR [주소]
	 *		- field : ETC_ADDR [기타 주소]
	 *		- field : FS_REG_USER_ID [최초등록사용자ID]
	 *		- field : LS_CHG_USER_ID [최종변경사용자ID]
	 *		- field : USER_NM [최종변경자]
	 *		- field : PRCH_MGMT_NO [매입 관리 번호]
	 *		- field : EVALCNSLR_SUGG [평가원 의견]
	 *		- field : ADJ_AMT [조정 금액]
	 *		- field : FEE_DEDC_YN [요금 공제 여부]
	 *		- field : SVC_NO [서비스 번호]
	 *		- field : SVC_MGMT_NO [서비스 관리 번호]
	 *		- field : IMEI [IMEI]
	 *		- field : ETC_CMPT [기타 구성품]
	 *		- field : PRC_GB [필드4]
	 *		- field : RETUR_DEALCO_CL [반송 거래처 구분]
	 *		- field : CUST_IDEA [고객 의사]
	 *		- field : AMT_RMT_YN [금액 송금 여부]
	 *		- field : UPDATE_COUNT [UPDATE_COUNT]
	 *		- field : POLY_OBJ_YN [정책 대상 여부]
	 *		- field : POLY_OBJ_CD [정책 대상 코드]
	 *		- field : PROGR_ST [진행상태]
	 *		- field : BOX_NO [박스 번호]
	 *		- field : SKN_CL [SKN 구분]
	 *		- field : SKN_EQP_ST [SKN 단말기 상태]
	 *		- field : SKN_JDG_AMT [KN 감정 금액]
	 *		- field : SKN_JDG_CL [SKN 감정 구분]
	 *		- field : SKN_SMPL_JDG_DAMT [SKN 샘플 감정 차액]
	 *		- field : PRE_DC_CL [선 할인 구분]
	 *		- field : SCRN_AFIMG_YN [화면 잔상 여부]
	 *		- field : STRD_INCEN [기준 인센티브]
	 *		- field : FST_RGSTR [최초 등록자]
	 *		- field : ASIANA_CARD_NO [아시아나 카드 번호]
	 *		- field : SCRB_MTHD [가입 방법]
	 *		- field : SELL_GRADE [판매등급]
	 *		- field : CMCT_CO [통신회사]
	 *		- field : DPSTR_ENPT [예금주 암호화]
	 *		- field : TEL_NO_ENPT [전화번호 암호화]
	 *		- field : ETC_ADDR_ENPT [기타 주소 암호화]
	 *		- field : ACCO_NO_ENPT [계좌번호 암호화]
	 *		- field : SVC_NO_ENPT [서비스번호 암호화]
	 *		- field : TLY_DT [검수일자]
	 *		- field : STARD_YN [회수 기준 여부]
	 *		- field : STARD_DD [회수 기준일자]
	 *		- field : PRCH_CHG_DAMT_AMT [매입 변경 차액 금액]
	 *		- field : PRCH_CHG_STRD_NOD [매입 변경 기준 일수]
	 *		- field : IN_CONF_DT [입고 확정 일자]
	 * </pre>
	 */
	public IDataSet fSGnrlJdgTlyRegLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dSGnrlJdgTlyRegLst(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]일반감정검수등록구성품목록조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNSL_MGMT_NO [상담관리번호]
	 *	- field : EQP_MDL_CD [단말기모델코드]
	 *	- field : SCRB_MTHD [가입방법]
	 *	- field : PRG_GB [검수등록대상구분]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_TLY_REG_CMPT_LIST
	 *		- field : EQP_MDL_CD [단말기 모델 코드]
	 *		- field : OUTS_MCN_CL [주변 기기 구분]
	 *		- field : QTY [수량]
	 *		- field : UPRC [단가]
	 *		- field : UNIT_UPRC [필드1]
	 *		- field : SALE_UPRC [매출 단가]
	 *		- field : NORM_QTY [정상 수량]
	 *		- field : HLD_QTY [보유 수량]
	 *		- field : SKN_SMPL_QTY [SKN 샘플 수량]
	 *		- field : DDCT_AMT [차감 금액]
	 *		- field : CNSL_SER_NO [상담 일련 번호]
	 *		- field : PRCH_SER_NO [매입 일련 번호]
	 *		- field : LAUNC_DT [출시일자]
	 *		- field : PRCH_MGMT_NO [매입관리번호]
	 *		- field : CNSL_MGMT_NO [상담 관리 번호]
	 *		- field : BATR_CD [배터리 코드]
	 *		- field : PROD_CL [상품 구분]
	 * </pre>
	 */
	public IDataSet fSGnrlJdgTlyRegCmptLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dSGnrlJdgTlyRegCmptLst(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]매입일반감정검수등록목록조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNSL_MGMT_NO [상담관리번호]
	 *	- field : EQP_MDL_CD [단말기모델코드]
	 *	- field : SCRB_MTHD [가입방법]
	 *	- field : PRG_GB [검수등록대상구분]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_TLY_REG_LIST
	 *		- field : CNSL_MGMT_NO [상담 관리 번호]
	 *		- field : EQP_MDL_CD [단말기 모델 코드]
	 *		- field : EQP_MDL_NM [단말기 모델명]
	 *		- field : EQP_SER_NO [단말기 일련 번호]
	 *		- field : CNSL_DT [상담 일자]
	 *		- field : DEAL_CO_NM [상담 거래처명]
	 *		- field : AFFIL_AGN_NM [소속대리점명]
	 *		- field : AGN_ORG_CD [대리점 조직 코드]
	 *		- field : AFFIL_AGN [소속 대리점]
	 *		- field : AFFIL_AGN_ORG_NM [소속 대리점조직명]
	 *		- field : CNSL_DEALCO_CD [상담 거래처코드]
	 *		- field : CNSL_DEALCO_NM [거래처 명]
	 *		- field : EQP_COLOR_CD [단말기 색상 코드]
	 *		- field : ONING_DT [개통 일자]
	 *		- field : EQP_ST [단말기 상태]
	 *		- field : EQP_ST_DTAIL [단말기 상태 세부]
	 *		- field : EQP_ST_TXT [단말기 상태 내용]
	 *		- field : USE_PRD_DDCT_AMT [사용 기간 차감 금액]
	 *		- field : INR_CORR_YN [내부 부식 여부]
	 *		- field : PWR_SYS [전원 시스템]
	 *		- field : CALL_QULT_TEST [통화 품질 테스트]
	 *		- field : DATA_PRFM [DATA 성능]
	 *		- field : SCRN_TEST [화면 테스트]
	 *		- field : FUNC_TEST [기능 테스트]
	 *		- field : LOSS_EQP_YN [분실 단말기 여부]
	 *		- field : RWD_RTN_OBJ_YN [보상 반납 대상 여부]
	 *		- field : RENTP_YN [임대폰 여부]
	 *		- field : WET_YN [침수 여부]
	 *		- field : USIM_EQPCHG_YN [USIM 기변 여부]
	 *		- field : OTHRCO_FORGN_EQP_YN [타사 해외 단말기 여부]
	 *		- field : SUGG [상담의견]
	 *		- field : EVALCNSLR_AUTCT_YN [평가원 인증 여부]
	 *		- field : FULLSET_YN [FULLSET 여부]
	 *		- field : CON_AMT [매입 금액]
	 *		- field : CNSL_AMT [상담 금액]
	 *		- field : PRCH_CL [매입 구]
	 *		- field : CTZ_NO [주민 번호]
	 *		- field : DPSTR [예금주]
	 *		- field : BANK_CD [은행 코드]
	 *		- field : ACCO_NO [계좌 번호]
	 *		- field : TEL_NO [전화 번호]
	 *		- field : ZIPCD [우편번호]
	 *		- field : ADDR [주소]
	 *		- field : ETC_ADDR [기타 주소]
	 *		- field : FS_REG_USER_ID [최초등록사용자ID]
	 *		- field : LS_CHG_USER_ID [최종변경사용자ID]
	 *		- field : USER_NM [최종변경자]
	 *		- field : PRCH_MGMT_NO [매입 관리 번호]
	 *		- field : EVALCNSLR_SUGG [평가원 의견]
	 *		- field : ADJ_AMT [조정 금액]
	 *		- field : FEE_DEDC_YN [요금 공제 여부]
	 *		- field : SVC_NO [서비스 번호]
	 *		- field : SVC_MGMT_NO [서비스 관리 번호]
	 *		- field : IMEI [IMEI]
	 *		- field : ETC_CMPT [기타 구성품]
	 *		- field : PRC_GB [필드4]
	 *		- field : RETUR_DEALCO_CL [반송 거래처 구분]
	 *		- field : CUST_IDEA [고객 의사]
	 *		- field : AMT_RMT_YN [금액 송금 여부]
	 *		- field : UPDATE_COUNT [UPDATE_COUNT]
	 *		- field : POLY_OBJ_YN [정책 대상 여부]
	 *		- field : POLY_OBJ_CD [정책 대상 코드]
	 *		- field : PROGR_ST [진행상태]
	 *		- field : BOX_NO [박스 번호]
	 *		- field : SKN_CL [SKN 구분]
	 *		- field : SKN_EQP_ST [SKN 단말기 상태]
	 *		- field : SKN_JDG_AMT [KN 감정 금액]
	 *		- field : SKN_JDG_CL [SKN 감정 구분]
	 *		- field : SKN_SMPL_JDG_DAMT [SKN 샘플 감정 차액]
	 *		- field : PRE_DC_CL [선 할인 구분]
	 *		- field : SCRN_AFIMG_YN [화면 잔상 여부]
	 *		- field : STRD_INCEN [기준 인센티브]
	 *		- field : FST_RGSTR [최초 등록자]
	 *		- field : ASIANA_CARD_NO [아시아나 카드 번호]
	 *		- field : SCRB_MTHD [가입 방법]
	 *		- field : SELL_GRADE [판매등급]
	 *		- field : CMCT_CO [통신회사]
	 *		- field : DPSTR_ENPT [예금주 암호화]
	 *		- field : TEL_NO_ENPT [전화번호 암호화]
	 *		- field : ETC_ADDR_ENPT [기타 주소 암호화]
	 *		- field : ACCO_NO_ENPT [계좌번호 암호화]
	 *		- field : SVC_NO_ENPT [서비스번호 암호화]
	 *		- field : TLY_DT [검수일자]
	 *		- field : STARD_YN [회수 기준 여부]
	 *		- field : STARD_DD [회수 기준일]
	 *		- field : PRCH_CHG_DAMT_AMT [매입 변경 차액 금액]
	 *		- field : PRCH_CHG_STRD_NOD [매입 변경 기준 일수]
	 *		- field : IN_CONF_DT [입고 확정 일자]
	 * </pre>
	 */
	public IDataSet fSPrchGnrlJdgTlyRegLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dSPrchGnrlJdgTlyRegLst(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]매입일반감정검수등록구성품목록조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNSL_MGMT_NO [상담관리번호]
	 *	- field : EQP_MDL_CD [단말기모델코드]
	 *	- field : SCRB_MTHD [가입방법]
	 *	- field : PRG_GB [검수등록대상구분]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_TLY_REG_CMPT_LIST
	 *		- field : EQP_MDL_CD [단말기 모델 코드]
	 *		- field : OUTS_MCN_CL [주변 기기 구분]
	 *		- field : QTY [수량]
	 *		- field : UPRC [단가]
	 *		- field : UNIT_UPRC [필드1]
	 *		- field : SALE_UPRC [매출 단가]
	 *		- field : NORM_QTY [정상 수량]
	 *		- field : HLD_QTY [보유 수량]
	 *		- field : SKN_SMPL_QTY [SKN 샘플 수량]
	 *		- field : DDCT_AMT [차감 금액]
	 *		- field : CNSL_SER_NO [상담 일련 번호]
	 *		- field : PRCH_SER_NO [매입 일련 번호]
	 *		- field : LAUNC_DT [출시일자]
	 *		- field : PRCH_MGMT_NO [매입관리번호]
	 *		- field : CNSL_MGMT_NO [상담 관리 번호]
	 *		- field : BATR_CD [배터리 코드]
	 *		- field : PROD_CL [상품 구분]
	 * </pre>
	 */
	public IDataSet fSPrchGnrlJdgTlyRegCmptLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dSPrchGnrlJdgTlyRegCmptLst(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]단말기모델정보조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_EQP_INFO_LIST
	 *		- field : EQP_MDL_CD [단말기 모델 코드]
	 *		- field : EQP_MDL_NM [단말기 모델 명]
	 *		- field : MFACT_CD [생산업체 코드]
	 *		- field : LAUNC_DT [출시 일자]
	 *		- field : SETOFF_COFC [상계 계수]
	 *		- field : PREFR_PRC_CL [선호도 가격 구분]
	 *		- field : PRC_RT [가격 비율]
	 *		- field : HLMT_PRC [상한 가격]
	 *		- field : LLMT_PRC [하한 가격]
	 *		- field : SLFCO_CL [자사 구분]
	 *		- field : FIX_PRC_YN [고정 가격 여부]
	 *		- field : LAUNC_YN [출시 여부]
	 *		- field : PROD_CHRTIC_1 [상품 특성 1]
	 *		- field : COMC [통신방식]
	 *		- field : BEFORE_YEAR [기준전년도]
	 * </pre>
	 */
	public IDataSet fSEqpMdlInfo(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dSEqpMdlInfo(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]단말기등급정보조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_EQP_GRADE_LIST
	 *		- field : PRC_GRADE [가격 등급]
	 *		- field : RATE [등급 비율]
	 *		- field : PRC_RT_SEQ [가격 비율 순번]
	 *		- field : STRD_INCEN [기준 인센티브]
	 * </pre>
	 */
	public IDataSet fSEqpMdlGradeInfo(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dSEqpMdlGradeInfo(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]일반검수진행매입상세정보등록</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : EQP_MDL_CD [단말기 모델 코드]
	 *	- field : OUTS_MCN_CL [주변 기기 구분]
	 *	- field : QTY [수량]
	 *	- field : UPRC [단가]
	 *	- field : UNIT_UPRC [필드1]
	 *	- field : SALE_UPRC [매출 단가]
	 *	- field : NORM_QTY [정상 수량]
	 *	- field : HLD_QTY [보유 수량]
	 *	- field : SKN_SMPL_QTY [SKN 샘플 수량]
	 *	- field : DDCT_AMT [차감 금액]
	 *	- field : CNSL_SER_NO [상담 일련 번호]
	 *	- field : PRCH_SER_NO [매입 일련 번호]
	 *	- field : LAUNC_DT [출시일자]
	 *	- field : PRCH_MGMT_NO [매입관리번호]
	 *	- field : CNSL_MGMT_NO [상담 관리 번호]
	 *	- field : BATR_CD [배터리 코드]
	 *	- field : PROD_CL [상품 구분]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fIGnrlTlyProgrTbEpCstPrchsDtl(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dIGnrlTlyProgrTbEpCstPrchsDtl(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]일반검수진행매입관리정보등록</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNSL_MGMT_NO [상담 관리 번호]
	 *	- field : EQP_MDL_CD [단말기 모델 코드]
	 *	- field : EQP_MDL_NM [단말기 모델명]
	 *	- field : EQP_SER_NO [단말기 일련 번호]
	 *	- field : CNSL_DT [상담 일자]
	 *	- field : DEAL_CO_NM [상담 거래처명]
	 *	- field : AGN_ORG_CD [대리점 조직 코드]
	 *	- field : AFFIL_AGN [소속 대리점]
	 *	- field : AFFIL_AGN_NM [소속대리점명]
	 *	- field : CNSL_DEALCO_CD [상담 거래처코드]
	 *	- field : CNSL_DEALCO_NM [거래처 명]
	 *	- field : EQP_COLOR_CD [단말기 색상 코드]
	 *	- field : ONING_DT [개통 일자]
	 *	- field : EQP_ST [단말기 상태]
	 *	- field : EQP_ST_DTAIL [단말기 상태 세부]
	 *	- field : EQP_ST_TXT [단말기 상태 내용]
	 *	- field : USE_PRD_DDCT_AMT [사용 기간 차감 금액]
	 *	- field : INR_CORR_YN [내부 부식 여부]
	 *	- field : PWR_SYS [전원 시스템]
	 *	- field : CALL_QULT_TEST [통화 품질 테스트]
	 *	- field : DATA_PRFM [DATA 성능]
	 *	- field : SCRN_TEST [화면 테스트]
	 *	- field : FUNC_TEST [기능 테스트]
	 *	- field : LOSS_EQP_YN [분실 단말기 여부]
	 *	- field : RWD_RTN_OBJ_YN [보상 반납 대상 여부]
	 *	- field : RENTP_YN [임대폰 여부]
	 *	- field : WET_YN [침수 여부]
	 *	- field : USIM_EQPCHG_YN [USIM 기변 여부]
	 *	- field : OTHRCO_FORGN_EQP_YN [타사 해외 단말기 여부]
	 *	- field : SUGG [상담의견]
	 *	- field : EVALCNSLR_AUTCT_YN [평가원 인증 여부]
	 *	- field : FULLSET_YN [FULLSET 여부]
	 *	- field : CON_AMT [매입 금액]
	 *	- field : CNSL_AMT [상담 금액]
	 *	- field : PRCH_CL [매입 구]
	 *	- field : CTZ_NO [주민 번호]
	 *	- field : DPSTR [예금주]
	 *	- field : BANK_CD [은행 코드]
	 *	- field : ACCO_NO [계좌 번호]
	 *	- field : TEL_NO [전화 번호]
	 *	- field : ZIPCD [우편번호]
	 *	- field : ADDR [주소]
	 *	- field : ETC_ADDR [기타 주소]
	 *	- field : FS_REG_USER_ID [최초등록사용자ID]
	 *	- field : LS_CHG_USER_ID [최종변경사용자ID]
	 *	- field : USER_NM [최종변경자]
	 *	- field : PRCH_MGMT_NO [매입 관리 번호]
	 *	- field : EVALCNSLR_SUGG [평가원 의견]
	 *	- field : ADJ_AMT [조정 금액]
	 *	- field : FEE_DEDC_YN [요금 공제 여부]
	 *	- field : SVC_NO [서비스 번호]
	 *	- field : SVC_MGMT_NO [서비스 관리 번호]
	 *	- field : IMEI [IMEI]
	 *	- field : ETC_CMPT [기타 구성품]
	 *	- field : PRC_GB [필드4]
	 *	- field : RETUR_DEALCO_CL [반송 거래처 구분]
	 *	- field : CUST_IDEA [고객 의사]
	 *	- field : AMT_RMT_YN [금액 송금 여부]
	 *	- field : UPDATE_COUNT [UPDATE_COUNT]
	 *	- field : POLY_OBJ_YN [정책 대상 여부]
	 *	- field : POLY_OBJ_CD [정책 대상 코드]
	 *	- field : PROGR_ST [진행상태]
	 *	- field : BOX_NO [박스 번호]
	 *	- field : SKN_CL [SKN 구분]
	 *	- field : SKN_EQP_ST [SKN 단말기 상태]
	 *	- field : SKN_JDG_AMT [KN 감정 금액]
	 *	- field : SKN_JDG_CL [SKN 감정 구분]
	 *	- field : SKN_SMPL_JDG_DAMT [SKN 샘플 감정 차액]
	 *	- field : PRE_DC_CL [선 할인 구분]
	 *	- field : SCRN_AFIMG_YN [화면 잔상 여부]
	 *	- field : STRD_INCEN [기준 인센티브]
	 *	- field : FST_RGSTR [최초 등록자]
	 *	- field : ASIANA_CARD_NO [아시아나 카드 번호]
	 *	- field : SCRB_MTHD [가입 방법]
	 *	- field : SELL_GRADE [판매등급]
	 *	- field : CMCT_CO [통신회사]
	 *	- field : DPSTR_ENPT [예금주 암호화]
	 *	- field : TEL_NO_ENPT [전화번호 암호화]
	 *	- field : ETC_ADDR_ENPT [기타 주소 암호화]
	 *	- field : ACCO_NO_ENPT [계좌번호 암호화]
	 *	- field : SVC_NO_ENPT [서비스번호 암호화]
	 *	- field : TLY_DT [검수일자]
	 *	- field : PRCH_CHG_DAMT_AMT [매입 변경 차액 금액]
	 *	- field : PRCH_CHG_STRD_NOD [매입 변경 기준 일수]
	 *	- field : IN_CONF_DT [입고 확정 일자]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fIGnrlTlyProgrTbEpCstPrchsMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dIGnrlTlyProgrTbEpCstPrchsMgmt(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]일반검수진행매입상세정보삭제</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : EQP_MDL_CD [단말기 모델 코드]
	 *	- field : OUTS_MCN_CL [주변 기기 구분]
	 *	- field : QTY [수량]
	 *	- field : UPRC [단가]
	 *	- field : UNIT_UPRC [필드1]
	 *	- field : SALE_UPRC [매출 단가]
	 *	- field : NORM_QTY [정상 수량]
	 *	- field : HLD_QTY [보유 수량]
	 *	- field : SKN_SMPL_QTY [SKN 샘플 수량]
	 *	- field : DDCT_AMT [차감 금액]
	 *	- field : CNSL_SER_NO [상담 일련 번호]
	 *	- field : PRCH_SER_NO [매입 일련 번호]
	 *	- field : LAUNC_DT [출시일자]
	 *	- field : PRCH_MGMT_NO [매입관리번호]
	 *	- field : CNSL_MGMT_NO [상담 관리 번호]
	 *	- field : BATR_CD [배터리 코드]
	 *	- field : PROD_CL [상품 구분]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fDGnrlTlyProgrTbEpCstPrchsDtl(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dDGnrlTlyProgrTbEpCstPrchsDtl(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]일반검수완료대상상담관리정보수정</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNSL_MGMT_NO [상담 관리 번호]
	 *	- field : EQP_MDL_CD [단말기 모델 코드]
	 *	- field : EQP_MDL_NM [단말기 모델명]
	 *	- field : EQP_SER_NO [단말기 일련 번호]
	 *	- field : CNSL_DT [상담 일자]
	 *	- field : DEAL_CO_NM [상담 거래처명]
	 *	- field : AGN_ORG_CD [대리점 조직 코드]
	 *	- field : AFFIL_AGN [소속 대리점]
	 *	- field : AFFIL_AGN_NM [소속대리점명]
	 *	- field : CNSL_DEALCO_CD [상담 거래처코드]
	 *	- field : CNSL_DEALCO_NM [거래처 명]
	 *	- field : EQP_COLOR_CD [단말기 색상 코드]
	 *	- field : ONING_DT [개통 일자]
	 *	- field : EQP_ST [단말기 상태]
	 *	- field : EQP_ST_DTAIL [단말기 상태 세부]
	 *	- field : EQP_ST_TXT [단말기 상태 내용]
	 *	- field : USE_PRD_DDCT_AMT [사용 기간 차감 금액]
	 *	- field : INR_CORR_YN [내부 부식 여부]
	 *	- field : PWR_SYS [전원 시스템]
	 *	- field : CALL_QULT_TEST [통화 품질 테스트]
	 *	- field : DATA_PRFM [DATA 성능]
	 *	- field : SCRN_TEST [화면 테스트]
	 *	- field : FUNC_TEST [기능 테스트]
	 *	- field : LOSS_EQP_YN [분실 단말기 여부]
	 *	- field : RWD_RTN_OBJ_YN [보상 반납 대상 여부]
	 *	- field : RENTP_YN [임대폰 여부]
	 *	- field : WET_YN [침수 여부]
	 *	- field : USIM_EQPCHG_YN [USIM 기변 여부]
	 *	- field : OTHRCO_FORGN_EQP_YN [타사 해외 단말기 여부]
	 *	- field : SUGG [상담의견]
	 *	- field : EVALCNSLR_AUTCT_YN [평가원 인증 여부]
	 *	- field : FULLSET_YN [FULLSET 여부]
	 *	- field : CON_AMT [매입 금액]
	 *	- field : CNSL_AMT [상담 금액]
	 *	- field : PRCH_CL [매입 구]
	 *	- field : CTZ_NO [주민 번호]
	 *	- field : DPSTR [예금주]
	 *	- field : BANK_CD [은행 코드]
	 *	- field : ACCO_NO [계좌 번호]
	 *	- field : TEL_NO [전화 번호]
	 *	- field : ZIPCD [우편번호]
	 *	- field : ADDR [주소]
	 *	- field : ETC_ADDR [기타 주소]
	 *	- field : FS_REG_USER_ID [최초등록사용자ID]
	 *	- field : LS_CHG_USER_ID [최종변경사용자ID]
	 *	- field : USER_NM [최종변경자]
	 *	- field : PRCH_MGMT_NO [매입 관리 번호]
	 *	- field : EVALCNSLR_SUGG [평가원 의견]
	 *	- field : ADJ_AMT [조정 금액]
	 *	- field : FEE_DEDC_YN [요금 공제 여부]
	 *	- field : SVC_NO [서비스 번호]
	 *	- field : SVC_MGMT_NO [서비스 관리 번호]
	 *	- field : IMEI [IMEI]
	 *	- field : ETC_CMPT [기타 구성품]
	 *	- field : PRC_GB [필드4]
	 *	- field : RETUR_DEALCO_CL [반송 거래처 구분]
	 *	- field : CUST_IDEA [고객 의사]
	 *	- field : AMT_RMT_YN [금액 송금 여부]
	 *	- field : UPDATE_COUNT [UPDATE_COUNT]
	 *	- field : POLY_OBJ_YN [정책 대상 여부]
	 *	- field : POLY_OBJ_CD [정책 대상 코드]
	 *	- field : PROGR_ST [진행상태]
	 *	- field : BOX_NO [박스 번호]
	 *	- field : SKN_CL [SKN 구분]
	 *	- field : SKN_EQP_ST [SKN 단말기 상태]
	 *	- field : SKN_JDG_AMT [KN 감정 금액]
	 *	- field : SKN_JDG_CL [SKN 감정 구분]
	 *	- field : SKN_SMPL_JDG_DAMT [SKN 샘플 감정 차액]
	 *	- field : PRE_DC_CL [선 할인 구분]
	 *	- field : SCRN_AFIMG_YN [화면 잔상 여부]
	 *	- field : STRD_INCEN [기준 인센티브]
	 *	- field : FST_RGSTR [최초 등록자]
	 *	- field : ASIANA_CARD_NO [아시아나 카드 번호]
	 *	- field : SCRB_MTHD [가입 방법]
	 *	- field : SELL_GRADE [판매등급]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fUGnrlTlyFnshTbEpCstConsultMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dUGnrlTlyFnshTbEpCstConsultMgmt(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]일반검수완료대상매입관리정보수정</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNSL_MGMT_NO [상담 관리 번호]
	 *	- field : EQP_MDL_CD [단말기 모델 코드]
	 *	- field : EQP_MDL_NM [단말기 모델명]
	 *	- field : EQP_SER_NO [단말기 일련 번호]
	 *	- field : CNSL_DT [상담 일자]
	 *	- field : DEAL_CO_NM [상담 거래처명]
	 *	- field : AGN_ORG_CD [대리점 조직 코드]
	 *	- field : AFFIL_AGN [소속 대리점]
	 *	- field : AFFIL_AGN_NM [소속대리점명]
	 *	- field : CNSL_DEALCO_CD [상담 거래처코드]
	 *	- field : CNSL_DEALCO_NM [거래처 명]
	 *	- field : EQP_COLOR_CD [단말기 색상 코드]
	 *	- field : ONING_DT [개통 일자]
	 *	- field : EQP_ST [단말기 상태]
	 *	- field : EQP_ST_DTAIL [단말기 상태 세부]
	 *	- field : EQP_ST_TXT [단말기 상태 내용]
	 *	- field : USE_PRD_DDCT_AMT [사용 기간 차감 금액]
	 *	- field : INR_CORR_YN [내부 부식 여부]
	 *	- field : PWR_SYS [전원 시스템]
	 *	- field : CALL_QULT_TEST [통화 품질 테스트]
	 *	- field : DATA_PRFM [DATA 성능]
	 *	- field : SCRN_TEST [화면 테스트]
	 *	- field : FUNC_TEST [기능 테스트]
	 *	- field : LOSS_EQP_YN [분실 단말기 여부]
	 *	- field : RWD_RTN_OBJ_YN [보상 반납 대상 여부]
	 *	- field : RENTP_YN [임대폰 여부]
	 *	- field : WET_YN [침수 여부]
	 *	- field : USIM_EQPCHG_YN [USIM 기변 여부]
	 *	- field : OTHRCO_FORGN_EQP_YN [타사 해외 단말기 여부]
	 *	- field : SUGG [상담의견]
	 *	- field : EVALCNSLR_AUTCT_YN [평가원 인증 여부]
	 *	- field : FULLSET_YN [FULLSET 여부]
	 *	- field : CON_AMT [매입 금액]
	 *	- field : CNSL_AMT [상담 금액]
	 *	- field : PRCH_CL [매입 구]
	 *	- field : CTZ_NO [주민 번호]
	 *	- field : DPSTR [예금주]
	 *	- field : BANK_CD [은행 코드]
	 *	- field : ACCO_NO [계좌 번호]
	 *	- field : TEL_NO [전화 번호]
	 *	- field : ZIPCD [우편번호]
	 *	- field : ADDR [주소]
	 *	- field : ETC_ADDR [기타 주소]
	 *	- field : FS_REG_USER_ID [최초등록사용자ID]
	 *	- field : LS_CHG_USER_ID [최종변경사용자ID]
	 *	- field : USER_NM [최종변경자]
	 *	- field : PRCH_MGMT_NO [매입 관리 번호]
	 *	- field : EVALCNSLR_SUGG [평가원 의견]
	 *	- field : ADJ_AMT [조정 금액]
	 *	- field : FEE_DEDC_YN [요금 공제 여부]
	 *	- field : SVC_NO [서비스 번호]
	 *	- field : SVC_MGMT_NO [서비스 관리 번호]
	 *	- field : IMEI [IMEI]
	 *	- field : ETC_CMPT [기타 구성품]
	 *	- field : PRC_GB [필드4]
	 *	- field : RETUR_DEALCO_CL [반송 거래처 구분]
	 *	- field : CUST_IDEA [고객 의사]
	 *	- field : AMT_RMT_YN [금액 송금 여부]
	 *	- field : UPDATE_COUNT [UPDATE_COUNT]
	 *	- field : POLY_OBJ_YN [정책 대상 여부]
	 *	- field : POLY_OBJ_CD [정책 대상 코드]
	 *	- field : PROGR_ST [진행상태]
	 *	- field : BOX_NO [박스 번호]
	 *	- field : SKN_CL [SKN 구분]
	 *	- field : SKN_EQP_ST [SKN 단말기 상태]
	 *	- field : SKN_JDG_AMT [KN 감정 금액]
	 *	- field : SKN_JDG_CL [SKN 감정 구분]
	 *	- field : SKN_SMPL_JDG_DAMT [SKN 샘플 감정 차액]
	 *	- field : PRE_DC_CL [선 할인 구분]
	 *	- field : SCRN_AFIMG_YN [화면 잔상 여부]
	 *	- field : STRD_INCEN [기준 인센티브]
	 *	- field : FST_RGSTR [최초 등록자]
	 *	- field : ASIANA_CARD_NO [아시아나 카드 번호]
	 *	- field : SCRB_MTHD [가입 방법]
	 *	- field : SELL_GRADE [판매등급]
	 *	- field : CMCT_CO [통신회사]
	 *	- field : DPSTR_ENPT [예금주 암호화]
	 *	- field : TEL_NO_ENPT [전화번호 암호화]
	 *	- field : ETC_ADDR_ENPT [기타 주소 암호화]
	 *	- field : ACCO_NO_ENPT [계좌번호 암호화]
	 *	- field : SVC_NO_ENPT [서비스번호 암호화]
	 *	- field : PRCH_CHG_DAMT_AMT [매입 변경 차액 금액]
	 *	- field : PRCH_CHG_STRD_NOD [매입 변경 기준 일수]
	 *	- field : IN_CONF_DT [입고 확정 일자]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fUGnrlTlyFnshTbEpCstPrchsMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dUGnrlTlyFnshTbEpCstPrchsMgmt(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]일반검수진행상담상태수정</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNSL_MGMT_NO [상담 관리 번호]
	 *	- field : EQP_MDL_CD [단말기 모델 코드]
	 *	- field : EQP_MDL_NM [단말기 모델명]
	 *	- field : EQP_SER_NO [단말기 일련 번호]
	 *	- field : CNSL_DT [상담 일자]
	 *	- field : DEAL_CO_NM [상담 거래처명]
	 *	- field : AGN_ORG_CD [대리점 조직 코드]
	 *	- field : AFFIL_AGN [소속 대리점]
	 *	- field : AFFIL_AGN_NM [소속대리점명]
	 *	- field : CNSL_DEALCO_CD [상담 거래처코드]
	 *	- field : CNSL_DEALCO_NM [거래처 명]
	 *	- field : EQP_COLOR_CD [단말기 색상 코드]
	 *	- field : ONING_DT [개통 일자]
	 *	- field : EQP_ST [단말기 상태]
	 *	- field : EQP_ST_DTAIL [단말기 상태 세부]
	 *	- field : EQP_ST_TXT [단말기 상태 내용]
	 *	- field : USE_PRD_DDCT_AMT [사용 기간 차감 금액]
	 *	- field : INR_CORR_YN [내부 부식 여부]
	 *	- field : PWR_SYS [전원 시스템]
	 *	- field : CALL_QULT_TEST [통화 품질 테스트]
	 *	- field : DATA_PRFM [DATA 성능]
	 *	- field : SCRN_TEST [화면 테스트]
	 *	- field : FUNC_TEST [기능 테스트]
	 *	- field : LOSS_EQP_YN [분실 단말기 여부]
	 *	- field : RWD_RTN_OBJ_YN [보상 반납 대상 여부]
	 *	- field : RENTP_YN [임대폰 여부]
	 *	- field : WET_YN [침수 여부]
	 *	- field : USIM_EQPCHG_YN [USIM 기변 여부]
	 *	- field : OTHRCO_FORGN_EQP_YN [타사 해외 단말기 여부]
	 *	- field : SUGG [상담의견]
	 *	- field : EVALCNSLR_AUTCT_YN [평가원 인증 여부]
	 *	- field : FULLSET_YN [FULLSET 여부]
	 *	- field : CON_AMT [매입 금액]
	 *	- field : CNSL_AMT [상담 금액]
	 *	- field : PRCH_CL [매입 구]
	 *	- field : CTZ_NO [주민 번호]
	 *	- field : DPSTR [예금주]
	 *	- field : BANK_CD [은행 코드]
	 *	- field : ACCO_NO [계좌 번호]
	 *	- field : TEL_NO [전화 번호]
	 *	- field : ZIPCD [우편번호]
	 *	- field : ADDR [주소]
	 *	- field : ETC_ADDR [기타 주소]
	 *	- field : FS_REG_USER_ID [최초등록사용자ID]
	 *	- field : LS_CHG_USER_ID [최종변경사용자ID]
	 *	- field : USER_NM [최종변경자]
	 *	- field : PRCH_MGMT_NO [매입 관리 번호]
	 *	- field : EVALCNSLR_SUGG [평가원 의견]
	 *	- field : ADJ_AMT [조정 금액]
	 *	- field : FEE_DEDC_YN [요금 공제 여부]
	 *	- field : SVC_NO [서비스 번호]
	 *	- field : SVC_MGMT_NO [서비스 관리 번호]
	 *	- field : IMEI [IMEI]
	 *	- field : ETC_CMPT [기타 구성품]
	 *	- field : PRC_GB [필드4]
	 *	- field : RETUR_DEALCO_CL [반송 거래처 구분]
	 *	- field : CUST_IDEA [고객 의사]
	 *	- field : AMT_RMT_YN [금액 송금 여부]
	 *	- field : UPDATE_COUNT [UPDATE_COUNT]
	 *	- field : POLY_OBJ_YN [정책 대상 여부]
	 *	- field : POLY_OBJ_CD [정책 대상 코드]
	 *	- field : PROGR_ST [진행상태]
	 *	- field : BOX_NO [박스 번호]
	 *	- field : SKN_CL [SKN 구분]
	 *	- field : SKN_EQP_ST [SKN 단말기 상태]
	 *	- field : SKN_JDG_AMT [KN 감정 금액]
	 *	- field : SKN_JDG_CL [SKN 감정 구분]
	 *	- field : SKN_SMPL_JDG_DAMT [SKN 샘플 감정 차액]
	 *	- field : PRE_DC_CL [선 할인 구분]
	 *	- field : SCRN_AFIMG_YN [화면 잔상 여부]
	 *	- field : STRD_INCEN [기준 인센티브]
	 *	- field : FST_RGSTR [최초 등록자]
	 *	- field : ASIANA_CARD_NO [아시아나 카드 번호]
	 *	- field : SCRB_MTHD [가입 방법]
	 *	- field : SELL_GRADE [판매등급]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fUGnrlTlyProgrConsultSt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dUGnrlTlyProgrConsultSt(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]일반검수진행상담관리정보수정</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNSL_MGMT_NO [상담 관리 번호]
	 *	- field : EQP_MDL_CD [단말기 모델 코드]
	 *	- field : EQP_MDL_NM [단말기 모델명]
	 *	- field : EQP_SER_NO [단말기 일련 번호]
	 *	- field : CNSL_DT [상담 일자]
	 *	- field : DEAL_CO_NM [상담 거래처명]
	 *	- field : AGN_ORG_CD [대리점 조직 코드]
	 *	- field : AFFIL_AGN [소속 대리점]
	 *	- field : AFFIL_AGN_NM [소속대리점명]
	 *	- field : CNSL_DEALCO_CD [상담 거래처코드]
	 *	- field : CNSL_DEALCO_NM [거래처 명]
	 *	- field : EQP_COLOR_CD [단말기 색상 코드]
	 *	- field : ONING_DT [개통 일자]
	 *	- field : EQP_ST [단말기 상태]
	 *	- field : EQP_ST_DTAIL [단말기 상태 세부]
	 *	- field : EQP_ST_TXT [단말기 상태 내용]
	 *	- field : USE_PRD_DDCT_AMT [사용 기간 차감 금액]
	 *	- field : INR_CORR_YN [내부 부식 여부]
	 *	- field : PWR_SYS [전원 시스템]
	 *	- field : CALL_QULT_TEST [통화 품질 테스트]
	 *	- field : DATA_PRFM [DATA 성능]
	 *	- field : SCRN_TEST [화면 테스트]
	 *	- field : FUNC_TEST [기능 테스트]
	 *	- field : LOSS_EQP_YN [분실 단말기 여부]
	 *	- field : RWD_RTN_OBJ_YN [보상 반납 대상 여부]
	 *	- field : RENTP_YN [임대폰 여부]
	 *	- field : WET_YN [침수 여부]
	 *	- field : USIM_EQPCHG_YN [USIM 기변 여부]
	 *	- field : OTHRCO_FORGN_EQP_YN [타사 해외 단말기 여부]
	 *	- field : SUGG [상담의견]
	 *	- field : EVALCNSLR_AUTCT_YN [평가원 인증 여부]
	 *	- field : FULLSET_YN [FULLSET 여부]
	 *	- field : CON_AMT [매입 금액]
	 *	- field : CNSL_AMT [상담 금액]
	 *	- field : PRCH_CL [매입 구]
	 *	- field : CTZ_NO [주민 번호]
	 *	- field : DPSTR [예금주]
	 *	- field : BANK_CD [은행 코드]
	 *	- field : ACCO_NO [계좌 번호]
	 *	- field : TEL_NO [전화 번호]
	 *	- field : ZIPCD [우편번호]
	 *	- field : ADDR [주소]
	 *	- field : ETC_ADDR [기타 주소]
	 *	- field : FS_REG_USER_ID [최초등록사용자ID]
	 *	- field : LS_CHG_USER_ID [최종변경사용자ID]
	 *	- field : USER_NM [최종변경자]
	 *	- field : PRCH_MGMT_NO [매입 관리 번호]
	 *	- field : EVALCNSLR_SUGG [평가원 의견]
	 *	- field : ADJ_AMT [조정 금액]
	 *	- field : FEE_DEDC_YN [요금 공제 여부]
	 *	- field : SVC_NO [서비스 번호]
	 *	- field : SVC_MGMT_NO [서비스 관리 번호]
	 *	- field : IMEI [IMEI]
	 *	- field : ETC_CMPT [기타 구성품]
	 *	- field : PRC_GB [필드4]
	 *	- field : RETUR_DEALCO_CL [반송 거래처 구분]
	 *	- field : CUST_IDEA [고객 의사]
	 *	- field : AMT_RMT_YN [금액 송금 여부]
	 *	- field : UPDATE_COUNT [UPDATE_COUNT]
	 *	- field : POLY_OBJ_YN [정책 대상 여부]
	 *	- field : POLY_OBJ_CD [정책 대상 코드]
	 *	- field : PROGR_ST [진행상태]
	 *	- field : BOX_NO [박스 번호]
	 *	- field : SKN_CL [SKN 구분]
	 *	- field : SKN_EQP_ST [SKN 단말기 상태]
	 *	- field : SKN_JDG_AMT [KN 감정 금액]
	 *	- field : SKN_JDG_CL [SKN 감정 구분]
	 *	- field : SKN_SMPL_JDG_DAMT [SKN 샘플 감정 차액]
	 *	- field : PRE_DC_CL [선 할인 구분]
	 *	- field : SCRN_AFIMG_YN [화면 잔상 여부]
	 *	- field : STRD_INCEN [기준 인센티브]
	 *	- field : FST_RGSTR [최초 등록자]
	 *	- field : ASIANA_CARD_NO [아시아나 카드 번호]
	 *	- field : SCRB_MTHD [가입 방법]
	 *	- field : SELL_GRADE [판매등급]
	 *	- field : DPSTR_ENPT [예금주 암호화]
	 *	- field : TEL_NO_ENPT [전화번호 암호화]
	 *	- field : ETC_ADDR_ENPT [기타 주소 암호화]
	 *	- field : ACCO_NO_ENPT [계좌번호 암호화]
	 *	- field : SVC_NO_ENPT [서비스번호 암호화]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fUGnrlTlyProgrTbEpCstConsultMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dUGnrlTlyProgrTbEpCstConsultMgmt(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]일반검수진행대상매입관리정보수정</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNSL_MGMT_NO [상담 관리 번호]
	 *	- field : EQP_MDL_CD [단말기 모델 코드]
	 *	- field : EQP_MDL_NM [단말기 모델명]
	 *	- field : EQP_SER_NO [단말기 일련 번호]
	 *	- field : CNSL_DT [상담 일자]
	 *	- field : DEAL_CO_NM [상담 거래처명]
	 *	- field : AGN_ORG_CD [대리점 조직 코드]
	 *	- field : AFFIL_AGN [소속 대리점]
	 *	- field : AFFIL_AGN_NM [소속대리점명]
	 *	- field : CNSL_DEALCO_CD [상담 거래처코드]
	 *	- field : CNSL_DEALCO_NM [거래처 명]
	 *	- field : EQP_COLOR_CD [단말기 색상 코드]
	 *	- field : ONING_DT [개통 일자]
	 *	- field : EQP_ST [단말기 상태]
	 *	- field : EQP_ST_DTAIL [단말기 상태 세부]
	 *	- field : EQP_ST_TXT [단말기 상태 내용]
	 *	- field : USE_PRD_DDCT_AMT [사용 기간 차감 금액]
	 *	- field : INR_CORR_YN [내부 부식 여부]
	 *	- field : PWR_SYS [전원 시스템]
	 *	- field : CALL_QULT_TEST [통화 품질 테스트]
	 *	- field : DATA_PRFM [DATA 성능]
	 *	- field : SCRN_TEST [화면 테스트]
	 *	- field : FUNC_TEST [기능 테스트]
	 *	- field : LOSS_EQP_YN [분실 단말기 여부]
	 *	- field : RWD_RTN_OBJ_YN [보상 반납 대상 여부]
	 *	- field : RENTP_YN [임대폰 여부]
	 *	- field : WET_YN [침수 여부]
	 *	- field : USIM_EQPCHG_YN [USIM 기변 여부]
	 *	- field : OTHRCO_FORGN_EQP_YN [타사 해외 단말기 여부]
	 *	- field : SUGG [상담의견]
	 *	- field : EVALCNSLR_AUTCT_YN [평가원 인증 여부]
	 *	- field : FULLSET_YN [FULLSET 여부]
	 *	- field : CON_AMT [매입 금액]
	 *	- field : CNSL_AMT [상담 금액]
	 *	- field : PRCH_CL [매입 구]
	 *	- field : CTZ_NO [주민 번호]
	 *	- field : DPSTR [예금주]
	 *	- field : BANK_CD [은행 코드]
	 *	- field : ACCO_NO [계좌 번호]
	 *	- field : TEL_NO [전화 번호]
	 *	- field : ZIPCD [우편번호]
	 *	- field : ADDR [주소]
	 *	- field : ETC_ADDR [기타 주소]
	 *	- field : FS_REG_USER_ID [최초등록사용자ID]
	 *	- field : LS_CHG_USER_ID [최종변경사용자ID]
	 *	- field : USER_NM [최종변경자]
	 *	- field : PRCH_MGMT_NO [매입 관리 번호]
	 *	- field : EVALCNSLR_SUGG [평가원 의견]
	 *	- field : ADJ_AMT [조정 금액]
	 *	- field : FEE_DEDC_YN [요금 공제 여부]
	 *	- field : SVC_NO [서비스 번호]
	 *	- field : SVC_MGMT_NO [서비스 관리 번호]
	 *	- field : IMEI [IMEI]
	 *	- field : ETC_CMPT [기타 구성품]
	 *	- field : PRC_GB [필드4]
	 *	- field : RETUR_DEALCO_CL [반송 거래처 구분]
	 *	- field : CUST_IDEA [고객 의사]
	 *	- field : AMT_RMT_YN [금액 송금 여부]
	 *	- field : UPDATE_COUNT [UPDATE_COUNT]
	 *	- field : POLY_OBJ_YN [정책 대상 여부]
	 *	- field : POLY_OBJ_CD [정책 대상 코드]
	 *	- field : PROGR_ST [진행상태]
	 *	- field : BOX_NO [박스 번호]
	 *	- field : SKN_CL [SKN 구분]
	 *	- field : SKN_EQP_ST [SKN 단말기 상태]
	 *	- field : SKN_JDG_AMT [KN 감정 금액]
	 *	- field : SKN_JDG_CL [SKN 감정 구분]
	 *	- field : SKN_SMPL_JDG_DAMT [SKN 샘플 감정 차액]
	 *	- field : PRE_DC_CL [선 할인 구분]
	 *	- field : SCRN_AFIMG_YN [화면 잔상 여부]
	 *	- field : STRD_INCEN [기준 인센티브]
	 *	- field : FST_RGSTR [최초 등록자]
	 *	- field : ASIANA_CARD_NO [아시아나 카드 번호]
	 *	- field : SCRB_MTHD [가입 방법]
	 *	- field : SELL_GRADE [판매등급]
	 *	- field : CMCT_CO [통신회사]
	 *	- field : DPSTR_ENPT [예금주 암호화]
	 *	- field : TEL_NO_ENPT [전화번호 암호화]
	 *	- field : ETC_ADDR_ENPT [기타 주소 암호화]
	 *	- field : ACCO_NO_ENPT [계좌번호 암호화]
	 *	- field : SVC_NO_ENPT [서비스번호 암호화]
	 *	- field : PRCH_CHG_DAMT_AMT [매입 변경 차액 금액]
	 *	- field : PRCH_CHG_STRD_NOD [매입 변경 기준 일수]
	 *	- field : IN_CONF_DT [입고 확정 일자]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fUGnrlTlyProgrTbEpCstPrchsMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dUGnrlTlyProgrTbEpCstPrchsMgmt(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]일반검수진행대상상담수정정보조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-08 10:41:10
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fSGnrlTlyProgrConsultCount(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dSGnrlTlyProgrConsultCount(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]매입관리번호조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-08 13:15:56
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fSPrchMgmtNoSeq(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dSPrchMgmtNoSeq(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]매입관리건수조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- field : PRCH_MGMT_COUNT [매입관리건수조회]
	 * </pre>
	 */
	public IDataSet fSTbEpCstPrchsMgmtCount(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dSTbEpCstPrchsMgmtCount(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]재감정검수등록목록조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNSL_MGMT_NO [상담관리번호]
	 *	- field : EQP_MDL_CD [단말기모델코드]
	 *	- field : SCRB_MTHD [가입방법]
	 *	- field : PRG_GB [검수등록대상구분]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_TLY_REG_LIST
	 *		- field : CNSL_MGMT_NO [상담 관리 번호]
	 *		- field : EQP_MDL_CD [단말기 모델 코드]
	 *		- field : EQP_MDL_NM [단말기 모델명]
	 *		- field : EQP_SER_NO [상담 관리 일련 번호]
	 *		- field : CNSL_DT [상담 일자]
	 *		- field : AFFIL_AGN_NM [소속 대리점명]
	 *		- field : AGN_ORG_CD [대리점 조직 코드]
	 *		- field : AFFIL_AGN [소속 대리점]
	 *		- field : AFFIL_AGN_ORG_NM [소속 대리점조직명]
	 *		- field : CNSL_DEALCO_CD [상담 거래처 코드]
	 *		- field : CNSL_DEALCO_NM [거래처 명]
	 *		- field : EQP_COLOR_CD [단말기 색상 코드]
	 *		- field : ONING_DT [개통 일자]
	 *		- field : EQP_ST [단말기 상태]
	 *		- field : EQP_ST_DTAIL [단말기 상태 세부]
	 *		- field : EQP_ST_TXT [단말기 상태]
	 *		- field : USE_PRD_DDCT_AMT [사용 기간 차감 금액]
	 *		- field : INR_CORR_YN [내부 부식 여부]
	 *		- field : PWR_SYS [전원 시스템]
	 *		- field : CALL_QULT_TEST [통화 품질 테스트]
	 *		- field : DATA_PRFM [DATA 성능]
	 *		- field : SCRN_TEST [화면 테스트]
	 *		- field : FUNC_TEST [기능 테스트]
	 *		- field : LOSS_EQP_YN [분실 단말기 여부]
	 *		- field : RWD_RTN_OBJ_YN [보상 반납 대상 여부]
	 *		- field : RENTP_YN [임대폰 여부]
	 *		- field : WET_YN [침수 여부]
	 *		- field : USIM_EQPCHG_YN [USIM 기변 여부]
	 *		- field : OTHRCO_FORGN_EQP_YN [타사 해외 단말기 여부]
	 *		- field : SUGG [의견]
	 *		- field : EVALCNSLR_AUTCT_YN [평가원 인증 여부]
	 *		- field : FULLSET_YN [FULLSET 여부]
	 *		- field : CNSL_AMT [매입 금액]
	 *		- field : PRCH_CL [매입 구분]
	 *		- field : DPSTR [예금주]
	 *		- field : BANK_CD [은행 코드]
	 *		- field : ACCO_NO [계좌 번호]
	 *		- field : TEL_NO [전화 번호]
	 *		- field : ZIPCD [우편번호]
	 *		- field : ADDR [주소]
	 *		- field : ETC_ADDR [기타 주소]
	 *		- field : FS_REG_USER_ID [최초 등록 사용자 ID]
	 *		- field : LS_CHG_USER_ID [최종 변경 사용자 ID]
	 *		- field : USER_NM [최종변경자]
	 *		- field : PRCH_MGMT_NO [매입 관리 번호]
	 *		- field : EVALCNSLR_SUGG [평가원 의견]
	 *		- field : ADJ_AMT [조정 금액]
	 *		- field : FEE_DEDC_YN [요금 공제 여부]
	 *		- field : SVC_NO [서비스 번호]
	 *		- field : SVC_MGMT_NO [서비스 관리 번호]
	 *		- field : IMEI [IMEI]
	 *		- field : ETC_CMPT [기타 구성품]
	 *		- field : PRG_GB [검수등록대상구분]
	 *		- field : CUST_IDEA [고객 의사]
	 *		- field : AMT_RMT_YN [금액 송금 여부]
	 *		- field : UPDATE_COUNT [UPDATE COUNT]
	 *		- field : POLY_OBJ_YN [정책 대상 여부]
	 *		- field : POLY_OBJ_CD [정책대상코드]
	 *		- field : PROGR_ST [진행 상태]
	 *		- field : BOX_NO [박스 번호]
	 *		- field : RETUR_DEALCO_CL [반송 거래처 구분]
	 *		- field : PRE_DC_CL [선 할인 구분]
	 *		- field : SKN_CL [SKN 구분]
	 *		- field : SKN_EQP_ST [SKN 단말기 상태]
	 *		- field : SKN_JDG_AMT [SKN 감정 금액]
	 *		- field : EQP_PRC [단말기 가격]
	 *		- field : STRD_INCEN [준 인센티브]
	 *		- field : STRD_INCEN_SCOP_AMT [기준 인센티브 범위 금액]
	 *		- field : UKEY_STRD_INCEN [기준 인센티브]
	 *		- field : SKN_JDG_CL [SKN 감정 구분]
	 *		- field : SKN_JDG_CL_SUB [SKN 감정 구분 서브]
	 *		- field : SKN_SMPL_JDG_DAMT [SKN 샘플 감정 차액]
	 *		- field : AGN_DDCT_YN [대리점 차감 여부]
	 *		- field : SCRN_AFIMG_YN [화면 잔상 여부]
	 *		- field : FST_RGSTR [최초 등록자]
	 *		- field : ASIANA_CARD_NO [아시아나 카드 번호]
	 *		- field : SALE_EQP_ST [판매등급]
	 *		- field : CMCT_CO [통신회사]
	 *		- field : DPSTR_ENPT [예금주 암호화]
	 *		- field : TEL_NO_ENPT [전화번호 암호화]
	 *		- field : ETC_ADDR_ENPT [기타 주소 암호화]
	 *		- field : ACCO_NO_ENPT [계좌번호 암호화]
	 *		- field : SVC_NO_ENPT [서비스번호 암호화]
	 *		- field : TLY_DT [검수 일자]
	 *		- field : STARD_YN [회수 기준 여부]
	 *		- field : STARD_DD [회수 기준일]
	 *		- field : PRCH_CHG_DAMT_AMT [매입 변경 차액 금액]
	 *		- field : PRCH_CHG_STRD_NOD [매입 변경 기준 일수]
	 *		- field : IN_CONF_DT [입고 확정 일자]
	 * </pre>
	 */
	public IDataSet fSRpetJdgTlyRegLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dSRpetJdgTlyRegLst(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]재감정검수등록구성품목록조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNSL_MGMT_NO [상담관리번호]
	 *	- field : EQP_MDL_CD [단말기모델코드]
	 *	- field : SCRB_MTHD [가입방법]
	 *	- field : PRG_GB [검수등록대상구분]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_TLY_REG_CMPT_LIST
	 *		- field : EQP_MDL_CD [단말기 모델 코드]
	 *		- field : OUTS_MCN_CL [주변 기기 구분]
	 *		- field : QTY [수량]
	 *		- field : UPRC [단가]
	 *		- field : UNIT_UPRC [필드1]
	 *		- field : SALE_UPRC [매출 단가]
	 *		- field : NORM_QTY [정상 수량]
	 *		- field : HLD_QTY [보유 수량]
	 *		- field : SKN_SMPL_QTY [SKN 샘플 수량]
	 *		- field : DDCT_AMT [차감 금액]
	 *		- field : CNSL_SER_NO [상담 일련 번호]
	 *		- field : PRCH_SER_NO [매입 일련 번호]
	 *		- field : LAUNC_DT [출시일자]
	 *		- field : PRCH_MGMT_NO [매입관리번호]
	 *		- field : CNSL_MGMT_NO [상담 관리 번호]
	 *		- field : BATR_CD [배터리 코드]
	 *		- field : PROD_CL [상품 구분]
	 * </pre>
	 */
	public IDataSet fSRpetJdgTlyRegCmptLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dSRpetJdgTlyRegCmptLst(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]매입재감정검수등록목록조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNSL_MGMT_NO [상담관리번호]
	 *	- field : EQP_MDL_CD [단말기모델코드]
	 *	- field : SCRB_MTHD [가입방법]
	 *	- field : PRG_GB [검수등록대상구분]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_TLY_REG_LIST
	 *		- field : CNSL_MGMT_NO [상담 관리 번호]
	 *		- field : EQP_MDL_CD [단말기 모델 코드]
	 *		- field : EQP_MDL_NM [단말기 모델명]
	 *		- field : EQP_SER_NO [상담 관리 일련 번호]
	 *		- field : CNSL_DT [상담 일자]
	 *		- field : AFFIL_AGN_NM [소속 대리점명]
	 *		- field : AGN_ORG_CD [대리점 조직 코드]
	 *		- field : AFFIL_AGN [소속 대리점]
	 *		- field : AFFIL_AGN_ORG_NM [소속 대리점조직명]
	 *		- field : CNSL_DEALCO_CD [상담 거래처 코드]
	 *		- field : CNSL_DEALCO_NM [거래처 명]
	 *		- field : EQP_COLOR_CD [단말기 색상 코드]
	 *		- field : ONING_DT [개통 일자]
	 *		- field : EQP_ST [단말기 상태]
	 *		- field : EQP_ST_DTAIL [단말기 상태 세부]
	 *		- field : EQP_ST_TXT [단말기 상태]
	 *		- field : USE_PRD_DDCT_AMT [사용 기간 차감 금액]
	 *		- field : INR_CORR_YN [내부 부식 여부]
	 *		- field : PWR_SYS [전원 시스템]
	 *		- field : CALL_QULT_TEST [통화 품질 테스트]
	 *		- field : DATA_PRFM [DATA 성능]
	 *		- field : SCRN_TEST [화면 테스트]
	 *		- field : FUNC_TEST [기능 테스트]
	 *		- field : LOSS_EQP_YN [분실 단말기 여부]
	 *		- field : RWD_RTN_OBJ_YN [보상 반납 대상 여부]
	 *		- field : RENTP_YN [임대폰 여부]
	 *		- field : WET_YN [침수 여부]
	 *		- field : USIM_EQPCHG_YN [USIM 기변 여부]
	 *		- field : OTHRCO_FORGN_EQP_YN [타사 해외 단말기 여부]
	 *		- field : SUGG [의견]
	 *		- field : EVALCNSLR_AUTCT_YN [평가원 인증 여부]
	 *		- field : FULLSET_YN [FULLSET 여부]
	 *		- field : CNSL_AMT [매입 금액]
	 *		- field : PRCH_CL [매입 구분]
	 *		- field : DPSTR [예금주]
	 *		- field : BANK_CD [은행 코드]
	 *		- field : ACCO_NO [계좌 번호]
	 *		- field : TEL_NO [전화 번호]
	 *		- field : ZIPCD [우편번호]
	 *		- field : ADDR [주소]
	 *		- field : ETC_ADDR [기타 주소]
	 *		- field : FS_REG_USER_ID [최초 등록 사용자 ID]
	 *		- field : LS_CHG_USER_ID [최종 변경 사용자 ID]
	 *		- field : USER_NM [최종변경자]
	 *		- field : PRCH_MGMT_NO [매입 관리 번호]
	 *		- field : EVALCNSLR_SUGG [평가원 의견]
	 *		- field : ADJ_AMT [조정 금액]
	 *		- field : FEE_DEDC_YN [요금 공제 여부]
	 *		- field : SVC_NO [서비스 번호]
	 *		- field : SVC_MGMT_NO [서비스 관리 번호]
	 *		- field : IMEI [IMEI]
	 *		- field : ETC_CMPT [기타 구성품]
	 *		- field : PRG_GB [검수등록대상구분]
	 *		- field : CUST_IDEA [고객 의사]
	 *		- field : AMT_RMT_YN [금액 송금 여부]
	 *		- field : UPDATE_COUNT [UPDATE COUNT]
	 *		- field : POLY_OBJ_YN [정책 대상 여부]
	 *		- field : POLY_OBJ_CD [정책대상코드]
	 *		- field : PROGR_ST [진행 상태]
	 *		- field : BOX_NO [박스 번호]
	 *		- field : RETUR_DEALCO_CL [반송 거래처 구분]
	 *		- field : PRE_DC_CL [선 할인 구분]
	 *		- field : SKN_CL [SKN 구분]
	 *		- field : SKN_EQP_ST [SKN 단말기 상태]
	 *		- field : SKN_JDG_AMT [SKN 감정 금액]
	 *		- field : EQP_PRC [단말기 가격]
	 *		- field : STRD_INCEN [준 인센티브]
	 *		- field : STRD_INCEN_SCOP_AMT [기준 인센티브 범위 금액]
	 *		- field : UKEY_STRD_INCEN [기준 인센티브]
	 *		- field : SKN_JDG_CL [SKN 감정 구분]
	 *		- field : SKN_JDG_CL_SUB [SKN 감정 구분 서브]
	 *		- field : SKN_SMPL_JDG_DAMT [SKN 샘플 감정 차액]
	 *		- field : AGN_DDCT_YN [대리점 차감 여부]
	 *		- field : SCRN_AFIMG_YN [화면 잔상 여부]
	 *		- field : FST_RGSTR [최초 등록자]
	 *		- field : ASIANA_CARD_NO [아시아나 카드 번호]
	 *		- field : SALE_EQP_ST [판매등급]
	 *		- field : CMCT_CO [통신회사]
	 *		- field : DPSTR_ENPT [예금주 암호화]
	 *		- field : TEL_NO_ENPT [전화번호 암호화]
	 *		- field : ETC_ADDR_ENPT [기타 주소 암호화]
	 *		- field : ACCO_NO_ENPT [계좌번호 암호화]
	 *		- field : SVC_NO_ENPT [서비스번호 암호화]
	 *		- field : TLY_DT [검수일자]
	 *		- field : STARD_YN [회수 기준 여부]
	 *		- field : STARD_DD [회수 기준일]
	 *		- field : PRCH_CHG_DAMT_AMT [매입 변경 차액 금액]
	 *		- field : PRCH_CHG_STRD_NOD [매입 변경 기준 일수]
	 *		- field : IN_CONF_DT [입고 확정 일자]
	 *		- field : SCRB_MTHD [가입방법]
	 * </pre>
	 */
	public IDataSet fSPrchRpetJdgTlyRegLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dSPrchRpetJdgTlyRegLst(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]매입재감정검수등록구성품목록조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNSL_MGMT_NO [상담관리번호]
	 *	- field : EQP_MDL_CD [단말기모델코드]
	 *	- field : SCRB_MTHD [가입방법]
	 *	- field : PRG_GB [검수등록대상구분]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_TLY_REG_CMPT_LIST
	 *		- field : EQP_MDL_CD [단말기 모델 코드]
	 *		- field : OUTS_MCN_CL [주변 기기 구분]
	 *		- field : QTY [수량]
	 *		- field : UPRC [단가]
	 *		- field : UNIT_UPRC [필드1]
	 *		- field : SALE_UPRC [매출 단가]
	 *		- field : NORM_QTY [정상 수량]
	 *		- field : HLD_QTY [보유 수량]
	 *		- field : SKN_SMPL_QTY [SKN 샘플 수량]
	 *		- field : DDCT_AMT [차감 금액]
	 *		- field : CNSL_SER_NO [상담 일련 번호]
	 *		- field : PRCH_SER_NO [매입 일련 번호]
	 *		- field : LAUNC_DT [출시일자]
	 *		- field : PRCH_MGMT_NO [매입관리번호]
	 *		- field : CNSL_MGMT_NO [상담 관리 번호]
	 *		- field : BATR_CD [배터리 코드]
	 *		- field : PROD_CL [상품 구분]
	 * </pre>
	 */
	public IDataSet fSPrchRpetJdgTlyRegCmptLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dSPrchRpetJdgTlyRegCmptLst(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]재검수진행매입관리정보등록</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : EQP_MDL_CD [단말기 모델 코드]
	 *	- field : OUTS_MCN_CL [주변 기기 구분]
	 *	- field : QTY [수량]
	 *	- field : UPRC [단가]
	 *	- field : UNIT_UPRC [필드1]
	 *	- field : SALE_UPRC [매출 단가]
	 *	- field : NORM_QTY [정상 수량]
	 *	- field : HLD_QTY [보유 수량]
	 *	- field : SKN_SMPL_QTY [SKN 샘플 수량]
	 *	- field : DDCT_AMT [차감 금액]
	 *	- field : CNSL_SER_NO [상담 일련 번호]
	 *	- field : PRCH_SER_NO [매입 일련 번호]
	 *	- field : LAUNC_DT [출시일자]
	 *	- field : PRCH_MGMT_NO [매입관리번호]
	 *	- field : CNSL_MGMT_NO [상담 관리 번호]
	 *	- field : BATR_CD [배터리 코드]
	 *	- field : PROD_CL [상품 구분]
	 *	- field : DPSTR_ENPT [예금주 암호화]
	 *	- field : TEL_NO_ENPT [전화번호 암호화]
	 *	- field : ETC_ADDR_ENPT [상세주소 암호화]
	 *	- field : ACCO_NO_ENPT [계좌번호 암호화]
	 *	- field : SVC_NO_ENPT [서비스번호 암호화]
	 *	- field : TLY_DT [검수일자]
	 *	- field : PRCH_CHG_DAMT_AMT [매입 변경 차액 금액]
	 *	- field : PRCH_CHG_STRD_NOD [매입 변경 기준 일수]
	 *	- field : IN_CONF_DT [입고 확정 일자]
	 *	- field : SELL_GRADE [판매등급]
	 *	- field : SCRB_MTHD [가입방법]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fIRpetTlyTbEpCstPrchsMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dIRpetTlyProgrTbEpCstPrchsMgmt(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]재검수진행매입상세정보등록</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNSL_MGMT_NO [상담 관리 번호]
	 *	- field : EQP_MDL_CD [단말기 모델 코드]
	 *	- field : EQP_MDL_NM [단말기 모델명]
	 *	- field : EQP_SER_NO [상담 관리 일련 번호]
	 *	- field : CNSL_DT [상담 일자]
	 *	- field : AFFIL_AGN_NM [소속 대리점명]
	 *	- field : AGN_ORG_CD [대리점 조직 코드]
	 *	- field : AFFIL_AGN [소속 대리점]
	 *	- field : CNSL_DEALCO_CD [상담 거래처 코드]
	 *	- field : CNSL_DEALCO_NM [거래처 명]
	 *	- field : EQP_COLOR_CD [단말기 색상 코드]
	 *	- field : ONING_DT [개통 일자]
	 *	- field : EQP_ST [단말기 상태]
	 *	- field : EQP_ST_DTAIL [단말기 상태 세부]
	 *	- field : EQP_ST_TXT [단말기 상태]
	 *	- field : USE_PRD_DDCT_AMT [사용 기간 차감 금액]
	 *	- field : INR_CORR_YN [내부 부식 여부]
	 *	- field : PWR_SYS [전원 시스템]
	 *	- field : CALL_QULT_TEST [통화 품질 테스트]
	 *	- field : DATA_PRFM [DATA 성능]
	 *	- field : SCRN_TEST [화면 테스트]
	 *	- field : FUNC_TEST [기능 테스트]
	 *	- field : LOSS_EQP_YN [분실 단말기 여부]
	 *	- field : RWD_RTN_OBJ_YN [보상 반납 대상 여부]
	 *	- field : RENTP_YN [임대폰 여부]
	 *	- field : WET_YN [침수 여부]
	 *	- field : USIM_EQPCHG_YN [USIM 기변 여부]
	 *	- field : OTHRCO_FORGN_EQP_YN [타사 해외 단말기 여부]
	 *	- field : SUGG [의견]
	 *	- field : EVALCNSLR_AUTCT_YN [평가원 인증 여부]
	 *	- field : FULLSET_YN [FULLSET 여부]
	 *	- field : CNSL_AMT [매입 금액]
	 *	- field : PRCH_CL [매입 구분]
	 *	- field : DPSTR [예금주]
	 *	- field : BANK_CD [은행 코드]
	 *	- field : ACCO_NO [계좌 번호]
	 *	- field : TEL_NO [전화 번호]
	 *	- field : ZIPCD [우편번호]
	 *	- field : ADDR [주소]
	 *	- field : ETC_ADDR [기타 주소]
	 *	- field : FS_REG_USER_ID [최초 등록 사용자 ID]
	 *	- field : LS_CHG_USER_ID [최종 변경 사용자 ID]
	 *	- field : USER_NM [최종변경자]
	 *	- field : PRCH_MGMT_NO [매입 관리 번호]
	 *	- field : EVALCNSLR_SUGG [평가원 의견]
	 *	- field : ADJ_AMT [조정 금액]
	 *	- field : FEE_DEDC_YN [요금 공제 여부]
	 *	- field : SVC_NO [서비스 번호]
	 *	- field : SVC_MGMT_NO [서비스 관리 번호]
	 *	- field : IMEI [IMEI]
	 *	- field : ETC_CMPT [기타 구성품]
	 *	- field : PRG_GB [검수등록대상구분]
	 *	- field : CUST_IDEA [고객 의사]
	 *	- field : AMT_RMT_YN [금액 송금 여부]
	 *	- field : UPDATE_COUNT [UPDATE COUNT]
	 *	- field : POLY_OBJ_YN [정책 대상 여부]
	 *	- field : POLY_OBJ_CD [정책대상코드]
	 *	- field : PROGR_ST [진행 상태]
	 *	- field : BOX_NO [박스 번호]
	 *	- field : RETUR_DEALCO_CL [반송 거래처 구분]
	 *	- field : PRE_DC_CL [선 할인 구분]
	 *	- field : SKN_CL [SKN 구분]
	 *	- field : SKN_EQP_ST [SKN 단말기 상태]
	 *	- field : SKN_JDG_AMT [SKN 감정 금액]
	 *	- field : EQP_PRC [단말기 가격]
	 *	- field : STRD_INCEN [준 인센티브]
	 *	- field : STRD_INCEN_SCOP_AMT [기준 인센티브 범위 금액]
	 *	- field : UKEY_STRD_INCEN [기준 인센티브]
	 *	- field : SKN_JDG_CL [SKN 감정 구분]
	 *	- field : SKN_JDG_CL_SUB [SKN 감정 구분 서브]
	 *	- field : SKN_SMPL_JDG_DAMT [SKN 샘플 감정 차액]
	 *	- field : AGN_DDCT_YN [대리점 차감 여부]
	 *	- field : SCRN_AFIMG_YN [화면 잔상 여부]
	 *	- field : FST_RGSTR [최초 등록자]
	 *	- field : ASIANA_CARD_NO [아시아나 카드 번호]
	 *	- field : SALE_EQP_ST [판매등급]
	 *	- field : SELL_GRADE [판매등급]
	 *	- field : CMCT_CO [통신회사]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fIRpetTlyTbEpCstPrchsDtl(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dIRpetTlyProgrTbEpCstPrchsDtl(requestData, onlineCtx);           
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]재검수대상상담관리정보수정</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNSL_MGMT_NO [상담 관리 번호]
	 *	- field : EQP_MDL_CD [단말기 모델 코드]
	 *	- field : EQP_MDL_NM [단말기 모델명]
	 *	- field : EQP_SER_NO [상담 관리 일련 번호]
	 *	- field : CNSL_DT [상담 일자]
	 *	- field : AFFIL_AGN_NM [소속 대리점명]
	 *	- field : AGN_ORG_CD [대리점 조직 코드]
	 *	- field : AFFIL_AGN [소속 대리점]
	 *	- field : CNSL_DEALCO_CD [상담 거래처 코드]
	 *	- field : CNSL_DEALCO_NM [거래처 명]
	 *	- field : EQP_COLOR_CD [단말기 색상 코드]
	 *	- field : ONING_DT [개통 일자]
	 *	- field : EQP_ST [단말기 상태]
	 *	- field : EQP_ST_DTAIL [단말기 상태 세부]
	 *	- field : EQP_ST_TXT [단말기 상태]
	 *	- field : USE_PRD_DDCT_AMT [사용 기간 차감 금액]
	 *	- field : INR_CORR_YN [내부 부식 여부]
	 *	- field : PWR_SYS [전원 시스템]
	 *	- field : CALL_QULT_TEST [통화 품질 테스트]
	 *	- field : DATA_PRFM [DATA 성능]
	 *	- field : SCRN_TEST [화면 테스트]
	 *	- field : FUNC_TEST [기능 테스트]
	 *	- field : LOSS_EQP_YN [분실 단말기 여부]
	 *	- field : RWD_RTN_OBJ_YN [보상 반납 대상 여부]
	 *	- field : RENTP_YN [임대폰 여부]
	 *	- field : WET_YN [침수 여부]
	 *	- field : USIM_EQPCHG_YN [USIM 기변 여부]
	 *	- field : OTHRCO_FORGN_EQP_YN [타사 해외 단말기 여부]
	 *	- field : SUGG [의견]
	 *	- field : EVALCNSLR_AUTCT_YN [평가원 인증 여부]
	 *	- field : FULLSET_YN [FULLSET 여부]
	 *	- field : CNSL_AMT [매입 금액]
	 *	- field : PRCH_CL [매입 구분]
	 *	- field : DPSTR [예금주]
	 *	- field : BANK_CD [은행 코드]
	 *	- field : ACCO_NO [계좌 번호]
	 *	- field : TEL_NO [전화 번호]
	 *	- field : ZIPCD [우편번호]
	 *	- field : ADDR [주소]
	 *	- field : ETC_ADDR [기타 주소]
	 *	- field : FS_REG_USER_ID [최초 등록 사용자 ID]
	 *	- field : LS_CHG_USER_ID [최종 변경 사용자 ID]
	 *	- field : USER_NM [최종변경자]
	 *	- field : PRCH_MGMT_NO [매입 관리 번호]
	 *	- field : EVALCNSLR_SUGG [평가원 의견]
	 *	- field : ADJ_AMT [조정 금액]
	 *	- field : FEE_DEDC_YN [요금 공제 여부]
	 *	- field : SVC_NO [서비스 번호]
	 *	- field : SVC_MGMT_NO [서비스 관리 번호]
	 *	- field : IMEI [IMEI]
	 *	- field : ETC_CMPT [기타 구성품]
	 *	- field : PRG_GB [검수등록대상구분]
	 *	- field : CUST_IDEA [고객 의사]
	 *	- field : AMT_RMT_YN [금액 송금 여부]
	 *	- field : UPDATE_COUNT [UPDATE COUNT]
	 *	- field : POLY_OBJ_YN [정책 대상 여부]
	 *	- field : POLY_OBJ_CD [정책대상코드]
	 *	- field : PROGR_ST [진행 상태]
	 *	- field : BOX_NO [박스 번호]
	 *	- field : RETUR_DEALCO_CL [반송 거래처 구분]
	 *	- field : PRE_DC_CL [선 할인 구분]
	 *	- field : SKN_CL [SKN 구분]
	 *	- field : SKN_EQP_ST [SKN 단말기 상태]
	 *	- field : SKN_JDG_AMT [SKN 감정 금액]
	 *	- field : EQP_PRC [단말기 가격]
	 *	- field : STRD_INCEN [준 인센티브]
	 *	- field : STRD_INCEN_SCOP_AMT [기준 인센티브 범위 금액]
	 *	- field : UKEY_STRD_INCEN [기준 인센티브]
	 *	- field : SKN_JDG_CL [SKN 감정 구분]
	 *	- field : SKN_JDG_CL_SUB [SKN 감정 구분 서브]
	 *	- field : SKN_SMPL_JDG_DAMT [SKN 샘플 감정 차액]
	 *	- field : AGN_DDCT_YN [대리점 차감 여부]
	 *	- field : SCRN_AFIMG_YN [화면 잔상 여부]
	 *	- field : FST_RGSTR [최초 등록자]
	 *	- field : ASIANA_CARD_NO [아시아나 카드 번호]
	 *	- field : SALE_EQP_ST [판매등급]
	 *	- field : CMCT_CO [통신회사]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fURpetTlyTbEpCstConsultMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dURpetTlyTbEpCstConsultMgmt(requestData, onlineCtx);           
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 * <pre>[FM]단말기등급변경차액정보조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_EQP_GRADE_CHG_LIST
	 *		- field : PRC_GRADE [가격 등급]
	 *		- field : RATE [등급 비율]
	 *		- field : PRC_RT_SEQ [가격 비율 순번]
	 *		- field : STRD_INCEN [기준 인센티브]
	 * </pre>
	 */
	public IDataSet fSEqpMdlGradeChgDamtInfo(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dSEqpMdlGradeChgDamtInfo(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

    /**
	 *
	 *
	 * @author 정동현 (jjddhh123)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : EQP_MDL_CD [단말기모델코드]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_EQP_COLOR_LIST
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : COLOR_CD [색상코드]
	 * </pre>
	 */
	public IDataSet fSTlyEqpMdlColorLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dSTlyEqpMdlColorLst(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
    }

	/**
	 * <pre>[FM]재검수진행고객상담정보수정</pre>
	 *
	 * @author 이하나 (hana526)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_TLY_REG_LIST
	 *		- field : CNSL_MGMT_NO [상담 관리 번호]
	 *		- field : EQP_MDL_CD [단말기 모델 코드]
	 *		- field : EQP_MDL_NM [단말기 모델명]
	 *		- field : EQP_SER_NO [상담 관리 일련 번호]
	 *		- field : CNSL_DT [상담 일자]
	 *		- field : AFFIL_AGN_NM [소속 대리점명]
	 *		- field : AGN_ORG_CD [대리점 조직 코드]
	 *		- field : AFFIL_AGN [소속 대리점]
	 *		- field : CNSL_DEALCO_CD [상담 거래처 코드]
	 *		- field : CNSL_DEALCO_NM [거래처 명]
	 *		- field : EQP_COLOR_CD [단말기 색상 코드]
	 *		- field : ONING_DT [개통 일자]
	 *		- field : EQP_ST [단말기 상태]
	 *		- field : EQP_ST_DTAIL [단말기 상태 세부]
	 *		- field : EQP_ST_TXT [단말기 상태]
	 *		- field : USE_PRD_DDCT_AMT [사용 기간 차감 금액]
	 *		- field : INR_CORR_YN [내부 부식 여부]
	 *		- field : PWR_SYS [전원 시스템]
	 *		- field : CALL_QULT_TEST [통화 품질 테스트]
	 *		- field : DATA_PRFM [DATA 성능]
	 *		- field : SCRN_TEST [화면 테스트]
	 *		- field : FUNC_TEST [기능 테스트]
	 *		- field : LOSS_EQP_YN [분실 단말기 여부]
	 *		- field : RWD_RTN_OBJ_YN [보상 반납 대상 여부]
	 *		- field : RENTP_YN [임대폰 여부]
	 *		- field : WET_YN [침수 여부]
	 *		- field : USIM_EQPCHG_YN [USIM 기변 여부]
	 *		- field : OTHRCO_FORGN_EQP_YN [타사 해외 단말기 여부]
	 *		- field : SUGG [의견]
	 *		- field : EVALCNSLR_AUTCT_YN [평가원 인증 여부]
	 *		- field : FULLSET_YN [FULLSET 여부]
	 *		- field : CNSL_AMT [매입 금액]
	 *		- field : PRCH_CL [매입 구분]
	 *		- field : DPSTR [예금주]
	 *		- field : BANK_CD [은행 코드]
	 *		- field : ACCO_NO [계좌 번호]
	 *		- field : TEL_NO [전화 번호]
	 *		- field : ZIPCD [우편번호]
	 *		- field : ADDR [주소]
	 *		- field : ETC_ADDR [기타 주소]
	 *		- field : FS_REG_USER_ID [최초 등록 사용자 ID]
	 *		- field : LS_CHG_USER_ID [최종 변경 사용자 ID]
	 *		- field : USER_NM [최종변경자]
	 *		- field : PRCH_MGMT_NO [매입 관리 번호]
	 *		- field : EVALCNSLR_SUGG [평가원 의견]
	 *		- field : ADJ_AMT [조정 금액]
	 *		- field : FEE_DEDC_YN [요금 공제 여부]
	 *		- field : SVC_NO [서비스 번호]
	 *		- field : SVC_MGMT_NO [서비스 관리 번호]
	 *		- field : IMEI [IMEI]
	 *		- field : ETC_CMPT [기타 구성품]
	 *		- field : PRG_GB [검수등록대상구분]
	 *		- field : CUST_IDEA [고객 의사]
	 *		- field : AMT_RMT_YN [금액 송금 여부]
	 *		- field : UPDATE_COUNT [UPDATE_COUNT]
	 *		- field : POLY_OBJ_YN [정책 대상 여부]
	 *		- field : POLY_OBJ_CD [정책대상코드]
	 *		- field : PROGR_ST [진행 상태]
	 *		- field : BOX_NO [박스 번호]
	 *		- field : RETUR_DEALCO_CL [반송 거래처 구분]
	 *		- field : PRE_DC_CL [선 할인 구분]
	 *		- field : SKN_CL [SKN 구분]
	 *		- field : SKN_EQP_ST [SKN 단말기 상태]
	 *		- field : SKN_JDG_AMT [SKN 감정 금액]
	 *		- field : EQP_PRC [단말기 가격]
	 *		- field : STRD_INCEN [기준 인센티브]
	 *		- field : STRD_INCEN_SCOP_AMT [기준 인센티브 범위 금액]
	 *		- field : UKEY_STRD_INCEN [기준 인센티브]
	 *		- field : SKN_JDG_CL [SKN 감정 구분]
	 *		- field : SKN_JDG_CL_SUB [SKN 감정 구분 서브]
	 *		- field : SKN_SMPL_JDG_DAMT [SKN 샘플 감정 차액]
	 *		- field : AGN_DDCT_YN [대리점 차감 여부]
	 *		- field : SCRN_AFIMG_YN [화면 잔상 여부]
	 *		- field : FST_RGSTR [최초 등록자]
	 *		- field : ASIANA_CARD_NO [아시아나 카드 번호]
	 *		- field : SELL_GRADE [판매등급]
	 *		- field : CMCT_CO [통신회사]
	 *		- field : DPSTR_ENPT [예금주 암호화]
	 *		- field : TEL_NO_ENPT [전화번호 암호화]
	 *		- field : ETC_ADDR_ENPT [상세주소 암호화]
	 *		- field : ACCO_NO_ENPT [계좌번호 암호화]
	 *		- field : SVC_NO_ENPT [서비스번호 암호화]
	 *		- field : TLY_DT [검수일자]
	 *		- field : PRCH_CHG_DAMT_AMT [매입 변경 차액 금액]
	 *		- field : PRCH_CHG_STRD_NOD [매입 변경 기준 일수]
	 *		- field : IN_CONF_DT [입고 확정 일자]
	 *		- field : SALE_EQP_ST [판매등급]
	 *		- field : SCRB_MTHD [가입방법]
	 *		- field : CLCT_DT [회수일자]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fURpetJdgTlyCustConsultMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dURpetJdgTlyCustConsultMgmt(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
	}

	/**
	 * <pre>[FM]재검수진행고객매입정보수정</pre>
	 *
	 * @author 이하나 (hana526)
	 * @since 2015-09-01 13:57:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_TLY_REG_LIST
	 *		- field : CNSL_MGMT_NO [상담 관리 번호]
	 *		- field : EQP_MDL_CD [단말기 모델 코드]
	 *		- field : EQP_MDL_NM [단말기 모델명]
	 *		- field : EQP_SER_NO [상담 관리 일련 번호]
	 *		- field : CNSL_DT [상담 일자]
	 *		- field : AFFIL_AGN_NM [소속 대리점명]
	 *		- field : AGN_ORG_CD [대리점 조직 코드]
	 *		- field : AFFIL_AGN [소속 대리점]
	 *		- field : CNSL_DEALCO_CD [상담 거래처 코드]
	 *		- field : CNSL_DEALCO_NM [거래처 명]
	 *		- field : EQP_COLOR_CD [단말기 색상 코드]
	 *		- field : ONING_DT [개통 일자]
	 *		- field : EQP_ST [단말기 상태]
	 *		- field : EQP_ST_DTAIL [단말기 상태 세부]
	 *		- field : EQP_ST_TXT [단말기 상태]
	 *		- field : USE_PRD_DDCT_AMT [사용 기간 차감 금액]
	 *		- field : INR_CORR_YN [내부 부식 여부]
	 *		- field : PWR_SYS [전원 시스템]
	 *		- field : CALL_QULT_TEST [통화 품질 테스트]
	 *		- field : DATA_PRFM [DATA 성능]
	 *		- field : SCRN_TEST [화면 테스트]
	 *		- field : FUNC_TEST [기능 테스트]
	 *		- field : LOSS_EQP_YN [분실 단말기 여부]
	 *		- field : RWD_RTN_OBJ_YN [보상 반납 대상 여부]
	 *		- field : RENTP_YN [임대폰 여부]
	 *		- field : WET_YN [침수 여부]
	 *		- field : USIM_EQPCHG_YN [USIM 기변 여부]
	 *		- field : OTHRCO_FORGN_EQP_YN [타사 해외 단말기 여부]
	 *		- field : SUGG [의견]
	 *		- field : EVALCNSLR_AUTCT_YN [평가원 인증 여부]
	 *		- field : FULLSET_YN [FULLSET 여부]
	 *		- field : CNSL_AMT [매입 금액]
	 *		- field : PRCH_CL [매입 구분]
	 *		- field : DPSTR [예금주]
	 *		- field : BANK_CD [은행 코드]
	 *		- field : ACCO_NO [계좌 번호]
	 *		- field : TEL_NO [전화 번호]
	 *		- field : ZIPCD [우편번호]
	 *		- field : ADDR [주소]
	 *		- field : ETC_ADDR [기타 주소]
	 *		- field : FS_REG_USER_ID [최초 등록 사용자 ID]
	 *		- field : LS_CHG_USER_ID [최종 변경 사용자 ID]
	 *		- field : USER_NM [최종변경자]
	 *		- field : PRCH_MGMT_NO [매입 관리 번호]
	 *		- field : EVALCNSLR_SUGG [평가원 의견]
	 *		- field : ADJ_AMT [조정 금액]
	 *		- field : FEE_DEDC_YN [요금 공제 여부]
	 *		- field : SVC_NO [서비스 번호]
	 *		- field : SVC_MGMT_NO [서비스 관리 번호]
	 *		- field : IMEI [IMEI]
	 *		- field : ETC_CMPT [기타 구성품]
	 *		- field : PRG_GB [검수등록대상구분]
	 *		- field : CUST_IDEA [고객 의사]
	 *		- field : AMT_RMT_YN [금액 송금 여부]
	 *		- field : UPDATE_COUNT [UPDATE COUNT]
	 *		- field : POLY_OBJ_YN [정책 대상 여부]
	 *		- field : POLY_OBJ_CD [정책대상코드]
	 *		- field : PROGR_ST [진행 상태]
	 *		- field : BOX_NO [박스 번호]
	 *		- field : RETUR_DEALCO_CL [반송 거래처 구분]
	 *		- field : PRE_DC_CL [선 할인 구분]
	 *		- field : SKN_CL [SKN 구분]
	 *		- field : SKN_EQP_ST [SKN 단말기 상태]
	 *		- field : SKN_JDG_AMT [SKN 감정 금액]
	 *		- field : EQP_PRC [단말기 가격]
	 *		- field : STRD_INCEN [기준 인센티브]
	 *		- field : STRD_INCEN_SCOP_AMT [기준 인센티브 범위 금액]
	 *		- field : UKEY_STRD_INCEN [기준 인센티브]
	 *		- field : SKN_JDG_CL [SKN 감정 구분]
	 *		- field : SKN_JDG_CL_SUB [SKN 감정 구분 서브]
	 *		- field : SKN_SMPL_JDG_DAMT [SKN 샘플 감정 차액]
	 *		- field : AGN_DDCT_YN [대리점 차감 여부]
	 *		- field : SCRN_AFIMG_YN [화면 잔상 여부]
	 *		- field : FST_RGSTR [최초 등록자]
	 *		- field : ASIANA_CARD_NO [아시아나 카드 번호]
	 *		- field : SELL_GRADE [판매등급]
	 *		- field : CMCT_CO [통신회사]
	 *		- field : DPSTR_ENPT [예금주 암호화]
	 *		- field : TEL_NO_ENPT [전화번호 암호화]
	 *		- field : ETC_ADDR_ENPT [상세주소 암호화]
	 *		- field : ACCO_NO_ENPT [계좌번호 암호화]
	 *		- field : SVC_NO_ENPT [서비스번호 암호화]
	 *		- field : TLY_DT [검수일자]
	 *		- field : PRCH_CHG_DAMT_AMT [매입 변경 차액 금액]
	 *		- field : PRCH_CHG_STRD_NOD [매입 변경 기준 일수]
	 *		- field : IN_CONF_DT [입고 확정 일자]
	 *		- field : SALE_EQP_ST [판매등급]
	 *		- field : SCRB_MTHD [가입방법]
	 *		- field : CLCT_DT [회수일자]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fURpetJdgTlyCustPrchsMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        try {
            // 1. DU lookup
            DEPTlyReg dEPTlyReg = (DEPTlyReg)lookupDataUnit(DEPTlyReg.class);
            responseData = dEPTlyReg.dURpetJdgTlyCustPrchsMgmt(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }        
        return responseData;
	}
  
}
