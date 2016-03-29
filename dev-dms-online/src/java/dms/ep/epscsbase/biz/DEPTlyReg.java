package dms.ep.epscsbase.biz;

import java.util.Map;

import org.apache.commons.logging.Log;

import fwk.common.CommonArea;
import fwk.utils.HpcUtils;
import nexcore.framework.core.data.DataSet;
import nexcore.framework.core.data.IDataSet;
import nexcore.framework.core.data.IOnlineContext;
import nexcore.framework.core.data.IRecord;
import nexcore.framework.core.data.IRecordSet;
import nexcore.framework.core.data.IResultMessage;
import nexcore.framework.core.data.ResultMessage;
import nexcore.framework.core.exception.BizRuntimeException;
import nexcore.framework.core.log.LogManager;
import nexcore.framework.core.util.StringUtils;


/**
 * <ul>
 * <li>업무 그룹명 : dms/에코폰</li>
 * <li>단위업무명: [DU]검수등록</li>
 * <li>설  명 : <pre>[DU]검수등록</pre></li>
 * <li>작성일 : 2015-09-01 13:57:33</li>
 * <li>작성자 : 김윤환 (kyh0904)</li>
 * </ul>
 *
 * @author 김윤환 (kyh0904)
 */
public class DEPTlyReg extends fwk.base.DataUnit {

	/**
	 * 이 클래스는 Singleton 객체로 수행됩니다. 
	 * 여기에 필드를 선언하여 사용하면 동시성 문제를 일으킬 수 있습니다.
	 */

	/**
	 * Default Constructor
	 */
	public DEPTlyReg(){
		super();
	}

