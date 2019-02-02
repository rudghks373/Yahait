package com.yahait.app.Controller;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.yahait.app.Dao.IDao;
import com.yahait.app.Dao.SDao;
import com.yahait.app.Dto.ItemDto;
import com.yahait.app.Dto.MemberDto;
import com.yahait.app.Dto.ShopDto;

@Controller
public class ShopController {
	@Autowired
	private SqlSession sqlSession;

	@RequestMapping("/Order")
	public String Order(Model model) {
		return "Order";
	}

	@RequestMapping("/Sell")
	public String Sell(Model model, HttpSession session, HttpServletResponse response) throws IOException {
		String logincheckstring = (String) session.getAttribute("iogincheck");
		if (logincheckstring == null) {
			System.out.println("로그인세션 없음");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('잘못된 접근'); window.location.href = \"Login\";</script>");
			out.flush();
		} else {
			System.out.println("Login Session : " + logincheckstring);
		}
		return "Sell";
	}

	@RequestMapping("/Shop")
	public String Shop(Model model, HttpSession session, @RequestBody String paramData, ServletRequest request)
			throws IOException, ParseException {

		System.out.println("상점 컨트롤러 접속");
		// 클라이언트측에서 날라온 데이터확인
		System.out.println("클라이언트전송데이터(JSON):" + paramData);
		// JSON객체를 생성하여 키&벨류 값으로 쪼개기
		String shop_num = request.getParameter("shop_num");
		SDao dao = sqlSession.getMapper(SDao.class);
		ArrayList<ShopDto> Shop_info = dao.Shop_info(shop_num);
		String member_num = Shop_info.get(0).getMember_num();
		String shop_name = Shop_info.get(0).getShop_name();
		String category_name1 = Shop_info.get(0).getCategory_name1();
		String category_name2 = Shop_info.get(0).getCategory_name2();
		int state = Shop_info.get(0).getState();

		System.out.println("상점 이름 :" + shop_name + " member_num :" + member_num);
		model.addAttribute("shop_name", shop_name);
		model.addAttribute("member_num", member_num);
		model.addAttribute("category_name1", category_name1);
		model.addAttribute("category_name2", category_name2);
		return "Shop";
	}

	@RequestMapping("/SellAct")
	@ResponseBody
	public String SellAct(Model model, HttpSession session, HttpServletResponse response, @RequestBody String paramData)
			throws IOException, ParseException {
		System.out.println("상품등록 컨트롤러 접속");
		// 클라이언트측에서 날라온 데이터확인
		String logincheckstring = (String) session.getAttribute("iogincheck");
		if (logincheckstring == null) {
			System.out.println("로그인세션 없음");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('잘못된 접근'); window.location.href = \"Login\";</script>");
			out.flush();
		} else {
			System.out.println("세션 존재");
		}
		System.out.println("클라이언트전송데이터(JSON):" + paramData);
		// JSON객체를 생성하여 키&벨류 값으로 쪼개기
		JSONParser parser = new JSONParser(); // –JSON Parser 생성
		JSONObject jsonObj = (JSONObject) parser.parse(paramData); // – 넘어온 문자열을 JSON 객체로 변환
		// JSON데이값을 스트링 객체로 저장
		Map map = new HashMap();
		SDao dao = sqlSession.getMapper(SDao.class);
		ShopDto dto = dao.Shop_admin(logincheckstring);
		map.put("member_num", dto.getMember_num());
		map.put("shop_name", jsonObj.get("title").toString());
		map.put("category1", jsonObj.get("category1").toString());
		map.put("category2", jsonObj.get("category2").toString());
		// 클라이언트측에서 날라온 JSON데이터를 서버측에서 받은 데이터 확인
		System.out.println("서버측 받은 데이터 ");
		System.out.println("TITLE:" + map.get("shop_name") + " CATEGORY:" + map.get("category1") + "CATEGORY:"
				+ map.get("category2"));
		try {
			SDao dao1 = sqlSession.getMapper(SDao.class);
			dao1.Shop_add(map);
		} catch (Exception e) {
			return "ERROR";
		}
		return "OK";
	}

	@RequestMapping("/Product")
	public String Product(Model model) {
		return "Product";
	}

	@RequestMapping("/Manager")
	public String Manager(Model model, HttpSession session, HttpServletResponse response) throws IOException {
		String logincheckstring = (String) session.getAttribute("iogincheck");
		if (logincheckstring == null) {
			System.out.println("로그인세션 없음");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>alert('잘못된 접근'); window.location.href = \"Login\";</script>");
			out.flush();
		} else {
			System.out.println("Login Session : " + logincheckstring);
		}
		SDao dao = sqlSession.getMapper(SDao.class);
		ShopDto dto = dao.Shop_admin(logincheckstring);
		String member_num = dto.getMember_num();
		SDao dao1 = sqlSession.getMapper(SDao.class);
		ArrayList<ShopDto> list = dao1.Shop_list(member_num);

		/*
		 * //최종 완성될 JSONObject 선언(전체) JSONObject ShopObject = new JSONObject();
		 * 
		 * //Shop의 JSON정보를 담을 Array 선언 JSONArray ShopArray = new JSONArray();
		 * 
		 * //person의 한명 정보가 들어갈 JSONObject 선언
		 * 
		 * 
		 * for(int i=0;i<list.size();i++) { JSONObject ShopInfo = new JSONObject();
		 * ShopInfo.put("shop_num", list.get(i).getShop_num());
		 * ShopInfo.put("shop_name",list.get(i).getShop_name());
		 * ShopInfo.put("shop_category_name1", list.get(i).getCategory_name1());
		 * ShopInfo.put("shop_category_name2", list.get(i).getCategory_name2());
		 * ShopInfo.put("state", list.get(i).getState()); ShopArray.add(ShopInfo); }
		 * String[] jsonlist= new String[ShopArray.size()]; for(int
		 * i=0;i<ShopArray.size();i++) { JSONObject info = (JSONObject)ShopArray.get(i);
		 * jsonlist[i]=info.toJSONString(); System.out.println("json 정보"+jsonlist[i]); }
		 */
		model.addAttribute("shop_data", list);
		return "Manager";
	}

	@RequestMapping("/Test")
	public String Test(Model model) {
		return "Test";
	}

	@RequestMapping("/StateUpdate")
	public String StateUpdate(Model model, HttpSession session, @RequestBody String paramData)
			throws IOException, ParseException {
		System.out.println("State_Update 컨트롤러 접속");
		// 클라이언트측에서 날라온 데이터확인
		System.out.println("클라이언트전송데이터(JSON):" + paramData);
		// JSON객체를 생성하여 키&벨류 값으로 쪼개기
		JSONParser parser = new JSONParser(); // –JSON Parser 생성
		JSONObject jsonObj = (JSONObject) parser.parse(paramData); // – 넘어온 문자열을 JSON 객체로 변환
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("shop_name", jsonObj.get("shop_name").toString().trim());
		map.put("state", jsonObj.get("shop_state").toString().trim());
		
		try {
		SDao dao = sqlSession.getMapper(SDao.class);
		dao.StateUpdate(map);
		System.out.println("상점 번호: "+map.get("shop_name")+"상태"+map.get("state"));
		System.out.println("UPDATE SUCCESS");
		}catch(Exception e) {
			System.out.println("SQL 에러");
		}
		return "EX";
	}
	
}