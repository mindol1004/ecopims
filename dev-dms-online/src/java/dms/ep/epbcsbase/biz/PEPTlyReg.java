package dms.ep.epbcsbase.biz;

import java.util.Map;

import org.apache.commons.logging.Log;

import fwk.common.CommonArea;
import fwk.utils.HpcUtils;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.IResultMessage;
import nexcore.framework.core.data.RecordSet;
import nexcore.framework.core.data.ResultMessage;
import nexcore.framework.core.exception.BizRuntimeException;
import nexcore.framework.core.log.LogManager;
import nexcore.framework.core.util.StringUtils;


/**
 * <ul>
 * <li>업무 그룹명 : dms/에코폰</li>
 * <li>단위업무명: [PU]검수등록</li>
 * <li>설  명 : <pre>[PU]검수등록</pre></li>
 * <li>작성일 : 2015-09-01 13:57:04</li>
 * <li>작성자 : 김윤환 (kyh0904)</li>
 * </ul>
 *
 * @author 김윤환 (kyh0904)
 */
public class PEPTlyReg extends fwk.base.ProcessUnit {

    /**
     * 이 클래스는 Singleton 객체로 수행됩니다. 
     * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
     */

    /**
     * Default Constructor
     */
    public PEPTlyReg(){
        super();
    }

