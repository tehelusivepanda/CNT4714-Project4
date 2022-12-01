// A simple servlet to process get requests.

//import javax.servlet.*;
//import javax.servlet.http.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import com.mysql.cj.jdbc.MysqlDataSource;

public class WelcomeServletSession extends HttpServlet {

    public String sbString;
    public String query;
    public String query2;
    public String [] tokens;
    public boolean firstLoad = true;
    public String test;
    public ResultSet resultSet;
    public ResultSetMetaData metadata;
    public int success;


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        sbString = "";

        StringBuilder table = new StringBuilder();

        String introduction = "<center><span id='welcome'>Welcome to the " +
                "Enterprise Computing Database System</span><br>";

        introduction += "<span id='subtitle'>A Servlet/JSP-based Multi-tiered Enterprise<br>Application Utilizing a Tomcat Container!</span><br><br>";
        introduction += "<span id='name'>Developed by: Devin Vanzant</span>";

        query = request.getParameter("query");

        try
        {
            if (query == null)
            {
                HttpSession session = request.getSession();
                session.setAttribute("introduction", introduction);
                session.setAttribute("sbString", "");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/welcome - SessionVersion.jsp");
                dispatcher.forward(request, response);

                firstLoad = false;

                return;
            }
            Class.forName("com.mysql.cj.jdbc.Driver");
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setURL("jdbc:mysql://127.0.0.1:3306/project4");
            dataSource.setUser("root");
            dataSource.setPassword("root");

            Connection connection = dataSource.getConnection();

            if (query.charAt(0) == 'i' || query.charAt(0) == 'I')
            {
                try
                {
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(query);
                    test = "INSERT";

                    if (query.contains("shipments"))
                    {
                        query2 = "update suppliers\n\tset status = status + 5\n\t\twhere snum in (select snum\n\t\t\tfrom shipments\n\t\t\twhere quantity > 100)";

                        success = statement.executeUpdate(query2);

                        table.append("<tr><td id='succ'><center>Business Logic Detected! - Updating Supplier Status<br>Updated " + success + " supplier status marks.</center></td></tr>");
                        sbString = table.toString();

                        table = null;
                        firstLoad = false;
                    }
                    else
                    {
                        table.append("<tr><td id='succ'><center>Successfully inserted!</center></td></tr>");
                        sbString = table.toString();

                        table = null;
                        firstLoad = false;
                    }
                }
                catch (SQLIntegrityConstraintViolationException e)
                {
                    table.append("<tr><td id='error'>Error: " + e + "!</td></tr>");
                    sbString = table.toString();

                    table = null;
                    firstLoad = false;
                }
            }
            else if (query.charAt(0) == 'd' || query.charAt(0) == 'D')
            {
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
                test = "DELETE";
            }
            else if (query.charAt(0) == 'u' || query.charAt(0) == 'U')
            {
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
                test = "UPDATE";

                if (query.contains("shipments"))
                {
                    query2 = "update suppliers\n\tset status = status + 5\n\t\twhere snum in (select snum\n\t\t\tfrom shipments\n\t\t\twhere quantity > 100)";

                    success = statement.executeUpdate(query2);

                    table.append("<tr><td id='succ'><center>Business Logic Detected! - Updating Supplier Status<br>Updated " + success + " supplier status marks.</center></td></tr>");
                    sbString = table.toString();

                    table = null;
                    firstLoad = false;
                }
                else
                {
                    table.append("<tr><td id='succ'><center>Successfully updated!</center></td></tr>");
                    sbString = table.toString();

                    table = null;
                    firstLoad = false;
                }
            }
            else if (query.charAt(0) == 's' || query.charAt(0) == 'S')
            {
                Statement statement = connection.createStatement();
                resultSet = statement.executeQuery(query);
                metadata = resultSet.getMetaData();

                int col = metadata.getColumnCount();

                table.append("<tr>");

                for (int i = 1; i <= col; i++)
                {
                    String colName = metadata.getColumnName(i);
                    table.append("<th>" + colName + "</th>");
                }

                table.append("</tr>");

                while (resultSet.next())
                {
                    String info = "";
                    table.append("<tr>");
                    for (int i = 1; i <= col; i++)
                    {
                        info = resultSet.getString(i);
                        table.append("<td>" + info + "</td>");
                    }
                    table.append("</tr>");
                }

                sbString = table.toString();

                table = null;
            }
            else
            {
                return;
            }

        }
        catch (SQLException | ClassNotFoundException e)
        {
            if (firstLoad)
            {
                table.append("<tr><td></td></tr>");
                sbString = table.toString();

                table = null;
                firstLoad = false;
            }
            else
            {
                table.append("<tr><td id='error'>Error: " + e + "</td></tr>");
                sbString = table.toString();

                table = null;
                firstLoad = false;
            }
        }
        catch (IndexOutOfBoundsException ee)
        {
            table.append("<tr><td id='error'>Error: empty command line!</td></tr>");
            sbString = table.toString();

            table = null;
            firstLoad = false;
        }

        //message += "your connection is " + connection.toString();
        HttpSession session = request.getSession();
        session.setAttribute("sbString", sbString);
        session.setAttribute("introduction", introduction);
        session.setAttribute("query", query);
        session.setAttribute("test", test);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/welcome - SessionVersion.jsp");
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        doGet(request, response);
    }
}
