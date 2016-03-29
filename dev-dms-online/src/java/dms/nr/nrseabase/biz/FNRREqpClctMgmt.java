package dms.nr.nrseabase.biz;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.exception.BizRuntimeException;
import fwk.common.CommonArea;
import fwk.utils.PagenateUtils;


/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [FU]R센터입고관리</li>
 * <li>설  명 : <pre>R센터입고관리</pre></li>
 * <li>작성일 : 2015-08-11 19:19:39</li>
 * <li>작성자 : 장미진 (kuramotojin)</li>
 * </ul>
 *
 * @author 장미진 (kuramotojin)
 */
public class FNRREqpClctMgmt extends fwk.base.FunctionUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public FNRREqpClctMgmt(){
		super();
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-08-11 19:19:39
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : ST_DT [회수시작일]
	 *	- field : ED_DT [회수종료일]
	 *	- field : EQP_MDL_CD [모델코드]
	 *	- field : ED_DT_IN [입고종료일]
	 *	- field : EQP_SER_NO [단말일련번호]
	 *	- field : ST_DT_IN [입고시작일]
	 *	- field : INVE_ST_CD [재고상태코드]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_RCIBM_LIST
	 *		- field : EQP_IN_DT [입고일]
	 *		- field : ASSET_NO [자산번호]
	 *		- field : EQP_MDL_NM [모델명]
	 *		- field : EQP_COLOR_CD [모델코드]
	 *		- field : EQP_SER_NO [단말기일련번호]
	 *		- field : EQP_IMEI_NO [IME번호]
	 *		- field : AGN_CD [반납대리점코드]
	 *		- field : IN_OBJ_DTL_CD [회수구분]
	 *		- field : SKN_CLCT_DT [회수일]
	 * </pre>
	 */
	public IDataSet fInqREqpClctLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    IDataSet dsCnt = new DataSet();
		IDataSet reqDs = (IDataSet) requestData.clone();//원거래의 DataSet의 훼손을 막기 위한 Clone
		IRecordSet usrListRs = null;
		int intTotalCnt = 0;

		try {
			// 1. DU lookup
			DNRREqpClctMgmt dNRREqpClctMgmt = (DNRREqpClctMgmt) lookupDataUnit(DNRREqpClctMgmt.class);
			
			// 2. TOTAL COUNT DM호출
			dsCnt = dNRREqpClctMgmt.dSREqpClctLstTotCnt(requestData, onlineCtx);
			intTotalCnt = Integer.parseInt(dsCnt.getField("TOTAL_CNT"));
			PagenateUtils.setPagenatedParamsToDataSet(reqDs);

			// 3. LISTDM호출
			responseData = dNRREqpClctMgmt.dSREqpClctLstPaging(reqDs, onlineCtx);
			usrListRs = responseData.getRecordSet("RS_RCIBM_LIST");
			PagenateUtils.setPagenatedParamToRecordSet(usrListRs, reqDs, intTotalCnt);
			
		} catch ( BizRuntimeException e ) {
			throw e; //시스템 오류가 발생하였습니다.
		}
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-08-11 19:19:39
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_REQP_CLCT_HANDLE
	 *		- field : ASSET_NO [자산번호]
	 *		- field : EQP_MDL_NM [모델명]
	 *		- field : EQP_COLOR_CD [색상]
	 *		- field : EQP_SER_NO [단말일련번호]
	 *		- field : EQP_IMEI_NO [IME번호]
	 *		- field : INVE_ST_CD [재고상태코드]
	 *		- field : AGN_CD [대리점코드]
	 *		- field : CHECKED [체크박스]
	 *		- field : EQP_CLCT_OBJ_NO [회수상태코드]
	 *		- field : IN_OBJ_DTL_CD [회수구분코드]
	 *		- field : INVE_DEL_YN [감정데이터삭제여부]
	 *		- field : EQP_IN_DT [입고일]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fUREqpClct(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    CommonArea ca = getCommonArea(onlineCtx);
	
	    try {
	    	
			// 1. DU lookup
			DNRREqpClctMgmt dNRREqpClctMgmt = (DNRREqpClctMgmt) lookupDataUnit(DNRREqpClctMgmt.class);
			
			IRecordSet rs = requestData.getRecordSet("RS_REQP_CLCT_HANDLE");
			
			// 2. 업무 DM호출
			
			for(int i=0; i < rs.getRecordCount(); i++){
				requestData.putFieldMap(rs.getRecordMap(i));
				requestData.putField("USER_NO", ca.getUserNo());
				
				if("Y".equals(requestData.getField("INVE_DEL_YN"))){
					 dNRREqpClctMgmt.dDJdgDEL(requestData, onlineCtx); //감정테이블삭제
									
				}
				
				responseData = dNRREqpClctMgmt.dUREqpClct(requestData, onlineCtx);
			}
			
			
		} catch ( BizRuntimeException e ) {
			throw e; //시스템 오류가 발생하였습니다.
		}
	    return responseData;
	}
  
}
