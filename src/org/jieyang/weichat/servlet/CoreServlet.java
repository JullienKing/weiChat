package org.jieyang.weichat.servlet;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;	
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CoreServlet extends HttpServlet {
	
	String sToken = "TGVUGxewmn4HRc";
	String sCorpID = "wxda31e91f8727b131";
	String sEncodingAESKey = "96IkS5HDCELAEVnHJ2Lq71jKhti6NfDr27gkt6nVZP0";
	//https://59.61.205.102/weiChat/CoreServlet
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		WXBizMsgCrypt wxCrypt = null;
		try {
			wxCrypt = new WXBizMsgCrypt(sToken,sEncodingAESKey, sCorpID);
		} catch (AesException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		// 解析出url上的参数值如下：
		String sVerifyMsgSig = request.	getParameter("msg_signature");
		//ring sVerifyMsgSig = "5c45ff5e21c57e6ad56bac8758b79b1d9ac89fd3";
		String sVerifyTimeStamp = request.getParameter("timestamp");
		//ring sVerifyTimeStamp = "1409659589";
		String sVerifyNonce = request.getParameter("nonce");
		//ring sVerifyNonce = "263014780";
		String sVerifyEchoStr = request.getParameter("echostr");
		//ring sVerifyEchoStr = "P9nAzCzyDtyTWESHep1vC5X9xho/qYX3Zpb4yKa9SKld1DsH3Iyt3tP3zNdtp+4RPcs8TgAE7OaBO+FZXvnaqQ==";
		String sEchoStr; //需要返回的明文
		System.out.println("sVerifyMsgSig:" + sVerifyMsgSig);
		System.out.println("sVerifyTimeStamp:" + sVerifyTimeStamp);
		System.out.println("sVerifyNonce:" + sVerifyNonce);
		System.out.println("sVerifyEchoStr:" + sVerifyEchoStr);

		
		try {
			sEchoStr = wxCrypt.VerifyURL(sVerifyMsgSig, sVerifyTimeStamp,
					sVerifyNonce, sVerifyEchoStr);
			System.out.println("verifyurl echostr: " + sEchoStr);
			// 验证URL成功，将sEchoStr返回
			response.getWriter().print(sEchoStr);
		} catch (Exception e) {
			//验证URL失败，错误原因请查看异常
			e.printStackTrace();
		}
		
	}
	
}
