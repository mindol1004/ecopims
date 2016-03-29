package dms.bi.bibbase.biz;

import fwk.common.CommonArea;
import fwk.constants.DmsConstants;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.exception.BizRuntimeException;
import nexcore.framework.core.util.StringUtils;


/**
 * <ul>
 * <li>업무 그룹명 : dms/기준정보</li>
 * <li>단위업무명: [PU]단말기모델관리</li>
 * <li>설  명 : <pre>단말기모델관리</pre></li>
 * <li>작성일 : 2015-06-29 10:12:14</li>
 * <li>작성자 : 개발자 (developer)</li>
 * </ul>
 *
 * @author 개발자 (developer)
 */
public class PBIEqpMdlMgmt extends fwk.base.ProcessUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public PBIEqpMdlMgmt(){
		super();
	}

	/**
	 * <pre>단말기모델조회</pre>
	 *
	 * @author 개발자 (developer)
	 * @since 2015-06-29 10:12:14
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : EQP_MDL_NM [모델명]
	 *	- field : EQP_MDL_CD [모델코드]
	 *	- field : MFACT_CD [제조사코드]
	 *	- field : NR_EQP_MDL_YN [신규R단말기모델여부]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_EQP_MDL_LIST
	 *		- field : EQP_MDL_CD [모델코드]
	 *		- field : EQP_MDL_NM [모델명]
	 *		- field : PET_NM [펫네임]
	 *		- field : MFACT_CD [제조사코드]
	 *		- field : DSTRB_EQP_YN [유통단말기여부]
	 *		- field : FS_REG_USER_ID [최초등록사용자ID]
	 *		- field : FS_REG_DTM [최초등록일시]
	 *		- field : LS_CHG_USER_ID [최종변경사용자ID]
	 *		- field : LS_CHG_DTM [최종변경일시]
	 *		- field : LAUNC_DT [출시일자]
	 *		- field : PROD_CHRTIC_1 [상품 특성1]
	 *		- field : PROD_CHRTIC_2 [상품 특성2]
	 *		- field : COMC [통신방식]
	 *		- field : SLFCO_CL [자사구분]
	 *		- field : HLMT_PRC [상한가격]
	 *		- field : OUT_PRC [출고가격]
	 *		- field : FIX_PRC_YN [고정가격여부]
	 * </pre>
	 */
	public IDataSet pInqEqpMdlLst(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
		try {
			// 1. FU lookup
			FBIEqpMdlMgmt fBIEqpMdlMgmt = (FBIEqpMdlMgmt) lookupFunctionUnit(FBIEqpMdlMgmt.class);
			// 2. FM 호출
			responseData = fBIEqpMdlMgmt.fInqEqpMdlLst(requestData, onlineCtx);
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
	 * <pre>단말기모델 저장</pre>
	 *
	 * @author 이영진 (newnofixing)
	 * @since 2015-06-29 10:12:14
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_EQP_MDL_LIST
	 *		- field : EQP_MDL_CD [모델코드]
	 *		- field : EQP_MDL_NM [모델명]
	 *		- field : PET_NM [펫네임]
	 *		- field : MFACT_CD [제조사코드]
	 *		- field : DSTRB_EQP_YN [유통단말기여부]
	 *		- field : FS_REG_USER_ID [최초등록사용자ID]
	 *		- field : FS_REG_DTM [최초등록일시]
	 *		- field : LS_CHG_USER_ID [최종변경사용자ID]
	 *		- field : LS_CHG_DTM [최종변경일시]
	 *		- field : LAUNC_DT [출시일자]
	 *		- field : PROD_CHRTIC_1 [상품 특성1]
	 *		- field : PROD_CHRTIC_2 [상품 특성2]
	 *		- field : COMC [통신방식]
	 *		- field : HLMT_PRC [상한가격]
	 *		- field : FIX_PRC_YN [고정가격여부]
	 *	- record : RS_EQP_COLOR_LIST
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : COLOR_CD [단말기색상코드]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet pSaveEqpMdl(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
		IRecordSet rs = requestData.getRecordSet("RS_EQP_COLOR_LIST");
		CommonArea ca = getCommonArea(onlineCtx);

		try {
			// 1. FU lookup
			FBIEqpMdlMgmt fBIEqpMdlMgmt = (FBIEqpMdlMgmt) lookupFunctionUnit(FBIEqpMdlMgmt.class);
			// 2. 입력 RS설정
			requestData.putFieldMap(requestData.getRecordSet("RS_EQP_MDL_LIST").getRecordMap(0));
			requestData.putField("USERNO", ca.getUserNo());
			// 3. 레코드셋 상태에 따른 분기
			if ( "I".equals(ca.getTrnPtrnDvcd()) ) { // INSERT
				fBIEqpMdlMgmt.fRegEqpMdl(requestData, onlineCtx);
				if(rs != null) {
                    for (int i=0; i<rs.getRecordCount(); i++) {
                        IRecord record = rs.getRecord(i);
                        requestData.putFieldMap(rs.getRecordMap(i));
                        if(requestData.getField("COLOR_CD") == null || "".equals(requestData.getField("COLOR_CD").toString().trim())){
                            continue;
                        }
                        if ( StringUtils.equals(DmsConstants.STATUS_INSERTED, record.get(DmsConstants.RECORD_STATUS)) ){
                            fBIEqpMdlMgmt.fRegEqpColor(requestData, onlineCtx);
                        } else if ( StringUtils.equals(DmsConstants.STATUS_DELETED, record.get(DmsConstants.RECORD_STATUS)) ){
                            fBIEqpMdlMgmt.fDelEqpColor(requestData, onlineCtx);
                        }
                    }
                }
			} else if ( "U".equals(ca.getTrnPtrnDvcd()) ) { // UPDATE
				if(requestData.getRecordSet("RS_EQP_MDL_LIST").getRecordCount() > 0) fBIEqpMdlMgmt.fUpdEqpMdl(requestData, onlineCtx);
				if(rs != null) {
				    for (int i=0; i<rs.getRecordCount(); i++) {
                        IRecord record = rs.getRecord(i);
                        requestData.putFieldMap(rs.getRecordMap(i));
                        if(requestData.getField("COLOR_CD") == null || "".equals(requestData.getField("COLOR_CD").toString().trim())){
                            continue;
                        }
                        if ( StringUtils.equals(DmsConstants.STATUS_INSERTED, record.get(DmsConstants.RECORD_STATUS)) ){
                            fBIEqpMdlMgmt.fRegEqpColor(requestData, onlineCtx);
                        } else if ( StringUtils.equals(DmsConstants.STATUS_DELETED, record.get(DmsConstants.RECORD_STATUS)) ){
                            fBIEqpMdlMgmt.fDelEqpColor(requestData, onlineCtx);
                        }
                    }
				}
			} else if ( "D".equals(ca.getTrnPtrnDvcd()) ) { // DELETE
				fBIEqpMdlMgmt.fDelEqpMdl(requestData, onlineCtx);
			}
			// 4. 결과값 리턴
			responseData.setOkResultMessage("DMS00000", null); //정상 처리되었습니다.
		} catch ( BizRuntimeException e ) {
			throw e;
		} catch ( Exception e ) {
			throw new BizRuntimeException("DMS00009", e); //시스템 오류
		}
		return responseData;
	}

    /**
	 *
	 *
	 * @author 이영진 (newnofixing)
	 * @since 2015-06-29 10:12:14
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : EQP_MDL_NM [모델명]
	 *	- field : EQP_MDL_CD [모델코드]
	 *	- field : MFACT_CD [제조사코드]
	 *	- field : NR_EQP_MDL_YN [신규R단말기모델여부]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_EQP_COLOR_LIST
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : COLOR_CD [단말기색상코드]
	 *		- field : CHECKED [체크여부]
	 * </pre>
	 */
	public IDataSet pInqEqpColorLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        try {
            // 1. FU lookup
            FBIEqpMdlMgmt fBIEqpMdlMgmt = (FBIEqpMdlMgmt) lookupFunctionUnit(FBIEqpMdlMgmt.class);
            // 2. FM 호출
            responseData = fBIEqpMdlMgmt.fEqpColorLst(requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }
        // 3. 결과값 리턴
        responseData.setOkResultMessage("DMS00001", null); // 정상 조회되었습니다.
        return responseData;
    }
  
}
