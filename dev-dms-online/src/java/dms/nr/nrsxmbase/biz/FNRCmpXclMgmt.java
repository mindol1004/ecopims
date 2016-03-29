package dms.nr.nrsxmbase.biz;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.xml.DataSetXmlTransformer;
import nexcore.framework.core.exception.BizRuntimeException;

import org.apache.commons.logging.Log;

import fwk.common.CommonArea;
import fwk.utils.PagenateUtils;


/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [FU]손해배상금정상정보관리</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-08-10 14:00:50</li>
 * <li>작성자 : 정동현 (jjddhh123)</li>
 * </ul>
 *
 * @author 정동현 (jjddhh123)
 */
public class FNRCmpXclMgmt extends fwk.base.FunctionUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public FNRCmpXclMgmt(){
		super();
	}

    /**
	 * <pre>손해배상금정산정보조회</pre>
	 *
	 * @author 정동현 (jjddhh123)
	 * @since 2015-08-10 14:00:50
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : XCL_YM [정산년월]
	 *	- field : EQP_MDL_CD [단말기모델코드]
	 *	- field : EQP_MDL_NM [단말기모델명]
	 *	- field : EQP_JDG_RSLT_CD [손해배상금유형]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_CMP_XCL_LST
	 *		- field : EQP_MDL_CD [단말기모델코드]
	 *		- field : EQP_MDL_NM [단말기모델명]
	 *		- field : EQP_CMP_AMT_TOT [DMS금액]
	 *		- field : OP_PROC_DT [업무처리일자]
	 * </pre>
	 */
	public IDataSet fInqCmpXclLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();

        IDataSet reqDs = (IDataSet) requestData.clone();//원거래의 DataSet의 훼손을 막기 위한 Clone
        IRecordSet agnSellComLstRs = null;
        int intTotalCnt = 0;
        try {
                            
            // 1. DU lookup
            DNRCmpXclMgmt dNRCmpXclMgmt = (DNRCmpXclMgmt) lookupDataUnit(DNRCmpXclMgmt.class);
            
            // 3. LISTDM호출 손해배상금 개인,법인체크리스트
            responseData = dNRCmpXclMgmt.dSCmpXclPsnlCorpList(reqDs, onlineCtx);
            agnSellComLstRs = responseData.getRecordSet("RS_CMP_XCL_STL_PSNL_CORP_LIST");
            PagenateUtils.setPagenatedParamToRecordSet(agnSellComLstRs, reqDs, intTotalCnt);
			
			// 4. TOTAL COUNT DM호출
            IDataSet ds = dNRCmpXclMgmt.dSCmpXclLstTotCnt(requestData, onlineCtx);
            responseData.putRecordSet(ds.getRecordSet("RS_SUM_LIST"));
            
        } catch ( BizRuntimeException e ) {
            throw e; //시스템 오류가 발생하였습니다.
        }
    
        return responseData;
    }

    /**
	 * <pre>손해배상금정산정보재집계</pre>
	 *
	 * @author 문재웅 (jaiwoong)
	 * @since 2015-08-10 14:00:50
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : YDATE [년+월]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqCmpXcReBatch(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        Log log = getLog(onlineCtx);
        CommonArea ca = getCommonArea(onlineCtx);
    
        try {           
        
                ByteArrayOutputStream bout = new ByteArrayOutputStream(1024);
                DataSetXmlTransformer.dataSetToXml(requestData, bout, "UTF-8");
                String dsXml = bout.toString("UTF-8");
            
                // call on-demand batch job
                HashMap params = new HashMap<String,String>();
                params.put("TASK_ID", "XCR010");
                params.put("TASK_NM", "손해배상금정산");
                params.put("LGIN_ID", onlineCtx.getUserInfo().getLoginId());
                params.put("USER_NO", ca.getUserNo());
                params.put("PROC_DT", requestData.getField("YDATE")+"01");
                params.put("COMPONENTNAME_LOCAL_ONLY", "dms.nr.XCR010");                
                params.put("ONDEMAND_DATASET", dsXml);
                
                log.debug("(((((((((((((((((((((((((((((((fInqCmpXcReBatch() params :"+ params);
                
                String jobExecutionId = callBatchJob("XCR010", params, onlineCtx);
                waitBatchJobEnd(jobExecutionId, 10000);
                int result = getJobReturnCode(jobExecutionId);
                
                
                log.debug("(((((((((((((((((((((((((((((((fInqCmpXcReBatch() result :"+ result);
            
                if(result == -1) throw new BizRuntimeException("DMS00009"); // 시스템 오류가 발생하였습니다.
                
        } catch ( BizRuntimeException e ) {
            throw e; //시스템 오류가 발생하였습니다.
        
        } catch (UnsupportedEncodingException ee) {
            throw new BizRuntimeException("DMS00009",ee); //시스템 오류가 발생하였습니다.
        }
    
        return responseData;    
    }

    /**
	 * <pre>손해배상금정산전표발행</pre>
	 *
	 * @author 문재웅 (jaiwoong)
	 * @since 2015-08-10 14:00:50
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
	 *		- field : DMG_STL_INFO_CL [손해배상금정책유형]
	 *		- field : XCL_DEALCO_CD [필드1]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fSaveCmpXclSlip(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        CommonArea ca = getCommonArea(onlineCtx);
        Log log = getLog(onlineCtx);
        IDataSet paramData = new DataSet();
        // 1. DU lookup
        DNRCmpXclMgmt dNRCmpXclMgmt = (DNRCmpXclMgmt) lookupDataUnit(DNRCmpXclMgmt.class);
        IRecordSet rs = requestData.getRecordSet("RS_SLIP_LIST");
        
        IDataSet paramDs = new DataSet();
        Map paramMap = null;
        
        int intSum = 0;
        
        for(int i=0; i<rs.getRecordCount(); i++)
        {           
            paramMap = rs.getRecordMap(i);          
            paramDs.initField();
            if( "1".equals(paramMap.get("CHK") )) //체크인항목은 BOND테이블 합계업데이트
            {
                String ukeySum = (String)paramMap.get("AMT");
                paramMap.put("USERNO", ca.getUserNo());
                
                intSum += Integer.parseInt(ukeySum);
                
                paramMap.put("AMT", intSum);
                paramDs.putFieldMap(paramMap);
            } 
        }
        if(intSum > 0)
        {   
            responseData = dNRCmpXclMgmt.dUpdateXclBondSum(paramDs, onlineCtx);
        }
        
        
        boolean isOnline = false;

        try {
        	 	//DNRCmpXclMgmt dNRCmpXclMgmt = (DNRCmpXclMgmt) lookupDataUnit(DNRCmpXclMgmt.class);        	 	

	            requestData.putField("USERNO", ca.getUserNo());
	            requestData.putField("SLIP_TYPE", "NR_J");
	                  
                log.debug("((((((((((((((((((((((((((((((((fSaveCmpXclSlip() fSaveCmpXclSlip requestData:"+ requestData);
        		
                ByteArrayOutputStream bout = new ByteArrayOutputStream(1024);
                DataSetXmlTransformer.dataSetToXml(requestData, bout, "UTF-8");
                String dsXml = bout.toString("UTF-8");

                HashMap params = new HashMap<String,String>();
                params.put("TASK_ID", "EPR010");
                params.put("TASK_NM", "전표발행");
                params.put("LGIN_ID", onlineCtx.getUserInfo().getLoginId());
                params.put("USER_NO", ca.getUserNo());
                params.put("COMPONENTNAME_LOCAL_ONLY", "dms.inf.EPR010");               
                params.put("POST_SLIP_DATASET", dsXml);
                
                log.debug("(((((((((((((((((((((((((((((((fSaveCmpXclSlip() params :"+ params);
                
                String jobExecutionId = callBatchJob("EPR010", params, onlineCtx);
                waitBatchJobEnd(jobExecutionId, 10000);
                int result = getJobReturnCode(jobExecutionId);
                
                log.debug("(((((((((((((((((((((((((((((((fSaveCmpXclSlip() result :"+ result);
            
                if(result == -1) throw new BizRuntimeException("DMS00009"); // 시스템 오류가 발
        	// }

        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); //시스템 오류
        }
    
        return responseData;
    }

    /**
	 * <pre>손해배상금정산전표삭제</pre>
	 *
	 * @author 문재웅 (jaiwoong)
	 * @since 2015-08-10 14:00:50
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
	public IDataSet fSaveCmpXclSlipDel(IDataSet requestData, IOnlineContext onlineCtx) {
        Log log = getLog(onlineCtx);
        IDataSet responseData = new DataSet();
        CommonArea ca = getCommonArea(onlineCtx);
        boolean isOnline = false;
        IRecordSet rs = requestData.getRecordSet("RS_SLIP_LIST");
        
        try{
            
        	//for(int i=0; i<rs.getRecordCount(); i++){
        		
                ByteArrayOutputStream bout = new ByteArrayOutputStream(1024);
                DataSetXmlTransformer.dataSetToXml(requestData, bout, "UTF-8");
                String dsXml = bout.toString("UTF-8");

                // call on-demand batch job
                HashMap params = new HashMap<String,String>();
                params.put("TASK_ID", "EPR011");
                params.put("TASK_NM", "전표삭제");
                params.put("USER_NO", ca.getUserNo());
                params.put("COMPONENTNAME_LOCAL_ONLY", "dms.inf.EPR011");               
                params.put("POST_SLIP_DATASET", dsXml);
                log.info("(((((((((((((((((((((((((((((((fSaveCmpXclSlipDel() params :"+ params);
                String jobExecutionId = callBatchJob("EPR011", params, onlineCtx);
                waitBatchJobEnd(jobExecutionId, 10000);
                int result = getJobReturnCode(jobExecutionId);
                
                
                log.info("(((((((((((((((((((((((((((((((fSaveCmpXclSlipDel() result :"+ result);

                if(result == -1) throw new BizRuntimeException("DMS00009"); // 시스템 오류가 발생하였습니다
        	//}
        
        } catch(BizRuntimeException e){
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }
    
        return responseData;
    }

	/**
	 * <pre>손해배상금정산정보전체엑셀</pre>
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-08-10 14:00:50
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : YDATE [년월]
	 *	- field : DMG_STL_INFO_CL [손해배상금유형]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqCmpXclAllExcel(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1. DU lookup
	    DNRCmpXclMgmt dNRCmpXclMgmt = (DNRCmpXclMgmt) lookupDataUnit(DNRCmpXclMgmt.class);

        // 2. LISTDM호출
        responseData = dNRCmpXclMgmt.dSCmpXclAllExcel(requestData, onlineCtx);
	
	    return responseData;
	}

	/**
	 * <pre>손해배상금정산전표상태재조회</pre>
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-08-10 14:00:50
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : YDATE [필드1]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fCmpXclMgmtSlipStSearch(IDataSet requestData, IOnlineContext onlineCtx) {
		 IDataSet responseData = new DataSet();
		    CommonArea ca = getCommonArea(onlineCtx);
		    

		    try {	    	
	    		
			    ByteArrayOutputStream bout = new ByteArrayOutputStream(1024);
				DataSetXmlTransformer.dataSetToXml(requestData, bout, "UTF-8");
				String dsXml = bout.toString("UTF-8");
			
				// call on-demand batch job
				HashMap params = new HashMap<String,String>();
				params.put("TASK_ID", "EPR001");
			    params.put("TASK_NM", "전표상태재조회");
			    params.put("LGIN_ID", onlineCtx.getUserInfo().getLoginId());
			    params.put("USER_NO", ca.getUserNo());
			    params.put("PROC_DT", requestData.getField("YDATE")+"01");
			    params.put("SYNC_TYPE", "XD");
			    params.put("COMPONENTNAME_LOCAL_ONLY", "dms.nr.EPR001");				
				params.put("ONDEMAND_DATASET", dsXml);
				
				String jobExecutionId = callBatchJob("EPR001", params, onlineCtx);
			    waitBatchJobEnd(jobExecutionId, 10000);
			    int result = getJobReturnCode(jobExecutionId);
			    
			
			    if(result == -1) throw new BizRuntimeException("DMS00009"); // 시스템 오류가 발생하였습니다.
			    
		    } catch ( BizRuntimeException e ) {
		    	throw e; //시스템 오류가 발생하였습니다.
			
		    } catch (UnsupportedEncodingException ee) {
		    	throw new BizRuntimeException("DMS00009",ee); //시스템 오류가 발생하였습니다.
		    }
		    
	    return responseData;
	}

	/**
	 * <pre>손해배상금정산체크항목삭제</pre>
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-08-10 14:00:50
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fDelCmpXclMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1. DU lookup
	    DNRCmpXclMgmt dNRCmpXclMgmt = (DNRCmpXclMgmt) lookupDataUnit(DNRCmpXclMgmt.class);
	    
	    IRecordSet rs = requestData.getRecordSet("RS_DEL_LIST");
	    
	    IDataSet paramDs = new DataSet();
	    Map paramMap = null;
	    
	    int intSum = 0;
	    
	    for(int i=0; i< rs.getRecordCount(); i++)
	    {
	    	paramMap = rs.getRecordMap(i);
	    	paramDs.initField();
	    	
	    	if(!"1".equals(paramMap.get("CHK")))//체크해제인것들은 총합 저장
	    	{
	    		String ukeySum = (String)paramMap.get("UKEY_PEN_AMT");
	    		
	    		intSum += Integer.parseInt(ukeySum);
	    		
	    		paramMap.put("UKEY_PEN_AMT", intSum);
	    		paramDs.putFieldMap(paramMap);
	    	} else if("1".equals(paramMap.get("CHK"))){ //체크한애들은 기타정산테이블 공제여부 'Y'
	    		paramDs.putFieldMap(paramMap);
	    		responseData = dNRCmpXclMgmt.dUpdateYCmpXclMgmt(paramDs,onlineCtx);
	    	}
	    	
	    }
	    
	    if(intSum > 0){
	    	responseData = dNRCmpXclMgmt.dUpdateCmpXclMgmt(paramDs,onlineCtx);
	    }
	    
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2015-08-10 14:00:50
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqCmpXclDtlLst(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
		
        try {
                            
            // 1. DU lookup
            DNRCmpXclMgmt dNRCmpXclMgmt = (DNRCmpXclMgmt) lookupDataUnit(DNRCmpXclMgmt.class);

            //손해배상금정산정보 현재월 기준조회
			IDataSet dsRtn = dNRCmpXclMgmt.dSCmpXclSysLst(requestData, onlineCtx);
			responseData.putRecordSet(dsRtn.getRecordSet("RS_CMP_XCL_SYS_LST"));

            // 5. 현재월 TOTAL COUNT DM호출
            IDataSet ds2  = dNRCmpXclMgmt.dSCmpXclSysLstTotCnt(requestData, onlineCtx);
            responseData.putRecordSet(ds2.getRecordSet("RS_SUM_DTL_LIST"));
            
        } catch ( BizRuntimeException e ) {
            throw e; //시스템 오류가 발생하였습니다.
        }
	
	    return responseData;
	}
  
}