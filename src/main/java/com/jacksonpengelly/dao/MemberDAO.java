package com.jacksonpengelly.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.jacksonpengelly.models.Member;
import com.jacksonpengelly.util.DatabaseConnection;

public class MemberDAO {
	public void registerMember(Member member) {
		String sql = "INSERT INTO members (first_name, last_name, email, phone_number, address, join_date, premium, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, member.getFirstName());
			stmt.setString(2, member.getLastName());
			stmt.setString(3, member.getEmail());
			stmt.setString(4, member.getPhoneNumber());
			stmt.setString(5, member.getAddress());
			stmt.setDate(6, java.sql.Date.valueOf(member.getJoinDate()));
			stmt.setBoolean(7, member.isPremium());
			stmt.setBoolean(8, member.isActive());

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Member getMemberByEmail(String email) {
		String sql = "SELECT member_id, first_name, last_name, email, phone_number, address, join_date, premium, active, creation_date FROM members WHERE email = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, email);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					int member_id = rs.getInt("member_id");
					String first_name = rs.getString("first_name");
					String last_name = rs.getString("last_name");
					String emailDB = rs.getString("email");
					String phone_number = rs.getString("phone_number");
					String address = rs.getString("address");
					LocalDate join_date = rs.getDate("join_date").toLocalDate();
					boolean premium = rs.getBoolean("premium");
					boolean active = rs.getBoolean("active");
					LocalDateTime creation_date = rs.getTimestamp("creation_date").toLocalDateTime();

					return new Member(member_id, first_name, last_name, emailDB, phone_number, address, join_date,
							premium, active, creation_date);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null; // if no member found
	}

	public List<Member> getAllMembers() {
		List<Member> members = new ArrayList<>();
		String sql = "SELECT member_id, first_name, last_name, email, phone_number, address, join_date, premium, active, creation_date FROM members";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int member_id = rs.getInt("member_id");
					String first_name = rs.getString("first_name");
					String last_name = rs.getString("last_name");
					String emailDB = rs.getString("email");
					String phone_number = rs.getString("phone_number");
					String address = rs.getString("address");
					LocalDate join_date = rs.getDate("join_date").toLocalDate();
					boolean premium = rs.getBoolean("premium");
					boolean active = rs.getBoolean("active");
					LocalDateTime creation_date = rs.getTimestamp("creation_date").toLocalDateTime();

					Member m = new Member(member_id, first_name, last_name, emailDB, phone_number, address, join_date,
							premium, active, creation_date);
					members.add(m);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return members;
	}

	public void updateMember(Member newMember) {
		String sql = "UPDATE members SET first_name = ?, last_name = ?, email = ?, phone_number = ?, address = ?, join_date = ?, premium = ?, active = ? WHERE member_id = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, newMember.getFirstName());
			stmt.setString(2, newMember.getLastName());
			stmt.setString(3, newMember.getEmail());
			stmt.setString(4, newMember.getPhoneNumber());
			stmt.setString(5, newMember.getAddress());
			stmt.setDate(6, java.sql.Date.valueOf(newMember.getJoinDate()));
			stmt.setBoolean(7, newMember.isPremium());
			stmt.setBoolean(8, newMember.isActive());
			stmt.setInt(9, newMember.getMemberID());

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean deleteMember(Member member) {
		String sql = "DELETE FROM members WHERE member_id = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, member.getMemberID());
			int rowsAffected = stmt.executeUpdate();

			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; // incase of error
	}

	public Member getMemberByMemberID(int memberID) {
		String sql = "SELECT member_id, first_name, last_name, email, phone_number, address, join_date, premium, active, creation_date FROM members WHERE member_id = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, memberID);

			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					int member_id = rs.getInt("member_id");
					String first_name = rs.getString("first_name");
					String last_name = rs.getString("last_name");
					String emailDB = rs.getString("email");
					String phone_number = rs.getString("phone_number");
					String address = rs.getString("address");
					LocalDate join_date = rs.getDate("join_date").toLocalDate();
					boolean premium = rs.getBoolean("premium");
					boolean active = rs.getBoolean("active");
					LocalDateTime creation_date = rs.getTimestamp("creation_date").toLocalDateTime();

					return new Member(member_id, first_name, last_name, emailDB, phone_number, address, join_date,
							premium, active, creation_date);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null; // if no member found
	}
}
