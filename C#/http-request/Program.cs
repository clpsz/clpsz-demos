using System;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Text;

class MyTcpListener
{
    public static void writeToFile(string filename, string text)
    {
        FileStream fs = new FileStream(filename, FileMode.Create);
        byte[] bytes = Encoding.UTF8.GetBytes(text);

        fs.Write(bytes, 0, bytes.Length);
        fs.Flush();
        fs.Close();
    }

    public static void Main()
    {
        string url = "https://www.baidu.com/";
        var request = (HttpWebRequest)WebRequest.Create(url);

        var response = (HttpWebResponse)request.GetResponse();
        Stream stream = response.GetResponseStream();
        StreamReader sr = new StreamReader(stream);
        string content = sr.ReadToEnd();

        //Console.Write(content);

        writeToFile("baidu.html", content);
    }
}