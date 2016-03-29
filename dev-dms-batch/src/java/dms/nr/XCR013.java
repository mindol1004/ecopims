package dms.nr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;

import dms.utils.SAPUtils;

/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>서브 업무명 : XCR005</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-08-06 19:26:06</li>
 * <li>작성자 : 진병수 (greatjin)</li>
 * </ul>
 */
public class XCR013 extends AbsBatchComponent {
    private Log log;
    private int processCnt = 0;
    private String taskNo = "";
    private int totCnt = 0;
    private String procFileName = "";
    
    public XCR013() {
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
     * 입력파라미터 준비
     * @param context
     * @return
     */
    private Map<String, String> prepareInputParam(IBatchContext context)
    {
    	log = context.getLogger();
    	Map<String, String> paramMap = new HashMap<String, String>();
     	log.debug("prepareInputParam() context :"+context);
     	paramMap.putAll(context.getInParameters());
    	paramMap.put("FILE_DT", context.getInParameter("FILE_DT")); //실행일
     	log.debug("prepareInputParam() paramMap :"+paramMap);
     	return paramMap;
    }
    
    /**
     * 배치 메인 메소드
     */
    public void execute(final IBatchContext context) {
        // 트랜잭션 시작
     	txBegin();  
     	dbStartSession();
     	dbBeginBatch();
        
     	//입력파라미터 셋팅
     	Map<String, String> paramMap = this.prepareInputParam(context);
     	
     	IRecord cntRd = dbSelectSingle("SSlipCnt", paramMap);
     	if( cntRd.getInt("CNT")>0 ){
     		throw new BizRuntimeException("DMS00159", new String[] {"전표"});
     	}
     	
        dbDelete("DEqpInsuCmms", paramMap, context); //전표처리 안된 데이타 삭제
        dbSelect("SEqpInsuCmms", paramMap, makeRecordHandler(context), context); //DB 조회
        
//    	IRecordSet existDebtList = SAPUtils.nvl(dbSelectMulti("SExtDebtInfo",paramMap));
    	
//    	if( existDebtList.getRecordCount() > 0 )
//    	Map tmpMap = null;
//    	for(int i=0; i<existDebtList.getRecordCount();i++)
//	    {
//	    	tmpMap = existDebtList.getRecordMap(i);
//	    	if("05".equals(SAPUtils.nvl(tmpMap,"SLIP_ST_CD","")))
//	    	{
//	    		dbDelete("DXclDebt", tmpMap);
//	            this.selectInsert("SClsComLst", paramMap, "IDebt"); //DB 채무            
//	            dbDelete("DXclEtcByYM", paramMap);
//	            this.selectInsert("SSalesCom2Etc", paramMap, "IEtcXcl"); //DB 기타        	    		
//	    	}
//	    }    	
 		
 		// 트랜잭션 커밋
 		dbEndBatch();
 		dbEndSession();
 		txCommit(); 
     }
    
    /**
     * select Insert
     * @param selectStmtName
     * @param paramMap
     * @param insertStmtName
     * @return
     */
    private int selectInsert(String selectStmtName, Object paramMap, String insertStmtName)
    {
    	int returnInt =0;
		IRecordSet list =dbSelectMulti(selectStmtName, paramMap);
		if(list != null)
		{
			for(int i=0; i<list.getRecordCount(); i++)
			{
				dbInsert(insertStmtName, list.getRecordMap(i));
				returnInt ++;
			}
		}
		return returnInt;
    }
    
    
    public AbsRecordHandler makeRecordHandler(IBatchContext context) {
        AbsRecordHandler rh = new AbsRecordHandler(context) {
            
            @SuppressWarnings("unchecked")
            @Override
            public void handleRecord(IRecord row) {
                context.setProgressCurrent(getCurrentRecordCount()); // 진행률 표시
                context.getLogger().debug("########### : " + row);
               
                dbUpdate("IEqpInsuCmms", row, context);
                processCnt++;
            }              
        };
        return rh;
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
	
		IDataSet resDS = callOnlineBizComponent("sc.SCSBase", "fUpdBatTaskOpHst",
				reqDS, onlineCtx);

		Log log = getLog(context);
		if (log.isDebugEnabled()) {
			log.debug("XCR007(대리점단말기매입정산)_컴포넌트 호출 결과:");
			log.debug(resDS);
		}
    }

}
