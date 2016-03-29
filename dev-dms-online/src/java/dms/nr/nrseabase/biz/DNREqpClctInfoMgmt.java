package dms.nr.nrseabase.biz;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;


/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [DU]단말기회수정보관리</li>
 * <li>설  명 : <pre>단말기회수정보관리DU</pre></li>
 * <li>작성일 : 2015-07-17 17:02:32</li>
 * <li>작성자 : 이민재 (mindol1004)</li>
 * </ul>
 *
 * @author 이민재 (mindol1004)
 */
public class DNREqpClctInfoMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DNREqpClctInfoMgmt(){
		super();
	}

	/**
	 * <pre>단말기회수정보조회</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-07-17 17:05:27
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSEqpClctInfoLstPaging(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	  	IRecordSet rsReturn = dbSelect("SEqpClctInfoLstPaging", requestData, onlineCtx);
	  	// 2.결과값 셋팅
	  	responseData.putRecordSet("RS_EQP_CLCT_INFO_LIST", rsReturn);
	
	    return responseData;
	}

	/**
	 * <pre>단말기회수정보총건수</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-07-17 17:06:09
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	@SuppressWarnings("unchecked")
	public IDataSet dSEqpClctInfoTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출	    
	  	IRecord record = dbSelectSingle("SEqpClctInfoTotCnt", requestData, onlineCtx);
	  	// 2.결과값 셋팅	    
	  	responseData.putFieldMap(record);
	
	    return responseData;
	}

	/**
	 * <pre>단말기회수정보상세조회</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-07-17 17:07:08
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSEqpClctInfoDtl(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	 	IRecordSet rsReturn = dbSelect("SEqpClctInfoDtl", requestData, onlineCtx);
	 	// 2.결과값 셋팅
	 	responseData.putRecordSet("RS_EQP_CLCT_INFO_DTL", rsReturn);
	
	    return responseData;
	}
  
}
