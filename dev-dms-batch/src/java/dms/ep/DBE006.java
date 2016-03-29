package dms.ep;

import java.util.HashMap;

import nexcore.framework.bat.IBatchContext;

import org.apache.commons.logging.Log;

import nexcore.framework.bat.base.AbsBatchComponent;
import nexcore.framework.bat.base.AbsRecordHandler;
import nexcore.framework.bat.base.AutoCommitRecordHandler;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.exception.BizRuntimeException;
import nexcore.framework.core.util.DateUtils;
import nexcore.framework.core.util.StringUtils;

/**
 * <ul>
 * <li>업무 그룹명 : DMS-IF/인터페이스</li>
 * <li>서브 업무명 : BIF110</li>
 * <li>설  명 : <pre>[EP]처리상태전송</pre></li>
 * <li>작성일 : 2015-08-10 11:07:57</li>
 * <li>작성자 : 이준우 (newnofixing)</li>
 * </ul>
 */
public class DBE006 extends AbsBatchComponent {
    private int processCnt = 0;
    private String taskNo = "";
    private String procFileName = "";

    /**
     * 배치 생성자. 상위클래스 생성자 호출
     */
    public DBE006() {
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
		
		IOnlineContext  onlineCtx  = makeOnlineContext(context);
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
        // 트랜잭션 시작
        txBegin();  
        dbStartSession();
//        dbBeginBatch();
        
        /* SQL ParmaMap 설정 */
        HashMap<String, Object> paramMap = new HashMap<String,Object>();
        paramMap.put("IF_PROC_DT", context.getInParameter("PROC_DT"));             //파라메터 날짜받기
        
        try {
        	//dbDelete("DTfProcStTrms", paramMap, context);             //처리상태이전정보 삭제
            dbSelect("SProcStTrms", paramMap, makeRecordHandler(context), context); // 처리상태정보 조회
            dbUpdate("UConsultMgmt", paramMap, context);     // 상담관리수정
            
        } catch (Exception e) {         
            throw new BizRuntimeException("M00001", e);
        }
        
        // 트랜잭션 커밋
//        dbEndBatch();
        dbEndSession();
        txCommit();
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
        IDataSet resDS = callOnlineBizComponent("sc.SCSBase", "fUpdBatTaskOpHst", reqDS, onlineCtx);

    }
    
