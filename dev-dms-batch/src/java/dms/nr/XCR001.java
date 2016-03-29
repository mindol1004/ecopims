package dms.nr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nexcore.framework.bat.IBatchContext;

import org.apache.commons.logging.Log;

import com.ibm.icu.util.Calendar;

import dms.utils.SAPUtils;
import nexcore.framework.bat.base.AbsBatchComponent;
import nexcore.framework.bat.base.AbsRecordHandler;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.util.DateUtils;

/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>서브 업무명 : XCR001</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-08-05 10:58:10</li>
 * <li>작성자 : 진병수 (greatjin)</li>
 * </ul>
 */
public class XCR001 extends AbsBatchComponent {
    private Log log;
    private int processCnt = 0;
    private String taskNo = "";
    private int totCnt = 0;
    private String procFileName = "";
    
    public XCR001() {
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
        
        //배치 전처리
        IDataSet resDS2 = callOnlineBizComponent("sc.SCSBase", "fRegBatTaskOpHst", reqDS, onlineCtx);

        Log log = getLog(context);
        if(log.isDebugEnabled()) {
            log.debug("공유컴포넌트 호출 결과:");
            log.debug(resDS);
        }		
		
		
    }

    /**
     * 배치 메인 메소드
     * 차 감가상각비 - 대 감가상각 누계액
     */
    public void execute(final IBatchContext context) {
        /* 참고사항
            
       */  
     	//입력파라미터를 받음
     	Map<String, String> paramMap = new HashMap<String, String>();
     	
     	paramMap.put("STD_YM",context.getInParameter("STD_YM"));  // 기준년월
     	paramMap.put("USER_NO",context.getInParameter("USER_NO"));  //USER_NO   
     	
     	//■■ 1.기준 감가대상 삭제 ■■
        txBegin();   dbStartSession();   dbBeginBatch();    //트랜잭션 시작
        IRecord rs = dbSelectSingle("SDeprMaxTS", paramMap);
        paramMap.putAll(SAPUtils.convertRecord2Map(rs));
        log.info("UAssetRestore :기존 잔액 원복>>"+paramMap);
        dbUpdate("UAssetRestore", paramMap);
        log.info("DBatDepr :기준 감가대상 삭제>>"+paramMap);
     	dbDelete("DBatDepr", paramMap, context);     	
        dbEndBatch();   dbEndSession(); txCommit();         //트랜잭션 커밋 

        //■■ 2.NR_마감감가상각상세 ■■
        txBegin();   dbStartSession();   dbBeginBatch();    //트랜잭션 시작
     	// 고객 파손에 대한 변상금이 있으면 감안해야함
     	log.debug("makeRecordHandler paramMap :"+paramMap);
//     	dbSelect("SDeprDtlLst", paramMap, makeRecordHandler(context), context); //DB 조회
        dbSelect("SDeprStdLst", paramMap, makeDeprRecordHandler(context), context); //DB 조회
     	dbEndBatch();   dbEndSession(); txCommit();         //트랜잭션 커밋     	

     	//■■ 3.NR_마감감가상각/5.자산테이블 업데이트 ■■
        txBegin();   dbStartSession();   dbBeginBatch();    //트랜잭션 시작
     	log.debug("makeSuperRecordHandler paramMap :"+paramMap);
     	dbSelect("SDeprLst", paramMap, makeSuperRecordHandler(context), context); //DB 조회
     	dbEndBatch();   dbEndSession(); txCommit();         //트랜잭션 커밋
     	//■■■■■■■■■■ 6.매출, 매입, 채권, 채무 처리 : 처리확인 ■■■■■■■■■■
     	//■■■■■■■■■■ 7.전표처리: 처리확인  ■■■■■■■■■■
     	
        //this.generateDepr(1000000, 24, "20150805",false);
     }
     
    
    /**
     * 처리
     * @param context
     * @return
     */
     public AbsRecordHandler makeRecordHandler(IBatchContext context) {
        AbsRecordHandler rh = new AbsRecordHandler(context) {
            
            @Override
            public void handleRecord(IRecord row) {
                context.setProgressCurrent(getCurrentRecordCount()); // 진행률 표시
                context.getLogger().debug("########### : " + row);
                
                IDataSet reqDs = new DataSet();
                reqDs.putFieldMap(row);
                reqDs.putField("USER_NO", context.getInParameter("USER_NO"));
                
                dbInsert("IDeprDtl", reqDs.getFieldMap(), context);                //NR_마감감가상각상세 등록
                dbUpdate("UAssetDeprInfo", reqDs.getFieldMap(), context);          //PR_단말기자산 회계금액Update
                dbUpdate("UEqpCntrtDrlAllwnAmt", reqDs.getFieldMap(), context);    //PR_단말기계약상세 충당금금액Update
                
//                dbInsert("IDeprDtl", row, context);                //NR_마감감가상각상세 등록
//                dbUpdate("UAssetDeprInfo", row, context);          //PR_단말기자산 회계금액Update
//                dbUpdate("UEqpCntrtDrlAllwnAmt", row, context);    //PR_단말기계약상세 충당금금액Update
                processCnt++;
                
            }
        };
        return rh;
     }
     