    /**
	 * <pre>[PM]일반감정검수등록목록조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:04
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
	 *		- field : AGN_ORG_CD [대리점 조직 코드]
	 *		- field : AFFIL_AGN [소속 대리점]
	 *		- field : AFFIL_AGN_NM [소속대리점명]
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
	 *	- record : RS_EQP_GRADE_LIST
	 *		- field : PRC_GRADE [가격 등급]
	 *		- field : RATE [등급 비율]
	 *		- field : PRC_RT_SEQ [가격 비율 순번]
	 *		- field : STRD_INCEN [기준 인센티브]
	 *	- record : RS_EQP_GRADE_CHG_LIST
	 *		- field : PRC_GRADE [가격 등급]
	 *		- field : RATE [등급 비율]
	 *		- field : PRC_RT_SEQ [가격 비율 순번]
	 *		- field : STRD_INCEN [기준 인센티브]
	 * </pre>
	 */
    public IDataSet pSGnrlJdgTlyRegLst(IDataSet requestData, IOnlineContext onlineCtx) {
        Log logger = LogManager.getFwkLog();
        IDataSet responseData = new DataSet();
        String prgGb = "";   // 검수등록대상구분
        try {            
            /*
            if (logger.isDebugEnabled()) {                
                logger.debug("***** 상담 관리 번호 =>> " +StringUtils.nvlObject(requestData.getField("CNSL_MGMT_NO"),"")+"\n");    
                logger.debug("***** 단말기모델코드 =>> " +StringUtils.nvlObject(requestData.getField("EQP_MDL_CD"),"")+"\n");
                logger.debug("***** 가입방법 =>> " +StringUtils.nvlObject(requestData.getField("SCRB_MTHD"),"")+"\n");
                logger.debug("***** 검수등록대상구분 =>> " +StringUtils.nvlObject(requestData.getField("PRG_GB"),"")+"\n");
            }
            */
            prgGb = StringUtils.nvlObject(requestData.getField("PRG_GB"),""); // 검수등록대상구분
            
            if("P".equals(prgGb) || "C".equals(prgGb) || "S".equals(prgGb)){
                /*
                // 검수등록대상구분
                P:매입제외목록
                C:송금목록/요금공제목록/정책대상목록/강제매입목록/아시아나마일리지목록/매입목록/매입대상목록
                S:삭제
                */                                
                responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSPrchGnrlJdgTlyRegLst", requestData, onlineCtx).getRecordSet("RS_TLY_REG_LIST"));
                responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSPrchGnrlJdgTlyRegCmptLst", requestData, onlineCtx).getRecordSet("RS_TLY_REG_CMPT_LIST"));                
            }else{
                /*
                // 검수등록대상구분
                N: 검수대상목록
                F:품질검사등록대상목록/품질검사등록취소목록                
                */   
                responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSGnrlJdgTlyRegLst", requestData, onlineCtx).getRecordSet("RS_TLY_REG_LIST"));
                responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSGnrlJdgTlyRegCmptLst", requestData, onlineCtx).getRecordSet("RS_TLY_REG_CMPT_LIST"));
            }
            responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSEqpMdlInfo", requestData, onlineCtx).getRecordSet("RS_EQP_INFO_LIST")); // 단말기정보 조회
            responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSEqpMdlGradeInfo", requestData, onlineCtx).getRecordSet("RS_EQP_GRADE_LIST"));   // 단말기 등급조회
            responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSEqpMdlGradeChgDamtInfo", requestData, onlineCtx).getRecordSet("RS_EQP_GRADE_CHG_LIST"));   // 단말기 등급변경차액정보조회
        
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
	 * <pre>[PM]일반검수완료대상등록정보</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:04
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_TLY_REG_LIST
	 *		- field : CNSL_MGMT_NO [상담 관리 번호]
	 *		- field : EQP_MDL_CD [단말기 모델 코드]
	 *		- field : EQP_MDL_NM [단말기 모델명]
	 *		- field : EQP_SER_NO [단말기 일련 번호]
	 *		- field : CNSL_DT [상담 일자]
	 *		- field : DEAL_CO_NM [상담 거래처명]
	 *		- field : AGN_ORG_CD [대리점 조직 코드]
	 *		- field : AFFIL_AGN [소속 대리점]
	 *		- field : AFFIL_AGN_NM [소속대리점명]
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
	 *		- field : ETC_ADDR_ENPT [상세주소 암호화]
	 *		- field : ACCO_NO_ENPT [계좌번호 암호화]
	 *		- field : SVC_NO_ENPT [서비스번호 암호화]
	 *		- field : TLY_DT [검수일자]
	 *		- field : PRCH_CHG_DAMT_AMT [매입 변경 차액 금액]
	 *		- field : PRCH_CHG_STRD_NOD [매입 변경 기준 일수]
	 *		- field : IN_CONF_DT [입고 확정 일자]
	 *		- field : CLCT_DT [회수일자]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
    public IDataSet pSaveGnrlTlyFnshRegInfo(IDataSet requestData, IOnlineContext onlineCtx) {
        Log logger = LogManager.getFwkLog();
        
        IDataSet responseData = new DataSet();
        IRecordSet rs = requestData.getRecordSet("RS_TLY_REG_LIST");
        CommonArea ca = getCommonArea(onlineCtx);        
        IRecord rec = null;
        
        IDataSet rowDs = new DataSet();
        
        try {
            for(int i = 0 ; i < rs.getRecordCount() ; i++){            
                rec = rs.getRecord(i);                            
                rowDs.putFieldMap(rec);
                rowDs.putField("USER_ID", ca.getUserNo());
                
                /*
                 *  암호화 대상 컬럼 추가
                 *  Ex.)
                 *  requestData.putField("EML_ADDR_ENPT", HpcUtils.encodeByAES(requestData.getField("EML_ADDR")));
                 *  requestData.putField("EMIL_ADDR_MASK", HpcUtils.maskingEmail(requestData.getField("EML_ADDR")));
                 *  
                 *  원천 데이터 암호화.
                 *  원천 데이터 마스킹처리.             
                 */                
                rowDs.putField("DPSTR", HpcUtils.maskingName(rec.get("DPSTR")));           /* 예금주 마스킹 */
                rowDs.putField("DPSTR_ENPT", HpcUtils.encodeByAES(rec.get("DPSTR")));      /* 예금주 암호화 */
                rowDs.putField("TEL_NO", HpcUtils.maskingTelNo(rec.get("TEL_NO")));        /* 전화번호 마스킹 */                    
                rowDs.putField("TEL_NO_ENPT", HpcUtils.encodeByAES(rec.get("TEL_NO")));    /* 전화번호 암호화 */
                rowDs.putField("ETC_ADDR", HpcUtils.maskingAddrDtl(rec.get("ETC_ADDR")));  /* 상세주소 마스킹 */
                rowDs.putField("ETC_ADDR_ENPT", HpcUtils.encodeByAES(rec.get("ETC_ADDR")));/* 상세주소 암호화 */ 
                rowDs.putField("ACCO_NO", HpcUtils.maskingAccountNo(rec.get("ACCO_NO")));  /* 계좌번호 마스킹 */
                rowDs.putField("ACCO_NO_ENPT", HpcUtils.encodeByAES(rec.get("ACCO_NO")));  /* 계좌번호 암호화 */
                rowDs.putField("SVC_NO", HpcUtils.maskingTelNo(rec.get("SVC_NO")));        /* 서비스번호 마스킹 */
                rowDs.putField("SVC_NO_ENPT", HpcUtils.encodeByAES(rec.get("SVC_NO")));    /* 서비스번호 암호화 */  
                
                
                callSharedBizComponentByDirect("ep.EPSCSBase", "fUGnrlTlyFnshTbEpCstConsultMgmt", rowDs, onlineCtx);    // 일반검수완료대상상담관리정보수정 
                callSharedBizComponentByDirect("ep.EPSCSBase", "fUGnrlTlyFnshTbEpCstPrchsMgmt", rowDs, onlineCtx);      // 일반검수완료대상매입관리정보수정           
            }            
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }
                                                     
        return responseData;
    }

    /**
	 * <pre>[PM]일반검수진행대상등록정보</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:04
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_TLY_REG_LIST
	 *		- field : CNSL_MGMT_NO [상담 관리 번호]
	 *		- field : EQP_MDL_CD [단말기 모델 코드]
	 *		- field : EQP_MDL_NM [단말기 모델명]
	 *		- field : EQP_SER_NO [단말기 일련 번호]
	 *		- field : CNSL_DT [상담 일자]
	 *		- field : DEAL_CO_NM [상담 거래처명]
	 *		- field : AGN_ORG_CD [대리점 조직 코드]
	 *		- field : AFFIL_AGN [소속 대리점]
	 *		- field : AFFIL_AGN_NM [소속대리점명]
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
	 *		- field : ETC_ADDR_ENPT [상세주소 암호화]
	 *		- field : ACCO_NO_ENPT [계좌번호 암호화]
	 *		- field : SVC_NO_ENPT [서비스번호 암호화]
	 *		- field : TLY_DT [검수일자]
	 *		- field : PRCH_CHG_DAMT_AMT [매입 변경 차액 금액]
	 *		- field : PRCH_CHG_STRD_NOD [매입 변경 기준 일수]
	 *		- field : IN_CONF_DT [입고 확정 일자]
	 *		- field : CLCT_DT [회수일자]
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
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
    public IDataSet pSaveGnrlTlyProgrRegInfo(IDataSet requestData, IOnlineContext onlineCtx) {
        Log logger = LogManager.getFwkLog();
        
        IDataSet responseData = new DataSet();
        IRecordSet rsList = requestData.getRecordSet("RS_TLY_REG_LIST");
        IRecordSet rsCmptList = requestData.getRecordSet("RS_TLY_REG_CMPT_LIST");
        CommonArea ca = getCommonArea(onlineCtx);        
        IRecord rec = null;
        IRecord recSub = null;
        String updateCount = "";
        IDataSet rowDs = new DataSet();
        IDataSet rowDsSub = new DataSet();
        
        try {
            for(int i = 0 ; i < rsList.getRecordCount() ; i++){
                rec = rsList.getRecord(i);                            
                rowDs.putFieldMap(rec);
                rowDs.putField("USER_ID", ca.getUserNo());
                
                /*
                 *  암호화 대상 컬럼 추가
                 *  Ex.)
                 *  requestData.putField("EML_ADDR_ENPT", HpcUtils.encodeByAES(requestData.getField("EML_ADDR")));
                 *  requestData.putField("EMIL_ADDR_MASK", HpcUtils.maskingEmail(requestData.getField("EML_ADDR")));
                 *  
                 *  원천 데이터 암호화.
                 *  원천 데이터 마스킹처리.             
                 */     
                rowDs.putField("DPSTR", HpcUtils.maskingName(rec.get("DPSTR")));           /* 예금주 마스킹 */
                rowDs.putField("DPSTR_ENPT", HpcUtils.encodeByAES(rec.get("DPSTR")));      /* 예금주 암호화 */
                rowDs.putField("TEL_NO", HpcUtils.maskingTelNo(rec.get("TEL_NO")));        /* 전화번호 마스킹 */                    
                rowDs.putField("TEL_NO_ENPT", HpcUtils.encodeByAES(rec.get("TEL_NO")));    /* 전화번호 암호화 */
                rowDs.putField("ETC_ADDR", HpcUtils.maskingAddrDtl(rec.get("ETC_ADDR")));  /* 상세주소 마스킹 */
                rowDs.putField("ETC_ADDR_ENPT", HpcUtils.encodeByAES(rec.get("ETC_ADDR")));/* 상세주소 암호화 */ 
                rowDs.putField("ACCO_NO", HpcUtils.maskingAccountNo(rec.get("ACCO_NO")));  /* 계좌번호 마스킹 */
                rowDs.putField("ACCO_NO_ENPT", HpcUtils.encodeByAES(rec.get("ACCO_NO")));  /* 계좌번호 암호화 */
                rowDs.putField("SVC_NO", HpcUtils.maskingTelNo(rec.get("SVC_NO")));        /* 서비스번호 마스킹 */
                rowDs.putField("SVC_NO_ENPT", HpcUtils.encodeByAES(rec.get("SVC_NO")));    /* 서비스번호 암호화 */  
            
                
                // 1. 일반검수진행대상 상담수정 정보조회
                updateCount = StringUtils.nvlObject(callSharedBizComponentByDirect("ep.EPSCSBase", "fSGnrlTlyProgrConsultCount", rowDs, onlineCtx).getField("UPDATE_COUNT"),"");    
                /*
                if (logger.isDebugEnabled()) {          
                    logger.debug("#### rowDs.toString #### =======>> "+rowDs.toString()+"\n");
                    logger.debug("**** rowDs.UPDATE_COUNT #### =======>> "+StringUtils.nvlObject(rowDs.getField("UPDATE_COUNT"),"")+"\n");                                                
                    logger.debug("**** updateCount #### =======>> "+updateCount+"\n");
                    logger.debug("**** rowDs.PRCH_CL (매입구분) #### =======>> "+StringUtils.nvlObject(rowDs.getField("PRCH_CL"),"")+"\n");
                    logger.debug("**** rowDs.PRC_GB (대상구분) #### =======>> "+StringUtils.nvlObject(rowDs.getField("PRC_GB"),"")+"\n");
                    logger.debug("**** rowDs.PROGR_ST (처리상태) #### =======>> "+StringUtils.nvlObject(rowDs.getField("PROGR_ST"),"")+"\n");                    
                }
                */
                // 2. 상담관리정보 Update Count 비교
                if(!updateCount.equals(StringUtils.nvlObject(rowDs.getField("UPDATE_COUNT"),""))){
                    throw new BizRuntimeException("DMS00111", new String[] {}); // 조회 후 다시 처리하십시요.                                                            
                }
                
                if("R".equals(StringUtils.nvlObject(rowDs.getField("PRCH_CL"),""))){
                    // 3.1. 일반검수진행상담상태수정(매입구분:R, 진행상태:15(검수제외반송) 수정)
                    callSharedBizComponentByDirect("ep.EPSCSBase", "fUGnrlTlyProgrConsultSt", rowDs, onlineCtx);                          
                }else if("P".equals(StringUtils.nvlObject(rowDs.getField("PRC_GB"),"")) 
                        || "23".equals(StringUtils.nvlObject(rowDs.getField("PROGR_ST"),""))  
                        || "2C".equals(StringUtils.nvlObject(rowDs.getField("PROGR_ST"),""))
                        || ("C".equals(StringUtils.nvlObject(rowDs.getField("PRC_GB"),"")) && "11".equals(StringUtils.nvlObject(rowDs.getField("PROGR_ST"),"")))
                        || ("P".equals(StringUtils.nvlObject(rowDs.getField("PRC_GB"),"")) && "14".equals(StringUtils.nvlObject(rowDs.getField("PROGR_ST"),"")))
                        || ("C".equals(StringUtils.nvlObject(rowDs.getField("PRC_GB"),"")) && "13".equals(StringUtils.nvlObject(rowDs.getField("PROGR_ST"),"")))                      
                ){
                    // 3.2.1. 일반검수진행대상매입관리정보수정
                    callSharedBizComponentByDirect("ep.EPSCSBase", "fUGnrlTlyProgrTbEpCstPrchsMgmt", rowDs, onlineCtx);                     
                    // 3.2.2. 일반검수진행매입상세정보삭제
                    callSharedBizComponentByDirect("ep.EPSCSBase", "fDGnrlTlyProgrTbEpCstPrchsDtl", rowDs, onlineCtx);
                    for(int j = 0 ; j < rsCmptList.getRecordCount() ; j++){
                        recSub = rsCmptList.getRecord(j);                            
                        rowDsSub.putFieldMap(recSub);
                        rowDsSub.putField("USER_ID", ca.getUserNo());   // 등록자ID 셋팅
                        rowDsSub.putField("PRCH_MGMT_NO", StringUtils.nvlObject(rowDs.getField("PRCH_MGMT_NO"),""));  // 매입관리번호 셋팅        
                        /*
                        if (logger.isDebugEnabled()) {
                            logger.debug("#### 3.2.rowDsSub.toString.Insert #### =======>> "+rowDsSub.toString()+"\n");
                        }
                        */
                        // 3.2.3. 일반검수진행매입상세정보등록
                        callSharedBizComponentByDirect("ep.EPSCSBase", "fIGnrlTlyProgrTbEpCstPrchsDtl", rowDsSub, onlineCtx);                                                      
                    }
                    
                    // 3.2.4. 일반검수진행상담관리정보수정
                    callSharedBizComponentByDirect("ep.EPSCSBase", "fUGnrlTlyProgrTbEpCstConsultMgmt", rowDs, onlineCtx); 
                    
                }else{
                    // 3.3.1. 매입관리번호조회
                    String prchMgntNo = StringUtils.nvlObject(callSharedBizComponentByDirect("ep.EPSCSBase", "fSPrchMgmtNoSeq", rowDs, onlineCtx).getField("PRCH_MGMT_NO"),"");    
                    rowDs.putField("PRCH_MGMT_NO", prchMgntNo);
                    /*
                    if (logger.isDebugEnabled()) {
                        logger.debug("#### 3.3.rowDs.toString.Insert #### =======>> "+rowDs.toString()+"\n");
                    }
                    */
                    // 3.3.2. 일반검수진행매입관리정보등록
                    callSharedBizComponentByDirect("ep.EPSCSBase", "fIGnrlTlyProgrTbEpCstPrchsMgmt", rowDs, onlineCtx);
                    
                    for(int j = 0 ; j < rsCmptList.getRecordCount() ; j++){
                        recSub = rsCmptList.getRecord(j);                            
                        rowDsSub.putFieldMap(recSub);
                        rowDsSub.putField("USER_ID", ca.getUserNo());   // 등록자ID 셋팅
                        rowDsSub.putField("PRCH_MGMT_NO", prchMgntNo);  // 매입관리번호 셋팅
                        // 3.3.3. 일반검수진행매입상세정보등록
                        /*
                        if (logger.isDebugEnabled()) {
                            logger.debug("#### 3.3.rowDsSub.toString.Insert #### =======>> "+rowDsSub.toString()+"\n");
                        }
                        */
                        callSharedBizComponentByDirect("ep.EPSCSBase", "fIGnrlTlyProgrTbEpCstPrchsDtl", rowDsSub, onlineCtx);                        
                    }
                    
                    // 3.3.4. 일반검수진행상담관리정보수정
                    callSharedBizComponentByDirect("ep.EPSCSBase", "fUGnrlTlyProgrTbEpCstConsultMgmt", rowDs, onlineCtx);
                    
                }                                
            }         
            
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }
        
    
        return responseData;
    }

    /**
	 * <pre>[PM]일반감정검수등록목록재조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:04
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNSL_MGMT_NO [상담관리번호]
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
	 *		- field : AGN_ORG_CD [대리점 조직 코드]
	 *		- field : AFFIL_AGN [소속 대리점]
	 *		- field : AFFIL_AGN_NM [소속대리점명]
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
	 *	- record : RS_EQP_GRADE_LIST
	 *		- field : PRC_GRADE [가격 등급]
	 *		- field : RATE [등급 비율]
	 *		- field : PRC_RT_SEQ [가격 비율 순번]
	 *		- field : STRD_INCEN [기준 인센티브]
	 *	- record : RS_EQP_GRADE_CHG_LIST
	 *		- field : PRC_GRADE [가격 등급]
	 *		- field : RATE [등급 비율]
	 *		- field : PRC_RT_SEQ [가격 비율 순번]
	 *		- field : STRD_INCEN [기준 인센티브]
	 *	- field : PRCH_MGMT_COUNT [매입관리건수]
	 * </pre>
	 */
    public IDataSet pSGnrlJdgTlyRegRpetLst(IDataSet requestData, IOnlineContext onlineCtx) {
        Log logger = LogManager.getFwkLog();
        IDataSet responseData = new DataSet();
        IRecordSet rs = null;
        IRecord rec = null;
        IDataSet rowDs = new DataSet();
        
        try {
            /*
            if (logger.isDebugEnabled()) {                
                logger.debug("***** 상담 관리 번호 =>> " +StringUtils.nvlObject(requestData.getField("CNSL_MGMT_NO"),"")+"\n");                    
            } 
            */           
            int prchMgmtCount =  Integer.parseInt(String.valueOf(callSharedBizComponentByDirect("ep.EPSCSBase", "fSTbEpCstPrchsMgmtCount", rowDs, onlineCtx).getField("PRCH_MGMT_COUNT")));            
            if(prchMgmtCount>0){
                responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSPrchGnrlJdgTlyRegLst", requestData, onlineCtx).getRecordSet("RS_TLY_REG_LIST"));
                rs = responseData.getRecordSet("RS_TLY_REG_LIST");
                if(rs.getRecordCount() > 0){
                    rec = rs.getRecord(0);
                    rowDs.putFieldMap(rec);
                    responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSPrchGnrlJdgTlyRegCmptLst", rowDs, onlineCtx).getRecordSet("RS_TLY_REG_CMPT_LIST"));                    
                }                                
            }else{
                responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSGnrlJdgTlyRegLst", requestData, onlineCtx).getRecordSet("RS_TLY_REG_LIST"));
                rs = responseData.getRecordSet("RS_TLY_REG_LIST");
                if(rs.getRecordCount() > 0){
                    rec = rs.getRecord(0);
                    rowDs.putFieldMap(rec);
                    responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSGnrlJdgTlyRegCmptLst", rowDs, onlineCtx).getRecordSet("RS_TLY_REG_CMPT_LIST"));                    
                }    
            }
            
            responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSEqpMdlInfo", rowDs, onlineCtx).getRecordSet("RS_EQP_INFO_LIST")); // 단말기정보 조회
            responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSEqpMdlGradeInfo", rowDs, onlineCtx).getRecordSet("RS_EQP_GRADE_LIST"));   // 단말기 등급조회
            responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSEqpMdlGradeChgDamtInfo", requestData, onlineCtx).getRecordSet("RS_EQP_GRADE_CHG_LIST"));   // 단말기 등급변경차액정보조회
                                 
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
	 * <pre>[PM]재감정검수등록목록조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:04
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
	 *	- record : RS_EQP_GRADE_LIST
	 *		- field : PRC_GRADE [가격 등급]
	 *		- field : RATE [등급 비율]
	 *		- field : PRC_RT_SEQ [가격 비율 순번]
	 *		- field : STRD_INCEN [기준 인센티브]
	 *	- record : RS_EQP_GRADE_CHG_LIST
	 *		- field : PRC_GRADE [가격 등급]
	 *		- field : RATE [등급 비율]
	 *		- field : PRC_RT_SEQ [가격 비율 순번]
	 *		- field : STRD_INCEN [기준 인센티브]
	 * </pre>
	 */
    public IDataSet pSRpetJdgTlyRegLst(IDataSet requestData, IOnlineContext onlineCtx) {
        Log logger = LogManager.getFwkLog();
        IDataSet responseData = new DataSet();
        String prgGb = "";   // 검수등록대상구분
        try {          
            /*
            if (logger.isDebugEnabled()) {                
                logger.debug("***** 상담 관리 번호 =>> " +StringUtils.nvlObject(requestData.getField("CNSL_MGMT_NO"),"")+"\n");    
                logger.debug("***** 단말기모델코드 =>> " +StringUtils.nvlObject(requestData.getField("EQP_MDL_CD"),"")+"\n");
                logger.debug("***** 가입방법 =>> " +StringUtils.nvlObject(requestData.getField("SCRB_MTHD"),"")+"\n");
                logger.debug("***** 검수등록대상구분 =>> " +StringUtils.nvlObject(requestData.getField("PRG_GB"),"")+"\n");
            }
            */
            prgGb = StringUtils.nvlObject(requestData.getField("PRG_GB"),""); // 검수등록대상구분
            
            if("P".equals(prgGb)){
                /*
                // 검수등록대상구분
                P:매입제외목록
                C:송금목록/요금공제목록/정책대상목록/강제매입목록/아시아나마일리지목록/매입목록/매입대상목록
                S:삭제
                */                                
                responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSPrchRpetJdgTlyRegLst", requestData, onlineCtx).getRecordSet("RS_TLY_REG_LIST"));
                responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSPrchRpetJdgTlyRegCmptLst", requestData, onlineCtx).getRecordSet("RS_TLY_REG_CMPT_LIST"));                
            }else{
                /*
                // 검수등록대상구분
                N: 검수대상목록
                F:품질검사등록대상목록/품질검사등록취소목록                
                */   
                responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSRpetJdgTlyRegLst", requestData, onlineCtx).getRecordSet("RS_TLY_REG_LIST"));
                responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSRpetJdgTlyRegCmptLst", requestData, onlineCtx).getRecordSet("RS_TLY_REG_CMPT_LIST"));
            }
            responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSEqpMdlInfo", requestData, onlineCtx).getRecordSet("RS_EQP_INFO_LIST")); // 단말기정보 조회
            responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSEqpMdlGradeInfo", requestData, onlineCtx).getRecordSet("RS_EQP_GRADE_LIST"));   // 단말기 등급조회
            responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSEqpMdlGradeChgDamtInfo", requestData, onlineCtx).getRecordSet("RS_EQP_GRADE_CHG_LIST"));   // 단말기 등급변경차액정보조회
        
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
	 * <pre>[PM]재감정검수등록목록재조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:04
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
	 *	- record : RS_EQP_GRADE_LIST
	 *		- field : PRC_GRADE [가격 등급]
	 *		- field : RATE [등급 비율]
	 *		- field : PRC_RT_SEQ [가격 비율 순번]
	 *		- field : STRD_INCEN [기준 인센티브]
	 *	- record : RS_EQP_GRADE_CHG_LIST
	 *		- field : PRC_GRADE [가격 등급]
	 *		- field : RATE [등급 비율]
	 *		- field : PRC_RT_SEQ [가격 비율 순번]
	 *		- field : STRD_INCEN [기준 인센티브]
	 * </pre>
	 */
    public IDataSet pSRpetJdgTlyRegRpetLst(IDataSet requestData, IOnlineContext onlineCtx) {
        Log logger = LogManager.getFwkLog();
        IDataSet responseData = new DataSet();
        IRecordSet rs = null;
        IRecord rec = null;
        IDataSet rowDs = new DataSet();
        
        try {            
            /*
            if (logger.isDebugEnabled()) {                
                logger.debug("***** 상담 관리 번호 =>> " +StringUtils.nvlObject(requestData.getField("CNSL_MGMT_NO"),"")+"\n");                    
            }
            */
            rowDs.putField("JDC_CL", "RE"); // 재감정 조건 추가.
            int prchMgmtCount =  Integer.parseInt(String.valueOf(callSharedBizComponentByDirect("ep.EPSCSBase", "fSTbEpCstPrchsMgmtCount", rowDs, onlineCtx).getField("PRCH_MGMT_COUNT")));            
            if(prchMgmtCount>0){
                responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSPrchRpetJdgTlyRegLst", requestData, onlineCtx).getRecordSet("RS_TLY_REG_LIST"));
                rs = responseData.getRecordSet("RS_TLY_REG_LIST");
                if(rs.getRecordCount() > 0){
                    rec = rs.getRecord(0);
                    rowDs.putFieldMap(rec);
                    responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSPrchRpetJdgTlyRegCmptLst", rowDs, onlineCtx).getRecordSet("RS_TLY_REG_CMPT_LIST"));                    
                }                                
            }else{
                responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSRpetJdgTlyRegLst", requestData, onlineCtx).getRecordSet("RS_TLY_REG_LIST"));
                rs = responseData.getRecordSet("RS_TLY_REG_LIST");
                if(rs.getRecordCount() > 0){
                    rec = rs.getRecord(0);
                    rowDs.putFieldMap(rec);
                    responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSRpetJdgTlyRegCmptLst", rowDs, onlineCtx).getRecordSet("RS_TLY_REG_CMPT_LIST"));                    
                }    
            }
            
            responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSEqpMdlInfo", rowDs, onlineCtx).getRecordSet("RS_EQP_INFO_LIST")); // 단말기정보 조회
            responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSEqpMdlGradeInfo", rowDs, onlineCtx).getRecordSet("RS_EQP_GRADE_LIST"));   // 단말기 등급조회
            responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSEqpMdlGradeChgDamtInfo", requestData, onlineCtx).getRecordSet("RS_EQP_GRADE_CHG_LIST"));   // 단말기 등급변경차액정보조회
            
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
	 * <pre>[PM]재검수대상등록정보</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:04
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
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
    public IDataSet pSaveRpetTlyRegInfo(IDataSet requestData, IOnlineContext onlineCtx) {
        Log logger = LogManager.getFwkLog();
        IDataSet responseData = new DataSet();
        IRecordSet rsList = requestData.getRecordSet("RS_TLY_REG_LIST");
        IRecordSet rsCmptList = requestData.getRecordSet("RS_TLY_REG_CMPT_LIST");                
        CommonArea ca = getCommonArea(onlineCtx);        
        IRecord rec = null;
        IRecord recSub = null;
        String updateCount = "";
        IDataSet rowDs = new DataSet();
        IDataSet rowDsSub = new DataSet();
        /*
        if (logger.isDebugEnabled()) {          
            logger.debug("#### rsList.toString #### =======>> "+rsList.getRecordCount()+"\n");
            logger.debug("#### rsCmptList.toString #### =======>> "+rsCmptList.getRecordCount()+"\n");           
        }
        */
        try {
            for(int i = 0 ; i < rsList.getRecordCount() ; i++){
                rec = rsList.getRecord(i);                            
                rowDs.putFieldMap(rec);
                rowDs.putField("USER_ID", ca.getUserNo());
                
                /*
                 *  암호화 대상 컬럼 추가
                 *  Ex.)
                 *  requestData.putField("EML_ADDR_ENPT", HpcUtils.encodeByAES(requestData.getField("EML_ADDR")));
                 *  requestData.putField("EMIL_ADDR_MASK", HpcUtils.maskingEmail(requestData.getField("EML_ADDR")));
                 *  
                 *  원천 데이터 암호화.
                 *  원천 데이터 마스킹처리.             
                 */
                rowDs.putField("DPSTR", HpcUtils.maskingName(rec.get("DPSTR")));           /* 예금주 마스킹 */
                rowDs.putField("DPSTR_ENPT", HpcUtils.encodeByAES(rec.get("DPSTR")));      /* 예금주 암호화 */
                rowDs.putField("TEL_NO", HpcUtils.maskingTelNo(rec.get("TEL_NO")));        /* 전화번호 마스킹 */                    
                rowDs.putField("TEL_NO_ENPT", HpcUtils.encodeByAES(rec.get("TEL_NO")));    /* 전화번호 암호화 */
                rowDs.putField("ETC_ADDR", HpcUtils.maskingAddrDtl(rec.get("ETC_ADDR")));  /* 상세주소 마스킹 */
                rowDs.putField("ETC_ADDR_ENPT", HpcUtils.encodeByAES(rec.get("ETC_ADDR")));/* 상세주소 암호화 */ 
                rowDs.putField("ACCO_NO", HpcUtils.maskingAccountNo(rec.get("ACCO_NO")));  /* 계좌번호 마스킹 */
                rowDs.putField("ACCO_NO_ENPT", HpcUtils.encodeByAES(rec.get("ACCO_NO")));  /* 계좌번호 암호화 */
                rowDs.putField("SVC_NO", HpcUtils.maskingTelNo(rec.get("SVC_NO")));        /* 서비스번호 마스킹 */
                rowDs.putField("SVC_NO_ENPT", HpcUtils.encodeByAES(rec.get("SVC_NO")));    /* 서비스번호 암호화 */  

                
                // 1. 일반검수진행대상 상담수정 정보조회
                updateCount = StringUtils.nvlObject(callSharedBizComponentByDirect("ep.EPSCSBase", "fSGnrlTlyProgrConsultCount", rowDs, onlineCtx).getField("UPDATE_COUNT"),"");
                /*
                if (logger.isDebugEnabled()) {          
                    logger.debug("#### rowDs.toString #### =======>> "+rowDs.toString()+"\n");
                    logger.debug("**** rowDs.UPDATE_COUNT #### =======>> "+StringUtils.nvlObject(rowDs.getField("UPDATE_COUNT"),"")+"\n");                                                
                    logger.debug("**** updateCount #### =======>> "+updateCount+"\n");                                       
                }
                */
                // 2. 상담관리정보 Update Count 비교
                if(!updateCount.equals(StringUtils.nvlObject(rowDs.getField("UPDATE_COUNT"),""))){
                    throw new BizRuntimeException("DMS00111", new String[] {}); // 조회 후 다시 처리하십시요.                                                            
                }
                
                // 3. 매입관리번호조회
                String prchMgntNo = StringUtils.nvlObject(callSharedBizComponentByDirect("ep.EPSCSBase", "fSPrchMgmtNoSeq", rowDs, onlineCtx).getField("PRCH_MGMT_NO"),"");
                
                if(!StringUtils.isEmpty(prchMgntNo)){
                    rowDs.putField("PRCH_MGMT_NO", prchMgntNo);   
                    /*
                    if (logger.isDebugEnabled()) {
                        logger.debug("#### rowDs.toString.Insert #### =======>> "+rowDs.toString()+"\n");
                    } 
                    */                   
                    // 4. 재검수진행매입관리정보등록
                    callSharedBizComponentByDirect("ep.EPSCSBase", "fIRpetTlyTbEpCstPrchsMgmt", rowDs, onlineCtx);
                    
                    for(int j = 0 ; j < rsCmptList.getRecordCount() ; j++){
                        recSub = rsCmptList.getRecord(j);                            
                        rowDsSub.putFieldMap(recSub);
                        rowDsSub.putField("USER_ID", ca.getUserNo());   // 등록자ID 셋팅
                        rowDsSub.putField("PRCH_MGMT_NO", prchMgntNo);  // 매입관리번호 셋팅                        
                        // 5. 재검수진행매입상세정보등록
                        if (logger.isDebugEnabled()) {
                            logger.debug("#### rowDsSub.toString.Insert #### =======>> "+rowDsSub.toString()+"\n");
                        }
                        callSharedBizComponentByDirect("ep.EPSCSBase", "fIRpetTlyTbEpCstPrchsDtl", rowDsSub, onlineCtx);                        
                    }                    
                    // 6. 재검수진행상담관리정보수정
                    callSharedBizComponentByDirect("ep.EPSCSBase", "fURpetTlyTbEpCstConsultMgmt", rowDs, onlineCtx);
                }                                                               
            }                        
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }
        
        return responseData;
    }

    /**
	 * <pre>[PM]검수단말기모델정보조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:04
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_TLY_REG_LIST
	 *		- field : CNSL_MGMT_NO [상담 관리 번호]
	 *		- field : EQP_MDL_CD [단말기 모델 코드]
	 *		- field : EQP_MDL_NM [단말기 모델명]
	 *		- field : EQP_SER_NO [단말기 일련 번호]
	 *		- field : CNSL_DT [상담 일자]
	 *		- field : DEAL_CO_NM [상담 거래처명]
	 *		- field : AGN_ORG_CD [대리점 조직 코드]
	 *		- field : AFFIL_AGN [소속 대리점]
	 *		- field : AFFIL_AGN_NM [소속대리점명]
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
	 *	- record : RS_EQP_GRADE_LIST
	 *		- field : PRC_GRADE [가격 등급]
	 *		- field : RATE [등급 비율]
	 *		- field : PRC_RT_SEQ [가격 비율 순번]
	 *		- field : STRD_INCEN [기준 인센티브]
	 * </pre>
	 */
	public IDataSet pSTlyEqpMdlInfoLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    Log logger = LogManager.getFwkLog();
        IDataSet responseData = new DataSet();
        IRecordSet rsList = requestData.getRecordSet("RS_TLY_REG_LIST");
        IRecord rec = null;
        IDataSet rowDs = new DataSet();
        
        try {
            for(int i = 0 ; i < rsList.getRecordCount() ; i++){            
                rec = rsList.getRecord(i);                            
                rowDs.putFieldMap(rec);
                /*
                if (logger.isDebugEnabled()) {
                    logger.debug("***** 상담 관리 번호 =>> " +StringUtils.nvlObject(rowDs.getField("CNSL_MGMT_NO"),"")+"\n");    
                    logger.debug("***** 단말기모델코드 =>> " +StringUtils.nvlObject(rowDs.getField("EQP_MDL_CD"),"")+"\n");
                } 
                */               
                responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSGnrlJdgTlyRegCmptLst", rowDs, onlineCtx).getRecordSet("RS_TLY_REG_CMPT_LIST"));
                responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSEqpMdlInfo", rowDs, onlineCtx).getRecordSet("RS_EQP_INFO_LIST")); // 단말기정보 조회
                responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSEqpMdlGradeInfo", rowDs, onlineCtx).getRecordSet("RS_EQP_GRADE_LIST"));   // 단말기 등급조회
                responseData.putRecordSet(callSharedBizComponentByDirect("ep.EPSCSBase", "fSEqpMdlGradeChgDamtInfo", rowDs, onlineCtx).getRecordSet("RS_EQP_GRADE_CHG_LIST"));   // 단말기 등급변경차액정보조회
            }    
            
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }
        
        
    
        return responseData;
    }

    /**
	 *
	 *
	 * @author 정동현 (jjddhh123)
	 * @since 2015-09-01 13:57:04
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
	public IDataSet pSTlyEqpMdlColorLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        
        try {
            // 1. FM 호출
            responseData = callSharedBizComponentByDirect("ep.EPSCSBase", "fSTlyEqpMdlColorLst", requestData, onlineCtx);
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
	 * <pre>[PM]재검수대상고객정보</pre>
	 *
	 * @author 이하나 (hana526)
	 * @since 2015-09-01 13:57:04
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
	public IDataSet pSaveRpetTlyCustInfo(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    IDataSet rowDs = new DataSet();
        IRecordSet rsList = requestData.getRecordSet("RS_TLY_REG_LIST");
        IRecord rec = null;
        CommonArea ca = getCommonArea(onlineCtx); 
        String updateCount = "";

        try {
        	
        	if (rsList != null) {  
        		
	                rec = rsList.getRecord(0);                            
	                rowDs.putFieldMap(rec);
	                rowDs.putField("USER_ID", ca.getUserNo());
	                
	                /*
	                 *  암호화 대상 컬럼 추가            
	                 */
	                rowDs.putField("DPSTR", HpcUtils.maskingName(rec.get("DPSTR")));           /* 예금주 마스킹 */
	                rowDs.putField("DPSTR_ENPT", HpcUtils.encodeByAES(rec.get("DPSTR")));      /* 예금주 암호화 */
	                rowDs.putField("TEL_NO", HpcUtils.maskingTelNo(rec.get("TEL_NO")));        /* 전화번호 마스킹 */                    
	                rowDs.putField("TEL_NO_ENPT", HpcUtils.encodeByAES(rec.get("TEL_NO")));    /* 전화번호 암호화 */
	                rowDs.putField("ETC_ADDR", HpcUtils.maskingAddrDtl(rec.get("ETC_ADDR")));  /* 상세주소 마스킹 */
	                rowDs.putField("ETC_ADDR_ENPT", HpcUtils.encodeByAES(rec.get("ETC_ADDR")));/* 상세주소 암호화 */ 
	                rowDs.putField("ACCO_NO", HpcUtils.maskingAccountNo(rec.get("ACCO_NO")));  /* 계좌번호 마스킹 */
	                rowDs.putField("ACCO_NO_ENPT", HpcUtils.encodeByAES(rec.get("ACCO_NO")));  /* 계좌번호 암호화 */
	                rowDs.putField("SVC_NO", HpcUtils.maskingTelNo(rec.get("SVC_NO")));        /* 서비스번호 마스킹 */
	                rowDs.putField("SVC_NO_ENPT", HpcUtils.encodeByAES(rec.get("SVC_NO")));    /* 서비스번호 암호화 */  	
	                
//	                // 1. 재검수진행 상담수정 정보조회
//	                updateCount = StringUtils.nvlObject(callSharedBizComponentByDirect("ep.EPSCSBase", "fSGnrlTlyProgrConsultCount", rowDs, onlineCtx).getField("UPDATE_COUNT"),"");
//	                
//	                // 2. 상담관리정보 Update Count 비교
//	                if(!updateCount.equals(StringUtils.nvlObject(rowDs.getField("UPDATE_COUNT"),""))){
//	                    throw new BizRuntimeException("DMS00111", new String[] {}); // 조회 후 다시 처리하십시요.                                                            
//	                }               
	                  
	                // 3. 고객정보 수정로직
					//3-1. 재검수진행 매입관리정보등록(기존 일반감정)					
					callSharedBizComponentByDirect("ep.EPSCSBase", "fURpetJdgTlyCustPrchsMgmt", rowDs, onlineCtx);
					//3-2. 재검수진행 접수고객정보 수정로직 (신규)
		            callSharedBizComponentByDirect("ep.EPSCSBase", "fURpetJdgTlyCustConsultMgmt", rowDs, onlineCtx);
											
							            
            }                                                                                          
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }

	    return responseData;		
	}  
}