package dms.nr.nrsxmbase.biz;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;


/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [DU]손해배상금정산정보조회</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-08-10 14:03:14</li>
 * <li>작성자 : 정동현 (jjddhh123)</li>
 * </ul>
 *
 * @author 정동현 (jjddhh123)
 */
public class DNRCmpXclMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DNRCmpXclMgmt(){
		super();
	}

    /**
	 * <pre>손해배상금정산개인,법인총건수 조회</pre>
	 *
	 * @author 정동현 (jjddhh123)
	 * @since 2015-08-10 14:03:14
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSCmpXclLstTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        
        // 1.쿼리문 호출     
	    IRecordSet record = dbSelect("SCmpXclLstTotCnt", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putRecordSet("RS_SUM_LIST",record); 
    
        return responseData;
    }

    /**
	 * <pre>손해배상금정산정보현재월조회</pre>
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-08-10 14:03:14
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSCmpXclSysLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출     
	    IRecordSet rsReturn = dbSelect("SCmpXclSysLst", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putRecordSet("RS_CMP_XCL_SYS_LST", rsReturn);
	
	    return responseData;
	}

	/**
	 * <pre>손해배상금정산정보현재월총건수</pre>
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-08-10 14:03:14
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSCmpXclSysLstTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출     
	    IRecordSet record = dbSelect("SCmpXclSysLstTotCnt", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putRecordSet("RS_SUM_DTL_LIST",record);  
	
	    return responseData;
	}

    /**
	 * <pre>손해배상금정산 개인,법인체크리스트</pre>
	 *
	 * @author 문재웅 (jaiwoong)
	 * @since 2015-08-10 14:03:14
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSCmpXclPsnlCorpList(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SCmpXclPsnlCorpList", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_CMP_XCL_STL_PSNL_CORP_LIST", rsReturn);
    
        return responseData;
    }

	/**
	 * <pre>손해배상금정산정보전체엑셀</pre>
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-08-10 14:03:14
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSCmpXclAllExcel(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SCmpXclAllExcel", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_CMP_XCL_SYS_ALL_EXCEL", rsReturn);
	
	    return responseData;
	}

	/**
	 * <pre>손해배상금정산체크해제항목업데이트</pre>
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-08-10 14:03:14
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUpdateCmpXclMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	    dbUpdate("UpdateCmpXclMgmt",requestData,onlineCtx);
	
	    return responseData;
	}

    /**
	 *
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-12-24 11:26:12
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUpdateXclBondSum(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        dbUpdate("UpdateXclBondSum",requestData,onlineCtx);
    
        return responseData;
    }

    /**
	 *
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-12-24 13:55:48
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUpdateYCmpXclMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        dbUpdate("UpdateYCmpXclMgmt",requestData,onlineCtx);
    
        return responseData;
    }
  
}
