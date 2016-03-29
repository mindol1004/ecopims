package dms.nr.nrsisbase.biz;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import fwk.common.CommonArea;
import fwk.utils.PagenateUtils;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.IResultMessage;
import nexcore.framework.core.data.Record;
import nexcore.framework.core.data.RecordSet;
import nexcore.framework.core.data.ResultMessage;
import nexcore.framework.core.data.xml.DataSetXmlTransformer;
import nexcore.framework.core.exception.BizRuntimeException;


/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [FU]보증보험료정산(가입)내역조회</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-10-13 16:33:22</li>
 * <li>작성자 : 장미진 (kuramotojin)</li>
 * </ul>
 *
 * @author 장미진 (kuramotojin)
 */
public class FNRInsuFeeMgmt extends fwk.base.FunctionUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public FNRInsuFeeMgmt(){
		super();
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-10-13 16:33:22
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqInsuScrbFeeInfoLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    IRecordSet rsCnt = null;
		IRecordSet listRs = null;
		int intTotalCnt = 0;

		try {
			// 1. DU lookup
			DNRInsuFeeMgmt dNRInsuFeeMgmt = (DNRInsuFeeMgmt) lookupDataUnit(DNRInsuFeeMgmt.class);

            // 2. TOTAL COUNT DM호출
            rsCnt = dNRInsuFeeMgmt.dSInsuScrbFeeSlipLstTotCnt(requestData, onlineCtx).getRecordSet("RS_SUM_LIST");
            if( rsCnt.getRecordCount() == 0 ){
                rsCnt.newRecord();
                IRecord record = rsCnt.getRecord(0);
                record.set(0, "0");
                record.set(1, "0");
            }
            intTotalCnt = Integer.parseInt(rsCnt.get(0,"TOTAL_CNT"));            
            PagenateUtils.setPagenatedParamsToDataSet(requestData);
            
            responseData.putRecordSet(rsCnt);
            
            // 3. LISTDM호출
            IDataSet dsSlip = dNRInsuFeeMgmt.dSInsuScrbFeeLstSlip(requestData, onlineCtx);
            listRs = dsSlip.getRecordSet("RS_SLIP_LIST");
            PagenateUtils.setPagenatedParamToRecordSet(listRs, requestData, intTotalCnt);
            
            responseData.putRecordSet(listRs);
            /*
			// 2. TOTAL COUNT DM호출
			dsCnt = dNRInsuFeeMgmt.dSInsuScrbFeeLstTotCnt(requestData, onlineCtx);
			IRecordSet sumRs = dsCnt.getRecordSet("RS_SUM_LIST");
			
			intTotalCnt = Integer.parseInt(sumRs.get(0,"TOTAL_CNT"));
			PagenateUtils.setPagenatedParamsToDataSet(requestData);
			
			// 3. LISTDM호출
			listds = dNRInsuFeeMgmt.dSInsuScrbFeeLstSlip(requestData, onlineCtx);
			listRs = listds.getRecordSet("RS_SLIP_LIST");
			*/

			/*
			//합계,타이틀 용 리스트
			responseData.putRecordSet("RS_SUM_LIST", sumRs);
			PagenateUtils.setPagenatedParamToRecordSet(sumRs, requestData, intTotalCnt);
			
			//전표처리용 리스트
			responseData.putRecordSet("RS_SLIP_LIST", listRs);
			PagenateUtils.setPagenatedParamToRecordSet(listRs, requestData, intTotalCnt);
			*/		
			//상세처리용리스트 
			/*           
			mainds = dNRInsuFeeMgmt.dSInsuScrbFeeLstDtl(requestData, onlineCtx);
            mainRs = mainds.getRecordSet("RS_INSU_FEE_LIST");
            
			responseData.putRecordSet("RS_INSU_FEE_LIST",mainRs);
			PagenateUtils.setPagenatedParamToRecordSet(mainRs, requestData, intTotalCnt);
			*/
			
	    } catch ( BizRuntimeException e ) {
	        throw e; //시스템 오류가 발생하였습니다.
	    }
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-10-13 16:33:22
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInsuScrbFeeAllExcel(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    try {
			// 1. DU lookup
			DNRInsuFeeMgmt dNRInsuFeeMgmt = (DNRInsuFeeMgmt) lookupDataUnit(DNRInsuFeeMgmt.class);

			// 2. LISTDM호출
			responseData = dNRInsuFeeMgmt.dSInsuScrbFeeAllExcel(requestData, onlineCtx);
			
	    } catch ( BizRuntimeException e ) {
	        throw e; //시스템 오류가 발생하였습니다.
	    }
	
	    return responseData;
	}

