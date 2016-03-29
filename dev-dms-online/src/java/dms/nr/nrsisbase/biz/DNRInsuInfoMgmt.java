package dms.nr.nrsisbase.biz;

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
 * <li>단위업무명: [DU]보증보험관리</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-08-18 12:28:08</li>
 * <li>작성자 : 안진갑 (bella21cjk)</li>
 * </ul>
 *
 * @author 안진갑 (bella21cjk)
 */
public class DNRInsuInfoMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DNRInsuInfoMgmt(){
		super();
	}

    /**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2015-08-18 12:29:51
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInsuInfoLstPaging(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 처리 결과값을 responseData에 넣어서 리턴하십시요 
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SInsuInfoLstPaging", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_INSU_INFO_LIST", rsReturn);
        return responseData;
    }

    /**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2015-08-18 12:31:39
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInsuInfoTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 처리 결과값을 responseData에 넣어서 리턴하십시요 
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SInsuInfoTotCnt", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putFieldMap(record); 
        return responseData;
    }

    /**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2015-08-18 12:28:08
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInsuInfoSum(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 처리 결과값을 responseData에 넣어서 리턴하십시요 
        IRecordSet rsReturn = dbSelect("SInsuInfoSum", requestData, onlineCtx);
        
        responseData.putRecordSet("RS_INSU_INFO_SUM", rsReturn);
        return responseData;
    }
  
}
