package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	
	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement statement = null;
		ResultSet resultStatement = null;
		
		try {
			statement = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName  "
					+ "FROM seller INNER JOIN department  "
					+ "ON seller.DepartmentId = department.Id  "
					+ "WHERE seller.Id = ?");
			
			statement.setInt(1, id);
			resultStatement = statement.executeQuery();
			
			if(resultStatement.next()) {
				Department dep = instatiateDepartment(resultStatement);
				Seller obj = instiateSeller(resultStatement, dep);
				return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(statement);
			DB.closeResultSet(resultStatement);
		}
	}

	private Seller instiateSeller(ResultSet resultStatement, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(resultStatement.getInt("Id"));
		obj.setName(resultStatement.getString("Name"));
		obj.setEmail(resultStatement.getString("Email"));
		obj.setBaseSalary(resultStatement.getDouble("BaseSalary"));
		obj.setBirthDate(resultStatement.getDate("BirthDate"));
		obj.setDepartment(dep);
		
		return obj;
	}

	private Department instatiateDepartment(ResultSet resultStatement) throws SQLException {
		Department dep = new Department();
		dep.setId(resultStatement.getInt("DepartmentId"));
		dep.setName(resultStatement.getString("DepName"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
