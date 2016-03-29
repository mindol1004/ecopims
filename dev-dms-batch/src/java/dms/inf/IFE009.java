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
import nexcore.framework.core.exception.BizRuntimeException;
import nexcore.framework.core.util.DateUtils;
import nexcore.framework.core.util.BaseUtils;

import org.apache.commons.logging.Log;

import fwk.utils.HpcUtils;
import fwk.utils.IFFileToDBUtils;

/**
 * <ul>
 * <li>업무 그룹명 : DMS-IF/인터페이스</li>
 * <li>서브 업무명 : BIF006</li>
 * <li>설  명 : <pre>[IF]유통망정보-B/R코드</pre></li>
 * <li>작성일 : 2015-08-07 16:40:57</li>
 * <li>작성자 : 이영진 (newnofixing)</li>
 * </ul>
 */
public class IFE009 extends AbsBatchComponent {
    private int processCnt = 0;
    private String taskNo = "";
    private String procFileName = "";
    private String batTaskProcStCd = "";

    /**
     * 배치 생성자. 
     * 상위클래스 생성자 호출
     */
    public IFE009() {
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
        reqDS.putField("GRP_ID", "EP");
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
        samTool.addColumnInfo("BAS_SETUP_PRICE",               6,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("ORG_ID",                       10,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("ORG_NM",                       80,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("ORG_ABR_NM",                   40,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("ORG_CD",                        6,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("SUB_ORG_CD",                    4,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("PSNEL_ORG_CD",                  4,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("ORG_PRINT_SEQ",                 6,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("HIGHR_ORG_ID",                 10,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("HIGHR_ORG_CD",                  6,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("HIGHR_SUB_ORG_CD",              4,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("HIGHR_ORG_NM",                 80,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("CNTR_ORG_ID",                  10,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("CNTR_ORG_CD",                   6,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("CNTR_SUB_ORG_CD",               4,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("CNTR_ORG_NM",                  80,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("HEADQ_ORG_ID",                 10,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("HEADQ_ORG_CD",                  6,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("HEADQ_SUB_ORG_CD",              4,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("HEADQ_ORG_NM",                 80,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("ORG_CLFC_CD",                   1,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("ORG_TYP_CD",                    3,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("MGPL_CD",                       1,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("AREA_CD",                       1,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("APPLY_STA_DT",                  8,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("APPLY_END_DT",                  8,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("VIRT_ORG_TYP_CD",               2,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("AFS_CNTR_TYP_CD",               1,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("SPS_GRADE_CD",                  1,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("CTZ_REG_SER_NO",               13,SAMFileTool.TYPE_BIGDECIMAL);
        samTool.addColumnInfo("BIZ_REG_NO",                   10,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("INVE_MGMT_YN",                  1,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("DEL_YN",                        1,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("ACTVN_YN",                      1,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("FFM_BYAGN_QUOTA_PRICE",        11,SAMFileTool.TYPE_BIGDECIMAL);
        samTool.addColumnInfo("CURT_DUTYP_ID",                10,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("FINC_REQT_ST_CD",               3,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("TEL_NO",                       12,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("FAX_NO",                       12,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("ZIPCD",                         6,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("BAS_ADDR",                     70,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("DTL_ADDR",                    100,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("MKT_ORG_LVL_CD",                2,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("UKEY_LAST_CHG_DTM",            20,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("CTZ_REG_NO_SECT_INFO",         13,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("CO_CL_CD",                      1,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("CNSL_ORG_INOUT_CL_CD",          1,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("RELT_ORG_ID",                  10,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("ADDR_ID",                      15,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("BUILD_DONG_NO",                 5,SAMFileTool.TYPE_BIGDECIMAL);
        samTool.addColumnInfo("BUILD_HHOLD_NO",                5,SAMFileTool.TYPE_BIGDECIMAL);
        samTool.addColumnInfo("ASTN_ADDR",                   100,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("ROADN_ADDR_YN",                 1,SAMFileTool.TYPE_STRING);

        // 암호화 컬럼 정의
        samTool.addColumnInfo("TEL_NO_ENPT",                   0,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("FAX_NO_ENPT",                   0,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("DTL_ADDR_ENPT",                 0,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("CTZ_REG_NO_SECT_INFO_ENPT",     0,SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("ASTN_ADDR_ENPT",                0,SAMFileTool.TYPE_STRING);

        samTool.addColumnInfo("IF_PROC_DT",        		       0, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("IF_FILE_NM",        		       0, SAMFileTool.TYPE_STRING);
        samTool.addColumnInfo("IF_SEQ",            		       0, SAMFileTool.TYPE_STRING);
        // 파일 레이아웃 정의
        samTool.setEncoding(BaseUtils.getConfiguration("interface.file.encoding"));

        Map<String, String> paramMap = new HashMap<String, String>();
        //paramMap.put("PROC_DT", context.getInParameter("PROC_DT"));
        paramMap.put("TASK_ID", context.getInParameter("TASK_ID"));
        paramMap.put("FILE_LOC", context.getInParameter("FILE_LOC"));
        paramMap.put("IF_SEQ", context.getInParameter("IF_SEQ"));
        paramMap.put("REC_LENG", "905");
        
        try {
            RecordHandlerExecutor rhe = new RecordHandlerExecutor(makeRecordHandler(context));
            IFFileToDBUtils fileUtil = new IFFileToDBUtils();
            // 전문 양식에 헤더, 테일 없이 DATA만 있는 인터페이스 유통망정보-BR코드 전용 listFileDataBRCd
            IDataSet responseData = fileUtil.listFileDataBRCd(samTool, paramMap, log, rhe);

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
                row.set("TEL_NO_ENPT", HpcUtils.encodeByAES(row.get("TEL_NO")));
                row.set("TEL_NO", HpcUtils.maskingTelNo(row.get("TEL_NO")));
                row.set("FAX_NO_ENPT", HpcUtils.encodeByAES(row.get("FAX_NO")));
                row.set("FAX_NO", HpcUtils.maskingTelNo(row.get("FAX_NO")));
                row.set("DTL_ADDR_ENPT", HpcUtils.encodeByAES(row.get("DTL_ADDR")));
                row.set("DTL_ADDR", HpcUtils.maskingAddrDtl(row.get("DTL_ADDR")));
                if(row.get("CTZ_REG_NO_SECT_INFO").trim().length() > 6) {
                    row.set("CTZ_REG_NO_SECT_INFO_ENPT", HpcUtils.encodeByAES(row.get("CTZ_REG_NO_SECT_INFO")));
                    row.set("CTZ_REG_NO_SECT_INFO", HpcUtils.maskingJuminNo(row.get("CTZ_REG_NO_SECT_INFO")));
                }else{
                    row.set("CTZ_REG_NO_SECT_INFO_ENPT", HpcUtils.encodeByAES(row.get("CTZ_REG_NO_SECT_INFO")));
                    row.set("CTZ_REG_NO_SECT_INFO", HpcUtils.maskingBirthYmd(row.get("CTZ_REG_NO_SECT_INFO")));
                }
                row.set("ASTN_ADDR_ENPT", HpcUtils.encodeByAES(row.get("ASTN_ADDR")));
                row.set("ASTN_ADDR", HpcUtils.maskingAddrDtl(row.get("ASTN_ADDR")));
                dbInsert("IDsnetInfoBrCd", row, context);

                if (getCurrentRecordCount()%1000 == 0) {
                	dbEndBatch();
                	dbBeginBatch();
                }
            }
        };
        return rh;
    } 

}