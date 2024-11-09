package in.co.rays.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.bean.BaseBean;
import in.co.rays.bean.TaskBean;
import in.co.rays.bean.TaskBean;
import in.co.rays.model.TaskModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

@WebServlet(name = "TaskListCtl", urlPatterns = "/TaskListCtl")
public class TaskListCtl extends BaseCtl {
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		TaskBean bean = new TaskBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setTitle(DataUtility.getString(request.getParameter("title")));
		bean.setDetails(DataUtility.getString(request.getParameter("details")));
		bean.setAssignedTo(DataUtility.getString(request.getParameter("assignedTo")));
		bean.setStatus(DataUtility.getString(request.getParameter("status")));
		populateDTO(bean, request);
		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		System.out.println(" page "+pageSize);

		TaskBean bean = null;
		TaskModel model = new TaskModel();

		try {
			List list = model.search(bean, pageNo, pageSize);
			List next = model.search(bean, pageNo + 1, pageSize);

			req.setAttribute("nextListSize", next.size());
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", req);
			}
			ServletUtility.setList(list, req);
			ServletUtility.setPageNo(pageNo, req);
			ServletUtility.setPageSize(pageSize, req);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ServletUtility.forward(getView(), req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<TaskBean> list = null;
		List<TaskBean> next = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

		TaskBean bean = (TaskBean) populateBean(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");

		TaskModel model = new TaskModel();

		try {
			if (OP_SEARCH.equalsIgnoreCase(op)) {
				pageNo = 1;
			} else if (OP_NEXT.equalsIgnoreCase(op)) {
				pageNo++;
			} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
				pageNo--;
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.TASK_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					for (String id : ids) {
						try {
							System.out.println("id"+id);
							model.delete(DataUtility.getLong(id));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					ServletUtility.setSuccessMessage("Data is deleted successfully", request);
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			} else if (OP_RESET.equalsIgnoreCase(op) || OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.TASK_LIST_CTL, request, response);
				return;
			}

			ServletUtility.setBean(bean, request);

			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);

			if (!OP_DELETE.equalsIgnoreCase(op) && (list == null || list.size() == 0)) {
				ServletUtility.setErrorMessage("No record found", request);
			}
			
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}

			request.setAttribute("nextListSize", next.size());
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.TASK_LIST_VIEW;
	}

}
