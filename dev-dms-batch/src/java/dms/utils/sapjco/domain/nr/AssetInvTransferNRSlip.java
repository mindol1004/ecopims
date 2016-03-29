/**
 * 
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
import dms.utils.sapjco.domain.CommonSlipHeader;
import fwk.erfif.sapjco.domain.CommonSlipItem;


/**
 * 자산전환
 * @author greatjin
 *
 */
public class AssetInvTransferNRSlip implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CommonSlipHeader header    ;
	CommonSlipItem[] items    = new CommonSlipItem[4] ;
	
	private String custCd; //Customer Code for init
	
	private SAP_SLIP_KINDS slipKind      =  SAP_SLIP_KINDS.ASSET_INV_TRANSFER_NR; 
	private SAP_SLIP_KINDS cancelSlipKind = SAP_SLIP_KINDS.ASSET_INV_TRANSFER_NR;
	
	private String dmsType;
	private String functionName ;
	private String slipType ;
    private boolean cancelSlipFlag;
	
    /**
     * Constructor
     */
	public AssetInvTransferNRSlip(String zserial, String userNo, String slipDt, String custCd, String amt, String deprAmt, String preAmt, String remAmt, String headerTxt, String itemTxt)
	{
		this.init(zserial, userNo, slipDt, custCd, amt, deprAmt, preAmt, remAmt, headerTxt, itemTxt, false);
	}
	
    /**
     * Constructor
     */
    public AssetInvTransferNRSlip(String zserial, String userNo, String slipDt, String custCd, String amt, String deprAmt, String preAmt, String remAmt, String headerTxt, String itemTxt, boolean cancelFlag)
    {
        this.init(zserial, userNo, slipDt, custCd, amt, deprAmt, preAmt, remAmt, headerTxt, itemTxt, cancelFlag);
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
	private void init(String zserial, String userNo, String slipDt, String custCd, String amt, String deprAmt, String preAmt, String remAmt, String headerTxt, String itemTxt, boolean cancelSlipFlag)
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
			items[i].setBp(SAP_BP.SKCC_HQ.getCode());			
			items[i].setDsignField(SAP_WBS_ELEM.DEVICE_R_BIZ.getCode());
			if(StringUtils.isNotEmpty(amt))   items[i].setAmt(amt);
			items[i].setTxt(itemTxt);
		}
		
		initDr1(items[0]);
		items[0].setAmt(remAmt);
        initDr2(items[1]);
        items[1].setAmt(deprAmt);
        initDr3(items[2]);
        items[2].setAmt(preAmt);
		initCr (items[3]);
		items[3].setAmt(amt);
	}
	
	/**
	 * 차변 초기화 잔존가 상품신규
	 * @param one
	 */
	private void initDr1(CommonSlipItem one)
	{
		
       if(cancelSlipFlag)
        {
            one.setPstngKey(SAP_PSTNG_KEY.M_DR.getCode());
        }
        else
        {
            one.setPstngKey(SAP_PSTNG_KEY.CR.getCode());
        }
        one.setDealCoCd(SAP_DEAL_CO_CD.GOODS_NR.getCode());
        one.setBizArea(SAP_BIZ_AREA.DEV_HQ.getCode());
        one.setWbsElem(SAP_WBS_ELEM.DEVICE_R_BIZ.getCode());
	} 
	
	   /**
     * 차변 초기화 신규감누액
     * @param one
     */
    private void initDr2(CommonSlipItem one)
    {
        
       if(cancelSlipFlag)
        {
            one.setPstngKey(SAP_PSTNG_KEY.M_DR.getCode());
        }
        else
        {
            one.setPstngKey(SAP_PSTNG_KEY.CR.getCode());
        }
        one.setDealCoCd(SAP_DEAL_CO_CD.ASSET_DEPRECIATION_SUM_AMT_NR.getCode());
    } 
    
    /**
     * 차변 초기화 충당부채기타
     * @param one
     */
    private void initDr3(CommonSlipItem one)
    {
        
       if(cancelSlipFlag)
        {
            one.setPstngKey(SAP_PSTNG_KEY.M_DR.getCode());
        }
        else
        {
            one.setPstngKey(SAP_PSTNG_KEY.CR.getCode());
        }
        one.setDealCoCd(SAP_DEAL_CO_CD.ALLWN_ETC.getCode());
        one.setBizArea(SAP_BIZ_AREA.DEV_HQ.getCode());
        one.setWbsElem(SAP_WBS_ELEM.DEVICE_R_BIZ.getCode());
    } 
	
	/**
	 * 대변초기화 - 매입가
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
            one.setPstngKey(SAP_PSTNG_KEY.RB_C.getCode());
        }
		one.setDealCoCd(SAP_DEAL_CO_CD.LEASE_ASSET_NR.getCode());
        one.setBizArea(SAP_BIZ_AREA.DEV_HQ.getCode());
        one.setWbsElem(SAP_WBS_ELEM.DEVICE_R_BIZ.getCode());
	}
    

	public CommonSlipHeader getHeader() {
		return header;
	}

	public CommonSlipItem[] getItems() {
		return items;
	}

	public String getFunctionName() {
		return functionName;
	}
	
	

}
