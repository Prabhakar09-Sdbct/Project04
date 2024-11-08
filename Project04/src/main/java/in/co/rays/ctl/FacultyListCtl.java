package in.co.rays.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.bean.BaseBean;
import in.co.rays.bean.FacultyBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.model.CollegeModel;
import in.co.rays.model.CourseModel;
import in.co.rays.model.FacultyModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

@WebServlet(name = "FacultyListCtl", urlPatterns = { "/FacultyListCtl" })
public class FacultyListCtl extends BaseCtl {

	@Override
	protected void preload(HttpServletRequest request) {

		CollegeModel cmodel = new CollegeModel();
		CourseModel comodel = new CourseModel();

		try {
			List clist = cmodel.list();
			List colist = comodel.list();

			request.setAttribute("CollegeList", clist);
			request.setAttribute("CourseList", colist);
		} catch (ApplicationException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected BaseBean populateBean(HttpServletRequest request) {

		FacultyBean bean = new FacultyBean();

		bean.setFirstName(DataUtility.getString(request.getParameter("firstname")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastname")));
		bean.setEmail(DataUtility.getString(request.getParameter("login")));
		bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeid")));
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseid")));

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List list;
		List nextList = null;

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		FacultyModel model = new FacultyModel();
		FacultyBean bean = (FacultyBean) populateBean(request);

		String op = DataUtility.getString(request.getParameter("operation"));

		try {
			list = model.search(bean, pageNo, pageSize);

			nextList = model.search(bean, pageNo + 1, pageSize);

			request.setAttribute("nextlist", nextList.size());

			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			e.printStackTrace();
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List list = null;
		List nextList = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		String op = DataUtility.getString(request.getParameter("operation"));

		FacultyBean bean = (FacultyBean) populateBean(request);
		FacultyModel model = new FacultyModel();

		String[] ids = (String[]) request.getParameterValues("ids");

		if (OP_SEARCH.equalsIgnoreCase(op)) {
			pageNo = 1;
		} else if (OP_NEXT.equalsIgnoreCase(op)) {
			pageNo++;
		} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
			if (pageNo > 1) {
				pageNo--;
			} else {
				pageNo = 1;
			}
		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
			return;
		}

		else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length != 0) {
				FacultyBean deletebean = new FacultyBean();
				for (String id : ids) {
					try {
						model.delete(DataUtility.getInt(id));
					} catch (ApplicationException e) {
						e.printStackTrace();
						return;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ServletUtility.setSuccessMessage("Data Deleted Succesfully", request);
				}

			} else {
				ServletUtility.setErrorMessage("Select at least one record", request);
			}
		}
		try {
			list = model.search(bean, pageNo, pageSize);

			nextList = model.search(bean, pageNo + 1, pageSize);

			request.setAttribute("nextlist", nextList.size());

			ServletUtility.setBean(bean, request);
		} catch (ApplicationException e) {
			e.printStackTrace();
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
			ServletUtility.setErrorMessage("No Record Found", request);
		}

		ServletUtility.setList(list, request);
		ServletUtility.setBean(bean, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() {
		return ORSView.FACULTY_LIST_VIEW;
	}

}
