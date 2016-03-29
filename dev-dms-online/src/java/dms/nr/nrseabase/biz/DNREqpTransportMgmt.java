package dms.nr.nrseabase.biz;

import java.util.Map;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.exception.BizRuntimeException;
import nexcore.framework.core.util.StringUtils;


/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [DU]SKN물류외입고관리</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-09-10 16:04:51</li>
 * <li>작성자 : 장미진 (kuramotojin)</li>
 * </ul>
 *
 * @author 장미진 (kuramotojin)
 */
public class DNREqpTransportMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DNREqpTransportMgmt(){
		super();
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-09-10 17:11:07
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUEqpTransprotSave(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    int cnt = dbUpdate("UEqpTransprotSave", requestData, onlineCtx);
	
	    	if (cnt == 0 ){
	    		throw new BizRuntimeException("DMS00009"); // 시스템 오류가 발생하였습니다.
	    	}
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-09-11 12:30:36
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqEqpTransportLstPaging(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	  	IRecordSet rsReturn = dbSelect("SInqEqpTransportLstPaging", requestData, onlineCtx);
	  	// 2.결과값 셋팅
	  	responseData.putRecordSet("RS_TRANSPORT_LIST", rsReturn);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-09-11 12:31:50
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	@SuppressWarnings("unchecked")
	public IDataSet dSInqEqpTransportLstTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출	    
	  	IRecord record = dbSelectSingle("SInqEqpTransportLstTotCnt", requestData, onlineCtx);
	  	// 2.결과값 셋팅	    
	  	responseData.putFieldMap(record);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-09-11 17:26:58
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	@SuppressWarnings("unchecked")
	public IDataSet dSEqpTransGetAssetNo(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();

	    // 1.회수테이블&택배테이블에 등록된것이 있는지 확인	    
	    IRecord recordCnt = dbSelectSingle("SEqpTransportGetSerNo", requestData, onlineCtx);	//택배테이블 뒤져서 
	    int ctn = recordCnt.getInt("TOTAL_CNT");
	    
	    if( ctn < 1 ){
	    	IRecordSet rsReturn = dbSelect("SEqpTransGetAssetNo", requestData, onlineCtx);//자산번호랑 색상코드 갖고와서
	    // 2.자산테이블에 등록된것이 있을때 ASSET_NO를 담는다    
	    	
		    if (rsReturn.getRecordCount() > 0) {
	    		responseData.putRecordSet("RS_ASSET_LIST", rsReturn);
    		}else{
    			throw new BizRuntimeException("DMS00085"); //해당되는 일련번호를 가진 모델이 없습니다.
    		}
	    			
    	}else{
    		
    	    throw new BizRuntimeException("DMS00071"); //이미 처리된 건이 있습니다.
    	}
	    
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-09-11 18:05:13
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	@SuppressWarnings("unchecked")
	public IDataSet dSEqpTransportSeq(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출	    
	    IRecord record = dbSelectSingle("SEqpTransportSeq", requestData, onlineCtx);
	    // 2.결과값 셋팅	    
	    responseData.putFieldMap(record);
	    
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-09-11 18:09:16
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	@SuppressWarnings("unused")
	public IDataSet dIEqpTransportInfoSave(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    IRecordSet transportLst = requestData.getRecordSet("RS_TRANSPORT_LIST");
	    
	    Map userPlus = null;
	    for(int i=0; i<transportLst.getRecordCount(); i++)
	    {
	    	userPlus = transportLst.getRecordMap(i);
	    	userPlus.put("USER_NO", requestData.getField("USER_NO"));
		    // 1.쿼리문 호출
	    	dbInsert("IEqpTransportInfoSave", userPlus, onlineCtx);
	    	
	    }
	  
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-09-14 11:20:00
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	@SuppressWarnings("unchecked")
	public IDataSet dSEqpTransGetEqpClctNo(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출	    
	    IRecord record = dbSelectSingle("SEqpTransGetEqpClctNo", requestData, onlineCtx);
	    // 2.결과값 셋팅	    
	    responseData.putFieldMap(record);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-09-14 13:12:27
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUEqpTransportUpdate(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    dbUpdate("UEqpTransportUpdate", requestData.getFieldMap(), onlineCtx);		//운송테이블 update
	    dbUpdate("UEqpTransportClctUpdate", requestData.getFieldMap(), onlineCtx);	//회수테이블 update	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-09-14 16:31:51
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dDEqpTransportDelete(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    dbDelete("DEqpTransportDelete", requestData, onlineCtx); //운송테이블 delete
	    dbUpdate("DEqpTransportClctDelete", requestData, onlineCtx); //회수테이블 delete
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-11-05 15:33:12
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dIEqpTransprotClctObj(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    dbInsert("IEqpTransprotClctObj", requestData.getFieldMap(), onlineCtx);
	
	    return responseData;
	}
  
}