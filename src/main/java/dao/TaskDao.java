package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.MySQLConnection;
import model.Task;

public class TaskDao {
	private UserDao userDao = new UserDao();
	private JobDao jobDao = new JobDao();
	private StatusDao statusDao = new StatusDao();

	public Task findById(int id) {
		Task task = null;
		Connection connection = MySQLConnection.getConnection();
		String sql = "select * from task where id = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				task = new Task();
				task.setId(id);
				task.setName(resultSet.getString("name"));
				task.setStartDate(resultSet.getDate("start_date").toLocalDate());
				task.setEndDate(resultSet.getDate("end_date").toLocalDate());
				task.setMember(userDao.findById(resultSet.getInt("user_id")));
				task.setJob(jobDao.findById(resultSet.getInt("job_id")));
				task.setStatus(statusDao.findById(resultSet.getInt("status_id")));
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return task;
	}

	public List<Task> findByJobId(int id) {
		List<Task> list = new ArrayList<>();
		Connection connection = MySQLConnection.getConnection();
		String sql = "select * from task where job_id = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Task task = new Task();
				task.setId(resultSet.getInt("id"));
				task.setName(resultSet.getString("name"));
				task.setStartDate(resultSet.getDate("start_date").toLocalDate());
				task.setEndDate(resultSet.getDate("end_date").toLocalDate());
				task.setMember(userDao.findById(resultSet.getInt("user_id")));
				task.setJob(jobDao.findById(id));
				task.setStatus(statusDao.findById(resultSet.getInt("status_id")));
				list.add(task);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<Task> findByUserId(int id) {
		List<Task> list = new ArrayList<>();
		Connection connection = MySQLConnection.getConnection();
		String sql = "select * from task where user_id = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Task task = new Task();
				task.setId(resultSet.getInt("id"));
				task.setName(resultSet.getString("name"));
				task.setStartDate(resultSet.getDate("start_date").toLocalDate());
				task.setEndDate(resultSet.getDate("end_date").toLocalDate());
				task.setMember(userDao.findById(id));
				task.setJob(jobDao.findById(resultSet.getInt("job_id")));
				task.setStatus(statusDao.findById(resultSet.getInt("status_id")));
				list.add(task);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
