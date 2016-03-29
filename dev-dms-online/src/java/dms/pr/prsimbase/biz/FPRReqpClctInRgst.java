package dms.pr.prsimbase.biz;

import java.util.Map;




import fwk.common.CommonArea;
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
 * <li>단위업무명: [FU]회수대상임대폰조회</li>
 * <li>설  명 : <pre>회수대상임대폰조회</pre></li>
 * <li>작성일 : 2015-07-14 20:47:57</li>
 * <li>작성자 : 이준우 (elmsliw)</li>
 * </ul>
 *
 * @author 이준우 (elmsliw)
 */
public class FPRReqpClctInRgst extends fwk.base.FunctionUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public FPRReqpClctInRgst(){
		super();
	}

	/**
	 * <pre>회수대상임대폰조회</pre>
	 *
	 * @author 박홍서 (uni9401)
	 * @since 2015-07-16 13:34:42
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : ST_DT [시작일]
	 *	- field : ED_DT [종료일]
	 *	- field : EQP_MDL_CD [모델]
	 *	- field : INVE_ST_CD [재고상태]
	 *	- field : EQP_SER_NO [시리얼넘버]
	 *	- field : ST_DT_IN [입고일자]
	 *	- field : ED_DT_IN [입고일자]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_EQP_LIST
	 *		- field : EQP_IN_NO [입고넘버]
	 *		- field : IN_SCHD_DT [입고예정일자]
	 *		- field : ASSET_NO [자산번호]
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : EQP_COLOR_CD [단말기색상코드]
	 *		- field : EQP_SER_NO [단말기일련번호]
	 *		- field : EQP_IMEI_NO [단말기IMEI번호]
	 *		- field : INVE_ST_CD [회수구분코드]
	 *		- field : EQP_CLCT_OBJ_NO [단말기입고대상번호]
	 *		- field : EQP_MDL_NM [단말기모델명]
	 *		- field : LAST_IN_OUT_NO [최종입출고번호]
	 *		- field : EQP_OUT_NO [출고번호]
	 *		- field : EQP_IN_DT [입고일자(입고TB)]
	 *		- field : EQP_IC_CD [입고구분]
	 * </pre>
	 */
	public IDataSet fInqReqpClctLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    IDataSet dsCnt = new DataSet();
		IRecordSet rsPagingList = null;
		int intTotalCnt = 0;
		
		try {
			// 1. DU lookup
			DPRReqpClctInRgst dPRReqpClctInRgst = (DPRReqpClctInRgst) lookupDataUnit(DPRReqpClctInRgst.class);
			// 2. TOTAL COUNT DM호출
			dsCnt = dPRReqpClctInRgst.dSReqpClctLstTotCnt(requestData, onlineCtx);
			
			intTotalCnt = Integer.parseInt(dsCnt.getField("TOTAL_CNT"));
			
			
			
			PagenateUtils.setPagenatedParamsToDataSet(requestData);
			// 3. LISTDM호출
			responseData = dPRReqpClctInRgst.dSReqpClctLstPaging(requestData, onlineCtx);

			rsPagingList = responseData.getRecordSet("RS_EQP_LIST");
			
			PagenateUtils.setPagenatedParamToRecordSet(rsPagingList, requestData, intTotalCnt);
			

		} catch ( BizRuntimeException e ) {
			throw e;
		} catch ( Exception e ) {
			throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
		}		
	    return responseData;
	}

	/**
	 * <pre>임대폰회수입고상태등록</pre>
	 *
	 * @author 이준우 (elmsliw)
	 * @since 2015-07-16 13:34:42
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_CLCT_LIST
	 *		- field : EQP_IN_DT [입고일자]
	 *		- field : EQP_IN_CLCT_NO [단말기입고대상번호]
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : EQP_SER_NO [단말기일련번호]
	 *		- field : INVE_ST_CD [입고상세코드]
	 *		- field : ASSET_NO [자산번호]
	 *		- field : EQP_IN_NO [입고번호]
	 *		- field : DEL_YN [삭제여부]
	 *		- field : LAST_IN_OUT_NO [최종입출고번호(자산)]
	 *		- field : LS_CHG_USER_ID [최종수정자ID]
	 *		- field : LS_CHG_DTM [최종수정일시]
	 *	- record : RS_EQP_IN_LIST
	 *		- field : EQP_IN_NO [단말기입고번호]
	 *		- field : ASSET_NO [자산번호]
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : EQP_SER_NO [단말기일련번호]
	 *		- field : EQP_IN_DT [입고일자]
	 *		- field : IN_DTL_CD [입고상세코드]
	 *		- field : IN_DEALCO_CD [입고처코드]
	 *		- field : CHRG_DEPT_CD [담당부서코드]
	 *		- field : DEL_YN [삭제여부]
	 *		- field : LAST_IN_OUT_NO [최종입출고번호(자산)]
	 *		- field : FS_REG_USER_ID [최초등록사용자ID]
	 *		- field : FS_REG_DTM [최초등록일시]
	 *		- field : LS_CHG_USER_ID [최종변경사용자ID]
	 *		- field : LS_CHG_DTM [최종변경일시]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fRegReqpClctIn(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    try {
			// 1. DU lookup
	    	DPRReqpClctInRgst dPRReqpClctInRgst = (DPRReqpClctInRgst) lookupDataUnit(DPRReqpClctInRgst.class);
			
	    	// 2. 단말기 입고 번호 채번
	    	IDataSet seqNoDs = dPRReqpClctInRgst.dSReqpClctLstSeq(requestData, onlineCtx);
	    		    	
	    	requestData.putField("EQP_IN_NO", seqNoDs.getField("EQP_IN_NO"));	//입고번호 셋
	    	requestData.putField("LAST_IN_OUT_NO", seqNoDs.getField("EQP_IN_NO")); //최종 입출고번호 셋

	    	// 3. 단말기 입고정보 등록
			responseData = dPRReqpClctInRgst.dIReqpClct(requestData, onlineCtx);
				
		} catch ( BizRuntimeException e ) {
			throw e;
		}
	    return responseData;
	}
/**
	 * <pre>임대폰회수입고상태수정</pre>
	 *
	 * @author 이준우 (elmsliw)
	 * @since 2015-07-16 13:34:42
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_CLCT_LIST
	 *		- field : EQP_IN_DT [입고일자]
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : EQP_SER_NO [단말기일련번호]
	 *		- field : INVE_ST_CD [입고상세코드]
	 *		- field : ASSET_NO [자산번호]
	 *		- field : DEL_YN [삭제여부]
	 *		- field : LAST_IN_OUT_NO [최종입출고번호(자산)]
	 *		- field : EQP_IN_NO [단말기입고번호]
	 *		- field : LS_CHG_USER_ID [최종수정자ID]
	 *		- field : LS_CHG_DTM [최종수정일시]
	 *	- record : RS_EQP_IN_LIST
	 *		- field : EQP_IN_NO [단말기입고번호]
	 *		- field : ASSET_NO [자산번호]
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : EQP_SER_NO [단말기일련번호]
	 *		- field : EQP_IN_DT [입고일자]
	 *		- field : IN_DTL_CD [입고상세코드]
	 *		- field : IN_DEALCO_CD [입고처코드]
	 *		- field : CHRG_DEPT_CD [담당부서코드]
	 *		- field : DEL_YN [삭제여부]
	 *		- field : LAST_IN_OUT_NO [최종입출고번호(자산)]
	 *		- field : FS_REG_USER_ID [최초등록사용자ID]
	 *		- field : FS_REG_DTM [최초등록일시]
	 *		- field : LS_CHG_USER_ID [최종변경사용자ID]
	 *		- field : LS_CHG_DTM [최종변경일시]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	
	/**
	 * <pre>임대폰회수입고상태수정</pre>
	 *
	 * @author 이준우 (elmsliw)
	 * @since 2015-07-16 13:34:42
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_CLCT_LIST
	 *		- field : IN_DT [입고일자]
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : EQP_SER_NO [단말기일련번호]
	 *		- field : INVE_ST_CD [입고상세코드]
	 *		- field : ASSET_NO [자산번호]
	 *		- field : DEL_YN [삭제여부]
	 *		- field : LAST_IN_OUT_NO [최종입출고번호(자산)]
	 *		- field : EQP_IN_NO [단말기입고번호]
	 *		- field : LS_CHG_USER_ID [최종수정자ID]
	 *		- field : LS_CHG_DTM [최종수정일시]
	 *	- record : RS_EQP_IN_LIST
	 *		- field : EQP_IN_NO [단말기입고번호]
	 *		- field : ASSET_NO [자산번호]
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : EQP_SER_NO [단말기일련번호]
	 *		- field : IN_DT [입고일자]
	 *		- field : IN_DTL_CD [입고상세코드]
	 *		- field : IN_DEALCO_CD [입고처코드]
	 *		- field : CHRG_DEPT_CD [담당부서코드]
	 *		- field : DEL_YN [삭제여부]
	 *		- field : LAST_IN_OUT_NO [최종입출고번호(자산)]
	 *		- field : FS_REG_USER_ID [최초등록사용자ID]
	 *		- field : FS_REG_DTM [최초등록일시]
	 *		- field : LS_CHG_USER_ID [최종변경사용자ID]
	 *		- field : LS_CHG_DTM [최종변경일시]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fUpdReqpClctIn(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    	    
	    try {
			// 1. DU lookup
	    	DPRReqpClctInRgst dPRReqpClctInRgst = (DPRReqpClctInRgst) lookupDataUnit(DPRReqpClctInRgst.class);
	    	
			// 2. 임대폰회수 입고 상태수정, 단말기 입고정보 수정, 단말기자산정보수정
	    	
			responseData = dPRReqpClctInRgst.dUReqpClct(requestData, onlineCtx);
			responseData = dPRReqpClctInRgst.dUReqpClctTbEqpIn(requestData, onlineCtx);
			responseData = dPRReqpClctInRgst.dUReqpClctTbEqpAsset(requestData, onlineCtx);
				    	

		} catch ( BizRuntimeException e ) {
			throw e;
		} 
	
	    return responseData;
	}
  
}