    /**
     * 처리
     * @param context
     * @return
     */
     public AbsRecordHandler makeDeprRecordHandler(IBatchContext context) {
     	AbsRecordHandler rh = new AbsRecordHandler(context) {
 			
 			@Override
 			public void handleRecord(IRecord row) {
 				context.setProgressCurrent(getCurrentRecordCount()); // 진행률 표시
 				context.getLogger().debug("########### : " + SAPUtils.convertRecord2Map(row));
 				
 				IDataSet reqDs = new DataSet();
                reqDs.putFieldMap(row);
                
                IDataSet resDS2 = callOnlineBizComponent("nr.NRSEABase", "fCalDepr", reqDs, makeOnlineContext(context));
                context.getLogger().info("after resDS2 ########### : " + resDS2);
                
                IRecordSet rs = resDS2.getRecordSet("RS_LIST");
                if(rs.getRecordCount()>0)
                {
                    Map paramMap = SAPUtils.convertRecord2Map(rs.getRecord(0));
                    paramMap.put("USER_NO", context.getInParameter("USER_NO"));
                   //감가상각처리
                    dbInsert("IDeprDtl", paramMap);                //NR_마감감가상각상세 등록
                    dbUpdate("UAssetDeprInfo", paramMap);          //PR_단말기자산 회계금액Update
                    dbUpdate("UEqpCntrtDrlAllwnAmt", paramMap);    //PR_단말기계약상세 충당금금액Update
                }

 				processCnt++;
 				
 			}
 		};
     	return rh;
     }
     
     /**
      * 처리
      * @param context
      * @return
      */
      public AbsRecordHandler makeSuperRecordHandler(IBatchContext context) {
      	AbsRecordHandler rh = new AbsRecordHandler(context) {
  			
  			@Override
  			public void handleRecord(IRecord row) {
  				context.setProgressCurrent(getCurrentRecordCount()); // 진행률 표시
  				context.getLogger().debug("########### : " + row);
  				
                IDataSet reqDs = new DataSet();
                reqDs.putFieldMap(row);
                reqDs.putField("USER_NO", context.getInParameter("USER_NO"));

                dbInsert("IDepr", reqDs.getFieldMap(), context);
//                dbInsert("IDepr", row, context);
  				processCnt++;
  				
  			}
  		};
      	return rh;
      }     
     /**
      * 감가 상각 시뮬레이션 Method
      * @param orignalPrice
      * @param lifeMonth
      * @param startYMD
      * @param monthFlag
      * @return
     * @throws ParseException 
      */
     private List<HashMap<String, Double>> generateDeprStandard4Accnt(double orignalPrice, int lifeMonthCnt, String startYMD, boolean monthFlag) throws ParseException
     {
         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
         Date startDate = simpleDateFormat.parse(startYMD);
         
         Calendar startCal = Calendar.getInstance();
         startCal.setTime(startDate);
//         log.debug("startCal :"+startCal.getTime());
         
         Calendar endCal    =  (Calendar) startCal.clone();
         endCal.add(Calendar.MONTH, lifeMonthCnt);

//         log.debug("endCal :"+endCal.getTime());

    	 long DayCount = this.daysBetween(startCal, endCal);
    	 
         //log.debug("DayCount :"+DayCount);

    	 List<HashMap<String, Double>> returnList = new ArrayList<HashMap<String, Double>>();
    	 List<Asset4Depr> tempList = new ArrayList<Asset4Depr>();
    	 Asset4Depr tempObj ;
    	 
    	 Calendar prcCal    =  (Calendar) startCal.clone();
         double remainAMT   = orignalPrice;; //잔존가
     	 double deprAMT     = 0; //감가상각액
     	 double deprSumAMT  = 0; //감가상각누계액
    	 for(int i=0; i<DayCount; i++)
    	 {
    		 deprAMT = orignalPrice / DayCount;
    		 deprSumAMT += deprAMT;
    		 remainAMT  -= deprAMT;
    		 prcCal.add(Calendar.DAY_OF_MONTH, 1);
    		 
    		 tempObj = new Asset4Depr(orignalPrice, lifeMonthCnt, startYMD, simpleDateFormat.format(prcCal.getTime()), remainAMT, deprAMT, deprSumAMT);
    		 //log.debug(tempObj);
    		 tempList.add(tempObj);
    	 }

    	 return returnList;
     }
    
