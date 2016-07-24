package org.liufeng.course.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 示例：使用JDBC操作BAE中的MySQL数据库
 * 
 * @author liufeng
 * @date 2013-11-18
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1196941092414541883L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("gb2312");
		response.setCharacterEncoding("gb2312");

		PrintWriter out = response.getWriter();
		// 查询user
		List<HashMap<String, Object>> userList = queryUser(request);
		// 遍历List集合
		for (HashMap<String, Object> map : userList) {
			out.println(map.get("name") + " " + map.get("age"));
		}
		out.flush();
		out.close();
	}

	/**
	 * 查询BAE MySQL数据库中user表的数据
	 * 
	 * @param request
	 * @return
	 */
	private static List<HashMap<String, Object>> queryUser(HttpServletRequest request) {
		List<HashMap<String, Object>> userList = new ArrayList<HashMap<String, Object>>();

		// 从request请求头中取出IP、端口、用户名和密码
		String host = request.getHeader("BAE_ENV_ADDR_SQL_IP");
		String port = request.getHeader("BAE_ENV_ADDR_SQL_PORT");
		String username = request.getHeader("BAE_ENV_AK");
		String password = request.getHeader("BAE_ENV_SK");
		// 数据库名称
		String dbName = "FTGJUvPHrbXsLGsYpwlp";
		// JDBC URL
		String url = String.format("jdbc:mysql://%s:%s/%s", host, port, dbName);

		try {
			// 加载MySQL驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 获取数据库连接
			Connection conn = DriverManager.getConnection(url, username, password);
			// 定义查询SQL语句
			String sql = "select name,age from user";
			// 创建PreparedStatement对象（包含已编译的SQL语句）
			PreparedStatement ps = conn.prepareStatement(sql);
			// 执行查询并获取结果集
			ResultSet rs = ps.executeQuery();
			// 遍历查询结果集
			while (rs.next()) {
				HashMap<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("name", rs.getString("name"));
				userMap.put("age", rs.getInt("age"));
				userList.add(userMap);
			}

			// 关闭连接，释放资源
			rs.close();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}
}