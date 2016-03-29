<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="pageName" value="HOME" />	
	<!-- top file include  start -->
	<%@ include file="include/top.jsp" %>	
	<!-- top page include  end -->	
<%			
 	//결과 
	IDataSet responseData = WebUtils.getResultDataSet(request);	
	IResultMessage resultMessage = null;
	IRecordSet rsList = null;  //공지사항 팝업 목록
	IRecordSet rsList2 = null; //Home공지사항 정보
	IRecordSet rsList3 = null; //파일 다운로드 목록
	IRecord record = null; 
	int _recordCnt = 0; //공지사항 팝업 목록건수
	int _recordCnt2 = 0; //파일 다운로드 목록건수
	
	String message = "";
	String sNoData = ""; 
    String targetNm = "";
	String num = "";
	String noticeTitle = ""; //공지사항 제목
	String noticeCtt = ""; //공지사항 내용
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
	    rsList = responseData.getRecordSet("RS_ANNCE_LIST");
	    rsList2 = responseData.getRecordSet("RS_ANNCE");
	    rsList3 = responseData.getRecordSet("NC_FILE_LIST");
	     if (rsList != null){	       
	       _recordCnt = rsList.getRecordCount();
		}
	     
	    if (rsList2 != null &&  rsList2.getRecordCount() >0 ){	       
	        record = rsList2.getRecord(0);	
		   	noticeTitle = record.get("TITL");; //공지사항 제목
			noticeCtt = record.get("CTT");; //공지사항 내용
		}
	    
	     if (rsList3 != null){	       
	       _recordCnt2 = rsList3.getRecordCount();
		}	
	} 
%>		
	<!-- content start -->
	<div id="contentDiv"> 	
		<div id ="contentWrapDiv" class="container"> 
		<form name="fileDownForm" method="post" enctype="multipart/form-data">
			<input type="hidden" name="${transactionId}">
			<input type="hidden" name="${target}">
			<input type="hidden" name="<%=Constants.UPLOAD_CATEGORY%>">
			<input type="hidden" name="<%=Constants.UPLOADED_FILE_ID%>">
			<input type="hidden" name="<%=Constants.UPLOADED_FILE_NAME%>">
			<input type="hidden" name="fileId">
			<input type="hidden" name="fileName">
		</form>
		<form name="homeForm" method="post">  
			  <input type="hidden" name="${transactionId}">
			  <input type="hidden" name="${target}">
			  <input type="hidden" name="LIST_YN" value="Y">
			  <input type="hidden" name="POPUP_YN" value="Y">
		</form>		
				<h1 id="homeTitleH1" class="text-center"><%=noticeTitle%></h1>
				<div id="homeDetailDiv">
					<%=noticeCtt%>
				</div>	  				
				<div id="homeButtonDiv" class="text-center">		
			<% 
					//*******************************************************************************************************************
					//첨부파일 다운로드 버튼 생성
					//*******************************************************************************************************************
					String[] fileNmArray = null;
					String fileId = "";
					String fileAllNm = "";
				    String fileNm = "";
					for(int i=0; i<_recordCnt2; i++){ 
						record = rsList3.getRecord(i);	
						fileId = record.get("FILE_ID");
						fileAllNm =record.get("FILE_NM"); 
						//fileAllNm = new String(fileAllNm.getBytes("EUC-KR"), "KSC5601").replaceAll("\\p{Space}", "");//공백제거
						fileNmArray = fileAllNm.split("\\.");
						//버튼명 
						if(fileNmArray.length >0){
							fileNm = fileNmArray[0];
						} else {
						    fileNm = fileAllNm;
						}  
						
				//	out.println("<a href='javascript:doDownload2(\""+fileId+"\",\""+fileAllNm+"\");' download='"+fileAllNm+"' class='btn btn-default btn-sm' >"+fileNm+"</a>");			
					out.println("<a href="+request.getContextPath()+"'/filedownload.cmd?"+Constants.UPLOADED_FILE_ID+"="+fileId+"&"+Constants.UPLOADED_FILE_NAME+"="+fileAllNm+"'  download='"+fileAllNm+"' class='btn btn-default  btn-sm' >"+fileNm+"</a>");
					}	
			%>	
				</div>
		</div>
		<!-- content end -->
		<!-- footer file include  start -->
		<%@ include file="include/footer.jsp" %>	
		<!-- footer page include  end -->	
	</div>
	<script>	
		$(document).ready(function() {			
			<%					
			 	 if(rsList == null){
			 	  	//팝업공지조회
				    out.println("goSearch();");
				 } else {						     
					//팝업오픈
				    out.println("fn_openNoticePopup();");
				 }				
			%>
		});
		//파일다운로드
		function doDownload2(fileId, fileName){
			location.href = "${contextPath}/filedownload.cmd?${downloadFileId}="+fileId+"&${downloadFileNm}="+getUriEncoding(fileName);
		}	
		
		//파일다운로드
		function doDownload(fileId, fileName){
			document.fileDownForm.${transactionId}.value="fwk.FWKSBase#fFWK09FileDownload";
			document.fileDownForm.<%=Constants.UPLOADED_FILE_ID%>.value=fileId;
			document.fileDownForm.<%=Constants.UPLOADED_FILE_NAME%>.value=fileName;
			document.fileDownForm.target = "_self";
			document.fileDownForm.action = "${contextPath}/filedownload.cmd";
			document.fileDownForm.submit();
		}
		//공지팝업조회
 		function goSearch(){ 			
			document.homeForm.${transactionId}.value="PSCAnncBrdMgmt_pSAnncBrdForWebNotice";
			document.homeForm.${target}.value="forward:jsp/home.jsp";		
			document.homeForm.target = "_self";
			document.homeForm.action = "${contextPath}/standard.cmd";
			document.homeForm.submit();
		}
		
 		//공지팝업오픈
 		function fn_openNoticePopup(){ 		
 			 var popupOpen = $.cookie("popup");	
     		   if(!gfn_isNull(popupOpen)){
 				 return;
 			 }   
			<% 
					for(int i=0; i<_recordCnt; i++){ 
				    	targetNm = "pop"+i;
						record = rsList.getRecord(i);
						num = record.get("NUM");
						out.println("window.open('/jsp/popup/noticePopup.jsp?number="+num+"', '"+targetNm+"','toolbar=no,directories=no,scrollbars=yes,resizable=yes,status=no,menubar=no,width=500,height=600,top=100,left="+((50+500)*i)+"');");						
					}	
			%>
 		}
	</script>	
</body>
</html>