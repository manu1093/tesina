package progetto.data.schema2;

public class GeometryTableSchema extends VisibleTableSchema{
	private String geometryField;
	public GeometryTableSchema(String name) {
		super(name);
		
	}
	
	public GeometryTableSchema(String name,String geometry) {
		super(name);
		geometryField=geometry;
	}
	public String getGeometryField(){
		return geometryField;
	}
}
