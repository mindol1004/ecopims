package dms.ep.epscsbase.biz;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.exception.BizRuntimeException;
import fwk.utils.PagenateUtils;


/**
 * <ul>
 * <li>업무 그룹명 : dms/에코폰</li>
 * <li>단위업무명: [FU]판매등급현황관리</li>
 * <li>설  명 : <pre>[FU]판매등급현황관리</pre></li>
 * <li>작성일 : 2015-09-03 15:49:58</li>
 * <li>작성자 : 정동현 (jjddhh123)</li>
 * </ul>
 *
 * @author 정동현 (jjddhh123)
 */
public class FEPSellGradeMgmt extends fwk.base.FunctionUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public FEPSellGradeMgmt(){
		super();
	}

    /**
	 * <pre>[FM]판매등급현황조회</pre>
	 *
	 * @author 정동현 (jjddhh123)
	 * @since 2015-09-03 15:49:58
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : DT_GB [일자구분]
	 *	- field : FROM_DT [시작일자]
	 *	- field : TO_DT [종료일자]
	 *	- field : EQP_MDL_NM [단말기 모델명]
	 *	- field : EQP_MDL_CD [단말기 모델코드]
	 *	- field : EQP_SER_NO [단말기 모델일련번호]
	 *	- field : CNSL_MGMT_NO [상담관리번호]
	 *	- field : SELL_GRADE [판매등급]
	 *	- field : SCRB_MTHD [가입방법]
	 *	- field : JDC_CL [중고폰감정구분]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_SALE_GRADE_LST
	 *		- field : ROWNO [번호]
	 *		- field : PROGR_ST [진행상태]
	 *		- field : CNSL_MGMT_NO [상담관리번호]
	 *		- field : CNSL_DT [상담일자]
	 *		- field : IN_CONF_DT [입고일자]
	 *		- field : MFACT_CD [제조사 코드]
	 *		- field : EQP_MDL_NM [단말기 모델명]
	 *		- field : EQP_SER_NO [단말기 모델일련번호]
	 *		- field : EQP_ST [단말기상태]
	 *		- field : EQP_COLOR_CD [단말기 모델색상코드]
	 *		- field : SELL_GRADE [판매등급]
	 *		- field : PRCH_AMT [매입금액]
	 *		- field : INS_USER_NM [입력자 명]
	 *		- field : PROC_DT [처리일자]
	 *		- field : BOX_NO [박스 번호]
	 *		- field : EQP_ST_DTAIL [단말기 상태세부]
	 *		- field : IMEI [IMEI]
	 *		- field : BOX_RMK [박스코멘트]
	 *		- field : PWR_ST [전원불량]
	 *		- field : RECG_ST [충전불량]
	 *		- field : USIM_ST [USIM불량]
	 *		- field : SBELL_ST [벨소리불량]
	 *		- field : VIBR_ST [진동불량]
	 *		- field : SPK_ST [스피크불량]
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
	 *		- field : CAMR_WINDO_ST [카메라윈도우손상]
	 *		- field : EDGE_ST [테두리및몸체]
	 *		- field : BACK_ST [매입불가]
	 *		- field : BACK_CL [매입불가여부]
	 *		- field : CMCT_CO [통신사]
	 *		- field : SCRB_MTHD [가입방법]
	 *		- field : SKN_EQP_ST [SKN 단말상태]
	 *		- field : AGN_DDCT_YN [대리점 차감 여부]
	 *		- field : EQP_RMK [단말기 감정사유]
	 * </pre>
	 */
	public IDataSet fInqSellGradeList(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        IDataSet dsCnt = new DataSet();
        IDataSet reqDs = (IDataSet) requestData.clone();//원거래의 DataSet의 훼손을 막기 위한 Clone
        IRecordSet rsPagingList = null;
        int intTotalCnt = 0;
        
        try {
            // 1. DU lookup
            DEPSellGradeMgmt dEPSellGradeMgmt = (DEPSellGradeMgmt)lookupDataUnit(DEPSellGradeMgmt.class);
            
            // 2. TOTAL COUNT DM호출
            dsCnt = dEPSellGradeMgmt.dSSellGradeListTotCnt(requestData, onlineCtx);   // 판매등급현황목록 총건수        
            intTotalCnt = Integer.parseInt(dsCnt.getField("TOTAL_CNT"));     
            PagenateUtils.setPagenatedParamsToDataSet(reqDs);
            
            // 3. LISTDM호출
            responseData = dEPSellGradeMgmt.dSSellGradeListPaging(reqDs, onlineCtx);    // 판매등급현황목록 조회       
            rsPagingList = responseData.getRecordSet("RS_SALE_GRADE_LST");          
            PagenateUtils.setPagenatedParamToRecordSet(rsPagingList, reqDs, intTotalCnt);                            
        } catch ( BizRuntimeException e ) {
            throw e; //시스템 오류가 발생하였습니다.
        }
    
        return responseData;
	}

    /**
	 *
	 *
	 * @author 정동현 (jjddhh123)
	 * @since 2015-09-03 15:49:58
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNSL_MGMT_NO [상담관리번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_SALE_GRADE
	 *		- field : CNSL_MGMT_NO [상담관리번호]
	 *		- field : SEQ [순번]
	 *		- field : PWR_ST [전원불량]
	 *		- field : USIM_ST [USIM불량]
	 *		- field : RECG_ST [충전불량]
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
	 *		- field : CAMR_WINDO_ST [카메라윈도우손상]
	 *		- field : EDGE_ST [테두리및몸체]
	 *		- field : BACK_CL [매입불가여부]
	 *		- field : BACK_ST [매입불가]
	 *		- field : SELL_GRADE [판매등급]
	 *		- field : FS_REG_USER_ID [최초 등록 사용자 ID]
	 *		- field : LS_CHG_USER_ID [최종 변경 사용자 ID]
	 *		- field : PRCH_GRADE [매입등급]
	 *		- field : EQP_ST_RMK [단말기상태비고]
	 * </pre>
	 */
	public IDataSet fInqSellGrade(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        
        try {
            // 1. DU lookup
            DEPSellGradeMgmt dEPSellGradeMgmt = (DEPSellGradeMgmt)lookupDataUnit(DEPSellGradeMgmt.class);        
            // 2. LISTDM호출
            responseData = dEPSellGradeMgmt.dSSellGrade(requestData, onlineCtx);    // 판매등급현황목록 조회                                
        } catch ( BizRuntimeException e ) {
            throw e; //시스템 오류가 발생하였습니다.
        }
    
        return responseData;
    }

    /**
	 *
	 *
	 * @author 정동현 (jjddhh123)
	 * @since 2015-09-03 15:49:58
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNSL_MGMT_NO [상담관리번호]
	 *	- field : SEQ [순번]
	 *	- field : PWR_ST [전원불량]
	 *	- field : USIM_ST [USIM불량]
	 *	- field : RECG_ST [충전불량]
	 *	- field : SBELL_ST [벨소리불량]
	 *	- field : VIBR_ST [진동불량]
	 *	- field : SPK_ST [스피커불량]
	 *	- field : ILRM_SENSOR_ST [조도센서불량]
	 *	- field : GRCP_ST [자이로센서불량]
	 *	- field : APRC_SENSOR_ST [근접센서불량]
	 *	- field : CAMR_ST [카메라불량]
	 *	- field : BATER_ST [B/T불량]
	 *	- field : AFIMG_ST [잔상불량]
	 *	- field : TOUCH_ST [터치불량]
	 *	- field : WIFI_ST [WIFI불량]
	 *	- field : STAIN_ST [얼룩]
	 *	- field : BRUI_ST [멍]
	 *	- field : SQUS_ST [눌림]
	 *	- field : LQFD_INFLO_ST [액상유입]
	 *	- field : LED_ST [LED파손]
	 *	- field : BUTN_ST [버튼불량]
	 *	- field : BOTM_TOUCH_ST [하단터치메뉴]
	 *	- field : BODY_ST [몸체미세휨]
	 *	- field : DMB_ST [DMB안테나손상]
	 *	- field : LCGLS_ST [액정유리]
	 *	- field : CHIP_ST [이나감]
	 *	- field : FGSC_ST [지문인식]
	 *	- field : CAMR_WINDO_ST [카메라윈도우손상]
	 *	- field : EDGE_ST [테두리및몸체]
	 *	- field : BACK_CL [매입불가여부]
	 *	- field : BACK_ST [매입불가]
	 *	- field : SELL_GRADE [판매등급]
	 *	- field : FS_REG_USER_ID [최초 등록 사용자 ID]
	 *	- field : LS_CHG_USER_ID [최종 변경 사용자 ID]
	 *	- field : PRCH_GRADE [매입등급]
	 *	- field : EQP_ST_RMK [단말기상태비고]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fRegSellGrade(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        
        try {
            // 1. DU lookup
            DEPSellGradeMgmt dEPSellGradeMgmt = (DEPSellGradeMgmt)lookupDataUnit(DEPSellGradeMgmt.class);        
            // 2. LISTDM호출
            responseData = dEPSellGradeMgmt.dISellGrade(requestData, onlineCtx);        // 판매등급등록 
            responseData = dEPSellGradeMgmt.dUCnslSellGrade(requestData, onlineCtx);    // 상담관리판매등급등록 
            responseData = dEPSellGradeMgmt.dUPrchSellGrade(requestData, onlineCtx);    // 매입관리판매등급등록 
            responseData = dEPSellGradeMgmt.dUSellGradeDelYn(requestData, onlineCtx);   // 이전 판매등급 삭제여부등록
        } catch ( BizRuntimeException e ) {
            throw e; //시스템 오류가 발생하였습니다.
        }
    
        return responseData;
    }
  
}
