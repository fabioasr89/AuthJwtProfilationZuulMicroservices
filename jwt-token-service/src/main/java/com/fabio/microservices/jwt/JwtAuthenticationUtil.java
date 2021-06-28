package com.fabio.microservices.jwt;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fabio.microservices.dto.JwtUser;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtAuthenticationUtil implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_AUDIENCE = "audience";
    static final String CLAIM_KEY_CREATED = "iat";
    static final String CLAIM_KEY_AUTHORITIES = "ruoli";
    static final String CLAIM_KEY_IS_ENABLED = "isEnabled";

 

    private static final Logger log=LoggerFactory.getLogger(JwtAuthenticationUtil.class);
  
   
    public  Map<String,Object> generaMappaAttributi(JwtUser user){
    	Map<String,Object> attributi=new LinkedHashMap<String, Object>();
    	attributi.put(CLAIM_KEY_USERNAME,user.getUsername());
    	attributi.put(CLAIM_KEY_CREATED, new Date());
    	attributi.put(CLAIM_KEY_AUTHORITIES,user.getRuoli().get(0));
    	return attributi;
    }
    public  JwtUser generaToken(JwtUser user) {
    	String token=null;
    	try {
    		Map<String,Object> mappa=generaMappaAttributi(user);
    		token=Jwts.builder().setClaims(mappa).
    				setExpiration(generaDataScadenza(user.getExpiration()))
    				.signWith(SignatureAlgorithm.HS256,user.getSecret()).compact();
    		user.setToken(token);
    	}catch(Exception e) {
    		log.error(e.getMessage(),e);
    	}
    	return user;
    }
    
    public Date generaDataScadenza(Long exp) throws Exception{
    	if(exp!=null) {
    		return new Date(System.currentTimeMillis()+exp*1000);
    	}else {
    		throw new Exception();
    	}
    }
    
    private Claims getClaims(String token,String secret){
    	Claims claims=null;
    	try {
    		claims=Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    	}catch(Exception e) {
    		log.error(e.getMessage(),e);
    	}
    	return claims;
    }
    
    public String getUsername(String token,String secret) {
    	Claims claims=null;
    	String username=null;
    	try {
    		if(token!=null && secret!=null) {
    			claims=getClaims(token,secret);
    			username=claims.getSubject();
    		}
    	}catch(Exception e) {
    		log.error(e.getMessage(),e);
    	}
    	return username;
    }
    
   
    
   public boolean scaduto(JwtUser user) {
	   Claims claims=null;
	   Date d=null;
	   if(user.getToken()!=null && user.getSecret()!=null) {
		   claims=getClaims(user.getToken(),user.getSecret());
		   d=claims.getExpiration();
		   return d.before(new Date());
	   }
	   return true;
   }
    
   public JwtUser getJwtUserDaToken(JwtUser jwtUser) throws Exception{
	   if(jwtUser!=null && jwtUser.getSecret()!=null && jwtUser.getToken()!=null) {
		   Claims claims=getClaims(jwtUser.getToken(),jwtUser.getSecret());
		   jwtUser.setUsername(claims.getSubject());
		   String ruolo=(String) claims.get(CLAIM_KEY_AUTHORITIES);
		   jwtUser.getRuoli().add(ruolo);
		   return jwtUser;
	   }else {
		   throw new Exception("Impossibile procedere con il parser del token");
	   }
	   
   }
   
    public boolean validaToken(JwtUser user) {
    	try {
    		if(user.getUsername()!=null && user.getToken()!=null &&  user.getSecret()!=null && user.getUsername().equals(getUsername(user.getToken(),user.getSecret()))) {
    			return true;
    		}
    	}catch(Exception e) {
    		log.error(e.getMessage(),e);
    	}
    	return false;
    }
    
    public JwtUser refreshToken(JwtUser user) {
    	String token=null;
    	Claims claims=null;
    	try {
    		claims=getClaims(user.getToken(),user.getSecret());
    		claims.setExpiration(generaDataScadenza(user.getExpiration()));
    		token=Jwts.builder().setClaims(claims)
    				.signWith(SignatureAlgorithm.HS256,user.getSecret()).compact();
    		user.setToken(token);
    		return user;
    	}catch(Exception e) {
    		log.error(e.getMessage(),e);
    	}
    	return user;
    }
}
