package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.MySQLConnection;
import model.User;

public class UserDao {
	private RoleDao roleDao = new RoleDao();

	public List<User> findAll() {
		List<User> list = new ArrayList<>();
		Connection connection = MySQLConnection.getConnection();
		String sql = "select * from user";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("id"));
				user.setEmail(resultSet.getString("email"));
				user.setPassword(resultSet.getString("password"));
				user.setFullname(resultSet.getString("fullname"));
				user.setRole(roleDao.findById(resultSet.getInt("role_id")));
				list.add(user);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public User findById(int id) {
		User user = null;
		Connection connection = MySQLConnection.getConnection();
		String sql = "select * from user where id = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				user = new User();
				user.setId(id);
				user.setEmail(resultSet.getString("email"));
				user.setPassword(resultSet.getString("password"));
				user.setFullname(resultSet.getString("fullname"));
				user.setRole(roleDao.findById(resultSet.getInt("role_id")));
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	public void save(User user) {
		Connection connection = MySQLConnection.getConnection();
		String sql = "insert into user(email, password, fullname, role_id) values(?, ?, ?, ?)";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFullname());
			statement.setInt(4, user.getRole().getId());
			statement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public User login(String email, String password) {
		User user = null;
		Connection connection = MySQLConnection.getConnection();
		String sql = "select * from user where email = ? and password = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				user = new User();
				user.setId(resultSet.getInt("id"));
				user.setEmail(email);
				user.setPassword(password);
				user.setFullname(resultSet.getString("fullname"));
				user.setRole(roleDao.findById(resultSet.getInt("role_id")));
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
}
