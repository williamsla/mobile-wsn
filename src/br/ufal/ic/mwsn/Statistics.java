package br.ufal.ic.mwsn;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Statistics {
	private long saltos;

	public long getSaltos() {
		return saltos;
	}

	public void incrementSaltos() {
		this.saltos += saltos;
	}
	private String getMD5Hash(String str) throws NoSuchAlgorithmException{
		
		MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        //System.out.println("Digest(in hex format):: " + sb.toString());

        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
    	for (int i=0;i<byteData.length;i++) {
    		String hex=Integer.toHexString(0xff & byteData[i]);
   	     	if(hex.length()==1) hexString.append('0');
   	     	hexString.append(hex);
    	}
    	return hexString.toString();
    		
		
	}
	
	public Map<String, Statistics> getStaticsperData(String dataArray[]) throws NoSuchAlgorithmException{
		Map<String, Statistics> statiscsMap = new HashMap<>();
		
		for (String string : dataArray) {
			String key = getMD5Hash(string);
			if(statiscsMap.containsKey(key)){
				statiscsMap.get(key).incrementSaltos();
			}
			else{
				Statistics statistics = new Statistics();
				statistics.incrementSaltos();
				statiscsMap.put(key, statistics);
			}
			
		}				
		return statiscsMap;
	}
}
