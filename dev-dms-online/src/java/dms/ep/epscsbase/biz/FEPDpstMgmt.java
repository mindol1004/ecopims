package dms.ep.epscsbase.biz;

import java.math.BigDecimal;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.RecordSet;
import nexcore.framework.core.exception.BizRuntimeException;
import nexcore.framework.core.util.DateUtils;
import fwk.common.CommonArea;
import fwk.common.CommonUtils;
import fwk.utils.HpcUtils;
import fwk.utils.PagenateUtils;


/**
 * <ul>
 * <li>업무 그룹명 : dms/에코폰</li>
 * <li>단위업무명: [FU]입금관리</li>
 * <li>설  명 : <pre>입금관리</pre></li>
 * <li>작성일 : 2015-09-15 18:48:39</li>
 * <li>작성자 : 양재석 (jsyang)</li>
 * </ul>
 *
 * @author 양재석 (jsyang)
 */
public class FEPDpstMgmt extends fwk.base.FunctionUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public FEPDpstMgmt(){
		super();
	}

    /**
	 * <pre>송금대상조회</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-15 18:48:39
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : IN_FR_DT [조회시작일자]
	 *	- field : IN_TO_DT [조회종료일자]
	 *	- field : AFFIL_AGN [대리점거래처코드]
	 *	- field : CNSL_DEALCO_CD [상담처코드]
	 *	- field : EQP_SER_NO [단말기일련번호]
	 *	- field : EQP_MDL_CD [모델코드]
	 *	- field : DPSTR_ENPT [예금주_암호화]
	 *	- field : CNSL_MGMT_NO [상담관리번호]
	 *	- field : BOX_NO [박스번호]
	 *	- field : DT_GB_CD [일자구분]
	 *	- field : ECO_FU_YN [에코4U여부]
	 *	- field : PRE_DC_CL [선할인여부]
	 *	- field : IN_AMT_YN [입금여부]
	 *	- field : DIR_PRCH_YN [직매입여부]
	 *	- field : EXCEL_ALL_YN [엑셀전체여부]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : SEND_AMT_OBJ_LIST
	 *		- field : PRCH_MGMT_NO [매입 관리 번호]
	 *		- field : CNSL_MGMT_NO [상담 관리 번호]
	 *		- field : EQP_MDL_CD [모델코드]
	 *		- field : EQP_SER_NO [단말기일련번호]
	 *		- field : PRCH_DT [매입 일자]
	 *		- field : CNSL_DT [상담 일자]
	 *		- field : EQP_COLOR_CD [단말기색상코드]
	 *		- field : HEADQ_NM [본부 명]
	 *		- field : MKT_TEAM_CD [마케팅 팀 코드]
	 *		- field : MKT_TEAM_NM [마케팅 팀 명]
	 *		- field : AGN_ORG_CD [대리점 조직 코드]
	 *		- field : UKEY_SUB_CD [UKEY 서브 코드]
	 *		- field : CNSL_DEALCO_NM [상담처명]
	 *		- field : AFFIL_AGN_NM [대리점명]
	 *		- field : CNSL_DEALCO_CD [상담처코드]
	 *		- field : EQP_MDL_NM [모델명]
	 *		- field : PRCH_AMT [매입금액]
	 *		- field : BANK_CD [은행코드]
	 *		- field : ACCO_NO [계좌번호]
	 *		- field : DPSTR [예금주]
	 *		- field : AMT_RMT_YN [금액 송금 여부]
	 *		- field : RMT_DT [송금 일자]
	 *		- field : EQP_ST [단말기 상태]
	 *		- field : EQP_ST_DTAIL [단말기 상태 세부]
	 *		- field : AFFIL_AGN [소속 대리점]
	 *		- field : PRE_DC_CL [선 할인 구분]
	 *		- field : UBO_AMT [유보금액]
	 *		- field : TOT_AMT [총금액]
	 *		- field : CUST_GRP [고객 그룹]
	 *		- field : CHECKED [CHECKED]
	 *		- field : ACCO_NO_ENPT [계좌번호_암호화]
	 *		- field : DPSTR_ENPT [예금주_암호화]
	 * </pre>
	 */
	public IDataSet fInqSendAmtObjList(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet responseRegData = new DataSet();
        IDataSet responseDtlData = new DataSet();
        IDataSet dsCnt = new DataSet();
        IDataSet reqDs = (IDataSet) requestData.clone();//원거래의 DataSet의 훼손을 막기 위한 Clone
        IRecordSet rsPagingList = null;
        IRecordSet rsList = null;
        int intTotalCnt = 0;

        try {
            // 1. DU lookup
            DEPDpstMgmt dEPDpstMgmt = (DEPDpstMgmt) lookupDataUnit(DEPDpstMgmt.class);
            // 2. TOTAL COUNT DM호출
            dsCnt = dEPDpstMgmt.dSInqSendAmtObjPrchListTotCnt(reqDs, onlineCtx);
            intTotalCnt = Integer.parseInt(dsCnt.getField("TOTAL_CNT"));
            PagenateUtils.setPagenatedParamsToDataSet(reqDs);
            if(requestData.getField("EXCEL_ALL_YN") != null && "Y".equals(requestData.getField("EXCEL_ALL_YN").toString()) && intTotalCnt > 2000){
                throw new BizRuntimeException("DMS00169"); //엑셀 전체 다운로드 최대조회건수를 초과하였습니다.
            }
            // 개인정보조회이력 파라미터 셋팅
            CommonArea ca = CommonUtils.getCommonArea(onlineCtx);

            reqDs.putField("USER_NO", ca.getUserNo()); 
            reqDs.putField("IP_ADDR", ca.getIpad());
            reqDs.putField("MAC_ADDR", ca.getPrcmMac());
            reqDs.putField("GLB_ID", ca.getGlobId());
            reqDs.putField("INQ_REQ_DTM", ca.getSvcStrnDttm());
                        
            // 3. LISTDM호출
            responseRegData = dEPDpstMgmt.dSInqSendAmtObjPrchListPaging(reqDs, onlineCtx);

            rsList = responseRegData.getRecordSet("SEND_AMT_OBJ_LIST");
            if(intTotalCnt > 0){
                if(requestData.getField("EXCEL_ALL_YN") == null || !"Y".equals(requestData.getField("EXCEL_ALL_YN").toString())){
                    // 4. 상세LISTDM호출
                    IRecordSet rsDtl = null;
                    int rsDtlIdx = 0;
                    for(int j = 0; j < rsList.getRecordCount(); j++){ 
                        //매입상세조회
                        //일괄로 조회시 대량건인경우 시스템에러 발생으로 각건별로 조회해서 에러 방지
                        if(rsList.get(j, "PRCH_MGMT_NO") != null && !"".equals(rsList.get(j, "PRCH_MGMT_NO").toString())){
                            reqDs.putField("PRCH_MGMT_NO", rsList.get(j, "PRCH_MGMT_NO"));
                            responseDtlData = dEPDpstMgmt.dSInqDpstMgmtPrchDtlList(reqDs, onlineCtx);
                            if(responseDtlData != null && 
                            responseDtlData.getRecordSetCount() > 0 && responseDtlData.getRecordSet("DPST_MGMT_PRCH_DTL_LIST") != null){
                                IRecordSet rsTemp = responseDtlData.getRecordSet("DPST_MGMT_PRCH_DTL_LIST");
                                if(rsDtl == null && rsTemp != null && rsTemp.getRecordCount() > 0){
                                    rsDtl = rsTemp;
                                    rsDtlIdx = rsDtl.getRecordCount();
                                }else if(rsTemp != null && rsTemp.getRecordCount() > 0){
                                    for(int i = 0; i < rsTemp.getRecordCount(); i++){
                                        rsDtlIdx = rsDtl.getRecordCount();
                                        rsDtl.addRecord(rsDtlIdx, rsTemp.getRecord(i));
                                    }
                                }
                            }
                        }
                    }
                    if(rsDtlIdx > 0){
                        responseData.putRecordSet("SEND_AMT_OBJ_DTL_LIST", rsDtl);
                    }
                }
                responseData.putRecordSet("SEND_AMT_OBJ_LIST", rsList);
            }else{
                responseData.putRecordSet("SEND_AMT_OBJ_LIST", responseRegData.getRecordSet("SEND_AMT_OBJ_LIST"));
            }
            rsPagingList = responseData.getRecordSet("SEND_AMT_OBJ_LIST");
            PagenateUtils.setPagenatedParamToRecordSet(rsPagingList, reqDs, intTotalCnt);
        } catch ( BizRuntimeException e ) {
            throw e;
        }
    
        return responseData;
    }

    /**
	 * <pre>송금대상목록에러체크조회</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-15 18:48:39
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RQ_SEND_AMT_OBJ_EQP_LIST
	 *		- field : EQP_MDL_CD [모델코드]
	 *		- field : PROD_NM [모델명]
	 *		- field : EQP_SER_NO [일련번호]
	 *		- field : DPSTR [고객명]
	 *		- field : CNSL_DT [상담일자]
	 *		- field : CNSL_DEALCO_CD [상담처코드]
	 *		- field : CON_PLC_NM [상담처명]
	 *		- field : ONING_DT [개통일자]
	 *		- field : EQP_ST [단말기등급]
	 *		- field : EQP_COLOR_CD [색상]
	 *		- field : LOSS_EQP_YN [분실단말기여부]
	 *		- field : RWD_RTN_OBJ_YN [보상반납대상여부]
	 *		- field : RENTP_YN [임대폰여부]
	 *		- field : USIM_EQPCHG_YN [USIM기변여부]
	 *		- field : OTHRCO_FORGN_EQP_YN [타사/해외단말기여부]
	 *		- field : SVC_NO [서비스번호]
	 *		- field : SVC_MGMT_NO [서비스관리번호]
	 *		- field : IMEI [IMEI]
	 *		- field : FEE_DEDC_YN [요금공제여부]
	 *		- field : SKN_JDG_AMT [매입금액]
	 *		- field : PRCH_CL [검수구분]
	 *		- field : TLY_DT [검수일자]
	 *		- field : CUST_IDEA [고객의사]
	 *		- field : CUST_CHK_DT [고객확인일자]
	 *		- field : ACC_NM [예금주]
	 *		- field : BANK_CD [은행코드]
	 *		- field : BANK_NM [은행명]
	 *		- field : ACCO_NO [계좌번호]
	 *		- field : TEL_NO [전화번호]
	 *		- field : ZIPCD [우편번호]
	 *		- field : ADDR [주소]
	 *		- field : ETC_ADDR [기타주소]
	 *		- field : SUGG [의견]
	 *		- field : T_MGMT_NO [T_관리번호]
	 *		- field : BATTERY [배터리]
	 *		- field : CHARGER [충전기]
	 *		- field : ZENDER [젠더]
	 *		- field : EARPHONES [이어폰]
	 *		- field : CASE [충전 Case]
	 *		- field : USB [USB CABLE]
	 *		- field : MEMORY [메모리카드]
	 *		- field : DTL_1 [구성품_1]
	 *		- field : DTL_2 [구성품_2]
	 *		- field : DTL_3 [구성품_3]
	 *		- field : OTHER [기타구성품]
	 *		- field : SELL_GRADE [판매등급]
	 *		- field : INVE_SLIP_NO [전표번호]
	 *		- field : INVE_SLIP_DT [전기일]
	 *		- field : ERR_DESC [ERROR]
	 *		- field : SKN_CL [SKN구분]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_SEND_AMT_OBJ_EQP_LIST
	 *		- field : EQP_MDL_CD [모델코드]
	 *		- field : PROD_NM [모델명]
	 *		- field : EQP_SER_NO [일련번호]
	 *		- field : DPSTR [고객명]
	 *		- field : CNSL_DT [상담일자]
	 *		- field : CNSL_DEALCO_CD [상담처코드]
	 *		- field : CON_PLC_NM [상담처명]
	 *		- field : ONING_DT [개통일자]
	 *		- field : EQP_ST [단말기등급]
	 *		- field : EQP_COLOR_CD [색상]
	 *		- field : LOSS_EQP_YN [분실단말기여부]
	 *		- field : RWD_RTN_OBJ_YN [보상반납대상여부]
	 *		- field : RENTP_YN [임대폰여부]
	 *		- field : USIM_EQPCHG_YN [USIM기변여부]
	 *		- field : OTHRCO_FORGN_EQP_YN [타사/해외단말기여부]
	 *		- field : SVC_NO [서비스번호]
	 *		- field : SVC_MGMT_NO [서비스관리번호]
	 *		- field : IMEI [IMEI]
	 *		- field : FEE_DEDC_YN [요금공제여부]
	 *		- field : SKN_JDG_AMT [매입금액]
	 *		- field : PRCH_CL [검수구분]
	 *		- field : TLY_DT [검수일자]
	 *		- field : CUST_IDEA [고객의사]
	 *		- field : CUST_CHK_DT [고객확인일자]
	 *		- field : ACC_NM [예금주]
	 *		- field : BANK_CD [은행코드]
	 *		- field : BANK_NM [은행명]
	 *		- field : ACCO_NO [계좌번호]
	 *		- field : TEL_NO [전화번호]
	 *		- field : ZIPCD [우편번호]
	 *		- field : ADDR [주소]
	 *		- field : ETC_ADDR [기타주소]
	 *		- field : SUGG [의견]
	 *		- field : T_MGMT_NO [T_관리번호]
	 *		- field : BATTERY [배터리]
	 *		- field : CHARGER [충전기]
	 *		- field : ZENDER [젠더]
	 *		- field : EARPHONES [이어폰]
	 *		- field : CASE [충전 Case]
	 *		- field : USB [USB CABLE]
	 *		- field : MEMORY [메모리카드]
	 *		- field : DTL_1 [구성품_1]
	 *		- field : DTL_2 [구성품_2]
	 *		- field : DTL_3 [구성품_3]
	 *		- field : OTHER [기타구성품]
	 *		- field : SELL_GRADE [판매등급]
	 *		- field : INVE_SLIP_NO [전표번호]
	 *		- field : INVE_SLIP_DT [전기일]
	 *		- field : ERR_DESC [ERROR]
	 *		- field : SKN_CL [SKN구분]
	 * </pre>
	 */
	public IDataSet fInqSendAmtObjErrChkList(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet responseTemp = new DataSet();
        IDataSet reqData = new DataSet();
        reqData.putFieldMap(requestData.getFieldMap());
        IRecordSet rs = requestData.getRecordSet("RQ_SEND_AMT_OBJ_EQP_LIST");
        CommonArea ca = getCommonArea(onlineCtx);

        try {
            // 1. DU lookup
            DEPDpstMgmt dEPDpstMgmt = (DEPDpstMgmt) lookupDataUnit(DEPDpstMgmt.class);
            // 2.
            String strMsg = "";
            for(int i = 0; i < rs.getRecordCount(); i++){
                reqData.putFieldMap(rs.getRecordMap(i));
                reqData.putField("EQP_MDL_NM", reqData.getField("PROD_NM"));
                reqData.putField("CON_PLC_NM", reqData.getField("CON_PLC_NM"));

                responseTemp = dEPDpstMgmt.dSInqSendAmtObjErrChkList(reqData, onlineCtx);
                
                strMsg = "";
                if(responseTemp == null || responseTemp.getRecordSetCount() <= 0 
                    || responseTemp.getRecordSet("EQP_ERR_CHK_LIST") == null || responseTemp.getRecordSet("EQP_ERR_CHK_LIST").getRecordCount() <= 0){
                    strMsg = "미존재단말기,미존재상담처";
                }else {
                    IRecordSet rsTemp = responseTemp.getRecordSet("EQP_ERR_CHK_LIST");
                    //모델체크
                    if(rsTemp.getRecord(0).get("EQP_MDL_NM") == null ||
                        "".equals(rsTemp.getRecord(0).get("EQP_MDL_NM").toString())){
                        strMsg = "미존재단말기";
                    }/*
                    else if(!rsTemp.getRecord(0).get("EQP_MDL_NM").toString().equals(reqData.getField("EQP_MDL_NM"))){
                        //strMsg = "단말기명비동일";
                        //rs.getRecord(i).set("EQP_MDL_NM", rsTemp.getRecord(0).get("EQP_MDL_NM").toString());
                    }*/
                    //상담처체크
                    if(rsTemp.getRecord(0).get("DEALCO_NM") == null ||
                        "".equals(rsTemp.getRecord(0).get("DEALCO_NM").toString())){
                        if(strMsg != null && !"".equals(strMsg)){
                            strMsg += ",";
                        }
                        strMsg = strMsg + "미존재상담처";
                    }else if(!rsTemp.getRecord(0).get("DEALCO_NM").toString().equals(reqData.getField("CON_PLC_NM"))){
                        //상담처명 재설정
                        rs.getRecord(i).put("CON_PLC_NM", rsTemp.getRecord(0).get("DEALCO_NM").toString());
                    }
                    //동일단말기여부
                    if(Integer.parseInt(rsTemp.getRecord(0).getBigDecimal("REG_CNT").toString()) > 0){
                        if(strMsg != null && !"".equals(strMsg)){
                            strMsg += ",";
                        }
                        strMsg = strMsg + "기등록단말기";
                    }
                    if(strMsg != null && !"".equals(strMsg)){
                        if(reqData.getField("ERR_DESC") != null && !"".equals(reqData.getField("ERR_DESC"))){
                            strMsg = "," + strMsg;
                        }
                        rs.getRecord(i).put("ERR_DESC", strMsg);
                    }
                    else{
                        rs.getRecord(i).put("ERR_DESC", "");
                    }
                }
            }
            responseData.putRecordSet("RS_SEND_AMT_OBJ_EQP_LIST", rs);
        } catch ( BizRuntimeException e ) {
            throw e;
        }
    
        return responseData;
    }

    /**
	 * <pre>[FM]입금관리송금단말기등록</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-15 18:48:39
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RQ_SEND_AMT_OBJ_EQP_LIST
	 *		- field : EQP_MDL_CD [모델코드]
	 *		- field : PROD_NM [모델명]
	 *		- field : EQP_SER_NO [일련번호]
	 *		- field : DPSTR [고객명]
	 *		- field : CNSL_DT [상담일자]
	 *		- field : CNSL_DEALCO_CD [상담처코드]
	 *		- field : CON_PLC_NM [상담처명]
	 *		- field : ONING_DT [개통일자]
	 *		- field : EQP_ST [단말기등급]
	 *		- field : EQP_COLOR_CD [색상]
	 *		- field : LOSS_EQP_YN [분실단말기여부]
	 *		- field : RWD_RTN_OBJ_YN [보상반납대상여부]
	 *		- field : RENTP_YN [임대폰여부]
	 *		- field : USIM_EQPCHG_YN [USIM기변여부]
	 *		- field : OTHRCO_FORGN_EQP_YN [타사/해외단말기여부]
	 *		- field : SVC_NO [서비스번호]
	 *		- field : SVC_MGMT_NO [서비스관리번호]
	 *		- field : IMEI [IMEI]
	 *		- field : FEE_DEDC_YN [요금공제여부]
	 *		- field : SKN_JDG_AMT [매입금액]
	 *		- field : PRCH_CL [검수구분]
	 *		- field : TLY_DT [검수일자]
	 *		- field : CUST_IDEA [고객의사]
	 *		- field : CUST_CHK_DT [고객확인일자]
	 *		- field : ACC_NM [예금주]
	 *		- field : BANK_CD [은행코드]
	 *		- field : BANK_NM [은행명]
	 *		- field : ACCO_NO [계좌번호]
	 *		- field : TEL_NO [전화번호]
	 *		- field : ZIPCD [우편번호]
	 *		- field : ADDR [주소]
	 *		- field : ETC_ADDR [기타주소]
	 *		- field : SUGG [의견]
	 *		- field : T_MGMT_NO [T_관리번호]
	 *		- field : BATTERY [배터리]
	 *		- field : CHARGER [충전기]
	 *		- field : ZENDER [젠더]
	 *		- field : EARPHONES [이어폰]
	 *		- field : CASE [충전 Case]
	 *		- field : USB [USB CABLE]
	 *		- field : MEMORY [메모리카드]
	 *		- field : DTL_1 [구성품_1]
	 *		- field : DTL_2 [구성품_2]
	 *		- field : DTL_3 [구성품_3]
	 *		- field : OTHER [기타구성품]
	 *		- field : SELL_GRADE [판매등급]
	 *		- field : INVE_SLIP_NO [전표번호]
	 *		- field : INVE_SLIP_DT [전기일]
	 *		- field : ERR_DESC [ERROR]
	 *		- field : SKN_CL [SKN구분]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fRegDpstMgmtSendAmtEqp(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet reqData = new DataSet();
        IDataSet reqDataTemp = new DataSet();
        IDataSet resData = new DataSet();
        reqData.putFieldMap(requestData.getFieldMap());
        IRecordSet reqSet = requestData.getRecordSet("RQ_SEND_AMT_OBJ_EQP_LIST");

        try {
            // 1. DU lookup
            DEPDpstMgmt dEPDpstMgmt = (DEPDpstMgmt) lookupDataUnit(DEPDpstMgmt.class);
            DEPTlyReg dEPTlyReg = (DEPTlyReg) lookupDataUnit(DEPTlyReg.class);
            CommonArea ca = getCommonArea(onlineCtx);
            String sCurrDt = DateUtils.getCurrentDate(); // 현재일자
            // 1-1. 재고정산일련번 채번
            resData = callSharedBizComponentByDirect("ep.EPSSXBase", "dSInqEpEqpInveXclSeq", reqData, onlineCtx);
            String strInveXclNo = resData.getField("INVE_XCL_NO").toString();
            String strInveSlipNo = "";
            String strInveSlipDt = "";
            // 2. 내역등록
            for(int i = 0; i < reqSet.getRecordCount(); i++){
                reqData.putFieldMap(reqSet.getRecordMap(i));
                //3. 상담관리번호채번
                resData = dEPDpstMgmt.dSInqCnslMgmtNo(reqData, onlineCtx);
                reqData.putField("CNSL_MGMT_NO", resData.getField("CNSL_MGMT_NO"));
                //4. 매입관리번호채번
                resData = dEPTlyReg.dSPrchMgmtNoSeq(reqData, onlineCtx);
                reqData.putField("PRCH_MGMT_NO", resData.getField("PRCH_MGMT_NO"));
                //5. 상품상세조회
                resData = dEPDpstMgmt.dSInqDspMgmtProdDtlInfoList(reqData, onlineCtx);
                IRecordSet reqSetDtl = resData.getRecordSet("PROD_DTL_INFO_LIST");
                for(int j = 0; j < reqSetDtl.getRecordCount(); j++){
                    IDataSet reqDataDtl = new DataSet();
                    reqDataDtl.putFieldMap(reqSetDtl.getRecordMap(j));
                    //6. 최초등록사용자ID,최종변경사용자ID
                    reqDataDtl.putField("FS_REG_USER_ID", ca.getUserNo());
                    reqDataDtl.putField("LS_CHG_USER_ID", ca.getUserNo());
                    reqDataDtl.putField("CNSL_MGMT_NO", reqData.getField("CNSL_MGMT_NO"));
                    reqDataDtl.putField("PRCH_MGMT_NO", reqData.getField("PRCH_MGMT_NO"));
                    reqDataDtl.putField("DTL_CL", reqDataDtl.getField("OUTS_MCN_CL"));
                    reqDataDtl.putField("CNSL_SER_NO", reqDataDtl.getField("SEQ"));
                    reqDataDtl.putField("PRCH_SER_NO", reqDataDtl.getField("SEQ"));
                    reqDataDtl.putField("CMPT_PROD_CD", reqDataDtl.getField("BATR_CD"));
                    reqDataDtl.putField("CL_CD", "1");
                    reqDataDtl.putField("PRCH_UPRC", reqDataDtl.getField("UPRC"));
                    reqDataDtl.putField("PRCH_QTY", reqDataDtl.getField("QTY"));
                    if(reqDataDtl.getField("OUTS_MCN_CL") != null){
                        if("1".equals(reqDataDtl.getField("OUTS_MCN_CL").toString()) && reqData.getField("BATTERY") != null 
                            && Integer.parseInt(reqData.getField("BATTERY").toString()) > 0){
                            reqDataDtl.putField("HLD_QTY", 1);
                        }else if("2".equals(reqDataDtl.getField("OUTS_MCN_CL").toString()) && reqData.getField("BATTERY") != null 
                            && Integer.parseInt(reqData.getField("BATTERY").toString()) > 1){
                            reqDataDtl.putField("HLD_QTY", Integer.parseInt(reqData.getField("BATTERY").toString()) - 1);
                        }else if("4".equals(reqDataDtl.getField("OUTS_MCN_CL").toString()) && reqData.getField("CHARGER") != null 
                            && reqData.getField("CHARGER").toString().endsWith("Y")){
                            reqDataDtl.putField("HLD_QTY", 1);
                        }else if("5".equals(reqDataDtl.getField("OUTS_MCN_CL").toString()) && reqData.getField("ZENDER") != null 
                            && reqData.getField("ZENDER").toString().endsWith("Y")){
                            reqDataDtl.putField("HLD_QTY", 1);
                        }else if("6".equals(reqDataDtl.getField("OUTS_MCN_CL").toString()) && reqData.getField("EARPHONES") != null 
                            && reqData.getField("EARPHONES").toString().endsWith("Y")){
                            reqDataDtl.putField("HLD_QTY", 1);
                        }else if("7".equals(reqDataDtl.getField("OUTS_MCN_CL").toString()) && reqData.getField("CASE") != null 
                            && reqData.getField("CASE").toString().endsWith("Y")){
                            reqDataDtl.putField("HLD_QTY", 1);
                        }else if("8".equals(reqDataDtl.getField("OUTS_MCN_CL").toString()) && reqData.getField("USB") != null 
                            && reqData.getField("USB").toString().endsWith("Y")){
                            reqDataDtl.putField("HLD_QTY", 1);
                        }else if("9".equals(reqDataDtl.getField("OUTS_MCN_CL").toString()) && reqData.getField("MEMORY") != null 
                            && reqData.getField("MEMORY").toString().endsWith("Y")){
                            reqDataDtl.putField("HLD_QTY", 1);
                        }
                    }
                    //7. 상담상세등록
                    dEPDpstMgmt.dIDpstMgmtConsultDtlRgst(reqDataDtl, onlineCtx);
                    //8. 매입상세등록
                    dEPDpstMgmt.dIDpstMgmtPrchsDtlRgst(reqDataDtl, onlineCtx);
                }
                //9. 최초등록사용자ID,최종변경사용자ID
                reqData.putField("FS_REG_USER_ID", ca.getUserNo());
                reqData.putField("LS_CHG_USER_ID", ca.getUserNo());
                
                if(reqData.getField("CNSL_DT") != null && !"".equals(reqData.getField("CNSL_DT").toString())){
                    reqData.putField("CNSL_DT", reqData.getField("CNSL_DT").toString().replace("-", ""));
                }
                if(reqData.getField("ONING_DT") != null && !"".equals(reqData.getField("ONING_DT").toString())){
                    reqData.putField("ONING_DT", reqData.getField("ONING_DT").toString().replace("-", ""));
                }
                if(reqData.getField("TLY_DT") != null && !"".equals(reqData.getField("TLY_DT").toString())){
                    reqData.putField("TLY_DT", reqData.getField("TLY_DT").toString().replace("-", ""));
                }
                if(reqData.getField("ACCO_NO") != null && !"".equals(reqData.getField("ACCO_NO").toString())){
                    reqData.putField("ACCO_NO_ENPT", HpcUtils.encodeByAES(reqData.getField("ACCO_NO")));
                    reqData.putField("ACCO_NO", HpcUtils.maskingAccountNo(reqData.getField("ACCO_NO")));
                }
                if(reqData.getField("TEL_NO") != null && !"".equals(reqData.getField("TEL_NO").toString())){
                    reqData.putField("TEL_NO_ENPT", HpcUtils.encodeByAES(reqData.getField("TEL_NO")));
                    reqData.putField("TEL_NO", HpcUtils.maskingTelNo(reqData.getField("TEL_NO")));
                }
                if(reqData.getField("ETC_ADDR") != null && !"".equals(reqData.getField("ETC_ADDR").toString())){
                    reqData.putField("ETC_ADDR_ENPT", HpcUtils.encodeByAES(reqData.getField("ETC_ADDR")));
                    reqData.putField("ETC_ADDR", HpcUtils.maskingAddrDtl(reqData.getField("ETC_ADDR")));
                }
                if(reqData.getField("SVC_NO") != null && !"".equals(reqData.getField("SVC_NO").toString())){
                    reqData.putField("SVC_NO_ENPT", HpcUtils.encodeByAES(reqData.getField("SVC_NO")));
                    reqData.putField("SVC_NO", HpcUtils.maskingTelNo(reqData.getField("SVC_NO")));
                }
                //10. 상담관리등록
                dEPDpstMgmt.dIDpstMgmtConsultRgst(reqData, onlineCtx);
                if(reqData.getField("SKN_JDG_AMT") != null && !"".equals(reqData.getField("SKN_JDG_AMT").toString())){
                    reqData.putField("PRCH_AMT", reqData.getField("SKN_JDG_AMT").toString());
                }
                //11. 매입관리등록
                dEPDpstMgmt.dIDpstMgmtPrchRgst(reqData, onlineCtx);
                //12. 재고정산상세등록
                reqData.putField("INVE_XCL_NO",strInveXclNo);
                reqData.putField("INVE_XCL_DTL_SEQ",i+1);
                if(reqData.getField("TLY_DT") != null && !"".equals(reqData.getField("TLY_DT").toString())){
                    reqData.putField("PRCH_DT", reqData.getField("TLY_DT").toString());
                }else{
                    reqData.putField("PRCH_DT", sCurrDt);
                }
                if(reqData.getField("PRCH_AMT") != null && !"".equals(reqData.getField("PRCH_AMT").toString())){
                    reqData.putField("XCL_AMT", reqData.getField("PRCH_AMT").toString());
                }
                reqData.putField("ASMT_DMG_AMT", new BigDecimal(0));
                if(i == 0){
                    strInveSlipNo = reqData.getField("INVE_SLIP_NO").toString();
                    strInveSlipDt = reqData.getField("INVE_SLIP_DT").toString();
                }
                reqData.putField("INVE_SLIP_NO",strInveSlipNo);
                reqData.putField("INVE_SLIP_DT",strInveSlipDt);
                resData = callSharedBizComponentByDirect("ep.EPSSXBase", "dIEpEqpInveXclDtlRgst", reqData, onlineCtx);
            }
            //20. 재고정산메인등록
            reqData.putField("INVE_XCL_NO",strInveXclNo);
            reqData.putField("XCL_STRD_DT",sCurrDt);
            reqData.putField("XCL_STRD_STA_DT",sCurrDt);
            reqData.putField("XCL_STRD_END_DT",sCurrDt);
            reqData.putField("SKN_CL","N"); //현장여브
            reqData.putField("INVE_CL_CD","20");  //자수폰
            reqData.putField("DEDC_TYP_CD","01");  //계좌송금
            reqData.putField("INVE_TYP_CD","10");  //확정재고
            reqData.putField("INVE_SLIP_NO",strInveSlipNo);
            reqData.putField("INVE_SLIP_DT",strInveSlipDt);
            reqData.putField("INVE_SLIP_ST","20"); //전표상태 : 회계팀승인
            reqData.putField("CNCL_SLIP_YN","N"); //취소여부
            reqData.putField("FS_REG_USER_ID", ca.getUserNo());
            reqData.putField("LS_CHG_USER_ID", ca.getUserNo());
            resData = callSharedBizComponentByDirect("ep.EPSSXBase", "dIEpEqpDpstMgmtInveXclRgst", reqData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        }
    
        return responseData;
    }

    /**
	 * <pre>[FM]입금관리등록재고처리</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-15 18:48:39
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fIDpstMgmtRegDisInProc(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet resData = new DataSet();
        IDataSet reqData = new DataSet();
        IDataSet dsCnt = new DataSet();

        reqData.putFieldMap(requestData.getFieldMap());
        CommonArea ca = getCommonArea(onlineCtx);
    
        String sInMgmtNo = ""; //입고관리번호
        String sPrchsMgmtNo = "";
        String sInSeq = ""; // 입고순번
        
        int iCount = 0;

        //RQ_SEND_AMT_OBJ_EQP_LIST
        //RQ_SEND_AMT_OBJ_DTL_LIST
        //SEND_AMT_MASTER
        //SEND_AMT_EQP
        //SEND_AMT_CONSULT
        
        // 발주,매입에 대한 입고 처리
        // 1. 입고관리번호 생성
        // 2. 입고  처리 (입고 상태를 입고확정으로 처리함)
        // 2-1. 입고 tabel insert
        // 2-2. 입고상세 tabel insert
        // 2-3. 입고구성품 tabel insert
        // 3. 재고  처리
        // 3-1. 재고 tabel insert
        // 3-2. 재고금액 tabel insert
        // 3-3. 재고입출고이력 tabel insert
        // 3-4. 재고이력 tabel insert
        // 3-5. 기타재고 tabel insert
        // 3-6. 기타재고입출고이력 tabel insert
        // 3-7. 기타재고이력 tabel insert

        try {
            // 1. DU lookup
            DEPRJdgQltyJdgMgmt dEPRJdgQltyJdgMgmt = (DEPRJdgQltyJdgMgmt) lookupDataUnit(DEPRJdgQltyJdgMgmt.class);
            // 입고 MASTER. 
            IRecordSet rsInMst = requestData.getRecordSet("SEND_AMT_MASTER");
    
            // 입고  상품 리스트. 
            IRecordSet rsProdList = requestData.getRecordSet("SEND_AMT_EQP");
            reqData.putFieldMap(rsProdList.getRecordMap(0));
            
            // 1. 입고관리번호 생성
            // 신규인 경우에 입고관리번호를 생성한다.
            // 매입재고확인
            dsCnt = dEPRJdgQltyJdgMgmt.dSRJdgQJdgInqEqpDisList(reqData, onlineCtx);
            int intDisCnt = Integer.parseInt(dsCnt.getField("DIS_CNT"));
            if(intDisCnt > 0){
                throw new BizRuntimeException("DMS00110");  //구성품이 매입 되어있는 상품입니다. 확인 하시기 바랍니다.
            }
            // 상품재고확인
            dsCnt = dEPRJdgQltyJdgMgmt.dSRJdgQJdgInqEqpProdDisList(reqData, onlineCtx);
            intDisCnt = Integer.parseInt(dsCnt.getField("PROD_DIS_CNT"));
            if(intDisCnt > 0){
                throw new BizRuntimeException("DMS00071");  //이미 처리된 건이 있습니다.
            }

            // 입고예정등록 확인
            reqData.putFieldMap(rsInMst.getRecordMap(0));
            dsCnt = dEPRJdgQltyJdgMgmt.dSRJdgQJdgInqEqpInOrdListCnt(reqData, onlineCtx);
            intDisCnt = Integer.parseInt(dsCnt.getField("ORD_CNT"));
            if(intDisCnt > 0){
                throw new BizRuntimeException("DMS00003");  //중복 된 데이터가 존재합니다.
            }
            
            // 입고관리번호 얻기.
            resData = dEPRJdgQltyJdgMgmt.dSInqDisInMgmtNo(reqData, onlineCtx);
            sInMgmtNo = resData.getField("IN_MGMT_NO").toString();
            
            if(rsProdList != null){
                String sProcFlag = null;
                String sInCd = "100"; // 입고구분
                String sInDtlCd = ""; // 입고상세구분
                String sDisSt = "01"; // 재고상태 : 01 가용, 02 비가용
                String sInSt = "03"; // 입고상태 : 01 미입고, 02 부분입고, 03 입고완료
                String sCurrDt = DateUtils.getCurrentDate(); // 현재일자
                String sInPlcId = ""; // 입고처

                sInCd = rsInMst.getRecordMap(0).get("IN_CL").toString();
                if("114".equals(sInCd)){
                    //sInCd = "100";
                    sInDtlCd = "114"; //매입입고

                    sPrchsMgmtNo = requestData.getField("PRCH_MGMT_NO") == null ? rsInMst.getRecordMap(0).get("PRCH_MGMT_NO").toString() : requestData.getField("PRCH_MGMT_NO");
                }else{
                    //sInCd = "100";
                    sInDtlCd = "101"; //구매입고
                }
                sInCd = "100";
                
                //입고처 
                sInPlcId = (String) rsInMst.getRecordMap(0).get("CNSL_DEALCO_CD");
                if(sInPlcId == null || "".equals(sInPlcId)){
                    sInPlcId = "11010"; // 본사창고..
                }

                // 2. 입고  처리 (입고상태를 입고완료로 처리함)
                // 2-1. 입고 tabel insert
                reqData = new DataSet();
                reqData.putFieldMap(rsInMst.getRecordMap(0));
                reqData.putField("IN_MGMT_NO", sInMgmtNo); //입고관리번호 셋팅           
                reqData.putField("IN_CL", sInDtlCd); //입고구분  
                reqData.putField("IN_CONF_DT", sCurrDt); //입고확정일자 
                reqData.putField("IN_ST", sInSt); //입고상태 
                reqData.putField("FS_REG_USER_ID", ca.getUserNo());
                reqData.putField("LS_CHG_USER_ID", ca.getUserNo());
                
                dEPRJdgQltyJdgMgmt.dIRjdgQltyJdgInRgst(reqData, onlineCtx);
                // **** 입고마스터 생성완료
                
                if ("114".equals(sInDtlCd)) {
                    // 매입입고
                    iCount = 1;
                } else {
                    //구매입고
                    iCount = rsProdList.getRecordCount();
                }
                
                // 2-2. 입고상세 tabel insert
                for (int i = 0; i < iCount; i++) {
                    IDataSet reqProdData = new DataSet();
                    reqProdData.putFieldMap(rsProdList.getRecordMap(i));
                    
                    reqData = new DataSet();
                    reqData.putFieldMap(rsInMst.getRecordMap(0));
                    reqData.putFieldMap(rsProdList.getRecordMap(i));
                    
                    // 입고순번 얻기
                    resData = dEPRJdgQltyJdgMgmt.dSInqDisInDtlSeq(reqData, onlineCtx);
                    sInSeq = resData.getField("IN_SEQ").toString();
                    
                    reqData.putField("IN_SEQ",sInSeq);  // 기타재고순번  셋팅.
                    reqData.putField("IN_MGMT_NO",sInMgmtNo);
                    reqData.putField("PRCH_MGMT_NO",sPrchsMgmtNo);
                    BigDecimal nQtyTemp = new BigDecimal(0);
                    nQtyTemp = new BigDecimal(reqProdData.getLongField("IN_QTY"));
                    reqData.putField("IN_QTY",nQtyTemp);  //입고확정수량    
                    reqData.putField("IN_CONF_DT",sCurrDt);  //입고확정일자   
                    reqData.putField("IN_CONF_YN","Y");  //입고확정여부
                    nQtyTemp = new BigDecimal(reqProdData.getLongField("IN_QTY"));
                    reqData.putField("IN_CONF_QTY",nQtyTemp);
                    nQtyTemp = new BigDecimal(reqProdData.getLongField("IN_AMT"));
                    reqData.putField("IN_AMT",nQtyTemp);  //입고금
                    reqData.putField("FS_REG_USER_ID", ca.getUserNo());
                    reqData.putField("LS_CHG_USER_ID", ca.getUserNo());
                    //입고상세 생성
                    dEPRJdgQltyJdgMgmt.dIRjdgQltyJdgInDtlRgst(reqData, onlineCtx);
                    // **** 입고상세 생성완료
                    
                    // 2-3. 입고구성품 tabel insert
                    // 매입일경우만 구성품을 생성시켜줌
                    if ("114".equals(sInDtlCd)) {
                        dEPRJdgQltyJdgMgmt.dIRjdgQltyJdgInCpntRgst(reqData, onlineCtx);
                    }
                    // 입고구성품 생성완료

                    // 3. 재고  처리
                    // 3-1. 재고 tabel insert
                    reqData = new DataSet();
                    reqData.putFieldMap(rsInMst.getRecordMap(0));
                    reqData.putFieldMap(rsProdList.getRecordMap(i));
                    reqData.putField("IN_MGMT_NO",sInMgmtNo);
                    nQtyTemp = new BigDecimal(0);
                    nQtyTemp = new BigDecimal(reqProdData.getLongField("IN_QTY"));
                    reqData.putField("IN_QTY",nQtyTemp);  //입고확정수량    
                    reqData.putField("IN_CONF_DT",sCurrDt);  //입고확정일자   
                    reqData.putField("IN_CONF_YN","Y");  //입고확정여부
                    nQtyTemp = new BigDecimal(reqProdData.getLongField("IN_QTY"));
                    reqData.putField("IN_CONF_QTY",nQtyTemp);
                    nQtyTemp = new BigDecimal(reqProdData.getLongField("IN_AMT"));
                    reqData.putField("IN_AMT",nQtyTemp);  //입고금
                    reqData.putField("FS_REG_USER_ID", ca.getUserNo());
                    reqData.putField("LS_CHG_USER_ID", ca.getUserNo());
                    
                    reqData.putField("HLD_DEALCO_ID", sInPlcId); //입고처 -> 보유처  
                    reqData.putField("INVE_ST", sDisSt); //재고상태  
                    //reqData.putField("EQP_ST", mProdData.get("eqp_st")); //단말기상태
                    reqData.putField("LAST_IN_OUT_DT", sCurrDt); //최종입출고일자
                    reqData.putField("IN_OUT_CL", sInCd); //최종입출고구분 
                    reqData.putField("IN_OUT_DTL_CL", sInDtlCd); //최종일출고상세구분    
                    reqData.putField("FST_IN_CONF_DT", sCurrDt); //최초 입고확정일자
                    //reqData.putField("FST_IN_DEALCO_ID", rsInMst.getRecordMap(0).get("")); //최초입고처 
                    reqData.putField("FST_IN_DEALCO_ID","");
                    reqData.putField("PRCHCO_ID", rsInMst.getRecordMap(0).get("PRCHCO_ID")); //최초매입처  
                    reqData.putField("LAST_MOV_IN_DT", sCurrDt); //마지막이동입고일자
                    reqData.putField("USED_CL", "1"); //중고구분 
                    reqData.putField("INVE_AMT", rsProdList.getRecordMap(i).get("IN_AMT")); //재고금액
                    
                    nQtyTemp = new BigDecimal(reqProdData.getLongField("IN_QTY"));
                    reqData.putField("QTY",nQtyTemp);  //입고수량 ->수량
                    reqData.putField("UPRC", rsProdList.getRecordMap(i).get("IN_AMT")); //입고단가 ->금액
                    reqData.putField("AMT", rsProdList.getRecordMap(i).get("IN_AMT")); //입고단가 ->금액
                    
                    if("1".equals(rsProdList.getRecordMap(i).get("PROD_CL").toString())){
                        // 단말기 재고 추가(매입 단말기)
                        dEPRJdgQltyJdgMgmt.dIRJdgQltyJdgEqpDisRgst(reqData, onlineCtx);
                        // 단말기 재고 금액 추가
                        reqData.putField("OCCR_CL", "01"); //발생구분
                        reqData.putField("OCCR_DT", sCurrDt); //발생일자
                        dEPRJdgQltyJdgMgmt.dIRjdgQltyJdgDisAmtRgst(reqData, onlineCtx);
                        
                        // 단말기 재고 입출고 이력
                        reqData.putField("IN_MGMT_NO", sInMgmtNo); //입고관리번호
                        reqData.putField("IN_SEQ", sInSeq); //입고관리순번
                        reqData.putField("OUT_MGMT_NO", ""); //출고관리번호
                        reqData.putField("OUT_SEQ", ""); //출고관리순번
                        reqData.putField("IN_OUT_DT", sCurrDt); //입출고일자
                        reqData.putField("IN_OUT_CL", sInCd); //입출고구분
                        reqData.putField("IN_OUT_DTL_CL", sInDtlCd); //입출고상세구분
                        reqData.putField("PRCH_DEALCO_ID", rsInMst.getRecordMap(0).get("PRCHCO_ID")); // 매입처
                        reqData.putField("OUT_DEALCO_ID", rsInMst.getRecordMap(0).get("PRCHCO_ID")); //출고처
                        reqData.putField("IN_DEALCO_ID", rsInMst.getRecordMap(0).get("IN_DEALCO_ID")); //입고처
                        
                        dEPRJdgQltyJdgMgmt.dIRJdgQltyJdgDisInoutHstRgst(reqData, onlineCtx);
                        
                        // 단말기 재고 이력
                        //시퀀스추가필요
                        reqData.putField("INOUT_MGMT_NO", sInMgmtNo); //입출고관리번호
                        
                        dEPRJdgQltyJdgMgmt.dIRJdgQltyJdgDisHstRgst(reqData, onlineCtx);

                    }
                }
            }
        } catch ( BizRuntimeException e ) {
            throw e;
        }

        return responseData;
    }

    /**
	 * <pre>[FM]입금관리등록재고취소처리</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-15 18:48:39
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fIDpstMgmtRegDisCnclProc(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        IDataSet resData = new DataSet();
        IDataSet reqData = new DataSet();
        
        IRecordSet rsList = null;
        
        CommonArea ca = getCommonArea(onlineCtx);
    
        // 매입에 대한 입고 취소 처리
        // 1. 입고  취소 처리 ( 판매관리번호로 입고정보 조회) 
        // 1-1. 입고          tabel delete
        // 1-2. 입고상세    tabel delete
        // 1-3. 입고구성품 tabel delete
        // 2. 재고  취소 처리
        // 2-1. 재고                 tabel delete
        // 2-2. 재고금액           tabel delete
        // 2-3. 재고입출고이력 tabel delete
        // 2-4. 재고이력           tabel delete
    
        String sInMgmtNo    = "";  // 입고관리번호
        String sPrchsMgmtNo = "";  // 매입관리번호
        String sInoutCl     = "";  // 입출고구분

        try {
            IRecordSet rsInMst = requestData.getRecordSet("SEND_AMT_CONSULT");
            reqData.putFieldMap(rsInMst.getRecordMap(0));
            sPrchsMgmtNo = reqData.getField("PRCH_MGMT_NO");        
            
            if (sPrchsMgmtNo == null || "".equals(sPrchsMgmtNo)) {
                throw new BizRuntimeException("DMS00112"); //입고취소 처리중 판매관리번호 에러가 발생하였습니다. 담당자에게 문의하세요
            }
            
            // 1. DU lookup
            DEPRJdgQltyJdgMgmt dEPRJdgQltyJdgMgmt = (DEPRJdgQltyJdgMgmt) lookupDataUnit(DEPRJdgQltyJdgMgmt.class);
            // 입고관리번호 조회 및 상태 check
            resData = dEPRJdgQltyJdgMgmt.dSInqRJdgQltyJdgInInfo(reqData, onlineCtx);
            if(resData != null && resData.getRecordSetCount() > 0){
                rsList = resData.getRecordSet("QLTY_JDG_IN_INFO_LIST");
                if(rsList != null && rsList.getRecordCount() > 0){
                    sInMgmtNo = rsList.get(0, "IN_MGMT_NO");
                }
                else{
                    throw new BizRuntimeException("DMS00113"); //입고정보가 없습니다. 담당자에게 문의하세요.
                }
            }
            else{
                throw new BizRuntimeException("DMS00113"); //입고정보가 없습니다. 담당자에게 문의하세요.
            }
            
            if(sInMgmtNo == null || "".equals(sInMgmtNo)){
                throw new BizRuntimeException("DMS00113"); //입고정보가 없습니다. 담당자에게 문의하세요.
            }
            
            // 입출고구분 check
            sInoutCl = rsList.get(0, "IN_OUT_CL");
            if(sInoutCl == null || "".equals(sInoutCl)){
                throw new BizRuntimeException("DMS00114");  //입출고상태 정보가 없습니다. 담당자에게 문의하세요.
            }else if("3".equals(sInoutCl.substring(1, 1))){
                throw new BizRuntimeException("DMS00115");  //매입 단말기가 출고되어 처리할수 없습니다. 담당자에게 문의하세요.
            }
            
            // 1. 입고  취소 처리 ( 판매관리번호로 입고정보 조회) 
            // 1-1. 입고          tabel delete
            // 1-2. 입고상세    tabel delete
            // 1-3. 입고구성품 tabel delete
            reqData.putFieldMap(rsList.getRecordMap(0));
            reqData.putField("LS_CHG_USER_ID", ca.getUserNo());
            //reqData.putField("IN_MGMT_NO", sInMgmtNo);
            //reqData.putField("IN_SEQ", rsList.get(0, "IN_SEQ"));
            DEPEpEqpDisMgmt dEPEpEqpDisMgmt = (DEPEpEqpDisMgmt) lookupDataUnit(DEPEpEqpDisMgmt.class);

            dEPEpEqpDisMgmt.dDEpEqpDisInDel(reqData, onlineCtx);
            dEPEpEqpDisMgmt.dDEpEqpDisInDtlDel(reqData, onlineCtx);
            dEPEpEqpDisMgmt.dDEpEqpDisInCpntDel(reqData, onlineCtx);
            
            // 2. 재고  취소 처리
            // 2-1. 재고                 tabel delete
            // 2-2. 재고금액           tabel delete
            // 2-3. 재고입출고이력 tabel delete
            // 2-4. 재고이력           tabel delete

            dEPEpEqpDisMgmt.dDEpEqpDisDel(reqData, onlineCtx);
            dEPEpEqpDisMgmt.dDEpEqpDisAmtDel(reqData, onlineCtx);
            dEPEpEqpDisMgmt.dDEpEqpDisInoutHstDel(reqData, onlineCtx);
            dEPEpEqpDisMgmt.dDEpEqpDisHstDel(reqData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        }    
        return responseData;
    }

    /**
	 * <pre>[FM]요금공제대상조회</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-15 18:48:39
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : IN_FR_DT [조회시작일자]
	 *	- field : IN_TO_DT [조회종료일자]
	 *	- field : AFFIL_AGN [대리점거래처코드]
	 *	- field : CNSL_DEALCO_CD [상담처코드]
	 *	- field : EQP_SER_NO [단말기일련번호]
	 *	- field : EQP_MDL_CD [모델코드]
	 *	- field : DPSTR [예금주]
	 *	- field : CNSL_MGMT_NO [상담관리번호]
	 *	- field : BOX_NO [박스번호]
	 *	- field : DT_GB_CD [일자구분]
	 *	- field : ECO_FU_YN [에코4U여부]
	 *	- field : IN_AMT_YN [입금여부]
	 *	- field : EXCEL_ALL_YN [전체엑셀여부]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : FEE_DEDC_OBJ_LIST
	 *	- record : FEE_DEDC_OBJ_DTL_LIST
	 * </pre>
	 */
	public IDataSet fInqFeeDedcObjList(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet responseRegData = new DataSet();
        IDataSet responseDtlData = new DataSet();
        IDataSet dsCnt = new DataSet();
        IDataSet reqDs = (IDataSet) requestData.clone();//원거래의 DataSet의 훼손을 막기 위한 Clone
        IRecordSet rsPagingList = null;
        IRecordSet rsList = null;
        int intTotalCnt = 0;

        try {
            // 1. DU lookup
            DEPDpstMgmt dEPDpstMgmt = (DEPDpstMgmt) lookupDataUnit(DEPDpstMgmt.class);
            // 2. TOTAL COUNT DM호출
            dsCnt = dEPDpstMgmt.dSInqFeeDedcObjPrchListTotCnt(reqDs, onlineCtx);
            intTotalCnt = Integer.parseInt(dsCnt.getField("TOTAL_CNT"));
            PagenateUtils.setPagenatedParamsToDataSet(reqDs);
            if(requestData.getField("EXCEL_ALL_YN") != null && "Y".equals(requestData.getField("EXCEL_ALL_YN").toString()) && intTotalCnt > 2000){
                throw new BizRuntimeException("DMS00169"); //엑셀 전체 다운로드 최대조회건수를 초과하였습니다.
            }

            // 개인정보조회이력 파라미터 셋팅
            CommonArea ca = CommonUtils.getCommonArea(onlineCtx);

            reqDs.putField("USER_NO", ca.getUserNo()); 
            reqDs.putField("IP_ADDR", ca.getIpad());
            reqDs.putField("MAC_ADDR", ca.getPrcmMac());
            reqDs.putField("GLB_ID", ca.getGlobId());
            reqDs.putField("INQ_REQ_DTM", ca.getSvcStrnDttm());
                        
            // 3. LISTDM호출
            responseRegData = dEPDpstMgmt.dSInqFeeDedcObjPrchListPaging(reqDs, onlineCtx);

            rsList = responseRegData.getRecordSet("FEE_DEDC_OBJ_LIST");
            if(intTotalCnt > 0){
                if(requestData.getField("EXCEL_ALL_YN") == null || !"Y".equals(requestData.getField("EXCEL_ALL_YN").toString())){
                    // 4. 상세LISTDM호출
                    IRecordSet rsDtl = null;
                    int rsDtlIdx = 0;
                    for(int j = 0; j < rsList.getRecordCount(); j++){
                        //매입상세조회
                        //일괄로 조회시 대량건인경우 시스템에러 발생으로 각건별로 조회해서 에러 방지
                        if(rsList.get(j, "PRCH_MGMT_NO") != null && !"".equals(rsList.get(j, "PRCH_MGMT_NO").toString())){
                            reqDs.putField("PRCH_MGMT_NO", rsList.get(j, "PRCH_MGMT_NO"));
                            responseDtlData = dEPDpstMgmt.dSInqDpstMgmtPrchDtlList(reqDs, onlineCtx);
                            if(responseDtlData != null && 
                            responseDtlData.getRecordSetCount() > 0 && responseDtlData.getRecordSet("DPST_MGMT_PRCH_DTL_LIST") != null){
                                IRecordSet rsTemp = responseDtlData.getRecordSet("DPST_MGMT_PRCH_DTL_LIST");
                                if(rsDtl == null && rsTemp != null && rsTemp.getRecordCount() > 0){
                                    rsDtl = rsTemp;
                                    rsDtlIdx = rsDtl.getRecordCount();
                                }else if(rsTemp != null && rsTemp.getRecordCount() > 0){
                                    for(int i = 0; i < rsTemp.getRecordCount(); i++){
                                        rsDtlIdx = rsDtl.getRecordCount();
                                        rsDtl.addRecord(rsDtlIdx, rsTemp.getRecord(i));
                                    }
                                }
                            }
                        }
                    }
                    if(rsDtlIdx > 0){
                        responseData.putRecordSet("FEE_DEDC_OBJ_DTL_LIST", rsDtl);
                    }
                }
                responseData.putRecordSet("FEE_DEDC_OBJ_LIST", rsList);
            }else{
                responseData.putRecordSet("FEE_DEDC_OBJ_LIST", responseRegData.getRecordSet("FEE_DEDC_OBJ_LIST"));
            }
            rsPagingList = responseData.getRecordSet("FEE_DEDC_OBJ_LIST");
            PagenateUtils.setPagenatedParamToRecordSet(rsPagingList, reqDs, intTotalCnt);
        } catch ( BizRuntimeException e ) {
            throw e;
        }    
        return responseData;
    }

    /**
	 * <pre>[FM]입금관리처리등록수정</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-15 18:48:39
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : REG_OBJ_LIST
	 *		- field : OP_GB [업무구분]
	 *		- field : OP_SUB_GB [서브업무구분]
	 *		- field : PRCH_MGMT_NO [매입관리번호]
	 *		- field : CNSL_MGMT_NO [상담관리번호]
	 *		- field : EQP_MDL_CD [모델코드]
	 *		- field : EQP_SER_NO [일련번호]
	 *		- field : PRCH_DT [매입일자]
	 *		- field : CNSL_DT [상담일자]
	 *		- field : EQP_COLOR_CD [색상코드]
	 *		- field : HEADQ_NM [본부 명]
	 *		- field : MKT_TEAM_CD [마케팅 팀 코드]
	 *		- field : MKT_TEAM_NM [마케팅 팀 명]
	 *		- field : AGN_ORG_CD [대리점 조직 코드]
	 *		- field : UKEY_SUB_CD [UKEY_SUB_CD]
	 *		- field : CNSL_DEALCO_NM [상담거래처명]
	 *		- field : AFFIL_AGN_NM [대리점명]
	 *		- field : CNSL_DEALCO_CD [상담거래처코드]
	 *		- field : EQP_MDL_NM [모델명]
	 *		- field : PRCH_AMT [매입금액]
	 *		- field : BANK_CD [은행코드]
	 *		- field : ACCO_NO [계좌번호]
	 *		- field : DPSTR [예금주]
	 *		- field : AMT_RMT_YN [송금여부]
	 *		- field : RMT_DT [송금일자]
	 *		- field : EQP_ST [등급]
	 *		- field : EQP_ST_DTAIL [등급SUB]
	 *		- field : AFFIL_AGN [소속대리점코드]
	 *		- field : PRE_DC_CL [필드1]
	 *		- field : UBO_AMT [유보금액]
	 *		- field : TOT_AMT [총금액]
	 *		- field : CUST_GRP [고객 그룹]
	 *		- field : CHECKED [CHECKED]
	 *		- field : ACCO_NO_ENPT [계좌번호암호화]
	 *		- field : DPSTR_ENPT [예금주암호화]
	 *		- field : FEE_DEDC_PROC_YN [요금공제여부]
	 *		- field : FEE_DEDC_PROC_DT [요금공제일자]
	 *		- field : SVC_NO [서비스번호]
	 *		- field : SVC_MGMT_NO [서비스관리번호]
	 *		- field : CUST_CHK_DT [고객 확인 일자]
	 *		- field : FORCING_PRCH_PROC_YN [강제매입처리여부]
	 *		- field : FORCING_PRCH_PROC_DT [강제매입처리일자]
	 *		- field : PPAY_PROC_TS [선납 처리 차수]
	 *	- record : REG_OBJ_DTL_LIST
	 *		- field : PRCH_MGMT_NO [매입관리번호]
	 *		- field : EQP_MDL_CD [모델코드]
	 *		- field : PROD_CL [상품구분]
	 *		- field : IN_QTY [매입수량]
	 *		- field : IN_AMT [매입금액]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fUDpstMgmtProcRegUpd(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet reqData = new DataSet();
        reqData.putFieldMap(requestData.getFieldMap());
        CommonArea ca = getCommonArea(onlineCtx);

        IRecordSet rsList = requestData.getRecordSet("REG_OBJ_LIST");
        IRecordSet rsDtlList = null;
        IRecordSet rsMasterList = null;
        IRecordSet rsEqpSerNoList = null;
        IRecordSet rsConsultList = null;
        
        String sDate = DateUtils.getCurrentDate();
        try {
            // 1. DU lookup
            DEPDpstMgmt dEPDpstMgmt = (DEPDpstMgmt) lookupDataUnit(DEPDpstMgmt.class);
            FEPEpEqpDisMgmt fEPEpEqpDisMgmt = (FEPEpEqpDisMgmt) lookupFunctionUnit(FEPEpEqpDisMgmt.class);

            if(requestData != null && requestData.getRecordSetCount() > 0){
                if(requestData.getRecordSet("REG_OBJ_LIST") != null){
                    rsDtlList = requestData.getRecordSet("REG_OBJ_DTL_LIST");
                    IRecord rMaster = null;
                    IRecord rEqp = null;
                    IRecord rCon = null;
                    for(int  i = 0; i < rsList.getRecordCount(); i++){
                        if("1".equals(rsList.get(i, "CHECKED"))){
                            rsMasterList = new RecordSet("SEND_AMT_MASTER", new String[]{"IN_SCHD_DT","AFFIL_ORG_ID","PRCHCO_ID","IN_CL","IN_CONF_QTY","PRCH_MGMT_NO"});
                            rMaster = rsMasterList.newRecord();
                            rMaster.set("IN_SCHD_DT", sDate);
                            rMaster.set("AFFIL_ORG_ID", rsList.get(i, "AFFIL_AGN").toString());
                            rMaster.set("PRCHCO_ID", rsList.get(i, "CNSL_DEALCO_CD").toString());
                            rMaster.set("IN_CL", "114");
                            rMaster.set("IN_CONF_QTY", "1");
                            rMaster.set("PRCH_MGMT_NO", rsList.get(i, "PRCH_MGMT_NO"));
                            
                            rsEqpSerNoList = new RecordSet("SEND_AMT_EQP", new String[]{"EQP_SER_NO","EQP_COLOR_CD","EQP_MDL_CD","PROD_CL","IN_QTY","IN_AMT","EQP_ST","PRCH_MGMT_NO","CNSL_MGMT_NO","CNSL_DT","NC_REC_STATUS"});
                            rsConsultList = new RecordSet("SEND_AMT_CONSULT", new String[]{"EQP_SER_NO","EQP_COLOR_CD","EQP_MDL_CD","PROD_CL","IN_QTY","IN_AMT","EQP_ST","PRCH_MGMT_NO","CNSL_MGMT_NO","CNSL_DT","NC_REC_STATUS"});
                            rEqp = rsEqpSerNoList.newRecord();
                            rEqp.set("EQP_SER_NO", rsList.get(i, "EQP_SER_NO").toString());
                            rEqp.set("EQP_COLOR_CD", rsList.get(i, "EQP_COLOR_CD").toString());
                            rEqp.set("EQP_MDL_CD", rsList.get(i, "EQP_MDL_CD").toString());
                            rEqp.set("PROD_CL", "1");
                            rEqp.set("IN_QTY", "1");
                            if(rsList.get(i, "PRCH_AMT") == null || "".equals(rsList.get(i, "PRCH_AMT").toString())){
                                rEqp.set("IN_AMT", "0");
                            }else{
                                rEqp.set("IN_AMT", rsList.get(i, "PRCH_AMT").toString());
                            }
                            if(rsList.get(i, "EQP_ST") == null || "".equals(rsList.get(i, "EQP_ST").toString())){
                                rEqp.set("EQP_ST", "");
                            }else{
                                rEqp.set("EQP_ST", rsList.get(i, "EQP_ST").toString());
                            }
                            rEqp.set("PRCH_MGMT_NO", rsList.get(i, "PRCH_MGMT_NO").toString());
                            rEqp.set("CNSL_MGMT_NO", rsList.get(i, "CNSL_MGMT_NO").toString());
                            rEqp.set("CNSL_DT", rsList.get(i, "CNSL_DT").toString());
                            rEqp.set("NC_REC_STATUS", "insert");
                            
                            rCon = rsConsultList.newRecord();
                            rCon = rEqp;
                            rsConsultList.setRecord(0, rCon);
                            
                            requestData.putRecordSet("SEND_AMT_CONSULT", rsConsultList);
                            
                            for(int j = 0; j < rsDtlList.getRecordCount(); j++){
                                if(rsList.get(i, "PRCH_MGMT_NO").equals(rsDtlList.get(j, "PRCH_MGMT_NO").toString())){
                                    rsEqpSerNoList.addRecord(rsDtlList.getRecord(j));
                                }
                            }
                            
                            requestData.putRecordSet("SEND_AMT_MASTER", rsMasterList);
                            requestData.putRecordSet("SEND_AMT_EQP", rsEqpSerNoList);
                            
                            reqData = new DataSet();
                            reqData.putFieldMap(rsList.getRecordMap(i));
                            reqData.putField("LS_CHG_USER_ID", ca.getUserNo());
                            String strTemp = "";
                            String strOp = "";
                            String strOpSub = "";
                            strOp = rsList.get(i,"OP_GB").toString();
                            strOpSub = rsList.get(i,"OP_SUB_GB").toString();
                            if("NO".equals(strOp) && "31".equals(strOpSub)){
                                //송금처리여부
                                dEPDpstMgmt.dUDpstMgmtPrchRmtUpd(reqData, onlineCtx);
                                strTemp = rsList.get(i,"AMT_RMT_YN").toString();
                            }else if("NO".equals(strOp) && "32".equals(strOpSub)){
                                //요금공제처리여부
                                dEPDpstMgmt.dUDpstMgmtPrchFeeDedcUpd(reqData, onlineCtx);
                                strTemp = rsList.get(i,"FEE_DEDC_PROC_YN").toString();
                            }else if("NO".equals(strOp) && "34".equals(strOpSub)){
                                //강제매입처리여부
                                dEPDpstMgmt.dUDpstMgmtForcingPrchUpd(reqData, onlineCtx);
                                strTemp = rsList.get(i,"FORCING_PRCH_PROC_YN").toString();
                            }

                            if("N".equals(strTemp)){
                                //재고처리
                                this.fIDpstMgmtRegDisInProc(requestData, onlineCtx);
                                //구성품출고,상품입고처리
                                fEPEpEqpDisMgmt.fIEpEqpCmptDisProc(requestData, onlineCtx);
                            }
                            else {
                                //구성품출고취소,상품입고취소
                                fEPEpEqpDisMgmt.fIEpEqpCmptDisCnclProc(requestData, onlineCtx);
                                //재고취소처리
                                this.fIDpstMgmtRegDisCnclProc(requestData, onlineCtx);
                            }
                        }
                    }
                }
            }
        } catch ( BizRuntimeException e ) {
            throw e;
        }
        return responseData;
    }

    /**
	 * <pre>[FM]강제매입대상조회</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-15 18:48:39
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : IN_FR_DT [조회시작일자]
	 *	- field : IN_TO_DT [조회종료일자]
	 *	- field : AFFIL_AGN [대리점거래처코드]
	 *	- field : CNSL_DEALCO_CD [상담처코드]
	 *	- field : EQP_SER_NO [단말기일련번호]
	 *	- field : EQP_MDL_CD [모델코드]
	 *	- field : DPSTR [예금주]
	 *	- field : CNSL_MGMT_NO [상담관리번호]
	 *	- field : BOX_NO [박스번호]
	 *	- field : DT_GB_CD [일자구분]
	 *	- field : ECO_FU_YN [에코4U여부]
	 *	- field : EXCEL_ALL_YN [전체엑셀여부]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : FORCING_PRCH_OBJ_LIST
	 *	- record : FORCING_PRCH_OBJ_DTL_LIST
	 * </pre>
	 */
	public IDataSet fInqForcingPrchObjList(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet responseRegData = new DataSet();
        IDataSet responseDtlData = new DataSet();
        IDataSet dsCnt = new DataSet();
        IDataSet reqDs = (IDataSet) requestData.clone();//원거래의 DataSet의 훼손을 막기 위한 Clone
        IRecordSet rsPagingList = null;
        IRecordSet rsList = null;
        int intTotalCnt = 0;

        try {
            // 1. DU lookup
            DEPDpstMgmt dEPDpstMgmt = (DEPDpstMgmt) lookupDataUnit(DEPDpstMgmt.class);
            // 2. TOTAL COUNT DM호출
            dsCnt = dEPDpstMgmt.dSInqForcingPrchObjListTotCnt(reqDs, onlineCtx);
            intTotalCnt = Integer.parseInt(dsCnt.getField("TOTAL_CNT"));
            PagenateUtils.setPagenatedParamsToDataSet(reqDs);
            if(requestData.getField("EXCEL_ALL_YN") != null && "Y".equals(requestData.getField("EXCEL_ALL_YN").toString()) && intTotalCnt > 2000){
                throw new BizRuntimeException("DMS00169"); //엑셀 전체 다운로드 최대조회건수를 초과하였습니다.
            }

            // 개인정보조회이력 파라미터 셋팅
            CommonArea ca = CommonUtils.getCommonArea(onlineCtx);

            reqDs.putField("USER_NO", ca.getUserNo()); 
            reqDs.putField("IP_ADDR", ca.getIpad());
            reqDs.putField("MAC_ADDR", ca.getPrcmMac());
            reqDs.putField("GLB_ID", ca.getGlobId());
            reqDs.putField("INQ_REQ_DTM", ca.getSvcStrnDttm());
                        
            // 3. LISTDM호출
            responseRegData = dEPDpstMgmt.dSInqForcingPrchObjListPaging(reqDs, onlineCtx);

            rsList = responseRegData.getRecordSet("FORCING_PRCH_OBJ_LIST");
            if(intTotalCnt > 0){
                if(requestData.getField("EXCEL_ALL_YN") == null || !"Y".equals(requestData.getField("EXCEL_ALL_YN").toString())){
                    // 4. 상세LISTDM호출
                    IRecordSet rsDtl = null;
                    int rsDtlIdx = 0;
                    for(int j = 0; j < rsList.getRecordCount(); j++){
                        //매입상세조회
                        //일괄로 조회시 대량건인경우 시스템에러 발생으로 각건별로 조회해서 에러 방지
                        if(rsList.get(j, "PRCH_MGMT_NO") != null && !"".equals(rsList.get(j, "PRCH_MGMT_NO").toString())){
                            reqDs.putField("PRCH_MGMT_NO", rsList.get(j, "PRCH_MGMT_NO"));
                            responseDtlData = dEPDpstMgmt.dSInqDpstMgmtPrchDtlList(reqDs, onlineCtx);
                            if(responseDtlData != null && 
                            responseDtlData.getRecordSetCount() > 0 && responseDtlData.getRecordSet("DPST_MGMT_PRCH_DTL_LIST") != null){
                                IRecordSet rsTemp = responseDtlData.getRecordSet("DPST_MGMT_PRCH_DTL_LIST");
                                if(rsDtl == null && rsTemp != null && rsTemp.getRecordCount() > 0){
                                    rsDtl = rsTemp;
                                    rsDtlIdx = rsDtl.getRecordCount();
                                }else if(rsTemp != null && rsTemp.getRecordCount() > 0){
                                    for(int i = 0; i < rsTemp.getRecordCount(); i++){
                                        rsDtlIdx = rsDtl.getRecordCount();
                                        rsDtl.addRecord(rsDtlIdx, rsTemp.getRecord(i));
                                    }
                                }
                            }
                        }
                    }
                    if(rsDtlIdx > 0){
                        responseData.putRecordSet("FORCING_PRCH_OBJ_DTL_LIST", rsDtl);
                    }
                }
                responseData.putRecordSet("FORCING_PRCH_OBJ_LIST", rsList);
            }else{
                responseData.putRecordSet("FORCING_PRCH_OBJ_LIST", responseRegData.getRecordSet("FORCING_PRCH_OBJ_LIST"));
            }
            rsPagingList = responseData.getRecordSet("FORCING_PRCH_OBJ_LIST");
            PagenateUtils.setPagenatedParamToRecordSet(rsPagingList, reqDs, intTotalCnt);
        } catch ( BizRuntimeException e ) {
            throw e;
        }    
        return responseData;
	}

    /**
	 * <pre>[FM]재감정송금대상목록조회</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-15 18:48:39
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : IN_FR_DT [조회시작일자]
	 *	- field : IN_TO_DT [조회종료일자]
	 *	- field : AFFIL_AGN [대리점거래처코드]
	 *	- field : CNSL_DEALCO_CD [상담처코드]
	 *	- field : EQP_SER_NO [단말기일련번호]
	 *	- field : EQP_MDL_CD [모델코드]
	 *	- field : DPSTR [예금주]
	 *	- field : CNSL_MGMT_NO [상담관리번호]
	 *	- field : BOX_NO [박스번호]
	 *	- field : DT_GB_CD [일자구분]
	 *	- field : ECO_FU_YN [에코4U여부]
	 *	- field : PRE_DC_CL [선할인여부]
	 *	- field : IN_AMT_YN [입금여부]
	 *	- field : EXCEL_ALL_YN [전체엑셀여부]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : SEND_AMT_OBJ_LIST
	 * </pre>
	 */
	public IDataSet fInqRJdgSendAmtObjList(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet dsCnt = new DataSet();
        IDataSet reqDs = (IDataSet) requestData.clone();//원거래의 DataSet의 훼손을 막기 위한 Clone
        IRecordSet rsPagingList = null;
        int intTotalCnt = 0;

        try {
            // 1. DU lookup
            DEPDpstMgmt dEPDpstMgmt = (DEPDpstMgmt) lookupDataUnit(DEPDpstMgmt.class);
            // 2. TOTAL COUNT DM호출
            dsCnt = dEPDpstMgmt.dSInqRJdgSendAmtObjPrchListTotCnt(reqDs, onlineCtx);
            intTotalCnt = Integer.parseInt(dsCnt.getField("TOTAL_CNT"));
            PagenateUtils.setPagenatedParamsToDataSet(reqDs);
            if(requestData.getField("EXCEL_ALL_YN") != null && "Y".equals(requestData.getField("EXCEL_ALL_YN").toString()) && intTotalCnt > 2000){
                throw new BizRuntimeException("DMS00169"); //엑셀 전체 다운로드 최대조회건수를 초과하였습니다.
            }
            
            // 개인정보조회이력 파라미터 셋팅
            CommonArea ca = CommonUtils.getCommonArea(onlineCtx);

            reqDs.putField("USER_NO", ca.getUserNo()); 
            reqDs.putField("IP_ADDR", ca.getIpad());
            reqDs.putField("MAC_ADDR", ca.getPrcmMac());
            reqDs.putField("GLB_ID", ca.getGlobId());
            reqDs.putField("INQ_REQ_DTM", ca.getSvcStrnDttm());
                        
            // 3. LISTDM호출
            responseData = dEPDpstMgmt.dSInqRJdgSendAmtObjPrchListPaging(reqDs, onlineCtx);
            rsPagingList = responseData.getRecordSet("SEND_AMT_OBJ_LIST");
            PagenateUtils.setPagenatedParamToRecordSet(rsPagingList, reqDs, intTotalCnt);
        } catch ( BizRuntimeException e ) {
            throw e;
        }
    
        return responseData;
    }

    /**
	 * <pre>[FM]입금관리재감정송금등록</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-15 18:48:39
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RQ_REG_OBJ_LIST
	 *		- field : OP_GB [업무구분]
	 *		- field : OP_SUB_GB [서브업무구분]
	 *		- field : CNSL_MGMT_NO [상담관리번호]
	 *		- field : PAY_MTHD_YN [지급여부]
	 *		- field : PAY_MTHD [지급방법]
	 *		- field : PAY_DT [지급일자]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fUDpstMgmtRJdgSendAmtReg(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet reqData = new DataSet();
        IDataSet resData = new DataSet();
        reqData.putFieldMap(requestData.getFieldMap());
        IRecordSet reqSet = requestData.getRecordSet("RQ_REG_OBJ_LIST");

        try {
            // 1. DU lookup
            DEPDpstMgmt dEPDpstMgmt = (DEPDpstMgmt) lookupDataUnit(DEPDpstMgmt.class);
            CommonArea ca = getCommonArea(onlineCtx);
            // 2. 
            for(int i = 0; i < reqSet.getRecordCount(); i++){
                reqData.putFieldMap(reqSet.getRecordMap(i));
                reqData.putField("LS_CHG_USER_ID", ca.getUserNo());
                reqData.putField("PAY_MTHD", "01");
                if(reqData.getField("CNSL_MGMT_NO") != null && !"".equals(reqData.getField("CNSL_MGMT_NO").toString())){
                    //3. 지급등록
                    dEPDpstMgmt.dUDpstMgmtRJdgRmtYesUpd(reqData, onlineCtx);
                }
            }
        } catch ( BizRuntimeException e ) {
            throw e;
        }
    
        return responseData;
    }

    /**
	 * <pre>[FM]입금관리재감정송금취소</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-15 18:48:39
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RQ_REG_OBJ_LIST
	 *		- field : OP_GB [업무구분]
	 *		- field : OP_SUB_GB [서브업무구분]
	 *		- field : CNSL_MGMT_NO [상담관리번호]
	 *		- field : PAY_MTHD_YN [지급여부]
	 *		- field : PAY_MTHD [지급방법]
	 *		- field : PAY_DT [지급일자]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fUDpstMgmtRJdgSendAmtCncl(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet reqData = new DataSet();
        IDataSet resData = new DataSet();
        reqData.putFieldMap(requestData.getFieldMap());
        IRecordSet reqSet = requestData.getRecordSet("RQ_REG_OBJ_LIST");

        try {
            // 1. DU lookup
            DEPDpstMgmt dEPDpstMgmt = (DEPDpstMgmt) lookupDataUnit(DEPDpstMgmt.class);
            DEPTlyReg dEPTlyReg = (DEPTlyReg) lookupDataUnit(DEPTlyReg.class);
            CommonArea ca = getCommonArea(onlineCtx);
            // 2. 
            for(int i = 0; i < reqSet.getRecordCount(); i++){
                reqData.putFieldMap(reqSet.getRecordMap(i));
                reqData.putField("LS_CHG_USER_ID", ca.getUserNo());
                if(reqData.getField("CNSL_MGMT_NO") != null && !"".equals(reqData.getField("CNSL_MGMT_NO").toString())){
                    //3. 취소
                    resData = dEPDpstMgmt.dUDpstMgmtRJdgRmtNoUpd(reqData, onlineCtx);
                }
            }
        } catch ( BizRuntimeException e ) {
            throw e;
        }
    
        return responseData;
    }

    /**
	 * <pre>[FM]재감정요금공제대상조회</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-15 18:48:39
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : IN_FR_DT [조회시작일자]
	 *	- field : IN_TO_DT [조회종료일자]
	 *	- field : AFFIL_AGN [대리점거래처코드]
	 *	- field : CNSL_DEALCO_CD [상담처코드]
	 *	- field : EQP_SER_NO [단말기일련번호]
	 *	- field : EQP_MDL_CD [모델코드]
	 *	- field : DPSTR [예금주]
	 *	- field : CNSL_MGMT_NO [상담관리번호]
	 *	- field : BOX_NO [박스번호]
	 *	- field : DT_GB_CD [일자구분]
	 *	- field : ECO_FU_YN [에코4U여부]
	 *	- field : PRE_DC_CL [선할인여부]
	 *	- field : SCRB_MTHD [클럽T여부]
	 *	- field : IN_AMT_YN [입금여부]
	 *	- field : EXCEL_ALL_YN [전체엑셀여부]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : FEE_DEDC_OBJ_LIST
	 * </pre>
	 */
	public IDataSet fInqRJdgFeeDedcObjList(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet dsCnt = new DataSet();
        IDataSet reqDs = (IDataSet) requestData.clone();//원거래의 DataSet의 훼손을 막기 위한 Clone
        IRecordSet rsPagingList = null;
        int intTotalCnt = 0;

        try {
            // 1. DU lookup
            DEPDpstMgmt dEPDpstMgmt = (DEPDpstMgmt) lookupDataUnit(DEPDpstMgmt.class);
            // 2. TOTAL COUNT DM호출
            dsCnt = dEPDpstMgmt.dSInqRJdgFeeDedcObjPrchListTotCnt(reqDs, onlineCtx);
            intTotalCnt = Integer.parseInt(dsCnt.getField("TOTAL_CNT"));
            PagenateUtils.setPagenatedParamsToDataSet(reqDs);
            if(requestData.getField("EXCEL_ALL_YN") != null && "Y".equals(requestData.getField("EXCEL_ALL_YN").toString()) && intTotalCnt > 2000){
                throw new BizRuntimeException("DMS00169"); //엑셀 전체 다운로드 최대조회건수를 초과하였습니다.
            }
            
            // 개인정보조회이력 파라미터 셋팅
            CommonArea ca = CommonUtils.getCommonArea(onlineCtx);

            reqDs.putField("USER_NO", ca.getUserNo()); 
            reqDs.putField("IP_ADDR", ca.getIpad());
            reqDs.putField("MAC_ADDR", ca.getPrcmMac());
            reqDs.putField("GLB_ID", ca.getGlobId());
            reqDs.putField("INQ_REQ_DTM", ca.getSvcStrnDttm());
                        
            // 3. LISTDM호출
            responseData = dEPDpstMgmt.dSInqRJdgFeeDedcObjPrchListPaging(reqDs, onlineCtx);
            rsPagingList = responseData.getRecordSet("FEE_DEDC_OBJ_LIST");
            PagenateUtils.setPagenatedParamToRecordSet(rsPagingList, reqDs, intTotalCnt);
        } catch ( BizRuntimeException e ) {
            throw e;
        }
    
        return responseData;
    }

    /**
	 * <pre>[FM]입금관리재감정요금공제등록</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-15 18:48:39
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RQ_REG_OBJ_LIST
	 *		- field : OP_GB [업무구분]
	 *		- field : OP_SUB_GB [서브업무구분]
	 *		- field : CNSL_MGMT_NO [상담관리번호]
	 *		- field : PAY_MTHD_YN [지급여부]
	 *		- field : PAY_MTHD [지급방법]
	 *		- field : PAY_DT [지급일자]
	 *		- field : PPAY_PROC_TS [선납 처리 차수]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fUDpstMgmtRJdgFeeDedcReg(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet reqData = new DataSet();
        IDataSet resData = new DataSet();
        reqData.putFieldMap(requestData.getFieldMap());
        IRecordSet reqSet = requestData.getRecordSet("RQ_REG_OBJ_LIST");

        try {
            // 1. DU lookup
            DEPDpstMgmt dEPDpstMgmt = (DEPDpstMgmt) lookupDataUnit(DEPDpstMgmt.class);
            DEPTlyReg dEPTlyReg = (DEPTlyReg) lookupDataUnit(DEPTlyReg.class);
            CommonArea ca = getCommonArea(onlineCtx);
            // 2. 
            for(int i = 0; i < reqSet.getRecordCount(); i++){
                reqData.putFieldMap(reqSet.getRecordMap(i));
                reqData.putField("LS_CHG_USER_ID", ca.getUserNo());
                reqData.putField("PAY_MTHD", "02");
                if(reqData.getField("CNSL_MGMT_NO") != null && !"".equals(reqData.getField("CNSL_MGMT_NO").toString())){
                    //3. 지급등록
                    resData = dEPDpstMgmt.dUDpstMgmtRJdgRmtYesUpd(reqData, onlineCtx);
                }
            }
        } catch ( BizRuntimeException e ) {
            throw e;
        }
    
        return responseData;
    }

    /**
	 * <pre>입금관리재고임시처리</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-15 18:48:39
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : DPST_TEMP_PROC
	 *		- field : CNSL_MGMT_NO [상담관리번호]
	 *		- field : PRCH_MGMT_NO [매입관리번호]
	 *		- field : GUBUN [구분]
	 *		- field : ERROR [에러]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : DPST_TEMP_PROC
	 *		- field : CNSL_MGMT_NO [상담관리번호]
	 *		- field : PRCH_MGMT_NO [매입관리번호]
	 *		- field : GUBUN [구분]
	 *		- field : ERROR [에러]
	 * </pre>
	 */
	public IDataSet fUDpstTempProc(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet reqDisData = new DataSet();
        IDataSet reqData = new DataSet();
        IDataSet resData = new DataSet();
    
        IRecordSet reqSet = requestData.getRecordSet("DPST_TEMP_PROC");
        if(reqSet != null){
            String strGubun = "";
            String strError = "";
            String strCnslNo = "";
            String strPrchNo = "";

            IRecordSet rsMasterList = null;
            IRecordSet rsEqpSerNoList = null;
            IRecordSet rsConsultList = null;

            DEPDpstMgmt dEPDpstMgmt = (DEPDpstMgmt) lookupDataUnit(DEPDpstMgmt.class);
            DEPEpEqpDisMgmt dEPEpEqpDisMgmt = (DEPEpEqpDisMgmt) lookupDataUnit(DEPEpEqpDisMgmt.class);
            FEPEpEqpDisMgmt fEPEpEqpDisMgmt = (FEPEpEqpDisMgmt) lookupFunctionUnit(FEPEpEqpDisMgmt.class);

            for(int i = 0; i < reqSet.getRecordCount(); i++){
                rsMasterList = new RecordSet("SEND_AMT_MASTER", new String[]{"IN_SCHD_DT","AFFIL_ORG_ID","PRCHCO_ID","IN_CL","IN_CONF_QTY","PRCH_MGMT_NO"});
                rsEqpSerNoList = new RecordSet("SEND_AMT_EQP", new String[]{"EQP_SER_NO","EQP_COLOR_CD","EQP_MDL_CD","PROD_CL","IN_QTY","IN_AMT","EQP_ST","PRCH_MGMT_NO","CNSL_MGMT_NO","CNSL_DT","NC_REC_STATUS"});
                rsConsultList = new RecordSet("SEND_AMT_CONSULT", new String[]{"EQP_SER_NO","EQP_COLOR_CD","EQP_MDL_CD","PROD_CL","IN_QTY","IN_AMT","EQP_ST","PRCH_MGMT_NO","CNSL_MGMT_NO","CNSL_DT","NC_REC_STATUS"});

                IRecord rMaster = null;
                IRecord rEqp = null;
                IRecord rCon = null;
                
                strGubun = reqSet.getRecord(i).get("GUBUN").toString();
                strCnslNo = reqSet.getRecord(i).get("CNSL_MGMT_NO").toString();
                strPrchNo = reqSet.getRecord(i).get("PRCH_MGMT_NO").toString();
                reqData = new DataSet();
                reqData.putField("PRCH_MGMT_NO", strPrchNo);
                reqData.putField("CNSL_MGMT_NO", strCnslNo);
                
                IDataSet dsCnt = new DataSet();
                resData = dEPDpstMgmt.dSInqDpstMgmtPrchDtlList(reqData, onlineCtx);
                dsCnt = dEPEpEqpDisMgmt.dSInqEpEqpProdDisRgstCnt(reqData, onlineCtx);
                int intDisCnt = Integer.parseInt(dsCnt.getField("CNT"));
                if(intDisCnt > 0){
                    reqSet.getRecord(i).set("ERROR", "기등록재고");
                    continue;
                }
                
                if(resData != null && resData.getRecordSetCount() > 0 && resData.getRecordSet("DPST_MGMT_PRCH_DTL_LIST") != null){
                    reqDisData.putRecordSet("REG_OBJ_DTL_LIST", resData.getRecordSet("DPST_MGMT_PRCH_DTL_LIST"));
                }
                resData = dEPDpstMgmt.dSInqDpstTempProcPrch(reqData, onlineCtx);
                //재고처리
                rMaster = rsMasterList.newRecord();
                rMaster.set("IN_SCHD_DT", resData.getRecordSet("DPST_TEMP_PROC_PRCH").get(0, "PRCH_DT").toString());
                rMaster.set("AFFIL_ORG_ID", resData.getRecordSet("DPST_TEMP_PROC_PRCH").get(0, "AFFIL_AGN").toString());
                rMaster.set("PRCHCO_ID", resData.getRecordSet("DPST_TEMP_PROC_PRCH").get(0, "CNSL_DEALCO_CD").toString());
                rMaster.set("IN_CL", "114");
                rMaster.set("IN_CONF_QTY", "1");
                rMaster.set("PRCH_MGMT_NO", resData.getRecordSet("DPST_TEMP_PROC_PRCH").get(0, "PRCH_MGMT_NO"));

                rEqp = rsEqpSerNoList.newRecord();
                rEqp.set("EQP_SER_NO", resData.getRecordSet("DPST_TEMP_PROC_PRCH").get(0, "EQP_SER_NO").toString());
                rEqp.set("EQP_COLOR_CD", resData.getRecordSet("DPST_TEMP_PROC_PRCH").get(0, "EQP_COLOR_CD").toString());
                rEqp.set("EQP_MDL_CD", resData.getRecordSet("DPST_TEMP_PROC_PRCH").get(0, "EQP_MDL_CD").toString());
                rEqp.set("PROD_CL", "1");
                rEqp.set("IN_QTY", "1");
                if(resData.getRecordSet("DPST_TEMP_PROC_PRCH").get(0, "PRCH_AMT") == null || "".equals(resData.getRecordSet("DPST_TEMP_PROC_PRCH").get(0, "PRCH_AMT").toString())){
                    rEqp.set("IN_AMT", "0");
                }else{
                    rEqp.set("IN_AMT", resData.getRecordSet("DPST_TEMP_PROC_PRCH").get(0, "PRCH_AMT").toString());
                }
                if(resData.getRecordSet("DPST_TEMP_PROC_PRCH").get(0, "EQP_ST") == null || "".equals(resData.getRecordSet("DPST_TEMP_PROC_PRCH").get(0, "EQP_ST").toString())){
                    rEqp.set("EQP_ST", "");
                }else{
                    rEqp.set("EQP_ST", resData.getRecordSet("DPST_TEMP_PROC_PRCH").get(0, "EQP_ST").toString());
                }
                rEqp.set("PRCH_MGMT_NO", resData.getRecordSet("DPST_TEMP_PROC_PRCH").get(0, "PRCH_MGMT_NO").toString());
                rEqp.set("CNSL_MGMT_NO", resData.getRecordSet("DPST_TEMP_PROC_PRCH").get(0, "CNSL_MGMT_NO").toString());
                rEqp.set("CNSL_DT", resData.getRecordSet("DPST_TEMP_PROC_PRCH").get(0, "CNSL_DT").toString());
                rEqp.set("NC_REC_STATUS", "insert");
                
                rCon = rsConsultList.newRecord();
                rCon = rEqp;
                rsConsultList.setRecord(0, rCon);
                
                reqData.putRecordSet("SEND_AMT_CONSULT", rsConsultList);
                
                for(int j = 0; j < reqDisData.getRecordSet("REG_OBJ_DTL_LIST").getRecordCount(); j++){
                    rsEqpSerNoList.addRecord(reqDisData.getRecordSet("REG_OBJ_DTL_LIST").getRecord(j));
                }
                
                reqData.putRecordSet("SEND_AMT_MASTER", rsMasterList);
                reqData.putRecordSet("SEND_AMT_EQP", rsEqpSerNoList);
                
                //재고처리
                this.fIDpstTempProcDisInProc(reqData, onlineCtx);
                //구성품출고,상품입고처리
                fEPEpEqpDisMgmt.fIEpEqpCmptDisProc(reqData, onlineCtx);

            }
            responseData.putRecordSet("DPST_TEMP_PROC",reqSet);
        }
    
        return responseData;
    }

    /**
	 * <pre>[FM]입금관리임시처리재고등록처리</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2016-01-06 19:30:00
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fIDpstTempProcDisInProc(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet resData = new DataSet();
        IDataSet reqData = new DataSet();
        IDataSet dsCnt = new DataSet();

        reqData.putFieldMap(requestData.getFieldMap());
        CommonArea ca = getCommonArea(onlineCtx);
    
        String sInMgmtNo = ""; //입고관리번호
        String sPrchsMgmtNo = "";
        String sInSeq = ""; // 입고순번
        
        int iCount = 0;

        //RQ_SEND_AMT_OBJ_EQP_LIST
        //RQ_SEND_AMT_OBJ_DTL_LIST
        //SEND_AMT_MASTER
        //SEND_AMT_EQP
        //SEND_AMT_CONSULT
        
        // 발주,매입에 대한 입고 처리
        // 1. 입고관리번호 생성
        // 2. 입고  처리 (입고 상태를 입고확정으로 처리함)
        // 2-1. 입고 tabel insert
        // 2-2. 입고상세 tabel insert
        // 2-3. 입고구성품 tabel insert
        // 3. 재고  처리
        // 3-1. 재고 tabel insert
        // 3-2. 재고금액 tabel insert
        // 3-3. 재고입출고이력 tabel insert
        // 3-4. 재고이력 tabel insert
        // 3-5. 기타재고 tabel insert
        // 3-6. 기타재고입출고이력 tabel insert
        // 3-7. 기타재고이력 tabel insert

        try {
            // 1. DU lookup
            DEPRJdgQltyJdgMgmt dEPRJdgQltyJdgMgmt = (DEPRJdgQltyJdgMgmt) lookupDataUnit(DEPRJdgQltyJdgMgmt.class);
            // 입고 MASTER. 
            IRecordSet rsInMst = requestData.getRecordSet("SEND_AMT_MASTER");
    
            // 입고  상품 리스트. 
            IRecordSet rsProdList = requestData.getRecordSet("SEND_AMT_EQP");
            reqData.putFieldMap(rsProdList.getRecordMap(0));
            
            // 1. 입고관리번호 생성
            // 신규인 경우에 입고관리번호를 생성한다.
            // 매입재고확인
            /*
            dsCnt = dEPRJdgQltyJdgMgmt.dSRJdgQJdgInqEqpDisList(reqData, onlineCtx);
            int intDisCnt = Integer.parseInt(dsCnt.getField("DIS_CNT"));
            if(intDisCnt > 0){
                throw new BizRuntimeException("DMS00110");  //구성품이 매입 되어있는 상품입니다. 확인 하시기 바랍니다.
            }
            // 상품재고확인
            dsCnt = dEPRJdgQltyJdgMgmt.dSRJdgQJdgInqEqpProdDisList(reqData, onlineCtx);
            intDisCnt = Integer.parseInt(dsCnt.getField("PROD_DIS_CNT"));
            if(intDisCnt > 0){
                throw new BizRuntimeException("DMS00003");  //중복 된 데이터가 존재합니다.
            }

            // 입고예정등록 확인
            reqData.putFieldMap(rsInMst.getRecordMap(0));
            dsCnt = dEPRJdgQltyJdgMgmt.dSRJdgQJdgInqEqpInOrdListCnt(reqData, onlineCtx);
            intDisCnt = Integer.parseInt(dsCnt.getField("ORD_CNT"));
            if(intDisCnt > 0){
                throw new BizRuntimeException("DMS00003");  //중복 된 데이터가 존재합니다.
            }
            */
            // 입고관리번호 얻기.
            resData = dEPRJdgQltyJdgMgmt.dSInqDisInMgmtNo(reqData, onlineCtx);
            sInMgmtNo = resData.getField("IN_MGMT_NO").toString();
            
            if(rsProdList != null){
                String sProcFlag = null;
                String sInCd = "100"; // 입고구분
                String sInDtlCd = ""; // 입고상세구분
                String sDisSt = "01"; // 재고상태 : 01 가용, 02 비가용
                String sInSt = "03"; // 입고상태 : 01 미입고, 02 부분입고, 03 입고완료
                String sCurrDt = DateUtils.getCurrentDate(); // 현재일자
                String sInPlcId = ""; // 입고처

                sInCd = rsInMst.getRecordMap(0).get("IN_CL").toString();
                if("114".equals(sInCd)){
                    //sInCd = "100";
                    sInDtlCd = "114"; //매입입고

                    sPrchsMgmtNo = requestData.getField("PRCH_MGMT_NO") == null ? rsInMst.getRecordMap(0).get("PRCH_MGMT_NO").toString() : requestData.getField("PRCH_MGMT_NO");
                }else{
                    //sInCd = "100";
                    sInDtlCd = "101"; //구매입고
                }
                sInCd = "100";
                
                //입고처 
                sInPlcId = (String) rsInMst.getRecordMap(0).get("CNSL_DEALCO_CD");
                if(sInPlcId == null || "".equals(sInPlcId)){
                    sInPlcId = "11010"; // 본사창고..
                }

                // 2. 입고  처리 (입고상태를 입고완료로 처리함)
                // 2-1. 입고 tabel insert
                reqData = new DataSet();
                reqData.putFieldMap(rsInMst.getRecordMap(0));
                reqData.putField("IN_MGMT_NO", sInMgmtNo); //입고관리번호 셋팅           
                reqData.putField("IN_CL", sInDtlCd); //입고구분  
                reqData.putField("IN_CONF_DT", sCurrDt); //입고확정일자 
                reqData.putField("IN_ST", sInSt); //입고상태 
                reqData.putField("FS_REG_USER_ID", ca.getUserNo());
                reqData.putField("LS_CHG_USER_ID", ca.getUserNo());
                
                dEPRJdgQltyJdgMgmt.dIRjdgQltyJdgInRgst(reqData, onlineCtx);
                // **** 입고마스터 생성완료
                
                if ("114".equals(sInDtlCd)) {
                    // 매입입고
                    iCount = 1;
                } else {
                    //구매입고
                    iCount = rsProdList.getRecordCount();
                }
                
                // 2-2. 입고상세 tabel insert
                for (int i = 0; i < iCount; i++) {
                    IDataSet reqProdData = new DataSet();
                    reqProdData.putFieldMap(rsProdList.getRecordMap(i));
                    
                    reqData = new DataSet();
                    reqData.putFieldMap(rsInMst.getRecordMap(0));
                    reqData.putFieldMap(rsProdList.getRecordMap(i));
                    
                    // 입고순번 얻기
                    resData = dEPRJdgQltyJdgMgmt.dSInqDisInDtlSeq(reqData, onlineCtx);
                    sInSeq = resData.getField("IN_SEQ").toString();
                    
                    reqData.putField("IN_SEQ",sInSeq);  // 기타재고순번  셋팅.
                    reqData.putField("IN_MGMT_NO",sInMgmtNo);
                    reqData.putField("PRCH_MGMT_NO",sPrchsMgmtNo);
                    BigDecimal nQtyTemp = new BigDecimal(0);
                    nQtyTemp = new BigDecimal(reqProdData.getLongField("IN_QTY"));
                    reqData.putField("IN_QTY",nQtyTemp);  //입고확정수량    
                    reqData.putField("IN_CONF_DT",sCurrDt);  //입고확정일자   
                    reqData.putField("IN_CONF_YN","Y");  //입고확정여부
                    nQtyTemp = new BigDecimal(reqProdData.getLongField("IN_QTY"));
                    reqData.putField("IN_CONF_QTY",nQtyTemp);
                    nQtyTemp = new BigDecimal(reqProdData.getLongField("IN_AMT"));
                    reqData.putField("IN_AMT",nQtyTemp);  //입고금
                    reqData.putField("FS_REG_USER_ID", ca.getUserNo());
                    reqData.putField("LS_CHG_USER_ID", ca.getUserNo());
                    //입고상세 생성
                    dEPRJdgQltyJdgMgmt.dIRjdgQltyJdgInDtlRgst(reqData, onlineCtx);
                    // **** 입고상세 생성완료
                    
                    // 2-3. 입고구성품 tabel insert
                    // 매입일경우만 구성품을 생성시켜줌
                    if ("114".equals(sInDtlCd)) {
                        dEPRJdgQltyJdgMgmt.dIRjdgQltyJdgInCpntRgst(reqData, onlineCtx);
                    }
                    // 입고구성품 생성완료

                    // 3. 재고  처리
                    // 3-1. 재고 tabel insert
                    reqData = new DataSet();
                    reqData.putFieldMap(rsInMst.getRecordMap(0));
                    reqData.putFieldMap(rsProdList.getRecordMap(i));
                    reqData.putField("IN_MGMT_NO",sInMgmtNo);
                    nQtyTemp = new BigDecimal(0);
                    nQtyTemp = new BigDecimal(reqProdData.getLongField("IN_QTY"));
                    reqData.putField("IN_QTY",nQtyTemp);  //입고확정수량    
                    reqData.putField("IN_CONF_DT",sCurrDt);  //입고확정일자   
                    reqData.putField("IN_CONF_YN","Y");  //입고확정여부
                    nQtyTemp = new BigDecimal(reqProdData.getLongField("IN_QTY"));
                    reqData.putField("IN_CONF_QTY",nQtyTemp);
                    nQtyTemp = new BigDecimal(reqProdData.getLongField("IN_AMT"));
                    reqData.putField("IN_AMT",nQtyTemp);  //입고금
                    reqData.putField("FS_REG_USER_ID", ca.getUserNo());
                    reqData.putField("LS_CHG_USER_ID", ca.getUserNo());
                    
                    reqData.putField("HLD_DEALCO_ID", sInPlcId); //입고처 -> 보유처  
                    reqData.putField("INVE_ST", sDisSt); //재고상태  
                    //reqData.putField("EQP_ST", mProdData.get("eqp_st")); //단말기상태
                    reqData.putField("LAST_IN_OUT_DT", sCurrDt); //최종입출고일자
                    reqData.putField("IN_OUT_CL", sInCd); //최종입출고구분 
                    reqData.putField("IN_OUT_DTL_CL", sInDtlCd); //최종일출고상세구분    
                    reqData.putField("FST_IN_CONF_DT", sCurrDt); //최초 입고확정일자
                    //reqData.putField("FST_IN_DEALCO_ID", rsInMst.getRecordMap(0).get("")); //최초입고처 
                    reqData.putField("FST_IN_DEALCO_ID","");
                    reqData.putField("PRCHCO_ID", rsInMst.getRecordMap(0).get("PRCHCO_ID")); //최초매입처  
                    reqData.putField("LAST_MOV_IN_DT", sCurrDt); //마지막이동입고일자
                    reqData.putField("USED_CL", "1"); //중고구분 
                    reqData.putField("INVE_AMT", rsProdList.getRecordMap(i).get("IN_AMT")); //재고금액
                    
                    nQtyTemp = new BigDecimal(reqProdData.getLongField("IN_QTY"));
                    reqData.putField("QTY",nQtyTemp);  //입고수량 ->수량
                    reqData.putField("UPRC", rsProdList.getRecordMap(i).get("IN_AMT")); //입고단가 ->금액
                    reqData.putField("AMT", rsProdList.getRecordMap(i).get("IN_AMT")); //입고단가 ->금액
                    
                    if("1".equals(rsProdList.getRecordMap(i).get("PROD_CL").toString())){
                        // 단말기 재고 추가(매입 단말기)
                        dEPRJdgQltyJdgMgmt.dIRJdgQltyJdgEqpDisRgst(reqData, onlineCtx);
                        // 단말기 재고 금액 추가
                        reqData.putField("OCCR_CL", "01"); //발생구분
                        reqData.putField("OCCR_DT", sCurrDt); //발생일자
                        dEPRJdgQltyJdgMgmt.dIRjdgQltyJdgDisAmtRgst(reqData, onlineCtx);
                        
                        // 단말기 재고 입출고 이력
                        reqData.putField("IN_MGMT_NO", sInMgmtNo); //입고관리번호
                        reqData.putField("IN_SEQ", sInSeq); //입고관리순번
                        reqData.putField("OUT_MGMT_NO", ""); //출고관리번호
                        reqData.putField("OUT_SEQ", ""); //출고관리순번
                        reqData.putField("IN_OUT_DT", sCurrDt); //입출고일자
                        reqData.putField("IN_OUT_CL", sInCd); //입출고구분
                        reqData.putField("IN_OUT_DTL_CL", sInDtlCd); //입출고상세구분
                        reqData.putField("PRCH_DEALCO_ID", rsInMst.getRecordMap(0).get("PRCHCO_ID")); // 매입처
                        reqData.putField("OUT_DEALCO_ID", rsInMst.getRecordMap(0).get("PRCHCO_ID")); //출고처
                        reqData.putField("IN_DEALCO_ID", rsInMst.getRecordMap(0).get("IN_DEALCO_ID")); //입고처
                        
                        dEPRJdgQltyJdgMgmt.dIRJdgQltyJdgDisInoutHstRgst(reqData, onlineCtx);
                        
                        // 단말기 재고 이력
                        //시퀀스추가필요
                        reqData.putField("INOUT_MGMT_NO", sInMgmtNo); //입출고관리번호
                        
                        dEPRJdgQltyJdgMgmt.dIRJdgQltyJdgDisHstRgst(reqData, onlineCtx);

                    }
                }
            }
        } catch ( BizRuntimeException e ) {
            throw e;
        }    
        return responseData;
    }
  
}
