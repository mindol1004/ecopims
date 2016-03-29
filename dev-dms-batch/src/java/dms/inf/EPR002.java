package dms.inf;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import nexcore.framework.bat.IBatchContext;
import nexcore.framework.bat.base.AbsBatchComponent;
import nexcore.framework.bat.base.AbsRecordHandler;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.exception.BizRuntimeException;
import nexcore.framework.core.util.DateUtils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;

import dms.utils.SAPUtils;
import fwk.constants.SlipConstants;
import fwk.utils.HpcUtils;

/**
 * <ul>
 * <li>업무 그룹명 : dms/인터페이스</li>
 * <li>서브 업무명 : EPR002</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-11-27 13:28:47</li>
 * <li>작성자 : 진병수 (greatjin)</li>
 * </ul>
 */
public class EPR002 extends AbsBatchComponent {
    private Log log;
    private int processCnt = 0;
    private String taskNo = "";
    private int totCnt = 0;
    private String procFileName = "";
    private String userNo       = "";
    
    public EPR002() {
        super();
    }

    /**
     * 배치 전처리 메소드. 
     * 여기서 Exception 발생시 execute() 메소드는 실행되지 않고, afterExecute() 는 실행됨
     * <?xml version="1.0" encoding="UTF-8"?><dataset>
<page type="string">1</page>
<page_size type="string">50</page_size>
<RS_SLIP_LIST type="recordset">
<record seq="1">
<DEALCO_BLICENS_NO type="string">501000</DEALCO_BLICENS_NO>
<ncRecStatus type="string">normal</ncRecStatus>
</record>
</RS_SLIP_LIST>
</dataset>
     */
    public void beforeExecute(IBatchContext context) {
    	log = getLog(context);
		
		processCnt = 0;
		taskNo = "";
		totCnt = 0;
		procFileName = "";
		
		IOnlineContext    onlineCtx  = makeOnlineContext(context);
		IDataSet reqDS = new DataSet();
		IDataSet resDS = callOnlineBizComponent("sc.SCSBase", "fInqTaskNoSeq", reqDS, onlineCtx);
		taskNo = resDS.getField("TASK_NO");
		
		reqDS.putField("TASK_DT", DateUtils.getCurrentDate());
        reqDS.putField("TASK_ID", context.getInParameter("TASK_ID"));
        reqDS.putField("TASK_NO", taskNo);
        reqDS.putField("TASK_NM", context.getInParameter("TASK_NM"));
        reqDS.putField("GRP_ID", "NR");
        reqDS.putField("INST_CD", "DMS");
        reqDS.putField("BAT_TASK_PROC_ST_CD", "B");
        reqDS.putField("OP_CNT", "0");
        reqDS.putField("FS_REG_USER_ID", "BAT");
        reqDS.putField("LS_CHG_USER_ID", "BAT");
        
        IDataSet resDS2 = callOnlineBizComponent("sc.SCSBase", "fRegBatTaskOpHst", reqDS, onlineCtx);

        Log log = getLog(context);
        if(log.isDebugEnabled()) {
            log.debug("공유컴포넌트 호출 결과:");
            log.debug(resDS);
        }		
    }
    
    /**
     * 배치 후처리 메소드. 
     * beforeExecute(), execute() 의 Exception 발생 여부와 관계없이 이 메소드는 실행됨
     */
    public void afterExecute(IBatchContext context) {
	   	 IOnlineContext onlineCtx = makeOnlineContext(context);
	     
	     IDataSet reqDS = new DataSet();
	     reqDS.putField("TASK_NO", taskNo);
	     reqDS.putField("OP_FILE_NM", procFileName);
	     reqDS.putField("LS_CHG_USER_ID", "BAT");
	      
	     if (super.exceptionInExecute == null) {
	         // execute() 정상인 경우
	         reqDS.putField("BAT_TASK_PROC_ST_CD", "S");
	     }else {
	         // execute() 에서 에러 발생할 경우
	         reqDS.putField("BAT_TASK_PROC_ST_CD", "F");
	         processCnt = 0;
	     }
	      
	     reqDS.putField("PROC_CNT", ""+processCnt);
	     IDataSet resDS = callOnlineBizComponent("sc.SCSBase", "fUpdBatTaskOpHst", reqDS, onlineCtx);
	
	     log = getLog(context);
	     if(log.isDebugEnabled()) {
	         log.debug("보증보험료정산 BATCH 호출 결과:");
	         log.debug(resDS);
	     }
    }
    
