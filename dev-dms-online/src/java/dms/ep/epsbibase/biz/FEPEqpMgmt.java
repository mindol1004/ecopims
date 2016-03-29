package dms.ep.epsbibase.biz;

import java.util.HashMap;
import java.util.Map;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.exception.BizRuntimeException;
import nexcore.framework.core.log.LogManager;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;


/**
 * <ul>
 * <li>업무 그룹명 : dms/에코폰</li>
 * <li>단위업무명: [FU]단말기상품관리</li>
 * <li>설  명 : <pre>단말기상품관리 FU</pre></li>
 * <li>작성일 : 2015-09-22 10:11:56</li>
 * <li>작성자 : 이민재 (mindol1004)</li>
 * </ul>
 *
 * @author 이민재 (mindol1004)
 */
public class FEPEqpMgmt extends fwk.base.FunctionUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public FEPEqpMgmt(){
		super();
	}

    /**
	 * <pre>단말기모델엑셀업로드검증</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-09-22 10:11:56
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_IN_EXCEL_LIST
	 *		- field : EQP_MDL_CD [모델코드]
	 *		- field : PROD_SER_NO [상품일련번호]
	 *		- field : SELL_AMT [판매가]
	 *		- field : XRATE_APPLY_AMT [환율금액]
	 *		- field : ERR_DESC [오류항목]
	 *		- field : EQP_COLOR_CD [색상코드]
	 *		- field : EQP_ST [단말기상태]
	 *		- field : INVE_ST [재고상태]
	 *		- field : HLD_DEALCO_ID [보유거래처ID]
	 *		- field : HLD_DEALCO_NM [보유거래처명]
	 *		- field : EQP_SER_NO [일련번호]
	 *		- field : UPDATE_COUNT [UPDATE COUNT]
	 *		- field : CNSL_MGMT_NO [상담관리번호]
	 *		- field : PROD_CL [상품구분]
	 *		- field : MFACT_CD [제조사코드]
	 *		- field : EQP_MDL_NM [모델명]
	 *		- field : LAUNC_DT [출시일]
	 *		- field : SELL_CHG_HST_CL [구분]
	 *		- field : SALE_QTY [판매수량]
	 *		- field : FIX_PRC_YN [고정가여부]
	 *		- field : SELL_PCOST [판매원가]
	 *		- field : SELL_UPRC [판매단가]
	 *		- field : SELL_MRGN [판매마진]
	 *		- field : SELL_CMMS [판매수수료]
	 *		- field : SURTAX [부가세]
	 *		- field : PRCH_MGMT_NO [매입관리번호]
	 *		- field : PRCH_AMT [매입가]
	 *		- field : IMEI [IMEI]
	 *		- field : INVE_AMT [재고금액]
	 *	- field : SELL_TYP [판매유형]
	 *	- field : HLD_DEALCO_ID [판매처]
	 *	- field : SELL_CL_CD [판매구분]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_EXCEL_LIST
	 *		- field : EQP_MDL_CD [모델코드]
	 *		- field : PROD_SER_NO [상품일련번호]
	 *		- field : SELL_AMT [판매가]
	 *		- field : XRATE_APPLY_AMT [환율금액]
	 *		- field : ERR_DESC [오류항목]
	 *		- field : EQP_COLOR_CD [색상코드]
	 *		- field : EQP_ST [단말기상태]
	 *		- field : INVE_ST [재고상태]
	 *		- field : HLD_DEALCO_ID [보유거래처ID]
	 *		- field : HLD_DEALCO_NM [보유거래처명]
	 *		- field : EQP_SER_NO [일련번호]
	 *		- field : UPDATE_COUNT [UPDATE COUNT]
	 *		- field : CNSL_MGMT_NO [상담관리번호]
	 *		- field : PROD_CL [상품구분]
	 *		- field : MFACT_CD [제조사코드]
	 *		- field : EQP_MDL_NM [모델명]
	 *		- field : LAUNC_DT [출시일]
	 *		- field : SELL_CHG_HST_CL [구분]
	 *		- field : SALE_QTY [판매수량]
	 *		- field : FIX_PRC_YN [고정가여부]
	 *		- field : SELL_PCOST [판매원가]
	 *		- field : SELL_UPRC [판매단가]
	 *		- field : SELL_MRGN [판매마진]
	 *		- field : SELL_CMMS [판매수수료]
	 *		- field : SURTAX [부가세]
	 *		- field : PRCH_MGMT_NO [매입관리번호]
	 *		- field : PRCH_AMT [매입가]
	 *		- field : IMEI [IMEI]
	 *		- field : INVE_AMT [재고금액]
	 * </pre>
	 */
	public IDataSet fInqEqpMgmtExcelErrLst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet reqDs = (IDataSet) requestData.clone();//원거래의 DataSet의 훼손을 막기 위한 Clone