    /**
	 * <pre>[DM]일반감정검수등록목록조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:33
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSGnrlJdgTlyRegLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SGnrlJdgTlyRegLst", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_TLY_REG_LIST", rsReturn);
        return responseData;
    }

    /**
	 * <pre>[DM]일반감정검수등록구성품목록조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:33
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSGnrlJdgTlyRegCmptLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SGnrlJdgTlyRegCmptLst", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_TLY_REG_CMPT_LIST", rsReturn);
        return responseData;
    }

    /**
	 * <pre>[DM]매입일반감정검수등록목록조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:33
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSPrchGnrlJdgTlyRegLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SPrchGnrlJdgTlyRegLst", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_TLY_REG_LIST", rsReturn);
        return responseData;
    }

    /**
	 * <pre>[DM]매입일반감정검수등록구성품목록조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:33
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSPrchGnrlJdgTlyRegCmptLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SPrchGnrlJdgTlyRegCmptLst", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_TLY_REG_CMPT_LIST", rsReturn);
        return responseData;
    }

    /**
	 * <pre>[DM]단말기모델정보조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-03 10:38:56
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSEqpMdlInfo(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SEqpMdlInfo", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_EQP_INFO_LIST", rsReturn);
        return responseData;
    }

    /**
	 * <pre>[DM]단말기등급정보조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-03 10:39:12
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSEqpMdlGradeInfo(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SEqpMdlGradeInfo", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_EQP_GRADE_LIST", rsReturn);
        return responseData;
    }

    /**
	 * <pre>[DM]일반검수진행매입상세정보등록</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-08 08:57:13
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dIGnrlTlyProgrTbEpCstPrchsDtl(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        dbInsert("IGnrlTlyProgrTbEpCstPrchsDtl", requestData, onlineCtx);
        return responseData;
    }

    /**
	 * <pre>[DM]일반검수진행매입관리정보등록</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-08 08:57:29
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dIGnrlTlyProgrTbEpCstPrchsMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        dbInsert("IGnrlTlyProgrTbEpCstPrchsMgmt", requestData, onlineCtx);
        return responseData;
    }

    /**
	 * <pre>[DM]일반검수진행매입상세정보삭제</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-08 08:57:42
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dDGnrlTlyProgrTbEpCstPrchsDtl(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        dbDelete("DGnrlTlyProgrTbEpCstPrchsDtl", requestData, onlineCtx);
        return responseData;
    }

    /**
	 * <pre>[DM]일반검수완료대상상담관리정보수정</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-08 08:57:56
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUGnrlTlyFnshTbEpCstConsultMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        dbUpdate("UGnrlTlyFnshTbEpCstConsultMgmt", requestData, onlineCtx);
        return responseData;
    }

    /**
	 * <pre>[DM]일반검수완료대상매입관리정보수정</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-08 08:58:10
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUGnrlTlyFnshTbEpCstPrchsMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        dbUpdate("UGnrlTlyFnshTbEpCstPrchsMgmt", requestData, onlineCtx);
        return responseData;
    }

    /**
	 * <pre>[DM]일반검수진행상담상태수정</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-08 08:58:24
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUGnrlTlyProgrConsultSt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        dbUpdate("UGnrlTlyProgrConsultSt", requestData, onlineCtx);
        return responseData;
    }

    /**
	 * <pre>[DM]일반검수진행상담관리정보수정</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-08 08:58:38
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUGnrlTlyProgrTbEpCstConsultMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        dbUpdate("UGnrlTlyProgrTbEpCstConsultMgmt", requestData, onlineCtx);
        return responseData;
    }

    /**
	 * <pre>[DM]일반검수진행대상매입관리정보수정</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-08 08:58:50
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dUGnrlTlyProgrTbEpCstPrchsMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        dbUpdate("UGnrlTlyProgrTbEpCstPrchsMgmt", requestData, onlineCtx);
        return responseData;
    }

    /**
	 * <pre>[DM]매입관리번호조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-08 08:59:05
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSPrchMgmtNoSeq(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
	    
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SPrchMgmtNoSeq", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putFieldMap(record); 
    
        return responseData;
    }

    /**
	 * <pre>[DM]일반검수진행대상상담수정정보조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-08 10:40:49
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSGnrlTlyProgrConsultCount(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("SGnrlTlyProgrConsultCount", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putFieldMap(record); 
    
        return responseData;
    }

    /**
	 * <pre>[DM]매입관리건수조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-09 13:46:21
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSTbEpCstPrchsMgmtCount(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        
        // 1.쿼리문 호출     
        IRecord record = dbSelectSingle("STbEpCstPrchsMgmtCount", requestData, onlineCtx);
        // 2.결과값 셋팅     
        responseData.putFieldMap(record); 
    
        return responseData;
    }

    /**
	 * <pre>[DM]재감정검수등록목록조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-01 13:57:33
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSRpetJdgTlyRegLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SRpetJdgTlyRegLst", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_TLY_REG_LIST", rsReturn);
        return responseData;
    }

    /**
	 * <pre>[DM]재감정검수등록구성품목록조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-11 09:50:36
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSRpetJdgTlyRegCmptLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SRpetJdgTlyRegCmptLst", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_TLY_REG_CMPT_LIST", rsReturn);
        return responseData;
    }

    /**
	 * <pre>[DM]매입재감정검수등록목록조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-11 09:50:46
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSPrchRpetJdgTlyRegLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SPrchRpetJdgTlyRegLst", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_TLY_REG_LIST", rsReturn);
        return responseData;
    }

    /**
	 * <pre>[DM]매입재감정검수등록구성품목록조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-11 09:50:59
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSPrchRpetJdgTlyRegCmptLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SPrchRpetJdgTlyRegCmptLst", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_TLY_REG_CMPT_LIST", rsReturn);
        return responseData;
    }

    /**
	 * <pre>[DM]재검수진행매입관리정보등록</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-14 15:59:38
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dIRpetTlyProgrTbEpCstPrchsMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        dbInsert("IRpetTlyProgrTbEpCstPrchsMgmt", requestData, onlineCtx);
        return responseData;
    }

    /**
	 * <pre>[DM]재검수진행매입상세정보등록</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-14 15:59:48
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dIRpetTlyProgrTbEpCstPrchsDtl(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        dbInsert("IRpetTlyProgrTbEpCstPrchsDtl", requestData, onlineCtx);
        return responseData;
    }

    /**
	 * <pre>[DM]재검수대상상담관리정보수정</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-09-14 15:59:57
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dURpetTlyTbEpCstConsultMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        dbUpdate("URpetTlyTbEpCstConsultMgmt", requestData, onlineCtx);
        return responseData;
    }

    /**
	 * <pre>[DM]단말기등급변경차액정보조회</pre>
	 *
	 * @author 김윤환 (kyh0904)
	 * @since 2015-11-16 17:02:34
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSEqpMdlGradeChgDamtInfo(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("SEqpMdlGradeChgDamtInfo", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_EQP_GRADE_CHG_LIST", rsReturn);
        return responseData;
    }

    /**
	 *
	 *
	 * @author 정동현 (jjddhh123)
	 * @since 2016-01-04 10:33:23
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dSTlyEqpMdlColorLst(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        IRecordSet rsReturn = dbSelect("STlyEqpMdlColorLst", requestData, onlineCtx);
        // 2.결과값 셋팅
        responseData.putRecordSet("RS_EQP_COLOR_LIST", rsReturn);
        return responseData;
    }

	/**
	 * <pre>[DM]재검수진행고객매입정보수정</pre>
	 *
	 * @author 이하나 (hana526)
	 * @since 2015-09-01 13:57:33
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dURpetJdgTlyCustPrchsMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        dbUpdate("URpetJdgTlyCustPrchsMgmt", requestData, onlineCtx);
        return responseData;
	}

	/**
	 * <pre>[DM]재검수진행고객상담정보수정</pre>
	 *
	 * @author 이하나 (hana526)
	 * @since 2015-09-01 13:57:33
	 *
	 * @param requestData 요청정보 DataSet 객체
	 * @param onlineCtx   요청 컨텍스트 정보
	 * @return 처리결과 DataSet 객체
	 */
	public IDataSet dURpetJdgTlyCustConsultMgmt(IDataSet requestData, IOnlineContext onlineCtx) {
	    IDataSet responseData = new DataSet();
        // 1.쿼리문 호출
        dbUpdate("URpetJdgTlyCustConsultMgmt", requestData, onlineCtx);
        return responseData;
	}
  
}
