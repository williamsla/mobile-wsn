package br.ufal.ic.mwsn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Sink extends Node {

	public void showData() {
		String dataArray[] = formatData();
		dataArray = eliminateDuplicate(dataArray);

		for (int i = 0; i < dataArray.length; i++) {
			System.out.println(i + ":" + dataArray[i].toString());
		}
	}

	public String[] formatData() {

		String dataArray[] = this.getData().split(";");

		// System.out.println("Sink data: " + dataArray.toString());
		return dataArray;
	}
	public String getMD5Hash(String str) throws NoSuchAlgorithmException{
		
		MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        System.out.println("Digest(in hex format):: " + sb.toString());

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
			if(statiscsMap.containsKey(string)){
				statiscsMap.get(string).incrementSaltos();
			}
			else{
				Statistics statistics = new Statistics();
				statistics.incrementSaltos();
				statiscsMap.put(string, statistics);
			}
			
		}				
		return statiscsMap;
	}

	public String[] eliminateDuplicate(String dataArray[]) {
		for (int i = 0; i < dataArray.length; i++) {
			for (int j = 0; j < dataArray.length; j++) {

				if (dataArray[i].toString().equals(dataArray[j].toString()) && j > i) {

					List<String> list = new ArrayList<String>(Arrays.asList(dataArray));
					list.remove(j);
					dataArray = list.toArray(new String[0]);
				}
			}
		}

		return dataArray;
	}

	@Override
	public void run() {
		this.placeSink();

		while (true) {
			showData();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void placeSink() {
		int sinkX = this.getPosition().getX();
		int sinkY = this.getPosition().getY();
		String sinkId = this.getId().toString();

		Simulation.getInstance().getEnvironment().contendGridPosition(sinkX, sinkY, sinkId);
	}

}
