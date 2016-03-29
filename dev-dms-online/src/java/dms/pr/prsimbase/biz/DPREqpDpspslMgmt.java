package dms.pr.prsimbase.biz;

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
 * <li>업무 그룹명 : dms/임대R</li>
 * <li>단위업무명: [DU]단말기매각관리</li>
 * <li>설  명 : <pre>[DU]단말기매각관리</pre></li>
 * <li>작성일 : 2015-09-01 16:51:26</li>
 * <li>작성자 : 양재석 (jsyang)</li>
 * </ul>
 *
 * @author 양재석 (jsyang)
 */
public class DPREqpDpspslMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DPREqpDpspslMgmt(){
		super();
	}

    /**
	 * <pre>[DM]매각대상단말기조회</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-01 16:52:58
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqDpspslObjEqpListPaging(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SInqDpspslObjEqpListPaging", requestData, onlineCtx);
        
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_DPSPSL_EQP_LIST", rsReturn);
    
        return responseData;
    }

    /**
	 * <pre>[DM]매각대상단말기조회총건수</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-01 16:53:49
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- field : TOTAL_CNT [필드1]
	 * </pre>
	 */
	public IDataSet dSInqDpspslObjEqpListTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SInqDpspslObjEqpListTotCnt", requestData, onlineCtx);
        
        // 2.결과값 셋팅     
        responseData.putFieldMap(record); 
    
        return responseData;
    }

    /**
	 * <pre>매각대상단말기출고일련번호</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-01 17:56:41
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- field : EQP_OUT_NO [단말기출고번호]
	 * </pre>
	 */
	public IDataSet dSDpspslObjEqpOutNum(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SDpspslObjEqpOutNum", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putFieldMap(record);
    
        return responseData;
    }

    /**
	 * <pre>매각대상단말기출고등록</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-01 17:57:43
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dIDpspslObjEqpOutReg(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        try {       
            dbInsert("IDpspslObjEqpOutReg", requestData, onlineCtx);   
        }
        catch ( BizRuntimeException e ) {
            throw e;
        } 
    
        return responseData;
    }

    /**
	 * <pre>매각대상단말기입고일련번호</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-01 17:58:37
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- field : EQP_IN_NO [단말기입고번호]
	 * </pre>
	 */
	public IDataSet dSDpspslObjEqpInNum(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SDpspslObjEqpInNum", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putFieldMap(record);
    
        return responseData;
    }

    /**
	 * <pre>매각대상단말기입고등록</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-01 17:59:15
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dIDpspslObjEqpInReg(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        try {       
            dbInsert("IDpspslObjEqpInReg", requestData, onlineCtx);   
        }
        catch ( BizRuntimeException e ) {
            throw e;
        } 
    
        return responseData;
    }

    /**
	 * <pre>매각대상단말기자산정보수정</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-01 17:59:58
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUDpspslObjEqpAssetInfoUpd(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        try {       
            dbInsert("UDpspslObjEqpAssetInfoUpd", requestData, onlineCtx); 
        }
        catch ( BizRuntimeException e ) {
            throw e;
        } 
    
        return responseData;
    }

    /**
	 * <pre>매각대상단말기계약정보수정</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-17 13:57:11
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUDpspsObjEqpCntrtInfoUpd(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        try {       
            dbInsert("UDpspsObjEqpCntrtInfoUpd", requestData, onlineCtx); 
        }
        catch ( BizRuntimeException e ) {
            throw e;
        } 
    
        return responseData;
    }
  
}
