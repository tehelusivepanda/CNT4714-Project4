<!DOCTYPE html>

<%-- start scriptlet --%>
<%
    String query = (String) session.getAttribute("query");
    String table = (String) session.getAttribute("sbString");
    String introduction = (String) session.getAttribute("introduction");
    String test = (String) session.getAttribute("test");
%>

<html lang="en">
    <head>
        <title>CNT4714 Remote DB System by Devin Vanzant</title>
        <meta charset="utf-8">
        <style type="text/css">
        <!--
        body { background-color: black; color:lime; font-family: verdana, arial, sans-serif; font-size: 1.4em;  }
        input[type="submit"] {background-color: yellow; font-weight:bold;}
        textarea {background-color:midnightblue; color:white; font-size:25px;}
        span {color:white;}
        #name {color:white; font-size:25px;}
        #welcome {color:white; font-size:50px;}
        #subtitle {color:red; font-size:25px;}
        #title {color:green; font-size:15px;}
        #default {color:green; font-size:10px;}
        button {background-color:green; color: white;
        padding: 15px 15px; border: none; border-radius: 8px; width:250px;}
        table, td {border: 1px solid white;}
        th {border: 3px solid white;}
        #tablehead {font-size:25px;}
        #error {background-color:red; color:white; padding: 10px 10px; font-size:25px;}
        #succ {background-color:green; color:white; padding: 10px 10px; font-size:25px;}
        -->
        </style>
    </head>

    <body>
        <h2>
            <%=introduction%>
        </h2>
        <center>
            <form action="/Project4/p4app" method="get">
                <label id="title">
                    You are connected to the database as an administrator.
                    <br>
                    Please enter any valid SQL query or update commands.
                    <br>
                    <span id="default">Default query is "select * from suppliers"</span>
                </label>
                <br>
                    <textarea rows="7" cols="50" name="query">select * from suppliers</textarea>
                <br>
                <button type="submit" class="button">Submit Command</button>
                <button type="reset" class="button">Reset Command</button>
            </form>
        </center>

        </form>
        <br><br>
        <center>
            <h1 id='tablehead'>Database Results</h1>
            <table id="resultstable">
                <%=table%>
            </table>
        </center>
        <br><br><br><br>
    </body>
</html>


