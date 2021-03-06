/**
 * 매출원가 국내
 */
package dms.utils.sapjco.domain.ep;

import java.io.Serializable;

import nexcore.framework.core.util.DateUtils;

import org.apache.commons.lang.StringUtils;

import dms.constants.enums.sapjco.elem.SAP_BIZ_AREA;
import dms.constants.enums.sapjco.elem.SAP_BP;
import dms.constants.enums.sapjco.elem.SAP_DEAL_CO_CD;
import dms.constants.enums.sapjco.elem.SAP_PSTNG_KEY;
import dms.constants.enums.sapjco.elem.SAP_SLIP_KINDS;
import dms.constants.enums.sapjco.elem.SAP_WBS_ELEM;
import dms.utils.sapjco.domain.CommonSlipHeader;
import fwk.constants.enums.DMS_MGMT_DEPT;
import fwk.erfif.sapjco.domain.CommonSlipItem;



/**
 * @author greatjin
 *
 */
public class IncostEPSlip implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CommonSlipHeader header    ;
	CommonSlipItem[] items    = new CommonSlipItem[2] ;
	
	
	private SAP_SLIP_KINDS slipKind =SAP_SLIP_KINDS.INCOST_EP; 
	private String dmsType      ;
	private String functionName ;
	private String slipType     ;
	private boolean flagB2C     =false;

	
	public IncostEPSlip( SAP_SLIP_KINDS slipKind, String zserial, String userNo, String slipDt, String evdcDt, String amt, String headerTxt,boolean flagB2C)
	{
		this.init(slipKind, zserial, userNo, slipDt, evdcDt, amt, headerTxt, flagB2C);
	}
	
	/**
	 * 공통초기화
	 * @param zserial
	 * @param userNo
	 */
	void initCommon(String zserial, String userNo)
	{
		dmsType = slipKind.getDmsType();
		functionName = slipKind.getFuncName();
		slipType = slipKind.getSlipType();
		
		header = new CommonSlipHeader();
		header.setSerNo(zserial);
		header.setDmsTyp(this.dmsType);
		header.setSlipTyp(this.slipType);
		header.setTransCd("FBV1");
		header.setUserNo(userNo);
	}
	
	/**
	 * 초기화
	 * @param amt
	 */
	private void init( SAP_SLIP_KINDS slipKind, String zserial, String userNo, String slipDt, String evdcDt, String amt, String headerTxt, boolean flagB2C)
	{
		this.slipKind = slipKind;
		this.initCommon(zserial, userNo);
		
		
		
		if(StringUtils.isNotEmpty(slipDt)) header.setPstngDt(slipDt);
		if(StringUtils.isNotEmpty(evdcDt))
        {
           header.setEvdcDt(evdcDt);
        }
		
		String txt =  "매출원가인식/"+headerTxt+(slipKind.equals(SAP_SLIP_KINDS.INCOST_EP)&&flagB2C?"(B2C판매)":"");
		
		header.setHdrTxt(txt);
		
		int idx = 1; 
		for(int i=0; i<items.length; i++)
		{
			items[i] = new CommonSlipItem();
			items[i].setSerNo(zserial);
			items[i].setDmsSeq(idx++ +"");
			items[i].setBp(SAP_BP.SKCC_HQ.getCode());
	        if(slipKind.equals(SAP_SLIP_KINDS.INCOST_EP))
	        {
	            items[i].setDsignField(SAP_WBS_ELEM.ECO_R_BIZ_SELL.getCode());
	        }
	        else
	        {
	            items[i].setDsignField(SAP_WBS_ELEM.ECO_R_BIZ.getCode());
	        }
			
			items[i].setAmt(amt);
			items[i].setTxt(txt);
		}
		
		initItem0(items[0]);
		initItem1(items[1]);
	}
	
	/**
	 * 거래처
	 * @param one
	 */
	private void initItem0(CommonSlipItem one)
	{
		one.setPstngKey(SAP_PSTNG_KEY.CR.getCode());
		one.setDealCoCd(SAP_DEAL_CO_CD.PJT_MATERIAL.getCode());
        if(slipKind.equals(SAP_SLIP_KINDS.INCOST_EP))
	    {
            one.setWbsElem(SAP_WBS_ELEM.ECO_R_BIZ_SELL.getCode());
        }
        else
        {
            one.setWbsElem(SAP_WBS_ELEM.ECO_R_BIZ.getCode());
        }
	}
	
	/**
	 *상품매출-국내매출
	 * @param one
	 */
	private void initItem1(CommonSlipItem one)
	{
		one.setPstngKey(SAP_PSTNG_KEY.RB_C.getCode());
		one.setDealCoCd(SAP_DEAL_CO_CD.GOODS.getCode());
		one.setBizArea(SAP_BIZ_AREA.DEV_HQ.getCode());
		if(slipKind.equals(SAP_SLIP_KINDS.INCOST_EP))
		{
		    one.setCostCntr(DMS_MGMT_DEPT.ECO_BUSINESS.getCode());
//			one.setWbsElem(SAP_WBS_ELEM.ECO_R_BIZ.getCode());
		}
		
	}

	
    public CommonSlipItem getDr()
    {
    	return this.items[0];
    }
	
    public CommonSlipItem getCr()
    {
    	return this.items[1];
    }
    
    

	public CommonSlipHeader getHeader() {
		return header;
	}

	public void setHeader(CommonSlipHeader header) {
		this.header = header;
	}

	public CommonSlipItem[] getItems() {
		return items;
	}

	public void setItems(CommonSlipItem[] items) {
		this.items = items;
	}


	public String getFunctionName() {
		return functionName;
	}
	
	

}