    /**
     * 입력파라미터 준비
     * @param context
     * @return
     */
    private Map<String, String> prepareInputParam(IBatchContext context)
    {
    	Map<String, String> paramMap = new HashMap<String, String>();
     	SAPUtils.debug("prepareInputParam() context :"+context);
     	paramMap.putAll(context.getInParameters());
      	paramMap.put("PROC_DT", context.getInParameter("PROC_DT")); //실행일
     	SAPUtils.debug("prepareInputParam() paramMap :"+paramMap);
     	return paramMap;
    }
    

    /**
     * 배치 메인 메소드
     */
    public void execute(final IBatchContext context) {
        
    	Map paramMap = this.prepareInputParam(context);
        IDataSet requestData = SAPUtils.prepareXMLInputParam (context,"ONDEMAND_DATASET");
        
        if(requestData != null  && requestData.containsRecordSet("RS_SLIP_LIST")) //전표번호로 조회
        {
            this.syncVendorByRegNo(context, paramMap,requestData.getRecordSet("RS_SLIP_LIST"));
        }
        else //기간으로 조회
        {
             this.syncVendorByTime(context, paramMap);
        }
    }
    
    /**
     * 
     * syncVendorByRegNo
     *  
     * @param context
     * @param paramMap
     * @param rs void
     */
    private void syncVendorByRegNo(final IBatchContext context, Map paramMap, IRecordSet rs)
    {
        if(rs!=null && rs.getRecordCount()>0)
        {
            String bizRegNo = "";
            String vendorStatusCd="";
            Map uMap = null;
            for(int i=0; i<rs.getRecordCount(); i++)
            {
                bizRegNo =  rs.get(i, "BIZ_REG_NO");
                vendorStatusCd =  this.checkERPVendorAtERP(context, bizRegNo);
                uMap = SAPUtils.convertRecord2Map(rs.getRecord(i));
                uMap.put("DEALCO_FISCL_ST_CD", vendorStatusCd);
                dbUpdate("UDealcoByBizRegNo", uMap);
                dbUpdate("UBondByBizRegNo", uMap);
                dbUpdate("USaleAsmptByBizRegNo", uMap);
            }
        }
    }
    
    /**
     * 기간으로 조회
     * syncVendorByTime
     *  
     * @param paramMap
     * @param context void
     */
    private void syncVendorByTime(final IBatchContext context, Map paramMap)
    {
        String toDate = SAPUtils.nvl(SAPUtils.nvl(paramMap,"PROC_DT",null),HpcUtils.getCurrentDate(Locale.KOREAN, SlipConstants.YYYYMMDD));
        String fromDate = DateUtils.addMonth(toDate, -2);
        
        if(    StringUtils.isNotEmpty(SAPUtils.nvl(paramMap,"STA_DT",null))
            && StringUtils.isNotEmpty(SAPUtils.nvl(paramMap,"END_DT",null))
           )
        {
            fromDate = SAPUtils.nvl(paramMap,"STA_DT",null);
            toDate   = SAPUtils.nvl(paramMap,"END_DT",null);
                    
        }
        
        paramMap.put("FROM_DATE", fromDate);
        paramMap.put("TO_DATE"  , toDate  );
        
        this.userNo = SAPUtils.nvl(paramMap,"USER_NO",SAPUtils.nvl(paramMap ,"TASK_ID", "BAT"));
        
        String operType = SAPUtils.nvl(paramMap, "OPER_TYPE","");
        String syncType;
        
        if("OND".equals(operType))
        {
            syncType = SAPUtils.nvl(paramMap, "SYNC_TYPE","");
        }
        else
        {
            syncType =SAPUtils.nvl(paramMap, "SYNC_TYPE4BS","");
        }
            
        if(StringUtils.isEmpty(syncType)) syncType="XD";
        paramMap.put("SYNC_TYPE", syncType);
        
        //if(StringUtils.isEmpty(syncType))
        {
            this.upsertHandler(context, paramMap, "SDealco", "UDealco");
            this.upsertHandler(context, paramMap, "SBondSlip", "UBond");
            this.upsertHandler(context, paramMap, "SSaleAsmptSlip", "USaleAsmpt");
        }
    }
    
