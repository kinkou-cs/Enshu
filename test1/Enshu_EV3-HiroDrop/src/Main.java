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

	static final double SETPOINT = 0.0f;			// �Z�b�g�|�C���g
	
	public static void main(String[] args) throws IOException, InterruptedException {
		Controller controller = new Controller();	// Controller
		Plant ev3 = new Plant();					// Plant (EV3)
		Stopwatch stopwatch = new Stopwatch();		// �o�ߎ��Ԏ擾�p�X�g�b�v�E�H�b�`
		double before_time = stopwatch.elapsed();	// �ߋ��o�ߎ���
		double now_time;							// ���݌o�ߎ���
		double T = 0.0f;						// �^�C�����O

        EV3TouchSensor touch = new EV3TouchSensor(SensorPort.S2);
        SensorMode touchMode = touch.getTouchMode();
        float[] sampleTouch = new float[touchMode.sampleSize()];
		
		double motorSpeed = 0.0f;	// ���[�^�[�̊p���x�i�����̏o�́C�v�����g�ւ̓��́j
		double ev3Output = 0.0f;	//�@�{�̂̊p���x�i�v�����g�̏o�́C�����ւ̓��́j

		Server server = new Server();
		
		while(true){
	        touchMode.fetchSample(sampleTouch, 0);
	        if((int)sampleTouch[0] != 0) break;

			now_time = stopwatch.elapsed();		// ���ݎ����̎擾
			if(now_time - before_time > T){		// ��莞�Ԍo�߂�����
//				double x_motorSpeed = controller.calc((ev3.isGoodCond()? -0.4f : 1.3f) * (SETPOINT - ev3Output));	// �����ɂ�鐧��J�n
				double x_motorSpeed = controller.calc((SETPOINT - ev3Output));	// �����ɂ�鐧��J�n
				before_time = now_time;			// �ߋ��o�ߎ��Ԃ��X�V
				motorSpeed = x_motorSpeed;
			}
			ev3Output = ev3.calc(motorSpeed);	// �W���C���Z���T����p���x���擾
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
