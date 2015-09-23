using System;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Text;

class MyTcpListener
{
    public static void Main()
    {
        string url = "http://www.qq.com/";
        var request = (HttpWebRequest)WebRequest.Create(url);

        var response = (HttpWebResponse)request.GetResponse();
        Stream stream = response.GetResponseStream();
        StreamReader sr = new StreamReader(stream);
        string content = sr.ReadToEnd();

        Console.Write(content);
    }
}