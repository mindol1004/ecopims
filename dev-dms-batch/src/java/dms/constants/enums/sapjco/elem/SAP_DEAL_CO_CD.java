package dms.constants.enums.sapjco.elem;

/**
 * gl계정 및 거래처 코드
 * @author greatjin
 *
 */
public enum SAP_DEAL_CO_CD {
	  LEASE_ASSET_NR   ("269107") //리스자산 신규폰 렌탈
	, UNCLT_KRW_DEV    ("217910") //미수금-원화 DEVICE사업  
	, PERSONAL         ("501000") // 개인
	, LEASE_INCOME_NR  ("506401") // 리스수익 NR
	, PAY_COMMISSION   ("726301") //[판매수수료] 지급수수료
	, ASSET_DEPRECIATION_AMT_NR ("713807")//감가상각비 NR
	, PJT_MATERIAL              ("625101")//PJT재료비
	, ALLWN_ETC                 ("444901") //충당부채기타
	, ASSET_DEPRECIATION_SUM_AMT_NR ("269207") //감가상각비누계액
	, ETC_INCOME                    ("939301") // 집이익
	, UNEXPOSED_INSURANCE           ("227201") //선급보험료
	, AP_ETC                        ("409950") //미지급금-기타
	, AFBD                          ("208101")//대손충당금 [allowance for bad debts, reserve for bad debts, 貸損充當金]
    , BDE                           ("729101")//대손상각비 [bad debt expense]
    , INVENTORY_RAW                 ("241101")//재고자산 원재료
    , GOODS_NR                      ("241702")//상품_신규(R)
    , INVENTORY_OBSOLESCENCE        ("971101")//재고자산 감모손실 [ inventory obsolescence ]
    , LOSS_VALUATION_INVENTORY      ("602801")//재고자산 평가손실 loss from valuation of inventory , 在庫資産評價損失
    
	
	, LEASE_ASSET_PR               ("269108") ///리스자산 임대 렌탈
	, ASSET_DEPRECIATION_SUM_AMT_PR("269208") //감가상각비누계액PR
	, ASSET_DEPRECIATION_AMT_PR    ("713808")//감가상각비 PR
	, LEASE_INCOME_PR              ("506402") // 리스수익 PR
	
	
	, GOODS            ("241701") //상품
	, NOT_ARRIVAL_GOODS("242601") //미착품
	, LCEL             ("430801") //유동충당부채손실 loss current estimated liabilities liabilities

	, INSALE_GOODS      ("502101") //상품매출-국내매출
	, OUTSALE_GOODS     ("502201") //상품매출-직수출
	
	, UNCLT_ETC        ("217950") //미수금-기타  

	, LDTA("962101") //유형자산처분손실 loss on disposition of tangible assets
	
	;
	private String code;
		
	SAP_DEAL_CO_CD(String code)
	{
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	
	public String toString()
	{
		return code;
	}
		
}
