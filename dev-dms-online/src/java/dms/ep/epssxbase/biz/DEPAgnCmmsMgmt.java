package dms.ep.epssxbase.biz;

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
 * <li>단위업무명: [DU]대리점수수료관리</li>
 * <li>설  명 : <pre>[DU]대리점수수료관리</pre></li>
 * <li>작성일 : 2015-10-05 10:36:23</li>
 * <li>작성자 : 김윤환 (kyh0904)</li>
 * </ul>
 *
 * @author 김윤환 (kyh0904)
 */
public class DEPAgnCmmsMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DEPAgnCmmsMgmt(){
		super();
	}

    /**
	 * <pre>[DM]대리점수수료목록조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-10-22 10:41:42
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSAgnCmmsPaging(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SAgnCmmsPaging", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_AGN_CMMS_LIST", rsReturn);
        return responseData;
    }

    /**
	 * <pre>[DM]대리점수수료총건수조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-10-22 10:41:50
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSAgnCmmsTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SAgnCmmsTotCnt", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putFieldMap(record);
    
        return responseData;
    }

    /**
	 * <pre>[DM]대리점수수료등록</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-10-22 15:35:36
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dIAgnCmms(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        
        // 1.쿼리문 호출 
        dbInsert("ITbEpIncenXcl", requestData, onlineCtx);
        
        return responseData;
    }

    /**
	 * <pre>[DM]대리점중복검사</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-10-23 15:34:44
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSAgnRpetErr(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();    
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SAgnRpetErrExmn", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putFieldMap(record); 
    
        return responseData;
    }

	/**
	 * <pre>[DM]대리점수수료삭제</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-10-22 10:27:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUAgnCmmsDel(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        
        // 1.쿼리문 호출 
        dbUpdate("UAgnCmmsDel", requestData, onlineCtx);
        
        return responseData;
    }

	/**
	 * <pre>[DM]대리점수수료조회</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2016-01-20 16:27:37
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqCmmsList(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SInqCmmsList", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_AGN_CMMS_LIST", rsReturn);
        return responseData;
    }

    
  
}
