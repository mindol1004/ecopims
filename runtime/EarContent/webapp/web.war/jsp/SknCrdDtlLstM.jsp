<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="pageName" value="지급내역상세 조회" />	
	<!-- top file include  start -->
	<%@ include file="include/top.jsp" %>	
	<!-- top page include  end -->	
<%		
	//조회조건값
	String _CNTRT_FR_DTM = request.getParameter("CNTRT_FR_DTM");
	String _CNTRT_TO_DTM = request.getParameter("CNTRT_TO_DTM");
	String _CNTRT_DTM = request.getParameter("CNTRT_DTM");
	String _CNTRT_YM_TS = request.getParameter("CNTRT_YM_TS");
	String _SVC_MGMT_NO = StringUtils.nvlObject(request.getParameter("SVC_MGMT_NO"), "");
	String _NR_CNTRTR_NM = StringUtils.nvlObject(request.getParameter("NR_CNTRTR_NM"), "");
	
	//기준년월 기본값 설정
	if(StringUtils.isEmpty(_CNTRT_DTM)){
	    _CNTRT_DTM = todate;
	}   
	
	//지급차수 공통코드 리스트
	List<HpcCode> codelist = HpcUtils.getCodes("DMS119");
	Iterator<HpcCode> _codelist = codelist.iterator();
	
	//리스트 결과 
	IDataSet responseData = WebUtils.getResultDataSet(request);	
	IResultMessage resultMessage = null;
	IRecordSet rsList = null;
	
	String message = null;
	String sNoData = "";
	int _recordCnt = 0; //데이터건수
	int _totalCnt = 0;	//총건수
	long l = 0;
	int sumPrc = 0;
	if(responseData != null){
	    //**********************************************************************************************************//
	    sNoData = sNoDataMessage;
	    resultMessage = responseData.getResultMessage();
	    message = BaseUtils.getMessage(resultMessage.getMessageId(), resultMessage.getMessageParams());
	    //세션체크
	    out.println( "<script>");
	    out.println("checkSessionAfterService('"+resultMessage.getMessageId()+"','"+message+"');");
	    out.println( "</script>");
	    //**********************************************************************************************************//
	   if (responseData.getRecordSet("RS_SKN_DTL_PG") != null){
	        rsList = responseData.getRecordSet("RS_SKN_DTL_PG");
	        _recordCnt = rsList.getRecordCount();
		    _totalCnt = rsList.getTotalRecordCount(); //총건수		    
		} 
	}
