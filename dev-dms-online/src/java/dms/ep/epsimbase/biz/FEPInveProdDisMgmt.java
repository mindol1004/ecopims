package dms.ep.epsimbase.biz;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.RecordHeader;
import nexcore.framework.core.data.RecordSet;
import nexcore.framework.core.exception.BizRuntimeException;

import org.apache.commons.lang.StringUtils;

import fwk.utils.PagenateUtils;


/**
 * <ul>
 * <li>업무 그룹명 : dms/에코폰</li>
 * <li>단위업무명: [FU]재고상품관리</li>
 * <li>설  명 : <pre>재고상품관리 FU</pre></li>
 * <li>작성일 : 2015-09-08 14:40:22</li>
 * <li>작성자 : 이민재 (mindol1004)</li>
 * </ul>
 *
 * @author 이민재 (mindol1004)
 */
public class FEPInveProdDisMgmt extends fwk.base.FunctionUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public FEPInveProdDisMgmt(){
		super();
	}

    /**
	 * <pre>재고상품조회</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-09-08 14:40:22
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : HLD_DEALCO_ID [출고처ID]
	 *	- field : HLD_DEALCO_NM [출고처명]
	 *	- field : PROD_SER_NO [상품일련번호]
	 *	- field : MFACT_CD [제조사코드]
	 *	- field : EQP_SER_NO [일련번호]
	 *	- field : CNSL_MGMT_NO [접수관리번호]
	 *	- field : EQP_MDL_CD [모델코드]
	 *	- field : BOX_NO [BOX NO]
	 *	- field : SELL_TYP [판매유형]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_INVE_DIS_LIST
	 *		- field : EQP_MDL_CD [모델코드]
	 *		- field : EQP_COLOR_CD [모델색상]
	 *		- field : PROD_SER_NO [상품일련번호]
	 *		- field : EQP_ST [단말기상태]
	 *		- field : INVE_ST [재고상태]
	 *		- field : HLD_DEALCO_ID [보유거래처ID]
	 *		- field : HLD_DEALCO_NM [보유거래처명]
	 *		- field : EQP_SER_NO [일련번호]
	 *		- field : UPDATE_COUNT [UPDATE COUNT]
	 *		- field : CNSL_MGMT_NO [상담관리번호]
	 *		- field : CONF_SELL_AMT [확정판매금액]
	 *		- field : PROD_CL [상품구분]
	 *		- field : MFACT_CD [제조사코드]
	 *		- field : EQP_MDL_NM [모델명]
	 *		- field : LAUNC_DT [출시일]
	 *		- field : PRCH_MGMT_NO [매입관리번호]
	 *		- field : BOX_NO [BOX_NO]
	 *		- field : SELL_CHG_HST_CL [구분]
	 *		- field : SALE_QTY [판매수량]
	 *		- field : FIX_PRC_YN [고정가여부]
	 *		- field : SELL_PCOST [판매원가]
	 *		- field : SELL_UPRC [판매단가]
	 *		- field : SELL_MRGN [판매마진]
	 *		- field : SELL_CMMS [판매수수료]
	 *		- field : SURTAX [부가세]
	 *		- field : SELL_AMT [판매가(부가세포함)]
	 * </pre>
	 */
	public IDataSet fInqInveProdDisList(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        IDataSet dsCnt = new DataSet();
        IDataSet reqDs = (IDataSet) requestData.clone();//원거래의 DataSet의 훼손을 막기 위한 Clone
        IRecordSet rsPagingList = null;
        int intTotalCnt = 0;

        try {
            // 1. DU lookup
            DEPInveProdDisMgmt dEPInveProdDisMgmt = (DEPInveProdDisMgmt) lookupDataUnit(DEPInveProdDisMgmt.class);
            // 2. TOTAL COUNT DM호출
            dsCnt = dEPInveProdDisMgmt.dSInveProdDisTotCnt(reqDs, onlineCtx);
            intTotalCnt = Integer.parseInt(dsCnt.getField("TOTAL_CNT"));
            PagenateUtils.setPagenatedParamsToDataSet(reqDs);
            // 3. LISTDM호출
            responseData = dEPInveProdDisMgmt.dSInveProdDisPaging(reqDs, onlineCtx);

            rsPagingList = responseData.getRecordSet("RS_INVE_DIS_LIST");
            PagenateUtils.setPagenatedParamToRecordSet(rsPagingList, reqDs, intTotalCnt);
            
        } catch ( BizRuntimeException e ) {
            throw e;
        }
    
        return responseData;
    }

    /**
	 * <pre>재고상품체크</pre>
	 *
	 * @author 이민재 (mindol1004)
	 * @since 2015-09-08 14:40:22
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_INVE_DIS_LIST
	 *		- field : EQP_MDL_CD [모델코드]
	 *		- field : PROD_SER_NO [일련번호]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_INVE_ERR_LIST
	 *		- field : ERR_MSG [에러메세지]
	 * </pre>
	 */
	public IDataSet fInqInveProdDisChkList(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        IDataSet reqDs = (IDataSet) requestData.clone();//원거래의 DataSet의 훼손을 막기 위한 Clone
        IDataSet inveDs = null;
        
        try {
            // 1. DU lookup
            DEPInveProdDisMgmt dEPInveProdDisMgmt = (DEPInveProdDisMgmt) lookupDataUnit(DEPInveProdDisMgmt.class);

            String ERR_MSG = "";
            
            IRecordSet rs = reqDs.getRecordSet("RS_INVE_DIS_LIST");
            for (int i = 0; i < rs.getRecordCount(); i++) {
                inveDs = new DataSet();
                inveDs.putFieldMap(rs.getRecordMap(i));
                
                // 2. LISTDM호출
                IRecordSet inveRs = dEPInveProdDisMgmt.dSInveProdDisChkList(inveDs, onlineCtx).getRecordSet("RS_INVE_CHK_LIST");
                
                if (inveRs.getRecordCount() == 0) {
                    ERR_MSG = ERR_MSG + inveDs.getField("PROD_SER_NO")
                            + " : 해당상품이 존재하지 않습니다.\\n";
                } else {
                    String INVE_ST = inveRs.get(0, "INVE_ST");
                    if ("02".equals(INVE_ST)) {
                        ERR_MSG = ERR_MSG + inveRs.get(0, "EQP_SER_NO")
                                + " : 비가용상태입니다.\\n";
                    } else {
                        String TRMS_CL = inveRs.get(0, "TRMS_CL");
                        String TRMS_YN = inveRs.get(0, "TRMS_YN");
                        
                        if("N".equals(TRMS_YN)){
                            if(!"N".equals(TRMS_CL)){
                                ERR_MSG = ERR_MSG + inveRs.get(0, "EQP_SER_NO")
                                        + " : U.key, T.Key 인터페이스 작업 중인 데이터 입니다.\\n";
                            }
                        }
                    }
                }
            }
            
            IRecordSet returnRs = new RecordSet("RS_INVE_ERR_LIST");
            returnRs.addHeader(new RecordHeader("ERR_MSG"));

            if (StringUtils.isNotEmpty(ERR_MSG)) {
                IRecord recordContents = returnRs.newRecord();
                recordContents.put("ERR_MSG", ERR_MSG);
                returnRs.addRecord(recordContents);
            }
            
            responseData.putRecordSet("RS_INVE_ERR_LIST", returnRs);
            
        } catch ( BizRuntimeException e ) {
            throw e; //시스템 오류가 발생하였습니다.
        }
    
        return responseData;
    }
  
}