//        Log logger = LogManager.getFwkLog();     
        try {

            // 1. DU lookup
            DEPEqpMgmt dEPEqpMgmt = (DEPEqpMgmt) lookupDataUnit(DEPEqpMgmt.class);
            
            IRecordSet excelRs = reqDs.getRecordSet("RS_IN_EXCEL_LIST");

            if (excelRs != null) {
                
                /*********************************************
                 * 모델조회.
                 *********************************************/     
                /*
                Map<Object, Object> eqpMap = new HashMap<Object, Object>();
                IDataSet eqpDs = new DataSet();
                IRecordSet eqpRs = dEPEqpMgmt.dSEqpMgmtLst(reqDs, onlineCtx).getRecordSet("RS_EQP_LIST");
                if (eqpRs != null) {
                    for(int i=0 ; i < eqpRs.getRecordCount(); i++){
                        eqpDs.putFieldMap(eqpRs.getRecordMap(i));
                        eqpMap.put(eqpDs.getField("EQP_MDL_CD"), eqpDs.getFieldMap());
                    }
                }else{
                    throw new BizRuntimeException("시스템에 등록된 상품 정보가 없습니다. 기준정보의 상품관리를 확인하세요.");
                }
                */
                
                /*********************************************
                 * 색상조회.
                 *********************************************/  
                /*
                Map<Object, Object> colorMap = new HashMap<Object, Object>();
                IDataSet colorDs = new DataSet();
                IRecordSet colorRs = dEPEqpMgmt.dSEqpMgmtColorLst(reqDs, onlineCtx).getRecordSet("RS_COLOR_LIST");
                if (colorRs != null) {
                    for(int i=0 ; i < colorRs.getRecordCount(); i++){
                        colorDs.putFieldMap(colorRs.getRecordMap(i));
                        colorMap.put(colorDs.getField("EQP_COLOR_CD"), colorDs.getFieldMap());
                    }
                }else{
                    throw new BizRuntimeException("시스템에 등록된 색상 정보가 없습니다. 기준정보의 상품관리를 확인하세요. ");
                }
                */
                
                /*********************************************
                 * 재고정보 조회
                 *********************************************/
                IDataSet disCnt = dEPEqpMgmt.dSEqpMgmtDisTotCnt(reqDs, onlineCtx);
                int totalCnt = Integer.parseInt(disCnt.getField("TOTAL_CNT"));
                int firstRowIndex = 1;
                int lastRowIndex = 5000;
                
                int end = (int) Math.ceil((double)totalCnt / lastRowIndex);
                
                IDataSet disDs = new DataSet();
                Map<String, Object> disMap = new HashMap<String, Object>();
                for (int i = 0; i < end; i++){
                    reqDs.putField("firstRowIndex", firstRowIndex);
                    reqDs.putField("lastRowIndex", lastRowIndex);
                    IRecordSet disRs = dEPEqpMgmt.dSEqpMgmtDisLst(reqDs, onlineCtx).getRecordSet("RS_EQP_DIS_LIST");
                    for(int j=0 ; j < disRs.getRecordCount(); j++){
                        disDs.putFieldMap(disRs.getRecordMap(j));
                        disMap.put(disDs.getField("EQP_MDL_CD")+disDs.getField("PROD_SER_NO"), disDs.getFieldMap());
                    }
                    
                    firstRowIndex = lastRowIndex + 1;
                    if(i == end-2) lastRowIndex = totalCnt;
                    else lastRowIndex += 5000;
                }
                
                IRecord excelRd = null;
                for (int i = 0; i < excelRs.getRecordCount(); i++) {
                    excelRd = excelRs.getRecord(i);
                    
                    // 모델코드 미존재 시
                    /*
                    if(!eqpMap.containsKey(excelRd.get("EQP_MDL_CD"))){
                        excelRd.set("ERR_DESC", "존재하지 않는 상품입니다.");
                        continue;
                    }
                    */
                    
                    // 색상코드 미존재 시
                    /*
                    if(!colorMap.containsKey(excelRd.get("EQP_COLOR_CD"))){
                        excelRd.set("ERR_DESC", "존재하지 않는 색상입니다.");
                        continue;
                    }
                    */
                    
                    int prodChk = 0;
                    for (int k = 0; k < excelRs.getRecordCount(); k++) {
                        if(StringUtils.equals(excelRd.get("PROD_SER_NO").trim(), excelRs.get(k, "PROD_SER_NO").trim())){
                            prodChk++;
                        }
                    }
                    
                    if(prodChk > 1){
                        excelRd.set("ERR_DESC", "중복된 상품일련번호가 있습니다.");
                        continue;
                    }
                    
                    if (disMap.containsKey(excelRd.get("EQP_MDL_CD")+excelRd.get("PROD_SER_NO"))) {
                        
                        @SuppressWarnings("unchecked")
                        Map<String, Object> value = (Map<String, Object>) disMap.get(excelRd.get("EQP_MDL_CD")+excelRd.get("PROD_SER_NO"));
//                        logger.debug("\n\n\n\n\n>>>>>>>>>>   value : "+value.get("CNSL_MGMT_NO")+"/FIX_INVE_SLIP_NO:"+value.get("FIX_INVE_SLIP_NO"));
                        
                        		
                        if("".equals(value.get("FIX_INVE_SLIP_NO"))){
                        	excelRd.set("ERR_DESC", "확정전표가 없는 데이터입니다.");
                            continue;
                        }else if (!"N".equals(value.get("TRMS_CL")) && "N".equals(value.get("TRMS_YN"))) {
                            excelRd.set("ERR_DESC", "I/F 중인 데이터 입니다.");
                            continue;
                        }
                        
                        /*********************************************
                         * 판매가 check 및 포멧팅.
                         * 판매가 입력하지 않은경우 계산하여 가져온다
                         *********************************************/
                        double sellAmt = 0;
                        double sellPcost = 0;
                        double surtax = 0;
                        if("04".equals(reqDs.getField("SELL_TYP"))){   // 위탁판매
                            sellAmt =  (Double) value.get("CONF_SELL_AMT");
                        }else{
                            if(excelRd.get("SELL_AMT") == null){
                                sellAmt = Double.parseDouble(value.get("SELL_AMT").toString());
                                surtax =  Double.parseDouble(value.get("SURTAX").toString());
                            }else{
                                
                                try {
                                    sellAmt = Double.parseDouble(excelRd.get("SELL_AMT").replace(",", "")); //판매가
                                } catch (NumberFormatException e) {
                                    excelRd.set("ERR_DESC", "잘못된 판매가 형식입니다.");
                                    continue;
                                }
                                
                                if(StringUtils.equals(reqDs.getField("SELL_CL_CD") ,"10") || StringUtils.equals(reqDs.getField("SELL_CL_CD") ,"19")){
                                    sellPcost = sellAmt/1.1;
                                    surtax = sellPcost * 0.1;
                                    //surtax =  (int)Math.round((sellAmt/1.1)/10);
                                    //sellPcost = sellAmt - surtax;
                                }else{
                                    sellPcost = sellAmt;
                                    surtax = 0;
                                }
                            }
                        }
                        
                        excelRd.set("PROD_CL", value.get("PROD_CL")); //상품 구분 코드
                        excelRd.set("EQP_MDL_CD", value.get("EQP_MDL_CD")); //상품 모델 코드
                        excelRd.set("EQP_MDL_NM", value.get("EQP_MDL_NM")); //상품명
                        excelRd.set("EQP_COLOR_CD", value.get("EQP_COLOR_CD")); //색상코드
                        excelRd.set("EQP_SER_NO", value.get("EQP_SER_NO")); //일련번호
                        excelRd.set("PROD_SER_NO", value.get("PROD_SER_NO")); //상품일련번호
                        excelRd.set("MFACT_CD", value.get("MFACT_CD")); //제조사ID
                        excelRd.set("HLD_DEALCO_ID", value.get("HLD_DEALCO_ID")); //보유 거래처ID
                        excelRd.set("HLD_DEALCO_NM", value.get("HLD_DEALCO_NM")); //보유 거래처
                        excelRd.set("SALE_QTY", value.get("SALE_QTY")); //판매수량
                        excelRd.set("CNSL_MGMT_NO", value.get("CNSL_MGMT_NO")); //상담 관리 번호
                        excelRd.set("INVE_AMT", value.get("INVE_AMT")); //재고금액
                        excelRd.set("INVE_ST", value.get("INVE_ST")); //재고상태
                        excelRd.set("EQP_ST", value.get("EQP_ST")); //단말기상태
                        excelRd.set("LAUNC_DT", value.get("LAUNC_DT")); //출시일
                        excelRd.set("SELL_CHG_HST_CL", value.get("SELL_CHG_HST_CL")); //판매 변경 이력 구분
                        excelRd.set("FIX_PRC_YN", value.get("FIX_PRC_YN")); //고정가 여부
                        excelRd.set("SELL_UPRC", sellPcost); //공급가액
                        excelRd.set("SELL_MRGN", value.get("SELL_MRGN")); //판매 마진
                        excelRd.set("SELL_CMMS", value.get("SELL_CMMS")); //판매 수수료
                        excelRd.set("SELL_AMT", sellAmt); //판매가
                        excelRd.set("SELL_PCOST", value.get("SELL_UPRC")); //매입원가
                        excelRd.set("SURTAX", surtax); //부가세
                        excelRd.set("PRCH_MGMT_NO", value.get("PRCH_MGMT_NO")); //매입번호
                        excelRd.set("PRCH_AMT", value.get("PRCH_AMT")); //매입가
                        excelRd.set("IMEI", value.get("IMEI")); //IMEI
                        if(excelRd.get("XRATE_APPLY_AMT") != null){
                            excelRd.set("XRATE_APPLY_AMT", Double.parseDouble(excelRd.get("XRATE_APPLY_AMT").replace(",", ""))); //환율금액
                        }
                        
                    }else{
                        excelRd.set("ERR_DESC", "재고에 입력된 일련번호로 등록된 상품이 없습니다.");
                        continue;
                    }
                }
               
            } else {
                throw new BizRuntimeException("DMS00004"); //데이터가 존재하지 않습니다.
            }
            
            responseData.putRecordSet("RS_EXCEL_LIST", excelRs);
            
        } catch ( BizRuntimeException e ) {
            throw e;
        }
    
        return responseData;
    }

    /**
	 * <pre>단말기모델취소엑셀업로드검증</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-09-22 10:11:56
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_IN_EXCEL_LIST
	 *		- field : EQP_MDL_CD [모델코드]
	 *		- field : PROD_SER_NO [상품일련번호]
	 *		- field : SELL_AMT [판매가]
	 *		- field : EQP_SER_NO [일련번호]
	 *		- field : EQP_MDL_NM [모델명]
	 *		- field : SALEPLC_NM [매출처명]
	 *		- field : SELL_DEALCO_CD_NM [보유처명]
	 *		- field : CNSL_MGMT_NO [상담관리번호]
	 *		- field : ERR_DESC [오류사항]
	 *		- field : EQP_COLOR_CD [모델색상]
	 *		- field : SALEPLC [매출처코드]
	 *		- field : SELL_DEALCO_CD [보유처코드]
	 *		- field : GNRL_SELL_NO [일반판매번호]
	 *		- field : GNRL_SELL_CHG_SEQ [일반판매변경순번]
	 *		- field : SELL_DTM [판매일시]
	 *		- field : SELL_SEQ [변경순번]
	 *		- field : SELL_PCOST [판매원가]
	 *		- field : SELL_MRGN [판매마진]
	 *		- field : SELL_CMMS [판매수수료]
	 *		- field : SURTAX [부가세]
	 *		- field : SELL_PRC_CASH_RECV_AMT [현금금액]
	 *		- field : SELL_PRC_CCARD_RECV_AMT_1 [카드금액]
	 *		- field : PROD_OUT_MGMT_NO [출고관리번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_EXCEL_LIST
	 *		- field : EQP_MDL_CD [모델코드]
	 *		- field : PROD_SER_NO [상품일련번호]
	 *		- field : SELL_AMT [판매가]
	 *		- field : EQP_SER_NO [일련번호]
	 *		- field : EQP_MDL_NM [모델명]
	 *		- field : SALEPLC_NM [매출처명]
	 *		- field : SELL_DEALCO_CD_NM [보유처명]
	 *		- field : CNSL_MGMT_NO [상담관리번호]
	 *		- field : ERR_DESC [오류사항]
	 *		- field : EQP_COLOR_CD [모델색상]
	 *		- field : SALEPLC [매출처코드]
	 *		- field : SELL_DEALCO_CD [보유처코드]
	 *		- field : GNRL_SELL_NO [일반판매번호]
	 *		- field : GNRL_SELL_CHG_SEQ [일반판매변경순번]
	 *		- field : SELL_DTM [판매일시]
	 *		- field : SELL_SEQ [변경순번]
	 *		- field : SELL_PCOST [판매원가]
	 *		- field : SELL_MRGN [판매마진]
	 *		- field : SELL_CMMS [판매수수료]
	 *		- field : SURTAX [부가세]
	 *		- field : SELL_PRC_CASH_RECV_AMT [현금금액]
	 *		- field : SELL_PRC_CCARD_RECV_AMT_1 [카드금액]
	 *		- field : PROD_OUT_MGMT_NO [출고관리번호]
	 * </pre>
	 */
	public IDataSet fInqEqpMgmtCnclExcelErrLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        IDataSet reqDs = (IDataSet) requestData.clone();//원거래의 DataSet의 훼손을 막기 위한 Clone
            
        try {

            // 1. DU lookup
            DEPEqpMgmt dEPEqpMgmt = (DEPEqpMgmt) lookupDataUnit(DEPEqpMgmt.class);
            
            IRecordSet excelRs = reqDs.getRecordSet("RS_IN_EXCEL_LIST");

            if (excelRs != null) {
                
                IRecord excelRd = null;
                for (int i = 0; i < excelRs.getRecordCount(); i++) {
                    excelRd = excelRs.getRecord(i);
                    
                    if(excelRd.get("PROD_SER_NO") == null){
                        excelRd.set("ERR_DESC", "상품일련번호가 없습니다.");
                        continue;
                    }
                    
                    /*********************************************
                     * 재고판매상품조회
                     *********************************************/
                    reqDs.putField("EQP_MDL_CD", excelRd.get("EQP_MDL_CD"));
                    reqDs.putField("PROD_SER_NO", excelRd.get("PROD_SER_NO"));
                    IDataSet disDs = dEPEqpMgmt.dSEqpMgmtSaleLst(reqDs, onlineCtx);
                    if (disDs.getFieldCount() == 0) {
                        excelRd.set("ERR_DESC", "판매 상품이 아닙니다.");
                        continue;
                    }
                    
                    if (StringUtils.isNotEmpty(disDs.getField("XCL_SLIP_NO")) && !"05".equals(disDs.getField("XCL_SLIP_ST"))){
                        excelRd.set("ERR_DESC", "전표 발행건으로 취소 불가능합니다.");
                        continue;
                    }
                    
                    excelRd.set("EQP_MDL_CD", disDs.getField("EQP_MDL_CD")); //상품 모델 코드
                    excelRd.set("EQP_MDL_NM", disDs.getField("EQP_MDL_NM")); //상품명
                    excelRd.set("EQP_COLOR_CD", disDs.getField("EQP_COLOR_CD")); //색상코드
                    excelRd.set("EQP_SER_NO", disDs.getField("EQP_SER_NO")); //일련번호
                    excelRd.set("PROD_SER_NO", disDs.getField("PROD_SER_NO")); //상품일련번호
                    excelRd.set("CNSL_MGMT_NO", disDs.getField("CNSL_MGMT_NO")); //상담관리번호
                    excelRd.set("SELL_DEALCO_CD", disDs.getField("SELL_DEALCO_CD")); //보유 거래처ID
                    excelRd.set("SELL_DEALCO_CD_NM", disDs.getField("SELL_DEALCO_CD_NM")); //보유 거래처
                    excelRd.set("GNRL_SELL_NO", disDs.getField("GNRL_SELL_NO")); //일반판매번호
                    excelRd.set("GNRL_SELL_CHG_SEQ", disDs.getField("GNRL_SELL_CHG_SEQ")); //일반판매변경순번
                    excelRd.set("SELL_DTM", disDs.getField("SELL_DTM")); //판매일시
                    excelRd.set("SALEPLC", disDs.getField("SALEPLC")); //매출처
                    excelRd.set("SALEPLC_NM", disDs.getField("SALEPLC_NM")); //매출처명
                    excelRd.set("SELL_SEQ", disDs.getField("SELL_SEQ")); //판매순번
                    excelRd.set("SELL_AMT", disDs.getField("SELL_AMT")); //판매가격
                    excelRd.set("SELL_PCOST", disDs.getField("SELL_PCOST")); //판매원가
                    excelRd.set("SELL_MRGN", disDs.getField("SELL_MRGN")); //판매마진
                    excelRd.set("SELL_CMMS", disDs.getField("SELL_CMMS")); //판매수수료
                    excelRd.set("SURTAX", disDs.getField("SURTAX")); //부가세
                    excelRd.set("SELL_PRC_CASH_RECV_AMT", disDs.getField("SELL_PRC_CASH_RECV_AMT")); //판매 가격 현금 수납 금액
                    excelRd.set("SELL_PRC_CCARD_RECV_AMT_1", disDs.getField("SELL_PRC_CCARD_RECV_AMT_1")); //판매 가격 신용카드 수납 금액 1
                    excelRd.set("PROD_OUT_MGMT_NO", disDs.getField("PROD_OUT_MGMT_NO")); //출고관리번호
                    
                }
               
            } else {
                throw new BizRuntimeException("DMS00004"); //데이터가 존재하지 않습니다.
            }
            
            responseData.putRecordSet("RS_EXCEL_LIST", excelRs);
            
        } catch ( BizRuntimeException e ) {
            throw e;
        }
    
        return responseData;
    }
  
}
