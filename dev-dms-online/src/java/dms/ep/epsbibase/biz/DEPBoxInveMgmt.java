package dms.ep.epsbibase.biz;

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
 * <li>업무 그룹명 : dms/임대R</li>
 * <li>단위업무명: [DU]임대폰분실관리</li>
 * <li>설  명 : <pre>[DU]임대폰분실관리</pre></li>
 * <li>작성일 : 2015-07-22 18:18:21</li>
 * <li>작성자 : 이준우 (elmsliw)</li>
 * </ul>
 *
 * @author 이준우 (elmsliw)
 */
public class DEPBoxInveMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DEPBoxInveMgmt(){
		super();
	}

	/**
	 * <pre>[DM]박스재고조회</pre>
	 *
	 * @author 이준우 (elmsliw)
	 * @since 2015-08-24 09:12:05
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
    public IDataSet dSBoxInveLstPaging(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
    	IRecordSet rsReturn = dbSelect("SBoxInveLstPaging", requestData, onlineCtx);
    	
    	// 2.결과값 셋팅
    	responseData.putRecordSet("RS_BIL_LIST", rsReturn);
    	
        return responseData;
    }

    /**
	 * <pre>[DM]박스재고조회총건수</pre>
	 *
	 * @author 이준우 (elmsliw)
	 * @since 2015-08-24 09:12:05
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
    public IDataSet dSBoxInveLstTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출     
    	IRecord record = dbSelectSingle("SBoxInveLstTotCnt", requestData, onlineCtx);
    	
    	// 2.결과값 셋팅     
    	responseData.putFieldMap(record);
    
        return responseData;
    }



    /**
	 * <pre>[DM]박스재고정보조회</pre>
	 *
	 * @author 이준우 (elmsliw)
	 * @since 2015-08-24 09:12:05
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
    public IDataSet dSBoxInveInfoLst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        IRecordSet rsInfoReturn = dbSelect("SBoxInfoInveLst", requestData, onlineCtx);
    	
    	// 2.결과값 셋팅
        responseData.putRecordSet("RS_BOX_INFO", rsInfoReturn);
    
        return responseData;
    }



    /**
     * <pre>[DM]바코드관리정보수정</pre>
     *
     * @author 이준우 (elmsliw)
     * @since 2015-08-24 09:12:05
     *
     * @param requestData 요청정보 DataSet 객체
     * @param onlineCtx   요청 컨텍스트 정보
     * @return 처리결과 DataSet 객체
     */
    public IDataSet dUBoxInveTbEBBL(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
     	dbUpdate("UBoxInveTbEBBL", requestData, onlineCtx);
     	
        return responseData;
    }

    /**
     * <pre>[DM]상담관리정보수정</pre>
     *
     * @author 이준우 (elmsliw)
     * @since 2015-08-24 09:12:05
     *
     * @param requestData 요청정보 DataSet 객체
     * @param onlineCtx   요청 컨텍스트 정보
     * @return 처리결과 DataSet 객체
     */
    public IDataSet dUBoxInveTbECCM(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
     	dbUpdate("UBoxInveTbECCM", requestData, onlineCtx);
     	
        return responseData;
    }

    /**
     * <pre>[DM]매입관리정보수정</pre>
     *
     * @author 이준우 (elmsliw)
     * @since 2015-08-24 09:12:05
     *
     * @param requestData 요청정보 DataSet 객체
     * @param onlineCtx   요청 컨텍스트 정보
     * @return 처리결과 DataSet 객체
     */
    public IDataSet dUBoxInveTbECPM(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
     	dbUpdate("UBoxInveTbECPM", requestData, onlineCtx);
     	
        return responseData;
    }

	/**
	 * <pre>[DM]박스재고상세번호초기화</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-08-24 09:12:05
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUBoxBarCdDtlBoxInit(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
     	dbUpdate("UBoxBarCdDtlBoxInit", requestData, onlineCtx);
     	
        return responseData;
    }

    /**
	 *
	 *
	 * @author 정동현 (jjddhh123)
	 * @since 2016-02-17 10:23:48
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUBoxInveTbEFEM(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    
        // 1.쿼리문 호출
        dbUpdate("UBoxInveTbEFEM", requestData, onlineCtx);
        
        return responseData;
    }


  
}
