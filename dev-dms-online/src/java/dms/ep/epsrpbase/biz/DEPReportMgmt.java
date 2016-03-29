package dms.ep.epsrpbase.biz;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;


/**
 * <ul>
 * <li>업무 그룹명 : dms/에코폰</li>
 * <li>단위업무명: [DU]보고서관리</li>
 * <li>설  명 : <pre>보고서관리</pre></li>
 * <li>작성일 : 2015-09-23 13:06:55</li>
 * <li>작성자 : 정동현 (jjddhh123)</li>
 * </ul>
 *
 * @author 정동현 (jjddhh123)
 */
public class DEPReportMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DEPReportMgmt(){
		super();
	}

    /**
	 *
	 *
	 * @author 정동현 (jjddhh123)
	 * @since 2015-09-23 13:07:11
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSMthSaleList(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출     
        IRecordSet rsReturn = dbSelect("SMthSaleList", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putRecordSet("RS_MTH_SALE_LIST", rsReturn);
    
        return responseData;
    }

    /**
	 * <pre>B2B판매실적현황조회</pre>
	 *
	 * @author 박민정 (dangnagu2)
	 * @since 2015-09-23 13:06:55
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSB2BSaleList(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출     
        IRecordSet rsReturn = dbSelect("SB2BSaleList", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putRecordSet("RS_B2B_SALE_LIST", rsReturn);
    
        return responseData;
    }

    /**
	 *
	 *
	 * @author 정동현 (jjddhh123)
	 * @since 2015-10-05 19:29:53
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSDirPrchList(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출     
        IRecordSet rsReturn = dbSelect("SDirPrchList", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putRecordSet("RS_DIR_PRCH_LIST", rsReturn);
    
        return responseData;
    }

    /**
	 * <pre>NEW보상기변실적현황조회</pre>
	 *
	 * @author 박민정 (dangnagu2)
	 * @since 2015-10-06 14:09:35
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqNewCmpChgSaleList(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출     
        IRecordSet rsReturn = dbSelect("SInqNewCmpChgSaleList", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putRecordSet("RS_NEW_CHG_SALE_LIST", rsReturn);
    
        return responseData;
    }

    /**
	 *
	 *
	 * @author 정동현 (jjddhh123)
	 * @since 2015-10-20 18:13:08
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSB2CSaleList(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출     
        IRecordSet rsReturn = dbSelect("SB2CSaleList", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putRecordSet("RS_B2C_SALE_LIST", rsReturn);
    
        return responseData;
    }

    /**
	 * <pre>T에코실적현황조회</pre>
	 *
	 * @author 박민정 (dangnagu2)
	 * @since 2015-10-21 13:35:31
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqTEchoSaleList(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출     
        IRecordSet rsReturn = dbSelect("SInqTEchoSaleList", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putRecordSet("RS_TECHO_SALE_LIST", rsReturn);
    
        return responseData;
    }

    /**
	 * <pre>제휴채널실적현황조회</pre>
	 *
	 * @author 박민정 (dangnagu2)
	 * @since 2015-10-23 15:02:18
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqJoinSaleList(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출     
        IRecordSet rsReturn = dbSelect("SInqJoinSaleList", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putRecordSet("RS_JOIN_SALE_LIST", rsReturn);
    
        return responseData;
    }

    /**
	 * <pre>클럽T실적현황조회</pre>
	 *
	 * @author 박민정 (dangnagu2)
	 * @since 2015-10-26 09:59:23
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqClubTSaleList(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출     
        IRecordSet rsReturn = dbSelect("SInqClubTSaleList", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putRecordSet("RS_CLUBT_SALE_LIST", rsReturn);
    
        return responseData;
    }

    /**
	 *
	 *
	 * @author 정동현 (jjddhh123)
	 * @since 2015-09-23 13:06:55
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSSaleRsltListPaging(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출     
        IRecordSet rsReturn = dbSelect("SSaleRsltListPaging", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putRecordSet("RS_SALE_RSLT_LIST", rsReturn);
    
        return responseData;
    }

    /**
	 *
	 *
	 * @author 정동현 (jjddhh123)
	 * @since 2015-11-30 17:12:13
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSSaleRsltListTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SSaleRsltListTotCnt", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putFieldMap(record);
    
        return responseData;
    }

	/**
	 * <pre>[DM]NEW보상기변실적현황피벗</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-09-23 13:06:55
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqNewCmpChgSaleListPivot(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출     
        IRecordSet rsReturn = dbSelect("SInqNewCmpChgSaleListPivot", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putRecordSet("RS_NEW_CHG_SALE_LIST", rsReturn);
    
        return responseData;
    }

	/**
	 * <pre>[DM]T에코실적현황조회피벗</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-09-23 13:06:55
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqTEchoSaleListPivot(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출     
        IRecordSet rsReturn = dbSelect("SInqTEchoSaleListPivot", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putRecordSet("RS_TECHO_SALE_LIST", rsReturn);
    
        return responseData;
    }

	/**
	 *
	 *
	 * @author 김상오 (myneti99)
	 * @since 2016-02-19 15:04:47
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqClubTSaleListPivot(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출     
        IRecordSet rsReturn = dbSelect("SInqClubTSaleListPivot", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putRecordSet("RS_CLUBT_SALE_LIST", rsReturn);
    
        return responseData;
    }
  
}
