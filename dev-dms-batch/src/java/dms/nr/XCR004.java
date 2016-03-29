package dms.nr;

import java.util.HashMap;
import java.util.Map;

import nexcore.framework.bat.IBatchContext;
import nexcore.framework.bat.base.AbsBatchComponent;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.util.DateUtils;

import org.apache.commons.logging.Log;

import dms.utils.SAPUtils;

/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>서브 업무명 : XCR004</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-08-04 15:54:10</li>
 * <li>작성자 : 진병수 (greatjin)</li>
 * </ul>
 */
public class XCR004 extends AbsBatchComponent {
    private Log log;
    private int processCnt = 0;
    private String taskNo = "";
    private String procFileName = "";
    
    public XCR004() {
        super();
    }

    /**
     * 배치 전처리 메소드. 
     * 여기서 Exception 발생시 execute() 메소드는 실행되지 않고, afterExecute() 는 실행됨
     */
    public void beforeExecute(IBatchContext context) {
    	log = getLog(context);
		
		processCnt = 0;
		taskNo = "";
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
        reqDS.putField("BAT_TASK_PROC_ST_CD", "B"); //before
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
     * 입력파라미터 준비
     * @param context
     * @return
     */
    private Map<String, String> prepareInputParam(IBatchContext context)
    {
    	Map<String, String> paramMap = new HashMap<String, String>();
     	log.debug("prepareInputParam() context :"+context);
     	paramMap.putAll(context.getInParameters());
      	paramMap.put("PROC_DT", context.getInParameter("PROC_DT")); //실행일
     	log.debug("prepareInputParam() paramMap :"+paramMap);
     	return paramMap;
    }
    
    /**
     * 배치 메인 메소드
     */
    public void execute(final IBatchContext context) {
    	log = context.getLogger();
        // 트랜잭션 시작
     	txBegin();  
     	dbStartSession();
     	dbBeginBatch();
     	
     	
     	//입력파라미터 셋팅
     	Map<String, String> paramMap = this.prepareInputParam(context);
     	
     	SAPUtils.debug("execute() paramMap:"+paramMap);
     	
     	IRecordSet rs = dbSelectMulti("SSaleExistAsmpt", paramMap);
     	
     	this.checkAndClearData(paramMap,rs);
     	
     	this.makeData(paramMap, rs);
 		
 		// 트랜잭션 커밋
 		dbEndBatch();
 		dbEndSession();
 		txCommit(); 
     }
    
    /**
     * 기존데이터가 있는지 체크하고 샂제한다.
     * @param paramMap
     */
    private void checkAndClearData(Map<String, String> paramMap,IRecordSet rs)
    {
    	boolean hasClearData = false;
     	
     	if(rs!=null && rs.getRecordCount()>0)
     	{
     		IDataSet ds = SAPUtils.convertRecord2DataSet(rs.getRecord(0));
     		
     		String totCnt = SAPUtils.nvl(ds.getField("TOT_CNT"),"0");
     		String tmpCnt = SAPUtils.nvl(ds.getField("TMP_CNT"),"0");
     		
     		hasClearData =Integer.parseInt(totCnt)>0 && Integer.parseInt(tmpCnt)>0 ; 
     		
     		if(hasClearData)
     		{
     			dbDelete("DSaleAsmpt", paramMap);
     		}
     	}
    }
    
    /**
     * db 에 넣는다.
     * @param paramMap
     */
    private void makeData(Map<String, String> paramMap, IRecordSet delRs)
    {
    	
    	IRecordSet rs = dbSelectMulti("SSaleAsmpt", paramMap);
    	
    	if(rs==null || rs.getRecordCount() ==0) return ;
    	boolean skipFlag = false;
    	Map inputMap = null;
    	String cntrtNo = null;
    		
    	for(int i=0; i<rs.getRecordCount(); i++)
    	{
    		inputMap = rs.getRecordMap(i);
    		inputMap.putAll(paramMap);;
    		cntrtNo= (String) inputMap.get("CNTRT_NO");
    		SAPUtils.debug("makeData() inputMap:"+inputMap);
    		if(delRs!=null && delRs.getRecordCount()>0)
    		{
	    		for(int j=0; j<delRs.getRecordCount(); j++)
	    		{
	    			if(    delRs.get(j, "CNTRT_NO").equals(cntrtNo)
	    			   && !"05".equals(delRs.get(j, "ASMPT_SLIP_ST"))
	    			   )
	    			{
	    				skipFlag  = true;
	    			}
	    		}
    		}
    		
    		if(skipFlag)
    		{
    			skipFlag = false;
    			continue;
    		}
    		else
    		{
    			dbInsert("ISaleAsmpt", inputMap);
    		}
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
		reqDS.putField("PROC_FILE_NM", procFileName);
		reqDS.putField("LS_CHG_USER_ID", "BAT");
		if (super.exceptionInExecute == null) {
			// execute() 정상인 경우
			reqDS.putField("BAT_TASK_PROC_ST_CD", "S");
		} else {
			// execute() 에서 에러 발생할 경우
			reqDS.putField("BAT_TASK_PROC_ST_CD", "F");
			processCnt = 0;
		}
		reqDS.putField("PROC_CNT", "" + processCnt);
		
		SAPUtils.debug("--------------------------------------> reqDS :"+reqDS);
	
		IDataSet resDS = callOnlineBizComponent("sc.SCSBase", "fUpdBatTaskOpHst",
				reqDS, onlineCtx);

		Log log = getLog(context);
		if (log.isDebugEnabled()) {
			log.debug(taskNo+"_컴포넌트 호출 결과:");
			log.debug(resDS);
		}
    }

}
