package in.co.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.co.rays.bean.ShoppingCartBean;
import in.co.rays.util.JDBCDataSourceRb;

public class ShoppingCartModel {

	public Integer nextPk() throws Exception {

		Connection con = JDBCDataSourceRb.getConnection();
		PreparedStatement psmt = con.prepareStatement("select max(id) from st_shopping_cart");

		ResultSet rs = psmt.executeQuery();

		int pk = 0;

		while (rs.next()) {
			pk = rs.getInt(1);
		}
		JDBCDataSourceRb.closeConnection(con);
		return pk + 1;
	}

	public void add(ShoppingCartBean bean) throws Exception {
		Connection con = JDBCDataSourceRb.getConnection();
		PreparedStatement psmt = con.prepareStatement("insert into st_shopping_cart values(?,?,?,?,?,?,?,?,?)");

		psmt.setLong(1, nextPk());
		psmt.setString(2, bean.getName());
		psmt.setString(3, bean.getProduct());
		psmt.setTimestamp(4, new Timestamp(bean.getDate().getTime()));
		psmt.setInt(5, bean.getQuantity());
		psmt.setString(6, bean.getCreatedBy());
		psmt.setString(7, bean.getModifiedBy());
		psmt.setTimestamp(8, bean.getCreatedDateTime());
		psmt.setTimestamp(9, bean.getModifiedDateTime());

		int i = psmt.executeUpdate();

		JDBCDataSourceRb.closeConnection(con);
		System.out.println("Data added " + i);
	}

	public void update(ShoppingCartBean bean) throws Exception {
		Connection con = JDBCDataSourceRb.getConnection();
		
		StringBuffer sql = new StringBuffer("update st_shopping_cart set name = ?, product = ?, `date` = ?, quantity = ?, "
				+ "created_by =?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");
		
		System.out.println("sql update is : "+sql.toString());
		PreparedStatement psmt = con
				.prepareStatement(sql.toString());

		psmt.setString(1, bean.getName());
		psmt.setString(2, bean.getProduct());
		if (bean.getDate() != null) {
		    psmt.setTimestamp(3, new Timestamp(bean.getDate().getTime()));
		} else {
		    psmt.setTimestamp(3, null);
		}
		psmt.setInt(4, bean.getQuantity());
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
		PreparedStatement psmt = con.prepareStatement("delete from st_shopping_cart where id = ?");

		psmt.setLong(1, id);

		int i = psmt.executeUpdate();

		JDBCDataSourceRb.closeConnection(con);
		System.out.println("Data deleted " + i);
	}

	public ShoppingCartBean findByPK(long id) throws Exception {
		Connection con = JDBCDataSourceRb.getConnection();
		PreparedStatement psmt = con.prepareStatement("select * from st_shopping_cart where id = ?");

		psmt.setLong(1, id);

		ShoppingCartBean bean = null;
		ResultSet rs = psmt.executeQuery();

		while (rs.next()) {
			bean = new ShoppingCartBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setProduct(rs.getString(3));
		    bean.setDate(rs.getTimestamp(4));
			bean.setQuantity(rs.getInt(5));
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

	public List search(ShoppingCartBean bean, int pageNo, int pageSize) throws Exception {
		Connection con = JDBCDataSourceRb.getConnection();

		StringBuffer sql = new StringBuffer("select * from st_shopping_cart where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id =" + bean.getId());
			}

			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name ='" + bean.getName() + "%'");
			}

			if (bean.getProduct() != null && bean.getProduct().length() > 0) {
				sql.append(" and product ='" + bean.getProduct() + "%'");
			}

			if (bean.getDate() != null) {
				sql.append(" and date ='" + bean.getDate() + "%'");
			}

			if (bean.getQuantity() > 0) {
				sql.append(" and quantity =" + bean.getQuantity());
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
			bean = new ShoppingCartBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setProduct(rs.getString(3));
			bean.setDate(rs.getTimestamp(4));
			bean.setQuantity(rs.getInt(5));
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