    /**
     * 상담관리상세대상IF조회 후 레코드 단위로 상담관리상세대상 테이블에 입력
     * 
     */
    public AutoCommitRecordHandler makeRecordHandler(IBatchContext context) {
    
        AutoCommitRecordHandler rh = new AutoCommitRecordHandler(this,context,  dbNewSession(context, false)) {
        
            int i = 1;  // I/F 채번순번 
            String strEqpStRmk = "";

            @Override
            public void handleRecord(IRecord row) {                                                             
                //Log log = getLog(context);
                String ifFileNm = "";
                ifFileNm = "EQ12.SKCC."+context.getInParameter("PROC_DT")+"_"+context.getInParameter("FILE_SEQ");               
                context.setProgressCurrent(getCurrentRecordCount()); // 진행률 표시
//                context.getLogger().debug("######row##### : " + row);    
                HashMap<String, Object> paramMap = new HashMap<String,Object>();                    
                paramMap.put("IF_PROC_DT", context.getInParameter("PROC_DT"));  // IF_처리일자
                paramMap.put("IF_FILE_NM", ifFileNm);                           // IF_파일명
                paramMap.put("IF_SEQ", i++);                                    // IF_순번                    
                
                paramMap.put("CNSL_MGMT_NO", StringUtils.nvlObject(row.get("CNSL_MGMT_NO"),""));  // 단말기모델코드
                paramMap.put("EQP_MDL_CD", StringUtils.nvlObject(row.get("EQP_MDL_CD"),""));      // 단말기모델코드
                paramMap.put("EQP_SER_NO", StringUtils.nvlObject(row.get("EQP_SER_NO"),""));      // 단말기일련번호
                paramMap.put("PROGR_ST", StringUtils.nvlObject(row.get("PROGR_ST"),""));          // 진행상태
                paramMap.put("PAY_CL", StringUtils.nvlObject(row.get("PAY_MTHD"),""));         // 지급구분
                paramMap.put("PAY_DT", StringUtils.nvlObject(row.get("PAY_DT"),""));              // 지급일자
                paramMap.put("CNSL_AMT", StringUtils.nvlObject(row.get("CNSL_AMT"),""));          // 상담금액
                paramMap.put("EQP_ST", StringUtils.nvlObject(row.get("EQP_ST"),""));              // 단말기상태
                
                paramMap.put("SKN_SMPL_JDG_DAMT",StringUtils.nvlObject(row.get("SKN_SMPL_JDG_DAMT"),""));                                             // SKN샘플감정차액
                paramMap.put("EQP_TLY_DT", StringUtils.nvlObject(row.get("TLY_DT"),""));                                                   // 단말기검수일자
                //paramMap.put("EQP_TLY_CONT", StringUtils.nvlObject(row.get("EQP_ST_RMK"),""));                                                  // 단말기검수내용
                strEqpStRmk = "";
                strEqpStRmk = StringUtils.nvlObject(row.get("EQP_ST_RMK"),"");
                //context.getLogger().debug("EQP_ST_RMK:("+StringUtils.length(strEqpStRmk)+")"+strEqpStRmk);
                //log.debug("====================>\n"+strEqpStRmk);
                try {
                    //검수내용이 60바이트를 넘을경우 60바이트까지만 및 한글체크--추후2바이트체크로직 보완필요
                    if(strEqpStRmk != null && !"".equals(strEqpStRmk) && strEqpStRmk.getBytes("EUC-KR").length > 60){
                        int nStrTotLeng = 0;
                        char[] strArray = strEqpStRmk.toCharArray();
                        String strTemp = "";
    
                        for (int j = 0; j < strArray.length; j++) {
                            if (strArray[j] >= 'A' && strArray[j] <= 'z') {//영문
                                nStrTotLeng+=1;
                            } else if (strArray[j] >= '\uAC00' && strArray[j] <= '\uD7A3') { //한글
                                nStrTotLeng+=2;
                            } else if (strArray[j] >= '0' && strArray[j] <= '9') {//숫자
                                nStrTotLeng+=1;
                            } else if (strArray[j] <= 0x7F) {//문자
                                nStrTotLeng+=1;
                            } else {//기타
                                nStrTotLeng+=2;
                            }
                            if(nStrTotLeng <= 58){//여유
                                strTemp = strTemp + strArray[j];
                            }else{
                                break;                                
                            }
                        }                    
                        //log.debug("===================>"+strTemp);
                        strEqpStRmk = strTemp;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                paramMap.put("EQP_TLY_CONT", strEqpStRmk);                                                  // 단말기검수내용
/*          
//                context.getLogger().debug("######paramMap##### : " + paramMap.toString());
                IRecord Record = dbSelectSingle("STfProcStTrmsCnt", paramMap, context); //처리상태조회 카운트                    
          
                if(Record.getInt("CNT",0) > 0){
//                    context.getLogger().debug("처리상태이전정보삭제");
                    dbDelete("DTfProcStTrms", paramMap, context);             //처리상태이전정보 삭제
                }
//                context.getLogger().debug("처리상태이전정보등록");
*/
                dbInsert("ITfProcStTrms", paramMap, context);     // TF_PROC_ST_TRMS 테이블에 등록
//                context.getLogger().debug("상담관리수정");
                
                processCnt++;
                
            }
            
        };
        
        rh.setAddBatchMode(true);
        rh.setCommitCount(1000);
        rh.setStopWhenException(false);
        
        return rh;
    }

}



