package gaindata;

public class GainData {
	private double K_p;			//比例ゲイン
	private double K_i;			//積分ゲイン
	private double K_d;			//微分ゲイン
	private byte flags;			//各ゲインを用いるかどうかを表すフラグ
	private double iHistory;	//積分の計算履歴
	
	public GainData(double K_p, double K_i, double K_d, byte flags){
		this.K_p = K_p;
		this.K_i = K_i;
		this.K_d = K_d;
		this.flags = flags;
		iHistory = 0.0f;
	}
	
	public double calc(double input){
		double motorSpeed = 0.0f;
		
		if((flags & 0x01) > 0x00){	//比例制御
			motorSpeed += input * K_p;
		}
		if((flags & 0x02) > 0x00){	//積分制御
			iHistory += input * K_i;
			motorSpeed += iHistory;
		}
		if((flags & 0x04) > 0x00){	//微分制御
			//今はパス
		}		
		
		return motorSpeed;
	}
}
