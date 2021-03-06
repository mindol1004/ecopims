<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="nexcore.framework.core.Constants"%>
<%@ page import="nexcore.framework.core.util.BaseUtils"%>
<%@ page import="nexcore.framework.core.data.user.IUserInfo"%>
<%@ page import="nexcore.framework.online.channel.util.WebUtils"%>
<%@page import="java.util.*" %>
<%@page import="java.text.*" %>
<%@ page import="nexcore.framework.core.*"%>
<%@ page import="nexcore.framework.core.file.*"%>
<%@ page import="nexcore.framework.core.prototype.*"%>
<%@ page import="nexcore.framework.online.channel.util.*"%>
<%@page import="nexcore.framework.core.data.IDataSet" %>
<%@page import="nexcore.framework.core.data.IRecordSet" %>
<%@page import="nexcore.framework.core.data.IRecord" %>
<%@page import="nexcore.framework.core.data.IResultMessage" %>
<%@page import="nexcore.framework.core.util.BaseUtils" %>
<%@page import="nexcore.framework.core.util.ExceptionUtil" %>
<%@page import="nexcore.framework.core.code.ICode"%>
<%@page import="fwk.utils.HpcUtils"%>
<%@page import="fwk.code.HpcCodeManager"%>
<%@page import="fwk.code.internal.HpcCode"%>
<%@page import="nexcore.framework.core.util.StringUtils"%>
<%@ page import="java.net.URLEncoder"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="contextPath" value="<%=request.getContextPath()%>" />
<c:set var="transactionId" value="<%= Constants.TRANSACTION_ID %>" />
<c:set var="target" value="<%= Constants.TARGET %>" />
<%			
	String num = StringUtils.nvlObject(request.getParameter("number"), "");	
	String readYN = StringUtils.nvlObject(request.getParameter("readYN"), "");	
	String message = null;
	IResultMessage resultMessage = null;
	IDataSet responseData = WebUtils.getResultDataSet(request);	
	IRecordSet rsList = null;
	IRecordSet rsList2 = null;
	IRecord record = null;
 	String title = "";
 	String ctt = "";
	
	int _recordCnt = 0; //데이터건수
	if(responseData != null){  
	   //**********************************************************************************************************//
	    resultMessage = responseData.getResultMessage();
	    message = BaseUtils.getMessage(resultMessage.getMessageId(), resultMessage.getMessageParams());	    	  
	  //**********************************************************************************************************//	    
	    rsList = responseData.getRecordSet("RS_ANNCE");
	     if (rsList != null && rsList.getRecordCount() >0){	
	         record = rsList.getRecord(0);
	         title =  StringUtils.nvlObject(record.get("TITL"), "");
	         ctt =  StringUtils.nvlObject(record.get("CTT"), "");
		}	    
	     rsList2 = responseData.getRecordSet("NC_FILE_LIST");
	     if (rsList2 != null){	
	         _recordCnt = rsList2.getRecordCount();
		}	    
	} 
%>		
<c:set var="num" value="<%=num%>" />
<c:set var="title" value="<%=title%>" />	
<!DOCTYPE html>
<html>
<head>
	<title>${title}</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" >
	<!-- libraryList include  start -->
	<link href=" ${contextPath}/ext/css/bootstrap.css" rel="stylesheet" type="text/css"/>
	<link href="${contextPath}/ext/css/common.css" rel="stylesheet" type="text/css"/>
	<!-- libraryList include  end -->			
</head>
<body>	
	<form name="noticePopupForm" method="post" class="hiddenForm">  
			  <input type="hidden" name="${transactionId}">
			  <input type="hidden" name="readYN" value="<%=readYN%>">
			  <input type="hidden" name="${target}">
			  <input type="hidden" name="NUM"  value="${num}">
			  <input type="hidden" name="LIST_YN" value="N">
		
	<div id="noticePopupDiv" class="display-hidden container-fluid container-fluid-popup-custom">
		 <div class="panel-default panel">
	        <div id="noticePopupHeaderDiv"  class="panel-heading popup-title-text"> ${title}</div>
	        <div id="noticePopupContentWrap" class="panel-body panel-body-custom">
	        	<div id="noticePopupContentDiv">
	        		<%=ctt%> 
	        	</div>
	        	<%
						if(_recordCnt >0){
				 %>	
				 <table id="noticePopupTable" class="table table-bordered table-hover table-striped panel-in-table panel-in-table-left-border">
							<colgroup>
								<col/>
							</colgroup>
							<thead>
								<tr>
									<th style="text-align: center;" width="2%;" >NO.</th>
									<th style="text-align: center;" width="70%;" >첨부파일</th>
								</tr>
							</thead>
							<tbody>
							<% 
						    	for(int i=0; i<_recordCnt; i++){
									String fileId = "";
									String fileAllNm = "";
								    
									record = rsList2.getRecord(i);
									
									fileId = record.get("FILE_ID");
									fileAllNm =record.get("FILE_NM"); 
									
									/* if(!StringUtils.isEmpty(fileAllNm)){
									    fileAllNm = new String(fileAllNm.getBytes("EUC-KR"), "KSC5601").replaceAll("\\p{Space}", "");//공백제거
									} */
							%>
								<tr> 
									<td style="text-align: center;"><%=i+1%></td>	
								    <td style="text-align: left;padding-left:20px;">
								    	<a href="${contextPath}/filedownload.cmd?<%=Constants.UPLOADED_FILE_ID%>=<%=fileId%>&<%=Constants.UPLOADED_FILE_NAME%>=<%=fileAllNm%>" >
								    	<%=fileAllNm%>
								    	</a>
								    </td>
									<%-- <td style="text-align: left;padding-left:20px;"><a href="javascript:doDownload('<%=fileId%>','<%=fileAllNm%>');"  ><%=fileAllNm%></a></td>  --%>
								</tr>
								<%
									}
								%>
							</tbody>
						</table>
					<%
						}
					%>	
			</div>	
			 <div id="noticePopupFooterDiv" class="popup-close-button-div">
		          <div id="noticePopupFooterDivBtnDiv" class="float-right">       
		         	<button type="button" class="btn btn-default btn-small-custom"  onclick="fn_close()">Close</button>
		         </div>
			     <div id="checkTodayViewDiv" class="checkbox float-right checkbox-div-custom">
					    <label class="font-small-custom">
					      <input type="checkbox" name="popup_end"  id="popup_end" onclick="fn_check()">
					       <p>오늘 하루 이창을 열지 않음</p>
					    </label>
				  </div>  		
	        </div>
	   </div>	   	  
   </div>