    /**
     * upsertHandler
     * @param context
     * @param paramMap
     * @param selectStmt
     * @param upsertStmt
     */
    private void upsertHandler(IBatchContext context, Map paramMap, String selectStmt, String upsertStmt)
    {
		try {
			context.setAttribute("upsertStmt", upsertStmt);
        	dbSelect(selectStmt, paramMap, upsertRecordHandler(context), context);         	        
    	} catch (Exception e) {
            throw new BizRuntimeException("M00001", e);
        }
    }
    
    /**
     * upsertRecordHandler
     * @param context
     * @return
     */
     private AbsRecordHandler upsertRecordHandler(IBatchContext context) {
     	AbsRecordHandler rh = new AbsRecordHandler(context) {
 			@Override
 			public void handleRecord(IRecord row) {
 				String upsertStmt  = context.getAttribute("upsertStmt").toString();
 				context.setProgressCurrent(getCurrentRecordCount()); // 진행률 표시
 				context.getLogger().debug("updateRecordHandler()"+upsertStmt+" : " + row);
 				//SAPUtils.debug("updateRecordHandler()"+updateStmt+" : " + row);
 				processWithinUpsertRecordHandler(context, row);
 				if(upsertStmt.startsWith("U"))
 				{
 					dbUpdate(upsertStmt, row, context);
 				}
 				else if(upsertStmt.startsWith("I"))
 				{
 					dbInsert(upsertStmt, row, context);
 				}
 				else if(upsertStmt.startsWith("D"))
 				{
 					dbDelete(upsertStmt, row, context);
 				}
 				processCnt++;
 			}
 		};
     	return rh;
     }
    
     /**
      * processWithinUpsertRecordeHandler
      * @param context
      * @param row
      * @return
      */
     private IRecord processWithinUpsertRecordHandler(IBatchContext context, IRecord row)
     {
		 IOnlineContext    onlineCtx  = makeOnlineContext(context);
		 row.put("DEALCO_FISCL_ST_CD", this.checkERPVendorAtERP(context, row.get("BIZ_REG_NO")));
    	 return row;
     }
     
     /**
      * 체크
      * checkERPVendorAtERP
      *  
      * @param context
      * @param bizRegNo
      * @return String
      */
     private String checkERPVendorAtERP(IBatchContext context, String bizRegNo)
     {
         IOnlineContext    onlineCtx  = makeOnlineContext(context);
         IDataSet reqDS = new DataSet();
         reqDS.putField("DEALCO_BLICENS_NO", bizRegNo);
         IDataSet resDS = callOnlineBizComponent("nr.NRSXMBase", "fInqVendorAtERP", reqDS, onlineCtx);
         return getSkipCustomer(bizRegNo, resDS.getField("ERP_MSG_CD"));
     }
     
     /**
      * 제외할 코드
      */
     private String getSkipCustomer(String bizRegNo, String originStCd)
     {
    	 String returnStr = originStCd;
    	 String[] arr = {"501000"};
    	 if(SAPUtils.in(arr, bizRegNo)) returnStr = "S";
    	 return returnStr;
     }
}