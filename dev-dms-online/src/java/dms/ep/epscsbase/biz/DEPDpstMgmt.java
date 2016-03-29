package dms.ep.epscsbase.biz;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.integration.ibatis.IRecordResolver;
import fwk.utils.HpcUtils;


/**
 * <ul>
 * <li>업무 그룹명 : dms/에코폰</li>
 * <li>단위업무명: [DU]입금관리</li>
 * <li>설  명 : <pre>입금관리</pre></li>
 * <li>작성일 : 2015-09-15 17:31:09</li>
 * <li>작성자 : 양재석 (jsyang)</li>
 * </ul>
 *
 * @author 양재석 (jsyang)
 */
public class DEPDpstMgmt extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DEPDpstMgmt(){
		super();
	}

    /**
	 * <pre>[DM]송금대상매입조회목록총건수</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-15 17:31:09
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- field : TOTAL_CNT [총건수]
	 * </pre>
	 */
	public IDataSet dSInqSendAmtObjPrchListTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SInqSendAmtObjPrchListTotCnt", requestData, onlineCtx);
        
        responseData.putFieldMap(record);
    
        return responseData;
    }

    /**
	 * <pre>[DM]송금대상매입조회목록</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-15 17:31:09
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqSendAmtObjPrchListPaging(final IDataSet requestData, final IOnlineContext onlineCtx) {
	       IDataSet responseData = new DataSet();
	       IRecordSet rsReturn = null;
	       
           // 1.쿼리문 호출
	        if(requestData.getField("EXCEL_ALL_YN") == null || !"Y".equals(requestData.getField("EXCEL_ALL_YN").toString())){
	            // 1.1쿼리문 호출
	            rsReturn = dbSelect("SInqSendAmtObjPrchListPaging", requestData, onlineCtx);            
	        } else {
	            
	            //송금대상매입조회목록 엑셀 다운로드시 복호화하는 Resolver.
	            IRecordResolver resolver = new IRecordResolver() {
	                    
	                public void resolve(IRecord record) {
	       
	                        //복호화모듈호출
	                        IDataSet reqHisData = new DataSet();
  
	                        reqHisData.putFieldMap(requestData.getFieldMap());
	                        reqHisData.putField("INQ_OBJ_NO", record.get("CNSL_MGMT_NO").toString());
	                        
	                        //이름                        
	                        if(record.get("DPSTR_ENPT") != null && !"".equals(record.get("DPSTR_ENPT").toString())){
	                            reqHisData.putField("PRN_INFO_INQ_ITEM_CD", "01");
	                            reqHisData.putField("PRN_INFO_INQ_CTT", record.get("DPSTR").toString());
	                            //복화화 담기
	                            record.set("DPSTR", HpcUtils.decodeByAES(record.get("DPSTR_ENPT").toString()));
	                            //이력입력
	                            dbInsert("IPrnInfoInqHst", reqHisData, onlineCtx);
	                        }
	                        
	                        //계좌번호                      
	                        if(record.get("ACCO_NO_ENPT") != null && !"".equals(record.get("ACCO_NO_ENPT").toString())){
                                reqHisData.putField("PRN_INFO_INQ_ITEM_CD", "07");
                                reqHisData.putField("PRN_INFO_INQ_CTT", record.get("ACCO_NO").toString());
                                //복화화 담기
                                record.set("ACCO_NO", HpcUtils.decodeByAES(record.get("ACCO_NO_ENPT").toString()));
                                //이력입력
                                dbInsert("IPrnInfoInqHst", reqHisData, onlineCtx);
	                        }
	                        
	                    }
	            };
	            
	            // 1.2. 쿼리문 호출
	            rsReturn = dbSelect("SInqSendAmtObjPrchListPaging", requestData, resolver, onlineCtx);
	        }
	        
	        // 2.결과값 셋팅
	        responseData.putRecordSet("SEND_AMT_OBJ_LIST", rsReturn);
	    
	        return responseData;
	    }

    /**
	 * <pre>송금대상매입상세조회목록</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-16 11:10:14
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqSendAmtObjPrchDtlList(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SInqSendAmtObjPrchDtlList", requestData, onlineCtx);
        
        // 2.결과값 셋팅
        responseData.putRecordSet("SEND_AMT_OBJ_DTL_LIST", rsReturn);
    
        return responseData;
    }

    /**
	 * <pre>송금대상목록에러체크조회</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-15 17:31:09
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqSendAmtObjErrChkList(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SInqSendAmtObjErrChkList", requestData, onlineCtx);
        
        // 2.결과값 셋팅
        responseData.putRecordSet("EQP_ERR_CHK_LIST", rsReturn);
    
        return responseData;
    }

    /**
	 * <pre>상담관리번호채번</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-15 17:31:09
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqCnslMgmtNo(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SInqDsptMgmtCnslMgmtNo", requestData, onlineCtx);
        
        responseData.putFieldMap(record);
    
        return responseData;
    }

    /**
	 * <pre>입금관리상품상세정보조회</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-17 13:00:04
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqDspMgmtProdDtlInfoList(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SInqDspMgmtProdDtlInfoList", requestData, onlineCtx);
        
        // 2.결과값 셋팅
        responseData.putRecordSet("PROD_DTL_INFO_LIST", rsReturn);
    
        return responseData;
    }

    /**
	 * <pre>입금관리상담상세등록</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-17 16:40:00
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dIDpstMgmtConsultDtlRgst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        dbInsert("IDpstMgmtConsultDtlRgst", requestData, onlineCtx);
    
        return responseData;
    }

    /**
	 * <pre>입금관리매입상세등록</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-17 16:57:53
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dIDpstMgmtPrchsDtlRgst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        dbInsert("IDpstMgmtPrchsDtlRgst", requestData, onlineCtx);
    
        return responseData;
    }

    /**
	 * <pre>입금관리매입관리등록</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-21 19:08:41
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dIDpstMgmtConsultRgst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        dbInsert("IDpstMgmtConsultRgst", requestData, onlineCtx);
    
        return responseData;
    }

    /**
	 * <pre>입금관리매입관리등록</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-21 19:09:07
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dIDpstMgmtPrchRgst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        dbInsert("IDpstMgmtPrchRgst", requestData, onlineCtx);
    
        return responseData;
    }

    /**
	 * <pre>입금관리송금여부수정</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-22 09:16:54
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUDpstMgmtPrchRmtUpd(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        dbInsert("UDpstMgmtPrchRmtUpd", requestData, onlineCtx);
    
        return responseData;
    }

    /**
	 * <pre>요금공제대상매입조회목록총건수</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-15 17:31:09
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- field : TOTAL_CNT [총건수]
	 * </pre>
	 */
	public IDataSet dSInqFeeDedcObjPrchListTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SInqFeeDedcObjPrchListTotCnt", requestData, onlineCtx);
        
        responseData.putFieldMap(record);
    
        return responseData;
    }

    /**
	 * <pre>요금공제대상매입조회목록</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-22 16:50:46
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqFeeDedcObjPrchListPaging(final IDataSet requestData, final IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IRecordSet rsReturn = null;

        // 1.쿼리문 호출
        if(requestData.getField("EXCEL_ALL_YN") == null || !"Y".equals(requestData.getField("EXCEL_ALL_YN").toString())){
            // 1.1쿼리문 호출
            rsReturn = dbSelect("SInqFeeDedcObjPrchListPaging", requestData, onlineCtx);            
        } else {
            
            //송금대상매입조회목록 엑셀 다운로드시 복호화하는 Resolver.
            IRecordResolver resolver = new IRecordResolver() {
                    
                public void resolve(IRecord record) {
       
                        //복호화모듈호출
                        IDataSet reqHisData = new DataSet();

                        reqHisData.putFieldMap(requestData.getFieldMap());
                        reqHisData.putField("INQ_OBJ_NO", record.get("CNSL_MGMT_NO").toString());
                        
                        //이름                        
                        if(record.get("DPSTR_ENPT") != null && !"".equals(record.get("DPSTR_ENPT").toString())){
                            reqHisData.putField("PRN_INFO_INQ_ITEM_CD", "01");
                            reqHisData.putField("PRN_INFO_INQ_CTT", record.get("DPSTR").toString());
                            //복화화 담기
                            record.set("DPSTR", HpcUtils.decodeByAES(record.get("DPSTR_ENPT").toString()));
                            //이력입력
                            dbInsert("IPrnInfoInqHst", reqHisData, onlineCtx);
                        }
                        
                        //계좌번호                      
                        if(record.get("ACCO_NO_ENPT") != null && !"".equals(record.get("ACCO_NO_ENPT").toString())){
                            reqHisData.putField("PRN_INFO_INQ_ITEM_CD", "07");
                            reqHisData.putField("PRN_INFO_INQ_CTT", record.get("ACCO_NO").toString());
                            //복화화 담기
                            record.set("ACCO_NO", HpcUtils.decodeByAES(record.get("ACCO_NO_ENPT").toString()));
                            //이력입력
                            dbInsert("IPrnInfoInqHst", reqHisData, onlineCtx);
                        }
                        
                        //서비스번호                      
                        if(record.get("SVC_NO_ENPT") != null && !"".equals(record.get("SVC_NO_ENPT").toString())){
                            reqHisData.putField("PRN_INFO_INQ_ITEM_CD", "13");
                            reqHisData.putField("PRN_INFO_INQ_CTT", record.get("SVC_NO").toString());
                            //복화화 담기
                            record.set("SVC_NO", HpcUtils.decodeByAES(record.get("SVC_NO_ENPT").toString()));
                            //이력입력
                            dbInsert("IPrnInfoInqHst", reqHisData, onlineCtx);
                        }
                    }
            };
            
            // 1.2. 쿼리문 호출
            rsReturn = dbSelect("SInqFeeDedcObjPrchListPaging", requestData, resolver, onlineCtx);
        }
        // 2.결과값 셋팅
        responseData.putRecordSet("FEE_DEDC_OBJ_LIST", rsReturn);
    
        return responseData;
    }

    /**
	 * <pre>[DM]요금공제대상매입상세조회</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-15 17:31:09
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqFeeDedcObjPrchDtlList(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SInqFeeDedcObjPrchDtlList", requestData, onlineCtx);
        
        // 2.결과값 셋팅
        responseData.putRecordSet("FEE_DEDC_OBJ_DTL_LIST", rsReturn);
    
        return responseData;
    }

    /**
	 * <pre>입금관리요금공제여부수정</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-22 19:44:41
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUDpstMgmtPrchFeeDedcUpd(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        dbInsert("UDpstMgmtPrchFeeDedcUpd", requestData, onlineCtx);
    
        return responseData;
    }

    /**
	 * <pre>강제매입대상조회목록총건수</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-15 17:31:09
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- field : TOTAL_CNT [총건수]
	 * </pre>
	 */
	public IDataSet dSInqForcingPrchObjListTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SInqForcingPrchObjListTotCnt", requestData, onlineCtx);
        
        responseData.putFieldMap(record);
    
        return responseData;
    }

    /**
	 * <pre>강제매입대상조회목록</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-23 13:13:53
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqForcingPrchObjListPaging(final IDataSet requestData, final IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IRecordSet rsReturn = null;

        // 1.쿼리문 호출
        if(requestData.getField("EXCEL_ALL_YN") == null || !"Y".equals(requestData.getField("EXCEL_ALL_YN").toString())){
            // 1.1쿼리문 호출
            rsReturn = dbSelect("SInqForcingPrchObjListPaging", requestData, onlineCtx);            
        } else {
            
            //송금대상매입조회목록 엑셀 다운로드시 복호화하는 Resolver.
            IRecordResolver resolver = new IRecordResolver() {
                    
                public void resolve(IRecord record) {
       
                        //복호화모듈호출
                        IDataSet reqHisData = new DataSet();

                        reqHisData.putFieldMap(requestData.getFieldMap());
                        reqHisData.putField("INQ_OBJ_NO", record.get("CNSL_MGMT_NO").toString());
                        
                        //이름                        
                        if(record.get("DPSTR_ENPT") != null && !"".equals(record.get("DPSTR_ENPT").toString())){
                            reqHisData.putField("PRN_INFO_INQ_ITEM_CD", "01");
                            reqHisData.putField("PRN_INFO_INQ_CTT", record.get("DPSTR").toString());
                            //복화화 담기
                            record.set("DPSTR", HpcUtils.decodeByAES(record.get("DPSTR_ENPT").toString()));
                            //이력입력
                            dbInsert("IPrnInfoInqHst", reqHisData, onlineCtx);
                        }
                        
                        //계좌번호                      
                        if(record.get("ACCO_NO_ENPT") != null && !"".equals(record.get("ACCO_NO_ENPT").toString())){
                            reqHisData.putField("PRN_INFO_INQ_ITEM_CD", "07");
                            reqHisData.putField("PRN_INFO_INQ_CTT", record.get("ACCO_NO").toString());
                            //복화화 담기
                            record.set("ACCO_NO", HpcUtils.decodeByAES(record.get("ACCO_NO_ENPT").toString()));
                            //이력입력
                            dbInsert("IPrnInfoInqHst", reqHisData, onlineCtx);
                        }
                        
                        //서비스번호                      
                        if(record.get("SVC_NO_ENPT") != null && !"".equals(record.get("SVC_NO_ENPT").toString())){
                            reqHisData.putField("PRN_INFO_INQ_ITEM_CD", "13");
                            reqHisData.putField("PRN_INFO_INQ_CTT", record.get("SVC_NO").toString());
                            //복화화 담기
                            record.set("SVC_NO", HpcUtils.decodeByAES(record.get("SVC_NO_ENPT").toString()));
                            //이력입력
                            dbInsert("IPrnInfoInqHst", reqHisData, onlineCtx);
                        }
                    }
            };
            
            // 1.2. 쿼리문 호출
            rsReturn = dbSelect("SInqForcingPrchObjListPaging", requestData, resolver, onlineCtx);
        }
        // 2.결과값 셋팅
        responseData.putRecordSet("FORCING_PRCH_OBJ_LIST", rsReturn);
    
        return responseData;
    }

    /**
	 * <pre>강제매입대상상세조회</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-15 17:31:09
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqForcingPrchObjDtlList(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SInqForcingPrchObjListPaging", requestData, onlineCtx);
        
        // 2.결과값 셋팅
        responseData.putRecordSet("FORCING_PRCH_OBJ_DTL_LIST", rsReturn);
    
        return responseData;
    }

    /**
	 * <pre>입금관리강제매입여부수정</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-23 16:48:16
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUDpstMgmtForcingPrchUpd(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        dbInsert("UDpstMgmtForcingPrchUpd", requestData, onlineCtx);
    
        return responseData;
    }

    /**
	 * <pre>입금관리매입상세조회</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-15 17:31:09
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqDpstMgmtPrchDtlList(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SInqDpstMgmtPrchDtlList", requestData, onlineCtx);
        
        // 2.결과값 셋팅
        responseData.putRecordSet("DPST_MGMT_PRCH_DTL_LIST", rsReturn);
        
        return responseData;
    }

    /**
	 * <pre>재감정송금대상매입조회목록총건수</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-24 18:02:00
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- field : TOTAL_CNT [총건수]
	 * </pre>
	 */
	public IDataSet dSInqRJdgSendAmtObjPrchListTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SInqRJdgSendAmtObjPrchListTotCnt", requestData, onlineCtx);
        
        responseData.putFieldMap(record);
    
        return responseData;
    }

    /**
	 * <pre>재감정송금대상매입조회목록</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-24 18:02:45
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqRJdgSendAmtObjPrchListPaging(final IDataSet requestData, final IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IRecordSet rsReturn = null;

        // 1.쿼리문 호출
        if(requestData.getField("EXCEL_ALL_YN") == null || !"Y".equals(requestData.getField("EXCEL_ALL_YN").toString())){
            // 1.1쿼리문 호출
            rsReturn = dbSelect("SInqRJdgSendAmtObjPrchListPaging", requestData, onlineCtx);            
        } else {
            
            //송금대상매입조회목록 엑셀 다운로드시 복호화하는 Resolver.
            IRecordResolver resolver = new IRecordResolver() {
                    
                public void resolve(IRecord record) {
       
                        //복호화모듈호출
                        IDataSet reqHisData = new DataSet();

                        reqHisData.putFieldMap(requestData.getFieldMap());
                        reqHisData.putField("INQ_OBJ_NO", record.get("CNSL_MGMT_NO").toString());
                        
                        //이름                        
                        if(record.get("DPSTR_ENPT") != null && !"".equals(record.get("DPSTR_ENPT").toString())){
                            reqHisData.putField("PRN_INFO_INQ_ITEM_CD", "01");
                            reqHisData.putField("PRN_INFO_INQ_CTT", record.get("DPSTR").toString());
                            //복화화 담기
                            record.set("DPSTR", HpcUtils.decodeByAES(record.get("DPSTR_ENPT").toString()));
                            //이력입력
                            dbInsert("IPrnInfoInqHst", reqHisData, onlineCtx);
                        }
                        
                        //연락처                        
                        if(record.get("TEL_NO_ENPT") != null && !"".equals(record.get("TEL_NO_ENPT").toString())){
                            reqHisData.putField("PRN_INFO_INQ_ITEM_CD", "10");
                            reqHisData.putField("PRN_INFO_INQ_CTT", record.get("TEL_NO").toString());
                            //복화화 담기
                            record.set("TEL_NO", HpcUtils.decodeByAES(record.get("TEL_NO_ENPT").toString()));
                            //이력입력
                            dbInsert("IPrnInfoInqHst", reqHisData, onlineCtx);
                        }

                        //계좌번호                      
                        if(record.get("ACCO_NO_ENPT") != null && !"".equals(record.get("ACCO_NO_ENPT").toString())){
                            reqHisData.putField("PRN_INFO_INQ_ITEM_CD", "07");
                            reqHisData.putField("PRN_INFO_INQ_CTT", record.get("ACCO_NO").toString());
                            //복화화 담기
                            record.set("ACCO_NO", HpcUtils.decodeByAES(record.get("ACCO_NO_ENPT").toString()));
                            //이력입력
                            dbInsert("IPrnInfoInqHst", reqHisData, onlineCtx);
                        }
                        
                        //서비스번호                      
                        if(record.get("SVC_NO_ENPT") != null && !"".equals(record.get("SVC_NO_ENPT").toString())){
                            reqHisData.putField("PRN_INFO_INQ_ITEM_CD", "13");
                            reqHisData.putField("PRN_INFO_INQ_CTT", record.get("SVC_NO").toString());
                            //복화화 담기
                            record.set("SVC_NO", HpcUtils.decodeByAES(record.get("SVC_NO_ENPT").toString()));
                            //이력입력
                            dbInsert("IPrnInfoInqHst", reqHisData, onlineCtx);
                        }
                    }
            };
            
            // 1.2. 쿼리문 호출
            rsReturn = dbSelect("SInqRJdgSendAmtObjPrchListPaging", requestData, resolver, onlineCtx);
        }        
        // 2.결과값 셋팅
        responseData.putRecordSet("SEND_AMT_OBJ_LIST", rsReturn);
    
        return responseData;
    }

    /**
	 * <pre>입금관리재감정송금등록</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-24 19:00:11
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUDpstMgmtRJdgRmtYesUpd(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        dbInsert("UDpstMgmtRJdgRmtYesUpd", requestData, onlineCtx);
    
        return responseData;
    }

    /**
	 * <pre>입금관리재감정송금취소</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-24 19:00:36
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUDpstMgmtRJdgRmtNoUpd(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        dbInsert("UDpstMgmtRJdgRmtNoUpd", requestData, onlineCtx);
    
        return responseData;
    }

    /**
	 * <pre>[DM]재감정요금공제대상매입조회목록총건수</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-25 10:52:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- field : TOTAL_CNT [총건수]
	 * </pre>
	 */
	public IDataSet dSInqRJdgFeeDedcObjPrchListTotCnt(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SInqRJdgFeeDedcObjPrchListTotCnt", requestData, onlineCtx);
        
        responseData.putFieldMap(record);
    
        return responseData;
    }

    /**
	 * <pre>재감정요금공제대상매입조회목록</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-09-25 10:52:50
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqRJdgFeeDedcObjPrchListPaging(final IDataSet requestData, final IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IRecordSet rsReturn = null;

        // 1.쿼리문 호출
        if(requestData.getField("EXCEL_ALL_YN") == null || !"Y".equals(requestData.getField("EXCEL_ALL_YN").toString())){
            // 1.1쿼리문 호출
            rsReturn = dbSelect("SInqRJdgFeeDedcObjPrchListPaging", requestData, onlineCtx);            
        } else {
            
            //송금대상매입조회목록 엑셀 다운로드시 복호화하는 Resolver.
            IRecordResolver resolver = new IRecordResolver() {
                    
                public void resolve(IRecord record) {
       
                        //복호화모듈호출
                        IDataSet reqHisData = new DataSet();

                        reqHisData.putFieldMap(requestData.getFieldMap());
                        reqHisData.putField("INQ_OBJ_NO", record.get("CNSL_MGMT_NO").toString());
                        
                    //이름                        
                    if(record.get("DPSTR_ENPT") != null && !"".equals(record.get("DPSTR_ENPT").toString())){
                        reqHisData.putField("PRN_INFO_INQ_ITEM_CD", "01");
                        reqHisData.putField("PRN_INFO_INQ_CTT", record.get("DPSTR").toString());
                        //복화화 담기
                        record.set("DPSTR", HpcUtils.decodeByAES(record.get("DPSTR_ENPT").toString()));
                        //이력입력
                        dbInsert("IPrnInfoInqHst", reqHisData, onlineCtx);
                    }
                    
                    //연락처                        
                    if(record.get("TEL_NO_ENPT") != null && !"".equals(record.get("TEL_NO_ENPT").toString())){
                        reqHisData.putField("PRN_INFO_INQ_ITEM_CD", "10");
                        reqHisData.putField("PRN_INFO_INQ_CTT", record.get("TEL_NO").toString());
                        //복화화 담기
                        record.set("TEL_NO", HpcUtils.decodeByAES(record.get("TEL_NO_ENPT").toString()));
                        //이력입력
                        dbInsert("IPrnInfoInqHst", reqHisData, onlineCtx);
                    }

                    //계좌번호                      
                    if(record.get("ACCO_NO_ENPT") != null && !"".equals(record.get("ACCO_NO_ENPT").toString())){
                        reqHisData.putField("PRN_INFO_INQ_ITEM_CD", "07");
                        reqHisData.putField("PRN_INFO_INQ_CTT", record.get("ACCO_NO").toString());
                        //복화화 담기
                        record.set("ACCO_NO", HpcUtils.decodeByAES(record.get("ACCO_NO_ENPT").toString()));
                        //이력입력
                        dbInsert("IPrnInfoInqHst", reqHisData, onlineCtx);
                    }
                    
                    //서비스번호                      
                    if(record.get("SVC_NO_ENPT") != null && !"".equals(record.get("SVC_NO_ENPT").toString())){
                        reqHisData.putField("PRN_INFO_INQ_ITEM_CD", "13");
                        reqHisData.putField("PRN_INFO_INQ_CTT", record.get("SVC_NO").toString());
                        //복화화 담기
                        record.set("SVC_NO", HpcUtils.decodeByAES(record.get("SVC_NO_ENPT").toString()));
                        //이력입력
                        dbInsert("IPrnInfoInqHst", reqHisData, onlineCtx);
                    }
                        
                    }
            };
            
            // 1.2. 쿼리문 호출
            rsReturn = dbSelect("SInqRJdgFeeDedcObjPrchListPaging", requestData, resolver, onlineCtx);
        }        
        // 2.결과값 셋팅
        responseData.putRecordSet("FEE_DEDC_OBJ_LIST", rsReturn);
    
        return responseData;
    }

    /**
	 * <pre>입금관리임시처리매입조회</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2016-01-06 19:04:39
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSInqDpstTempProcPrch(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SInqDpstTempProcPrch", requestData, onlineCtx);
        
        // 2.결과값 셋팅
        responseData.putRecordSet("DPST_TEMP_PROC_PRCH", rsReturn);
    
        return responseData;
    }
  
}
