package in.co.rays.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.rays.bean.BaseBean;
import in.co.rays.bean.TaskBean;
import in.co.rays.bean.UserBean;
import in.co.rays.exception.ApplicationException;
import in.co.rays.exception.DuplicateRecordException;
import in.co.rays.model.TaskModel;
import in.co.rays.model.UserModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.DataValidator;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

@WebServlet(name = "TaskCtl", urlPatterns = { "/TaskCtl" })
public class TaskCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("title"))) {
			request.setAttribute("title", PropertyReader.getValue("error.require", "Tile"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("details"))) {
			request.setAttribute("details", PropertyReader.getValue("error.require", "Details"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("assignedTo"))) {
			request.setAttribute("assignedTo", PropertyReader.getValue("error.require", "Assigned To"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("status"))) {
			request.setAttribute("status", PropertyReader.getValue("error.require", "Status"));
			pass = false;
		}
		
		return pass;
	}

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
		Long id = DataUtility.getLong(request.getParameter("id"));

		if (id > 0) {

			TaskModel model = new TaskModel();

			try {
				TaskBean bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		TaskBean bean = (TaskBean) populateBean(request);

		// get model
		TaskModel model = new TaskModel();

		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			try {
				if (id > 0) {
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Data is Successfully Updated ", request);
				} else {
					model.add(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Data is Successfully Added ", request);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TASK_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.TASK_LIST_CTL, request, response);
			return;
		}
		ServletUtility.setBean(bean, request);
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected String getView() {
		return ORSView.TASK_VIEW;
	}

}
