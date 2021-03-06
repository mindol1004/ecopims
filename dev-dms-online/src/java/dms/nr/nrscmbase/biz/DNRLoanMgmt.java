package dms.nr.nrscmbase.biz;

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
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [DU]SKN여신관리</li>
 * <li>설  명 : <pre>[DU]SKN여신관리</pre></li>
 * <li>작성일 : 2015-07-23 09:13:28</li>
 * <li>작성자 : 박홍서 (uni9401)</li>
 * </ul>
 *
 * @author 박홍서 (uni9401)
 */
public class DNRLoanMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DNRLoanMgmt(){
		super();
	}

    /**
	 * <pre>SKN여신 총건수 조회</pre>
	 *
	 * @author 박홍서 (uni9401)
	 * @since 2015-07-23 09:17:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSSKNLoanTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SSKNLoanTotCnt", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putFieldMap(record);
    
        return responseData;
    }

    /**
	 * <pre>SKN여신 페이징 조회</pre>
	 *
	 * @author 박홍서 (uni9401)
	 * @since 2015-07-23 09:18:34
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSSKNLoanPaging(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SSKNLoanPaging", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_SKN_PG", rsReturn);
        return responseData;
    }

    /**
	 * <pre>SKN여신 조회</pre>
	 *
	 * @author 박홍서 (uni9401)
	 * @since 2015-07-23 09:19:47
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSSKNLoanLst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SSKNLoanLst", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_SKN_LST", rsReturn);
        return responseData;
    }

    /**
	 * <pre>SKN여신 상세 조회</pre>
	 *
	 * @author 박홍서 (uni9401)
	 * @since 2015-07-23 15:15:04
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSSKNLoanDtlLst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SSKNLoanDtlLst", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_SKN_DTL_LST", rsReturn);
        return responseData;
    }

    /**
	 * <pre>SKT여신 상세 총건수 조회</pre>
	 *
	 * @author 박홍서 (uni9401)
	 * @since 2015-07-23 15:15:47
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSSKNLoanDtlTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SSKNLoanDtlTotCnt", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putFieldMap(record);
    
        return responseData;
    }

    /**
	 * <pre>SKN여신 상세 페이징 조회</pre>
	 *
	 * @author 박홍서 (uni9401)
	 * @since 2015-07-23 15:18:51
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSSKNLoanDtlPaging(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SSKNLoanDtlPaging", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_SKN_DTL_PG", rsReturn);
        return responseData;
    }

    /**
	 * <pre>신청서항목체크 페이징 조회</pre>
	 *
	 * @author 박홍서 (uni9401)
	 * @since 2015-08-04 13:50:37
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSApplfChkPaging(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SApplfChkPaging", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_NR_APPLF_PG", rsReturn);
        return responseData;
    }

    /**
	 * <pre>신청서항목체크 총건수 조회</pre>
	 *
	 * @author 박홍서 (uni9401)
	 * @since 2015-08-04 13:51:15
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSApplfChkTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SApplfChkTotCnt", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putFieldMap(record);
    
        return responseData;
    }

    /**
	 * <pre>신청서항목체크 리스트 조회</pre>
	 *
	 * @author 박홍서 (uni9401)
	 * @since 2015-08-04 13:52:00
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSApplfChkLst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SApplfChkLst", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_NR_APPLF", rsReturn);
        return responseData;
    }

    /**
	 * <pre>신청서항목체크 조회상세</pre>
	 *
	 * @author 박홍서 (uni9401)
	 * @since 2015-07-23 09:13:28
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
    public IDataSet dSApplfChkDtl(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SApplfChkDtl", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_NR_APPLF_DTL", rsReturn);
        return responseData;
    }
  
}
