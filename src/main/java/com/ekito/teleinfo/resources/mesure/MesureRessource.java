package com.ekito.teleinfo.resources.mesure;

 
import java.util.Date;


public class MesureRessource {

  
    
    public MesureRessource(String ptec, Integer papp, Date date) {
		super();
		this.ptec = ptec;
		this.papp = papp;
		this.date = date;
	}



	private String ptec; 
   
    private Integer papp; 
     
    private Date date;
    
    
    public MesureRessource() {}

 
    public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
  
	public String getPtec() {
		return ptec;
	}

	public void setPtec(String ptec) {
		this.ptec = ptec;
	}

	 

	public Integer getPapp() {
		return papp;
	}

	public void setPapp(Integer papp) {
		this.papp = papp;
	}
  

}
