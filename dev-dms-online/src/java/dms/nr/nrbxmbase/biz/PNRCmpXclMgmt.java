package dms.nr.nrbxmbase.biz;

import java.util.Map;

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
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [PU]손해배상금정산관리</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-08-10 13:36:53</li>
 * <li>작성자 : 정동현 (jjddhh123)</li>
 * </ul>
 *
 * @author 정동현 (jjddhh123)
 */
public class PNRCmpXclMgmt extends fwk.base.ProcessUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public PNRCmpXclMgmt(){
		super();
	}

    /**
	 * <pre>손해배상금정산정보조회</pre>
	 *
	 * @author 정동현 (jjddhh123)
	 * @since 2015-08-10 13:36:53
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : EQP_MDL_CD [단말기모델코드]
	 *	- field : EQP_MDL_NM [단말기모델명]
	 *	- field : DMG_STL_INFO_CL [손해배상금유형]
	 *	- field : SVC_MGMT_NO [서비스관리번호]
	 *	- field : YDATE [년월]
	 *	- field : XCL_DEDC_YN [정산공제여부]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_CMP_XCL_LST
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : EQP_MDL_NM [단말기모델명]
	 *		- field : DMS_PEN_AMT [DMS금액]
	 *		- field : UKEY_PEN_AMT [UKEY금액]
	 *		- field : XCL_UKEY_INV_AMT [차액]
	 *		- field : PEN_RECV_AMT [수납액]
	 *		- field : OP_PROC_DT [업무처리일자]
	 *		- field : DMG_STL_INFO_CL [손해배상금정책유형]
	 *		- field : XCL_ITM_CD [손해배상금유형]
	 *		- field : UN_DIV_DMG [미납액]
	 *		- field : SVC_MGMT_NO [서비스관리번호]
	 *		- field : TS [차수]
	 *	- record : RS_CMP_XCL_SYS_LST
	 *		- field : SVC_MGMT_NO [서비스관리번호]
	 *		- field : CNTRT_NO [계약번호]
	 *		- field : ASSET_NO [자산번호]
	 *		- field : XCL_ITM_CD [손해배상금유형]
	 *		- field : RENTAL_CNTRT_STA_DT [계약시작일]
	 *		- field : RENTAL_CNTRT_END_DT [계약종료일]
	 *		- field : END_DT [해지일자]
	 *		- field : XCL_SLIP_NO [전표번호]
	 *		- field : DMS_PEN_AMT [렌탈금액]
	 *		- field : UKEY_PEN_AMT [청구금액]
	 *		- field : PEN_RECV_AMT [수납금액]
	 *		- field : DIV_PEN [차액]
	 *		- field : UN_DIV_PEN [미납액]
	 *		- field : SLIP_ST_CD [전표상태코드]
	 *		- field : XCL_DEDC_YN [공제여부]
	 *		- field : TS [차수]
	 *	- record : RS_SUM_LIST
	 *		- field : M_CNT [건수]
	 *		- field : M_PRC [금액]
	 *	- record : RS_SUM_DTL_LIST
	 *		- field : S_CNT [건수]
	 *		- field : S_PRC [금액]
	 *	- record : RS_CMP_XCL_STL_PSNL_CORP_LIST
	 *		- field : DEALCO_BLICENS_NO [사업자번호]
	 *		- field : NEW_CNTRTR_NM [법인명]
	 *		- field : CUST_TYP [고객유형]
	 *		- field : AMT [정산금액]
	 *		- field : GUBUN [구분]
	 *		- field : SELEC [개인,법인]
	 *		- field : SLIP_NO [전표번호]
	 *		- field : SLIP_ST_CD [전표상태]
	 *		- field : YYYY [년월]
	 *		- field : DMG_STL_INFO_CL [손해배상금정책유형]
	 *		- field : BOND_DEALCO_FISCL_ST_CD [필드1]
	 *		- field : TS [차수]
	 * </pre>
	 */
	public IDataSet pInqCmpXclLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        
        try {
            // 1. FM 호출
            responseData = callSharedBizComponentByDirect("nr.NRSXMBase", "fInqCmpXclLst", requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }
        // 2. 결과값 리턴
        responseData.setOkResultMessage("DMS00001", null); // 정상 조회되었습니다.
    
        return responseData;
    }

    /**
	 * <pre>손해배상금정산정보재집계</pre>
	 *
	 * @author 문재웅 (jaiwoong)
	 * @since 2015-08-10 13:36:53
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : YDATE [년+월]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet pInqCmpXcReBatch(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        
        try {
            // 1. FM 호출
            responseData = callSharedBizComponentByDirect("nr.NRSXMBase", "fInqCmpXcReBatch", requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }
        // 2. 결과값 리턴
        responseData.setOkResultMessage("DMS00001", null); // 정상 조회되었습니다.
        
        return responseData;
    }

    /**
	 * <pre>손해배상금정산전표발행</pre>
	 *
	 * @author 문재웅 (jaiwoong)
	 * @since 2015-08-10 13:36:53
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_SLIP_LIST
	 *		- field : DEALCO_BLICENS_NO [사업자번호]
	 *		- field : NEW_CNTRTR_NM [법인명]
	 *		- field : SLIP_TYPE [전표타입(손해배상금)]
	 *		- field : AMT [정산금액]
	 *		- field : GUBUN [구분]
	 *		- field : SELEC [필드6]
	 *		- field : SLIP_NO [전표번호]
	 *		- field : SLIP_ST_CD [전표상태]
	 *		- field : YYYYMM [년월]
	 *		- field : DMG_STL_INFO_CL [손해배상금유형]
	 *		- field : BOND_DEALCO_FISCL_ST_CD [필드1]
	 *		- field : XCL_DEALCO_CD [대리점코드]
	 *		- field : TS [차수]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet pSaveCmpXclSlip(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        
        try {
            // 1. FM 호출
            responseData = callSharedBizComponentByDirect("nr.NRSXMBase", "fSaveCmpXclSlip", requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }
        // 2. 결과값 리턴
        responseData.setOkResultMessage("DMS00001", null); // 정상 조회되었습니다.
    
        return responseData;
    }

    /**
	 * <pre>손해배상금정산전표삭제</pre>
	 *
	 * @author 문재웅 (jaiwoong)
	 * @since 2015-08-10 13:36:53
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_SLIP_LIST
	 *		- field : DEALCO_BLICENS_NO [사업자번호]
	 *		- field : NEW_CNTRTR_NM [고객명]
	 *		- field : SLIP_TYPE [전표타입(손해배상금)]
	 *		- field : AMT [정산금액]
	 *		- field : GUBUN [구분]
	 *		- field : SELEC [필드6]
	 *		- field : SLIP_NO [전표번호]
	 *		- field : SLIP_ST_CD [전표상태]
	 *		- field : YYYYMM [년월]
	 *		- field : YYYY [년]
	 *		- field : DMG_STL_INFO_CL [손해배상금유형]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet pSaveCmpXclSlipDel(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        
        try {
            // 1. FM 호출
            responseData = callSharedBizComponentByDirect("nr.NRSXMBase", "fSaveCmpXclSlipDel", requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }
        // 3. 결과값 리턴
        responseData.setOkResultMessage("DMS00001", null); // 정상 조회되었습니다.
    
        return responseData;    }

	/**
	 * <pre>손해배상금정산정보전체엑셀</pre>
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-08-10 13:36:53
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : YDATE [년월]
	 *	- field : DMG_STL_INFO_CL [손해배상금유형]
	 *	- field : XCL_DEDC_YN [공제여부]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet pInqCmpXclAllExcel(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    try {
            // 1. FM 호출
            responseData = callSharedBizComponentByDirect("nr.NRSXMBase", "fInqCmpXclAllExcel", requestData, onlineCtx);
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
	 * <pre>손해배상금정산전표상태재조회</pre>
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-08-10 13:36:53
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : YDATE [필드1]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet pCmpXclMgmtSlipStSearch(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    try {
            // 1. FM 호출
            responseData = callSharedBizComponentByDirect("nr.NRSXMBase", "fCmpXclMgmtSlipStSearch", requestData, onlineCtx);
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
	 * <pre>손해배상금정산체크항목삭제</pre>
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-08-10 13:36:53
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_DEL_LIST
	 *		- field : CNTRT_NO [계약번호]
	 *		- field : ASSET_NO [자산번호]
	 *		- field : XCL_ITM_CD [필드3]
	 *		- field : UKEY_PEN_AMT [필드4]
	 *		- field : CHK [필드1]
	 *		- field : XCL_DEDC_YN [공제여부]
	 *		- field : YYYYMM [정산년월]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet pDelCmpXclMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    try {
            // 1. FM 호출
            responseData = callSharedBizComponentByDirect("nr.NRSXMBase", "fDelCmpXclMgmt", requestData, onlineCtx);
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
	 * @author 안진갑 (bella21cjk)
	 * @since 2015-08-10 13:36:53
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : YYYYMM [기준년월]
	 *	- field : DMG_STL_INFO_CL [정산구분코드]
	 *	- field : XCL_DEDC_YN [제외여부]
	 *	- field : DEALCO_BLICENS_NO [사업자번호]
	 *	- field : TS [차수]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet pInqCmpXclDtlLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
        try {
            // 1. FM 호출
            responseData = callSharedBizComponentByDirect("nr.NRSXMBase", "fInqCmpXclDtlLst", requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }
        // 2. 결과값 리턴
        responseData.setOkResultMessage("DMS00001", null); // 정상 조회되었습니다.
	
	    return responseData;
	}
  
}
