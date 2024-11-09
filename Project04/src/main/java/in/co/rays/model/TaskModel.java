package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.bean.TaskBean;
import in.co.rays.util.JDBCDataSourceRb;

public class TaskModel {

	public Integer nextPk() throws Exception {

		Connection con = JDBCDataSourceRb.getConnection();
		PreparedStatement psmt = con.prepareStatement("select max(id) from st_task");

		ResultSet rs = psmt.executeQuery();

		int pk = 0;

		while (rs.next()) {
			pk = rs.getInt(1);
		}
		JDBCDataSourceRb.closeConnection(con);
		return pk + 1;
	}

	public void add(TaskBean bean) throws Exception {
		Connection con = JDBCDataSourceRb.getConnection();
		PreparedStatement psmt = con.prepareStatement("insert into st_task values(?,?,?,?,?,?,?,?,?)");

		psmt.setLong(1, nextPk());
		psmt.setString(2, bean.getTitle());
		psmt.setString(3, bean.getDetails());
		psmt.setString(4, bean.getAssignedTo());
		psmt.setString(5, bean.getStatus());
		
		psmt.setString(6, bean.getCreatedBy());
		psmt.setString(7, bean.getModifiedBy());
		psmt.setTimestamp(8, bean.getCreatedDateTime());
		psmt.setTimestamp(9, bean.getModifiedDateTime());

		int i = psmt.executeUpdate();

		JDBCDataSourceRb.closeConnection(con);
		System.out.println("Data added " + i);
	}

	public void update(TaskBean bean) throws Exception {
		Connection con = JDBCDataSourceRb.getConnection();
		
		StringBuffer sql = new StringBuffer("update st_task set title = ?, details = ?, assigned_to = ?, status = ?, "
				+ "created_by =?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
		
		System.out.println("sql update is : "+sql.toString());
		PreparedStatement psmt = con
				.prepareStatement(sql.toString());

		psmt.setString(1, bean.getTitle());
		psmt.setString(2, bean.getDetails());
		psmt.setString(3, bean.getAssignedTo());
		psmt.setString(4, bean.getStatus());
		
		psmt.setString(5, bean.getCreatedBy());
		psmt.setString(6, bean.getModifiedBy());
		psmt.setTimestamp(7, bean.getCreatedDateTime());
		psmt.setTimestamp(8, bean.getModifiedDateTime());
		psmt.setLong(9, bean.getId());

		int i = psmt.executeUpdate();

		JDBCDataSourceRb.closeConnection(con);
		System.out.println("Data update " + i);
	}

	public void delete(long id) throws Exception {
		Connection con = JDBCDataSourceRb.getConnection();
		PreparedStatement psmt = con.prepareStatement("delete from st_task where id = ?");

		psmt.setLong(1, id);

		int i = psmt.executeUpdate();

		JDBCDataSourceRb.closeConnection(con);
		System.out.println("Data deleted " + i);
	}

	public TaskBean findByPK(long id) throws Exception {
		Connection con = JDBCDataSourceRb.getConnection();
		PreparedStatement psmt = con.prepareStatement("select * from st_task where id = ?");

		psmt.setLong(1, id);

		TaskBean bean = null;
		ResultSet rs = psmt.executeQuery();

		while (rs.next()) {
			bean = new TaskBean();
			bean.setId(rs.getLong(1));
			bean.setTitle(rs.getString(2));
			bean.setDetails(rs.getString(3));
			bean.setAssignedTo(rs.getString(4));
			bean.setStatus(rs.getString(5));
			
			bean.setCreatedBy(rs.getString(6));
			bean.setModifiedBy(rs.getString(7));
			bean.setCreatedDateTime(rs.getTimestamp(8));
			bean.setModifiedDateTime(rs.getTimestamp(9));
		}

		JDBCDataSourceRb.closeConnection(con);
		return bean;
	}

	public List list() throws Exception {
		return search(null, 0, 0);
	}

	public List search(TaskBean bean, int pageNo, int pageSize) throws Exception {
		Connection con = JDBCDataSourceRb.getConnection();

		StringBuffer sql = new StringBuffer("select * from st_task where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id =" + bean.getId());
			}

			if (bean.getTitle() != null && bean.getTitle().length() > 0) {
				sql.append(" and title like'" + bean.getTitle() + "%'");
			}

			if (bean.getDetails() != null && bean.getDetails().length() > 0) {
				sql.append(" and details like'" + bean.getDetails() + "%'");
			}

			if (bean.getAssignedTo() != null && bean.getAssignedTo().length() > 0) {
				sql.append(" and assigned_to like'" + bean.getAssignedTo() + "%'");
			}

			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" and status like'" + bean.getStatus() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		System.out.println(" search sql :>" + sql.toString());

		PreparedStatement psmt = con.prepareStatement(sql.toString());

		ResultSet rs = psmt.executeQuery();
		List list = new ArrayList();
		while (rs.next()) {
			bean = new TaskBean();
			bean.setId(rs.getLong(1));
			bean.setTitle(rs.getString(2));
			bean.setDetails(rs.getString(3));
			bean.setAssignedTo(rs.getString(4));
			bean.setStatus(rs.getString(5));
			
			bean.setCreatedBy(rs.getString(6));
			bean.setModifiedBy(rs.getString(7));
			bean.setCreatedDateTime(rs.getTimestamp(8));
			bean.setModifiedDateTime(rs.getTimestamp(9));
			list.add(bean);
		}

		JDBCDataSourceRb.closeConnection(con);
		return list;
	}
}
