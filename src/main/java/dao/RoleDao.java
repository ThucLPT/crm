package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.MySQLConnection;
import model.Role;

public class RoleDao {
	public List<Role> findAll() {
		List<Role> list = new ArrayList<>();
		Connection connection = MySQLConnection.getConnection();
		String sql = "select * from role";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Role role = new Role();
				role.setId(resultSet.getInt("id"));
				role.setName(resultSet.getString("name"));
				role.setDescription(resultSet.getString("description"));
				list.add(role);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public Role findById(int id) {
		Role role = null;
		Connection connection = MySQLConnection.getConnection();
		String sql = "select * from role where id = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				role = new Role();
				role.setId(id);
				role.setName(resultSet.getString("name"));
				role.setDescription(resultSet.getString("description"));
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return role;
	}
}
