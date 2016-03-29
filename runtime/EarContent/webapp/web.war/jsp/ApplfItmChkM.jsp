<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="pageName" value="신청서 보완내역 조회" />	
	<!-- top file include  start -->
	<%@ include file="include/top.jsp" %>	
	<!-- top page include  end -->	
<%		
	//조회조건값
	String _SVC_MGMT_NO = StringUtils.nvlObject(request.getParameter("SVC_MGMT_NO"), "");

	//신청서상태 공통코드 리스트
	List<HpcCode> codelist = HpcUtils.getCodes("DMS266");
	Iterator<HpcCode> _codelist = codelist.iterator();
	
	//리스트 결과 
	IDataSet responseData = WebUtils.getResultDataSet(request);	
	IResultMessage resultMessage = null;
	IRecordSet rsList = null;
	
	String message = null;
	String sNoData = "";
	int _recordCnt = 0; //데이터건수
	int _totalCnt = 0;	//총건수
	
	if(responseData != null){
	  //**********************************************************************************************************//
	    sNoData = sNoDataMessage;
	    resultMessage = responseData.getResultMessage();
	    message = BaseUtils.getMessage(resultMessage.getMessageId(), resultMessage.getMessageParams());	    
	    //세션체크
	    out.println("<script>");
	    out.println("checkSessionAfterService('"+resultMessage.getMessageId()+"','"+message+"');");
	    out.println( "</script>");
	  //**********************************************************************************************************//	    
	    if (responseData.getRecordSet("RS_NR_APPLF_PG") != null){
		    rsList = responseData.getRecordSet("RS_NR_APPLF_PG");
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
						  <input type="hidden" name="AGN_CD" value="${agnOrgCd}">
						   <input type="hidden" name="page" value="<%=_page%>"> 
						  <input type="hidden" name="page_size" value="<%=_page_size%>">						 
		  				 
	          	<%-- 	<div class="formGroup">	
						<div class="formGroupLabel"><label class="formFontStyle"  for="신청서유형">신청서유형</label></div>
						<div class="input-append">
          					<select name="APPLF_TYP" style="width:auto;font-size:12px; font-family:돋움,Dotum,Arial,sans-serif;" >
          						<option value="%">전체</option>
							<%
							while (_codelist.hasNext()) {
								HpcCode c = (HpcCode) _codelist.next();
							%>
								<option value="<%=c.getCodeId() %>" <%if(c.getCodeId().equals(_APPLF_TYP)) {%>selected<% } %>><%=c.getCodeNm() %></option>
							<%
							}
							%>
							</select>        
						</div>
					</div>  	 --%>
					<div class="formGroup">
			          		<div class="formGroupLabel"><label class="formFontStyle"  for="서비스관리번호">서비스관리번호</label></div>
			          		&nbsp;<input type="text" name="SVC_MGMT_NO"  value="<%=_SVC_MGMT_NO %>"  class="form-control formFontStyle input-width-150 enterKeyupTarget"  onkeyPress="if (event.keyCode==13){return false;}">									
	          		</div> 	
					<div class="formGroup float-left  alertSearchZone"  title="Warning!"  data-placement="right" data-toggle="popover" data-content="" ></div> 
					</form> 	
					<div class="searchBtnDiv btn-group float-right" >
				      <input type="button" value="초기화" onclick="formEmpty();" class="btn btn-default  btn-sm" />
				      <input type="button" value="검색" onclick="fn_beforeSearch();" class="btn btn-success  btn-sm" />
				    </div>	
		      </div>	
			</div>
			<div id ="listDiv" >
				<div id="listTitleDiv"><p class="listTitleP">총 <%=_totalCnt %> 건</p></div>
				<div id="listTableDiv">
					<table class="listTable table table-bordered table-hover table-striped">
						<colgroup>
							<col width="2%" />
		            		<col width="10%" />
		            		<col width="10%" />
		            		<col width="10%" />
		            		<col width="13%" />
		            		<col width="15%" />
		            		<col width="20%" />
		            		<col width="20%" />
						</colgroup>
						<thead>
							<tr>
								<th style="text-align: center;" >NO.</th>
		            			<th style="text-align: center;" >서비스관리번호</th>
		            			<th  style="text-align: center;" >렌탈일련번호</th>
		            			<th  style="text-align: center;" >가입자명</th>
		            			<th  style="text-align: center;" >사업자번호</th>
		            			<th  style="text-align: center;" >신청서상태</th>
		            			<th  style="text-align: center;" >오류항목</th>
		            			<th  style="text-align: center;" >보완내용</th>
							</tr>
						</thead>
						<tbody>
						<%
							if(_recordCnt==0){
						 %>	    
						 	<tr  class="" >
								<td colspan="16" ><p class="text-center table-noDataInfo"><%=sNoData%></p></td>								
							</tr>
						<% } else {
						    String applfSt = "";
					    	for(int i=0; i<_recordCnt; i++){
								IRecord record = rsList.getRecord(i);
								
								//신청서상태 코드명 
								applfSt = StringUtils.nvlObject(record.get("APPLF_ST"), "");
								_codelist = codelist.iterator(); //iterator 초기화
								while (_codelist.hasNext()) {
									HpcCode c = (HpcCode) _codelist.next();
									if(c.getCodeId().equals(applfSt)){
									    applfSt = c.getCodeNm();
									}
								}
						%>
							<tr id="<%=StringUtils.nvlObject(record.get("CNTRT_NO"), "")%>"  class="tabletr" > <!--cursor-hand   -->
								<td style="text-align: center;"><%=record.get("ROWNO")%></td>	
		            			<td style="text-align: center;"><%=StringUtils.nvlObject(record.get("SVC_MGMT_NO"), "")%></td>
		            			<td style="text-align: center;"><%=StringUtils.nvlObject(record.get("RENTAL_SER_NUM"), "")%></td>
		            			<td style="text-align: center;"><%=StringUtils.nvlObject(record.get("NR_CNTRTR_NM"), "")%></td>
		            		    <td style="text-align: center;"><%=StringUtils.nvlObject(record.get("NR_CNTRTR_BIZ_NUM"), "")%></td>
		            			<td style="text-align: center;"><%=applfSt%></td>
		            			<td style="text-align: center;"><%=StringUtils.nvlObject(record.get("APPLF_ITM"), "")%></td>
		            			<td style="text-align: center;"><%=StringUtils.nvlObject(record.get("APPLF_RMK"), "")%></td>
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
			
			//1. 페이징초기화 			
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
			
			//2. 조회조건항목에서 엔터키 입력시 조회 이벤트 추가
			$(".enterKeyupTarget").keyup(function(event){ 
				if( event.which == 13){
					fn_beforeSearch();
				}
			});
			//3. 테이블 행 더블클릭시 팝업오픈 이벤트 추가
			/* $('.tabletr').on('dblclick', function(event){
				   var targetClassNm = this.className;
				   $(".tabletr").removeClass("info"); //모든 행의 "선택 상태" 취소
				   this.className = "cursor-hand tabletr info";
				   var cntrNo = this.id;
				   window.open('/jsp/popup/AppIfItmChkPopup.jsp?cntrNo='+cntrNo, 'popup'+cntrNo,'toolbar=no,directories=no,status=no,menubar=no,width=700,height=905,top=0');

			}) */
			
			//4. 테이블 행 클릭시 모든 행의 "선택 상태" 취소
			$('.tabletr').on('click', function(event){
				   var targetClassNm = this.className;
				   $(".tabletr").removeClass("info"); //모든 행의 "선택 상태" 취소
			})
		});
		//조회 버튼 클릭시 이벤트
		function fn_beforeSearch(){
			
			//검색조건 유효성검사	
			
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
			document.MyForm.${transactionId}.value="PNRLoanMgmt_pInqApplfChkLstWeb";
			document.MyForm.${target}.value="forward:jsp/ApplfItmChkM.jsp";
			document.MyForm.target = "_self";
			document.MyForm.action = "${contextPath}/standard.cmd";
			document.MyForm.submit();
		}
		//초기화 버튼 클릭시 이벤트
		function formEmpty(){
			$("input[name=SVC_MGMT_NO]").val('');
		}
		
	</script>	
</body>
</html>