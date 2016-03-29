package dms.ep.epssxbase.biz;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import fwk.common.CommonArea;
import fwk.utils.PagenateUtils;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.IResultMessage;
import nexcore.framework.core.data.RecordHeader;
import nexcore.framework.core.data.ResultMessage;
import nexcore.framework.core.data.RecordSet;
import nexcore.framework.core.data.xml.DataSetXmlTransformer;
import nexcore.framework.core.exception.BizRuntimeException;
import nexcore.framework.core.util.DateUtils;


/**
 * <ul>
 * <li>업무 그룹명 : dms/에코폰</li>
 * <li>단위업무명: [FU]재고정산관리</li>
 * <li>설  명 : <pre>[FU]재고정산관리</pre></li>
 * <li>작성일 : 2015-10-14 10:18:26</li>
 * <li>작성자 : 양재석 (jsyang)</li>
 * </ul>
 *
 * @author 양재석 (jsyang)
 */
public class FEPInveXclMgmt extends fwk.base.FunctionUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public FEPInveXclMgmt(){
		super();
	}

    /**
	 * <pre>[FM]중고단말기재고정산조회</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-10-14 10:18:26
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : STRD_MTH [기준년월]
	 *	- field : OP_GB_CD [재고업무구분]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_INVE_XCL_LIST
	 *		- field : INVE_XCL_NO [재고 정산 번호]
	 *		- field : XCL_STRD_DT [정산 기준 일자]
	 *		- field : INVE_CL_CD [재고구분]
	 *		- field : DEDC_TYP_CD [공제 유형 코드]
	 *		- field : INVE_TYP_CD [재고 유형 코드]
	 *		- field : XCL_AMT [정산 금액]
	 *		- field : INVE_XCL_CNT [재고 정산 건수]
	 *		- field : INVE_SLIP_DT [재고 전표 일자]
	 *		- field : INVE_SLIP_NO [재고 전표 번호]
	 *		- field : INVE_SLIP_ST [재고 전표 상태]
	 *		- field : INVE_CNCL_SLIP_DT [재고 취소 전표 일자]
	 *		- field : INVE_CNCL_SLIP_NO [재고 취소 전표 번호]
	 *		- field : CHECKED [CHECKED]
	 *		- field : ASMT_DMG_AMT [손실금액]
	 *		- field : SKN_CL [SKN 구분]
	 *		- field : XCL_STRD_STA_DT [정산 기준 시작 일자]
	 *		- field : XCL_STRD_END_DT [정산 기준 종료 일자]
	 * </pre>
	 */
	public IDataSet fSInqEpEqpInveXclList(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        try {
            // 1. DU lookup
            DEPInveXclMgmt dEPInveXclMgmt = (DEPInveXclMgmt) lookupDataUnit(DEPInveXclMgmt.class);
            // 2. LISTDM호출
            if(requestData.getField("OP_GB_CD") != null && "10".equals(requestData.getField("OP_GB_CD").toString())){//확정재고
                responseData = dEPInveXclMgmt.dSInqEpEqpArrivInveXclList(requestData, onlineCtx);
            }else{//미착재고, 반제
                responseData = dEPInveXclMgmt.dSInqEpEqpInveXclList(requestData, onlineCtx);
            }
            
        } catch ( BizRuntimeException e ) {
            throw e;
        }    

        return responseData;
    }

    /**
	 * <pre>[FM]중고단말기재고정산등록</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-10-14 10:18:26
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : OP_GB_CD [재고업무구분]
	 *	- field : CLCT_FR_DT [조회회수시작일자]
	 *	- field : CLCT_TO_DT [조회회수종료일자]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fIEpEqpInveXclRgst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet resData = new DataSet();

        String strInveXclNo = ""; //재고정산번호
        String strInveTypeCd = ""; //재고유형
        String sCurrDt = DateUtils.getCurrentDate(); // 현재일자
        
        try {
            CommonArea ca = getCommonArea(onlineCtx);
            // 1. DU lookup
            DEPInveXclMgmt dEPInveXclMgmt = (DEPInveXclMgmt) lookupDataUnit(DEPInveXclMgmt.class);
            requestData.putField("FS_REG_USER_ID", ca.getUserNo());
            requestData.putField("LS_CHG_USER_ID", ca.getUserNo());
            strInveTypeCd = requestData.getField("OP_GB_CD").toString();
            // 2. 재고생성
            IRecordSet rsInveXclList = null;
            if("20".equals(strInveTypeCd)){ //미착재고
                // 2-11. 재고정산일련번호채번
                resData = dEPInveXclMgmt.dSInqEpEqpInveXclSeq(requestData, onlineCtx);
                strInveXclNo = resData.getField("INVE_XCL_NO").toString();
                requestData.putField("INVE_XCL_NO", strInveXclNo);
                requestData.putField("INVE_TYP_CD", strInveTypeCd); //재고유형코드
                requestData.putField("XCL_STRD_DT", sCurrDt); //정산 기준 일자
                requestData.putField("XCL_STRD_STA_DT", requestData.getField("CLCT_FR_DT").toString()); //정산 기준 시작 일자
                requestData.putField("XCL_STRD_END_DT", requestData.getField("CLCT_TO_DT").toString()); //정산 기준 종료 일자
                requestData.putField("SKN_CL", "Y"); //SKN 구분(현장여부)
                // 2-12. 에코폰 선할인 정산 상세 생성
                requestData.putField("INVE_CL_CD", "10"); // 재고구분 10:에코폰, 20:자수폰
                dEPInveXclMgmt.dIEpEqpNotArrivPreDcInveRgst(requestData, onlineCtx);
                // 2-13. 에코폰 선할인 생성건수 합계 추출
                resData = dEPInveXclMgmt.dSInqEpEqpInveXclSumList(requestData, onlineCtx);
                rsInveXclList = resData.getRecordSet("INVE_XCL_SUM_LIST");
                if(rsInveXclList != null && rsInveXclList.getRecordCount() > 0 && Long.parseLong(rsInveXclList.get(0, "INVE_XCL_CNT").toString()) > 0){
                    requestData.putField("DEDC_TYP_CD", "02"); //공제유형코드 선할인
                    requestData.putField("XCL_AMT", rsInveXclList.get(0, "XCL_AMT")); //정산 금액
                    requestData.putField("INVE_XCL_CNT", rsInveXclList.get(0, "INVE_XCL_CNT")); //재고 정산 건수
                    // 2-14. 재고합계등록
                    dEPInveXclMgmt.dIEpEqpInveXclRgst(requestData, onlineCtx);
                    // 2-21. 재고정산일련번호채번
                    resData = dEPInveXclMgmt.dSInqEpEqpInveXclSeq(requestData, onlineCtx);
                    strInveXclNo = resData.getField("INVE_XCL_NO").toString();
                }
                requestData.putField("INVE_XCL_NO", strInveXclNo);
                // 2-22. 자수폰 선할인 정산 상세 생성
                requestData.putField("INVE_CL_CD", "20"); // 재고구분 10:에코폰, 20:자수폰
                dEPInveXclMgmt.dIEpEqpNotArrivPreDcInveRgst(requestData, onlineCtx);
                // 2-23. 자수폰 선할인 생성건수 합계 추출
                resData = dEPInveXclMgmt.dSInqEpEqpInveXclSumList(requestData, onlineCtx);
                rsInveXclList = resData.getRecordSet("INVE_XCL_SUM_LIST");
                if(rsInveXclList != null && rsInveXclList.getRecordCount() > 0 && Long.parseLong(rsInveXclList.get(0, "INVE_XCL_CNT").toString()) > 0){
                    requestData.putField("DEDC_TYP_CD", "02"); //공제유형코드 선할인
                    requestData.putField("XCL_AMT", rsInveXclList.get(0, "XCL_AMT")); //정산 금액
                    requestData.putField("INVE_XCL_CNT", rsInveXclList.get(0, "INVE_XCL_CNT")); //재고 정산 건수
                    // 2-24. 재고합계등록
                    dEPInveXclMgmt.dIEpEqpInveXclRgst(requestData, onlineCtx);
                    // 2-3. 계좌송금 정산 상세 생성
                    // 2-31. 재고정산일련번호채번
                    resData = dEPInveXclMgmt.dSInqEpEqpInveXclSeq(requestData, onlineCtx);
                    strInveXclNo = resData.getField("INVE_XCL_NO").toString();
                }
                requestData.putField("INVE_XCL_NO", strInveXclNo);
                // 2-32. 에코폰 계좌송금 정산 상세 생성
                requestData.putField("INVE_CL_CD", "10"); // 재고구분 10:에코폰, 20:자수폰
                dEPInveXclMgmt.dIEpEqpNotArrivSendAmtInveRgst(requestData, onlineCtx);
                // 2-33. 에코폰 계좌송금 생성건수 합계 추출
                resData = dEPInveXclMgmt.dSInqEpEqpInveXclSumList(requestData, onlineCtx);
                rsInveXclList = resData.getRecordSet("INVE_XCL_SUM_LIST");
                if(rsInveXclList != null && rsInveXclList.getRecordCount() > 0 && Long.parseLong(rsInveXclList.get(0, "INVE_XCL_CNT").toString()) > 0){
                    requestData.putField("DEDC_TYP_CD", "01"); //공제유형코드 계좌송금
                    requestData.putField("XCL_AMT", rsInveXclList.get(0, "XCL_AMT")); //정산 금액
                    requestData.putField("INVE_XCL_CNT", rsInveXclList.get(0, "INVE_XCL_CNT")); //재고 정산 건수
                    // 2-14. 재고합계등록
                    dEPInveXclMgmt.dIEpEqpInveXclRgst(requestData, onlineCtx);
                    // 2-41. 재고정산일련번호채번
                    resData = dEPInveXclMgmt.dSInqEpEqpInveXclSeq(requestData, onlineCtx);
                    strInveXclNo = resData.getField("INVE_XCL_NO").toString();
                }
                requestData.putField("INVE_XCL_NO", strInveXclNo);
                // 2-42. 자수폰 계좌송금 정산 상세 생성
                requestData.putField("INVE_CL_CD", "20"); // 재고구분 10:에코폰, 20:자수폰
                dEPInveXclMgmt.dIEpEqpNotArrivSendAmtInveRgst(requestData, onlineCtx);
                // 2-43. 자수폰 계좌송금 생성건수 합계 추출
                resData = dEPInveXclMgmt.dSInqEpEqpInveXclSumList(requestData, onlineCtx);
                rsInveXclList = resData.getRecordSet("INVE_XCL_SUM_LIST");
                if(rsInveXclList != null && rsInveXclList.getRecordCount() > 0 && Long.parseLong(rsInveXclList.get(0, "INVE_XCL_CNT").toString()) > 0){
                    requestData.putField("DEDC_TYP_CD", "01"); //공제유형코드 계좌송금
                    requestData.putField("XCL_AMT", rsInveXclList.get(0, "XCL_AMT")); //정산 금액
                    requestData.putField("INVE_XCL_CNT", rsInveXclList.get(0, "INVE_XCL_CNT")); //재고 정산 건수
                    // 2-44. 재고합계등록
                    dEPInveXclMgmt.dIEpEqpInveXclRgst(requestData, onlineCtx);
                    // 2-5. 요금선납 정산 상세 생성
                    // 2-51. 재고정산일련번호채번
                    resData = dEPInveXclMgmt.dSInqEpEqpInveXclSeq(requestData, onlineCtx);
                    strInveXclNo = resData.getField("INVE_XCL_NO").toString();
                }
                requestData.putField("INVE_XCL_NO", strInveXclNo);
                // 2-52. 에코폰 요금선납 정산 상세 생성
                requestData.putField("INVE_CL_CD", "10"); // 재고구분 10:에코폰, 20:자수폰
                dEPInveXclMgmt.dIEpEqpNotArrivFreeDedcInveRgst(requestData, onlineCtx);
                // 2-53. 에코폰 요금선납 생성건수 합계 추출
                resData = dEPInveXclMgmt.dSInqEpEqpInveXclSumList(requestData, onlineCtx);
                rsInveXclList = resData.getRecordSet("INVE_XCL_SUM_LIST");
                if(rsInveXclList != null && rsInveXclList.getRecordCount() > 0 && Long.parseLong(rsInveXclList.get(0, "INVE_XCL_CNT").toString()) > 0){
                    requestData.putField("DEDC_TYP_CD", "03"); //공제유형코드 요금선납
                    requestData.putField("XCL_AMT", rsInveXclList.get(0, "XCL_AMT")); //정산 금액
                    requestData.putField("INVE_XCL_CNT", rsInveXclList.get(0, "INVE_XCL_CNT")); //재고 정산 건수
                    // 2-54. 재고합계등록
                    dEPInveXclMgmt.dIEpEqpInveXclRgst(requestData, onlineCtx);
                    // 2-61. 재고정산일련번호채번
                    resData = dEPInveXclMgmt.dSInqEpEqpInveXclSeq(requestData, onlineCtx);
                    strInveXclNo = resData.getField("INVE_XCL_NO").toString();
                }                
                requestData.putField("INVE_XCL_NO", strInveXclNo);
                // 2-62. 자수폰 요금선납 정산 상세 생성
                requestData.putField("INVE_CL_CD", "20"); // 재고구분 10:에코폰, 20:자수폰
                dEPInveXclMgmt.dIEpEqpNotArrivFreeDedcInveRgst(requestData, onlineCtx);
                // 2-63. 자수폰 요금선납 생성건수 합계 추출
                resData = dEPInveXclMgmt.dSInqEpEqpInveXclSumList(requestData, onlineCtx);
                rsInveXclList = resData.getRecordSet("INVE_XCL_SUM_LIST");
                if(rsInveXclList != null && rsInveXclList.getRecordCount() > 0 && Long.parseLong(rsInveXclList.get(0, "INVE_XCL_CNT").toString()) > 0){
                    requestData.putField("DEDC_TYP_CD", "03"); //공제유형코드 요금선납
                    requestData.putField("XCL_AMT", rsInveXclList.get(0, "XCL_AMT")); //정산 금액
                    requestData.putField("INVE_XCL_CNT", rsInveXclList.get(0, "INVE_XCL_CNT")); //재고 정산 건수
                    // 2-64. 재고합계등록
                    dEPInveXclMgmt.dIEpEqpInveXclRgst(requestData, onlineCtx);
                }                
            }else if("10".equals(strInveTypeCd)){ //확정재고
                // 재감정 시작
                // 3-11. 재고정산일련번호채번
                resData = dEPInveXclMgmt.dSInqEpEqpInveXclSeq(requestData, onlineCtx);
                strInveXclNo = resData.getField("INVE_XCL_NO").toString();
                requestData.putField("INVE_XCL_NO", strInveXclNo);
                requestData.putField("INVE_TYP_CD", strInveTypeCd); //재고유형코드
                requestData.putField("XCL_STRD_DT", sCurrDt); //정산 기준 일자
                requestData.putField("XCL_STRD_STA_DT", requestData.getField("CLCT_FR_DT").toString()); //정산 기준 시작 일자
                requestData.putField("XCL_STRD_END_DT", requestData.getField("CLCT_TO_DT").toString()); //정산 기준 종료 일자
                // 3-12. 에코폰 선할인 정산 상세 생성
                requestData.putField("INVE_CL_CD", "10"); // 재고구분 10:에코폰, 20:자수폰
                dEPInveXclMgmt.dIEpEqpArrivPreDcInveRgst(requestData, onlineCtx);
                // 3-13. 에코폰 선할인 생성건수 합계 추출
                resData = dEPInveXclMgmt.dSInqEpEqpInveXclSumList(requestData, onlineCtx);
                rsInveXclList = resData.getRecordSet("INVE_XCL_SUM_LIST");
                if(rsInveXclList != null && rsInveXclList.getRecordCount() > 0 && Long.parseLong(rsInveXclList.get(0, "INVE_XCL_CNT").toString()) > 0){
                    requestData.putField("DEDC_TYP_CD", "02"); //공제유형코드 선할인
                    requestData.putField("XCL_AMT", rsInveXclList.get(0, "XCL_AMT")); //정산 금액
                    requestData.putField("INVE_XCL_CNT", rsInveXclList.get(0, "INVE_XCL_CNT")); //재고 정산 건수
                    requestData.putField("ASMT_DMG_AMT", rsInveXclList.get(0, "ASMT_DMG_AMT")); //평가 손해 금액
                    requestData.putField("SKN_CL", "Y"); //SKN 구분(현장여부)
                    // 3-14. 재고합계등록
                    dEPInveXclMgmt.dIEpEqpInveXclRgst(requestData, onlineCtx);
                    // 3-21. 재고정산일련번호채번
                    resData = dEPInveXclMgmt.dSInqEpEqpInveXclSeq(requestData, onlineCtx);
                    strInveXclNo = resData.getField("INVE_XCL_NO").toString();
                }
                requestData.putField("INVE_XCL_NO", strInveXclNo);
                // 3-22. 자수폰 선할인 정산 상세 생성
                requestData.putField("INVE_CL_CD", "20"); // 재고구분 10:에코폰, 20:자수폰
                dEPInveXclMgmt.dIEpEqpArrivPreDcInveRgst(requestData, onlineCtx);
                // 3-23. 자수폰 선할인 생성건수 합계 추출
                resData = dEPInveXclMgmt.dSInqEpEqpInveXclSumList(requestData, onlineCtx);
                rsInveXclList = resData.getRecordSet("INVE_XCL_SUM_LIST");
                if(rsInveXclList != null && rsInveXclList.getRecordCount() > 0 && Long.parseLong(rsInveXclList.get(0, "INVE_XCL_CNT").toString()) > 0){
                    requestData.putField("DEDC_TYP_CD", "02"); //공제유형코드 선할인
                    requestData.putField("XCL_AMT", rsInveXclList.get(0, "XCL_AMT")); //정산 금액
                    requestData.putField("INVE_XCL_CNT", rsInveXclList.get(0, "INVE_XCL_CNT")); //재고 정산 건수
                    requestData.putField("ASMT_DMG_AMT", rsInveXclList.get(0, "ASMT_DMG_AMT")); //평가 손해 금액
                    requestData.putField("SKN_CL", "Y"); //SKN 구분(현장여부)
                    // 3-24. 재고합계등록
                    dEPInveXclMgmt.dIEpEqpInveXclRgst(requestData, onlineCtx);
                    // 3-3. 계좌송금 정산 상세 생성
                    // 3-31. 재고정산일련번호채번
                    resData = dEPInveXclMgmt.dSInqEpEqpInveXclSeq(requestData, onlineCtx);
                    strInveXclNo = resData.getField("INVE_XCL_NO").toString();
                }
                requestData.putField("INVE_XCL_NO", strInveXclNo);                
                // 3-32. 에코폰 계좌송금 정산 상세 생성
                requestData.putField("INVE_CL_CD", "10"); // 재고구분 10:에코폰, 20:자수폰
                dEPInveXclMgmt.dIEpEqpArrivSendAmtInveRgst(requestData, onlineCtx);
                // 3-33. 에코폰 계좌송금 생성건수 합계 추출
                resData = dEPInveXclMgmt.dSInqEpEqpInveXclSumList(requestData, onlineCtx);
                rsInveXclList = resData.getRecordSet("INVE_XCL_SUM_LIST");
                if(rsInveXclList != null && rsInveXclList.getRecordCount() > 0 && Long.parseLong(rsInveXclList.get(0, "INVE_XCL_CNT").toString()) > 0){
                    requestData.putField("DEDC_TYP_CD", "01"); //공제유형코드 계좌송금
                    requestData.putField("XCL_AMT", rsInveXclList.get(0, "XCL_AMT")); //정산 금액
                    requestData.putField("INVE_XCL_CNT", rsInveXclList.get(0, "INVE_XCL_CNT")); //재고 정산 건수
                    requestData.putField("ASMT_DMG_AMT", rsInveXclList.get(0, "ASMT_DMG_AMT")); //평가 손해 금액
                    requestData.putField("SKN_CL", "Y"); //SKN 구분(현장여부)
                    // 3-14. 재고합계등록
                    dEPInveXclMgmt.dIEpEqpInveXclRgst(requestData, onlineCtx);
                    // 3-41. 재고정산일련번호채번
                    resData = dEPInveXclMgmt.dSInqEpEqpInveXclSeq(requestData, onlineCtx);
                    strInveXclNo = resData.getField("INVE_XCL_NO").toString();
                }
                requestData.putField("INVE_XCL_NO", strInveXclNo);
                // 3-42. 자수폰 계좌송금 정산 상세 생성
                requestData.putField("INVE_CL_CD", "20"); // 재고구분 10:에코폰, 20:자수폰
                dEPInveXclMgmt.dIEpEqpArrivSendAmtInveRgst(requestData, onlineCtx);
                // 3-43. 자수폰 계좌송금 생성건수 합계 추출
                resData = dEPInveXclMgmt.dSInqEpEqpInveXclSumList(requestData, onlineCtx);
                rsInveXclList = resData.getRecordSet("INVE_XCL_SUM_LIST");
                if(rsInveXclList != null && rsInveXclList.getRecordCount() > 0 && Long.parseLong(rsInveXclList.get(0, "INVE_XCL_CNT").toString()) > 0){
                    requestData.putField("DEDC_TYP_CD", "01"); //공제유형코드 계좌송금
                    requestData.putField("XCL_AMT", rsInveXclList.get(0, "XCL_AMT")); //정산 금액
                    requestData.putField("INVE_XCL_CNT", rsInveXclList.get(0, "INVE_XCL_CNT")); //재고 정산 건수
                    requestData.putField("ASMT_DMG_AMT", rsInveXclList.get(0, "ASMT_DMG_AMT")); //평가 손해 금액
                    requestData.putField("SKN_CL", "Y"); //SKN 구분(현장여부)
                    // 3-44. 재고합계등록
                    dEPInveXclMgmt.dIEpEqpInveXclRgst(requestData, onlineCtx);
                    // 3-5. 요금선납 정산 상세 생성
                    // 3-51. 재고정산일련번호채번
                    resData = dEPInveXclMgmt.dSInqEpEqpInveXclSeq(requestData, onlineCtx);
                    strInveXclNo = resData.getField("INVE_XCL_NO").toString();
                }
                requestData.putField("INVE_XCL_NO", strInveXclNo);
                // 3-52. 에코폰 요금선납 정산 상세 생성
                requestData.putField("INVE_CL_CD", "10"); // 재고구분 10:에코폰, 20:자수폰
                dEPInveXclMgmt.dIEpEqpArrivFreeDedcInveRgst(requestData, onlineCtx);
                // 3-53. 에코폰 요금선납 생성건수 합계 추출
                resData = dEPInveXclMgmt.dSInqEpEqpInveXclSumList(requestData, onlineCtx);
                rsInveXclList = resData.getRecordSet("INVE_XCL_SUM_LIST");
                if(rsInveXclList != null && rsInveXclList.getRecordCount() > 0 && Long.parseLong(rsInveXclList.get(0, "INVE_XCL_CNT").toString()) > 0){
                    requestData.putField("DEDC_TYP_CD", "03"); //공제유형코드 요금선납
                    requestData.putField("XCL_AMT", rsInveXclList.get(0, "XCL_AMT")); //정산 금액
                    requestData.putField("INVE_XCL_CNT", rsInveXclList.get(0, "INVE_XCL_CNT")); //재고 정산 건수
                    requestData.putField("ASMT_DMG_AMT", rsInveXclList.get(0, "ASMT_DMG_AMT")); //평가 손해 금액
                    requestData.putField("SKN_CL", "Y"); //SKN 구분(현장여부)
                    // 3-54. 재고합계등록
                    dEPInveXclMgmt.dIEpEqpInveXclRgst(requestData, onlineCtx);
                    // 3-61. 재고정산일련번호채번
                    resData = dEPInveXclMgmt.dSInqEpEqpInveXclSeq(requestData, onlineCtx);
                    strInveXclNo = resData.getField("INVE_XCL_NO").toString();
                }                
                requestData.putField("INVE_XCL_NO", strInveXclNo);
                // 3-62. 자수폰 요금선납 정산 상세 생성
                requestData.putField("INVE_CL_CD", "20"); // 재고구분 10:에코폰, 20:자수폰
                dEPInveXclMgmt.dIEpEqpArrivFreeDedcInveRgst(requestData, onlineCtx);
                // 3-63. 자수폰 요금선납 생성건수 합계 추출
                resData = dEPInveXclMgmt.dSInqEpEqpInveXclSumList(requestData, onlineCtx);
                rsInveXclList = resData.getRecordSet("INVE_XCL_SUM_LIST");
                if(rsInveXclList != null && rsInveXclList.getRecordCount() > 0 && Long.parseLong(rsInveXclList.get(0, "INVE_XCL_CNT").toString()) > 0){
                    requestData.putField("DEDC_TYP_CD", "03"); //공제유형코드 요금선납
                    requestData.putField("XCL_AMT", rsInveXclList.get(0, "XCL_AMT")); //정산 금액
                    requestData.putField("INVE_XCL_CNT", rsInveXclList.get(0, "INVE_XCL_CNT")); //재고 정산 건수
                    requestData.putField("ASMT_DMG_AMT", rsInveXclList.get(0, "ASMT_DMG_AMT")); //평가 손해 금액
                    requestData.putField("SKN_CL", "Y"); //SKN 구분(현장여부)
                    // 3-64. 재고합계등록
                    dEPInveXclMgmt.dIEpEqpInveXclRgst(requestData, onlineCtx);
                    // 3-71. 재고정산일련번호채번
                    resData = dEPInveXclMgmt.dSInqEpEqpInveXclSeq(requestData, onlineCtx);
                    strInveXclNo = resData.getField("INVE_XCL_NO").toString();
                }
                requestData.putField("INVE_XCL_NO", strInveXclNo);
                // 3-72. 에코폰 클럽T 정산 상세 생성
                requestData.putField("INVE_CL_CD", "10"); // 재고구분 10:에코폰, 20:자수폰
                dEPInveXclMgmt.dIEpEqpArrivClubTInveRgst(requestData, onlineCtx);
                // 3-73. 에코폰  클럽T 생성건수 합계 추출
                resData = dEPInveXclMgmt.dSInqEpEqpInveXclSumList(requestData, onlineCtx);
                rsInveXclList = resData.getRecordSet("INVE_XCL_SUM_LIST");
                if(rsInveXclList != null && rsInveXclList.getRecordCount() > 0 && Long.parseLong(rsInveXclList.get(0, "INVE_XCL_CNT").toString()) > 0){
                    requestData.putField("DEDC_TYP_CD", "04"); //공제유형코드  클럽T
                    requestData.putField("XCL_AMT", rsInveXclList.get(0, "XCL_AMT")); //정산 금액
                    requestData.putField("INVE_XCL_CNT", rsInveXclList.get(0, "INVE_XCL_CNT")); //재고 정산 건수
                    requestData.putField("ASMT_DMG_AMT", rsInveXclList.get(0, "ASMT_DMG_AMT")); //평가 손해 금액
                    requestData.putField("SKN_CL", "Y"); //SKN 구분(현장여부)
                    // 3-74. 재고합계등록
                    dEPInveXclMgmt.dIEpEqpInveXclRgst(requestData, onlineCtx);
                    // 4-11. 재고정산일련번호채번
                    resData = dEPInveXclMgmt.dSInqEpEqpInveXclSeq(requestData, onlineCtx);
                    strInveXclNo = resData.getField("INVE_XCL_NO").toString();
                }
                // 재감정 완료
                //************************
                // 일반감정시작
                requestData.putField("INVE_XCL_NO", strInveXclNo);
                requestData.putField("ASMT_DMG_AMT", new BigDecimal(0)); //평가 손해 금액
                requestData.putField("SKN_CL", "N"); //SKN 구분(현장여부)
                // 4-12. 에코폰 계좌송금 정산 상세 생성
                requestData.putField("INVE_CL_CD", "10"); // 재고구분 10:에코폰, 20:자수폰
                requestData.putField("DEDC_TYP_CD", "01"); //공제유형코드 계좌송금
                dEPInveXclMgmt.dIEpEqpStdJdgArrivInveRgst(requestData, onlineCtx);
                // 4-13. 에코폰 계좌송금 생성건수 합계 추출
                resData = dEPInveXclMgmt.dSInqEpEqpInveXclSumList(requestData, onlineCtx);
                rsInveXclList = resData.getRecordSet("INVE_XCL_SUM_LIST");
                if(rsInveXclList != null && rsInveXclList.getRecordCount() > 0 && Long.parseLong(rsInveXclList.get(0, "INVE_XCL_CNT").toString()) > 0){
                    requestData.putField("XCL_AMT", rsInveXclList.get(0, "XCL_AMT")); //정산 금액
                    requestData.putField("INVE_XCL_CNT", rsInveXclList.get(0, "INVE_XCL_CNT")); //재고 정산 건수
                    // 4-14. 재고합계등록
                    dEPInveXclMgmt.dIEpEqpInveXclRgst(requestData, onlineCtx);
                    // 4-21. 재고정산일련번호채번
                    resData = dEPInveXclMgmt.dSInqEpEqpInveXclSeq(requestData, onlineCtx);
                    strInveXclNo = resData.getField("INVE_XCL_NO").toString();
                }
                requestData.putField("INVE_XCL_NO", strInveXclNo);
                // 4-22. 자수폰 계좌송금 정산 상세 생성
                requestData.putField("INVE_CL_CD", "20"); // 재고구분 10:에코폰, 20:자수폰
                requestData.putField("DEDC_TYP_CD", "01"); //공제유형코드 계좌송금
                dEPInveXclMgmt.dIEpEqpStdJdgArrivInveRgst(requestData, onlineCtx);
                // 4-23. 자수폰 계좌송금 생성건수 합계 추출
                resData = dEPInveXclMgmt.dSInqEpEqpInveXclSumList(requestData, onlineCtx);
                rsInveXclList = resData.getRecordSet("INVE_XCL_SUM_LIST");
                if(rsInveXclList != null && rsInveXclList.getRecordCount() > 0 && Long.parseLong(rsInveXclList.get(0, "INVE_XCL_CNT").toString()) > 0){
                    requestData.putField("XCL_AMT", rsInveXclList.get(0, "XCL_AMT")); //정산 금액
                    requestData.putField("INVE_XCL_CNT", rsInveXclList.get(0, "INVE_XCL_CNT")); //재고 정산 건수
                    // 4-24. 재고합계등록
                    dEPInveXclMgmt.dIEpEqpInveXclRgst(requestData, onlineCtx);
                    // 4-31. 재고정산일련번호채번
                    resData = dEPInveXclMgmt.dSInqEpEqpInveXclSeq(requestData, onlineCtx);
                    strInveXclNo = resData.getField("INVE_XCL_NO").toString();
                }
                requestData.putField("INVE_XCL_NO", strInveXclNo);
                // 4-32. 에코폰 요금선납 정산 상세 생성
                requestData.putField("INVE_CL_CD", "10"); // 재고구분 10:에코폰, 20:자수폰
                requestData.putField("DEDC_TYP_CD", "03"); //공제유형코드 요금선납
                dEPInveXclMgmt.dIEpEqpStdJdgArrivInveRgst(requestData, onlineCtx);
                // 4-13. 에코폰 요금선납 생성건수 합계 추출
                resData = dEPInveXclMgmt.dSInqEpEqpInveXclSumList(requestData, onlineCtx);
                rsInveXclList = resData.getRecordSet("INVE_XCL_SUM_LIST");
                if(rsInveXclList != null && rsInveXclList.getRecordCount() > 0 && Long.parseLong(rsInveXclList.get(0, "INVE_XCL_CNT").toString()) > 0){
                    requestData.putField("XCL_AMT", rsInveXclList.get(0, "XCL_AMT")); //정산 금액
                    requestData.putField("INVE_XCL_CNT", rsInveXclList.get(0, "INVE_XCL_CNT")); //재고 정산 건수
                    // 4-14. 재고합계등록
                    dEPInveXclMgmt.dIEpEqpInveXclRgst(requestData, onlineCtx);
                    // 4-21. 재고정산일련번호채번
                    resData = dEPInveXclMgmt.dSInqEpEqpInveXclSeq(requestData, onlineCtx);
                    strInveXclNo = resData.getField("INVE_XCL_NO").toString();
                }
                requestData.putField("INVE_XCL_NO", strInveXclNo);
                // 4-22. 자수폰 요금선납 정산 상세 생성
                requestData.putField("INVE_CL_CD", "20"); // 재고구분 10:에코폰, 20:자수폰
                requestData.putField("DEDC_TYP_CD", "03"); //공제유형코드 요금선납
                dEPInveXclMgmt.dIEpEqpStdJdgArrivInveRgst(requestData, onlineCtx);
                // 4-23. 자수폰 요금선납 생성건수 합계 추출
                resData = dEPInveXclMgmt.dSInqEpEqpInveXclSumList(requestData, onlineCtx);
                rsInveXclList = resData.getRecordSet("INVE_XCL_SUM_LIST");
                if(rsInveXclList != null && rsInveXclList.getRecordCount() > 0 && Long.parseLong(rsInveXclList.get(0, "INVE_XCL_CNT").toString()) > 0){
                    requestData.putField("XCL_AMT", rsInveXclList.get(0, "XCL_AMT")); //정산 금액
                    requestData.putField("INVE_XCL_CNT", rsInveXclList.get(0, "INVE_XCL_CNT")); //재고 정산 건수
                    // 4-24. 재고합계등록
                    dEPInveXclMgmt.dIEpEqpInveXclRgst(requestData, onlineCtx);
                }
                // 일반감정 완료
            }

        } catch ( BizRuntimeException e ) {
            throw e;
        }    
    
        return responseData;
    }

    /**
	 * <pre>[FM]중고단말기미착재고정산조회</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-10-14 10:18:26
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : INVE_XCL_NO [재고 정산 번호]
	 *	- field : EXCEL_FIRST [EXCEL_FIRST]
	 *	- field : EXCEL_LAST [EXCEL_LAST]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : INVE_XCL_DTL_LIST
	 *		- field : INVE_XCL_NO [재고 정산 번호]
	 *		- field : XCL_STRD_DT [정산기준일자]
	 *		- field : INVE_CL_CD [재고구분코드]
	 *		- field : DEDC_TYP_CD [공제유형코드]
	 *		- field : INVE_TYP_CD [재고유형코드]
	 *		- field : INVE_SLIP_DT [재고전표일자]
	 *		- field : INVE_SLIP_NO [재고전표번호]
	 *		- field : INVE_SLIP_ST [재고전표상태]
	 *		- field : INVE_CNCL_SLIP_DT [재고취소전표일자]
	 *		- field : INVE_CNCL_SLIP_NO [재고취소전표번호]
	 *		- field : CNSL_MGMT_NO [상담관리번호]
	 *		- field : CNSL_DT [접수일자]
	 *		- field : CLCT_DT [회수일자]
	 *		- field : EQP_MDL_CD [단말기코드]
	 *		- field : EQP_MDL_NM [단말기명]
	 *		- field : EQP_SER_NO [단말기일련번호]
	 *		- field : SKN_EQP_ST [SKN감정등급]
	 *		- field : EQP_ST [등급]
	 *		- field : XCL_AMT [정산금액]
	 *		- field : ASMT_DMG_AMT [손실금액]
	 *		- field : ROWNO [ROWNO]
	 *		- field : EXCEL_TOTAL_CNT [EXCEL_TOTAL_CNT]
	 *		- field : ARRIV_AMT_DT [입금일자]
	 *		- field : NOARRIV_INVE_SLIP_NO [미착재고전표번호]
	 *		- field : NOARRIV_INVE_SLIP_DT [미착재고전표일자]
	 *		- field : SKN_CL [SKN 구분]
	 *		- field : CNCL_SLIP_YN [취소여부]
	 * </pre>
	 */
	public IDataSet fSInqEpEqpNotArrivInveXclList(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();

        try {
            // 1. DU lookup
            DEPInveXclMgmt dEPInveXclMgmt = (DEPInveXclMgmt) lookupDataUnit(DEPInveXclMgmt.class);
            // 2. LISTDM호출
            responseData = dEPInveXclMgmt.dSInqEpEqpNotArrivInveXclList(requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        }    
    
        return responseData;
    }

    /**
	 * <pre>[FM]중고단말기재고정산상세등록</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-10-14 10:18:26
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : START_YN [시작여부]
	 *	- field : LAST_YN [마지막여부]
	 *	- field : NEW_INVE_XCL_NO [신규정산관리번호]
	 *	- record : INVE_XCL_DTL_LIST
	 *		- field : INVE_XCL_NO [재고 정산 번호]
	 *		- field : CNSL_MGMT_NO [상담관리번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : UPLOAD_STS
	 *		- field : START_YN [시작여부]
	 *		- field : LAST_YN [마지막여부]
	 *		- field : NEW_INVE_XCL_NO [신규정산관리번호]
	 * </pre>
	 */
	public IDataSet fUEpEqpInveXclDtlRgst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet reqData = new DataSet();
        IDataSet resData = new DataSet();
        IRecordSet rs = requestData.getRecordSet("INVE_XCL_DTL_LIST");
        
        try {
            CommonArea ca = getCommonArea(onlineCtx);
            // 1. DU lookup
            DEPInveXclMgmt dEPInveXclMgmt = (DEPInveXclMgmt) lookupDataUnit(DEPInveXclMgmt.class);
            
            IRecordSet rsInveXclList = null;
            String strStartYn = "";
            String strLastYn = "";
            String strNewInveXclNo = "";
            if(requestData != null && requestData.getField("START_YN") != null){
                strStartYn = requestData.getField("START_YN").toString();
            }
            if(requestData != null && requestData.getField("LAST_YN") != null){
                strLastYn = requestData.getField("LAST_YN").toString();
            }
            
            IRecordSet rsUploadSts = new RecordSet("UPLOAD_STS", new String[]{"START_YN","LAST_YN","NEW_INVE_XCL_NO"});
            IRecord rUploadSts = rsUploadSts.newRecord();
            rUploadSts.set("START_YN", requestData.getField("START_YN"));
            rUploadSts.set("LAST_YN", requestData.getField("LAST_YN"));
            rUploadSts.set("NEW_INVE_XCL_NO", requestData.getField("NEW_INVE_XCL_NO"));
            strNewInveXclNo = requestData.getField("NEW_INVE_XCL_NO").toString();
            
            if("Y".equals(strStartYn) && "Y".equals(strLastYn)){ //업로드데이터가 모두 들어 왔을경우
                for(int i = 0; i < rs.getRecordCount(); i++){
                    reqData.putFieldMap(rs.getRecordMap(i));
                    reqData.putField("LS_CHG_USER_ID", ca.getUserNo());
                    if(i == 0){
                        //해당정산번호의상세를 모두 삭제
                        dEPInveXclMgmt.dDEpEqpInveXclDtlDel(reqData, onlineCtx);
                    }
                    //등록대상상세의 삭제여부를 N으로 수정
                    dEPInveXclMgmt.dUEpEqpInveXclDelDtlUpd(reqData, onlineCtx);
                }
                if(reqData != null && reqData.getField("INVE_XCL_NO") != null && !"".equals(reqData.getField("INVE_XCL_NO").toString())){
                    resData = dEPInveXclMgmt.dSInqEpEqpInveXclSumList(reqData, onlineCtx);
                    rsInveXclList = resData.getRecordSet("INVE_XCL_SUM_LIST");
                    if(rsInveXclList != null && rsInveXclList.getRecordCount() > 0 && Long.parseLong(rsInveXclList.get(0, "INVE_XCL_CNT").toString()) > 0){
                        reqData.putField("XCL_AMT", rsInveXclList.get(0, "XCL_AMT")); //정산 금액
                        reqData.putField("ASMT_DMG_AMT", rsInveXclList.get(0, "ASMT_DMG_AMT")); //정산 금액
                        reqData.putField("INVE_XCL_CNT", rsInveXclList.get(0, "INVE_XCL_CNT")); //재고 정산 건수
                        // 재고합계등록
                        dEPInveXclMgmt.dUEpEqpInveXclSumUpd(reqData, onlineCtx);
                        // 재고전표발행상태 수정
                        reqData.putField("INVE_SLIP_ST", "00"); //재고 전표 발행 상태
                        dEPInveXclMgmt.dUEpEqpInveXclSlipStUpd(reqData, onlineCtx);
                    }
                }
            }else { //분할업로드일경우
                if("Y".equals(strStartYn) && !"Y".equals(strLastYn)){//업로드데이터가 시작부분들어왔을경우
                    //임시번호채번
                    resData = dEPInveXclMgmt.dSInqEpEqpInveXclSeq(requestData, onlineCtx);
                    strNewInveXclNo = resData.getField("INVE_XCL_NO").toString();
                    rUploadSts.set("NEW_INVE_XCL_NO", strNewInveXclNo);//임시사용번호
                }
                
                for(int i = 0; i < rs.getRecordCount(); i++){
                    reqData.putFieldMap(rs.getRecordMap(i));
                    reqData.putField("LS_CHG_USER_ID", ca.getUserNo());
                    reqData.putField("NEW_INVE_XCL_NO", strNewInveXclNo);
                    dEPInveXclMgmt.dIEpEqpInveXclDtlRgstExcelUp(reqData, onlineCtx);
                }
                
                if(!"Y".equals(strStartYn) && "Y".equals(strLastYn)){//업로드데이터가 마지막부분경우 합계 처리
                    //해당정산번호의상세를 모두 삭제
                    dEPInveXclMgmt.dDEpEqpInveXclDtlDel(reqData, onlineCtx);
                    //임시번호로등록한상세내역을 원데이터에 반영
                    dEPInveXclMgmt.dUEpEqpInveXclTempExcelUpd(reqData, onlineCtx);
                    //합계구하기
                    resData = dEPInveXclMgmt.dSInqEpEqpInveXclSumList(reqData, onlineCtx);
                    rsInveXclList = resData.getRecordSet("INVE_XCL_SUM_LIST");
                    if(rsInveXclList != null && rsInveXclList.getRecordCount() > 0 && Long.parseLong(rsInveXclList.get(0, "INVE_XCL_CNT").toString()) > 0){
                        reqData.putField("XCL_AMT", rsInveXclList.get(0, "XCL_AMT")); //정산 금액
                        reqData.putField("ASMT_DMG_AMT", rsInveXclList.get(0, "ASMT_DMG_AMT")); //정산 금액
                        reqData.putField("INVE_XCL_CNT", rsInveXclList.get(0, "INVE_XCL_CNT")); //재고 정산 건수
                        // 재고합계등록
                        dEPInveXclMgmt.dUEpEqpInveXclSumUpd(reqData, onlineCtx);
                        // 재고전표발행상태 수정
                        reqData.putField("INVE_SLIP_ST", "00"); //재고 전표 발행 상태
                        dEPInveXclMgmt.dUEpEqpInveXclSlipStUpd(reqData, onlineCtx);
                        // 임시정보삭제
                        dEPInveXclMgmt.dDEpEqpInveXclDtlTempInfoDel(reqData, onlineCtx);
                    }
                }

            }
            rsUploadSts.setRecord(0, rUploadSts);
            responseData.putRecordSet(rsUploadSts);

        } catch ( BizRuntimeException e ) {
            throw e;
        }    
    
        return responseData;
    }

    /**
	 * <pre>[FM]재고정산관리전표발행</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-10-14 10:18:26
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RQ_INVE_XCL_LIST
	 *		- field : INVE_XCL_NO [재고정산번호]
	 *		- field : XCL_AMT [정산금액]
	 *		- field : INVE_XCL_CNT [재고정산건수]
	 *		- field : INVE_SLIP_DT [전표발행일자]
	 *		- field : INVE_SLIP_NO [전표번호]
	 *		- field : INVE_SLIP_ST [전표상태]
	 *		- field : INVE_CNCL_SLIP_DT [취소전표일자]
	 *		- field : INVE_CNCL_SLIP_NO [취소전표번호]
	 *		- field : ASMT_DMG_AMT [손실금액]
	 *		- field : INVE_TYP_CD [재고유형]
	 *		- field : YYYY [YYYY]
	 *		- field : SLIP_NO [SLIP_NO]
	 *		- field : CNCL_SLIP_YN [취소여부]
	 *		- field : DEDC_TYP_CD [공제유형]
	 *		- field : INVE_TYP_NM [재고유형명]
	 *		- field : DEDC_TYP_NM [공제유형명]
	 *		- field : SKN_CL [현장여부]
	 *		- field : SKN_CL_NM [현장여부명]
	 *		- field : INVE_CL_CD [재고구분]
	 *		- field : INVE_CL_NM [재고구분명]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fRegInveXclSlip(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet reqSlipDs = new DataSet();
        IDataSet reqDs = (IDataSet) requestData.clone();//원거래의 DataSet의 훼손을 막기 위한 Clone
        CommonArea ca = getCommonArea(onlineCtx);
        
        IDataSet inveDs = null;

        try {
            
            // DU lookup
            DEPInveXclMgmt dEPInveXclMgmt = (DEPInveXclMgmt) lookupDataUnit(DEPInveXclMgmt.class);
            
            // 업무 DM호출
            IRecordSet inveRs = reqDs.getRecordSet("RQ_INVE_XCL_LIST");
            inveDs = new DataSet();
            inveDs.putFieldMap(inveRs.getRecordMap(0));

            reqSlipDs.putRecordSet("RS_SLIP_LIST", inveRs); //전표콜 RS이름은  RS_SLIP_LIST 으로 통일
            reqSlipDs.putField("USERNO", ca.getUserNo());
            if("10".equals(inveDs.getField("INVE_TYP_CD").toString())){
                if("Y".equals(inveDs.getField("CNCL_SLIP_YN").toString())){
                    reqSlipDs.putField("SLIP_TYPE", "EP_OJ"); //확정반제
                }else{
                    reqSlipDs.putField("SLIP_TYPE", "EP_ZJ"); //확정
                }
            }else{
                if("Y".equals(inveDs.getField("CNCL_SLIP_YN").toString())){
                    reqSlipDs.putField("SLIP_TYPE", "EP_OI"); //미착반제
                }else{
                    reqSlipDs.putField("SLIP_TYPE", "EP_ZI"); //미착
                }
            }

            ByteArrayOutputStream bout = new ByteArrayOutputStream(1024);
            DataSetXmlTransformer.dataSetToXml(reqSlipDs, bout, "UTF-8");
            String dsXml = bout.toString("UTF-8");

            // call on-demand batch job
            // 배치에서 작업 : TB_EP_INVE_XCL, TB_EP_INVE_XCL_DTL 에 INVE_XCL_NO 와 같은건에 대해 INVE_SLIP_ST,INVE_SLIP_NO,INVE_SLIP_DT 수정 
            Map<String ,String> params = new HashMap<String ,String>();
            params.put("TASK_ID", "EPR010");
            params.put("TASK_NM", "전표발행");
            params.put("USER_NO", ca.getUserNo());
            params.put("COMPONENTNAME_LOCAL_ONLY", "dms.inf.EPR010");               
            params.put("POST_SLIP_DATASET", dsXml);
            String jobExecutionId = callBatchJob("EPR010", params, onlineCtx);
            waitBatchJobEnd(jobExecutionId, 10000);
            int result = getJobReturnCode(jobExecutionId);

            if(result == -1) throw new BizRuntimeException("DMS00009"); // 시스템 오류가 발생하였습니다.
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); //시스템 오류
        }
        
        return responseData;
	}

    /**
	 * <pre>[FM]재고정산관리전표발행취소</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-10-14 10:18:26
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RQ_INVE_XCL_LIST
	 *		- field : INVE_XCL_NO [재고정산번호]
	 *		- field : XCL_AMT [정산금액]
	 *		- field : INVE_XCL_CNT [재고정산건수]
	 *		- field : INVE_SLIP_DT [전표발행일자]
	 *		- field : INVE_SLIP_NO [전표번호]
	 *		- field : INVE_SLIP_ST [전표상태]
	 *		- field : INVE_CNCL_SLIP_DT [취소전표일자]
	 *		- field : INVE_CNCL_SLIP_NO [취소전표번호]
	 *		- field : ASMT_DMG_AMT [손실금액]
	 *		- field : INVE_TYP_CD [재고유형]
	 *		- field : YYYY [YYYY]
	 *		- field : SLIP_NO [전표번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fRegInveXclSlipCncl(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet reqSlipDs = new DataSet();
        IDataSet reqDs = (IDataSet) requestData.clone();//원거래의 DataSet의 훼손을 막기 위한 Clone
        CommonArea ca = getCommonArea(onlineCtx);
        
        IDataSet inveDs = null;

        try {
            
            // DU lookup
            DEPInveXclMgmt dEPInveXclMgmt = (DEPInveXclMgmt) lookupDataUnit(DEPInveXclMgmt.class);
            
            // 업무 DM호출
            reqSlipDs.putRecordSet("RS_SLIP_LIST", reqDs.getRecordSet("RQ_INVE_XCL_LIST")); //전표콜 RS이름은  RS_SLIP_LIST 으로 통일
            reqSlipDs.putField("USERNO", ca.getUserNo());
            reqSlipDs.putField("SLIP_TYPE", "EP_ZK"); //취소는 일반으로

            ByteArrayOutputStream bout = new ByteArrayOutputStream(1024);
            DataSetXmlTransformer.dataSetToXml(reqSlipDs, bout, "UTF-8");
            String dsXml = bout.toString("UTF-8");

            // call on-demand batch job
            // 배치에서 작업 : TB_EP_INVE_XCL, TB_EP_INVE_XCL_DTL 에 INVE_XCL_NO 와 같은건에 대해 INVE_SLIP_ST,INVE_SLIP_NO,INVE_SLIP_DT 초기화
            Map<String ,String> params = new HashMap<String ,String>();
            params.put("TASK_ID", "EPR011");
            params.put("TASK_NM", "전표취소");
            params.put("USER_NO", ca.getUserNo());
            params.put("COMPONENTNAME_LOCAL_ONLY", "dms.inf.EPR011");               
            params.put("POST_SLIP_DATASET", dsXml);
            String jobExecutionId = callBatchJob("EPR011", params, onlineCtx);
            waitBatchJobEnd(jobExecutionId, 10000);
            int result = getJobReturnCode(jobExecutionId);

            if(result == -1) throw new BizRuntimeException("DMS00009"); // 시스템 오류가 발생하였습니다.
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); //시스템 오류
        }
        
        return responseData;
    }

    /**
	 * <pre>재고반제전표등록오류체크</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-10-14 10:18:26
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : INVE_RVED_SLIP_LIST
	 *		- field : ROWNO [ROWNO]
	 *		- field : CNSL_MGMT_NO [관리번호]
	 *		- field : EQP_MDL_CD [모델코드]
	 *		- field : EQP_MDL_NM [모델명]
	 *		- field : EQP_SER_NO [단말기일련번호]
	 *		- field : XCL_AMT [정산금액]
	 *		- field : INVE_SLIP_NO [재고전표번호]
	 *		- field : ERROR [ERROR]
	 *		- field : INVE_SLIP_DT [재고전표일자]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : INVE_RVED_SLIP_LIST
	 *		- field : ROWNO [ROWNO]
	 *		- field : CNSL_MGMT_NO [관리번호]
	 *		- field : EQP_MDL_CD [모델코드]
	 *		- field : EQP_MDL_NM [모델명]
	 *		- field : EQP_SER_NO [단말기일련번호]
	 *		- field : XCL_AMT [정산금액]
	 *		- field : INVE_SLIP_NO [재고전표번호]
	 *		- field : ERROR [ERROR]
	 *		- field : INVE_SLIP_DT [재고전표일자]
	 * </pre>
	 */
	public IDataSet fInqInveRvedSlipRegErrChkList(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet reqDs = (IDataSet) requestData.clone();//원거래의 DataSet의 훼손을 막기 위한 Clone
        IDataSet resData = new DataSet();
        IDataSet resDataTemp = new DataSet();

        IDataSet inveOrgDs = null;
        IDataSet inveDs = null;

        try {
            
            // DU lookup
            DEPInveXclMgmt dEPInveXclMgmt = (DEPInveXclMgmt) lookupDataUnit(DEPInveXclMgmt.class);
            
            // 업무 DM호출
            String strErr = "";
            int nArriveCnt = 0;
            int nArriveNotCnclCnt = 0;
            int nNotArriveCnt = 0;
            String strArriveSlipNo = "";
            String strNotArriveDedcTypCd = "";
            String strNotArriveInveClCd = "";
            String strNotArriveSknCl = "";
            IRecord rEqpSts = null;
            IRecordSet inveRs = reqDs.getRecordSet("INVE_RVED_SLIP_LIST");
            inveRs.addHeader(8, new RecordHeader("INVE_SLIP_DT"));
            if (inveRs != null) {
                for (int i = 0; i < inveRs.getRecordCount(); i++) {
                    inveOrgDs = new DataSet();
                    inveDs = new DataSet();
                    inveOrgDs.putFieldMap(inveRs.getRecordMap(i));
                    rEqpSts = inveRs.getRecord(i);
                    
                    strErr = "";
                    nArriveNotCnclCnt = 0;
                    // 1. 원 정산 데이터 조회
                    resData = dEPInveXclMgmt.dSInqInveXclEqpSts(inveOrgDs, onlineCtx);
                    if(resData != null && resData.getRecordSetCount() > 0 && resData.getRecordSet("RS_INVE_XCL_EQP_STS") != null && resData.getRecordSet("RS_INVE_XCL_EQP_STS").getRecordCount() > 0){
                        inveDs.putFieldMap(resData.getRecordSet("RS_INVE_XCL_EQP_STS").getRecordMap(0));
                        if(inveDs.getField("INVE_TYP_CD") != null && "10".equals(inveDs.getField("INVE_TYP_CD").toString())){
                            nArriveCnt++;
                            if(i == 0){
                                strArriveSlipNo = inveDs.getField("INVE_SLIP_NO").toString();
                            }
                        }else if(inveDs.getField("INVE_TYP_CD") != null && "20".equals(inveDs.getField("INVE_TYP_CD").toString())){
                            nNotArriveCnt++;
                            if(i == 0){
                                strNotArriveDedcTypCd = inveDs.getField("DEDC_TYP_CD").toString();
                                strNotArriveInveClCd = inveDs.getField("INVE_CL_CD").toString();
                                strNotArriveSknCl = inveDs.getField("INVE_SKN_CL").toString();
                            }
                            resDataTemp = new DataSet();
                            resDataTemp = dEPInveXclMgmt.dSInqEpEqpArrivInveCnclYn(inveOrgDs, onlineCtx);
                            if(resDataTemp != null && resDataTemp.getRecordSet("RS_ARRIV_INVE_CNCL_YN") != null && resDataTemp.getRecordSet("RS_ARRIV_INVE_CNCL_YN").getRecordCount() > 0){
                                for(int j = 0; j < resDataTemp.getRecordSet("RS_ARRIV_INVE_CNCL_YN").getRecordCount(); j++){
                                    if("N".equals(resDataTemp.getRecordSet("RS_ARRIV_INVE_CNCL_YN").get(j, "CNCL_SLIP_YN").toString())){
                                        nArriveNotCnclCnt = 1;
                                        break;
                                    }
                                }
                            }
                        }
                        if(nArriveCnt > 0 && nNotArriveCnt > 0){
                            throw new BizRuntimeException("DMS00170"); //반제전표는 미착재고,확정재고를 동시에 처리할수 없습니다.
                        }
                        if((inveDs.getField("CNCL_SLIP_YN") != null && "Y".equals(inveDs.getField("CNCL_SLIP_YN").toString())) ||
                        (inveDs.getField("INVE_CNCL_SLIP_NO") != null && !"".equals(inveDs.getField("INVE_CNCL_SLIP_NO").toString()))){
                            strErr = strErr + "기반제전표등록데이터";//+inveDs.getField("INVE_CNCL_SLIP_NO").toString();
                        }
                        else{
                            if(inveDs.getField("EQP_MDL_CD") != null && !inveOrgDs.getField("EQP_MDL_CD").toString().equals(inveDs.getField("EQP_MDL_CD").toString())){
                                strErr = strErr + "모델코드";
                            }
                            if(i > 0 && nArriveCnt > 1 && !strArriveSlipNo.equals(inveDs.getField("INVE_SLIP_NO").toString())){
                                if(strErr != null && !"".equals(strErr)) strErr = strErr + ",";
                                strErr = strErr + "확정전표번호";
                            }
                            if(i > 0 && nNotArriveCnt > 1){
                                if(!strNotArriveDedcTypCd.equals(inveDs.getField("DEDC_TYP_CD").toString())){
                                    if(strErr != null && !"".equals(strErr)) strErr = strErr + ",";
                                    strErr = strErr + "보상유형";
                                }
                                if(!strNotArriveInveClCd.equals(inveDs.getField("INVE_CL_CD").toString())){
                                    if(strErr != null && !"".equals(strErr)) strErr = strErr + ",";
                                    strErr = strErr + "재고구분(에코,자수)";
                                }
                                if(!strNotArriveSknCl.equals(inveDs.getField("INVE_SKN_CL").toString())){
                                    if(strErr != null && !"".equals(strErr)) strErr = strErr + ",";
                                    strErr = strErr + "현장구분";
                                }
                            }
                            /*
                            if(inveDs.getField("EQP_MDL_NM") != null && !inveOrgDs.getField("EQP_MDL_NM").toString().equals(inveDs.getField("EQP_MDL_NM").toString())){
                                if(strErr != null && !"".equals(strErr)) strErr = strErr + ",";
                                strErr = strErr + "모델명";
                            }
                            */
                            if(inveDs.getField("EQP_SER_NO") != null && !inveOrgDs.getField("EQP_SER_NO").toString().equals(inveDs.getField("EQP_SER_NO").toString())){
                                if(strErr != null && !"".equals(strErr)) strErr = strErr + ",";
                                strErr = strErr + "일련번호";
                            }
                            if(inveDs.getField("XCL_AMT") != null && !inveOrgDs.getField("XCL_AMT").toString().equals(inveDs.getField("XCL_AMT").toString())){
                                if(strErr != null && !"".equals(strErr)) strErr = strErr + ",";
                                strErr = strErr + "매입금액";
                            }
                            if(strErr != null && !"".equals(strErr)) strErr = strErr + " 비동일";
                            if(nArriveNotCnclCnt > 0){
                                if(strErr != null && !"".equals(strErr)) strErr = strErr + ",";
                                strErr = strErr + "확정전표반제미처리";
                            }
                            if(inveDs.getField("INVE_SLIP_ST") == null || (inveDs.getField("INVE_SLIP_ST") != null && !"20".equals(inveDs.getField("INVE_SLIP_ST").toString()))){
                                if(strErr != null && !"".equals(strErr)) strErr = strErr + ",";
                                strErr = strErr + "회계미승인전표번호";
                            }
                            if(inveDs.getField("INVE_TYP_CD") != null && ("20".equals(inveDs.getField("INVE_TYP_CD").toString()) || "10".equals(inveDs.getField("INVE_TYP_CD").toString()))){
                                /* 
                                if(inveDs.getField("DEDC_TYP_CD") != null && ("01".equals(inveDs.getField("DEDC_TYP_CD").toString()) || "03".equals(inveDs.getField("DEDC_TYP_CD").toString()))){
                                    if("20".equals(inveDs.getField("INVE_TYP_CD").toString()) || (inveDs.getField("SKN_CL") == null && "Y".equals(inveDs.getField("SKN_CL").toString()))){//미착,현장
                                        if(inveDs.getField("PAY_DT") != null && !"".equals(inveDs.getField("PAY_DT").toString())){
                                            if(strErr != null && !"".equals(strErr)) strErr = strErr + ",";
                                            if("01".equals(inveDs.getField("DEDC_TYP_CD").toString())) {
                                                strErr = strErr + "송금취소미처리";
                                            }else{
                                                strErr = strErr + "요금공제취소미처리";
                                            }
                                        }
                                    }else{//비현장
                                        if("01".equals(inveDs.getField("DEDC_TYP_CD").toString()) && (inveDs.getField("RMT_DT") != null && !"".equals(inveDs.getField("RMT_DT").toString()))) { //송금
                                            if(strErr != null && !"".equals(strErr)) strErr = strErr + ",";
                                            strErr = strErr + "송금취소미처리";
                                        }else if("03".equals(inveDs.getField("DEDC_TYP_CD").toString()) && (inveDs.getField("FEE_DEDC_PROC_DT") != null && !"".equals(inveDs.getField("FEE_DEDC_PROC_DT").toString()))){ //요금공제
                                            if(strErr != null && !"".equals(strErr)) strErr = strErr + ",";
                                            strErr = strErr + "요금공제취소미처리";
                                        }
                                    }
                                }
                                if("20".equals(inveDs.getField("INVE_TYP_CD").toString())){//미착일경우만 입고제외 확인하게 수정--확정은 추후 확인필요
                                    if(inveDs.getField("IN_CONF_YN") != null && !"N".equals(inveDs.getField("IN_CONF_YN").toString())){
                                        if(strErr != null && !"".equals(strErr)) strErr = strErr + ",";
                                        strErr = strErr + "입고제외미처리";
                                    }
                                }
                                if(inveDs.getField("PROGR_ST") != null && "13".equals(inveDs.getField("PROGR_ST").toString())){
                                    if(strErr != null && !"".equals(strErr)) strErr = strErr + ",";
                                    strErr = strErr + "검수취소미처리";
                                }
                                */
                            }else{
                                // 5. 기타
                                if(strErr != null && !"".equals(strErr)) strErr = strErr + ",";
                                strErr = strErr + "재고유형오류";
                            }
                            rEqpSts.set("INVE_SLIP_DT", inveDs.getField("INVE_SLIP_DT").toString());
                        }
                    }
                    else{
                        strErr = strErr + "재고정산전표 또는 상담자료 미존재";
                    }
                    rEqpSts.set("ERROR", strErr);
                    inveRs.setRecord(i, rEqpSts);
                }
                responseData.putRecordSet("INVE_RVED_SLIP_LIST", inveRs);
            }
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); //시스템 오류
        }
    
        return responseData;
    }

    /**
	 * <pre>[FM]중고단말기재고반제전표등록</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-10-14 10:18:26
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : INVE_RVED_SLIP_LIST
	 *		- field : ROWNO [ROWNO]
	 *		- field : CNSL_MGMT_NO [관리번호]
	 *		- field : EQP_MDL_CD [모델코드]
	 *		- field : EQP_MDL_NM [모델명]
	 *		- field : EQP_SER_NO [단말기일련번호]
	 *		- field : XCL_AMT [정산금액]
	 *		- field : INVE_SLIP_NO [재고전표번호]
	 *		- field : ERROR [ERROR]
	 *		- field : INVE_SLIP_DT [재고전표일자]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fIEpEqpInveRvedSlipRgst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet reqDs = (IDataSet) requestData.clone();//원거래의 DataSet의 훼손을 막기 위한 Clone
        IDataSet resData = new DataSet();
        IDataSet inveDs = null;

        IRecordSet rsInveXclList = null;

        String strNotArrivInveXclNo = ""; //미착재고정산번호
        String strArrivInveXclNo = ""; //확정재고정산번호
        String sCurrDt = DateUtils.getCurrentDate(); // 현재일자
        
        try {
            CommonArea ca = getCommonArea(onlineCtx);
            // 1. DU lookup
            DEPInveXclMgmt dEPInveXclMgmt = (DEPInveXclMgmt) lookupDataUnit(DEPInveXclMgmt.class);
            IRecordSet inveRs = reqDs.getRecordSet("INVE_RVED_SLIP_LIST");
            int nArrivNo = 0;
            int nNotArrivNo = 0;
            if (inveRs != null) {
                // 재고정산일련번호채번
                for (int i = 0; i < inveRs.getRecordCount(); i++) {
                    inveDs = new DataSet();
                    inveDs.putFieldMap(inveRs.getRecordMap(i));
                    //재고유형
                    resData = dEPInveXclMgmt.dSInqEpEqpInveXclInfo(inveDs, onlineCtx);
                    rsInveXclList = resData.getRecordSet("RS_INVE_XCL_EQP_INFO");
                    if(rsInveXclList != null && rsInveXclList.getRecordCount() > 0 && rsInveXclList.get(0, "INVE_TYP_CD") != null && !"".equals(rsInveXclList.get(0, "INVE_TYP_CD").toString())){
                        if("20".equals(rsInveXclList.get(0, "INVE_TYP_CD").toString())){
                            if("".equals(strNotArrivInveXclNo)){
                                resData = dEPInveXclMgmt.dSInqEpEqpInveXclSeq(requestData, onlineCtx);
                                strNotArrivInveXclNo = resData.getField("INVE_XCL_NO").toString();
                            }
                            inveDs.putField("INVE_XCL_NO", strNotArrivInveXclNo);
                            nNotArrivNo++;
                            inveDs.putField("INVE_XCL_DTL_SEQ", nNotArrivNo);
                        }else{
                            if("".equals(strArrivInveXclNo)){
                                resData = dEPInveXclMgmt.dSInqEpEqpInveXclSeq(requestData, onlineCtx);
                                strArrivInveXclNo = resData.getField("INVE_XCL_NO").toString();
                            }
                            inveDs.putField("INVE_XCL_NO", strArrivInveXclNo);
                            nArrivNo++;
                            inveDs.putField("INVE_XCL_DTL_SEQ", nArrivNo);
                            if(rsInveXclList.get(0, "ASMT_DMG_AMT") != null){
                                inveDs.putField("ASMT_DMG_AMT", rsInveXclList.get(0, "EQP_ASMT_DMG_AMT"));
                            }
                        }
                        inveDs.putField("FS_REG_USER_ID", ca.getUserNo());
                        inveDs.putField("LS_CHG_USER_ID", ca.getUserNo());
                        if(inveDs.getField("XCL_AMT") != null && !"".equals(inveDs.getField("XCL_AMT").toString()) && !"0".equals(inveDs.getField("XCL_AMT").toString())){
                            inveDs.putField("XCL_AMT", Long.parseLong(inveDs.getField("XCL_AMT").toString())*-1);
                        }
                        if(inveDs.getField("ASMT_DMG_AMT") != null && !"".equals(inveDs.getField("ASMT_DMG_AMT").toString()) && !"0".equals(inveDs.getField("ASMT_DMG_AMT").toString())){
                            inveDs.putField("ASMT_DMG_AMT", Long.parseLong(inveDs.getField("ASMT_DMG_AMT").toString())*-1);
                        }
                        // 상세등록
                        dEPInveXclMgmt.dIEpEqpInveXclDtlRgst(inveDs, onlineCtx);
                    }
                }
                //미착재고합산
                if(!"".equals(strNotArrivInveXclNo)){
                    inveDs = new DataSet();
                    inveDs.putField("FS_REG_USER_ID", ca.getUserNo());
                    inveDs.putField("LS_CHG_USER_ID", ca.getUserNo());
                    inveDs.putField("INVE_XCL_NO", strNotArrivInveXclNo);
                    inveDs.putField("INVE_SLIP_ST", "00");
                    inveDs.putField("CNCL_SLIP_YN", "Y");
                    inveDs.putField("SKN_CL", rsInveXclList.get(0, "SKN_CL"));
                    inveDs.putField("INVE_CL_CD", rsInveXclList.get(0, "INVE_CL_CD"));
                    inveDs.putField("DEDC_TYP_CD", rsInveXclList.get(0, "DEDC_TYP_CD"));
                    // 합계조회
                    resData = dEPInveXclMgmt.dSInqEpEqpInveXclSumList(inveDs, onlineCtx);
                    rsInveXclList = resData.getRecordSet("INVE_XCL_SUM_LIST");
                    if(rsInveXclList != null && rsInveXclList.getRecordCount() > 0 && Long.parseLong(rsInveXclList.get(0, "INVE_XCL_CNT").toString()) > 0){
                        inveDs.putField("XCL_AMT", rsInveXclList.get(0, "XCL_AMT")); //정산 금액
                        inveDs.putField("INVE_XCL_CNT", rsInveXclList.get(0, "INVE_XCL_CNT")); //재고 정산 건수
                        inveDs.putField("INVE_TYP_CD", "20");
                        // 정산등록
                        inveDs.putField("XCL_STRD_DT", sCurrDt); //정산 기준 일자
                        dEPInveXclMgmt.dIEpEqpInveXclRgst(inveDs, onlineCtx);
                    }
                }
                //확정재고합산
                if(!"".equals(strArrivInveXclNo)){
                    inveDs = new DataSet();
                    inveDs.putField("FS_REG_USER_ID", ca.getUserNo());
                    inveDs.putField("LS_CHG_USER_ID", ca.getUserNo());
                    inveDs.putField("INVE_XCL_NO", strArrivInveXclNo);
                    inveDs.putField("INVE_SLIP_ST", "00");
                    inveDs.putField("CNCL_SLIP_YN", "Y");
                    inveDs.putField("SKN_CL", rsInveXclList.get(0, "SKN_CL"));
                    inveDs.putField("INVE_CL_CD", rsInveXclList.get(0, "INVE_CL_CD"));
                    inveDs.putField("DEDC_TYP_CD", rsInveXclList.get(0, "DEDC_TYP_CD"));
                    // 합계조회
                    resData = dEPInveXclMgmt.dSInqEpEqpInveXclSumList(inveDs, onlineCtx);
                    rsInveXclList = resData.getRecordSet("INVE_XCL_SUM_LIST");
                    if(rsInveXclList != null && rsInveXclList.getRecordCount() > 0 && Long.parseLong(rsInveXclList.get(0, "INVE_XCL_CNT").toString()) > 0){
                        inveDs.putField("XCL_AMT", rsInveXclList.get(0, "XCL_AMT")); //정산 금액
                        inveDs.putField("ASMT_DMG_AMT", rsInveXclList.get(0, "ASMT_DMG_AMT")); //손실금액
                        inveDs.putField("INVE_XCL_CNT", rsInveXclList.get(0, "INVE_XCL_CNT")); //재고 정산 건수
                        inveDs.putField("INVE_TYP_CD", "10");
                        // 정산등록
                        inveDs.putField("XCL_STRD_DT", sCurrDt); //정산 기준 일자
                        dEPInveXclMgmt.dIEpEqpInveXclRgst(inveDs, onlineCtx);
                    }
                }
            }
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); //시스템 오류
        }
    
        return responseData;
    }

    /**
	 * <pre>[FM]재고정산관리삭제</pre>
	 *
	 * @author 양재석 (jsyang)
	 * @since 2015-11-03 12:54:39
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RQ_INVE_XCL_LIST
	 *		- field : INVE_XCL_NO [재고정산번호]
	 *		- field : XCL_AMT [정산금액]
	 *		- field : INVE_XCL_CNT [재고정산건수]
	 *		- field : INVE_SLIP_DT [전표발행일자]
	 *		- field : INVE_SLIP_NO [전표번호]
	 *		- field : INVE_SLIP_ST [전표상태]
	 *		- field : INVE_CNCL_SLIP_DT [취소전표일자]
	 *		- field : INVE_CNCL_SLIP_NO [취소전표번호]
	 *		- field : ASMT_DMG_AMT [손실금액]
	 *		- field : INVE_TYP_CD [재고유형]
	 *		- field : YYYY [YYYY]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fDelInveXcl(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
        IDataSet reqDs = (IDataSet) requestData.clone();//원거래의 DataSet의 훼손을 막기 위한 Clone
        CommonArea ca = getCommonArea(onlineCtx);
        IDataSet resData = new DataSet();
        
        IDataSet inveDs = null;

        try {
            
            // DU lookup
            DEPInveXclMgmt dEPInveXclMgmt = (DEPInveXclMgmt) lookupDataUnit(DEPInveXclMgmt.class);
            
            // 업무 DM호출
            IRecordSet inveRs = reqDs.getRecordSet("RQ_INVE_XCL_LIST");
            for(int i = 0; i < inveRs.getRecordCount(); i++){
                reqDs.putFieldMap(inveRs.getRecordMap(i));
                reqDs.putField("LS_CHG_USER_ID", ca.getUserNo());
                resData = dEPInveXclMgmt.dSInqEpEqpInveXclList(reqDs, onlineCtx);
                resData.getRecordSet("RS_INVE_XCL_LIST");
                if(resData != null && resData.getRecordSet("RS_INVE_XCL_LIST") != null && resData.getRecordSet("RS_INVE_XCL_LIST").getRecordCount() > 0
                && resData.getRecordSet("RS_INVE_XCL_LIST").getRecord(0).get("INVE_SLIP_ST") != null){
                    String slipSt = resData.getRecordSet("RS_INVE_XCL_LIST").getRecord(0).get("INVE_SLIP_ST").toString();
                    if(slipSt != null && !"".equals(slipSt) && !"00".equals(slipSt) && !"05".equals(slipSt)){
                        throw new BizRuntimeException("DMS00025"); // 삭제할 수 없는 데이터 입니다.
                    }
                }
                //정산 상세 삭제
                dEPInveXclMgmt.dDEpEqpInveXclDtlDel(reqDs, onlineCtx);
                //정산 삭제
                dEPInveXclMgmt.dDEpEqpInveXclDel(reqDs, onlineCtx);
            }
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); //시스템 오류
        }

        return responseData;
    }
  
}
