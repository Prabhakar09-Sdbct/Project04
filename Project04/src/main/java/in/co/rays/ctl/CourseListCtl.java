package in.co.rays.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.bean.CourseBean;
import in.co.rays.model.CourseModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.ServletUtility;

@WebServlet(name = "CourseListCtl", urlPatterns = "/CourseListCtl")
public class CourseListCtl extends BaseCtl {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int pageNo = 1;
		int pageSize = 10;

		CourseBean bean = null;
		CourseModel model = new CourseModel();
		try {
			List list = model.search(bean, pageNo, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", req);
			}
			ServletUtility.setList(list, req);
			ServletUtility.setPageNo(pageNo, req);
			ServletUtility.setPageSize(pageSize, req);
			ServletUtility.forward(getView(), req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String op = DataUtility.getStringData(req.getParameter("operation"));
		
		int pageNo = DataUtility.getInt(req.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(req.getParameter("pageSize"));
	
		
		if(op.equalsIgnoreCase(CourseCtl.OP_PREVIOUS)) {
			
		}
		
		ServletUtility.forward(getView(), req, resp);
		
		
	}

	@Override
	protected String getView() {
		return ORSView.COURSE_LIST_VIEW;
	}

}
