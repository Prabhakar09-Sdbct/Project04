package in.co.rays.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.rays.bean.BaseBean;
import in.co.rays.bean.RoleBean;
import in.co.rays.bean.UserBean;
import in.co.rays.model.RoleModel;
import in.co.rays.model.UserModel;
import in.co.rays.util.DataUtility;
import in.co.rays.util.DataValidator;
import in.co.rays.util.PropertyReader;
import in.co.rays.util.ServletUtility;

@WebServlet(name = "LoginCtl", urlPatterns = "/LoginCtl")
public class LoginCtl extends BaseCtl {

	public static final String OP_SIGN_IN = "Sign In";
	public static final String OP_SIGN_UP = "Sign Up";

	@Override
	protected boolean validate(HttpServletRequest request) {
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		boolean pass = true;
		
		if(OP_LOG_OUT.equalsIgnoreCase(op)) {
			return pass;
		}

		if (DataValidator.isNull(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("login"))) {
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		}

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		UserBean bean = new UserBean();
		bean.setLogin(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		ServletUtility.forward(getView(), req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String op = DataUtility.getString(req.getParameter("operation"));

		System.out.println(req.getParameter("operation"));

		if (OP_SIGN_IN.equalsIgnoreCase(op)) {
			System.out.println(" working");
			UserBean bean = (UserBean) populateBean(req);

			UserModel model = new UserModel();
			RoleModel roleModel = new RoleModel();
			
			HttpSession session = req.getSession();

			try {
				bean = model.authenticate(bean.getLogin(), bean.getPassword());
				if (bean != null) {
					session.setAttribute("user", bean);
					long rollId = bean.getRoleId();
						
					RoleBean rolebean = roleModel.findByPk(bean.getRoleId());
					if (rolebean != null) {
						System.out.println(" role "+rolebean.getName());
						session.setAttribute("role", rolebean.getName());
					}
					ServletUtility.redirect(ORSView.WELCOME_CTL, req, resp);
				} else {
					req.setAttribute("msg", "login id & password invalid");
					ServletUtility.setBean(bean, req);
					ServletUtility.forward(getView(), req, resp);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	protected String getView() {

		return ORSView.LOGIN_VIEW;
	}

}
