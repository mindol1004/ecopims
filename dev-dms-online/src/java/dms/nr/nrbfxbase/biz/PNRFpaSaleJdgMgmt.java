package dms.nr.nrbfxbase.biz;

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
 * <li>업무 그룹명 : dms/신규R</li>
 * <li>단위업무명: [PU]FPA판매감정관리</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2016-01-26 11:21:56</li>
 * <li>작성자 : 안진갑 (bella21cjk)</li>
 * </ul>
 *
 * @author 안진갑 (bella21cjk)
 */
public class PNRFpaSaleJdgMgmt extends fwk.base.ProcessUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public PNRFpaSaleJdgMgmt(){
		super();
	}

	/**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2016-01-26 11:21:56
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : CNTRT_NO [계약번호]
	 *	- field : SELL_GRADE_SEQ [순번]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet pInqSaleJdgDtlLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    try {
            //1. FU lookup                                                      
            responseData = callSharedBizComponentByDirect("nr.NRSFXBase", "fInqSaleJdgDtlLst", requestData, onlineCtx);
        
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2016-01-26 11:21:56
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : FR_SALE_JDG [감정일(시작)]
	 *	- field : TO_SALE_JDG [감정일(종료)]
	 *	- field : SVC_MGMT_NO [서비스관리번호]
	 *	- field : FPA_GRADE [FPA등급]
	 *	- field : FPA_DSPSL_DT [Resale Ready 일자]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet pInqSaleJdgLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    try {
            //1. FU lookup                                                      
            responseData = callSharedBizComponentByDirect("nr.NRSFXBase", "fInqSaleJdgLst", requestData, onlineCtx);
        
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }
	
	    return responseData;
	}

    /**
	 *
	 *
	 * @author 장미진 (kuramotojin)
	 * @since 2016-01-26 11:21:56
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : SVC_MGMT_NO [서비스관리번호]
	 *	- field : CNTRT_NO [계약번호]
	 *	- field : SELL_GRADE_SEQ [순번]
	 *	- field : PWR_ST [전원상태]
	 *	- field : USIM_ST [USIM상태]
	 *	- field : RECG_ST [충전상태]
	 *	- field : SBELL_ST [벨소리상태]
	 *	- field : VIBR_ST [진동상태]
	 *	- field : SPK_ST [스피커상태]
	 *	- field : ILRM_SENSOR_ST [조도센서상태]
	 *	- field : GRCP_ST [자이로스코프상태]
	 *	- field : APRC_SENSOR_ST [근접센서상태]
	 *	- field : CAMR_ST [카메라상태]
	 *	- field : BATER_ST [베터리상태]
	 *	- field : AFIMG_ST [잔상상태]
	 *	- field : TOUCH_ST [터치상태]
	 *	- field : WIFI_ST [WIFI상태]
	 *	- field : STAIN_ST [얼룩상태]
	 *	- field : BRUI_ST [멍상태]
	 *	- field : SQUS_ST [눌림상태]
	 *	- field : LQFD_INFLO_ST [액상유입상태]
	 *	- field : LED_ST [LED상태]
	 *	- field : BUTN_ST [버튼상태]
	 *	- field : BOTM_TOUCH_ST [하단터치상태]
	 *	- field : BODY_ST [몸체상태]
	 *	- field : DMB_ST [DMB상태]
	 *	- field : LCGLS_ST [액정유리상태]
	 *	- field : CHIP_ST [이나감상태]
	 *	- field : CAMR_WINDO_ST [카메라윈도우상태]
	 *	- field : EDGE_ST [테두리상태]
	 *	- field : BACK_CL [반환구분]
	 *	- field : BACK_ST [반환상태]
	 *	- field : SELL_GRADE [판매등급]
	 *	- field : PRCH_GRADE [매입등급]
	 *	- field : FGSC_ST [지문인식상태]
	 *	- field : EQP_ST_RMK [상태비고]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet pSaveSaleJdgDtl(IDataSet requestData, IOnlineContext onlineCtx) {
        IDataSet responseData = new DataSet();
    
        try {
            //1. FU lookup                                                      
            responseData = callSharedBizComponentByDirect("nr.NRSFXBase", "fSaveSaleJdgDtl", requestData, onlineCtx);
        
        } catch ( BizRuntimeException e ) {
            throw e;
        } catch ( Exception e ) {
            throw new BizRuntimeException("DMS00009", e); // 시스템 오류가 발생하였습니다.
        }
    
        return responseData;
    }

	/**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2016-01-26 11:21:56
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : FPA_DSPSL_DT [Resale Ready 일자]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet pSaveFpaStock(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    responseData = callSharedBizComponentByDirect("nr.NRSFXBase", "fSaveFpaStock", requestData, onlineCtx);
	
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 안진갑 (bella21cjk)
	 * @since 2016-01-26 11:21:56
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- field : FPA_DSPSL_DT [Resale Ready 일자]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet pInqFpaExcel(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	
	    responseData = callSharedBizComponentByDirect("nr.NRSFXBase", "fInqFpaExcel", requestData, onlineCtx);
	
	    return responseData;
	}
  
}