    /**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2015-10-13 16:33:22
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqInsuTermFeeAllExcel(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 처리 결과값을 responseData에 넣어서 리턴하십시요 
        try {
            // 1. DU lookup
            DNRInsuFeeMgmt dNRInsuFeeMgmt = (DNRInsuFeeMgmt) lookupDataUnit(DNRInsuFeeMgmt.class);

            // 2. LISTDM호출
            responseData = dNRInsuFeeMgmt.dSInsuTermFeeAllExcel(requestData, onlineCtx);
            
        } catch ( BizRuntimeException e ) {
            throw e; //시스템 오류가 발생하였습니다.
        }
        
        return responseData;
    }

    /**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2015-10-13 16:33:22
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqInsuTermFeeLst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IRecordSet listRs = null;
        int intTotalCnt = 0;
    
        // 처리 결과값을 responseData에 넣어서 리턴하십시요 
        try {
            // 1. DU lookup
            DNRInsuFeeMgmt dNRInsuFeeMgmt = (DNRInsuFeeMgmt) lookupDataUnit(DNRInsuFeeMgmt.class);

            IRecordSet rsCnt = dNRInsuFeeMgmt.dSInsuTermFeeSlipLstTotCnt(requestData, onlineCtx).getRecordSet("RS_SUM_LIST");
            if( rsCnt.getRecordCount() == 0 ){
                rsCnt.newRecord();
                IRecord record = rsCnt.getRecord(0);
                record.set(0, "0");
                record.set(1, "0");
            }
            intTotalCnt = Integer.parseInt(rsCnt.get(0,"TOTAL_CNT"));
            PagenateUtils.setPagenatedParamsToDataSet(requestData);
            
            responseData.putRecordSet(rsCnt);
            
            // 3. LISTDM호출
            IDataSet dsSlip = dNRInsuFeeMgmt.dSInsuTermFeeLstSlip(requestData, onlineCtx);
            listRs = dsSlip.getRecordSet("RS_SLIP_LIST");
            PagenateUtils.setPagenatedParamToRecordSet(listRs, requestData, intTotalCnt);
            
            responseData.putRecordSet(listRs);
            
            /*
            mainds = dNRInsuFeeMgmt.dSInsuScrbFeeLstDtl(requestData, onlineCtx);
            mainRs = mainds.getRecordSet("RS_INSU_FEE_LIST");
            

            //합계,타이틀 용 리스트
            responseData.putRecordSet("RS_SUM_LIST", sumRs);
            PagenateUtils.setPagenatedParamToRecordSet(sumRs, requestData, intTotalCnt);
            
            //전표처리용 리스트
            responseData.putRecordSet("RS_SLIP_LIST", listRs);
            PagenateUtils.setPagenatedParamToRecordSet(listRs, requestData, intTotalCnt);
            
            //상세처리용리스트 
            responseData.putRecordSet("RS_INSU_FEE_LIST",mainRs);
            PagenateUtils.setPagenatedParamToRecordSet(mainRs, requestData, intTotalCnt);
            */
        } catch ( BizRuntimeException e ) {
            throw e; //시스템 오류가 발생하였습니다.
        }
        
        return responseData;
    }

    /**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2015-10-13 16:33:22
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqInsuScrbFeeInfoDtlLst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IRecordSet listRs = null;
        int intTotalCnt = 0;

        try {
            // 1. DU lookup
            DNRInsuFeeMgmt dNRInsuFeeMgmt = (DNRInsuFeeMgmt) lookupDataUnit(DNRInsuFeeMgmt.class);

            // 2. TOTAL COUNT DM호출
            IRecordSet rsCnt = dNRInsuFeeMgmt.dSInsuScrbFeeSlipDtlLstTotCnt(requestData, onlineCtx).getRecordSet("RS_SUM_DTL_LIST");
            if( rsCnt.getRecordCount() == 0 ){
                rsCnt.newRecord();
                IRecord record = rsCnt.getRecord(0);
                record.set(0, "0");
                record.set(1, "0");
                record.set(2, "0");
            }
            intTotalCnt = Integer.parseInt(rsCnt.get(0,"TOTAL_CNT"));
            PagenateUtils.setPagenatedParamsToDataSet(requestData);
            
            responseData.putRecordSet(rsCnt);
            
            // 3. LISTDM호출
            IDataSet dsSlip = dNRInsuFeeMgmt.dSInsuScrbFeeLstDtl(requestData, onlineCtx);
            listRs = dsSlip.getRecordSet("RS_INSU_FEE_LIST");
            PagenateUtils.setPagenatedParamToRecordSet(listRs, requestData, intTotalCnt);
            
            responseData.putRecordSet(listRs);
        
        } catch ( BizRuntimeException e ) {
            throw e; //시스템 오류가 발생하였습니다.
        }        
        return responseData;
    }

    /**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2015-10-13 16:33:22
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : FEE_YM [정산년월]
	 *	- field : INSURE_MGMT_NO [보증보험관리번호]
	 *	- field : XCL_ITM_CD [정산항목코드]
	 *	- field : TS [차수]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqInsuTermFeeDtlLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        IRecordSet listRs = null;
        int intTotalCnt = 0;

        try {
            // 1. DU lookup
            DNRInsuFeeMgmt dNRInsuFeeMgmt = (DNRInsuFeeMgmt) lookupDataUnit(DNRInsuFeeMgmt.class);

            // 2. TOTAL COUNT DM호출
            IRecordSet rsCnt = dNRInsuFeeMgmt.dSInsuTermFeeDtlLstTotCnt(requestData, onlineCtx).getRecordSet("RS_SUM_DTL_LIST");
            if( rsCnt.getRecordCount() == 0 ){
                rsCnt.newRecord();
                IRecord record = rsCnt.getRecord(0);
                record.set(0, "0");
                record.set(1, "0");
                record.set(2, "0");
            }
            intTotalCnt = Integer.parseInt(rsCnt.get(0,"TOTAL_CNT"));
            PagenateUtils.setPagenatedParamsToDataSet(requestData);
            
            responseData.putRecordSet(rsCnt);
            
            // 3. LISTDM호출
            IDataSet dsSlip = dNRInsuFeeMgmt.dSInsuTermFeeLstDtl(requestData, onlineCtx);
            listRs = dsSlip.getRecordSet("RS_INSU_FEE_LIST");
            PagenateUtils.setPagenatedParamToRecordSet(listRs, requestData, intTotalCnt);
            
            responseData.putRecordSet(listRs);
        
        } catch ( BizRuntimeException e ) {
            throw e; //시스템 오류가 발생하였습니다.
        }        
        return responseData;
    }

    /**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2015-10-22 10:38:44
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fReSum(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        CommonArea ca = getCommonArea(onlineCtx);
    
        // 처리 결과값을 responseData에 넣어서 리턴하십시요 
        try {           
            ByteArrayOutputStream bout = new ByteArrayOutputStream(1024);
            DataSetXmlTransformer.dataSetToXml(requestData, bout, "UTF-8");
            String dsXml = bout.toString("UTF-8");

            // call on-demand batch job
            HashMap params = new HashMap<String,String>();
            params.put("TASK_ID", "XCR016");
            params.put("TASK_NM", "보증보험료정산");
            params.put("LGIN_ID", onlineCtx.getUserInfo().getLoginId());
            params.put("USER_NO", ca.getUserNo());
            params.put("XCL_ITM_CD", requestData.getField("XCL_ITM_CD"));
            params.put("PROC_DT", requestData.getField("FEE_YM"));
            params.put("COMPONENTNAME_LOCAL_ONLY", "dms.nr.XCR016");
            params.put("ONDEMAND_DATASET", dsXml);
            
            String jobExecutionId = callBatchJob("XCR016", params, onlineCtx);
            waitBatchJobEnd(jobExecutionId, 10000);
            int result = getJobReturnCode(jobExecutionId);
            
            if(result == -1) throw new BizRuntimeException("DMS00009"); // 시스템 오류가 발생하였습니다.
                
            
        }catch ( BizRuntimeException e ) {
            throw e;
        }catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }
    
        return responseData;
	}
  
}