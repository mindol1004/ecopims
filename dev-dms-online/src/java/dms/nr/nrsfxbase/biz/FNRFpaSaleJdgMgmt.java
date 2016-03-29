package dms.nr.nrsfxbase.biz;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Properties;

import fwk.common.CommonArea;
import fwk.utils.PagenateUtils;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.xml.DataSetXmlTransformer;
import nexcore.framework.core.exception.BizRuntimeException;
import nexcore.framework.core.util.StringUtils;


/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [FU]FPA판매감정관리</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2016-01-26 11:22:52</li>
 * <li>작성자 : 안진갑 (bella21cjk)</li>
 * </ul>
 *
 * @author 안진갑 (bella21cjk)
 */
public class FNRFpaSaleJdgMgmt extends fwk.base.FunctionUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public FNRFpaSaleJdgMgmt(){
		super();
	}

	/**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2016-01-26 11:22:52
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqSaleJdgLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
		IDataSet reqDs = (IDataSet) requestData.clone();//원거래의 DataSet의 훼손을 막기 위한 Clone
	
	    try {
            // 1. DU lookup
	        DNRFpaSaleJdgMgmt dNRFpaSaleJdgMgmt = (DNRFpaSaleJdgMgmt) lookupDataUnit(DNRFpaSaleJdgMgmt.class);

			// 2. TOTAL COUNT DM호출
			int totalCnt = dNRFpaSaleJdgMgmt.dSSaleJdgTotCnt(reqDs, onlineCtx).getIntField("TOTAL_CNT");
			PagenateUtils.setPagenatedParamsToDataSet(reqDs);
			
            // 3. LISTDM호출
            responseData = dNRFpaSaleJdgMgmt.dSSaleJdgLstPaging(reqDs, onlineCtx);
			IRecordSet rs = responseData.getRecordSet("RS_JDG_LST");
			PagenateUtils.setPagenatedParamToRecordSet(rs, reqDs, totalCnt);            
        } catch ( BizRuntimeException e ) {
            throw e; //시스템 오류가 발생하였습니다.
        } 
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2016-01-26 11:26:28
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqSaleJdgDtlLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    try {
            // 1. DU lookup
	        DNRFpaSaleJdgMgmt dNRFpaSaleJdgMgmt = (DNRFpaSaleJdgMgmt) lookupDataUnit(DNRFpaSaleJdgMgmt.class);

            // 2. LISTDM호출
            responseData = dNRFpaSaleJdgMgmt.dSSaleJdgDtlLst(requestData, onlineCtx);
            
        } catch ( BizRuntimeException e ) {
            throw e; //시스템 오류가 발생하였습니다.
        } 
	
	    return responseData;
	}

    /**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2016-01-26 11:22:52
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fSaveSaleJdgDtl(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        CommonArea ca = getCommonArea(onlineCtx);
    
        try {
            // 1. DU lookup
            DNRFpaSaleJdgMgmt dNRFpaSaleJdgMgmt = (DNRFpaSaleJdgMgmt) lookupDataUnit(DNRFpaSaleJdgMgmt.class);

            // 2.FPA잔존가 확인
            int remCnt = dNRFpaSaleJdgMgmt.dSEqpRemPrcCnt(requestData, onlineCtx).getIntField("REM_COUNT");
            if( remCnt == 0 ){
            	throw new BizRuntimeException("DMS00174");	
            }
            
            requestData.putField("USER_NO", ca.getUserNo());
            // 3. LISTDM호출
            dNRFpaSaleJdgMgmt.dISaveSaleJdgDtl(requestData, onlineCtx);
            
            // 4. 재고상태업데이트(판매감정완료)
            dNRFpaSaleJdgMgmt.dUEqpAssetSt(requestData, onlineCtx);
            
            // 5. interface 호출
            responseData = dNRFpaSaleJdgMgmt.dSIFPAEqpInfo01(requestData, onlineCtx);
            
            // 6.판매감정후 1차 이관
            responseData = callSharedBizComponentByDirect("ep.EPSFEBase", "fIFPAEqpRgst", responseData, onlineCtx);
            
            if( StringUtils.isNotEmpty(responseData.getField("RESULT")) ){
            	throw new BizRuntimeException("XYZE0000", new String[]{"인터페이스 전송"});
            }            
        } catch ( BizRuntimeException e ) {
            throw e; //시스템 오류가 발생하였습니다.
        } 
    
        return responseData;
    }

    /**
	 *
	 *
	 * @author 진병수 (greatjin)
	 * @since 2016-02-15 16:18:18
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fSaveEqpBkagAmt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        CommonArea ca = getCommonArea(onlineCtx);
        
        try {
            // 1. DU lookup
            DNRFpaSaleJdgMgmt dNRFpaSaleJdgMgmt = (DNRFpaSaleJdgMgmt) lookupDataUnit(DNRFpaSaleJdgMgmt.class);

            requestData.putField("USER_NO", ca.getUserNo());
            // 2. LISTDM호출
            responseData = dNRFpaSaleJdgMgmt.dISaveEqpBkagAmt(requestData, onlineCtx);
            
        } catch ( BizRuntimeException e ) {
            throw e; //시스템 오류가 발생하였습니다.
        } 
    
        return responseData;
    }

	/**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2016-02-26 18:15:02
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fSaveFpaStock(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
        CommonArea ca = getCommonArea(onlineCtx);
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream(1024);
            DataSetXmlTransformer.dataSetToXml(requestData, bout, "UTF-8");
            String dsXml = bout.toString("UTF-8");
    
            // call on-demand batch job
            HashMap params = new HashMap<String,String>();
            params.put("TASK_ID", "DBR031");
            params.put("TASK_NM", "자산재고이동");
            params.put("LGIN_ID", onlineCtx.getUserInfo().getLoginId());
            params.put("USER_NO", ca.getUserNo());
            params.put("FPA_DSPSL_DT", requestData.getField("FPA_DSPSL_DT"));    //매각년월
            params.put("COMPONENTNAME_LOCAL_ONLY", "dms.nr.DBR031");
            params.put("ONDEMAND_DATASET", dsXml);
            String jobExecutionId = callBatchJob("DBR031", params, onlineCtx);
            
            waitBatchJobEnd(jobExecutionId, 30000);
            	
            int result = getJobReturnCode(jobExecutionId);                
            if(result == -1){
            	Properties returnValue = getBatchJobResultValue(jobExecutionId);
            	String msgId = returnValue.getProperty("msgId");
            	String msgStr = returnValue.getProperty("msgStr");
            	
                if( StringUtils.isNotEmpty(msgId) ){
                	if( StringUtils.isEmpty(msgStr) ){
                		throw new BizRuntimeException(msgId);
                	}else{
                		throw new BizRuntimeException(msgId, new String[]{msgStr});
                	}
                }else{
                	throw new BizRuntimeException("DMS00009"); // 시스템 오류가 발생하였습니다.
                }
            }
        
        } catch ( BizRuntimeException e ) { 
            throw e; 
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2016-03-02 14:36:11
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqFpaExcel(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    CommonArea ca = getCommonArea(onlineCtx);
	    
	    String pattern = ca.getTrnPtrnDvcd();
	    
	    try {
	    	DNRFpaSaleJdgMgmt dNRFpaSaleJdgMgmt = (DNRFpaSaleJdgMgmt) lookupDataUnit(DNRFpaSaleJdgMgmt.class);
	    	
		    if( StringUtils.equals(pattern, "MORE") ){ //가입
		    	responseData = dNRFpaSaleJdgMgmt.dSFpaMORExcel(requestData, onlineCtx);
		    }else if( StringUtils.equals(pattern, "MASR") ){ //교체
		    	responseData = dNRFpaSaleJdgMgmt.dSFpaMASRExcel(requestData, onlineCtx);
		    }else if( StringUtils.equals(pattern, "WARR") ){ //회수
		    	responseData = dNRFpaSaleJdgMgmt.dSFpaWARRExcel(requestData, onlineCtx);
		    }else if( StringUtils.equals(pattern, "SR") ){ //정산
		    	responseData = dNRFpaSaleJdgMgmt.dSFpaSRExcel(requestData, onlineCtx);
		    }
        } catch ( BizRuntimeException e ) {
            throw e; //시스템 오류가 발생하였습니다.
        } 
	    
	    return responseData;
	}
  
}
