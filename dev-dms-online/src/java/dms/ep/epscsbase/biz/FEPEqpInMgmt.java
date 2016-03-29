package dms.ep.epscsbase.biz;

import java.util.Map;

import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.exception.BizRuntimeException;
import nexcore.framework.core.log.LogManager;
import nexcore.framework.core.util.StringUtils;

import org.apache.commons.logging.Log;

import fwk.utils.HpcUtils;


/**
 * <ul>
 * <li>업무 그룹명 : dms/에코폰</li>
 * <li>단위업무명: [FU]입고관리</li>
 * <li>설  명 : <pre>[FU]입고관리</pre></li>
 * <li>작성일 : 2015-08-19 09:54:21</li>
 * <li>작성자 : 김상오 (myneti99)</li>
 * </ul>
 *
 * @author 김상오 (myneti99)
 */
public class FEPEqpInMgmt extends fwk.base.FunctionUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public FEPEqpInMgmt(){
		super();
	}

	/**
	 * <pre>[FM]단말기입고대상조회</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-08-19 09:55:16
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fInqEqpInObjList(IDataSet requestData, IOnlineContext onlineCtx) {
		 IDataSet responseData = new DataSet();
	        try {
	            // 1. DU lookup
	        	DEPEqpInMgmt du = (DEPEqpInMgmt) lookupDataUnit(DEPEqpInMgmt.class);
	            // 2. 업무 DM호출
	            responseData = du.dInqEqpInObjList(requestData, onlineCtx);
	        } catch ( BizRuntimeException e ) {
	            throw e;
	        }
		    
		    return responseData;
	}

	/**
	 * <pre>[FM]의견저장</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-08-21 16:15:13
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fUInProcRsn(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
        try {
        	// 1. DU lookup
        	DEPEqpInMgmt du = (DEPEqpInMgmt) lookupDataUnit(DEPEqpInMgmt.class);
            // 2. 업무 DM호출
            responseData = du.dUInProcRsn(requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        }
	   
	    return responseData;
	}

	/**
	 * <pre>[FM]단말기입고대상등록</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-08-24 15:34:05
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fRegEqpInObjList(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        try {
        	// 1. DU lookup
        	DEPEqpInMgmt du = (DEPEqpInMgmt) lookupDataUnit(DEPEqpInMgmt.class);
            // 2. 업무 DM호출
            responseData = du.dIEqpInObjListReg(requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        }
	   
	    return responseData;
	}

	/**
	 * <pre>입고확정등록</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-08-26 17:24:26
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fUpdEqpInChkList(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
        try {
        	// 1. DU lookup
        	DEPEqpInMgmt du = (DEPEqpInMgmt) lookupDataUnit(DEPEqpInMgmt.class);
            // 2. 업무 DM호출
            responseData = du.dUpdEqpInChkList(requestData, onlineCtx); 
        } catch ( BizRuntimeException e ) {
            throw e;
        }
	   
	    return responseData;
	}

	/**
	 * <pre>[FM]입고등록취소</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-08-26 18:20:02
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fUpdEqpRegCncl(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
        try {
        	// 1. DU lookup
        	DEPEqpInMgmt du = (DEPEqpInMgmt) lookupDataUnit(DEPEqpInMgmt.class);
            // 2. 업무 DM호출
            responseData = du.dUpdEqpRegCncl(requestData, onlineCtx); 
        } catch ( BizRuntimeException e ) {
            throw e;
        }
	   
	    return responseData;
	}

	/**
	 * <pre>[FM]입고제외처리</pre>
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-08-19 09:54:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fUEqpInExl(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();
		IDataSet rqRevdData = new DataSet();
        try {
        	// 1. DU lookup
        	DEPEqpInMgmt du = (DEPEqpInMgmt) lookupDataUnit(DEPEqpInMgmt.class);
            // 2. 업무 DM호출
            responseData = du.dUEqpInExl(requestData, onlineCtx); 
            // 2. 반제전표용자료생성
            /*--자동반제처리 사용 안하기로함
            if(requestData != null && requestData.getRecordSet("RS_EQP_IN_OBJ_LIST") != null && requestData.getRecordSet("RS_EQP_IN_OBJ_LIST").getRecordCount() > 0){
                FEPCnclInveRevdSlipRgst fEPCnclInveRevdSlipRgst = (FEPCnclInveRevdSlipRgst) lookupFunctionUnit(FEPCnclInveRevdSlipRgst.class);
                rqRevdData.putRecordSet("RQ_RVED_SLIP_LIST",requestData.getRecordSet("RS_EQP_IN_OBJ_LIST"));
                String strChk = "";
                String strJdgCd = "";
                for(int i = (rqRevdData.getRecordSet("RQ_RVED_SLIP_LIST").getRecordCount()-1); i >= 0; i--){
                    strChk = rqRevdData.getRecordSet("RQ_RVED_SLIP_LIST").getRecord(i).get("PRC_CHECK").toString();
                    strJdgCd = rqRevdData.getRecordSet("RQ_RVED_SLIP_LIST").getRecord(i).get("JDG_CD").toString();
                    if(strChk == null || "".equals(strChk) || "0".equals(strChk) || strJdgCd == null || "".equals(strJdgCd) || !"RE".equals(strJdgCd)){
                        rqRevdData.getRecordSet("RQ_RVED_SLIP_LIST").removeRecord(i);
                    }
                }
                if(rqRevdData != null && rqRevdData.getRecordSet("RQ_RVED_SLIP_LIST") != null && rqRevdData.getRecordSet("RQ_RVED_SLIP_LIST").getRecordCount() > 0){
                    fEPCnclInveRevdSlipRgst.fRegCnclInveRevdSlipRgst(rqRevdData, onlineCtx);
                }
            }
            */
        } catch ( BizRuntimeException e ) {
            throw e;
        }
	   
	    return responseData;
	}

	/**
	 *
	 *
	 * @author 김상오 (myneti99)
	 * @since 2015-08-19 09:54:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fRegPrNrIn(IDataSet requestData, IOnlineContext onlineCtx) {
		IDataSet responseData = new DataSet();

		DEPEqpInMgmt du = (DEPEqpInMgmt) lookupDataUnit(DEPEqpInMgmt.class);
		/// logger
		Log logger = LogManager.getFwkLog(); 
		Map mMgmt = null;
		Map mProdDtl = null;
		IRecordSet rsProdDtl = null;
		IRecordSet ts = null;
		logger.debug("\n\n >>>>>>>>>>> requestData :   "+requestData+"\n\n");
		logger.debug("\n\n\n");
//		logger.debug(">>>>>>>  ts ASSET_NO : "+requestData.getField("ASSET_NO")  );
//		ASSET_NO | EQP_MDL_CD | EQP_SER_NO | EQP_COLOR_CD
//		requestData.putField("ASSET_NO", requestData.getField("ASSET_NO") );
//		requestData.putField("EQP_MDL_CD", requestData.getField("EQP_MDL_CD") );
		
		responseData = du.dSAssetInfo(requestData, onlineCtx);
		ts = responseData.getRecordSet("RS_ASSET_INFO");
		mMgmt =ts.getRecordMap(0);
		//자산정보 셀렉트
		//
		logger.debug("\n\n\n");
		
		try {
			//DU lookup
			//        
			responseData = du.dSInqEqpProdDtlList(requestData, onlineCtx);
			rsProdDtl = responseData.getRecordSet("RS_EQP_PROD_DTL_LIST");
			logger.debug("\n\n\n >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			logger.debug("rsProdDtl.getRecordCount() :"+rsProdDtl.getRecordCount());
			logger.debug("rsProdDtl :"+rsProdDtl);

			logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> \n\n\n");
			for (int j = 0; j < rsProdDtl.getRecordCount(); j++) {
				mProdDtl = rsProdDtl.getRecordMap(j);
				//				logger.debug("상품상세 등록맵 mProdDtl:"+mProdDtl);
				//상담관리 값 인풋
				mProdDtl.put("CNSL_MGMT_NO", requestData.getField("ASSET_NO"));

				//				logger.debug("mProdDtl.get(OUTS_MCN_CL):"+mProdDtl.get("OUTS_MCN_CL"));
				//				logger.debug("BATTERY :"+mMgmt.get("BATTERY"));
				//				logger.debug("BATTERY int:"+Integer.parseInt(mMgmt.get("BATTERY").toString()) );
				//OUTS_MCN_CL  주변기기구분   (DTL_CL : ASIS)
				// 널처리 
				mMgmt.put("BATTERY"   , StringUtils.nvlObject(mMgmt.get("BATTERY"  ), "0"));
				mMgmt.put("CHARGER"   , StringUtils.nvlObject(mMgmt.get("CHARGER"  ), ""));
				mMgmt.put("ZENDER"    , StringUtils.nvlObject(mMgmt.get("ZENDER"   ), ""));
				mMgmt.put("EARPHONES" , StringUtils.nvlObject(mMgmt.get("EARPHONES"), ""));
				mMgmt.put("CASE"      , StringUtils.nvlObject(mMgmt.get("CASE"     ), ""));
				mMgmt.put("USB"       , StringUtils.nvlObject(mMgmt.get("USB"      ), ""));
				mMgmt.put("MEMORY"    , StringUtils.nvlObject(mMgmt.get("MEMORY"   ), ""));

				if ("1".equals(mProdDtl.get("OUTS_MCN_CL")) && Integer.parseInt(mMgmt.get("BATTERY").toString()) > 0 ) {
					mProdDtl.put("HLD_QTY", 1);
				}else if ("2".equals(mProdDtl.get("OUTS_MCN_CL")) && Integer.parseInt(mMgmt.get("BATTERY").toString()) > 1 ) {
						mProdDtl.put("HLD_QTY", Integer.parseInt(mMgmt.get("BATTERY").toString()) - 1);
				}else if ("4".equals(mProdDtl.get("OUTS_MCN_CL").toString()) && mMgmt.get("CHARGER").toString().endsWith("Y")) {
						mProdDtl.put("HLD_QTY", 1);
				}else if ("5".equals(mProdDtl.get("OUTS_MCN_CL").toString()) && mMgmt.get("ZENDER").toString().endsWith("Y") ) {
						mProdDtl.put("HLD_QTY", 1);
				}else if ("6".equals(mProdDtl.get("OUTS_MCN_CL").toString()) && mMgmt.get("EARPHONES").toString().endsWith("Y")) {
						mProdDtl.put("HLD_QTY", 1);
				}else if ("7".equals(mProdDtl.get("OUTS_MCN_CL").toString()) && mMgmt.get("CASE").toString().endsWith("Y")) {
						mProdDtl.put("HLD_QTY", 1);
				}else if ("8".equals(mProdDtl.get("OUTS_MCN_CL").toString()) && mMgmt.get("USB").toString().endsWith("Y")) {
						mProdDtl.put("HLD_QTY", 1);
				}else if ("9".equals(mProdDtl.get("OUTS_MCN_CL").toString()) && mMgmt.get("MEMORY").toString().endsWith("Y")) {
						mProdDtl.put("HLD_QTY", 1);
				}
				
				mProdDtl.put("USERID", requestData.getField("FS_REG_USER_ID"));
				logger.debug("\n mProdDtl:"+mProdDtl);
				requestData.putFieldMap(mProdDtl);
				//TB_EP_CST_CONSULT_DTL  상담관리 상세 테이블 등록
				du.dICnslMgmtDtl(requestData, onlineCtx);

			}
			
			//CNSL_MGMT_NO 상담관리번호
			mMgmt.put("CNSL_MGMT_NO", requestData.getField("ASSET_NO"));
			mMgmt.put("USERID", requestData.getField("FS_REG_USER_ID"));
			mMgmt.put("USE_PRD_DDCT_AMT", "0");
			mMgmt.put("CNSL_AMT", "0");
			mMgmt.put("ETC_CMPT", "");
			
			mMgmt.put("CTZ_NO"   , StringUtils.nvlObject(mMgmt.get("CTZ_NO"  ), ""));
			mMgmt.put("DPSTR"   , StringUtils.nvlObject(mMgmt.get("DPSTR"  ), ""));
			mMgmt.put("ACCO_NO"    , StringUtils.nvlObject(mMgmt.get("ACCO_NO"   ), ""));
			mMgmt.put("ADDR" , StringUtils.nvlObject(mMgmt.get("ADDR"), ""));
			mMgmt.put("ETC_ADDR"      , StringUtils.nvlObject(mMgmt.get("ETC_ADDR"     ), ""));
			mMgmt.put("SVC_NO"       , StringUtils.nvlObject(mMgmt.get("SVC_NO"      ), ""));
			mMgmt.put("TEL_NO"    , StringUtils.nvlObject(mMgmt.get("TEL_NO"   ), ""));
			
			
			if("PR".equals(requestData.getField("PRC_GB"))){
				mMgmt.put("CNSL_DEALCO_CD"    , "1000000013");
			}else if("NR".equals(requestData.getField("PRC_GB"))){
				mMgmt.put("CNSL_DEALCO_CD"    , "1000000014");
			}
			
			logger.debug("\n\n\n\n >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  상담관리 등록\n mMgmt :"+mMgmt);
			//상담관리 TB_EP_CST_CONSULT_MGMT 등록
			requestData.putFieldMap(mMgmt);
			du.dICnslMgmt(requestData, onlineCtx);
//			int cnt =  dbInsert("DEPEqpInMgmt.ICnslMgmt", mMgmt, onlineCtx);
//			logger.debug(">>>>>>>>>>>>> cnt "+cnt);
			

		} catch ( BizRuntimeException e ) {
            throw e;
        }
	   
	    return responseData;
	}
  
}
