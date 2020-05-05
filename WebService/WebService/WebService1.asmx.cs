using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Data.SqlClient;
using System.Data;
using System.Text;

namespace WebService
{
    /// <summary>
    /// WebService1 的摘要说明
    /// </summary>
    [WebService(Namespace = "http://andorid.News/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // 若要允许使用 ASP.NET AJAX 从脚本中调用此 Web 服务，请取消注释以下行。 
    // [System.Web.Script.Services.ScriptService]
    public class WebService1 : System.Web.Services.WebService
    {
        //数据库的信息
        string connStr = "server =DESKTOP-I8SQO82;database=News;uid=sa;pwd=123456";
        [WebMethod(Description = "获取所有分类")]
        public string GetAllNews()
        {    
            try
            {
                string sqlStr = "select * from News";
                DataSet ds = new DataSet();
                SqlDataAdapter da = new SqlDataAdapter(sqlStr, new SqlConnection(connStr));
                da.Fill(ds);
                return CreateJsonParameters(ds.Tables[0]);
            }
            catch
            {
                return null;
            }
        }
        [WebMethod(Description = "获取所有分类")]
        public string GetAllCategroy()
        {
            try
            {
                string sqlStr = "select * from Category";
                DataSet ds = new DataSet();
                SqlDataAdapter da = new SqlDataAdapter(sqlStr, new SqlConnection(connStr));
                da.Fill(ds);
                return CreateJsonParameters(ds.Tables[0]);
            }
            catch
            {
                return null;
            }
        }
        [WebMethod(Description ="添加新闻")]
        public bool InsertNew(string title,string image,int cid, string data,string content)
        {
            try
            {
              
                string sqlStr = string.Format("insert into News values ('{0}','{1}','{2}','{3}','{4}')", title, data, cid, image, content); ;
                DataSet ds = new DataSet();
                SqlDataAdapter da = new SqlDataAdapter(sqlStr, new SqlConnection(connStr));
                da.Fill(ds);
                return true;
            }
            catch
            {
                return false;
            }
        }
        [WebMethod(Description = "删除新闻")]
        public bool DeleteNew(int id)
        {
            try
            {

                string sqlStr = "delete News where id="+id;
                DataSet ds = new DataSet();
                SqlDataAdapter da = new SqlDataAdapter(sqlStr, new SqlConnection(connStr));
                da.Fill(ds);
                return true;
            }
            catch
            {
                return false;
            }
        }
        [WebMethod(Description = "修改新闻")]
        public bool ChangeNew(string title, string image, int cid, string data, string content,int nid)
        {
            try
            {

                string sqlStr = string.Format("update News set title = '{0}', publish_date = '{1}', cid = '{2}', front_image = '{3}',content='{4}' where id='{5}'", title, data, cid, image, content, nid); ;
                DataSet ds = new DataSet();
                SqlDataAdapter da = new SqlDataAdapter(sqlStr, new SqlConnection(connStr));
                da.Fill(ds);
                return  true;
            }
            catch
            {
                return false;
            }
        }
        private static string CreateJsonParameters(DataTable dt)
        {
            /* /****************************************************************************
             * Without goingin to the depth of the functioning of this Method, i will try to give an overview
             * As soon as this method gets a DataTable it starts to convert it into JSON String,
             * it takes each row and in each row it grabs the cell name and its data.
             * This kind of JSON is very usefull when developer have to have Column name of the .
             * Values Can be Access on clien in this way. OBJ.HEAD[0].<ColumnName>
             * NOTE: One negative point. by this method user will not be able to call any cell by its index.
             * *************************************************************************/
            StringBuilder JsonString = new StringBuilder();
            //Exception Handling        
            if (dt != null && dt.Rows.Count > 0)
            {

                JsonString.Append("[ ");
                for (int i = 0; i < dt.Rows.Count; i++)
                {
                    JsonString.Append("{ ");
                    for (int j = 0; j < dt.Columns.Count; j++)
                    {
                        if (j < dt.Columns.Count - 1)
                        {
                            JsonString.Append("\"" + dt.Columns[j].ColumnName.ToString() + "\":" + "\"" + dt.Rows[i][j].ToString() + "\",");
                        }
                        else if (j == dt.Columns.Count - 1)
                        {
                            JsonString.Append("\"" + dt.Columns[j].ColumnName.ToString() + "\":" + "\"" + dt.Rows[i][j].ToString() + "\"");
                        }
                    }
                    /*end Of String*/
                    if (i == dt.Rows.Count - 1)
                    {
                        JsonString.Append("} ");
                    }
                    else
                    {
                        JsonString.Append("}, ");
                    }
                }
                JsonString.Append("]");
                return JsonString.ToString();
            }
            else
            {
                return null;
            }
        }
    }
}
