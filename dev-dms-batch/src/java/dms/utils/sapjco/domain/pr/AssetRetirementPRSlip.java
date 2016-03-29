/**
 * 자산제각전표(임대)
 */
package dms.utils.sapjco.domain.pr;

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
public class AssetRetirementPRSlip implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CommonSlipHeader header    ;
	CommonSlipItem[] items    = new CommonSlipItem[3] ;
	
	private String deptCd; //Customer Code for init
	
	private SAP_SLIP_KINDS slipKinds =SAP_SLIP_KINDS.ASSET_RETIRE_PR; 
	private String dmsType      ;
	private String functionName ;
	private String slipType     ;
	

	
	public AssetRetirementPRSlip(String zserial, String userNo, String slipDt, String deptCd, String amt, String prcAmt, String ldtaAmt)
	{
		this.init(zserial, userNo, slipDt, deptCd, amt, prcAmt, ldtaAmt);
	}
	
	/**
	 * 공통초기화
	 * @param zserial
	 * @param userNo
	 */
	void initCommon(String zserial, String userNo)
	{
		dmsType = slipKinds.getDmsType();
		functionName = slipKinds.getFuncName();
		slipType = slipKinds.getSlipType();
		
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
	private void init(String zserial, String userNo, String slipDt, String deptCd, String amt, String prcAmt, String ldtaAmt)
	{
		this.initCommon(zserial, userNo);
		
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
		}
		
		initItem0(items[0]);
		items[0].setAmt(prcAmt);
		initItem1(items[1]);
		items[1].setAmt(amt);
		initItem2(items[2]);
		items[2].setAmt(ldtaAmt);
	}
	
	/**
	 * 감가상각액 아이템 초기화
	 * @param one
	 */
	private void initItem0(CommonSlipItem one)
	{
		
		SAPUtils.debug("initItem0 one============================"+one);
		one.setPstngKey(SAP_PSTNG_KEY.CR.getCode());
		one.setDealCoCd(SAP_DEAL_CO_CD.ASSET_DEPRECIATION_SUM_AMT_PR.getCode());
	}
	
	/**
	 *유형자산처분손실
	 * @param one
	 */
	private void initItem1(CommonSlipItem one)
	{
		one.setPstngKey(SAP_PSTNG_KEY.CR.getCode());
		one.setDealCoCd(SAP_DEAL_CO_CD.LDTA.getCode());
		one.setBizArea(SAP_BIZ_AREA.DEV_HQ.getCode());
		one.setWbsElem(SAP_WBS_ELEM.ECO_R_BIZ.getCode());
	}
	
	/**
	 * 유형자산 취득계정
	 * @param one
	 */
	private void initItem2(CommonSlipItem one)
	{
		one.setPstngKey(SAP_PSTNG_KEY.RB_C.getCode());
		one.setDealCoCd(SAP_DEAL_CO_CD.LEASE_ASSET_PR.getCode());
		one.setWbsElem(SAP_WBS_ELEM.ECO_R_BIZ.getCode());
		
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
