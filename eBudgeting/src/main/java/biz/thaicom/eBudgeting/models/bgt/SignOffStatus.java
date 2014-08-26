package biz.thaicom.eBudgeting.models.bgt;

public enum SignOffStatus {
	SIGNOFF(0), RELEASE(1), SIGNOFF_HEAD(2), RELEASE_HEAD(3);
	
	private int value;
	
	private SignOffStatus(int value) {
		this.value = value;
	}
	
	public String getStatusText() {
		switch(value) {
		case 0:
			return "ทำการ Sign Off";
		case 1:
			return "ทำการ Release";
		}
		
		return null;
	}
}
