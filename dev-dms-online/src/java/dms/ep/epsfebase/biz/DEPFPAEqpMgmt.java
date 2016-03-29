package dms.ep.epsfebase.biz;

import java.util.Map;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.IResultMessage;
import nexcore.framework.core.data.ResultMessage;
import nexcore.framework.core.exception.BizRuntimeException;


/**
 * <ul>
 * <li>업무 그룹명 : dms/에코폰</li>
 * <li>단위업무명: [DU]FPA단말기관리</li>
 * <li>설  명 : <pre>FPA단말기관리</pre></li>
 * <li>작성일 : 2016-02-16 10:09:18</li>
 * <li>작성자 : 양재석 (jsyang)</li>
 * </ul>
 *
 * @author 양재석 (jsyang)
 */
public class DEPFPAEqpMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DEPFPAEqpMgmt(){
		super();
	}

    /**
	 * <pre>FPA단말기등록</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2016-02-16 10:12:10
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dIFPAEqpRgst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        dbInsert("IFPAEqpRgst", requestData, onlineCtx);
    
        return responseData;
    }

    /**
	 * <pre>FPA단말기조회</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2016-02-16 10:09:18
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqFPAEqpList(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SInqFPAEqpList", requestData, onlineCtx);            
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_FPA_EQP_LIST", rsReturn);
    
        return responseData;
    }

    /**
	 * <pre>FPA단말기정보수정매입</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2016-02-16 10:09:18
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUFPAEqpInfoUpdPrch(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        dbInsert("UFPAEqpInfoUpdPrch", requestData, onlineCtx);
    
        return responseData;
    }

    /**
	 * <pre>FPA단말기판매평균금액목록조회</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2016-02-16 15:57:45
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqFPAEqpSellAvgAmtList(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SInqFPAEqpSellAvgAmtList", requestData, onlineCtx);            
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_FPA_EQP_SELL_LIST", rsReturn);
    
        return responseData;
    }

    /**
	 * <pre>FPA등록단말기정보수정</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2016-02-25 16:08:09
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUFPAEqpInfoUpdRgst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        dbInsert("UFPAEqpInfoUpdRgst", requestData, onlineCtx);
    
        return responseData;
    }

    /**
	 *
	 *
	 * @author 정동현 (jjddhh123)
	 * @since 2016-02-29 16:53:38
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dIFPAEqpSellAvgAmtSaveSend(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    
        // 1.쿼리문 호출
        dbInsert("IFPAEqpSellAvgAmtSaveSend", requestData, onlineCtx);
    
        return responseData;
    }

    /**
	 * <pre>FPA단말기판매평균단가전송이력조회</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2016-03-25 11:32:24
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqFPAEqpSellAvgSendList(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SInqFPAEqpSellAvgSendList", requestData, onlineCtx);            
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_FPA_EQP_SELL_SEND_LIST", rsReturn);
    
        return responseData;
    }
  
}
