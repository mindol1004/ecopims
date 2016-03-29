<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
	<!-- top file include  start -->
	<%@ include file="include/top.jsp" %>	
	<!-- top page include  end -->	
	<%
	//get data
	IDataSet responseData = WebUtils.getResultDataSet(request);
	if(responseData != null){
		IResultMessage resultMessage = responseData.getResultMessage();
		String Message = BaseUtils.getMessage(resultMessage.getMessageId(), resultMessage.getMessageParams());
		
		//로그인성공여부 확인 
		if(resultMessage.getStatus() != IResultMessage.FAIL){  
		    out.println( "<script>");
		    out.println( " location.href='jsp/home.jsp';");
		    out.println( "</script>"); 
		} else {
		    //로그인여부에 따라 분기처리  
	 	    if( "DMS00011".equals(resultMessage.getMessageId()) || "DMS00013".equals(resultMessage.getMessageId())){
	 	       out.println( "<script>");
			    out.println( "alert('"+Message+"');"); 
			    out.println( "window.open('/jsp/chgPwdPopup.jsp', 'pop','toolbar=no,directories=no,scrollbars=no,resizable=no,status=no,menubar=no,width=395,height=460');");
			    out.println( "</script>"); 
		    } else { 
			    out.println( "<script>");
			    out.println( "alert('"+Message+"');");
			    out.println( " location.href='jsp/login.jsp';");
			    out.println( "</script>"); 
		    }
		}
	}	
	%>	
	<!-- content start -->
	<div id="contentDiv"> 
		<div id ="contentWrapDiv"> 			
			<!-- login layout start  -->	
			<div id="loginWrapDiv">
				 <div class="page_tit"><img src="${contextPath}/ext/img/member_title_01.gif" alt="로그인" /></div>
	                <div class="loginDiv">            
	                	<form name="loginForm" method="post" >
	                	<input type="hidden" name="${transactionId}">
						<input type="hidden" name="${target}">
						<%-- <input type="hidden" name="ipad" value="<%=request.getRemoteAddr()%>">		
						<input type="hidden" name="urlReferer" id="urlReferer" value="${urlReferer}"/> --%>
						<input type="hidden" name="LINK_SYSTEM" value="NO">		
						<input type="hidden" name="FORCED_YN" value="Y">		
	                    <div class="login-option">                		
	                        <ul class="login-form" >
	                            <li>
	                                <input type="text" name="USER_ID" id="USER_ID" class="input form-control" alt="아이디" />
	                            </li>
	                            <li class="mt-10">
	                                <input type="password" name="USER_PWD"  id="USER_PWD"  class="input form-control enterKeyupTarget" alt="비밀번호"  />
	                            </li>
	                            <li class="mt-10">	      
	                            	  <div id="checkSaveUserIdP" class="checkbox float-left checkbox-div-custom">
									    <label class="font-small-custom">
									      <input type="checkbox" name="saveUserId"  id="saveUserId">
									      <p>아이디저장</p>
									    </label>
									  </div>                     
	                                  <div class="alertSearchZone"  title="Warning!"  data-placement="bottom" data-toggle="popover" data-content="" ></div>
	                            </li>  
	                        </ul>
	                    </div>	                 
	                </form>	                    
                   	<div class="loginButtonDiv"><input type="image" src="${contextPath}/ext/img/btn_box_login.gif" onClick="fn_onBeforeLogin()" /></div>               	 	
                </div> 
            </div>
			<!-- login layout end -->
		</div>
		<!-- content end -->
		<!-- footer file include  start -->
		 <%@ include file="include/footer.jsp" %>	
		<!-- footer page include  end -->	
	</div>	
	
<script>	
	$(document).ready(function() {
		var userId = $.cookie("admin_id");		
		if ( userId == "undefined" || userId == null || userId == "") {
	 		$("input[name='saveUserId']").removeAttr("checked");
			$("input[name='USER_ID']").focus(); 
		} else {
		    $("input[name='saveUserId']").attr("checked", "checked");
			$("input[name='USER_ID']").val(userId);
			$("input[name='USER_PWD']").focus(); 
		} 				
		
		//엔터키 입력시 이벤트 추가
		$(".enterKeyupTarget").keyup(function(event){ 
			if( event.which == 13){
				fn_onBeforeLogin();
			}
		});
		
	});
	
	function fn_onBeforeLogin() {
		var saveUserId = document.getElementById("saveUserId").checked;
		var userId = $("input[name='USER_ID']").val();
		
		if(!gfn_check(["USER_ID","USER_PWD"])){
			return false;	
		}
		
		if(saveUserId){$.cookie('admin_id', userId, {path:'/', expires:30});}
		else{$.cookie('admin_id', null, {path:'/'});}		
		doLogin();
	
	}

	function doLogin(){
		document.loginForm.${transactionId}.value="PSCLginOp_pLginWeb";
		document.loginForm.${target}.value="forward:jsp/login.jsp"; //loginProcess
		document.loginForm.target = "_self";
		document.loginForm.action = "${contextPath}/login.cmd";
		document.loginForm.submit();
	}
	
</script>
</body>
</html>