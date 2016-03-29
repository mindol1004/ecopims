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
import nexcore.framework.core.exception.BizRuntimeException;
import nexcore.framework.core.util.DateUtils;

import org.apache.commons.logging.Log;

import dms.utils.SAPUtils;

/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>서브 업무명 : XCR007</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-08-07 14:53:49</li>
 * <li>작성자 : 진병수 (greatjin)</li>
 * </ul>
 */
public class XCR007 extends AbsBatchComponent {
    private Log log;
	private int processCnt = 0;
	private String taskNo = "";
	private String procFileName = "";
    
    public XCR007() {
        super();
    }

    /**
     * 배치 전처리 메소드. 
     * 여기서 Exception 발생시 execute() 메소드는 실행되지 않고, afterExecute() 는 실행됨
     */
    public void beforeExecute(IBatchContext context) {
    	Log log = getLog(context);

		processCnt = 0;
		taskNo = "";
		procFileName = "";

		IOnlineContext onlineCtx = makeOnlineContext(context);
		IDataSet reqDS = new DataSet();
		IDataSet resDS = callOnlineBizComponent("sc.SCSBase", "fInqTaskNoSeq",
				reqDS, onlineCtx);
		taskNo = resDS.getField("TASK_NO");

		reqDS.putField("TASK_DT", DateUtils.getCurrentDate());
		reqDS.putField("TASK_ID", context.getInParameter("TASK_ID"));
		reqDS.putField("TASK_NO", taskNo);
		reqDS.putField("TASK_NM", context.getInParameter("TASK_NM"));
		reqDS.putField("GRP_ID", "PR");
		reqDS.putField("INST_CD", "DMS");
		reqDS.putField("BAT_TASK_PROC_ST_CD", "B");
		reqDS.putField("PROC_CNT", "0");
		reqDS.putField("FS_REG_USER_ID", "BAT");
		reqDS.putField("LS_CHG_USER_ID", "BAT");

		callOnlineBizComponent("sc.SCSBase", "fRegBatTaskOpHst", reqDS,onlineCtx);

		log = getLog(context);
		if (log.isDebugEnabled()) {
			log.debug("XCR007(대리점단말기매입정산)_컴포넌트 호출 결과:");
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
    
    
    /**
     * 입력파라미터 준비
     * @param context
     * @return
     */
    private Map<String, String> prepareInputParam(IBatchContext context)
    {
    	log = context.getLogger();
    	Map<String, String> paramMap = new HashMap<String, String>();
     	paramMap.putAll(context.getInParameters());
    	paramMap.put("PROC_DT", context.getInParameter("PROC_DT")); //실행일
     	SAPUtils.debug("prepareInputParam() paramMap :"+paramMap);
     	return paramMap;
    }

    
    /**
     * 배치 메인 메소드
     * 1. SKN여신의 데이터를 기반으로 채무테이블에 쓴다.
     * 2. (차)미지급 - (대) AP SKN
     * 3. 매입 테이블 과 여신 테이블을 비교해서 현급 분에 대해서.
     * 4. (차)미지급 - (대) AP 대리점
     * 
     */
    public void execute(final IBatchContext context) {
    	// 트랜잭션 시작
		txBegin();
		dbStartSession();
		dbBeginBatch();
		
		//입력파라미터를 받음
     	Map<String, String> paramMap = this.prepareInputParam(context);// 기준년월
		try {
		    
		    /**
		     * job스캐쥴러에 의해 실행되면 차수를 자동 계산한다.
		     */
		    String ts ="2";
		    if(!paramMap.containsKey("DEBT_PRCH_TS"))
		    {
		        String currentDD = DateUtils.getCurrentDate("dd");
		        SAPUtils.debug("adjustTS currentDD:"+currentDD);
		        currentDD = SAPUtils.getDD_YYYYMMDD(SAPUtils.nvl(paramMap,"PROC_DT",currentDD));
		        SAPUtils.debug("adjustTS currentDD:"+currentDD);
		        if(Integer.parseInt(currentDD) >=1 && Integer.parseInt(currentDD) <= 23) ts = "1";
		    }
//		    else
//		    {
//		        ts = SAPUtils.nvl(paramMap,"DEBT_PRCH_TS","");
//		    }
		    paramMap.put("TS", ts);
		    
//		    if(paramMap.containsKey("YDATE"))
//		    {
//		        paramMap.put("PROC_DT", paramMap.get("YDATE") + "01");
//		    }
		    
			this.clearData(paramMap);
        	dbSelect("SEqpSKN", paramMap, makeRecordHandler(context), context); // 단말기회수정보대상 IF조회 및 등록        	        
    	} catch (Exception e) {
            throw new BizRuntimeException("M00001", e);
        }
		
		// 트랜잭션 커밋
		dbEndBatch();
		dbEndSession();
		txCommit();
	}
    
    
    /**
     * 기존데이터가 있는지 체크하고 샂제한다.
     * @param paramMap
     */
    private void clearData(Map<String, String> paramMap)
    {
    	boolean hasClearData = true;
     	
 		if(hasClearData)
 		{
 			SAPUtils.debug("clearData paramMap:"+paramMap);
 			dbDelete("DXclDebt", paramMap);
 		}
    }
    
    
    /**
     * makeRecordHandler
     * @param context
     * @return
     */
     public AbsRecordHandler makeRecordHandler(IBatchContext context) {
     	AbsRecordHandler rh = new AbsRecordHandler(context) {
 			
 			@Override
 			public void handleRecord(IRecord row) {
 				context.setProgressCurrent(getCurrentRecordCount()); // 진행률 표시
 				context.getLogger().debug("########### : " + row);
 				//채무정보
 				dbInsert("IXclDebt", row, context);
 				//매입정보
 				processCnt++;
 			}
 		};
     	return rh;
     }
    


}
