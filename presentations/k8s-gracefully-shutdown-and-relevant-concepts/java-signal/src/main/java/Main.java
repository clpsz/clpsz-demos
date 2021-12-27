import sun.misc.Signal;
import sun.misc.SignalHandler;


public class Main {
    public static volatile boolean quit = false;

    public static void main(String[] args) {
        // 信号处理实例
        MySignalHandler mySignalHandler = new MySignalHandler();

        // 注册对指定信号的处理
        // kill -1
        Signal.handle(new Signal("HUP") ,mySignalHandler);
        // kill -2
        // Signal.handle(new Signal("INT"), mySignalHandler);
        // kill or kill -15
        Signal.handle(new Signal("TERM") ,mySignalHandler);

        System.out.println("[Thread:"+Thread.currentThread().getName() + "] is sleep" );
        while(true) {
            try {
                System.out.println("hello world.");
                Thread.sleep(3000);
                System.out.println("hello shopee.");
                Thread.sleep(3000);
                if(quit) {
                    System.exit(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

@SuppressWarnings("restriction")
class MySignalHandler implements SignalHandler {

    @Override
    public void handle(Signal signal) {

        // 信号量名称
        String name = signal.getName();
        // 信号量数值
        int number = signal.getNumber();

        // 当前进程名
        String currentThreadName = Thread.currentThread().getName();

        System.out.println("[Thread:"+currentThreadName + "] received signal: " + name + " == kill -" + number);
        if(name.equals("TERM")){
            Main.quit = true;
        }
    }

}