     /**
      * 감가상각용 클래스 dto
      * @author greatjin
      *
      */
     class Asset4Depr
     {
    	private double orignalPrice; //원가격
    	private int    lifeMonthCnt; //내용년수(개월)
    	private String startYMD    ; //시작일자
    	private String endYMD      ; //종료일(계산)
    	private String processYMD  ; //기준일자
    	private double remainAMT   ; //잔존가
    	private double deprAMT     ; //감가상각액
    	private double deprSumAMT  ; //감가상각누계액
    	
    	/**
    	 * constructor
    	 * @param orignalPrice
    	 * @param lifeMonthCnt
    	 * @param startYMD
    	 * @param processYMD
    	 * @param remainAMT
    	 * @param deprAMT
    	 * @param deprSumAMT
    	 */
    	public Asset4Depr(double orignalPrice, int lifeMonthCnt,
				String startYMD, String processYMD, double remainAMT,
				double deprAMT, double deprSumAMT) {
			super();
			this.orignalPrice = orignalPrice;
			this.lifeMonthCnt = lifeMonthCnt;
			this.startYMD = startYMD;
			this.processYMD = processYMD;
			this.remainAMT = remainAMT;
			this.deprAMT = deprAMT;
			this.deprSumAMT = deprSumAMT;
		}
		
		public double getOrignalPrice() {
			return orignalPrice;
		}
		public void setOrignalPrice(double orignalPrice) {
			this.orignalPrice = orignalPrice;
		}
		public int getLifeMonthCnt() {
			return lifeMonthCnt;
		}
		public void setLifeMonthCnt(int lifeMonthCnt) {
			this.lifeMonthCnt = lifeMonthCnt;
		}
		public String getStartYMD() {
			return startYMD;
		}
		public void setStartYMD(String startYMD) {
			this.startYMD = startYMD;
		}
		public String getEndYMD() {
			return endYMD;
		}
		public void setEndYMD(String endYMD) {
			this.endYMD = endYMD;
		}
		public String getProcessYMD() {
			return processYMD;
		}
		public void setProcessYMD(String processYMD) {
			this.processYMD = processYMD;
		}
		public double getRemainAMT() {
			return remainAMT;
		}
		public void setRemainAMT(double remainAMT) {
			this.remainAMT = remainAMT;
		}
		public double getDeprAMT() {
			return deprAMT;
		}
		public void setDeprAMT(double deprAMT) {
			this.deprAMT = deprAMT;
		}
		public double getDeprSumAMT() {
			return deprSumAMT;
		}
		public void setDeprSumAMT(double deprSumAMT) {
			this.deprSumAMT = deprSumAMT;
		}

		@Override
		public String toString() {
			return "Asset4Depr [orignalPrice=" + orignalPrice
					+ ", lifeMonthCnt=" + lifeMonthCnt + ", startYMD="
					+ startYMD + ", endYMD=" + endYMD + ", processYMD="
					+ processYMD + ", remainAMT=" + remainAMT + ", deprAMT="
					+ deprAMT + ", deprSumAMT=" + deprSumAMT + "]";
		}
    	 
    	 
     }
     
     /** 
      * 두 날자간 일자 비교 
      */
     public long daysBetween(Calendar startDate, Calendar endDate) {
         Calendar date = (Calendar) startDate.clone();
         long daysBetween = 0;
         while (date.before(endDate)) {
             date.add(Calendar.DAY_OF_MONTH, 1);
             daysBetween++;
         }
         return daysBetween;
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
            log.debug("단말기매각등록 BATCH 호출 결과:");
            log.debug(resDS);
        }
     }
    
}