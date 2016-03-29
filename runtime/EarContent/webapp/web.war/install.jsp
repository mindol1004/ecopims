<%@ page import="nets.websso.ssoclient.authcheck.*" %>
<%@ page import="java.util.Properties" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR" %>
<HTML>
<HEAD>
<TITLE>XPLATFORM Install Page</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9, requiresActiveX=true">
<link rel="stylesheet" href="img/style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript"> 
<%
	String url = request.getRequestURL().toString();
	url = url.substring(0, url.indexOf("/",8));
	
%>

var sKey = "DMS";  //프로젝트 고유id
var Server_Path = "http://" + window.location.host;	//사용하는 서버 주소
var Path = "/dms";
var bOnError = false;
var XPLATFORM_CAB_VER = "2015,5,26,1";	//cab ver 지정

function fn_load()
{  	
	if(bOnError == false) {  
		//Xflatform open and parent window close
		fn_launchX();
		setInterval("fn_close()", 1000);
	}
}

//프로그램설치여부 체크함수
function fn_objectOnError(obj) {	
	bOnError = true;
}

function fn_close()
{	
	if (navigator.userAgent.indexOf("MSIE") > -1 || navigator.userAgent.indexOf("Trident") !=-1) {
		 //Explorer 실행시 
		window.opener = "nothing";
		window.open('', '_parent', '');
		window.close();		
	} else {
	   //Chrom에서 실행시 
	   window.open('', '_self', '');
	   window.close();
		 
	}
}

function fn_launchX()
{  
	XLauncher.key = sKey;
	XLauncher.version = "9.2.1";
	XLauncher.xadl = Server_Path + Path + "/DMS.xadl";	//프로젝트의 xadl 위치
	XLauncher.onlyone = true;	//실행할 프로그램 한개 or 한개이상 실행여부 설정
	XLauncher.splashimage = Server_Path + Path +"/Install/img/hpc_loading.gif";	//loading 이미지 지정부분
	XLauncher.loadingimage = Server_Path + Path+"/Install/img/hpc_loading.gif";	//loading 이미지 지정부분	
	XLauncher.globalvalue = "gv_LinkSystem=NO";
	XLauncher.makeshortcut("DMS", Server_Path + Path +"/Install/img/mainicon.ico", "desktop",true);	// 단축 Icon 지정
	XLauncher.launch();	//전용브라우저 실행
}
</SCRIPT> 
</HEAD>


