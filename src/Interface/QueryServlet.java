package Interface;
import Etsy.*;
import Item.ItemDetail;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;
import Classes.Document;
import Classes.Query;
import IndexingLucene.MainLucene;
import IndexingLucene.MyIndexReader;
import PreprocessData.processSearchResult;
import PseudoRFSearch.ExtractTop10;
import PseudoRFSearch.PseudoRFRetrievalModel;
import Search.ExtractQuery;



@WebServlet("/Query")
public class QueryServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public QueryServlet(){
		super();
	}
	public void processRequest(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		response.setContentType("text/html; charset=UTF-8");
		
		Parameter parameter = new Parameter();
		
		parameter.setSearchValue(new String(request.getParameter("SearchInput").getBytes("ISO-8859-1"), "GB2312"));
		
		System.out.println(parameter.getValue());

		HttpSession session = request.getSession();
		session.setAttribute("Parameters", parameter);
		
		String path = getServletContext().getContextPath();
		System.out.println(path);
		//Start to call the Etsy SearchEtsy and SearchResult class
		SearchEtsy searchEtsy = new SearchEtsy(parameter.getValue());
		try{
			String output = searchEtsy.getSearchResult();
			SearchResult searchResult = new SearchResult();
			searchResult.writeResultfile(output);
		}catch(Exception e){
			
		}
		
		// new lines from here
		// process the raw data and record
		processSearchResult processSearch = new processSearchResult();
		HashMap<String, Object> processedMap = (HashMap<String, Object>) processSearch.processResult();

		// create index for search result
		MainLucene index = new MainLucene();
		try {
			index.WriteIndex();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExtractTop10 top = new ExtractTop10();
		// store top10 most highest relevant id in Hashset
		HashMap<Integer, String> top10ID;
		try {
			top10ID = top.getTop10();
			top.writeTop10List(top10ID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//newlines end here

		// generate the result List --Simiao
		ProcessingList process = new ProcessingList();
		List<ItemDetail> list = new ArrayList<ItemDetail>();
		list = process.getList();
		
		for(int i = 0; i< list.size(); i++){
			System.out.println("Sucessfule" + list.get(i).getTitle());
		}
		
		request.setAttribute("list", list);
		request.getRequestDispatcher("list.jsp").forward(request, response);

//		
//		if(session != null && session.getAttribute("Parameters") != null){
//			response.sendRedirect("http://localhost:8080/IR_finalproject/list.jsp");
//			return;
//		}
//		RequestDispatcher dispatcher = request.getRequestDispatcher("Default.jsp");
//		dispatcher.forward(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

}
