using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net.Http;
using System.Net;



namespace WindowsFormsApplication1
{
    class MyHttpClient
    {
        public MyHttpClient()
        {

        }

        public static String sendHttpRequest()
        {
            var baseAddress = new Uri("http://www.baidu.com");
            var cookieContainer = new CookieContainer();
            using (var handler = new HttpClientHandler() { CookieContainer = cookieContainer })
            using (var client = new HttpClient(handler) { BaseAddress = baseAddress })
            {
                var task = client.GetAsync(baseAddress);

                task.Result.EnsureSuccessStatusCode();
                HttpResponseMessage response = task.Result;
                var result = response.Content.ReadAsStringAsync();
                String stringResult = result.Result;

                return stringResult;
            }
        }
    }
}
