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
import dms.constants.enums.sapjco.elem.SAP_TAX_CD;
import dms.constants.enums.sapjco.elem.SAP_WBS_ELEM;
import dms.utils.sapjco.domain.CommonSlipHeader;
import fwk.erfif.sapjco.domain.CommonSlipItem;



/**
 * 단말기 대금정산
 * @author greatjin
 *
 */
public class AgencyAmtAPNRSlip implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CommonSlipHeader header    ;
	CommonSlipItem[] items    = new CommonSlipItem[2] ;
	
	private String custCd; //Customer Code for init
	private String taxAmt;
	private String payAltCd; //지급대체
	
    private SAP_SLIP_KINDS slipKind       = SAP_SLIP_KINDS.AGENCY_AMT_AP_NR; 
    private SAP_SLIP_KINDS cancelSlipKind = SAP_SLIP_KINDS.AGENCY_AMT_AP_CANCEL_NR;
    
    private String dmsType;
    private String functionName ;
    private String slipType ;
    private boolean cancelSlipFlag;

	public AgencyAmtAPNRSlip(String zserial, String userNo, String slipDt, String custCd, String netAmt, String taxAmt, String payAltCd, String headerTxt, String itmTxt)
	{
		this.init(zserial, userNo,  slipDt, custCd, netAmt, taxAmt, payAltCd, headerTxt, itmTxt, false);
	}
	
	   public AgencyAmtAPNRSlip(String zserial, String userNo, String slipDt, String custCd, String netAmt, String taxAmt, String payAltCd, String headerTxt, String itmTxt, boolean cancelFlag)
	    {
	        this.init(zserial, userNo,  slipDt, custCd, netAmt, taxAmt, payAltCd, headerTxt, itmTxt, cancelFlag);
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
	private void init(String zserial, String userNo, String slipDt, String custCd, String netAmt, String taxAmt, String payAltCd, String headerTxt, String itmTxt, boolean cancelSlipFlag)
	{
        this.cancelSlipFlag = cancelSlipFlag;
        
        if(cancelSlipFlag) this.slipKind = this.cancelSlipKind;
        
	    this.initCommon(zserial, userNo);
		header.setHdrTxt(headerTxt+(cancelSlipFlag?"취소":""));
		
		this.custCd   = custCd;
		this.taxAmt   = taxAmt;
		this.payAltCd = payAltCd;
		
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
			items[i].setTaxCd(SAP_TAX_CD.INTAX10_TAXBILL.getCode());
			items[i].setAmt(netAmt);
			items[i].setTxt(itmTxt+(cancelSlipFlag?"취소":""));	
		}
		
		initDr(items[0],header);
		initCr(items[1],header);
	}
	
	/**
	 * 차변 초기화
	 * @param one
	 */
	private void initDr(CommonSlipItem one, CommonSlipHeader header)
	{
       if(cancelSlipFlag)
        {
            one.setPstngKey(SAP_PSTNG_KEY.M_DR.getCode());
        }
        else
        {
            one.setPstngKey(SAP_PSTNG_KEY.DR.getCode());
        }
		one.setDealCoCd(custCd);
		one.setPayCond(SAP_PAY_COND.KRW_1ST.getCode());
		long netAmt = Long.parseLong(one.getAmt());
		long taxAmt = Long.parseLong(this.taxAmt);
		one.setAmt(netAmt+taxAmt +"");
		one.setTaxAmt(taxAmt +"");
		one.setPayAlt(this.payAltCd);
	}
	
	/**
	 * 대변초기화
	 * @param one
	 */
	private void initCr(CommonSlipItem one, CommonSlipHeader header)
	{
       if(cancelSlipFlag)
        {
            one.setPstngKey(SAP_PSTNG_KEY.M_CR.getCode());
        }
        else
        {
            one.setPstngKey(SAP_PSTNG_KEY.CR.getCode());
        }
		one.setDealCoCd(SAP_DEAL_CO_CD.UNCLT_KRW_DEV.getCode());
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

	public String getCustCd() {
		return custCd;
	}

	public void setCustCd(String custCd) {
		this.custCd = custCd;
	}

	public String getFunctionName() {
		return functionName;
	}
	
	

}