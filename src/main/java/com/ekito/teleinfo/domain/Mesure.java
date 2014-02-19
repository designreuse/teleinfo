package com.ekito.teleinfo.domain;

 
import java.util.Date;

 
import org.springframework.data.annotation.Id;

public class Mesure {

    @Id
    private String id;
 
    public Mesure() {}

 
    public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	private Date date;
 
    public String getId() {
		return id;
	}

	public void setIdMesure(String idMesure) {
		this.id = idMesure;
	}

	public String getAdco() {
		return adco;
	}

	public void setAdco(String adco) {
		this.adco = adco;
	}

	public Integer getHchc() {
		return hchc;
	}

	public void setHchc(Integer hchc) {
		this.hchc = hchc;
	}

	public Integer getHchp() {
		return hchp;
	}

	public void setHchp(Integer hchp) {
		this.hchp = hchp;
	}

	public String getHhphc() {
		return hhphc;
	}

	public void setHhphc(String hhphc) {
		this.hhphc = hhphc;
	}

	public String getPtec() {
		return ptec;
	}

	public void setPtec(String ptec) {
		this.ptec = ptec;
	}

	public Integer getIinst() {
		return iinst;
	}

	public void setIinst(Integer iinst) {
		this.iinst = iinst;
	}

	public Integer getPapp() {
		return papp;
	}

	public void setPapp(Integer papp) {
		this.papp = papp;
	}

	public Integer getImax() {
		return imax;
	}

	public void setImax(Integer imax) {
		this.imax = imax;
	}

	public String getOptarif() {
		return optarif;
	}

	public void setOptarif(String optarif) {
		this.optarif = optarif;
	}

	public Integer getIsousc() {
		return isousc;
	}

	public void setIsousc(Integer isousc) {
		this.isousc = isousc;
	}

	public String getMotdetat() {
		return motdetat;
	}

	public void setMotdetat(String motdetat) {
		this.motdetat = motdetat;
	}

	private String adco; // Adresse du concentrateur de te??le??report - 12car
    private Integer hchc; // Index option Heures Creuses - 8car - Wh
    private Integer hchp; // Index option Heures Pleine - 8car - Wh
   // private Integer ejphn; // Index option EJP, Heures Normales - 8car - Wh
   // private Integer ejphpm; // Index option EJP, Heures de Pointe Mobile - 8car - Wh
    private	String hhphc;  // Groupe horaire si option = heures creuses ou tempo - 1 car
    private String ptec; // Pe??riode Tarifaire en cours - 4car
    //- TH..=> Toutes les Heures.
    //- HC..=> Heures Creuses.
    //- HP..=> Heures Pleines.
    //- HN..=> Heures Normales.
    //- PM..=> Heures de Pointe Mobile.

    private Integer iinst; // Intensit?? instantan??e - 3car - A 
    private Integer papp; // Puissance apparente - 5car - VA
    private Integer imax; // Intensit??e max - 3 car - A
    private String optarif; // Option tarifaire choisie - 4 car 
    private Integer isousc; // Intensit?? souscrite - 2 car - A
    private String motdetat; // Mot d'e??tat du compteur - 6car
    // private String base; // Index option Base - 8car - Wh  
 


    public Mesure(String pId, String pAdco, Integer pHchc, Integer pHcHp, 
    		String pHhphc, String pPtec, 
    		Integer pIinst, Integer pPapp, Integer pImax, String pOptarif, Integer pIsousc, String pMotdetat) {
        this.id = pId;
        this.adco = pAdco;
        this.hchc =pHchc ;
        this.hchp = pHcHp ;
        this.hhphc = pHhphc ;
        this.ptec = pPtec;
        this.iinst = pIinst ;
        this.papp = pPapp;
        this.imax = pImax;
        this.optarif = pOptarif;
        this.isousc = pIsousc;
        this.motdetat = pMotdetat;
        this.date = new Date(new Date().getTime()+60*60*1000);// ajout d'une heure, TODO voir pb heure UTC 
        
        
        
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getClass().getName());
        buffer.append("@").append(Integer.toHexString(System.identityHashCode(this)));
        buffer.append("[");
        buffer.append("adco=").append(getAdco());
        buffer.append(",");
        
        buffer.append("hchc=").append(getHchc());
        buffer.append(",");
        
        buffer.append("hchp=").append(getHchp());
        buffer.append(",");
        
        buffer.append("hhphc=").append(getHhphc());
        buffer.append(",");
        
        buffer.append("ptec=").append(getPtec());
        buffer.append(",");
        
        buffer.append("iinst=").append(getIinst());
        buffer.append(",");
        
        buffer.append("papp=").append(getPapp());
        buffer.append(",");
        
        buffer.append("imax=").append(getImax());
        buffer.append(",");
        
        buffer.append("optarif=").append(getOptarif());
        buffer.append(",");
        
        buffer.append("isousc=").append(getIsousc());
        buffer.append(",");
        
        buffer.append("motdetat=").append(getMotdetat());
        buffer.append(",");
        
      
        buffer.append("]");
        return buffer.toString();
                
        
    }

}