<BODY onload="fn_load()">
<SCRIPT LANGUAGE="JavaScript">
if (navigator.userAgent.indexOf("MSIE") > -1 || navigator.userAgent.indexOf("Trident") !=-1) {
	
	 //Explorer 실행시 
	// PDF Export Module
	document.write('<object');
	document.write(' id=rdpdf50');
	document.write(' classid="clsid:0D0862D3-F678-48B5-876B-456457E668BC"');
	document.write(' width=0%');
	document.write(' height=0%');
	document.write(' onError="fn_objectOnError(this)" ');
	document.write(' codebase="'+Server_Path+Path+'/Install/Download/rdpdf50.cab#version=2,2,0,96">');
	document.write(' </object>'); 
	
	// Barcode Control 9
// 	document.write('<object');
// 	document.write(' id=rdbarcode9');
// 	document.write(' classid="clsid:88F70D57-2624-4E7C-A31E-2FC338B61EF2"');
// 	document.write(' width=0%');
// 	document.write(' height=0%');
// 	document.write(' codebase="'+Server_Path+Path+'/Install/Download/rdbarcode9.cab#version=9,0,0,8245">');
// 	document.write('</object>');
	
	// Barcode Control 5
	document.write('<object');
	document.write(' id=rdbarcode5');
	document.write(' classid="clsid:AA30E61C-DBC4-4DF6-B2CC-FAE39282CF56"');
	document.write(' width=0%');
	document.write(' height=0%');
	document.write(' onError="fn_objectOnError(this)" ');
	document.write(' codebase="'+Server_Path+Path+'/Install/Download/rdbarcode5.cab#version=5,5,1,54">');
	document.write('</object>');
	
	//주의사항 : 레포트뷰어 object 3개 중 cxviewer의 순서가 마지막이어야함
	//Report Designer ActiveX Control (width, height는 화면 크기에 맞게 조정)

	document.write(' <object ');
	document.write(' id=Rdviewer');
	document.write(' name=Rdviewer');
	document.write(' width=0%');
	document.write(' height=0%');
	document.write(' onError="fn_objectOnError(this)" ');
	document.write('  classid="clsid:5A7B56B3-603D-4953-9909-1247D41967F8"');
	document.write(' codebase="'+Server_Path+Path+'/Install/Download/rdviewer50u.cab#version=5,0,0,539">');
	document.write(' </object>');
	
	//USACN viewer
	document.write(' <object ');
	document.write(' id=InziViewer_R');
	document.write(' name=InziViewer_R');
	document.write(' width=0%');
	document.write(' height=0%');
	document.write(' onError="fn_objectOnError(this)" ');
	document.write('  classid="clsid:E4BB38C3-21C1-4781-AE63-971E8BAA97DD"');
	document.write(' codebase="'+Server_Path+Path+'/Install/Download/InziViewer_R_Setup.cab#version=1,3,0,0">');
	document.write(' </object>');	
	
	/*USACN SFS 폴더암호화
	document.write(' <object ');
	document.write(' id=InziSFS');
	document.write(' name=InziSFS');
	document.write(' width=0%');
	document.write(' height=0%');
	document.write('  classid="clsid:A22D6A34-980C-4B04-A02A-4C6C995AF5CF"');
	document.write(' codebase="'+Server_Path+Path+'/Install/Download/SKT_InziiSecuSetup_UK_Win7.cab#version=2,0,0,1">');
	document.write(' </object>');

	//USACN From setup
	document.write(' <object ');
	document.write(' id=InziFORM');
	document.write(' name=InziFORM');
	document.write(' width=0%');
	document.write(' height=0%');
	document.write('  classid="clsid:91064958-2574-4BB3-B236-61C44E6A88DF"');
	document.write(' codebase="'+Server_Path+Path+'/Install/Download/SKT_InziiFormSetup_UK_Win7.cab#version=2,0,0,1">');
	document.write(' </object>');
	
	//USACN viewer
	document.write(' <object ');
	document.write(' id=InziViewer_R');
	document.write(' name=InziViewer_R');
	document.write(' width=0%');
	document.write(' height=0%');
	document.write('  classid="clsid:E4BB38C3-21C1-4781-AE63-971E8BAA97DD"');
	document.write(' codebase="'+Server_Path+Path+'/Install/Download/InziViewer_R.ocx#version=1,0,0,0">');
	document.write(' </object>');	
	*/
	
	//  XPLATFORM Engine
	document.write('<OBJECT ID="XPlatformAX" CLASSID="CLSID:7E0D6AB0-6E1E-441C-969A-CD85DCA58DFB" width="0" height="0" '
   		+ 'CodeBase="'+Server_Path+Path+'/Install/Download/XPLATFORM9.2.1_SetupEngine.cab#VERSION='+XPLATFORM_CAB_VER+'" onError="fn_objectOnError(this)">'
   		+ '</OBJECT>');

	//  XPLATFORM Launcher
	document.write('<OBJECT ID="XLauncher" CLASSID="CLSID:A30D5481-7381-4dd9-B0F4-0D1D37449E97" width="0" height="0" '
     	+ 'codebase="'+Server_Path+Path+'/Install/Download/XPLATFORM9.2.1_XPLauncher.cab#VERSION='+XPLATFORM_CAB_VER+'" onError="fn_objectOnError(this)">'
   		+ '<PARAM NAME="key" VALUE="' + sKey + '">'
   		+ '</OBJECT>');
	   

} else {
	//Chrom에서 실행시 
	//  XPLATFORM Launcher 
	document.write('<embed ID="XLauncher" type="application/xplauncher9.2-plugin" width="0" height="0" onError="fn_objectOnError()" ></embed>');
	//Report Designer ActiveX Control
	document.write('<embed id="cxViewer" type="application/x-cxviewer60" width="0" height="0" ></embed>');	
}
</SCRIPT>
<table id="ins_tbl" align="center" border=0 width="600">
  <tr>
    <td><table width="637" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td height="70"></td>
        </tr>
        <tr>
          <td><img src="img/insu_down_listbg1.gif" width="637" height="11"></td>
        </tr>
        <tr>
          <td valign="top" bgcolor="#EEEEEE"> <table width="607" border="0" align="center" cellpadding="0" cellspacing="0">
              
              <tr>
                <td height="120"><table width="590" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td rowspan="3"><img src="img/insu_down_img.gif"></td>
                      <td width="590" height="40"><img src="img/insu_down_title.gif" ></td>
                    </tr>
                    <tr>
                      <td height="20"><img src="img/insu_down_title2.gif"></td>
                    </tr>
                  </table></td>
              </tr>
              
              <tr>
                <td><table width="607" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="12" height="12"><img src="img/insu_down_listbg_sub1.gif" width="12" height="12"></td>
                      <td width="583" height="12" bgcolor="#FFFFFF"></td>
                      <td width="12" height="12"><img src="img/insu_down_listbg_sub2.gif" width="12" height="12"></td>
                    </tr>
		    <tr>
                      <td width="12"  bgcolor="#FFFFFF"></td>
                      <td width="583" height="20" bgcolor="#FFFFFF"></td>
                      <td width="12" bgcolor="#FFFFFF"></td>
                    </tr>
                    <tr>
                      <td rowspan="5" bgcolor="#FFFFFF">&nbsp;</td>
                      <td bgcolor="#FFFFFF"><table width="550" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
                            <td width="10" valign="top"><img src="img/insu_down_list_icon1.gif" width="8" height="11"></td>
                            <td width="540" class="length">암호화 프로그램 설치여부를 묻는 보안경고창이 나타나면 반드시
                              “<strong><font color="#008a00">예</font></strong>”를 
				선택하여주시기 바랍니다.<br>
                              "아니오"를 선택하시면 보안을 위해 사용이 제한됩니다.</td>
                          </tr>
                        </table></td>
                      <td rowspan="5" bgcolor="#FFFFFF">&nbsp;</td>
                    </tr>
                    <tr>
                      <td bgcolor="#FFFFFF">&nbsp;</td>
                    </tr>
                    <tr>
                      <td bgcolor="#FFFFFF"><table width="550" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
                            <td width="10" valign="top"><img src="img/insu_down_list_icon1.gif" width="8" height="11"></td>
                            <td width="540" class="length">윈도우 XP 서비스팩2 사용자께서는 주소 표시줄 아래 경고문구“ <img src="img/insu_down_list_icon2.gif" width="15" height="15" align="absmiddle">"이
                      사이트에서...여기를 클릭하십시오" 를 선택하시어, Active X컨트롤을 설치하시기 바랍니다.</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td bgcolor="#FFFFFF">&nbsp;</td>
                    </tr>
                    <tr>
			<td bgcolor="#FFFFFF"><table width="550" border="0" align="center" cellpadding="0" cellspacing="0">
						<tr>
                            				<td width="10" valign="top"><img src="img/insu_down_list_icon1.gif" width="8" height="11"></td>
                           				 <td width="540" class="length">프로그램 설치가 정상적이지 않을 경우에는 문의하기</font></strong>를 통해 질문
                      하시면 신속히 답변을 <br>드리겠습니다.</td>
                         			 </tr>
						 <tr>
                      					<td bgcolor="#FFFFFF">&nbsp;</td>
                    				</tr>						  
									  
                        </table></td>
                    </tr>
			
		<tr>
                      <td width="12"  bgcolor="#FFFFFF"></td>
                      <td width="583" height="5" bgcolor="#FFFFFF"></td>
                      <td width="12" bgcolor="#FFFFFF"></td>
                    </tr>
		<tr>
                      <td width="12" height="12"><img src="img/insu_down_listbg_sub3.gif" width="12" height="12"></td>
                      <td bgcolor="#FFFFFF"></td>
                      <td width="12" height="12"><img src="img/insu_down_listbg_sub4.gif" width="12" height="12"></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td><img src="img/insu_down_listbg2.gif" width="637" height="11"></td>
        </tr>
        <tr>
          <td height="5"></td>
        </tr>
      </table></td>
  </tr>
</table>


</BODY>
</HTML>
