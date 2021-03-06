package dms.sc.scsbase.biz;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;

/**
 * <ul>
 * <li>업무 그룹명 : HPC/업무공통</li>
 * <li>단위업무명: DSCAlrtMgmt</li>
 * <li>설  명 : 알림관리</li>
 * <li>작성일 : 2014-08-21 20:33:33</li>
 * <li>작성자 : 김석영 (kimsukyoung)</li>
 * </ul>
 *
 * @author 김석영 (kimsukyoung)
 */
public class DSCAlrtMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DSCAlrtMgmt() {
		super();
	}

	/**
	 * 알림발송번호채번
	 *
	 * @author
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSAlrtSndNo(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
		IRecord record = dbSelectSingle("SAlrtSndNo", requestData, onlineCtx);
		responseData.putFieldMap(record);
		return responseData;
	}

	/**
	 * MMS발송
	 *
	 * @author
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dISndMms(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
		dbInsert("ISndMms", requestData, onlineCtx);
		return responseData;
	}

	/**
	 * SMS발송
	 *
	 * @author
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dISndSms(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
		dbInsert("ISndSms", requestData, onlineCtx);
		return responseData;
	}

	/**
	 * 알림발송내역등록
	 *
	 * @author
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dIAlrtSndItm(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
		dbInsert("IAlrtSndItm", requestData, onlineCtx);
		return responseData;
	}

	/**
	 * 알림서비스조회
	 *
	 * @author
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSAlrtSvc(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
		IRecordSet rsReturn = dbSelect("SAlrtSvc", requestData, onlineCtx);
		responseData.putRecordSet("RS_ALRT_SVC_LIST", rsReturn);
		return responseData;
	}

	/**
	 * 알림서비스발송내역조회
	 *
	 * @author 이유미 (leeyoumee)
	 * @since 2014-11-17 14:47:40
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSAlrtSvcSndHstPaging(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
		IRecordSet rsReturn = dbSelect("SAlrtSvcSndHstPaging", requestData, onlineCtx);
		responseData.putRecordSet("RS_ALRT_SVC_SND_LIST", rsReturn);
		return responseData;
	}

	/**
	 * 알림서비스 발송내역 건수 조회
	 *
	 * @author 이유미 (leeyoumee)
	 * @since 2014-08-21 20:33:33
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSAlrtSvcSndHstTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
		IRecord record = dbSelectSingle("SAlrtSvcSndHstTotCnt", requestData, onlineCtx);
		if ( record != null ) {
			responseData.putFieldMap(record);
		}
		return responseData;
	}

    /**
	 * <pre>알림발송번호채번</pre>
	 *
	 * @author admin (admin)
	 * @since 2015-07-13 12:41:50
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
    public IDataSet dSAlrtSndNoNEW(IDataSet requestData, IOnlineContext onlineCtx) {
    	IDataSet responseData = new DataSet();
    	IRecord record = dbSelectSingle("SAlrtSndNoNEW", requestData, onlineCtx);
    	responseData.putFieldMap(record);
    	return responseData;
    }

    /**
	 *
	 *
	 * @author 이영진 (newnofixing)
	 * @since 2015-10-02 15:11:23
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUAlrtRslt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        dbUpdate("UAlrtRslt", requestData, onlineCtx);
        return responseData;
    }

}
