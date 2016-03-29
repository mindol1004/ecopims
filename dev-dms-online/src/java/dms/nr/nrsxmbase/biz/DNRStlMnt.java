package dms.nr.nrsxmbase.biz;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecordSet;


/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [DU]위약금정산정보조회</li>
 * <li>설  명 : <pre>위약금정산정보조회DU</pre></li>
 * <li>작성일 : 2015-07-27 17:43:21</li>
 * <li>작성자 : 김혁범 (kezmain1)</li>
 * </ul>
 *
 * @author 김혁범 (kezmain1)
 */
public class DNRStlMnt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DNRStlMnt(){
		super();
	}

	/**
	 *
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-07-27 17:43:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSCalChrStlLstPaging(IDataSet requestData, IOnlineContext onlineCtx) {
		 IDataSet responseData = new DataSet();
			
		    // 1.쿼리문 호출
	 		IRecordSet rsReturn = dbSelect("SCalChrStlLstPaging", requestData, onlineCtx);
	 		// 2.결과값 셋팅
	 		responseData.putRecordSet("RS_CAL_CHR_STL_LIST", rsReturn);
		
		    return responseData;
	}

	/**
	 *위약금정산정보조회 총건수
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-07-27 17:54:32
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSCalChrStlTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
		  IDataSet responseData = new DataSet();
		    
		    // 1.쿼리문 호출	    
		  IRecordSet record = dbSelect("SCalChrStlTotCnt", requestData, onlineCtx);
	  		// 2.결과값 셋팅	    
	  	  responseData.putRecordSet("RS_SUM_LIST",record); 
		
		  return responseData;
	}

	/**
	 *위약금정산정보현재월조회
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-09-08 11:26:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSCalChrStlSysLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
  		IRecordSet rsReturn = dbSelect("SCalChrStlSysLst", requestData, onlineCtx);
  		// 2.결과값 셋팅
  		responseData.putRecordSet("RS_CAL_CHR_STL_SYS_LIST", rsReturn); 
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-09-08 17:20:42
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSCalChrStlSysLstTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출	    
  		IRecordSet record = dbSelect("SCalChrStlSysLstTotCnt", requestData, onlineCtx);
  		// 2.결과값 셋팅	    
  		responseData.putRecordSet("RS_SUM_DTL_LIST",record); 
	
	    return responseData;
	}

    /**
	 *
	 *
	 * @author 문재웅 (jaiwoong)
	 * @since 2015-07-27 17:43:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSCalChrStlPsnlCorpList(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SCalChrStlPsnlCorpList", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_CAL_CHR_STL_PSNL_CORP_LIST", rsReturn);
    
        return responseData;
    }

	/**
	 *
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-10-07 15:40:42
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dScalChrStlAllExcel(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("ScalChrStlAllExcel", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_CAL_CHR_STL_ALL_EXCEL", rsReturn);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-07-27 17:43:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUpdateCalChrStlMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	    dbUpdate("UpdateCalChrStlMgmt",requestData,onlineCtx);
	
	    return responseData;
	}

    /**
	 *
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-12-23 16:29:28
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUpdateYCalChrStlMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        dbUpdate("UpdateYCalChrStlMgmt",requestData,onlineCtx);
    
        return responseData;
    }

    /**
	 *
	 *
	 * @author 김혁범 (kezmain1)
	 * @since 2015-12-23 17:10:06
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUpdateBondSum(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        dbUpdate("UpdateBondSum",requestData,onlineCtx);
    
        return responseData;
    }
  
}
