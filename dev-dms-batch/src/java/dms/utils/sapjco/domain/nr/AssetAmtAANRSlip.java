/**
 * 
 */
package dms.utils.sapjco.domain.nr;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import dms.constants.enums.sapjco.elem.SAP_BIZ_AREA;
import dms.constants.enums.sapjco.elem.SAP_BP;
import dms.constants.enums.sapjco.elem.SAP_DEAL_CO_CD;
import dms.constants.enums.sapjco.elem.SAP_PAY_COND;
import dms.constants.enums.sapjco.elem.SAP_PSTNG_KEY;
import dms.constants.enums.sapjco.elem.SAP_SLIP_KINDS;
import dms.constants.enums.sapjco.elem.SAP_WBS_ELEM;
import dms.utils.sapjco.domain.CommonSlipHeader;
import fwk.erfif.sapjco.domain.CommonSlipItem;


/**
 * @author greatjin
 *
 */
public class AssetAmtAANRSlip implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CommonSlipHeader header    ;
	CommonSlipItem[] items    = new CommonSlipItem[2] ;
	
	private String custCd; //Customer Code for init
	
	private SAP_SLIP_KINDS slipKind      =  SAP_SLIP_KINDS.ASSET_AMT_AA_NR; 
	private SAP_SLIP_KINDS cancelSlipKind = SAP_SLIP_KINDS.ASSET_AMT_AA_CANCEL_NR;
	
	private String dmsType;
	private String functionName ;
	private String slipType ;
    private boolean cancelSlipFlag;
	
    /**
     * Constructor
     */
	public AssetAmtAANRSlip(String zserial, String userNo, String slipDt, String custCd, String amt, String headerTxt, String itemTxt)
	{
		this.init(zserial, userNo, slipDt, custCd, amt, headerTxt, itemTxt, false);
	}
	
    /**
     * Constructor
     */
    public AssetAmtAANRSlip(String zserial, String userNo, String slipDt, String custCd, String amt, String headerTxt, String itemTxt, boolean cancelFlag)
    {
        this.init(zserial, userNo, slipDt, custCd, amt, headerTxt, itemTxt, cancelFlag);
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
	private void init(String zserial, String userNo, String slipDt, String custCd, String amt, String headerTxt, String itemTxt, boolean cancelSlipFlag)
	{
	    this.cancelSlipFlag = cancelSlipFlag;
        
        if(cancelSlipFlag) this.slipKind = this.cancelSlipKind;
        
        this.initCommon(zserial, userNo);
        header.setHdrTxt(headerTxt);
		
		this.custCd = custCd;
		
		if(StringUtils.isNotEmpty(slipDt)) header.setPstngDt(slipDt);
		if(StringUtils.isNotEmpty(slipDt)) header.setEvdcDt(slipDt);
		
		int idx = 1; 
		for(int i=0; i<items.length; i++)
		{
			items[i] = new CommonSlipItem();
			items[i].setSerNo(zserial);
			items[i].setDmsSeq(idx++ +"");
			items[i].setBizArea(SAP_BIZ_AREA.DEV_HQ.getCode());
			items[i].setBp(SAP_BP.SKCC_HQ.getCode());			
			items[i].setDsignField(SAP_WBS_ELEM.DEVICE_R_BIZ.getCode());
			if(StringUtils.isNotEmpty(amt))   items[i].setAmt(amt);
			items[i].setTxt(itemTxt);
		}
		
		initDr(items[0]);
		initCr(items[1]);
	}
	
	/**
	 * 차변 초기화
	 * @param one
	 */
	private void initDr(CommonSlipItem one)
	{
		
       if(cancelSlipFlag)
        {
            one.setPstngKey(SAP_PSTNG_KEY.M_DR.getCode());
        }
        else
        {
            one.setPstngKey(SAP_PSTNG_KEY.DR.getCode());
        }
		
		if(StringUtils.isNotEmpty(this.custCd))
		{
			one.setDealCoCd(custCd);
		}
		else
		{
			one.setDealCoCd("1111111103");
		}
		one.setPayCond(SAP_PAY_COND.KRW_1ST.getCode());
	} 
	
	/**
	 * 대변초기화
	 * @param one
	 */
	private void initCr(CommonSlipItem one)
	{
       if(cancelSlipFlag)
        {
            one.setPstngKey(SAP_PSTNG_KEY.M_CR.getCode());          
        }
        else
        {
            one.setPstngKey(SAP_PSTNG_KEY.CR.getCode());
        }
		one.setDealCoCd(SAP_DEAL_CO_CD.LEASE_ASSET_NR.getCode());
		one.setWbsElem(SAP_WBS_ELEM.DEVICE_R_BIZ.getCode());
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