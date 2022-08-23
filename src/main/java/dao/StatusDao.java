package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.MySQLConnection;
import model.Status;

public class StatusDao {
	public Status findById(int id) {
		Status status = null;
		Connection connection = MySQLConnection.getConnection();
		String sql = "select * from status where id = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(id, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				status = new Status();
				status.setName(resultSet.getString("name"));
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
}
