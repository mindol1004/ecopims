package dms.ep.epsbibase.biz;

import java.util.Map;

import org.apache.commons.logging.Log;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.IResultMessage;
import nexcore.framework.core.data.ResultMessage;
import nexcore.framework.core.exception.BizRuntimeException;
import nexcore.framework.core.log.LogManager;


/**
 * <ul>
 * <li>업무 그룹명 : dms/에코폰</li>
 * <li>단위업무명: [DU]박스바코드관리</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-09-02 11:00:17</li>
 * <li>작성자 : 이하나 (hana526)</li>
 * </ul>
 *
 * @author 이하나 (hana526)
 */
public class DEPBoxBarcdMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DEPBoxBarcdMgmt(){
		super();
	}

	/**
	 *
	 *
	 * @author 이하나 (hana526)
	 * @since 2015-09-02 11:00:17
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUBoxBarcdConsultInfo(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();

        dbUpdate("UBoxBarcdConsultInfo", requestData, onlineCtx);

        return responseData;
	}

	/**
	 *
	 *
	 * @author 이하나 (hana526)
	 * @since 2015-09-02 11:00:17
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUBoxBarcdPrchsInfo(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();

        dbUpdate("UBoxBarcdPrchsInfo", requestData, onlineCtx);     	
     	
        return responseData;
	}

    /**
	 *
	 *
	 * @author 정동현 (jjddhh123)
	 * @since 2016-02-17 10:02:22
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUBoxBarcdFpaEqpInfo(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();

        dbUpdate("UBoxBarcdFpaEqpInfo", requestData, onlineCtx);         
        
        return responseData;
    }
  
}
