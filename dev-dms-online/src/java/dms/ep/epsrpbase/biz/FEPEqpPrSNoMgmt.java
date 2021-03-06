package dms.ep.epsrpbase.biz;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.exception.BizRuntimeException;
import fwk.utils.PagenateUtils;


/**
 * <ul>
 * <li>업무 그룹명 : dms/에코폰</li>
 * <li>단위업무명: [FU]단말기매입매출전표현황관리</li>
 * <li>설  명 : <pre>단말기매입매출전표현황관리</pre></li>
 * <li>작성일 : 2015-12-09 11:03:41</li>
 * <li>작성자 : 박민정 (dangnagu2)</li>
 * </ul>
 *
 * @author 박민정 (dangnagu2)
 */
public class FEPEqpPrSNoMgmt extends fwk.base.FunctionUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public FEPEqpPrSNoMgmt(){
		super();
	}

    /**
	 * <pre>단말기매입매출전표현황조회</pre>
	 *
	 * @author 박민정 (dangnagu2)
	 * @since 2015-12-09 11:03:41
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : DT_GB [기간구분]
	 *	- field : FR_DT [시작일]
	 *	- field : TO_DT [종료일]
	 *	- field : SKN_GB [현장/일반구분]
	 *	- field : RWD_GB [보상유형]
	 *	- field : NO_GB [전표발행상태]
	 *	- field : CNSL_MGMT_NO [접수관리번호]
	 *	- field : EQP_SER_NO [일련번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_EQP_PRSNO_LIST
	 *		- field : CNSL_DT [접수일자]
	 *		- field : CLCT_DT [회수일자(반영일자)]
	 *		- field : CNSL_MGMT_NO [상담관리번호]
	 *		- field : EQP_MDL_CD [단말기 코드]
	 *		- field : EQP_SER_NO [단말기 일련번호]
	 *		- field : EQP_MDL_NM [모델명]
	 *		- field : SKN_CL [현장/감정구분]
	 *		- field : PROGR_ST [진행상태]
	 *		- field : RWD_CD [보상유형]
	 *		- field : NOT_XCL_AMT [미착AP매입금액]
	 *		- field : NOT_DEDC_TYP_CD [미착공제유형코드]
	 *		- field : NOT_INVE_SLIP_NO [미착AP번호]
	 *		- field : NOT_INVE_SLIP_DT [미착AP기산일]
	 *		- field : NOT_INVE_TYP_CD [미착AP처리구분]
	 *		- field : FIX_XCL_AMT [확정AP매입금액]
	 *		- field : FIX_DEDC_TYP_CD [확정공제유형코드]
	 *		- field : FIX_INVE_SLIP_NO [확정AP번호]
	 *		- field : FIX_INVE_SLIP_DT [확정AP기산일]
	 *		- field : FIX_INVE_TYP_CD [확정AP처리구분]
	 *		- field : NOT_RVED_XCL_AMT [미착반제AP매입금액]
	 *		- field : NOT_RVED_DEDC_TYP_CD [미착반제공제유형코드]
	 *		- field : NOT_RVED_INVE_SLIP_NO [미착반제AP번호]
	 *		- field : NOT_RVED_INVE_SLIP_DT [미착반제AP기산일]
	 *		- field : NOT_RVED_INVE_TYP_CD [미착반제AP처리구분]
	 *		- field : FIX_RVED_XCL_AMT [확정반제AP매입금액]
	 *		- field : FIX_RVED_DEDC_TYP_CD [확정반제공제유형코드]
	 *		- field : FIX_RVED_INVE_SLIP_NO [확정반제AP번호]
	 *		- field : FIX_RVED_INVE_SLIP_DT [확정반제AP기산일]
	 *		- field : FIX_RVED_INVE_TYP_CD [확정반제AP처리구분]
	 *		- field : PAY_SLIP_NO [전표번호]
	 *		- field : PAY_SLIP_DT [전표일자]
	 *		- field : PAY_XCL_AMT [금액]
	 *		- field : PAY_CNCL_SLIP_NO [취소전표번호]
	 *		- field : PAY_CNCL_SLIP_DT [전표번호]
	 *		- field : PAY_RMT_DT [송금일자]
	 *		- field : PAY_TS [공제 차수]
	 *		- field : AR_SALE_AMT [AR 매출가]
	 *		- field : AR_XCL_SLIP_NO [AR 매출번호]
	 *		- field : AR_XCL_SLIP_DT [AR 기산일]
	 * </pre>
	 */
	public IDataSet fInqEqpPrSNoList(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        IDataSet dsCnt = new DataSet();
        IDataSet reqDs = (IDataSet) requestData.clone();//원거래의 DataSet의 훼손을 막기 위한 Clone
        IRecordSet rsPagingList = null;
        int intTotalCnt = 0;

        try {
            // 1. DU lookup
            DEPEqpPrSNoMgmt dEPEqpPrSNoMgmt = (DEPEqpPrSNoMgmt)lookupDataUnit(DEPEqpPrSNoMgmt.class);
            
            // 2. TOTAL COUNT DM호출
            dsCnt = dEPEqpPrSNoMgmt.dSEqpPrSNoListToCnt(reqDs, onlineCtx);
            intTotalCnt = Integer.parseInt(dsCnt.getField("TOTAL_CNT"));
            PagenateUtils.setPagenatedParamsToDataSet(reqDs);
            // 3. LISTDM호출
            responseData = dEPEqpPrSNoMgmt.dSEqpPrSNoListPaging(reqDs, onlineCtx);

            rsPagingList = responseData.getRecordSet("RS_EQP_PRSNO_LIST");
            PagenateUtils.setPagenatedParamToRecordSet(rsPagingList, reqDs, intTotalCnt);
            
        } catch ( BizRuntimeException e ) {
            throw e;
        }
    
        return responseData;
    }
  
}
