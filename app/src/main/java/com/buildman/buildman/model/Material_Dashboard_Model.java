package com.buildman.buildman.model;

public class Material_Dashboard_Model {
	private int matl_dashboard_id;
	int matl_id;
	String material_name = null;
	String material_units = null;
	String material_quantity = null;
	String material_value = null;
	int site_id;
	String site_name = null;
	String date = null;
	
	public Material_Dashboard_Model(int material_id, String material_name,
			String material_units, String material_quantity, int site_id,String site_name,String date ) {
		// TODO Auto-generated constructor stub
		this.matl_id=material_id;
		this.material_name=material_name;
		this.material_units=material_units;
		this.material_quantity=material_quantity;
		this.site_id=site_id;
		this.site_name=site_name;
		this.date=date;
	}
	public Material_Dashboard_Model() {
		// TODO Auto-generated constructor stub
	}
	public void setMaterialDashboard_ID(int material_dashboard_id) {
	    this.matl_dashboard_id = material_dashboard_id;
	}
	public int getMaterialDashboard_ID() {
	    return this.matl_dashboard_id;
	}
	public void setMaterial_ID(int material_id) {
	    this.matl_id = material_id;
	}
	public int getMaterial_ID() {
	    return this.matl_id;
	}

	public void setMaterial_Name(String material_name) {
	    this.material_name = material_name;
	}

	public String 	getMaterial_Name() {
	    return this.material_name;
	}
	public void setMaterial_Units(String material_units) {
	    this.material_units = material_units;
	}

	public String getMaterial_Units() {
	    return this.material_units;
	}

	public void setMaterial_Quantity(String material_Quantity) {
	    this.material_quantity = material_Quantity;
	}

	public String getMaterial_Quantity() {
	    return this.material_quantity;
	}
	public void setMaterial_Value(String material_value) {
	    this.material_value = material_value;
	}

	public String getMaterial_Value() {
	    return this.material_value;
	}
	public void setSite_ID(int site_id) {
	    this.site_id = site_id;
	}
	public int getSite_ID() {
	    return this.site_id;
	}
	public void setSite_Name(String site_name) {
	    this.site_name = site_name;
	}

	public String getSite_Name() {
	    return this.site_name;
	}
	public void setDate(String date) {
	    this.date = date;
	}

	public String getDate() {
	    return this.date;
	}
}
