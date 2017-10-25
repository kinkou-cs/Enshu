import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import block.Controller;
import block.Plant;
import lejos.utility.Delay;
import lejos.utility.Stopwatch;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;

public class Main{

	static final double SETPOINT = 0.0f;			// セットポイント
	
	public static void main(String[] args) throws IOException, InterruptedException {
		Controller controller = new Controller();	// Controller
		Plant ev3 = new Plant();					// Plant (EV3)
		Stopwatch stopwatch = new Stopwatch();		// 経過時間取得用ストップウォッチ
		double before_time = stopwatch.elapsed();	// 過去経過時間
		double now_time;							// 現在経過時間
		double T = 0.0f;						// タイムラグ

        EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S2);
        SensorMode touchMode = touch.getTouchMode();
        float[] sampleTouch = new float[touchMode.sampleSize()];
		
		double motorSpeed = 0.0f;	// モーターの角速度（制御器の出力，プラントへの入力）
		double ev3Output = 0.0f;	//　本体の角速度（プラントの出力，制御器への入力）

		Server server = new Server();
		
		while(true){
	        touchMode.fetchSample(sampleTouch, 0);
	        if((int)sampleTouch[0] != 0) break;

			now_time = stopwatch.elapsed();		// 現在時刻の取得
			if(now_time - before_time > T){		// 一定時間経過したら
//				double x_motorSpeed = controller.calc((ev3.isGoodCond()? -0.4f : 1.3f) * (SETPOINT - ev3Output));	// 制御器による制御開始
				double x_motorSpeed = controller.calc((SETPOINT - ev3Output));	// 制御器による制御開始
				before_time = now_time;			// 過去経過時間を更新
				motorSpeed = x_motorSpeed;
			}
			ev3Output = ev3.calc(motorSpeed);	// ジャイロセンサから角速度を取得
			server.send(stopwatch.elapsed() + "," + ev3Output + "," + motorSpeed);
		}
		server.close();
	}
}

class Server {

	private ServerSocket ss = new ServerSocket(6000);
	private Socket s = null;
	private PrintWriter pw;

	public Server() throws IOException, InterruptedException{
		try{
			System.out.println("Waitinng...");
			s = ss.accept();
			pw = new PrintWriter(s.getOutputStream(), true);
			System.out.println("Connected!");
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public void send(String msg){
		pw.println(msg);
		pw.flush();
	}

	public void close(){
		try{
			if(s != null) s.close();
		} catch (IOException e){
			System.err.println(e);
		}
		try{
			if(ss != null) ss.close();
		} catch (IOException e){
			System.err.println(e);
		}
	}

}
