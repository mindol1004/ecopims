package dms.pr.prsimbase.biz;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.exception.BizRuntimeException;
import fwk.common.CommonArea;
import fwk.utils.PagenateUtils;


/**
 * <ul>
 * <li>업무 그룹명 : dms/임대R</li>
 * <li>단위업무명: [FU]단말기매각관리</li>
 * <li>설  명 : <pre>단말기매각관리</pre></li>
 * <li>작성일 : 2015-09-01 17:01:16</li>
 * <li>작성자 : 양재석 (jsyang)</li>
 * </ul>
 *
 * @author 양재석 (jsyang)
 */
public class FPREqpDpspslMgmt extends fwk.base.FunctionUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public FPREqpDpspslMgmt(){
		super();
	}

    /**
	 * <pre>[FM]매각대상단말기조회</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-01 17:01:16
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CLCT_FR_DT [회수조회시작일자]
	 *	- field : CLCT_TO_DT [회수조회종료일자]
	 *	- field : EQP_MDL_CD [모델코드]
	 *	- field : EQP_SER_NO [단말기일련번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_DPSPSL_EQP_LIST
	 *		- field : EQP_MDL_CD [모델코드]
	 *		- field : EQP_MDL_NM [모델명]
	 *		- field : EQP_SER_NO [단말기일련번호]
	 *		- field : EQP_COLOR_CD [색상코드]
	 *		- field : ASSET_NO [자산번호]
	 *		- field : EQP_IN_DT [회수입고일자]
	 *		- field : FISCL_REMPRC [회계잔존가]
	 *		- field : EQP_JDG_RSLT_CD [단말기감정결과코드]
	 *		- field : EQP_OUT_DT [출고일자(매각일자)]
	 *		- field : CONF_YN [확정여부]
	 *		- field : EXP_DT [경과기간]
	 *		- field : RENT_END_DT [계약종료일]
	 *		- field : CNTRT_NO [계약번호]
	 * </pre>
	 */
	public IDataSet fInqDpspslObjEqpList(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet dsCnt = new DataSet();
       
        IDataSet reqDs = (IDataSet) requestData.clone();//원거래의 DataSet의 훼손을 막기 위한 Clone
        IRecordSet rsPagingList = null;
       
        int intTotalCnt = 0;
       
        try {
            // 1. DU lookup
            DPREqpDpspslMgmt dPREqpDpspslMgmt = (DPREqpDpspslMgmt) lookupDataUnit(DPREqpDpspslMgmt.class);
           
            // 2. TOTAL COUNT DM호출
            dsCnt = dPREqpDpspslMgmt.dSInqDpspslObjEqpListTotCnt(reqDs, onlineCtx);
            intTotalCnt = Integer.parseInt(dsCnt.getField("TOTAL_CNT"));  
            PagenateUtils.setPagenatedParamsToDataSet(reqDs);  //카운트 셋
           
            // 3. LIST DM호출
            responseData = dPREqpDpspslMgmt.dSInqDpspslObjEqpListPaging(reqDs, onlineCtx);
   
            rsPagingList = responseData.getRecordSet("RS_DPSPSL_EQP_LIST");
            PagenateUtils.setPagenatedParamToRecordSet(rsPagingList, reqDs, intTotalCnt);  //리스트 셋
               
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }
    
        return responseData;
    }

    /**
	 * <pre>[FM]매각대상단말기등록</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-01 17:01:16
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RQ_DPSPSL_EQP_LIST
	 *		- field : ASSET_NO [자산번호]
	 *		- field : EQP_MDL_CD [모델코드]
	 *		- field : EQP_SER_NO [단말기일련번호]
	 *		- field : EQP_COLOR_CD [색상코드]
	 *		- field : EQP_OUT_DT [출고일자]
	 *		- field : IN_DEALCO_CD [입고거래처코드]
	 *		- field : IN_CHRG_DEPT_CD [입고부서코드]
	 *		- field : OUT_DEALCO_CD [출고거래처코드]
	 *		- field : OUT_CHRG_DEPT_CD [출고부서코드]
	 *		- field : IN_DTL_CD [입고상세코드]
	 *		- field : OUT_DTL_CD [출고상세코드]
	 *		- field : CNTRT_NO [계약번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fDpspslObjEqpReg(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet reqDate = new DataSet();
        reqDate.putFieldMap(requestData.getFieldMap());
        
        try {
            CommonArea ca = getCommonArea(onlineCtx);
            IRecordSet reqSet = requestData.getRecordSet("RQ_DPSPSL_EQP_LIST");
            // 1. DU lookup
            DPREqpDpspslMgmt dPREqpDpspslMgmt = (DPREqpDpspslMgmt) lookupDataUnit(DPREqpDpspslMgmt.class);
            for(int i = 0; i < reqSet.getRecordCount(); i++){
                reqDate.putFieldMap(reqSet.getRecordMap(i));
                //0. 최초등록사용자ID,최종변경사용자ID
                reqDate.putField("FS_REG_USER_ID", ca.getUserNo());
                reqDate.putField("LS_CHG_USER_ID", ca.getUserNo());
                reqDate.putField("PRC_GB", "PR");
                
                //1.출고채번
                IDataSet valDS = dPREqpDpspslMgmt.dSDpspslObjEqpOutNum(reqDate, onlineCtx);
                reqDate.putField("EQP_OUT_NO", valDS.getField("EQP_OUT_NO"));
                //2.매각대상단말기출고등록
                reqDate.putField("CHRG_DEPT_CD", ""); //출고부서코드
                reqDate.putField("OUT_DEALCO_CD", ""); //출고거래처코드
                reqDate.putField("OUT_DTL_CD", "40"); //출고상세코드
                dPREqpDpspslMgmt.dIDpspslObjEqpOutReg(reqDate, onlineCtx);
                //3.입고채번
                valDS = dPREqpDpspslMgmt.dSDpspslObjEqpInNum(reqDate, onlineCtx);
                reqDate.putField("EQP_IN_NO", valDS.getField("EQP_IN_NO"));
                //4.매각대상단말기입고등록
                reqDate.putField("CHRG_DEPT_CD", ""); //입고부서코드
                reqDate.putField("OUT_DEALCO_CD", ""); //입고거래처코드
                reqDate.putField("OUT_DTL_CD", "50"); //입고상세코드
                dPREqpDpspslMgmt.dIDpspslObjEqpInReg(reqDate, onlineCtx);
                //5.매각대상단말기자산정보수정
                reqDate.putField("OP_CL_CD", "EP"); //업무구분코드를 중고로 변경
                reqDate.putField("LAST_IN_OUT_NO", reqDate.getField("EQP_IN_NO")); //최종입고출고번호
                dPREqpDpspslMgmt.dUDpspslObjEqpAssetInfoUpd(reqDate, onlineCtx);
                //6.단말기계약종료
                reqDate.putField("RENT_END_DT", reqDate.getField("EQP_OUT_DT")); //계약종료일자
                dPREqpDpspslMgmt.dUDpspsObjEqpCntrtInfoUpd(reqDate, onlineCtx);
                
                //매각처리후 TB_EP_CST_CONSULT_MGMT 삽입..
                responseData = callSharedBizComponentByDirect("ep.EPSCSBase", "fRegPrNrIn", reqDate, onlineCtx);
                 
            }
        } catch ( BizRuntimeException e ) {
            throw e;
        }
    
        return responseData;
    }
  
}
