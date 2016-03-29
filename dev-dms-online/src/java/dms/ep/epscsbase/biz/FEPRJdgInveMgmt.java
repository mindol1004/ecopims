package dms.ep.epscsbase.biz;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.exception.BizRuntimeException;
import nexcore.framework.core.util.DateUtils;
import fwk.common.CommonArea;
import fwk.utils.HpcUtils;
import fwk.utils.PagenateUtils;


/**
 * <ul>
 * <li>업무 그룹명 : dms/에코폰</li>
 * <li>단위업무명: [FU]재감정재고관리</li>
 * <li>설  명 : <pre>[FU]재감정재고관리</pre></li>
 * <li>작성일 : 2015-08-19 13:30:36</li>
 * <li>작성자 : 김윤환 (kyh0904)</li>
 * </ul>
 *
 * @author 김윤환 (kyh0904)
 */
public class FEPRJdgInveMgmt extends fwk.base.FunctionUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public FEPRJdgInveMgmt(){
		super();
	}

	/**
	 * <pre>[FM]재감정현황조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-08-19 13:30:36
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : DT_GB [일자구분]
	 *	- field : FROM_DT [시작일자]
	 *	- field : TO_DT [종료일자]
	 *	- field : AFFIL_AGN_NM [대리점명]
	 *	- field : AFFIL_AGN [대리점코드]
	 *	- field : CNSL_DEALCO_NM [상담처코드]
	 *	- field : CNSL_DEALCO_CD [상담처명]
	 *	- field : EQP_MDL_NM [단말기 모델코드]
	 *	- field : EQP_MDL_CD [단말기 모델명]
	 *	- field : EQP_SER_NO [단말기 일련번호]
	 *	- field : DPSTR [고객명]
	 *	- field : CNSL_MGMT_NO [상담관리번호]
	 *	- field : SRH_GB [찾기구분]
	 *	- field : PRE_DC_CL [선할인여부]
	 *	- field : SCRB_MTHD [가입방법]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_JDG_PRST_LST
	 *		- field : ROWNO [번호]
	 *		- field : PROGR_ST [진행상태]
	 *		- field : CNSL_DT [상담일자]
	 *		- field : IN_CONF_DT [입고확정일자]
	 *		- field : CLCT_DT [회수일자]
	 *		- field : PROD_CHRTIC_1 [상품특성]
	 *		- field : MFACT_CD [제조사 코드]
	 *		- field : EQP_MDL_NM [단말기 모델명]
	 *		- field : EQP_MDL_CD [단말기 모델코드]
	 *		- field : EQP_SER_NO [단말기 일련번호]
	 *		- field : AFFIL_ORG_ID [소속조직코드(조직)]
	 *		- field : AFFIL_ORG_NM [소속조직명(조직)]
	 *		- field : AGN_ORG_CD [대리점 조직 코드]
	 *		- field : AFFIL_AGN [소속 대리점코드(팀코드)]
	 *		- field : AFFIL_AGN_NM [소속 대리점명(팀명)]
	 *		- field : CNSL_DEALCO_CD [상담 거래처코드(상담처)]
	 *		- field : CNSL_DEALCO_NM [상담 거래처명(상담처)]
	 *		- field : DPSTR [예금주]
	 *		- field : DPSTR_ENPT [예금주 암호화]
	 *		- field : ONING_DT [개통일자]
	 *		- field : SKN_EQP_ST [SKN 단말기상태]
	 *		- field : EQP_PRC [단말기 가격]
	 *		- field : U_STRD_INCEN [UKEY 기준 인센티브]
	 *		- field : STRD_INCEN_SCOP_AMT [기준 인센티브 범위금액]
	 *		- field : SKN_JDG_AMT [SKN 상담금액]
	 *		- field : EQP_ST [단말기상태]
	 *		- field : EQP_ST_DTAIL [단말기 상태 세부]
	 *		- field : SALE_EQP_ST [판매등급]
	 *		- field : ADJ_AMT [조정금액]
	 *		- field : PRCH_AMT [매입금액]
	 *		- field : STRD_INCEN [기준 인센티브]
	 *		- field : DEC_INCV_AMT [확정인센티브]
	 *		- field : SKN_SMPL_JDG_DAMT [SKN 감정 차액]
	 *		- field : TOT_AMT [총금액]
	 *		- field : SKN_JDG_CL [SKN 감정구분]
	 *		- field : SKN_JDG_CL_SUB [SKN 감정구분 서브]
	 *		- field : EVALCNSLR_SUGG [평가원의견]
	 *		- field : INPT [검수자]
	 *		- field : TLY_DT [검수일자]
	 *		- field : SKN_PROC_YN [SKN 처리여부]
	 *		- field : SKN_PROC_DT [SKN 처리일자]
	 *		- field : CNSL_MGMT_NO [상담 관리 번호]
	 *		- field : OLDEQ_PAY_DT_1 [송금일자]
	 *		- field : OLDEQ_PAY_DT_2 [요금공제일자]
	 *		- field : BOX_NO [박스 번호]
	 *		- field : IMEI [IMEI]
	 *		- field : PRE_DC_CL [선할인구분]
	 *		- field : IN_USER_NM [입고처리담당자]
	 *		- field : BOX_PROC_CHRGR [박스처리담당자]
	 *		- field : BOX_DT [박스처리일자]
	 *		- field : SCRN_AFIMG_YN [화면잔상여부]
	 *		- field : CMCT_CO [통신사]
	 *		- field : SCRB_MTHD [가입방법]
	 *		- field : HEADQ_NM [본부 명]
	 *		- field : MKT_TEAM_CD [마케팅 팀 코드]
	 *		- field : MKT_TEAM_NM [마케팅 팀 명]
	 *		- field : PRCH_CHG_DAMT_AMT [매입 변경 차액 금액]
	 *		- field : BOX_RMK [박스코멘트]
	 *		- field : PWR_ST [전원불량]
	 *		- field : RECG_ST [충전불량]
	 *		- field : USIM_ST [USIM불량]
	 *		- field : SBELL_ST [벨소리불량]
	 *		- field : VIBR_ST [진동불량]
	 *		- field : SPK_ST [스피커불량]
	 *		- field : ILRM_SENSOR_ST [조도센서불량]
	 *		- field : GRCP_ST [자이로센서불량]
	 *		- field : APRC_SENSOR_ST [근접센서불량]
	 *		- field : CAMR_ST [카메라불량]
	 *		- field : BATER_ST [B/T불량]
	 *		- field : AFIMG_ST [잔상불량]
	 *		- field : TOUCH_ST [터치불량]
	 *		- field : WIFI_ST [WIFI불량]
	 *		- field : STAIN_ST [얼룩]
	 *		- field : BRUI_ST [멍]
	 *		- field : SQUS_ST [눌림]
	 *		- field : LQFD_INFLO_ST [액상유입]
	 *		- field : LED_ST [LED파손]
	 *		- field : BUTN_ST [버튼불량]
	 *		- field : BOTM_TOUCH_ST [하단터치메뉴]
	 *		- field : BODY_ST [몸체미세휨]
	 *		- field : DMB_ST [DMB안테나손상]
	 *		- field : LCGLS_ST [액정유리]
	 *		- field : CHIP_ST [이나감]
	 *		- field : FGSC_ST [지문인식]
	 *		- field : CAMR_WINDO_ST [카메리윈도우손상]
	 *		- field : EDGE_ST [테두리및몸체]
	 *		- field : EQP_ST_RMK [대표오감정사유]
	 *		- field : UKEY_SUB_CD [매장코드]
	 *		- field : EQP_COLOR_CD [단말기색상코드]
	 *		- field : AGN_DDCT_YN [대리점차감여부]
	 * </pre>
	 */
	public IDataSet fInqRJdgPrstList(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        IDataSet dsCnt = new DataSet();
        IDataSet reqDs = (IDataSet) requestData.clone();//원거래의 DataSet의 훼손을 막기 위한 Clone
        IRecordSet rsPagingList = null;
        int intTotalCnt = 0;
        
        try {
            // 1. DU lookup
            DEPRJdgInveMgmt dEPRJdgInveMgmt = (DEPRJdgInveMgmt)lookupDataUnit(DEPRJdgInveMgmt.class);
            
            // 2. TOTAL COUNT DM호출
            dsCnt = dEPRJdgInveMgmt.dSRJdgPrstListTotCnt(requestData, onlineCtx);   // 재감정회수일자누락목록 총건수        
            intTotalCnt = Integer.parseInt(dsCnt.getField("TOTAL_CNT"));     
            PagenateUtils.setPagenatedParamsToDataSet(reqDs);
            
            // 3. LISTDM호출
            responseData = dEPRJdgInveMgmt.dSRJdgPrstListPaging(reqDs, onlineCtx);    // 재감정회수일자누락목록 조회       
            rsPagingList = responseData.getRecordSet("RS_JDG_PRST_LST");          
            PagenateUtils.setPagenatedParamToRecordSet(rsPagingList, reqDs, intTotalCnt);  
            
        } catch ( BizRuntimeException e ) {
            throw e; //시스템 오류가 발생하였습니다.
        }
        
        return responseData;
	}

    /**
	 * <pre>[FM]재감정재고취소목록조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-08-19 13:30:36
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNSL_MGMT_NO [상담관리번호]
	 *	- field : BOX_NO [박스번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_RJDG_INVE_CNCL_LIST
	 *		- field : ROWNO [순번]
	 *		- field : CNSL_MGMT_NO [상담관리번호]
	 *		- field : EQP_SER_NO [단말기 일련 번호]
	 *		- field : EQP_MDL_CD [단말기 모델 코드]
	 *		- field : EQP_MDL_NM [단말기 모델 명]
	 *		- field : EQP_COLOR_CD [단말기 색상 코드]
	 *		- field : SKN_CL [SKN 구분]
	 *		- field : PROGR_ST [진행상태]
	 *		- field : SKN_PROC_DT [SKN 처리 일자]
	 *		- field : G_EQP_SER_NO [G_단말기일련번호]
	 * </pre>
	 */
	public IDataSet fSRJdgInveCnclLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        IDataSet dsCnt = new DataSet();
        IRecordSet rsPagingList = null;
        int intTotalCnt = 0;
        
        try {
            // 1. DU lookup
            DEPRJdgInveMgmt dEPRJdgInveMgmt = (DEPRJdgInveMgmt)lookupDataUnit(DEPRJdgInveMgmt.class);
            // 2. TOTAL COUNT DM호출
            dsCnt = dEPRJdgInveMgmt.dSRdgInveCnclTotCnt(requestData, onlineCtx);          
            intTotalCnt = Integer.parseInt(dsCnt.getField("TOTAL_CNT"));            
            PagenateUtils.setPagenatedParamsToDataSet(requestData);
            
            // 3. LISTDM호출
            responseData = dEPRJdgInveMgmt.dSRJdgInveCnclPaging(requestData, onlineCtx);            
            rsPagingList = responseData.getRecordSet("RS_RJDG_INVE_CNCL_LIST");          
            PagenateUtils.setPagenatedParamToRecordSet(rsPagingList, requestData, intTotalCnt);                     
        } catch ( BizRuntimeException e ) {
            throw e;
        }
        return responseData;
    }

    /**
	 * <pre>[FM]재고삭제</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-08-19 13:30:36
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : ROWNO [순번]
	 *	- field : CNSL_MGMT_NO [상담관리번호]
	 *	- field : EQP_SER_NO [단말기 일련 번호]
	 *	- field : EQP_MDL_CD [단말기 모델 코드]
	 *	- field : EQP_MDL_NM [단말기 모델 명]
	 *	- field : EQP_COLOR_CD [단말기 색상 코드]
	 *	- field : SKN_CL [SKN 구분]
	 *	- field : PROGR_ST [진행상태]
	 *	- field : SKN_PROC_DT [SKN 처리 일자]
	 *	- field : G_EQP_SER_NO [G_단말기일련번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fDTbEpDisDis(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();        
        try {        
            // 1. DU lookup
            DEPRJdgInveMgmt dEPRJdgInveMgmt = (DEPRJdgInveMgmt)lookupDataUnit(DEPRJdgInveMgmt.class);
            responseData = dEPRJdgInveMgmt.dDTbEpDisDis(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }
    
        return responseData;
    }

    /**
	 * <pre>[FM]재고금액삭제</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-08-19 13:30:36
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : ROWNO [순번]
	 *	- field : CNSL_MGMT_NO [상담관리번호]
	 *	- field : EQP_SER_NO [단말기 일련 번호]
	 *	- field : EQP_MDL_CD [단말기 모델 코드]
	 *	- field : EQP_MDL_NM [단말기 모델 명]
	 *	- field : EQP_COLOR_CD [단말기 색상 코드]
	 *	- field : SKN_CL [SKN 구분]
	 *	- field : PROGR_ST [진행상태]
	 *	- field : SKN_PROC_DT [SKN 처리 일자]
	 *	- field : G_EQP_SER_NO [G_단말기일련번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fDTbEpDisDisAmt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();        
        try {        
            // 1. DU lookup
            DEPRJdgInveMgmt dEPRJdgInveMgmt = (DEPRJdgInveMgmt)lookupDataUnit(DEPRJdgInveMgmt.class);
            responseData = dEPRJdgInveMgmt.dDTbEpDisDisAmt(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }
        return responseData;
    }

    /**
	 * <pre>[FM]상품재고삭제</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-08-19 13:30:36
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : ROWNO [순번]
	 *	- field : CNSL_MGMT_NO [상담관리번호]
	 *	- field : EQP_SER_NO [단말기 일련 번호]
	 *	- field : EQP_MDL_CD [단말기 모델 코드]
	 *	- field : EQP_MDL_NM [단말기 모델 명]
	 *	- field : EQP_COLOR_CD [단말기 색상 코드]
	 *	- field : SKN_CL [SKN 구분]
	 *	- field : PROGR_ST [진행상태]
	 *	- field : SKN_PROC_DT [SKN 처리 일자]
	 *	- field : G_EQP_SER_NO [G_단말기일련번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fDTbEpDisProdDis(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();        
        try {        
            // 1. DU lookup
            DEPRJdgInveMgmt dEPRJdgInveMgmt = (DEPRJdgInveMgmt)lookupDataUnit(DEPRJdgInveMgmt.class);
            responseData = dEPRJdgInveMgmt.dDTbEpDisProdDis(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }
        return responseData;
    }

    /**
	 * <pre>[FM]매입관리진행상태수정</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-08-19 13:30:36
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : ROWNO [순번]
	 *	- field : CNSL_MGMT_NO [상담관리번호]
	 *	- field : EQP_SER_NO [단말기 일련 번호]
	 *	- field : EQP_MDL_CD [단말기 모델 코드]
	 *	- field : EQP_MDL_NM [단말기 모델 명]
	 *	- field : EQP_COLOR_CD [단말기 색상 코드]
	 *	- field : SKN_CL [SKN 구분]
	 *	- field : PROGR_ST [진행상태]
	 *	- field : SKN_PROC_DT [SKN 처리 일자]
	 *	- field : G_EQP_SER_NO [G_단말기일련번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fUTbEpCstPrchsProgrSt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();        
        try {        
            // 1. DU lookup
            DEPRJdgInveMgmt dEPRJdgInveMgmt = (DEPRJdgInveMgmt)lookupDataUnit(DEPRJdgInveMgmt.class);
            responseData = dEPRJdgInveMgmt.dUTbEpCstPrchsProgrSt(requestData, onlineCtx);            
        } catch ( BizRuntimeException e ) {
            throw e;
        }
        return responseData;
    }

    /**
	 * <pre>[FM]재고취소처리</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-08-19 13:30:36
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : ROWNO [순번]
	 *	- field : CNSL_MGMT_NO [상담관리번호]
	 *	- field : EQP_SER_NO [단말기 일련 번호]
	 *	- field : EQP_MDL_CD [단말기 모델 코드]
	 *	- field : EQP_MDL_NM [단말기 모델 명]
	 *	- field : EQP_COLOR_CD [단말기 색상 코드]
	 *	- field : SKN_CL [SKN 구분]
	 *	- field : PROGR_ST [진행상태]
	 *	- field : SKN_PROC_DT [SKN 처리 일자]
	 *	- field : G_EQP_SER_NO [G_단말기일련번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fDTbEpDisCnclProc(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet resData = new DataSet();
        IDataSet reqDtlData = new DataSet();
        CommonArea ca = getCommonArea(onlineCtx);

        try {
            String sInoutCl = "100"; // 입출고구분 (입고)
            String sInoutDtlCl = "113"; // 입출고상세구분 (구성품출고취소)
            String sCurrDt = DateUtils.getCurrentDate(); // 현재일자        
            requestData.putField("LS_CHG_USER_ID", ca.getUserNo());
            // 1. DU lookup
            DEPEpEqpDisMgmt dEPEpEqpDisMgmt = (DEPEpEqpDisMgmt) lookupDataUnit(DEPEpEqpDisMgmt.class); //중고단말기재고관리
            DEPRJdgInveMgmt dEPRJdgInveMgmt = (DEPRJdgInveMgmt)lookupDataUnit(DEPRJdgInveMgmt.class);  //재감정재고관리
            // 1.상품재고취소
            dEPRJdgInveMgmt.dDTbEpDisProdDis(requestData, onlineCtx);
            // 2.중고폰상품재고단가삭제
            dEPEpEqpDisMgmt.dDEpEqpProdDisUnitPrcDel(requestData, onlineCtx);
            // 3.중고단말기상품재고구성품삭제
            dEPEpEqpDisMgmt.dDEpEqpProdDisCpntDel(requestData, onlineCtx);
            // 4.중고단말기상품재고입출고이력삭제
            dEPEpEqpDisMgmt.dDEpEqpProdDisInoutHstDel(requestData, onlineCtx);
            
            // 5.중고단말기재고입고삭제
            dEPEpEqpDisMgmt.dDEpEqpDisInDel(requestData, onlineCtx);
            // 6.중고단말기재고입고상세삭제
            dEPEpEqpDisMgmt.dDEpEqpDisInDtlDel(requestData, onlineCtx);
            // 7.중고단말기재고입고구성품삭제
            dEPEpEqpDisMgmt.dDEpEqpDisInCpntDel(requestData, onlineCtx);
            
            // 8.중고단말기재고삭제
            dEPEpEqpDisMgmt.dDEpEqpDisDel(requestData, onlineCtx);
            // 9.중고단말기재고금액삭제
            dEPEpEqpDisMgmt.dDEpEqpDisAmtDel(requestData, onlineCtx);
            
            //입고관리번호채번
            resData = dEPEpEqpDisMgmt.dSInqEpEqpDisInMgmtNo(requestData, onlineCtx);
            requestData.putField("IN_MGMT_NO", resData.getField("IN_MGMT_NO").toString());
            //입고순번채번
            resData = dEPEpEqpDisMgmt.dSInqEpEqpDisInDtlSeq(requestData, onlineCtx);
            requestData.putField("IN_SEQ", resData.getField("IN_SEQ").toString());
            //최종입고이력조회
            resData = dEPEpEqpDisMgmt.dSInqEpEqpDisLastInHst(requestData, onlineCtx);
            IRecordSet rsListTemp = resData.getRecordSet("DIS_LAST_IN_HST");
            
            if(rsListTemp != null && rsListTemp.getRecordCount() > 0){
                reqDtlData.putFieldMap(rsListTemp.getRecordMap(0));
                requestData.putField("CURR_DT", sCurrDt);
                requestData.putField("FS_REG_USER_ID", ca.getUserNo());
                requestData.putField("LS_CHG_USER_ID", ca.getUserNo());
                requestData.putField("LAST_IN_MGMT_NO", reqDtlData.getField("IN_MGMT_NO").toString());
                requestData.putField("INOUT_MGMT_NO", reqDtlData.getField("IN_MGMT_NO").toString());
                requestData.putField("LAST_IN_SEQ", reqDtlData.getField("IN_SEQ").toString());
                requestData.putField("LAST_IN_OUT_SEQ", reqDtlData.getField("IN_OUT_SEQ").toString());
                requestData.putField("IN_OUT_CL", sInoutCl);
                requestData.putField("IN_OUT_DTL_CL", sInoutDtlCl);
                // 10.중고단말기재고출고취소입고처리
                dEPEpEqpDisMgmt.dIEpEqpDisOutCnclInProc(requestData, onlineCtx);
                // 11.중고단말기재고출고취소입고상세처리
                dEPEpEqpDisMgmt.dIEpEqpDisOutCnclInDtlProc(requestData, onlineCtx);
                // 12.중고단말기재고출고취소구성품입고처리
                dEPEpEqpDisMgmt.dIEpEqpDisOutCnclInCpntProc(requestData, onlineCtx);
                // 13.중고단말기재고삭제취소입출고이력등록
                dEPEpEqpDisMgmt.dIEpEqpDisDelCnclInoutHstRgst(requestData, onlineCtx);
                // 14.중고단말기재고출고취소이력등록
                dEPEpEqpDisMgmt.dIEpEqpDisOutCnclHstRgst(requestData, onlineCtx);
            }
        } catch ( BizRuntimeException e ) {
            throw e;
        }
    
        return responseData;
    }
  
}