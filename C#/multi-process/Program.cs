using System.Diagnostics;


namespace ConsoleApplication1
{
    class Program
    {
        static void Main(string[] args)
        {
            Process proc = Process.Start("notepad.exe");
        }
    }
}
