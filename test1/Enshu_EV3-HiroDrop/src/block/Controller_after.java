package block;

import gaindata.GainData;

/*
 * Controller program
 */
public class Controller_after implements block{

	static private final byte flags = 0x07;	//比例制御，積分制御．微分制御を使用するかどうかのフラグ
	private final double SETPOINT = 90.0f;	//セットポイント
	private GainData stop, forward, backward;
	private int mode = 1;
	
	public Controller_after(){
		stop = new GainData(0.0, 0.0, 0.0, (byte)0x07);
		forward = new GainData(0.0, 0.0, 0.0, (byte)0x07);
		backward = new GainData(0.0, 0.0, 0.0, (byte)0x07);
	}
	
	@Override
	public double calc(double input) {
		if(mode == 1)
			return stop.calc(input);
		else if(mode == 2)
			return forward.calc(input);
		else if(mode == 3)
			return backward.calc(input);
		return 0.0f;
	}

	public void setMode(int mode){
		this.mode = mode;
	}
}
