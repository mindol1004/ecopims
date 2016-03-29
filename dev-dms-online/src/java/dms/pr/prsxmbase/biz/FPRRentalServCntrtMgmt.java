package dms.pr.prsxmbase.biz;

import java.util.Map;

import fwk.common.CommonArea;
import fwk.constants.DmsConstants;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.IResultMessage;
import nexcore.framework.core.data.ResultMessage;
import nexcore.framework.core.exception.BizRuntimeException;
import nexcore.framework.core.util.StringUtils;


/**
 * <ul>
 * <li>업무 그룹명 : dms/임대R</li>
 * <li>단위업무명: [FU]렌탈용역계약관리</li>
 * <li>설  명 : <pre>[FU]렌탈용역계약관리</pre></li>
 * <li>작성일 : 2015-07-24 10:17:15</li>
 * <li>작성자 : 양재석 (jsyang)</li>
 * </ul>
 *
 * @author 양재석 (jsyang)
 */
public class FPRRentalServCntrtMgmt extends fwk.base.FunctionUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public FPRRentalServCntrtMgmt(){
		super();
	}

    /**
	 * <pre>[FM]포괄계약조회</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-07-24 10:17:15
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CMPHS_CNTRT_NM [포괄계약명]
	 *	- field : STA_IN_DT [조회시작일자]
	 *	- field : END_IN_DT [조회종료일자]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_CMPHS_CNTRT_LIST
	 *		- field : CMPHS_CNTRT_NO [포괄계약번호]
	 *		- field : CMPHS_CNTRT_NM [포괄계약명]
	 *		- field : CMPHS_CNTRT_STA_DT [포괄계약시작일자]
	 *		- field : CMPHS_CNTRT_END_DT [포괄계약종료일자]
	 * </pre>
	 */
	public IDataSet fInqCmphsCntrtLst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        try {
            // 1. DU lookup
            DPRRentalServCntrtMgmt dPRRentalServCntrtMgmt = (DPRRentalServCntrtMgmt) lookupDataUnit(DPRRentalServCntrtMgmt.class);
            // 2. 업무 DM호출
            responseData = dPRRentalServCntrtMgmt.dSInqCmphsCntrtLst(requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        }
    
        return responseData;
    }

    /**
	 * <pre>[FM]단말기계약목록</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-08-11 17:39:50
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CMPHS_CNTRT_NO [포괄계약번호]
	 *	- field : OP_CL_CD [업무구분코드]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_EQP_CNTRT_LIST
	 *		- field : CNTRT_NO [계약번호]
	 *		- field : OP_CL_CD [업무구분코드]
	 *		- field : CMPHS_CNTRT_NO [포괄계약번호]
	 *		- field : RENTAL_POLY_NO [렌탈정책번호]
	 *		- field : PEN_POLY_NO [위약금정책번호]
	 *		- field : DCP_NO [손해배상금정책번호]
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : RENTAL_CNTRT_STA_DT [계약시작일자]
	 *		- field : RENTAL_CNTRT_END_DT [계약종료일자]
	 *		- field : RENTAL_CNTRT_PRD [계약기간]
	 *		- field : MM_RENTAL_FEE [월렌탈료]
	 *		- field : RENTAL_QTY [수량]
	 *		- field : EQP_RECMC_CMMS [재상품화수수료]
	 *		- field : EQP_PRCH_AMT [매입가]
	 *		- field : EQP_REMPRC [잔존가]
	 *		- field : EQP_ALLOT_CMMS [할부수수료]
	 *		- field : RENTAL_CNTRT_RMK [비고]
	 *		- field : FS_REG_USER_ID [최초등록사용자ID]
	 *		- field : LS_CHG_USER_ID [최종변경사용자ID]
	 *		- field : EQP_MDL_NM [단말기모델명]
	 * </pre>
	 */
	public IDataSet fInqEqpCntrtLst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        try {
            // 1. DU lookup
            DPRRentalServCntrtMgmt dPRRentalServCntrtMgmt = (DPRRentalServCntrtMgmt) lookupDataUnit(DPRRentalServCntrtMgmt.class);
            // 2. 업무 DM호출
            responseData = dPRRentalServCntrtMgmt.dSInqEqpCntrtLst(requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        }
    
        return responseData;
    }

    /**
	 * <pre>[FM]포괄계약등록</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-07-24 10:17:15
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RQ_CMPHS_CNTRT_LIST
	 *		- field : CMPHS_CNTRT_NO [포괄계약번호]
	 *		- field : CMPHS_CNTRT_NM [포괄계약명]
	 *		- field : CMPHS_CNTRT_STA_DT [포괄계약시작일자]
	 *		- field : CMPHS_CNTRT_END_DT [포괄계약종료일자]
	 *		- field : FS_REG_USER_ID [최초등록사용자ID]
	 *		- field : LS_CHG_USER_ID [최종변경사용자ID]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fRgsCmphsCntrt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        try {
            CommonArea ca = getCommonArea(onlineCtx);
            IRecordSet reqSet = requestData.getRecordSet("RQ_CMPHS_CNTRT_LIST");
            // 1. DU lookup
            DPRRentalServCntrtMgmt dPRRentalServCntrtMgmt = (DPRRentalServCntrtMgmt) lookupDataUnit(DPRRentalServCntrtMgmt.class);
            for(int i = 0; i < reqSet.getRecordCount(); i++){
                requestData.putFieldMap(reqSet.getRecordMap(i));
                // 2. 계약일련번호조회
                IDataSet valDS = dPRRentalServCntrtMgmt.dSCmphsCntrtNo(requestData, onlineCtx);
                requestData.putField("CMPHS_CNTRT_NO", valDS.getField("CMPHS_CNTRT_NO"));
                // 3. 최초등록사용자ID,최종변경사용자ID
                requestData.putField("FS_REG_USER_ID", ca.getUserNo());
                requestData.putField("LS_CHG_USER_ID", ca.getUserNo());
                // 4. 등록
                responseData = dPRRentalServCntrtMgmt.dICmphsCntrtRgst(requestData, onlineCtx);
            }
        } catch ( BizRuntimeException e ) {
            throw e;
        }    
        return responseData;
    }

    /**
	 * <pre>[FM]포괄계약수정</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-07-24 14:19:09
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RQ_CMPHS_CNTRT_LIST
	 *		- field : CMPHS_CNTRT_NO [포괄계약번호]
	 *		- field : CMPHS_CNTRT_NM [포괄계약명]
	 *		- field : CMPHS_CNTRT_STA_DT [포괄계약시작일자]
	 *		- field : CMPHS_CNTRT_END_DT [포괄계약종료일자]
	 *		- field : FS_REG_USER_ID [최초등록사용자ID]
	 *		- field : LS_CHG_USER_ID [최종변경사용자ID]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fUpdCmphsCntrt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        try {
            CommonArea ca = getCommonArea(onlineCtx);
            IRecordSet reqSet = requestData.getRecordSet("RQ_CMPHS_CNTRT_LIST");
            // 1. DU lookup
            DPRRentalServCntrtMgmt dPRRentalServCntrtMgmt = (DPRRentalServCntrtMgmt) lookupDataUnit(DPRRentalServCntrtMgmt.class);
            for(int i = 0; i < reqSet.getRecordCount(); i++){
                requestData.putFieldMap(reqSet.getRecordMap(i));
                // 1. 최초등록사용자ID,최종변경사용자ID
                requestData.putField("LS_CHG_USER_ID", ca.getUserNo());
                // 2. 수정
                responseData = dPRRentalServCntrtMgmt.dUCmphsCntrtUpd(requestData, onlineCtx);
                
                requestData.putField("RENTAL_CNTRT_STA_DT", requestData.getField("CMPHS_CNTRT_STA_DT"));
                requestData.putField("RENTAL_CNTRT_END_DT", requestData.getField("CMPHS_CNTRT_END_DT"));
                // 3. 단말기 렌탈계약 기간 수정
                responseData = dPRRentalServCntrtMgmt.dUEqpRentalCntrtDtCmphsUpd(requestData, onlineCtx);
            }
        } catch ( BizRuntimeException e ) {
            throw e;
        }    
    
        return responseData;
    }

    /**
	 * <pre>[FM]포괄계약삭제</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-07-24 14:20:36
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RQ_CMPHS_CNTRT_LIST
	 *		- field : CMPHS_CNTRT_NO [포괄계약번호]
	 *		- field : LS_CHG_USER_ID [최종변경사용자ID]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fDelCmphsCntrt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        try {
            CommonArea ca = getCommonArea(onlineCtx);
            IRecordSet reqSet = requestData.getRecordSet("RQ_CMPHS_CNTRT_LIST");
            // 1. DU lookup
            DPRRentalServCntrtMgmt dPRRentalServCntrtMgmt = (DPRRentalServCntrtMgmt) lookupDataUnit(DPRRentalServCntrtMgmt.class);
            for(int i = 0; i < reqSet.getRecordCount(); i++){
                requestData.putFieldMap(reqSet.getRecordMap(i));
                // 0. 매칭계약조회
                responseData = dPRRentalServCntrtMgmt.dSInqCmphsCntrtJoinCnt(requestData, onlineCtx);
                if(Integer.parseInt(responseData.getField("CNTRT_CNT").toString()) > 0){
                    throw new BizRuntimeException("DMS00099"); //단말기 매칭이 이루어진 계약 건 입니다. 확인후 삭제 바랍니다.
                }
                // 1. 최초등록사용자ID,최종변경사용자ID
                requestData.putField("LS_CHG_USER_ID", ca.getUserNo());
                // 2. 수정
                responseData = dPRRentalServCntrtMgmt.dDCmphsCntrtDel(requestData, onlineCtx);
            }
        } catch ( BizRuntimeException e ) {
            throw e;
        }    
    
        return responseData;
    }

    /**
	 * <pre>[FM]단말기렌탈계약삭제</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-07-24 16:52:09
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RQ_EQP_RENTAL_CNTRT_LIST
	 *		- field : CNTRT_NO [계약번호]
	 *		- field : LS_CHG_USER_ID [최종변경사용자ID]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fDelEqpRentalCntrt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        try {
            CommonArea ca = getCommonArea(onlineCtx);
            IRecordSet reqSet = requestData.getRecordSet("RQ_EQP_RENTAL_CNTRT_LIST");
            // 1. DU lookup
            DPRRentalServCntrtMgmt dPRRentalServCntrtMgmt = (DPRRentalServCntrtMgmt) lookupDataUnit(DPRRentalServCntrtMgmt.class);
            for(int i = 0; i < reqSet.getRecordCount(); i++){
                requestData.putFieldMap(reqSet.getRecordMap(i));
                // 0. 매칭계약조회
                responseData = dPRRentalServCntrtMgmt.dSInqEqpCntrtJoinCnt(requestData, onlineCtx);
                if(Integer.parseInt(responseData.getField("CNTRT_CNT").toString()) > 0){
                    throw new BizRuntimeException("DMS00099"); //단말기 매칭이 이루어진 계약 건 입니다. 확인후 삭제 바랍니다.
                }
                // 1. 최초등록사용자ID,최종변경사용자ID
                requestData.putField("LS_CHG_USER_ID", ca.getUserNo());
                // 2. 수정
                responseData = dPRRentalServCntrtMgmt.dDEqpRentalCntrtDel(requestData, onlineCtx);
            }
        } catch ( BizRuntimeException e ) {
            throw e;
        }    
    
        return responseData;
    }

    /**
	 * <pre>[FM]단말기렌탈계약수정</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-08-11 17:39:50
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RQ_EQP_RENTAL_CNTRT_LIST
	 *		- field : CNTRT_NO [계약번호]
	 *		- field : OP_CL_CD [업무구분코드]
	 *		- field : CMPHS_CNTRT_NO [포괄계약번호]
	 *		- field : RENTAL_POLY_NO [렌탈정책번호]
	 *		- field : PEN_POLY_NO [위약금정책번호]
	 *		- field : DCP_NO [손해배상금정책번호]
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : RENTAL_CNTRT_STA_DT [계약시작일자]
	 *		- field : RENTAL_CNTRT_END_DT [계약종료일자]
	 *		- field : RENTAL_CNTRT_PRD [계약기간]
	 *		- field : MM_RENTAL_FEE [월렌탈료]
	 *		- field : RENTAL_QTY [수량]
	 *		- field : EQP_RECMC_CMMS [재상품화수수료]
	 *		- field : EQP_PRCH_AMT [매입가]
	 *		- field : EQP_REMPRC [잔존가]
	 *		- field : EQP_ALLOT_CMMS [할부수수료]
	 *		- field : RENTAL_CNTRT_RMK [비고]
	 *		- field : FS_REG_USER_ID [최초등록사용자ID]
	 *		- field : LS_CHG_USER_ID [최종변경사용자ID]
	 *	- record : RQ_EQP_CNTRT_CMPT_LIST
	 *		- field : CMPT_CD [구성품코드]
	 *		- field : CMPT_UPRC [구성품단가]
	 *		- field : RTN_ITM_YN [반납품목여부]
	 *		- field : CNTRT_NO [계약번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fUpdEqpRentalCntrt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet reqTemp = new DataSet();
        IDataSet responseDataPoly = new DataSet();
        IDataSet requestDataCmpt = new DataSet();
        IDataSet requestDataPoly = new DataSet();
        IRecordSet cmpPolyListRs = null;

        try {
            CommonArea ca = getCommonArea(onlineCtx);
            IRecordSet reqSet = requestData.getRecordSet("RQ_EQP_RENTAL_CNTRT_LIST");
            // 1. DU lookup
            DPRRentalServCntrtMgmt dPRRentalServCntrtMgmt = (DPRRentalServCntrtMgmt) lookupDataUnit(DPRRentalServCntrtMgmt.class);
            for(int i = 0; i < reqSet.getRecordCount(); i++){
                requestData.putFieldMap(reqSet.getRecordMap(i));
                // 1. 최초등록사용자ID,최종변경사용자ID
                requestData.putField("LS_CHG_USER_ID", ca.getUserNo());
                // 2.손해배상금정책번호 취득
                responseDataPoly = new DataSet();
                requestDataPoly.putField("OP_CL_CD", "PR");
                requestDataPoly.putField("EQP_MDL_CD", requestData.getField("EQP_MDL_CD"));
                requestDataPoly.putField("RENTAL_CNTRT_STA_DT", requestData.getField("RENTAL_CNTRT_STA_DT"));
                requestDataPoly.putField("RENTAL_CNTRT_END_DT", requestData.getField("RENTAL_CNTRT_END_DT"));
                responseDataPoly = new DataSet();
                responseDataPoly = callSharedBizComponentByDirect("nr.NRSPLBase", "fInqCmpPolyLst", requestDataPoly, onlineCtx);
                if(responseDataPoly.getRecordSetCount() > 0){
                    cmpPolyListRs = responseDataPoly.getRecordSet("RS_CMP_POLY_LIST");
                    requestData.putField("DCP_NO", cmpPolyListRs.getRecordMap(0).get("DCP_NO"));
                }
                // 3.위약금정책번호 취득
                responseDataPoly = new DataSet();
                responseDataPoly = callSharedBizComponentByDirect("nr.NRSPLBase", "fInqPenPolyLst", requestDataPoly, onlineCtx);
                if(responseDataPoly.getRecordSetCount() > 0){
                    cmpPolyListRs = responseDataPoly.getRecordSet("RS_PEN_POLY_LIST");
                    requestData.putField("PEN_POLY_NO", cmpPolyListRs.getRecordMap(0).get("PEN_POLY_NO"));
                }
                // 4.렌탈판매정책번호 취득
                responseDataPoly = new DataSet();
                responseDataPoly = callSharedBizComponentByDirect("nr.NRSPLBase", "fInqEqpPolyLst", requestDataPoly, onlineCtx);
                if(responseDataPoly.getRecordSetCount() > 0){
                    cmpPolyListRs = responseDataPoly.getRecordSet("RS_EQP_POLY_LIST");
                    requestData.putField("RENTAL_POLY_NO", cmpPolyListRs.getRecordMap(0).get("RENTAL_POLY_NO"));
                }
                // 5. 수정
                responseData = dPRRentalServCntrtMgmt.dUEqpRentalCntrtUpd(requestData, onlineCtx);
                // 구성품
                // 기존구성품 삭제
                reqTemp.putField("CNTRT_NO", requestData.getField("CNTRT_NO"));
                reqTemp.putField("LS_CHG_USER_ID", ca.getUserNo());
                responseData = dPRRentalServCntrtMgmt.dDEqpCntrtCmptUprcDel(reqTemp, onlineCtx);
            }
        } catch ( BizRuntimeException e ) {
            throw e;
        }    
    
        return responseData;
    }

    /**
	 * <pre>[FM]단말기렌탈계약등록</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-08-11 17:39:50
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RQ_EQP_RENTAL_CNTRT_LIST
	 *		- field : CNTRT_NO [계약번호]
	 *		- field : OP_CL_CD [업무구분코드]
	 *		- field : CMPHS_CNTRT_NO [포괄계약번호]
	 *		- field : RENTAL_POLY_NO [렌탈정책번호]
	 *		- field : PEN_POLY_NO [위약금정책번호]
	 *		- field : DCP_NO [손해배상금정책번호]
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : RENTAL_CNTRT_STA_DT [계약시작일자]
	 *		- field : RENTAL_CNTRT_END_DT [계약종료일자]
	 *		- field : RENTAL_CNTRT_PRD [계약기간]
	 *		- field : MM_RENTAL_FEE [월렌탈료]
	 *		- field : RENTAL_QTY [수량]
	 *		- field : EQP_RECMC_CMMS [재상품화수수료]
	 *		- field : EQP_PRCH_AMT [매입가]
	 *		- field : EQP_REMPRC [잔존가]
	 *		- field : EQP_ALLOT_CMMS [할부수수료]
	 *		- field : RENTAL_CNTRT_RMK [비고]
	 *		- field : FS_REG_USER_ID [최초등록사용자ID]
	 *		- field : LS_CHG_USER_ID [최종변경사용자ID]
	 *	- record : RQ_EQP_CNTRT_CMPT_LIST
	 *		- field : CMPT_CD [구성품코드]
	 *		- field : CMPT_UPRC [구성품단가]
	 *		- field : RTN_ITM_YN [반납품목여부]
	 *		- field : CNTRT_NO [계약번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fRgsEqpRentalCntrt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet responseDataPoly = new DataSet();
        IDataSet requestDataCmpt = new DataSet();
        IDataSet requestDataPoly = new DataSet();
        IRecordSet cmpPolyListRs = null;
    
        try {
            CommonArea ca = getCommonArea(onlineCtx);
            IRecordSet reqSet = requestData.getRecordSet("RQ_EQP_RENTAL_CNTRT_LIST");
            // 1. DU lookup
            DPRRentalServCntrtMgmt dPRRentalServCntrtMgmt = (DPRRentalServCntrtMgmt) lookupDataUnit(DPRRentalServCntrtMgmt.class);
            for(int i = 0; i < reqSet.getRecordCount(); i++){
                requestData.putFieldMap(reqSet.getRecordMap(i));
                // 2. 단말기렌탈계약일련번호조회
                IDataSet valDS = dPRRentalServCntrtMgmt.dSEqpRentalCntrtNo(requestData, onlineCtx);
                requestData.putField("CNTRT_NO", valDS.getField("CNTRT_NO"));
                // 3.손해배상금정책번호 취득
                responseDataPoly = new DataSet();
                requestDataPoly.putField("OP_CL_CD", "PR");
                requestDataPoly.putField("EQP_MDL_CD", requestData.getField("EQP_MDL_CD"));
                requestDataPoly.putField("RENTAL_CNTRT_STA_DT", requestData.getField("RENTAL_CNTRT_STA_DT"));
                requestDataPoly.putField("RENTAL_CNTRT_END_DT", requestData.getField("RENTAL_CNTRT_END_DT"));
                responseDataPoly = new DataSet();
                responseDataPoly = callSharedBizComponentByDirect("nr.NRSPLBase", "fInqCmpPolyLst", requestDataPoly, onlineCtx);
                if(responseDataPoly.getRecordSetCount() > 0){
                    cmpPolyListRs = responseDataPoly.getRecordSet("RS_CMP_POLY_LIST");
                    requestData.putField("DCP_NO", cmpPolyListRs.getRecordMap(0).get("DCP_NO"));
                }
                // 4.위약금정책번호 취득
                responseDataPoly = new DataSet();
                responseDataPoly = callSharedBizComponentByDirect("nr.NRSPLBase", "fInqPenPolyLst", requestDataPoly, onlineCtx);
                if(responseDataPoly.getRecordSetCount() > 0){
                    cmpPolyListRs = responseDataPoly.getRecordSet("RS_PEN_POLY_LIST");
                    requestData.putField("PEN_POLY_NO", cmpPolyListRs.getRecordMap(0).get("PEN_POLY_NO"));
                }
                // 5.렌탈판매정책번호 취득
                responseDataPoly = new DataSet();
                responseDataPoly = callSharedBizComponentByDirect("nr.NRSPLBase", "fInqEqpPolyLst", requestDataPoly, onlineCtx);
                if(responseDataPoly.getRecordSetCount() > 0){
                    cmpPolyListRs = responseDataPoly.getRecordSet("RS_EQP_POLY_LIST");
                    requestData.putField("RENTAL_POLY_NO", cmpPolyListRs.getRecordMap(0).get("RENTAL_POLY_NO"));
                }
                
                // 6. 최초등록사용자ID,최종변경사용자ID
                requestData.putField("FS_REG_USER_ID", ca.getUserNo());
                requestData.putField("LS_CHG_USER_ID", ca.getUserNo());
                // 7. 등록
                responseData = dPRRentalServCntrtMgmt.dIEqpRentalCntrtRgst(requestData, onlineCtx);
                // 구성품
                IRecordSet reqSetCmpt = requestData.getRecordSet("RQ_EQP_CNTRT_CMPT_LIST");
                for(int j = 0; j < reqSetCmpt.getRecordCount(); j++){
                    requestDataCmpt.putFieldMap(reqSetCmpt.getRecordMap(j));
                    requestDataCmpt.putField("CNTRT_NO", valDS.getField("CNTRT_NO"));
                    requestDataCmpt.putField("FS_REG_USER_ID", ca.getUserNo());
                    requestDataCmpt.putField("LS_CHG_USER_ID", ca.getUserNo());
                    // 등록
                    responseData = dPRRentalServCntrtMgmt.dIEqpCntrtCmptUprcRgst(requestDataCmpt, onlineCtx);
                }
            }
        } catch ( BizRuntimeException e ) {
            throw e;
        }    
    
        return responseData;
    }

    /**
	 * <pre>[FM]단말기렌탈계약복합처리(C/U/D)</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-08-11 17:39:50
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RQ_EQP_RENTAL_CNTRT_LIST
	 *		- field : CNTRT_NO [계약번호]
	 *		- field : OP_CL_CD [업무구분코드]
	 *		- field : CMPHS_CNTRT_NO [포괄계약번호]
	 *		- field : RENTAL_POLY_NO [렌탈정책번호]
	 *		- field : PEN_POLY_NO [위약금정책번호]
	 *		- field : DMG_CMP_AMT_POLY_NO [손해배상금정책번호]
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : RENTAL_CNTRT_STA_DT [계약시작일자]
	 *		- field : RENTAL_CNTRT_END_DT [계약종료일자]
	 *		- field : RENTAL_CNTRT_PRD [계약기간]
	 *		- field : MTH_RENTAL_FEE [월렌탈료]
	 *		- field : RENTAL_QTY [수량]
	 *		- field : RE_CMC_CMMS [재상품화수수료]
	 *		- field : PRCH_PRC [매입가]
	 *		- field : REM_PRC [잔존가]
	 *		- field : ALLOT_CMMS [할부수수료]
	 *		- field : RMK [비고]
	 *		- field : FS_REG_USER_ID [최초등록사용자ID]
	 *		- field : LS_CHG_USER_ID [최종변경사용자ID]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fMixEqpRentalCntrt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        try {
            CommonArea ca = getCommonArea(onlineCtx);
            IRecordSet reqSet = requestData.getRecordSet("RQ_EQP_RENTAL_CNTRT_LIST");
            // 1. DU lookup
            DPRRentalServCntrtMgmt dPRRentalServCntrtMgmt = (DPRRentalServCntrtMgmt) lookupDataUnit(DPRRentalServCntrtMgmt.class);
            for(int i = 0; i < reqSet.getRecordCount(); i++){
                requestData.putFieldMap(reqSet.getRecordMap(i));
                requestData.putField("LS_CHG_USER_ID", ca.getUserNo());
            }
        } catch ( BizRuntimeException e ) {
            throw e;
        }    
    
        return responseData;
    }

    /**
	 * <pre>[FM]단말기계약구성품단가조회</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-07-24 10:17:15
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNTRT_NO [계약번호]
	 *	- field : OP_CL_CD [업무구분]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_EQP_CNTRT_CMPT_UPRC_LIST
	 *		- field : CNTRT_NO [계약번호]
	 *		- field : CMPT_CD [구성품코드]
	 *		- field : CMPT_UPRC [구성품단가]
	 *		- field : RTN_ITM_YN [반납품목여부]
	 *		- field : CHECKED [CHECKED]
	 * </pre>
	 */
	public IDataSet fInqEqpCntrtCmptUprcLst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        try {
            // 1. DU lookup
            DPRRentalServCntrtMgmt dPRRentalServCntrtMgmt = (DPRRentalServCntrtMgmt) lookupDataUnit(DPRRentalServCntrtMgmt.class);
            // 2. 업무 DM호출
            responseData = dPRRentalServCntrtMgmt.dSInqEqpCntrtCmptUprcLst(requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        }
    
        return responseData;
    }

    /**
	 * <pre>[FM]단말기계약구성품단가관리</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-07-28 13:43:43
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RQ_EQP_CNTRT_CMPT_LIST
	 *		- field : CMPT_CD [구성품코드]
	 *		- field : CNTRT_NO [계약번호]
	 *		- field : CMPT_UPRC [구성품단가]
	 *		- field : RTN_ITM_YN [반납품목여부]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fUpdEqpCntrtCmptUprcMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        try {
            CommonArea ca = getCommonArea(onlineCtx);
            IRecordSet reqSet = requestData.getRecordSet("RQ_EQP_CNTRT_CMPT_LIST");
            // 1. DU lookup
            DPRRentalServCntrtMgmt dPRRentalServCntrtMgmt = (DPRRentalServCntrtMgmt) lookupDataUnit(DPRRentalServCntrtMgmt.class);
            for(int i = 0; i < reqSet.getRecordCount(); i++){
                requestData.putFieldMap(reqSet.getRecordMap(i));
                // 1. 최초등록사용자ID,최종변경사용자ID
                requestData.putField("FS_REG_USER_ID", ca.getUserNo());
                requestData.putField("LS_CHG_USER_ID", ca.getUserNo());
                requestData.putField("DEL_YN", "N");
                responseData = dPRRentalServCntrtMgmt.dIUEqpCntrtCmptUprcUpdRgst(requestData, onlineCtx);
            }
        } catch ( BizRuntimeException e ) {
            throw e;
        }    
    
        return responseData;
    }

    /**
	 * <pre>[FM]임대R계약검색</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-08-11 17:39:50
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CMPHS_CNTRT_NM [계약명]
	 *	- field : EQP_MDL_CD [모델코드]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RENTR_CNRTR_LIST
	 *		- field : CMPHS_CNTRT_NM [계약명]
	 *		- field : CMPHS_CNTRT_STA_DT [계약시작일자]
	 *		- field : CMPHS_CNTRT_END_DT [계약종료일자]
	 *		- field : CNTRT_NO [계약번호]
	 *		- field : EQP_MDL_CD [단말기코드]
	 *		- field : EQP_MDL_NM [단말기명]
	 *		- field : RENTAL_CNTRT_PRD [계약기간]
	 * </pre>
	 */
	public IDataSet fInqRentRCntrtLst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        try {
            // 1. DU lookup
            DPRRentalServCntrtMgmt dPRRentalServCntrtMgmt = (DPRRentalServCntrtMgmt) lookupDataUnit(DPRRentalServCntrtMgmt.class);
            // 2. 업무 DM호출
            responseData = dPRRentalServCntrtMgmt.dSInqRentRCntrtLst(requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        }
    
        return responseData;
    }
  
}
