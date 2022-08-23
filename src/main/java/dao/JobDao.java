package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.MySQLConnection;
import model.Job;

public class JobDao {
	private UserDao userDao = new UserDao();

	public Job findById(int id) {
		Job job = null;
		Connection connection = MySQLConnection.getConnection();
		String sql = "select * from job where id = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				job = new Job();
				job.setId(id);
				job.setName(resultSet.getString("name"));
				job.setStartDate(resultSet.getDate("start_date").toLocalDate());
				job.setEndDate(resultSet.getDate("end_date").toLocalDate());
				job.setLeader(userDao.findById(resultSet.getInt("user_id")));
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return job;
	}

	public List<Job> findByUserId(int id) {
		List<Job> list = new ArrayList<>();
		Connection connection = MySQLConnection.getConnection();
		String sql = "select * from job where user_id = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Job job = new Job();
				job.setId(resultSet.getInt("id"));
				job.setName(resultSet.getString("name"));
				job.setStartDate(resultSet.getDate("start_date").toLocalDate());
				job.setEndDate(resultSet.getDate("end_date").toLocalDate());
				job.setLeader(userDao.findById(id));
				list.add(job);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public void save(Job job) {
		Connection connection = MySQLConnection.getConnection();
		String sql = "insert into job(name, start_date, end_date, user_id) values(?, ?, ?, ?)";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, job.getName());
			statement.setDate(2, Date.valueOf(job.getStartDate()));
			statement.setDate(3, Date.valueOf(job.getEndDate()));
			statement.setInt(4, job.getLeader().getId());
			statement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
