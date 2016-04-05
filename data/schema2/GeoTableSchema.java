package progetto.data.schema2;

public class GeoTableSchema extends VisibleTableSchema{
	private String geography;
	public GeoTableSchema(String name,String geography){
		super(name);
		this.geography=geography;
	}
	public String getGeography(){
		return geography;
	}

}
