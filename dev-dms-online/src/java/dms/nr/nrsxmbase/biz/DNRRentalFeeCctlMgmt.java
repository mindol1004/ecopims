package dms.nr.nrsxmbase.biz;

import java.util.ArrayList;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.RecordHeader;


/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [DU]렌탈비정산정보조회</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-09-07 19:19:22</li>
 * <li>작성자 : 장미진 (kuramotojin)</li>
 * </ul>
 *
 * @author 장미진 (kuramotojin)
 */
public class DNRRentalFeeCctlMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DNRRentalFeeCctlMgmt(){
		super();
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-09-07 19:19:22
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSRentalFeeCctlInfoLstPaging(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SRentalFeeLstPaging", requestData, onlineCtx);
        IRecordSet rsCnt = dbSelect("SRentalFeeLstTotCnt", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_RENTAL_FEE_INFO_LIST", rsReturn);
        responseData.putRecordSet("RS_SUM_LIST", rsCnt);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-09-07 19:19:22
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSRentalFeeCctlInfoLstPagingDtl(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    IRecordSet rs = requestData.getRecordSet("RS_RENTAL_FEE_INFO_LIST");
	    IRecordSet rsCntcall = requestData.getRecordSet("RS_SUM_LIST");
	    
		ArrayList<String> paramList = new ArrayList<String>();
		
		for(int i = 0; i<rs.getRecordCount(); i++){
			
			paramList.add(rs.get(i, "SVC_MGMT_NO") );
		}
		requestData.putField("SVC_MGMT_NO_LIST", paramList);
	    // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SRentalFeeLstPagingDtl", requestData.getFieldMap(), onlineCtx);
        IRecordSet rsCnt = dbSelect("SRentalFeeLstDtlTotCnt", requestData.getFieldMap(), onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_RENTAL_FEE_INFO_DETAIL", rsReturn);
        
        IRecord ir = rsCntcall.getRecord(0);
        
        if(rsCntcall.getHeader("S_CNT") == null){
        	rsCntcall.addHeader(new RecordHeader("S_CNT"));
        }
        
        	ir.put("S_CNT", rsCnt.get(0, "S_CNT"));
        	
        if(rsCntcall.getHeader("S_PRC") == null){
        	rsCntcall.addHeader(new RecordHeader("S_PRC"));
        }
        	ir.put("S_PRC", rsCnt.get(0,"S_PRC"));
        
        responseData.putRecordSet("RS_SUM_LIST", rsCntcall);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-09-09 15:31:17
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSRentalFeeCctlInfoLstDtlM(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    
	    // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SRentalFeeCctlInfoLstDtlM", requestData, onlineCtx); //list용
        
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_RENTAL_FEE_DTL", rsReturn);
              
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-09-09 15:32:45
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSRentalFeeCctlInfoLstDtlG(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SRentalFeeCctlInfoLstDtlG", requestData, onlineCtx); //그리드용
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_RENTAL_INFO", rsReturn);
        
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-09-15 13:31:13
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSRentalFeeTotalLstPaging(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    IRecordSet rsCntcall = requestData.getRecordSet("RS_SUM_LIST");
	
	    // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SRentalFeeTotalLstPaging", requestData, onlineCtx); //그리드용
        IRecordSet rsCnt = dbSelect("SRentalFeeTotalLstTotCnt", requestData, onlineCtx);//COUNT용
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_RENTAL_FEE_TOTAL_LIST", rsReturn);
        
        IRecord ir = rsCntcall.getRecord(0);

        if(rsCntcall.getHeader("ST_CNT") == null){
	       	rsCntcall.addHeader(new RecordHeader("ST_CNT"));
	       }
	       ir.put("ST_CNT", rsCnt.get(0, "ST_CNT"));
	       	
	    if(rsCntcall.getHeader("ST_PRC") == null){
	       	rsCntcall.addHeader(new RecordHeader("ST_PRC"));
	       }
	       	ir.put("ST_PRC", rsCnt.get(0,"ST_PRC"));
	      
	       
	       responseData.putRecordSet("RS_SUM_LIST", rsCntcall);
        
	
	    return responseData;
	}

    /**
	 *
	 *
	 * @author 문재웅 (jaiwoong)
	 * @since 2015-09-24 18:02:57
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSRentalFeeAllExcel(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        //1. 쿼리문호출
        IRecordSet rsReturn = dbSelect("SRentalFeeAllExcel", requestData.getFieldMap(), onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_ALL_EXCEL_LIST", rsReturn);
    
        return responseData;
    }

    /**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2015-09-07 19:19:22
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
    public IDataSet dSRentalFeeCctlInfoLstDtlAddG(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SRentalFeeCctlInfoLstDtlAddG", requestData, onlineCtx); //그리드용
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_RENTAL_INFO", rsReturn);
        
        return responseData;
    }
  
}
