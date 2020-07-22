package model;

public class Model {
	private String tableName;
	protected boolean saved = false;

	public Model(String tablename) {
		super();
		this.tableName = tableName;
	}
}