</form>	
<form name="fileDownForm" method="post" enctype="multipart/form-data" class="hiddenForm">
	<input type="hidden" name="${transactionId}">
	<input type="hidden" name="${target}">
	<input type="hidden" name="<%=Constants.UPLOAD_CATEGORY%>">
	<input type="hidden" name="<%=Constants.UPLOADED_FILE_ID%>">
	<input type="hidden" name="<%=Constants.UPLOADED_FILE_NAME%>">
</form>
<script type="text/javascript" src="${contextPath}/ext/js/jquery-1.9.1.js"></script>
<script type="text/javascript" src="${contextPath}/ext/js/jquery.cookie.js"></script> 
<script type="text/javascript" src="${contextPath}/ext/js/common.js"></script>
<script>	
	$(document).ready(function() {
	<%		
			if(responseData != null){  
			    out.println("checkSessionAfterService('"+resultMessage.getMessageId()+"','"+message+"','popup');");
			}
		 	 if(rsList == null){
		 	  	//공지사항내용조회
			    out.println("goSearch();"); 
			 } else {		 	
				//공지사항게시판 읽기 전용인 경우 체크박스 display hidden처리 
				out.println("fn_setPopUp('"+readYN+"','"+_recordCnt+"');");
			 }
		%>
				
	});
	
	function fn_setPopUp(readYN,fileCount){		
		//초기화
		$('#noticePopupDiv').removeClass('display-hidden');
		var iContentHeight = $('#noticePopupContentWrap').height();
		var tableHieght = $('#noticePopupTable').height();
		//var iContentHeight = $('body').height();
		//공지사항 게시글 상세조회인 경우 
		if(readYN == "Y"){
			$('#checkTodayViewDiv').addClass('display-hidden');
			$("#noticePopupFooterDivBtnDiv").removeClass("float-right");
			$('#noticePopupFooterDivBtnDiv').addClass('text-center');
		} 
		
		if(iContentHeight <= 480) {
			$('#noticePopupContentDiv').css("min-height", (460-tableHieght)+"px");
			$('#noticePopupContentWrap').css("min-height", "480px");
		}
		/* if( fileCount>0 ){
			$('#noticePopupContentWrap').css("min-height", "397px");
		} else {
			$('#noticePopupContentWrap').css("min-height", "460px");
		}	 */
		//$('body').css("overflow", "hidden");
		/* if(iContentHeight <= 590) {
			//$('body').css("overflow", "hidden");
		} else {
			//$('body').css("overflow", "auto");
			//$(window).resize();
		} */
	}
	
	function doDownload(fileId, fileName){
		document.fileDownForm.${transactionId}.value="";
		document.fileDownForm.<%=Constants.UPLOADED_FILE_ID%>.value=fileId;
		document.fileDownForm.<%=Constants.UPLOADED_FILE_NAME%>.value=fileName;
		document.fileDownForm.target = "_self";
		document.fileDownForm.action = "${contextPath}/filedownload.cmd";
		document.fileDownForm.submit();
	}
	
	//공지팝업조회
	function goSearch(){ 			
		document.noticePopupForm.${transactionId}.value="PSCAnncBrdMgmt_pSAnncBrdForWebNotice";
		document.noticePopupForm.${target}.value="forward:jsp/popup/noticePopup.jsp";		
		document.noticePopupForm.target = "_self";
		document.noticePopupForm.action = "${contextPath}/standard.cmd";
		document.noticePopupForm.submit();
	}
	
	//팝업오픈체크시 이벤트
	function fn_check(){
		var bPopupEnd = document.getElementById("popup_end").checked;
		if(bPopupEnd){
			$.cookie('popup', 'y', {path:'/', expires: 1});
	    }else{
			$.cookie('popup', null, {path:'/'});
		}		
		
	} 	
	//닫기 이벤트
	function fn_close(){
		this.close();
	} 
</script>   
</body>
</html>