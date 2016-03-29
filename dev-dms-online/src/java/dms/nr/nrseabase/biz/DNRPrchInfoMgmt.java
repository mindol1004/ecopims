package dms.nr.nrseabase.biz;

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
 * <li>단위업무명: [DU]매입정보관리</li>
 * <li>설  명 : <pre>매입정보관리DU</pre></li>
 * <li>작성일 : 2015-07-17 15:37:37</li>
 * <li>작성자 : 이민재 (mindol1004)</li>
 * </ul>
 *
 * @author 이민재 (mindol1004)
 */
public class DNRPrchInfoMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DNRPrchInfoMgmt(){
		super();
	}

	/**
	 * <pre>매입정보 페이징 조회</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-07-17 15:37:37
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSPrchInfoLstPaging(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SPrchInfoLstPaging", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_PRCH_LIST", rsReturn);
	    return responseData;
	}

	/**
	 * <pre>매입정보 총건수 조회</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-07-17 15:37:37
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSPrchInfoTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SPrchInfoTotCnt", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putFieldMap(record);
	    return responseData;
	}

	/**
	 * <pre>매입정보상세조회</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-07-17 15:44:14
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSPrchInfoDtl(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SPrchInfoDtl", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_PRCH_DTL", rsReturn);
        
	    return responseData;
	}

    /**
	 * <pre>매입정보 리스트 조회</pre>
	 *
	 * @author 박홍서 (uni9401)
	 * @since 2015-07-17 15:37:37
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSPrchInfoLst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SPrchInfoLst", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_PRCH_LST", rsReturn);
        return responseData;
    }
  
}
