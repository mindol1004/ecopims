<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="pageName" value="공지사항" />	
	<!-- top file include  start -->
	<%@ include file="include/top.jsp" %>	
	<!-- top page include  end -->	
<%		
	//조회조건값  
	String _SEARCH_COND_TYPE = StringUtils.nvlObject(request.getParameter("SEARCH_COND_TYPE"), ""); //검색조건구분
	String _SEARCH_COND_VALUE = StringUtils.nvlObject(request.getParameter("SEARCH_COND_VALUE"), ""); //검색조건값
	String _QUERY_TYPE= StringUtils.nvlObject(request.getParameter("QUERY_TYPE"), ""); //쿼리유형
	
	//검색조건 공통코드 리스트
	List<HpcCode> codelist = HpcUtils.getCodes("DMS011");
	Iterator<HpcCode> _codelist = codelist.iterator();
	
	//리스트 결과 
	IDataSet responseData = WebUtils.getResultDataSet(request);	
	IResultMessage resultMessage = null;

	String message = null;
	String sNoData = "";
	int _recordCnt = 0; //데이터건수
	int _totalCnt = 0;	//총건수
	
	IRecordSet rsList = null;
	IRecord record = null;
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
	   	rsList = responseData.getRecordSet("RS_ANNCE_LIST");
	    if (rsList != null){		   
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
						  <input type="hidden" name="page" value="<%=_page%>"> 
						  <input type="hidden" name="page_size" value="<%=_page_size%>"> 
						  <input type="hidden" name="ANNCE_TYPE" value="30">
						  <input type="hidden" name="TITL" value="">
						  <input type="hidden" name="CTT" value="">
						  <input type="hidden" name="USE_YN" value="10">
						  <input type="hidden" name="QUERY_TYPE" value="LIST">
  					 <div class="formGroup">	
						<div class="formGroupLabel"><label class="formFontStyle"  for="검색조건">검색조건</label></div>
						<div class="input-append">
          					<select name="SEARCH_COND_TYPE"  class="formFontStyle input-width-100"  onchange="fn_searchTypeonChange(this.value)">
	          					<option value="">선택</option>			
	          					<%
								while (_codelist.hasNext()) {
									HpcCode c = (HpcCode) _codelist.next();
								%>
								<option value="<%=c.getCodeId() %>" <%if(c.getCodeId().equals(_SEARCH_COND_TYPE)) {%>selected<% } %>><%=c.getCodeNm() %></option>
								<%
								}
								%>				
							</select>
							<input type="text" name="SEARCH_COND_VALUE"  class="formFontStyle input-width-150 enterKeyupTarget" value="<%=_SEARCH_COND_VALUE%>"  onkeyPress="if (event.keyCode==13){return false;}">    	    
						</div>
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
					<p class="listTitleP float-left">총 <%=_totalCnt %> 건</p> 					
				</div>
				<div id="listTableDiv">
					<table class="table table-bordered table-hover table-striped listTable">
						<colgroup>
							<col/>
							<col/>
							<col/>
							<col/>
						</colgroup>
						<thead>
							<tr>
								<th style="text-align: center;" width="2%;" >NO.</th>
								<th style="text-align: center;" width="70%;" >제목</th>
								<th style="text-align: center;" width="15%;" >작성자</th>
								<th style="text-align: center;" width="13%;" >작성일자</th>
							</tr>
						</thead>
						<tbody>
						<%
							if(_recordCnt==0){
						 %>	    
						 	<tr>
								<td colspan="6" ><p class="text-center table-noDataInfo"><%=sNoData%></p></td>								
							</tr>
						<% } else {
					    	for(int i=0; i<_recordCnt; i++){
								record = rsList.getRecord(i);
						%>
							<tr> 
								<td style="text-align: center;"><%=record.get("ROWNO")%></td>	
								<td style="text-align: left;padding-left:20px;"><a href="javascript:void(0);" onclick="fn_searchDetail('<%=record.get("ANNCE_NUM")%>')"><%=record.get("TITL")%></a></td> 
								<td style="text-align: center;"><%=record.get("FS_REG_USER_NM")%></td> 
								<td style="text-align: center;"><%=record.get("FS_REG_DTM").substring(0,19)%></td>
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
	</div><%-- 
	<!-- Modal -->
	<div class="modal fade" id="noticeModal" tabindex="-1" role="dialog" aria-labelledby="noticeModalLabel" data-backdrop="static">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="noticeTitleModal"><%=sTitle%></h4>
				</div>
				<div class="modal-body" id="noticeContentModal"><%=sCtt%></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<!-- Modal end --> --%>
	<script>	
		$(document).ready(function() {		
			//페이징초기화 			
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
			
			//조회조건항목에서 엔터키 입력시 조회 이벤트 추가
			$(".enterKeyupTarget").keyup(function(event){ 
				if( event.which == 13){
					fn_beforeSearch();
				}
			});
			
			//검색조건콤보값 초기화
			fn_searchTypeonChange(<%=_SEARCH_COND_TYPE%>);
			
			//최초 페이지 오픈시 검색			
			<%					
		 	 if(_QUERY_TYPE == ""){
		 	  	//조회
			    out.println("goSearch();");
			 } 
			%> 
		});
		
		//조회 버튼 클릭시 이벤트
		function fn_beforeSearch(){			
			var sType = document.MyForm.SEARCH_COND_TYPE.value;			
			if(sType=="10"){
				document.MyForm.TITL.value = document.MyForm.SEARCH_COND_VALUE.value;
				document.MyForm.CTT.value = "";
			} else if(sType=="20"){
				document.MyForm.TITL.value = "";
				document.MyForm.CTT.value = document.MyForm.SEARCH_COND_VALUE.value;
			}	
			
			//조회서비스 호출
			goSearch();
		}
		//다른 페이지이동시 이벤트
		function goSearchPage(page){
			goSearch(page);
		}
		
		//조회서비스 호출 이벤트
 		function goSearch(nPage){			
			//페이지 설정
			if(nPage != null){
				document.MyForm.page.value = nPage;
			}			
			document.MyForm.${transactionId}.value="PSCAnncBrdMgmt_pInqAnncBrdLst";
			document.MyForm.${target}.value="forward:jsp/noticeList.jsp";		
			document.MyForm.target = "_self";
			document.MyForm.action = "${contextPath}/standard.cmd";
			document.MyForm.submit();
		} 
		//초기화 버튼 클릭시 이벤트
		function formEmpty(){
			$("select[name=SEARCH_COND_TYPE]").val('');
			$("input[name=SEARCH_COND_VALUE]").val("");
			fn_searchTypeonChange();
		}	
		
		//검색조건 콤보박스 변경시 이벤트
		function fn_searchTypeonChange(value){
			if(gfn_isNull(value)){
				$("input[name=SEARCH_COND_VALUE]").attr("disabled", "disabled"); 
			} else {
				$("input[name=SEARCH_COND_VALUE]").removeAttr("disabled");
			}	
		}	
		//공지글 상세보기 조회
		function fn_searchDetail(num){			
			if(gfn_isNull(num)){
				return;
			}
			window.open('/jsp/popup/noticePopup.jsp?readYN=Y&number='+num, 'popup','toolbar=no,directories=no,scrollbars=yes,resizable=yes,status=no,menubar=no,width=800,height=600,top=100');
		}  
	
	</script>	
</body>
</html>