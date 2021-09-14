package webEx;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/member/*")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
		MemberDAO memberDAO;//MemberDAO ��ü �ʱ�ȭ
		
	public void init() throws ServletException { //MemberDAO ��ü �ʱ�ȭ
		memberDAO = new MemberDAO();//memberDAO�� ����
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			doHandle(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
	doHandle(request, response);
	}
	
	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = null;
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String action = request.getPathInfo(); //URL���� ��û���� ������
		System.out.println("action:" + action);
		
		if (action == null || action.equals("/listMembers.do")) { //���� ��û�̰ų� action ���� /memberList.do �� ȸ�� ����� ���
			List<MemberVO> membersList = memberDAO.listMembers();
			request.setAttribute("membersList", membersList);
			nextPage = "/listMembers.jsp";//test02������ listMember.jsp�� ������
			//nextPage = "/webapp/listMembers.jsp";//test02������ listMember.jsp�� ������
		} else if (action.equals("/addMember.do")) { //action ���� /addMember.do�� ���۵� ȸ�� ������ �����ͼ� ���̺� �߰�
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			MemberVO memberVO = new MemberVO(id, pwd, name, email);
			memberDAO.addMember(memberVO);
			nextPage = "/member/listMembers.do";//ȸ�� ��� �� �ٽ� ȸ�� ����� ���
		} else if (action.equals("/memberForm.do")) {//action ���� /memberForm.do�� ȸ�� ����â�� ȭ�鿡 ���
			nextPage = "/memberForm.jsp"; //test02������ memberForm.jsp�� ������
		}else if(action.equals("/modMemberForm.do")){ //ȸ�� ����â ��û �� ID�� ȸ�� ������ ��ȸ�� �� ����â���� ������
			String id=request.getParameter("id");
			MemberVO memInfo = memberDAO.findMember(id); //ȸ�� ���� ����â�� ��û�ϸ鼭 ���۵� ID�� �̿��� ���� �� ȸ�� ���� ��ȸ
			request.setAttribute("memInfo", memInfo);//request�� ���ε��Ͽ� ȸ�� ���� ����â�� �����ϱ� �� ȸ�� ������ ����
			nextPage="/modMemberForm.jsp";
		}else if(action.equals("/modMember.do")){//���̺��� ȸ�� ���� ����
			String id=request.getParameter("id");
			String pwd=request.getParameter("pwd");
			String name= request.getParameter("name");
			String email= request.getParameter("email");
			//���۵� ����ȸ�� ������ ������ �� MemberVO ��ü �Ӽ��� ����
			MemberVO memberVO = new MemberVO(id, pwd, name, email);
			memberDAO.modMember(memberVO);
			request.setAttribute("msg", "modified"); //ȸ�� ���â���� �����۾��Ϸ� �޽��� ����
			nextPage="/member/listMembers.do"; 
		}else if(action.equals("/delMember.do")){//ȸ��ID�� SQL������ ������ ���̺��� ȸ�� ������ ����
			String id=request.getParameter("id");
			memberDAO.delMember(id);
			request.setAttribute("msg", "deleted"); //ȸ�� ���â���� ���� �۾� �Ϸ� �޽��� ����
			nextPage="/member/listMembers.do";
		}
		else {//�� �� �ٸ� action���� ȸ�� ����� ���
			List<MemberVO> membersList = memberDAO.listMembers();
			request.setAttribute("membersList", membersList);
			nextPage = "/listMembers.jsp";
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage); //nextPage�� ������ ��û������ �ٽ� ������ ��û
		dispatch.forward(request, response);
		}

}
	

