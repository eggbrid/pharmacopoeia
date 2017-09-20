package com.pharmacopoeia.util.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public enum SexEnum  {
	MALE("男", "0"),
	FEMALE("女", "1");

	// 成员变量
	    private String name;
	    private String value;
	    
	// 构造方法
	    private SexEnum(String name, String value) {
	        this.name = name;
	        this.value = value;
	    }
	    
	// 普通方法
	    public static String getName(String value) {
	    	return getEnumByName(value).getName();

	    }
	 
	// get set 方法
	    public String getName() {
	        return name;
	    }
	    public String  getValue() {
	    	return this.value;
	    }
		public static SexEnum getEnumByName(String value){
			SexEnum resultEnum = null;
			SexEnum[] enumAry = SexEnum.values();
			for(int i = 0;i<enumAry.length;i++){
				if(enumAry[i].getName().equals(value)){
					resultEnum = enumAry[i];
					break;
				}
			}
			return resultEnum;
		}
		public static SexEnum getEnum(String value){
			SexEnum resultEnum = null;
			SexEnum[] enumAry = SexEnum.values();
			for(int i = 0;i<enumAry.length;i++){
				if(enumAry[i].getValue().equals(value)){
					resultEnum = enumAry[i];
					break;
				}
			}
			return resultEnum;
		}
		
		public static Map<String, Map<String, Object>> toMap() {
			SexEnum[] ary = SexEnum.values();
			Map<String, Map<String, Object>> enumMap = new HashMap<String, Map<String, Object>>();
			for (int num = 0; num < ary.length; num++) {
				Map<String, Object> map = new HashMap<String, Object>();
				String key = String.valueOf(getEnumByName(ary[num].getValue()));
				map.put("value", ary[num].getValue());
				map.put("desc", ary[num].getName());
				enumMap.put(key, map);
			}
			return enumMap;
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List toList(){
			SexEnum[] ary = SexEnum.values();
			List list = new ArrayList();
			for(int i=0;i<ary.length;i++){
				Map<String,String> map = new HashMap<String,String>();
				map.put("value",ary[i].getValue());
				map.put("desc", ary[i].getName());
				list.add(map);
			}
			return list;
		}      
}
