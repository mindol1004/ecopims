/**
 * 감가상각전표
 */
package dms.utils.sapjco.domain.nr;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import dms.constants.enums.sapjco.elem.SAP_BIZ_AREA;
import dms.constants.enums.sapjco.elem.SAP_BP;
import dms.constants.enums.sapjco.elem.SAP_DEAL_CO_CD;
import dms.constants.enums.sapjco.elem.SAP_PSTNG_KEY;
import dms.constants.enums.sapjco.elem.SAP_SLIP_KINDS;
import dms.constants.enums.sapjco.elem.SAP_WBS_ELEM;
import dms.utils.SAPUtils;
import dms.utils.sapjco.domain.CommonSlipHeader;
import fwk.erfif.sapjco.domain.CommonSlipItem;


/**
 * @author greatjin
 *
 */
public class AssetDepreciationNRSlip implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CommonSlipHeader header    ;
	CommonSlipItem[] items    = new CommonSlipItem[4] ;
	
	private String deptCd; //Customer Code for init
	
	private SAP_SLIP_KINDS slipKind       = SAP_SLIP_KINDS.ASSET_DEPRECIATION_NR; 
    private SAP_SLIP_KINDS cancelSlipKind = SAP_SLIP_KINDS.ASSET_DEPRECIATION_CANCEL_NR;
    
    private String dmsType;
    private String functionName ;
    private String slipType ;
    private boolean cancelSlipFlag;
	

	
	public AssetDepreciationNRSlip(String zserial, String userNo, String slipDt, String deptCd, String amt, String etcAmt, String refField)
	{
		this.init(zserial, userNo, slipDt, deptCd, amt, etcAmt,refField, false);
	}
	
    public AssetDepreciationNRSlip(String zserial, String userNo, String slipDt, String deptCd, String amt, String etcAmt, String refField, boolean cancelFlag)
    {
        this.init(zserial, userNo, slipDt, deptCd, amt, etcAmt,refField,true);
    }
	
	/**
	 * 공통초기화
	 * @param zserial
	 * @param userNo
	 */
	void initCommon(String zserial, String userNo)
	{
		dmsType      = slipKind.getDmsType();
		functionName = slipKind.getFuncName();
		slipType     = slipKind.getSlipType();
		
		header = new CommonSlipHeader();
		header.setSerNo(zserial);
		header.setDmsTyp(this.dmsType);
		header.setSlipTyp(this.slipType);
		header.setSlipTyp("AF");  //전표유형
		header.setTransCd("FBV1");
		header.setUserNo(userNo);
	}
	
	/**
	 * 초기화
	 * @param amt
	 */
	private void init(String zserial, String userNo, String slipDt, String deptCd, String amt, String etcAmt, String refField, boolean cancelSlipFlag)
	{
	    this.cancelSlipFlag = cancelSlipFlag;
	       
	    if(cancelSlipFlag) this.slipKind = this.cancelSlipKind;
	        
		this.initCommon(zserial, userNo);
		
		header.setHdrTxt((cancelSlipFlag?"취소":""));
		
		this.deptCd = deptCd;
		
		if(StringUtils.isNotEmpty(slipDt)) header.setPstngDt(slipDt);
		if(StringUtils.isNotEmpty(slipDt)) header.setEvdcDt(slipDt);
		
		int idx = 1; 
		for(int i=0; i<items.length; i++)
		{
			items[i] = new CommonSlipItem();
			items[i].setSerNo(zserial);
			items[i].setDmsSeq(idx++ +"");
			items[i].setBp(SAP_BP.SKCC_HQ.getCode());			
			if(StringUtils.isNotEmpty(amt))   items[i].setAmt(amt);
			items[i].setTxt((cancelSlipFlag?"취소":""));
		}
		
		initItem0(items[0]);
		items[0].setDsignField(SAPUtils.nvl(refField,header.getPstngDt()));
		initItem1(items[1]);
		items[1].setAmt(etcAmt);
		initItem2(items[2]);
		items[2].setAmt(etcAmt);
		initItem3(items[3]);
	}
	
	/**
	 * 감가상각액 아이템 초기화
	 * @param one
	 */
	private void initItem0(CommonSlipItem one)
	{
       if(cancelSlipFlag)
        {
            one.setPstngKey(SAP_PSTNG_KEY.M_CR.getCode());          
        }
        else
        {
            one.setPstngKey(SAP_PSTNG_KEY.CR.getCode());
        }
       
		one.setDealCoCd(SAP_DEAL_CO_CD.ASSET_DEPRECIATION_AMT_NR.getCode());
		one.setWbsElem(SAP_WBS_ELEM.DEVICE_R_BIZ.getCode());
		one.setTxt("AFB01201500701-0000000184");
	}
	
	/**
	 * 재료비 아이템 초기화
	 * @param one
	 */
	private void initItem1(CommonSlipItem one)
	{
       if(cancelSlipFlag)
        {
            one.setPstngKey(SAP_PSTNG_KEY.M_CR.getCode());          
        }
        else
        {
            one.setPstngKey(SAP_PSTNG_KEY.CR.getCode());
        }
	       
		one.setDealCoCd(SAP_DEAL_CO_CD.PJT_MATERIAL.getCode());
		one.setWbsElem(SAP_WBS_ELEM.DEVICE_R_BIZ.getCode());
	}
	
	/**
	 * 충당부채 기타 초기화
	 * @param one
	 */
	private void initItem2(CommonSlipItem one)
	{
       if(cancelSlipFlag)
        {
            one.setPstngKey(SAP_PSTNG_KEY.CR.getCode());          
        }
        else
        {
            one.setPstngKey(SAP_PSTNG_KEY.M_CR.getCode());
        }
       
		one.setDealCoCd(SAP_DEAL_CO_CD.ALLWN_ETC.getCode());
		one.setBizArea(SAP_BIZ_AREA.DEV_HQ.getCode());
		one.setWbsElem(SAP_WBS_ELEM.DEVICE_R_BIZ.getCode());
		
	}
	
	/**
	 * 누계액 초기화 (발생액임)
	 * @param one
	 */
	private void initItem3(CommonSlipItem one)
	{
       if(cancelSlipFlag)
        {
            one.setPstngKey(SAP_PSTNG_KEY.CR.getCode());          
        }
        else
        {
            one.setPstngKey(SAP_PSTNG_KEY.M_CR.getCode());
        }
       
		one.setDealCoCd(SAP_DEAL_CO_CD.ASSET_DEPRECIATION_SUM_AMT_NR.getCode());
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
