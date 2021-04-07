package com.buildman.buildman.model;

public class ChooseMaterial_Model {
	// Choose Material(CM) variables
	private int material_id_CM;
	private String material_name_CM = null;
	private String material_quantity_CM = null;
	private String units_CM = null;
	
	public ChooseMaterial_Model(int mMaterial_id_CM, String mMaterial_name_CM,
			String mMaterial_quantity_CM, String mUnits_CM) {
		// TODO Auto-generated constructor stub
		   this.material_id_CM = mMaterial_id_CM;
		   this.material_name_CM = mMaterial_name_CM;
		   this.material_quantity_CM = mMaterial_quantity_CM;
	       this.units_CM = mUnits_CM;	 
	}
	public void setMaterial_ID_CM(int mMaterial_id_CM) {
	    this.material_id_CM = mMaterial_id_CM;
	}
	public int getMaterial_ID_CM() {
	    return this.material_id_CM;
	}
	
	public void setMaterial_Name_CM(String mMaterial_name_CM) {
	    this.material_name_CM = mMaterial_name_CM;
	}
	public String getMaterial_Name_CM() {
	    return this.material_name_CM;
	}
	
	public void setMaterial_Quantity_CM(String mMaterial_Quantity_CM) {
	    this.material_quantity_CM = mMaterial_Quantity_CM;
	}
	public String getMaterial_Quantity_CM() {
	    return this.material_quantity_CM;
	}
	
	public void setUnits_CM(String mUnits_CM) {
	    this.units_CM = mUnits_CM;
	}
	public String getUnits_CM() {
	    return this.units_CM;
	}
	
}
