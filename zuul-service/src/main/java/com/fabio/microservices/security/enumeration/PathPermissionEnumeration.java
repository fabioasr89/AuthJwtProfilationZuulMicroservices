package com.fabio.microservices.security.enumeration;

import java.util.ArrayList;
import java.util.List;

public enum PathPermissionEnumeration {
	
	
	PATH_ADMIN_1("amministratore","/services/rs/api/admin/*"),
	PATH_ADMIN_2("amministratore","/services/rs/api/apl/*"),
	PATH_ADMIN_3("amministratore","/services/rs/api/fondo/**"),
	PATH_FONDO("fondo","/services/rs/api/fondo/**"),
	PATH_APL("apl","/services/rs/api/apl/*");
	
	
	private String ruolo;
	private String pathAcesso;
	
	private PathPermissionEnumeration(String ruolo,String pathAccesso) {
		this.ruolo=ruolo;
		this.pathAcesso=pathAccesso;
	}
	
	public static List<String> getPathPerRuolo(String ruolo){
		List<String> path=null;
		if(ruolo!=null) {
			path=new ArrayList<String>();
			for(PathPermissionEnumeration enumeration:PathPermissionEnumeration.values()) {
				if(enumeration.getRuolo().equals(ruolo)) {
					path.add(enumeration.getPathAcesso());
				}
			}
		}
		return path;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getPathAcesso() {
		return pathAcesso;
	}

	public void setPathAcesso(String pathAcesso) {
		this.pathAcesso = pathAcesso;
	}
	
}
