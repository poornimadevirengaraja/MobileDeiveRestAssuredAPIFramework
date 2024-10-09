package api.payloads;
//POJO - Plain Old java Object 

public class payloadgetsetpojo {
 
	private String name;	
	private int year;
	private float price;
	private String cpumodel;
	private String harddisksize;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float d) {
		this.price = d;
	}
	public String getCpumodel() {
		return cpumodel;
	}
	public void setCpumodel(String cpumodel) {
		this.cpumodel = cpumodel;
	}
	public String getHarddisksize() {
		return harddisksize;
	}
	public void setHarddisksize(String harddisksize) {
		this.harddisksize = harddisksize;
	}
	
	
	
}
