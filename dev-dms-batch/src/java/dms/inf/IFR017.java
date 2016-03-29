package dms.inf;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import nexcore.framework.bat.IBatchContext;
import nexcore.framework.bat.base.AbsBatchComponent;
import nexcore.framework.bat.base.AbsRecordHandler;
import nexcore.framework.bat.base.RecordHandlerExecutor;
import nexcore.framework.bat.util.SAMFileTool;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.RecordHeader;
import nexcore.framework.core.exception.BizRuntimeException;
import nexcore.framework.core.util.DateUtils;
import nexcore.framework.core.util.BaseUtils;

import org.apache.commons.logging.Log;

import fwk.utils.HpcUtils;
import fwk.utils.IFFileToDBUtils;

/**
 * <ul>
 * <li>업무 그룹명 : DMS-IF/인터페이스</li>
 * <li>서브 업무명 : BIF017</li>
 * <li>설  명 : <pre>[IF]보증보험가입변경</pre></li>
 * <li>작성일 : 2015-08-10 11:07:57</li>
 * <li>작성자 : 이영진 (newnofixing)</li>
 * </ul>
 */
public class IFR017 extends AbsBatchComponent {
    private int processCnt = 0;
    private String taskNo = "";
    private String procFileName = "";
    private String batTaskProcStCd = "";
    
    /**
     * 배치 생성자. 
     * 상위클래스 생성자 호출
     */
    public IFR017() {
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
		batTaskProcStCd = "";
		
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

        if(log.isDebugEnabled()) {
            log.debug("공유컴포넌트 호출 결과:");
            log.debug(resDS);
        }
		
    }

    /**
     * 배치 메인 메소드
     */
    public void execute(final IBatchContext context) {
        Log log = getLog(context);
        
        // 트랜잭션 시작
        txBegin();  
        dbStartSession();
        dbBeginBatch();
        
        // SAM 파일 본문부 레이아웃 정의
        SAMFileTool samTool = new SAMFileTool();
        samTool.addColumnInfo("REC_CL_CD",                 1, SAMFileTool.TYPE_STRING);
        
        samTool.addColumnInfo("OP_PROC_CL_CD",             2, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("CUST_TYP_CD",               1, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("CUST_NM",                  40, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("CTZ_COPREG_NO",            13, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("BLICENS_NO",               10, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("INSU_MGMT_NO",             14, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("INSU_SVC_NO",              12, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("SVC_MGMT_NO",              10, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("RENTAL_SER_NO",            15, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("RENTAL_SCRB_DT",            8, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("RENTAL_TERM_DT",            8, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("TTUL_CHG_DT",               8, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("INSU_SCUR_NO",             18, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("INSU_SCRB_AMT",             8, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("INSU_STA_DT",               8, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("INSU_END_DT",               8, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("INSU_TERM_RSN_CD",          2, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("INSU_SCRB_ST_CD",           2, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("TEEN_YN",                   1, SAMFileTool.TYPE_STRING);
        
        // 암호화 컬럼 정의
        samTool.addColumnInfo("CUST_NM_ENPT",              0, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("CTZ_COPREG_NO_ENPT",        0, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("INSU_SVC_NO_ENPT",          0, SAMFileTool.TYPE_STRING);

        samTool.addColumnInfo("IF_PROC_DT",                0, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("IF_FILE_NM",                0, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("IF_SEQ",                    0, SAMFileTool.TYPE_STRING);
        // 파일 레이아웃 정의
        samTool.setEncoding(BaseUtils.getConfiguration("interface.file.encoding"));

        Map<String, String> paramMap = new HashMap<String, String>();
        //paramMap.put("PROC_DT", context.getInParameter("PROC_DT"));
        paramMap.put("TASK_ID", context.getInParameter("TASK_ID"));
        paramMap.put("FILE_LOC", context.getInParameter("FILE_LOC"));
        paramMap.put("IF_SEQ", context.getInParameter("IF_SEQ"));
        paramMap.put("REC_LENG", "189");
        
        try {
            RecordHandlerExecutor rhe = new RecordHandlerExecutor(makeRecordHandler(context));
            IFFileToDBUtils fileUtil = new IFFileToDBUtils();
            IDataSet responseData = fileUtil.listFile(samTool, paramMap, log, rhe);

            processCnt = responseData.getIntField("TOT_PROC_CNT");
            procFileName = responseData.getField("PROC_FILE_NM");
            batTaskProcStCd = responseData.getField("BAT_TASK_PROC_ST_CD");
            if ("F".equals(batTaskProcStCd)) throw new BizRuntimeException("DMS00009"); //오류가 발생했습니다.
        } catch(Exception e) {
            log.debug("Exception:["+e.toString()+"]");
            throw new BizRuntimeException("DMS00009", e); //오류가 발생했습니다.
        }
        
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
        
        reqDS.putField("TASK_NO", taskNo);
        reqDS.putField("PROC_FILE_NM", procFileName);
        reqDS.putField("LS_CHG_USER_ID", "BAT");
        
        if (super.exceptionInExecute == null) {
            // execute() 정상인 경우
            reqDS.putField("BAT_TASK_PROC_ST_CD", batTaskProcStCd);
            if ("F".equals(batTaskProcStCd)) processCnt = 0;
        }else {
            // execute() 에서 에러 발생할 경우
            reqDS.putField("BAT_TASK_PROC_ST_CD", "F");
            processCnt = 0;
        }
        
        reqDS.putField("PROC_CNT", ""+processCnt);
        
        IDataSet resDS = callOnlineBizComponent("sc.SCSBase", "fUpdBatTaskOpHst", reqDS, onlineCtx);

        Log log = getLog(context);
        if(log.isDebugEnabled()) {
            log.debug("공유컴포넌트 호출 결과:");
            log.debug(resDS);
        }
        
    }
    
    /**
     * 레코드셋 핸들러
     */
    public AbsRecordHandler makeRecordHandler(IBatchContext context) {
        AbsRecordHandler rh = new AbsRecordHandler(context) {
            
            @Override
            public void handleRecord(IRecord row) {
                context.getLogger().debug("########### : " + row);
                
                //본업무 처리로직 구현
                row.set("CUST_NM_ENPT", HpcUtils.encodeByAES(row.get("CUST_NM")));
                row.set("CUST_NM", HpcUtils.maskingName(row.get("CUST_NM")));
                row.set("CTZ_COPREG_NO_ENPT", HpcUtils.encodeByAES(row.get("CTZ_COPREG_NO")));
                row.set("CTZ_COPREG_NO", HpcUtils.maskingJuminNo(row.get("CTZ_COPREG_NO")));
                row.set("INSU_SVC_NO_ENPT", HpcUtils.encodeByAES(row.get("INSU_SVC_NO")));
                row.set("INSU_SVC_NO", HpcUtils.maskingTelNo(row.get("INSU_SVC_NO")));
                dbInsert("IInsuScrbChg", row, context);

                if (getCurrentRecordCount()%1000 == 0) {
                	dbEndBatch();
                	dbBeginBatch();
                }
            }
        };
        return rh;
    } 

}
