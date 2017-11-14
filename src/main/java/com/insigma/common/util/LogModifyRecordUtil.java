package com.insigma.common.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.insigma.mvc.model.EF13;
import com.insigma.resolver.AppException;

public class LogModifyRecordUtil {

	/**
	 * 比较两个对象的值，返回值不相同的变更明细列表
	 * @param oldObj 原对象
	 * @param newObj 新对象
	 * @param tableName 表名
	 * @return List，内部是ArchivesAlterDetailDTO
	 * @throws AppException
	 */ 
	public static List<EF13> propertyCompare(Object oldObj,Object newObj,String tableName) throws AppException{
		if(oldObj.getClass()!=newObj.getClass()){
			throw new AppException("比较对象的类型不一致！");
		}
		List<EF13> list=new ArrayList();
		tableName=tableName.toUpperCase();
		
		try{
			PropertyDescriptor[] props = Introspector.getBeanInfo(oldObj.getClass(),Object.class).getPropertyDescriptors();
			for(int i=0;i<props.length;i++){
				Object oldValue=props[i].getReadMethod().invoke(oldObj, new Object[]{});
				Object newValue=props[i].getReadMethod().invoke(newObj, new Object[]{});
				if(CompareUtil.isBasicClass(props[i].getPropertyType())){
					boolean flag=true;
					if(props[i].getPropertyType()==String.class){
						flag=CompareUtil.compareString((String)oldValue,(String)newValue);
					}else if(props[i].getPropertyType()==Long.class){
						flag=CompareUtil.compareLong((Long)oldValue,(Long)newValue);
					}else if(props[i].getPropertyType()==Integer.class){
						flag=CompareUtil.compareInteger((Integer)oldValue,(Integer)newValue);
					}else if(props[i].getPropertyType()==Short.class){
						flag=CompareUtil.compareShort((Short)oldValue,(Short)newValue);
					}else if(props[i].getPropertyType()==Float.class){
						flag=CompareUtil.compareFloat((Float)oldValue,(Float)newValue);
					}else if(props[i].getPropertyType()==Double.class){
						flag=CompareUtil.compareDouble((Double)oldValue,(Double)newValue);
					}else if(props[i].getPropertyType()==Date.class){
						flag=CompareUtil.compareDate((Date)oldValue,(Date)newValue);
					}
					if(!flag){
						String colName=props[i].getName().toUpperCase();
						EF13 dto=new EF13();
						dto.setAef140(tableName);//表名
						dto.setAef141(colName);//变更项目
						if(props[i].getPropertyType()==Date.class){
							dto.setAef142(oldValue==null?null:DateUtil.formatDate((Date)oldValue));//变更前的信息
							dto.setAef143(newValue==null?null:DateUtil.formatDate((Date)newValue));//变更后的信息
						}else{
							dto.setAef142(oldValue==null?null:oldValue.toString());//老数据
							dto.setAef143(newValue==null?null:newValue.toString());//新数据
						}		
						list.add(dto);
					}
				} 
			}
		}catch(Exception e){
			if(e instanceof AppException){
				throw (AppException)e;
			}else{
				throw new AppException("属性比较失败！",e);
			}
		}
		return list;
	}
	
}