%>		
	<!-- content start -->
	<div id="contentDiv"> 
		<div id ="contentWrapDiv"> 
			<div id ="titleDiv" >
				<p>${pageName}</p>
			</div>
			<div id="searchDiv"  > 
				<div class="searchFormDiv">
					<form name="MyForm" method="post" class="float-left">  
						  <input type="hidden" name="${transactionId}">
						  <input type="hidden" name="${target}">
						  <input type="hidden" name="CNTRT_FR_DTM" value="<%=_CNTRT_FR_DTM %>">
			   			  <input type="hidden" name="CNTRT_TO_DTM" value="<%=_CNTRT_TO_DTM %>">
						  <input type="hidden" name="AGN_CD" value="${dealCoCd}">
						  <input type="hidden" name="page" value="<%=_page%>"> 
						  <input type="hidden" name="page_size" value="<%=_page_size%>">
						 
					  <div class="formGroup">
		          		<div class="formGroupLabel"><label class="formFontStyle"  for="기준년월">기준년월</label></div>
		          		<div class="input-append date"  id="fromDt" data-date="" data-date-format="yyyy-mm" data-date-viewmode="months" data-date-minviewmode="months">
								<input  type="text"  size="16"  name="CNTRT_DTM" value="<%=_CNTRT_DTM %>"  class="form-control input-width-100 formFontStyle enterKeyupTarget" >
								<span class="add-on"><i class="icon-calendar"></i></span>
					 	</div>
          			</div> 
	          		<div class="formGroup">	
						<div class="formGroupLabel"><label class="formFontStyle"  for="지급차수">지급차수</label></div>
						<div class="input-append">
          					<select name="CNTRT_YM_TS"  class="formFontStyle enterKeyupTarget input-width-150">
          					<option value="%">전체</option>
							<%
							while (_codelist.hasNext()) {
								HpcCode c = (HpcCode) _codelist.next();
							%>
								<option value="<%=c.getCodeId() %>" <%if(c.getCodeId().equals(_CNTRT_YM_TS)) {%>selected<% } %>><%=c.getCodeNm() %></option>
							<%
							}
							%>
							</select>        
						</div>
					</div>  							
					 <div class="formGroup">
			          		<div class="formGroupLabel"><label class="formFontStyle" for="서비스관리번호">서비스관리번호</label></div>
			          		&nbsp;<input name="SVC_MGMT_NO"  class="form-control formFontStyle input-width-150 enterKeyupTarget" value="<%=_SVC_MGMT_NO %>" type="text" >									
	          		</div> 
					<div class="formGroup">
			          		<div class="formGroupLabel"><label class="formFontStyle"  for="가입자명">가입자명</label></div>
			          		&nbsp;<input type="text" name="NR_CNTRTR_NM" class="form-control formFontStyle input-width-150 enterKeyupTarget"  value="<%=_NR_CNTRTR_NM %>" >									
	          		</div> 						
					<div class="formGroup float-left  alertSearchZone"  title="Warning!"  data-placement="right" data-toggle="popover" data-content="" ></div> 
					</form> 	
					<div class="searchBtnDiv btn-group float-right" >
				     	 <button type="button" onclick="formEmpty();" class="btn btn-default  btn-sm" >초기화</button>
					      <button type="button" class="btn btn-success  btn-sm"  onclick="fn_beforeSearch();" >검색</button>
				    </div>					
		      </div>	
			</div>
			<div id ="listDiv" >
				<div id="listTitleDiv">
					<p class="listTitleP">총 <%=_totalCnt %> 건 
					<%
						if(_recordCnt>0){
					        IRecord record2 = rsList.getRecord(0);
					        out.println("| 합계금액 : "+df.format(Integer.parseInt(record2.get("SUM_PRC")))+"원");
			    		}
					%>
					</p>
				</div>
				<div id="listTableDiv">
					<table class="listTable table table-bordered table-hover table-striped">
						<colgroup>
							<col/>
							<col/>
							<col/>
							<col/>
							<col/>
							<col/>
							<col/>
							<col/>
							<col/>
						    <col/>
							<col/>
							<col/>
							<col/>
						</colgroup>
						<thead>
							<tr>
								<th width="2%"  style="text-align: center;">NO.</th>
		            			<th style="text-align: center;" >서비스관리번호</th>
								<th style="text-align: center;" >기준년월</th>
		            			<th style="text-align: center;" >지급차수</th>
		            			<th style="text-align: center;" >가입자명</th>
		            			<th style="text-align: center;" >모델명</th>
		            			<th style="text-align: center;" >단말일련번호</th>
		            			<th style="text-align: center;" >렌탈시작일</th>
		            			<th style="text-align: center;" >렌탈종료일</th>
		            			<th style="text-align: center;" >렌탈개월</th>
		            			<th style="text-align: center;" >매입금액</th>
		            			<th style="text-align: center;" >부가세</th>
		            			<th style="text-align: center;" >지급금액합계</th>
							</tr>
						</thead>
						<tbody>
						<%
							if(_recordCnt==0){
						 %>	    
						 	<tr>
								<td colspan="13" ><p class="text-center table-noDataInfo"><%=sNoData%></p></td>								
							</tr>
						<% } else {
						    
						    String cntrtYmTsNm = "";
					    	for(int i=0; i<_recordCnt; i++){
								IRecord record = rsList.getRecord(i);
								
								//지급차수 코드명 
								cntrtYmTsNm = StringUtils.nvlObject(record.get("CNTRT_YM_TS"), "");
								_codelist = codelist.iterator(); //iterator 초기화
								while (_codelist.hasNext()) {
									HpcCode c = (HpcCode) _codelist.next();
									if(c.getCodeId().equals(cntrtYmTsNm)){
									    cntrtYmTsNm = c.getCodeNm();
									}
								}
						%>
							<tr>
								<td style="text-align: center;"><%=record.get("ROWNO")%></td>	 
								<td  style="text-align: center;"><%=record.get("SVC_MGMT_NO")%></td>
								<td  style="text-align: center;"><%=record.get("CNTRT_YM").substring(0,4) + "-" + record.get("CNTRT_YM").substring(4,6)%></td>
								<td  style="text-align: center;"><%=cntrtYmTsNm%></td>
		            			<td  style="text-align: center;"><%=record.get("NR_CNTRTR_NM")%></td>
		            			<td  style="text-align: left;padding-left:5px;"><%=record.get("EQP_MDL_NM")%></td>
		            			<td  style="text-align: center;"><%=record.get("EQP_SER_NO")%></td>
		            			<td  style="text-align: center;"><%=record.get("RENTAL_CNTRT_STA_DT").substring(0,4) + "-" + record.get("RENTAL_CNTRT_STA_DT").substring(4,6) + "-" + record.get("RENTAL_CNTRT_STA_DT").substring(6,8)%></td>
		            			<td  style="text-align: center;"><%=record.get("RENTAL_CNTRT_END_DT").substring(0,4) + "-" + record.get("RENTAL_CNTRT_END_DT").substring(4,6) + "-" + record.get("RENTAL_CNTRT_END_DT").substring(6,8)%></td>
		            			<td  style="text-align: center;"><%=record.get("CNTRT_MTH")%></td>
		            			<td  style="text-align: right;padding-right:5px;"><%=df.format(Integer.parseInt(record.get("SPLY_PRC")))%></td>
		            			<td  style="text-align: right;padding-right:5px;"><%=df.format(Integer.parseInt(record.get("SURTAX_AMT")))%></td>
		            			<td style="text-align: right;padding-right:5px;"><%=df.format(Integer.parseInt(record.get("PRCH_AMT")))%></td>
							</tr>
							<%
									}//for
								}
							%>
						</tbody>
					</table>
				</div>
				<!-- paging navi start -->
				<div class="page-wrap"></div>
				<!-- paging navi end -->
			</div>
		</div>				
		<!-- content end -->
		<!-- footer file include  start -->
		<%@ include file="include/footer.jsp" %>	
		<!-- footer page include  end -->	
	</div>	
	<script>
		$(document).ready(function() {
			//1.달력초기화
			$('#fromDt').datepicker();
			
			//2. 페이징초기화 			
			var page = <%=_page%>;
			var pageCnt = <%=_page_size%>;			
			var totalCnt = <%=_totalCnt%>;
			if(totalCnt >0){
				 $(".page-wrap").pager({ 
							pagenumber: page, 
							pagecount: pageCnt, 
							totalcount: totalCnt,
							buttonClickCallback: 
							function(pagenumber){ 
								goSearchPage(pagenumber);
							}
				});
				$(".page-wrap .page-list li ").css("margin","0 2px");		
			}
			
			//3. 조회조건항목에서 엔터키 입력시 조회 이벤트 추가
			$(".enterKeyupTarget").keyup(function(event){ 
				if( event.which == 13){
					fn_beforeSearch();
				}
			});
			
		});
		//조회 버튼 클릭시 이벤트
		function fn_beforeSearch(){		
			var oCurYear  = "";
 			var oCurMonth = "";
 			var cv_sDate = "23" //차수 구분일자 : 23일까지는 1차, 24일부터는 2차
 			var _CNTRT_DTM = document.MyForm.CNTRT_DTM.value;
 			var _CNTRT_YM_TS = document.MyForm.CNTRT_YM_TS.value;
 			 			
 			oCurYear = _CNTRT_DTM.substring(0,4);
 			oCurMonth = _CNTRT_DTM.substring(4,6);

 			var oMaxDay = new Date(new Date(oCurYear, oCurMonth, 1)-86400000).getDate();
 			var sCntrtDtm = document.MyForm.CNTRT_DTM.value.replace("-","");
 			
 			//검색조건 유효성검사	
			//기준년월
			var obj = { 	alertContent	:"기준년월을 확인해주세요",
							startDate:sCntrtDtm,
							endDate:'',
							startDateName:"CNTRT_DTM",
							endDateName:'',
							mode:2 //2: 단일 월 체크
			             };
			
			if(!gfn_checkPeriod(obj)){
				return false;
			}
			 			
 			if(_CNTRT_YM_TS == "01"){
 				
 				document.MyForm.CNTRT_FR_DTM.value = sCntrtDtm + "01";
 				document.MyForm.CNTRT_TO_DTM.value = sCntrtDtm + cv_sDate;
 			}else if(_CNTRT_YM_TS == "02"){
 				document.MyForm.CNTRT_FR_DTM.value = sCntrtDtm + cv_sDate;
 				document.MyForm.CNTRT_TO_DTM.value = sCntrtDtm + oMaxDay;
 			}else{
 				document.MyForm.CNTRT_FR_DTM.value = sCntrtDtm + "01";
 				document.MyForm.CNTRT_TO_DTM.value = sCntrtDtm + oMaxDay;
 			}
 			
			
			//조회서비스 호출
			goSearch();
		}
		
		//다른 페이지이동시 이벤트
		function goSearchPage(page){
			goSearch(page);
		}
			
		//조회 버튼 클릭시 이벤트
 		function goSearch(page){
 			 			
 			//페이지 설정
			if(page != null){
				document.MyForm.page.value = page;
			}
 			
			document.MyForm.${transactionId}.value="PNRLoanMgmt_pInqSKNLoanDtlLstWeb";
			document.MyForm.${target}.value="forward:jsp/SknCrdDtlLstM.jsp";				
			document.MyForm.target = "_self";
			document.MyForm.action = "${contextPath}/standard.cmd";
			document.MyForm.submit();
		} 
		//초기화 버튼 클릭시 이벤트
		function formEmpty(){
			$("input[name=SVC_MGMT_NO]").val('');
			$("input[name=NR_CNTRTR_NM]").val('');
			$("input[name=CNTRT_FR_DTM]").val('');
			$("input[name=CNTRT_TO_DTM]").val('');
			$("input[name=CNTRT_DTM]").val('<%=todate%>');
			$("select[name=CNTRT_YM_TS] option[value='']").attr("selected",true);
		}
				
		
	</script>	
</body>
</html>