package dms.nr.nrsxmbase.biz;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.xml.DataSetXmlTransformer;
import nexcore.framework.core.exception.BizRuntimeException;

import org.apache.commons.logging.Log;

import fwk.common.CommonArea;
import fwk.utils.PagenateUtils;


/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [FU]대리점회수수수료정산관리</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-08-05 17:13:54</li>
 * <li>작성자 : 김혁범 (kezmain1)</li>
 * </ul>
 *
 * @author 김혁범 (kezmain1)
 */
public class FNRAgnClctComMgnt extends fwk.base.FunctionUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public FNRAgnClctComMgnt(){
		super();
	}

	/**
	 *대리점회수수수료정산정보조회
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-08-05 17:14:34
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqAgnClctLst(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
		  IDataSet dsCnt = new DataSet();
		  IRecordSet usrListRs = null;
		  IRecordSet rsList = null;
		  int intTotalCnt = 0;
		  
		  try {
				// 1. DU lookup
			  DNRAgnClctComMgnt   dNRAgnClctComMgnt = (DNRAgnClctComMgnt) lookupDataUnit(DNRAgnClctComMgnt.class);
				
				// 2. TOTAL COUNT DM호출
				dsCnt = dNRAgnClctComMgnt.dSAgnClctTotCnt(requestData, onlineCtx);
				intTotalCnt = Integer.parseInt(dsCnt.getField("TOTAL_CNT"));
				PagenateUtils.setPagenatedParamsToDataSet(requestData);

				// 3. LISTDM호출
				responseData = dNRAgnClctComMgnt.dSAgnClctLstPaging(requestData, onlineCtx);
				usrListRs = responseData.getRecordSet("RS_SLIP_LIST");
				PagenateUtils.setPagenatedParamToRecordSet(usrListRs, requestData, intTotalCnt);
				
				// 2. TOTAL COUNT DM호출
			    IDataSet dsRtn = dNRAgnClctComMgnt.dSAgnClctComSumTotCnt(requestData, onlineCtx);
			    rsList = dsRtn.getRecordSet("RS_SUM_LIST");
			    PagenateUtils.setPagenatedParamToRecordSet(rsList, requestData, intTotalCnt);
			    
			    responseData.putRecordSet("RS_SLIP_LIST", usrListRs);
			    responseData.putRecordSet("RS_SUM_LIST", rsList); //현재월총건수
			    
				
			} catch ( BizRuntimeException e ) {
				throw e; //시스템 오류가 발생하였습니다.
			}
	
	    return responseData;
	}

	/**
	 *대리점회수수수료정산정보상세
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-08-06 15:28:17
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqAgnClctDtlLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    IRecordSet rsList = null;
	    IRecordSet rsList2 = null;
	
	    try {
			// 1. DU lookup
	    	DNRAgnClctComMgnt dNRAgnClctComMgnt = (DNRAgnClctComMgnt) lookupDataUnit(DNRAgnClctComMgnt.class);

			// 2. LISTDM호출
	    	responseData = dNRAgnClctComMgnt.dSAgnClctLstDtlLst(requestData, onlineCtx);
	    	rsList = responseData.getRecordSet("RS_AGN_CLCT_DTL_LIST");
			
			 // 2. TOTAL COUNT DM호출
		    IDataSet dsRtn = dNRAgnClctComMgnt.dSAgnClctComDtlSumTotCnt(requestData, onlineCtx);
		    rsList2 = dsRtn.getRecordSet("RS_SUM_LIST"); //대리점회수수수료 매입 총금액,총건수
		    
		    responseData.putRecordSet("RS_AGN_CLCT_DTL_LIST", rsList); //대리점회수수수료상세리스트 조회
		    responseData.putRecordSet("RS_SUM_LIST", rsList2); //대리점회수수수료상세 총금액,총건수
			
		} catch ( BizRuntimeException e ) {
			throw e; //시스템 오류가 발생하였습니다.
		}
	
	    return responseData;
	}

    /**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2015-09-24 09:17:47
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fSaveAgnClctReBatch(IDataSet requestData, IOnlineContext onlineCtx) {
	       IDataSet responseData = new DataSet();
	        Log log = getLog(onlineCtx);
	        CommonArea ca = getCommonArea(onlineCtx);
	    
	        try {           
	                
	                ByteArrayOutputStream bout = new ByteArrayOutputStream(1024);
	                DataSetXmlTransformer.dataSetToXml(requestData, bout, "UTF-8");
	                String dsXml = bout.toString("UTF-8");
	            
	                // call on-demand batch job
	                HashMap params = new HashMap<String,String>();
	                params.put("TASK_ID", "XCR008");
	                params.put("TASK_NM", "대리점회수수수료등록");
	                params.put("LGIN_ID", onlineCtx.getUserInfo().getLoginId());
	                params.put("USER_NO", ca.getUserNo());
	                params.put("PROC_DT", requestData.getField("XCL_YM")+"01");
	                params.put("COMPONENTNAME_LOCAL_ONLY", "dms.nr.XCR008");                
	                params.put("ONDEMAND_DATASET", dsXml);
	                
	                log.debug("(((((((((((((((((((((((((((((((fInqSaleReBatch() params :"+ params);
	                
	                String jobExecutionId = callBatchJob("XCR008", params, onlineCtx);
	                waitBatchJobEnd(jobExecutionId, 10000);
	                int result = getJobReturnCode(jobExecutionId);
	                
	                
	                log.debug("(((((((((((((((((((((((((((((((fInqSaleReBatch() result :"+ result);
	            
	                if(result == -1) throw new BizRuntimeException("DMS00009"); // 시스템 오류가 발생하였습니다.
	                
	        } catch ( BizRuntimeException e ) {
	            throw e; //시스템 오류가 발생하였습니다.
	        
	        } catch (UnsupportedEncodingException ee) {
	            throw new BizRuntimeException("DMS00009",ee); //시스템 오류가 발생하였습니다.
	        }
	    
	        return responseData;
    }

    /**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2015-08-05 17:13:54
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fSaveAgnClctSlip(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        
        CommonArea ca = getCommonArea(onlineCtx);
        Log log = getLog(onlineCtx);
        IRecordSet rs = requestData.getRecordSet("RS_SLIP_LIST");
        IDataSet paramData = new DataSet();

        try {
            // 1. 입력 RS설정
            requestData.putField("USERNO", ca.getUserNo());
            requestData.putField("SLIP_TYPE", "RETURN_COMMISSION");
            
            DNRAgnClctComMgnt dNRAgnClctComMgnt = (DNRAgnClctComMgnt) lookupDataUnit(DNRAgnClctComMgnt.class);
           
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
                
                log.debug("(((((((((((((((((((((((((((((((fSaveAgnClctSlip() params :"+ params);
                
                String jobExecutionId = callBatchJob("EPR010", params, onlineCtx);
                waitBatchJobEnd(jobExecutionId, 10000);
                int result = getJobReturnCode(jobExecutionId);
                
                
                log.debug("(((((((((((((((((((((((((((((((fSaveAgnClctSlip() result :"+ result);
            
                if(result == -1) throw new BizRuntimeException("DMS00009"); // 시스템 오류가 발
            //}


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
	 * @author 안진갑 (bella21cjk)
	 * @since 2015-09-24 09:20:57
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fSaveAgnClctSlipDel(IDataSet requestData, IOnlineContext onlineCtx) {
	     Log log = getLog(onlineCtx);
	        IDataSet responseData = new DataSet();
	        CommonArea ca = getCommonArea(onlineCtx);
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
	                log.info("(((((((((((((((((((((((((((((((fSaveAgnClctSlipDel() params :"+ params);
	                String jobExecutionId = callBatchJob("EPR011", params, onlineCtx);
	                waitBatchJobEnd(jobExecutionId, 10000);
	                int result = getJobReturnCode(jobExecutionId);
	                
	                
	                log.info("(((((((((((((((((((((((((((((((fSaveAgnClctSlipDel() result :"+ result);

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
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2015-09-24 10:02:55
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqAgnClctAllExcel(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        
        try {
              // 1. DU lookup
            DNRAgnClctComMgnt   dNRAgnClctComMgnt = (DNRAgnClctComMgnt) lookupDataUnit(DNRAgnClctComMgnt.class);

              // 3. LISTDM호출
            responseData = dNRAgnClctComMgnt.dSAgnClctAllExcel(requestData, onlineCtx);
              
          } catch ( BizRuntimeException e ) {
              throw e; //시스템 오류가 발생하였습니다.
          }
        return responseData;
    }

	/**
	 *
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-08-05 17:13:54
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : XCL_YM [필드1]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqAgnClctSlipStSearch(IDataSet requestData, IOnlineContext onlineCtx) {
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
		    params.put("PROC_DT", requestData.getField("XCL_YM")+"01");
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
  
}
