package dms.nr.nrbeibase.biz;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.exception.BizRuntimeException;
import nexcore.framework.core.util.StringUtils;
import fwk.common.CommonArea;
import fwk.constants.DmsConstants;


/**
 * <ul>
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [PU]보증보험관리</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-08-18 12:25:20</li>
 * <li>작성자 : 안진갑 (bella21cjk)</li>
 * </ul>
 *
 * @author 안진갑 (bella21cjk)
 */
public class PNREqpInsuMgmt extends fwk.base.ProcessUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public PNREqpInsuMgmt(){
		super();
	}

    /**
	 *
	 *
	 * @author 이영진 (newnofixing)
	 * @since 2015-10-13 13:14:31
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : EQP_MDL_CD [모델코드]
	 *	- field : EFF_PRD_STA_DT [필드1]
	 *	- field : EFF_PRD_END_DT [필드2]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 * <pre>
	 *	- record : RS_EQP_INSU_LIST
	 *		- field : EQP_MDL_CD [모델코드]
	 *		- field : EQP_MDL_NM [모델명]
	 *		- field : OUT_PRC [출고가]
	 *		- field : EQP_INSURE_FEE [단말보험료]
	 *		- field : LAUNC_DT [출시일]
	 *		- field : REG_DT [등록일]
	 *		- field : EQP_INSURE_RMK [비고]
	 *		- field : OP_CL_CD [업무구분코드]
	 *		- field : CAPA_CD [용량코드]
	 *		- field : EQP_INSURE_NO [단말기보험번호]
	 * </pre>
	 */
	public IDataSet pInqEqpInsuLst(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 처리 결과값을 responseData에 넣어서 리턴하십시요 
        try {
            // 1. FM 호출
            responseData = callSharedBizComponentByDirect("nr.NRSEIBase", "fInqEqpInsuLst", requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }
        // 3. 결과값 리턴
        responseData.setOkResultMessage("DMS00001", null); // 정상 조회되었습니다.
        return responseData;
    }

    /**
	 *
	 *
	 * @author 박홍서 (uni9401)
	 * @since 2015-10-13 13:14:31
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_EQP_INSU
	 *		- field : EQP_INSURE_NO [단말기보험번호]
	 *		- field : EQP_MDL_CD [모델코드]
	 *		- field : OP_CL_CD [업무구분코드]
	 *		- field : CAPA_CD [용량코드]
	 *		- field : EQP_INSURE_FEE [단말기보험요금]
	 *		- field : EQP_INSURE_RMK [단말기보험비고]
	 *		- field : EFF_PRD_STA_DT [필드1]
	 *		- field : EFF_PRD_END_DT [필드2]
	 *		- field : POLY_USE_YN [필드3]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
    public IDataSet pSaveEqpInsu(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        // 처리 결과값을 responseData에 넣어서 리턴하십시요 
    	CommonArea ca = getCommonArea(onlineCtx);    	

		try {
			// 1. 입력 RS설정
			requestData.putFieldMap(requestData.getRecordSet("RS_EQP_INSU").getRecordMap(0));
			requestData.putField("USERNO", ca.getUserNo());
			// 2. 레코드셋 상태에 따른 분기
			if ( "I".equals(ca.getTrnPtrnDvcd()) ) { // INSERT
				callSharedBizComponentByDirect("nr.NRSEIBase", "fRegEqpInsu", requestData, onlineCtx);
			} else if ( "U".equals(ca.getTrnPtrnDvcd()) ) { // UPDATE
				callSharedBizComponentByDirect("nr.NRSEIBase", "fUpdEqpInsu", requestData, onlineCtx);
			}
			// 3. 결과값 리턴
			responseData.setOkResultMessage("DMS00000", null); //정상 처리되었습니다.
		} catch ( BizRuntimeException e ) {
			throw e;
		} catch ( Exception e ) {
			throw new BizRuntimeException("DMS00009", e); //시스템 오류
		}
    	
        return responseData;
    }
  
}
