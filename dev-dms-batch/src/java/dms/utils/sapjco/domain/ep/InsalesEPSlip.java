/**
 * 단말기 판매 국내
 */
package dms.utils.sapjco.domain.ep;

import java.io.Serializable;

import nexcore.framework.core.util.DateUtils;

import org.apache.commons.lang.StringUtils;

import dms.constants.enums.sapjco.elem.SAP_BIZ_AREA;
import dms.constants.enums.sapjco.elem.SAP_BP;
import dms.constants.enums.sapjco.elem.SAP_DEAL_CO_CD;
import dms.constants.enums.sapjco.elem.SAP_PAY_COND;
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
public class InsalesEPSlip implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CommonSlipHeader header    ;
	CommonSlipItem[] items    = new CommonSlipItem[2] ;
	
	private String custCd; //Customer Code for init
	
	private SAP_SLIP_KINDS slipKind =SAP_SLIP_KINDS.INSALES_EP; 
	private String dmsType      ;
	private String functionName ;
	private String slipType     ;
	private String reknDt       ;

	
	public InsalesEPSlip(String zserial, String userNo, String slipDt, String evdcDt, String custCd, String custNm, String taxCd, String amt, String taxAmt, boolean flagB2C)
	{
		this.init( zserial, userNo, slipDt, evdcDt,custCd,custNm, taxCd, amt, taxAmt, flagB2C);
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
	private void init(String zserial, String userNo, String slipDt, String evdcDt, String custCd, String custNm, String taxCd, String amt, String taxAmt,boolean flagB2C)
	{
		this.initCommon(zserial, userNo);
		
		this.custCd = custCd;
		
		if(StringUtils.isNotEmpty(slipDt)) header.setPstngDt(slipDt);
		if(StringUtils.isNotEmpty(evdcDt))
		{
		   header.setEvdcDt(evdcDt);
		   reknDt = DateUtils.addDay(evdcDt, 60);
		}
		header.setHdrTxt(SAPUtils.getMM_YYYYMMDD(evdcDt)+"/"+SAPUtils.getDD_YYYYMMDD(evdcDt) + "_"+ custNm +"");
        


		int idx = 1; 
		for(int i=0; i<items.length; i++)
		{
			items[i] = new CommonSlipItem();
			items[i].setSerNo(zserial);
			items[i].setDmsSeq(idx++ +"");
			items[i].setBp(SAP_BP.SKCC_HQ.getCode());
			
			items[i].setTaxCd(SAPUtils.nvl(taxCd, "A9"));
			items[i].setAmt(amt);
		}
		
		initItem0(items[0]);
		if(!flagB2C) items[0].setRef1(this.custCd);
		if(!"0".equals(taxAmt)) items[0].setAmt((Long.parseLong(amt) + Long.parseLong(taxAmt))+"");
		if(!"0".equals(taxAmt)) items[0].setTaxAmt(taxAmt);
		initItem1(items[1]);
		items[1].setRef1(this.custCd);
	}
	
	/**
	 * 거래처
	 * @param one
	 */
	private void initItem0(CommonSlipItem one)
	{
		one.setPstngKey(SAP_PSTNG_KEY.RB_D.getCode());
		one.setDealCoCd(this.custCd); //개인에코폰
		one.setBizArea(SAP_BIZ_AREA.DEV_HQ.getCode());
		one.setDsignField(SAP_WBS_ELEM.ECO_R_BIZ_SELL.getCode());
		one.setReknDt(reknDt);
		one.setPayCond(SAP_PAY_COND.AT_ONCE_CASH_IN.getCode());
	}
	
	/**
	 *상품매출-국내매출
	 * @param one
	 */
	private void initItem1(CommonSlipItem one)
	{
		one.setPstngKey(SAP_PSTNG_KEY.RB_C.getCode());
		one.setDealCoCd(SAP_DEAL_CO_CD.INSALE_GOODS.getCode());
		one.setDsignField(header.getPstngDt());
		one.setWbsElem(SAP_WBS_ELEM.ECO_R_BIZ_SELL.getCode());
		//one.setCostCntr(DMS_MGMT_DEPT.ECO_BUSINESS.getCode());
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
