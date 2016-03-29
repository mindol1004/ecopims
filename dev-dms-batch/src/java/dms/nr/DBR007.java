package dms.nr;

import java.util.HashMap;
import java.util.Map;

import nexcore.framework.bat.IBatchContext;
import nexcore.framework.bat.base.AbsBatchComponent;
import nexcore.framework.bat.base.AbsRecordHandler;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.util.DateUtils;

import org.apache.commons.logging.Log;

/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>서브 업무명 : DBR007</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-08-18 14:38:31</li>
 * <li>작성자 : 장미진 (kuramotojin)</li>
 * </ul>
 */
public class DBR007 extends AbsBatchComponent {
    private Log log;
    private int processCnt = 0;
    private String taskNo = "";
    @SuppressWarnings("unused")
	private int totCnt = 0;
    private String procFileName = "";
    
    public DBR007() {
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
        reqDS.putField("BAT_TASK_PROC_ST_CD", "B");
        reqDS.putField("PROC_CNT", "0");
        reqDS.putField("FS_REG_USER_ID", "BAT");
        reqDS.putField("LS_CHG_USER_ID", "BAT");
        
        callOnlineBizComponent("sc.SCSBase", "fRegBatTaskOpHst", reqDS, onlineCtx);

        log = getLog(context);
        if(log.isDebugEnabled()) {
            log.debug("공유컴포넌트 호출 결과:");
            log.debug(resDS);
        }
		
    }

    /**
     * 배치 메인 메소드
     */
    public void execute(final IBatchContext context) {
    	txBegin();  
    	dbStartSession();
    	dbBeginBatch();
    	
    	Map<String, String> paramMap = new HashMap<String, String>();
    	paramMap.put("PROC_DT", context.getInParameter("PROC_DT"));    //처리일
    	
    	dbSelect("SREqpClctInfo", paramMap, makeRecordHandler(context), context); //단말기회수정보IF조회
    	
		// 트랜잭션 커밋
		dbEndBatch();
		dbEndSession();
		txCommit(); 
    }
    
    /**
     * 배치 후처리 메소드. 
     * beforeExecute(), execute() 의 Exception 발생 여부와 관계없이 이 메소드는 실행됨
     */
    public void afterExecute(IBatchContext context) {
    	IOnlineContext    onlineCtx  = makeOnlineContext(context);
        IDataSet reqDS = new DataSet();
        
    	
        if (super.exceptionInExecute == null) {
            // execute() 정상인 경우
            reqDS.putField("BAT_TASK_PROC_ST_CD", "S");
        }else {
            // execute() 에서 에러 발생할 경우
            reqDS.putField("BAT_TASK_PROC_ST_CD", "F");
            processCnt = 0;
        }
        
        reqDS.putField("TASK_NO", taskNo);
        reqDS.putField("PROC_CNT", ""+processCnt);
        reqDS.putField("OP_FILE_NM", procFileName);
        reqDS.putField("LS_CHG_USER_ID", "BAT");
        IDataSet resDS = callOnlineBizComponent("sc.SCSBase", "fUpdBatTaskOpHst", reqDS, onlineCtx);

        Log log = getLog(context);
        if(log.isDebugEnabled()) {
            log.debug("공유컴포넌트 호출 결과:");
            log.debug(resDS);
        }
    }
    
    public AbsRecordHandler makeRecordHandler(IBatchContext context) {
    	AbsRecordHandler rh = new AbsRecordHandler(context) {
			
			@SuppressWarnings("unchecked")
			@Override
			public void handleRecord(IRecord row) {
				String ifFileNm = "";
				ifFileNm = "R007.SKCC."+context.getInParameter("PROC_DT")+"_"+context.getInParameter("FILE_SEQ");
				
				context.setProgressCurrent(getCurrentRecordCount()); // 진행률 표시
				context.getLogger().debug("########### : " + row);
				
				IDataSet reqDs = new DataSet();
				reqDs.putFieldMap(row);
				reqDs.putField("IF_SEQ",processCnt+1);
				reqDs.putField("IF_PROC_DT", context.getInParameter("PROC_DT"));	// IF_처리일자
				reqDs.putField("IF_FILE_NM", ifFileNm);	// IF_파일명
				dbInsert("IREqpClctInfo",reqDs.getFieldMap(),context);//IF테이블에 전송정보를 넣는다.
			
				processCnt++;
			}
		};
    	return rh;
    }    
}
