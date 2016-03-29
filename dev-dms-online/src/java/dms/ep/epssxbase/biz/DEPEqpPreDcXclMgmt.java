package dms.ep.epssxbase.biz;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;


/**
 * <ul>
 * <li>업무 그룹명 : dms/에코폰</li>
 * <li>단위업무명: [DU]단말기대금선할인정산관리</li>
 * <li>설  명 : <pre>단말기대금선할인정산관리 DU</pre></li>
 * <li>작성일 : 2015-10-23 09:56:19</li>
 * <li>작성자 : 이민재 (mindol1004)</li>
 * </ul>
 *
 * @author 이민재 (mindol1004)
 */
public class DEPEqpPreDcXclMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DEPEqpPreDcXclMgmt(){
		super();
	}

    /**
	 * <pre>[DM]단말기대금선할인정산목록조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-10-23 09:56:19
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSEqpDcinadXclPaging(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();      
        // 1.쿼리문 호출                
        IRecordSet rsReturn = dbSelect("SEqpDcinadXclPaging", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_EQP_DCINAD_LIST", rsReturn);     
        return responseData;
    }

    /**
	 * <pre>[DM]단말기대금선할인정산상세엑셀목록조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-10-28 11:09:07
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSEqpDcinadXclDtlExcelLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    
        // 1.쿼리문 호출                
        IRecordSet rsReturn = dbSelect("SEqpDcinadXclDtlExcelLst", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_EQP_DCINAD_EXCEL_LIST", rsReturn); 
    
        return responseData;
    }

    /**
	 * <pre>[DM]단말기대금선할인정산총건수</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-10-28 17:59:29
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSEqpDcinadXclTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SEqpDcinadXclTotCnt", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putFieldMap(record);  
    
        return responseData;
    }

    /**
	 * <pre>[DM]단말기대금선할인전표발행여부</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-10-30 10:22:51
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSEqpPreDcSlipYn(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SEqpPreDcSlipYn", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putFieldMap(record); 
    
        return responseData;
    }

	/**
	 * <pre>[DM]전표발행대상사업자번호ERP체크리스트</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-11-27 18:16:58
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSEqpBlicensNoCheckList(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출     
        IRecordSet rsReturn = dbSelect("SEqpBlicensNoCheckList", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putRecordSet("BlicensNoCheckList",rsReturn);
    
        return responseData;
    }

    /**
	 *
	 *
	 * @author 정동현 (jjddhh123)
	 * @since 2015-12-07 14:28:50
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSEqpDcinadXclDtlExcelTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    // 1.쿼리문 호출     
        IRecordSet rsReturn = dbSelect("SEqpDcinadXclDtlExcelTotCnt", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putRecordSet("EXCEL_TOTAL_CNT",rsReturn);
        return responseData;
    }

    /**
	 * <pre>단말기대금선할인엑셀업로드검증조회</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2016-01-22 13:09:59
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSEqpPreDcXclExcelList(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SEqpPreDcXclExcelList", requestData, onlineCtx);
        
        // 2.결과값 셋팅     
        if ( record != null ) {
            responseData.putFieldMap(record);
        }
    
        return responseData;
    }

    /**
	 * <pre>단말기대금선할인엑셀저장시퀀스채번</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-10-23 09:56:19
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSEqpPreDcXclExcelSeq(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SEqpPreDcXclExcelSeq", requestData, onlineCtx);
        
        responseData.putFieldMap(record);
    
        return responseData;
    }

    /**
	 * <pre>단말기대금선할인삭제</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2016-01-22 13:16:48
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUEqpPreDcXclExcelDel(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        dbUpdate("UEqpPreDcXclExcelDel", requestData, onlineCtx);
    
        return responseData;
    }

    /**
	 * <pre>단말기대금선할인상세삭제</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2016-01-22 13:19:05
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUEqpPreDcXclExcelDtlDel(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        dbUpdate("UEqpPreDcXclExcelDtlDel", requestData, onlineCtx);
    
        return responseData;
    }

    /**
	 * <pre>단말기대금클럽T엑셀저장</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2016-01-22 13:22:45
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dIEqpPreDcXclExcel(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        dbInsert("IEqpPreDcXclExcel", requestData, onlineCtx); 
    
        return responseData;
    }

    /**
	 * <pre>단말기대금선할인엑셀상세저장</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2016-01-22 13:25:04
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dIEqpPreDcXclExcelDtl(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        dbInsert("IEqpPreDcXclExcelDtl", requestData, onlineCtx);  
    
        return responseData;
    }

    /**
	 * <pre>단말기대금선할인시퀀스조회</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2016-01-25 10:15:51
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSEqpPreDcXclSeq(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SEqpPreDcXclSeq", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_EQP_PREDC_SEQ", rsReturn);
    
        return responseData;
    }
  
}
