package dms.nr.nrseabase.biz;

import java.util.Map;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecordSet;


/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [DU]단말매각관리</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-08-06 08:56:11</li>
 * <li>작성자 : 장미진 (kuramotojin)</li>
 * </ul>
 *
 * @author 장미진 (kuramotojin)
 */
public class DNREqpDspslMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DNREqpDspslMgmt(){
		super();
	}

	/**
	 * <pre>[DM]단말매각현황조회</pre>
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-08-06 08:56:11
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSEqpDspslLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	 	// 1.쿼리문 호출
	  	IRecordSet rsReturn = dbSelect("SEqpDspslLst", requestData, onlineCtx);
	  	
	  	// 2.결과값 셋팅
	  	responseData.putRecordSet("RS_EQP_DSPSL_LIST", rsReturn);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-08-06 08:56:11
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSEqpDspslTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
	  	IRecordSet rsReturn = dbSelect("SEqpDspslTotCnt", requestData, onlineCtx);
	  	// 2.결과값 셋팅
	  	responseData.putRecordSet("RS_SUM_LIST", rsReturn);  
	    
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-08-06 08:56:11
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUPowerDspslController(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    
	    IRecordSet powerDspl = requestData.getRecordSet("RS_POWER_DSPSL_LIST");
	    
	    Map userPlus = null;
	    for(int i=0; i<powerDspl.getRecordCount(); i++)
	    {
	    	userPlus = powerDspl.getRecordMap(i);
	    	userPlus.put("DEPR_DT", requestData.getField("DEPR_DT"));
	    	userPlus.put("USER_NO", requestData.getField("USER_NO"));
		    // 1.쿼리문 호출
	    	dbUpdate("UPowerDspslController", userPlus, onlineCtx);
	    }
	
	    return responseData;
	}

    /**
	 * <pre>[DM]단말매각현황전체다운로드</pre>
	 *
	 * @author 문재웅 (jaiwoong)
	 * @since 2015-10-26 17:56:49
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSEqpDspslAllExcel(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();

        //1. 쿼리문호출
        IRecordSet rsReturn = dbSelect("SEqpDspslAllExcel", requestData.getFieldMap(), onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_ALL_EXCEL_LIST", rsReturn);
    
        return responseData;
    }

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-08-06 08:56:11
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSEqpDspslTotal(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    
	    //1. 쿼리문호출
        IRecordSet rsReturn = dbSelect("SEqpDspslTotal", requestData.getFieldMap(), onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_SLIP_LIST", rsReturn);
	    
	    return responseData;
	}

    /**
	 * <pre>[DM]단말매각전표처리현황</pre>
	 *
	 * @author 문재웅 (jaiwoong)
	 * @since 2015-11-03 14:39:31
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSEqpDspslSum(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SEqpDspslSum", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_SUM_LIST", rsReturn);  
        
        return responseData;
    }

    /**
	 * <pre>[DM]매각마감자산처분삭제</pre>
	 *
	 * @author 문재웅 (jaiwoong)
	 * @since 2015-08-06 08:56:11
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dDDspslClsAssetDispo(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        
        IRecordSet rs = requestData.getRecordSet("RS_SLIP_SLIT");
        Map delList = null;
        for(int i=0; i<rs.getRecordCount(); i++)
        {
            delList = rs.getRecordMap(i);
           
            // 1.쿼리문 호출
            dbDelete("DDspslClsAssetDispo", delList, onlineCtx);
        }
        
        return responseData;
    }

    /**
	 * <pre>[DM]매각마감자산처분등록</pre>
	 *
	 * @author 문재웅 (jaiwoong)
	 * @since 2015-08-06 08:56:11
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dIDspslClsAssetDispo(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        
        dbUpdate("IDspslClsAssetDispo", requestData, onlineCtx);
        //dbInsert("IDspslClsAssetDispo", requestData, onlineCtx);
        
        return responseData;
    }

    /**
	 * <pre>[DM]매각마감자산처분상세등록</pre>
	 *
	 * @author 문재웅 (jaiwoong)
	 * @since 2015-08-06 08:56:11
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dIDspslClsAssetDispoDtl(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        
        IRecordSet Dspal = requestData.getRecordSet("RS_SLIP_LIST");
        
        Map assetDispo = null;
        for(int i=0; i<Dspal.getRecordCount(); i++)
        {
            assetDispo = Dspal.getRecordMap(i);
            assetDispo.put("LST_DEPR_DT", requestData.getField("DEPR_DT"));
            assetDispo.put("USER_NO", requestData.getField("USER_NO"));
            // 1.쿼리문 호출
            dbUpdate("IDspslClsAssetDispoDtl", assetDispo, onlineCtx);
        }
    
        return responseData;
	
	}
  
}
