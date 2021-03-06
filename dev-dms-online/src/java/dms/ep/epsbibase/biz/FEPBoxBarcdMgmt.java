package dms.ep.epsbibase.biz;

import java.util.Map;

import org.apache.commons.logging.Log;

import fwk.common.CommonArea;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.IResultMessage;
import nexcore.framework.core.data.ResultMessage;
import nexcore.framework.core.exception.BizRuntimeException;
import nexcore.framework.core.log.LogManager;


/**
 * <ul>
 * <li>업무 그룹명 : dms/에코폰</li>
 * <li>단위업무명: [FU]박스바코드관리</li>
 * <li>설  명 : </li>
 * <li>작성일 : 2015-09-02 11:01:14</li>
 * <li>작성자 : 이하나 (hana526)</li>
 * </ul>
 *
 * @author 이하나 (hana526)
 */
public class FEPBoxBarcdMgmt extends fwk.base.FunctionUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public FEPBoxBarcdMgmt(){
		super();
	}

	/**
	 *
	 *
	 * @author 이하나 (hana526)
	 * @since 2015-09-02 11:01:14
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_BOXDTL_LST
	 *		- field : BOX_BAR_CD [박스바코드]
	 *		- field : MGMT_BAR_CD [관리바코드]
	 *		- field : S_GB [중고폰업무구분]
	 *		- field : FS_REG_USER_ID [최초등록자ID]
	 *		- field : FS_REG_DTM [최초등록일시]
	 *		- field : LS_CHG_USER_ID [최종수정자ID]
	 *		- field : LS_CHG_DTM [최종수정일시]
	 *	- field : BOX_NO [박스바코드번호]
	 *	- field : USER_NO [사용자ID]
	 *	- field : FS_REG_USER_ID [최초등록자ID]
	 *	- field : LS_CHG_USER_ID [최종수정자ID]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fBoxBarcdConsultInfo(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
    	
        try {
            // 1. DU lookup
            DEPBoxBarcdMgmt dEPBoxBarcdMgmt = (DEPBoxBarcdMgmt) lookupDataUnit(DEPBoxBarcdMgmt.class);
            // 2. LISTDM호출
            dEPBoxBarcdMgmt.dUBoxBarcdConsultInfo(requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        }
	
	    return responseData;
	}

	/**
	 * <pre>[FM]박스바코드매입정보수정</pre>
	 *
	 * @author 이하나 (hana526)
	 * @since 2015-09-02 11:01:14
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * <pre>
	 *	- record : RS_BOXDTL_LST
	 *		- field : BOX_BAR_CD [박스바코드]
	 *		- field : MGMT_BAR_CD [관리바코드]
	 *		- field : S_GB [중고폰업무구분]
	 *		- field : FS_REG_USER_ID [최초등록자ID]
	 *		- field : FS_REG_DTM [최초등록일시]
	 *		- field : LS_CHG_USER_ID [최종수정자ID]
	 *		- field : LS_CHG_DTM [최종수정일시]
	 *	- field : BOX_NO [박스바코드번호]
	 *	- field : USER_NO [사용자ID]
	 *	- field : FS_REG_USER_ID [최초등록자ID]
	 *	- field : LS_CHG_USER_ID [최종수정자ID]
	 * </pre>
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fBoxBarcdPrchsInfo(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        
        try {
            // 1. DU lookup
            DEPBoxBarcdMgmt dEPBoxBarcdMgmt = (DEPBoxBarcdMgmt) lookupDataUnit(DEPBoxBarcdMgmt.class);
            // 2. LISTDM호출
            dEPBoxBarcdMgmt.dUBoxBarcdPrchsInfo(requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        }
	
	    return responseData;
	}

    /**
	 *
	 *
	 * @author 정동현 (jjddhh123)
	 * @since 2016-02-17 10:01:18
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet fBoxBarcdFpaEqpInfo(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        
        try {
            // 1. DU lookup
            DEPBoxBarcdMgmt dEPBoxBarcdMgmt = (DEPBoxBarcdMgmt) lookupDataUnit(DEPBoxBarcdMgmt.class);
            // 2. LISTDM호출
            dEPBoxBarcdMgmt.dUBoxBarcdFpaEqpInfo(requestData, onlineCtx);
        } catch ( BizRuntimeException e ) {
            throw e;
        }
    
        return responseData;
    }
  
}
