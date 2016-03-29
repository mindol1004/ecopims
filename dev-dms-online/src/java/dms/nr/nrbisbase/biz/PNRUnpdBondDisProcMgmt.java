package dms.nr.nrbisbase.biz;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.exception.BizRuntimeException;


/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [PU]미납채권 조회 및 제각처리</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-11-11 09:34:32</li>
 * <li>작성자 : 김혁범 (kezmain1)</li>
 * </ul>
 *
 * @author 김혁범 (kezmain1)
 */
public class PNRUnpdBondDisProcMgmt extends fwk.base.ProcessUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public PNRUnpdBondDisProcMgmt(){
		super();
	}

	/**
	 *
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-11-11 09:34:32
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : INV_PROC_DATE [미납기간]
	 *	- field : SVC_MGMT_NO [서비스관리번호]
	 *	- field : FR_INV_PROC_STA_DT [미납시작일]
	 *	- field : FR_INV_PROC_END_DT [미납종료일]
	 *	- field : STAND_DT [기준일자]
	 *	- field : ITM_CD [매출항목코드]
	 *	- field : SLIP_ST_CD [전표상태]
	 *	- field : SLIP_NO [전표번호]
	 *	- field : SLIP_STA_DT [전표처리일(시작일)]
	 *	- field : SLIP_END_DT [전표처리일(종료일)]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_UNPD_BOND_DIS_PRO_LIST
	 *		- field : SVC_MGMT_NO [서비스관리번호]
	 *		- field : FR_INV_PROC_DT [최초미납시작일]
	 *		- field : INV_PROC_DATE [미납기간]
	 *		- field : UNPAID [미납금액]
	 *		- field : INVE_ST_CD [제각상태]
	 *	- record : RS_TOT_SUM
	 *		- field : TOTAL_SUM [미납금액합계]
	 * </pre>
	 */
	public IDataSet pInqUnpdBondDisProcLst(IDataSet requestData, IOnlineContext onlineCtx) {
		 IDataSet responseData = new DataSet();
			
		 	try {
		        // 1. FM 호출
		        responseData = callSharedBizComponentByDirect("nr.NRSISBase", "fInqUnpdBondDisProcLst", requestData, onlineCtx);
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
	 * @author 장미진 (kuramotojin)
	 * @since 2015-11-11 09:34:32
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_SLIP_LIST
	 *		- field : SVC_MGMT_NO [서비스관리번호]
	 *		- field : FR_INV_PROC_DT [최초미납일]
	 *		- field : INV_PROC_DATE [매납개월수]
	 *		- field : UNPAID [미납금액]
	 *		- field : INVE_ST_CD [재고상태코드]
	 *		- field : SLIP_ST_CD [전표상태코드]
	 *		- field : ITM_CD [매출항목구분]
	 *		- field : INV_DT [청구일자]
	 *		- field : SLIP_NO [전표번호]
	 *		- field : CNTRT_NO [계약번호]
	 *		- field : DMG_DEPR_AMT [대손상각비]
	 *		- field : DMG_ALLWN_AMT [대손충당금]
	 *		- field : CUST_TYP_NM [고객구분]
	 *		- field : BIZ_REG_NO [사업자번호]
	 *		- field : OBJ_YM [필드1]
	 *	- field : TOTAL_SUM [미납금액합계]
	 *	- field : BD_AMT [대손충당금]
	 *	- field : CASE_WHEN [분기용]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet pUnpdBondDisProcSlipCall(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        try {
            if("01".equals(requestData.getField("CASE_WHEN"))){//전표발행
                // 1. FM 호출
                responseData = callSharedBizComponentByDirect("nr.NRSISBase", "fUnpdBondDisProcSlipCreate", requestData, onlineCtx);
            }else if("02".equals(requestData.getField("CASE_WHEN"))){ //전표삭제
                // 1. FM 호출
                responseData = callSharedBizComponentByDirect("nr.NRSISBase", "fUnpdBondDisProcSlipCancle", requestData, onlineCtx);
            }
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }
    
        return responseData;
    }
  
}