package dms.ep.epbbibase.biz;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.exception.BizRuntimeException;


/**
 * <ul>
 * <li>업무 그룹명 : dms/에코폰</li>
 * <li>단위업무명: [PU]단말기상품관리</li>
 * <li>설  명 : <pre>단말기상품관리 PU</pre></li>
 * <li>작성일 : 2015-09-22 10:11:11</li>
 * <li>작성자 : 이민재 (mindol1004)</li>
 * </ul>
 *
 * @author 이민재 (mindol1004)
 */
public class PEPEqpMgmt extends fwk.base.ProcessUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public PEPEqpMgmt(){
		super();
	}

    /**
	 * <pre>단말기모델엑셀업로드검증</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-09-22 10:11:11
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
	public IDataSet pInqEqpMgmtExcelErrLst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        try {
            // 1. FM 호출
            responseData = callSharedBizComponentByDirect("ep.EPSBIBase", "fInqEqpMgmtExcelErrLst", requestData, onlineCtx);
            
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }
    
        return responseData;
    }

    /**
	 * <pre>단말기모델취소엑셀업로드검증</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-09-22 10:11:11
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
	public IDataSet pInqEqpMgmtCnclExcelErrLst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        try {
            // 1. FM 호출
            responseData = callSharedBizComponentByDirect("ep.EPSBIBase", "fInqEqpMgmtCnclExcelErrLst", requestData, onlineCtx);
            
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }
    
        return responseData;
    }
  
}
