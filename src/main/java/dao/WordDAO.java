package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import model.Word;

public class WordDAO {
	private Connection db;
	private PreparedStatement ps;
	private ResultSet rs;

	private void connect() {
				
		try{
			Class.forName("com.mysql.jdbc.Driver");
			db = DriverManager.getConnection("jdbc:mysql://localhost:3306/dictionary", "root", "dolphin03");
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException c) {
			c.printStackTrace();
		}
	}
	private void disconnect() throws SQLException {
		if(rs !=null) {
			rs.close();
		}
		if(ps !=null) {
			ps.close();
		}
		if(db != null) {
			db.close();
		}
	}
	public List<Word> getListBySearchWord(String searchWord,String mode){
		List<Word> list=new ArrayList<>();
		switch(mode) {
		case "startsWith":
			searchWord=searchWord+"%";
			break;
		case "contains":
			searchWord="%"+searchWord+"%";
			break;
		case "endsWith":
			searchWord="%"+searchWord;
		}
		try {
			connect();
			ps=db.prepareStatement("SELECT * FROM words WHERE title LIKE ?");
			ps.setString(1, searchWord);
			//System.out.println(ps);
			rs=ps.executeQuery();
			while(rs.next()) {
				String title=rs.getString("title");
				String body=rs.getString("body");
				Word w=new Word(title,body);
				list.add(w);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				this.disconnect();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public List<Word> getListBySearchWord(String searchWord,String mode,int limit) throws NamingException {
		List<Word> list=new ArrayList<>();
		switch(mode) {
		case "startsWith":
			searchWord=searchWord+"%";
			break;
		case "contains":
			searchWord="%"+searchWord+"%";
			break;
		case "endsWith":
			searchWord="%"+searchWord;
		}
		try {
			this.connect();
			ps = db.prepareStatement("SELECT * FROM words WHERE title LIKE ? LIMIT ?");
			ps.setString(1, searchWord);
			ps.setInt(2, limit);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String body = rs.getString("body");
				list.add(new Word(id, title, body));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				this.disconnect();
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		return list;
	}
	
	public List<Word> getListBySearchWord(String searchWord,String mode,int limit,int offset) throws NamingException{
		List<Word> list=new ArrayList<>();
		switch(mode) {
		case "startsWith":
			searchWord=searchWord+"%";
			break;
		case "contains":
			searchWord="%"+searchWord+"%";
			break;
		case "endsWith":
			searchWord="%"+searchWord;
		}
		try {
			this.connect();
			ps = db.prepareStatement("SELECT * FROM words WHERE title LIKE ? LIMIT ? OFFSET ?");
			ps.setString(1, searchWord);
			ps.setInt(2, limit);
			ps.setInt(3, offset);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String body = rs.getString("body");
				list.add(new Word(id, title, body));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				this.disconnect();
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}
		return list;
	}

	
	//一致件数を求めるメソッド
		public int getCount(String searchWord,String mode) throws NamingException{
			switch(mode) {
			case "startsWith":
				searchWord=searchWord+"%";
				break;
			case "contains":
				searchWord="%"+searchWord+"%";
				break;
			case "endsWith":
				searchWord="%"+searchWord;
			}
			int total=0;
			try {
				this.connect();
				ps = db.prepareStatement("SELECT count(*) AS total FROM words WHERE title LIKE ?");
				ps.setString(1, searchWord);
				rs = ps.executeQuery();
				if (rs.next()) {
					total = rs.getInt("total");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					this.disconnect();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return total;
		}


}

