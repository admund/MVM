package pl.admund.MVM_Server.SportStuff;

public class League 
{
	private int id;
	private String name;
	private int level;
	private int sezon;
	
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSezon() {
		return sezon;
	}
	public void setSezon(int sezon) {
		this.sezon = sezon;
	}
}
