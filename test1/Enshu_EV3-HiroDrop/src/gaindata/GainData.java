package gaindata;

public class GainData {
	private double K_p;			//���Q�C��
	private double K_i;			//�ϕ��Q�C��
	private double K_d;			//�����Q�C��
	private byte flags;			//�e�Q�C����p���邩�ǂ�����\���t���O
	private double iHistory;	//�ϕ��̌v�Z����
	
	public GainData(double K_p, double K_i, double K_d, byte flags){
		this.K_p = K_p;
		this.K_i = K_i;
		this.K_d = K_d;
		this.flags = flags;
		iHistory = 0.0f;
	}
	
	public double calc(double input){
		double motorSpeed = 0.0f;
		
		if((flags & 0x01) > 0x00){	//��ᐧ��
			motorSpeed += input * K_p;
		}
		if((flags & 0x02) > 0x00){	//�ϕ�����
			iHistory += input * K_i;
			motorSpeed += iHistory;
		}
		if((flags & 0x04) > 0x00){	//��������
			//���̓p�X
		}		
		
		return motorSpeed;
	}
}
