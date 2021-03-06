package dms.pr.prsimbase.biz;

import java.util.Map;

import fwk.utils.PagenateUtils;
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
 * <li>업무 그룹명 : dms/임대R</li>
 * <li>단위업무명: [FU]재상품화처리관리</li>
 * <li>설  명 : <pre>재상품화처리관리</pre></li>
 * <li>작성일 : 2015-08-04 11:11:05</li>
 * <li>작성자 : 박민정 (dangnagu2)</li>
 * </ul>
 *
 * @author 박민정 (dangnagu2)
 */
public class FPRRCmcProcRgstMgmt extends fwk.base.FunctionUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public FPRRCmcProcRgstMgmt(){
		super();
	}

	/**
	 * <pre>재상품화처리조회</pre>
	 *
	 * @author 박민정 (dangnagu2)
	 * @since 2015-08-04 11:11:05
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : IN_FR_DT [입고시작일]
	 *	- field : IN_TO_DT [입고종료일]
	 *	- field : EQP_SER_NO [단말기일련번호]
	 *	- field : EQP_MDL_CD [단말기모델코드]
	 *	- field : EQP_MDL_NM [단말기모델명]
	 *	- field : BOX_NO [박스번호]
	 *	- field : EQP_RECMC_YN [재상품화여부]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_CMC_PROC_LIST
	 *		- field : IN_DT [입고일]
	 *		- field : ASSET_NO [자산번호]
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : EQP_MDL_NM [단말기모델명]
	 *		- field : EQP_COLOR_CD [단말기칼라코드]
	 *		- field : EQP_SER_NO [단말기일련번호]
	 *		- field : EQP_IMEI_NO [단말기IMEI번호]
	 *		- field : BOX_NO [박스번호]
	 *		- field : PRCH_AMT [매입가]
	 *		- field : EQP_JDG_DT [단말기감정일]
	 *		- field : EQP_JDG_RSLT_CD [단말기감정결과코드]
	 *		- field : JDG_APRV_YN [감정승인여부]
	 *		- field : JDG_CHRGR_NO [감정담당자번호]
	 *		- field : EQP_RECMC_YN [재상품화여부]
	 *		- field : IN_DEALCO_CD [매입처]
	 *		- field : DEALCO_NM [매입처명]
	 *		- field : JDG_APRV_DT [감정승인일자]
	 *		- field : EQP_JDG_SEQ [단말기감정순번]
	 *		- field : CMPT_OUT_NM [재상품화처리자]
	 *		- field : SVC_MGMT_NO [서비스관리번호]
	 * </pre>
	 */
	public IDataSet fInqRCmcProcRgstLst(IDataSet requestData, IOnlineContext onlineCtx) {
		 IDataSet responseData = new DataSet();
	     IDataSet dsCnt = new DataSet();
	    
		 IDataSet reqDs = (IDataSet) requestData.clone();//원거래의 DataSet의 훼손을 막기 위한 Clone
		 IRecordSet rsPagingList = null;
		
		 int intTotalCnt = 0;
		
		 try {
		 	 // 1. DU lookup
			 DPRRCmcProcRgstMgmt dPRRCmcProcRgstMgmt = (DPRRCmcProcRgstMgmt) lookupDataUnit(DPRRCmcProcRgstMgmt.class);
			
			 // 2. TOTAL COUNT DM호출
			 dsCnt = dPRRCmcProcRgstMgmt.dSRCmcProcRgstLstTotCnt(reqDs, onlineCtx);
			 intTotalCnt = Integer.parseInt(dsCnt.getField("TOTAL_CNT"));  
			 PagenateUtils.setPagenatedParamsToDataSet(reqDs);	//카운트 셋
			
			 // 3. LIST DM호출
			 responseData = dPRRCmcProcRgstMgmt.dSRCmcProcRgstLstPaging(reqDs, onlineCtx);
	
			 rsPagingList = responseData.getRecordSet("RS_CMC_PROC_LIST");
			 PagenateUtils.setPagenatedParamToRecordSet(rsPagingList, reqDs, intTotalCnt);	//리스트 셋
				
		 } catch ( BizRuntimeException e ) {
			 throw e;
		 } catch ( Exception e ) {
			 throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
		 }
	    return responseData;
	}

	/**
	 * <pre>재상품화단말기구성품재매칭목록조회</pre>
	 *
	 * @author 박민정 (dangnagu2)
	 * @since 2015-08-04 11:11:05
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : ASSET_NO [자산번호]
	 *	- field : EQP_JDG_SEQ [단말기감정순번]
	 *	- field : EQP_MDL_CD [단말기모델코드]
	 *	- field : EQP_SER_NO [단말기일련번호]
	 *	- field : SVC_MGMT_NO [서비스관리번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_CONST_MATCH_LIST
	 *		- field : CM_CD_ID [구성품코드]
	 *		- field : CM_CD_NM [구성품명]
	 *		- field : CMPT_CD [출고구성품코드]
	 *		- field : CHECKED [적용]
	 *		- field : CHECKED2 [재매칭대상여부]
	 * </pre>
	 */
	public IDataSet fInqRProdEqpConstRMatchLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    try {
            // 1. DU lookup
	    	 DPRRCmcProcRgstMgmt dPRRCmcProcRgstMgmt = (DPRRCmcProcRgstMgmt) lookupDataUnit(DPRRCmcProcRgstMgmt.class);
            // 2. 업무 DM호출
            responseData = dPRRCmcProcRgstMgmt.dSRProdEqpConstRMatchLst(requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        }
	
	    return responseData;
	}

	/**
	 * <pre>구성품출고등록</pre>
	 *
	 * @author 박민정 (dangnagu2)
	 * @since 2015-08-04 11:11:05
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_CONST_OUT_LIST
	 *		- field : CMPT_OUT_NO [구성품출고번호]
	 *		- field : CMPT_OUT_DTL_CD [구성품출고상세코드]
	 *		- field : CMPT_CD [구성품코드]
	 *		- field : CMPT_USE_QTY [구성품사용수량]
	 *		- field : CMPT_OUT_DT [구성품출고일자]
	 *		- field : ASSET_NO [자산번호]
	 *		- field : EQP_JDG_SEQ [단말기감정순번]
	 *		- field : DEL_YN [삭제여부]
	 *		- field : FS_REG_USER_ID [최초등록사용자ID]
	 *		- field : FS_REG_DTM [최초등록일시]
	 *		- field : LS_CHG_USER_ID [최종변경사용자ID]
	 *		- field : LS_CHG_DTM [최종변경일시]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fRegConstOut(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    try {
			// 1. DU lookup
	    	DPRRCmcProcRgstMgmt dPRRCmcProcRgstMgmt = (DPRRCmcProcRgstMgmt) lookupDataUnit(DPRRCmcProcRgstMgmt.class);
			
			//2.구성품일련번호조회
			IDataSet valDS = dPRRCmcProcRgstMgmt.dSConstOutNum(requestData, onlineCtx);
			requestData.putField("CMPT_OUT_NO", valDS.getField("CMPT_OUT_NO"));

			// 3. 업무 DM호출 구성품입고입력
			responseData = dPRRCmcProcRgstMgmt.dIConstOut(requestData, onlineCtx);

		} catch ( BizRuntimeException e ) {
			throw e;
		} 
	
	    return responseData;
	}

	/**
	 * <pre>구성품출고상태수정</pre>
	 *
	 * @author 박민정 (dangnagu2)
	 * @since 2015-08-04 11:11:05
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_CONST_OUT_LIST
	 *		- field : CMPT_OUT_NO [구성품출고번호]
	 *		- field : CMPT_OUT_DTL_CD [구성품출고상세코드]
	 *		- field : CMPT_CD [구성품코드]
	 *		- field : CMPT_USE_QTY [구성품사용수량]
	 *		- field : CMPT_OUT_DT [구성품출고일자]
	 *		- field : ASSET_NO [자산번호]
	 *		- field : EQP_JDG_SEQ [단말기감정순번]
	 *		- field : DEL_YN [삭제여부]
	 *		- field : FS_REG_USER_ID [최초등록사용자ID]
	 *		- field : FS_REG_DTM [최초등록일시]
	 *		- field : LS_CHG_USER_ID [최종변경사용자ID]
	 *		- field : LS_CHG_DTM [최종변경일시]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fUpdConstOutStat(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    try {
			// 1. DU lookup
	    	DPRRCmcProcRgstMgmt dPRRCmcProcRgstMgmt = (DPRRCmcProcRgstMgmt) lookupDataUnit(DPRRCmcProcRgstMgmt.class);
	    	
			// 3. 단말기계약수정
	    	
			responseData = dPRRCmcProcRgstMgmt.dUReqpConstOutStat(requestData, onlineCtx);  	

		} catch ( BizRuntimeException e ) {
			throw e;
		} 
		
	    return responseData;
	}

	/**
	 * <pre>단말기재상품여부상태수정</pre>
	 *
	 * @author 박민정 (dangnagu2)
	 * @since 2015-08-04 11:11:05
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_EQP_JDG_LIST
	 *		- field : EQP_RECMC_YN [재상품여부]
	 *		- field : ASSET_NO [자산번호]
	 *		- field : EQP_JDG_SEQ [단말기감정순번]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fUpdjdgStat(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    try {
			// 1. DU lookup
	    	DPRRCmcProcRgstMgmt dPRRCmcProcRgstMgmt = (DPRRCmcProcRgstMgmt) lookupDataUnit(DPRRCmcProcRgstMgmt.class);
	    	
			// 3. 단말기계약수정
	    	
			responseData = dPRRCmcProcRgstMgmt.dUReqpjdgStat(requestData, onlineCtx);  	

		} catch ( BizRuntimeException e ) {
			throw e;
		} 
	
	    return responseData;
	}
  
}
