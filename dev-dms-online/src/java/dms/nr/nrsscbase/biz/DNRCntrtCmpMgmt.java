package dms.nr.nrsscbase.biz;

import java.util.Map;

import fwk.utils.HpcUtils;
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
 * <li>단위업무명: [DU]계약손해배상금관리</li>
 * <li>설  명 : <pre>계약손해배상금관리DU</pre></li>
 * <li>작성일 : 2015-07-17 11:21:35</li>
 * <li>작성자 : 이민재 (mindol1004)</li>
 * </ul>
 *
 * @author 이민재 (mindol1004)
 */
public class DNRCntrtCmpMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DNRCntrtCmpMgmt(){
		super();
	}

	/**
	 * <pre>계약손해배상금조회</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-07-17 11:25:37
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSCntrtCmpLstPaging(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 처리 결과값을 responseData에 넣어서 리턴하십시요 
	    // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SCntrtCmpLstPaging", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_CNTRT_CMP_LIST", rsReturn);
        
	    return responseData;
	}

	/**
	 * <pre>계약손해배상금총건수</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-07-17 11:26:22
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSCntrtCmpTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    
        // 처리 결과값을 responseData에 넣어서 리턴하십시요 
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SCntrtCmpTotCnt", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putFieldMap(record); 
        
	    return responseData;
	}

	/**
	 * <pre>계약손해배상금상세조회</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-07-17 11:26:46
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSCntrtCmpDtl(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 처리 결과값을 responseData에 넣어서 리턴하십시요 
	       // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SCntrtCmpDtl", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_CNTRT_CMP_DTL", rsReturn);
	    return responseData;
	}
  
}
