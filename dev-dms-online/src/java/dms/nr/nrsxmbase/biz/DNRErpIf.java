package dms.nr.nrsxmbase.biz;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import fwk.constants.SlipConstants;
import fwk.utils.SAPUtils;
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
 * <li>단위업무명: [DM] 전표등록</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-08-20 14:44:10</li>
 * <li>작성자 : 진병수 (greatjin)</li>
 * </ul>
 *
 * @author 진병수 (greatjin)
 */
public class DNRErpIf extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DNRErpIf(){
		super();
	}

	/**
	 *
	 *
	 * @author 진병수 (greatjin)
	 * @since 2015-08-20 14:44:10
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dISlipData(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
		
//	    requestData.putField("USER_NO", getCommonArea(onlineCtx).getUserNo());
	    
	    // 1.쿼리문 호출
 		dbInsert("ISlipData", requestData, onlineCtx);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 진병수 (greatjin)
	 * @since 2015-08-20 14:44:10
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSSlipSeq(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    
	    IRecord ir  = dbSelectSingle("SSlipSeq", requestData, onlineCtx);
	    
	    responseData.putField(SlipConstants.SLIP_NO,ir.get(SlipConstants.SLIP_NO));
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 진병수 (greatjin)
	 * @since 2015-08-31 09:30:02
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUSlipStatus(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
//	    requestData.putField("USER_NO", getCommonArea(onlineCtx).getUserNo());
	    
	    // 1.쿼리문 호출
 	    dbUpdate("USlipStatus", requestData, onlineCtx);  
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 진병수 (greatjin)
	 * @since 2015-08-20 14:44:10
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSlipInfo(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    
	    IRecord ir  = dbSelectSingle("SSlipInfo", requestData, onlineCtx);
	    
	    responseData = SAPUtils.convertRecord2DataSet(ir);
	
	    return responseData;
	}
	

}
