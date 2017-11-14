package com.insigma.fastdfsclient;

import java.io.IOException;
import java.io.InputStream;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

/**
 * fastdfs�ļ��ϴ�����
 * @author wengsh
 *
 */
public class Fastdfs{
	
   private static  Fastdfs fastdfs;
   
   private static TrackerClient trackerClient = null;
   private static TrackerServer trackerServer = null;
   private static StorageClient storageClient= null;
   private static StorageServer storageServer= null;
	 
	private Fastdfs(){
		
	}
	
	/**
	 * ����ģʽ
	 * @return
	 */
	public static Fastdfs  getInstance (){
		if(fastdfs==null){
			fastdfs=new Fastdfs();
			try {
				System.out.println("��ȡ�����ļ�");
				//��ȡ�����ļ�
				//��ȡFastDFS�ͻ��˵������ļ��ͳ�ʼ������
				InputStream in = Fastdfs.class.getClassLoader().getResourceAsStream("fdfs_client.properties");
	            ClientGlobal.init(in);
	            trackerClient = new TrackerClient();
                trackerServer = trackerClient.getConnection();
                storageClient = new StorageClient(trackerServer,   storageServer); 
                trackerServer.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return fastdfs;
	}

	
	/**
	 * �ļ��ϴ�
	 * @param byteFile
	 * @param ext_file �ļ�������jpg��doc
	 * @return
	 * @throws MyException
	 * @throws IOException
	 */
	public String uploadFile(String groupname,byte []  byteFile,String ext_file) throws MyException,IOException{
		//�����ֽ����ϴ��ļ�  
		StringBuffer sbPath=new StringBuffer();
        String[] strings = storageClient.upload_file(groupname,byteFile, ext_file, null);  
        for (String string : strings) {  
            sbPath.append("/"+string);  
        }
        // ȫ·��  
        System.out.println(sbPath);
        return sbPath.toString();
	}
	
	/**
	 * �ļ��ϴ�
	 * @param byteFile
	 * @param ext_file �ļ�������jpg��doc
	 * @return
	 * @throws MyException
	 * @throws IOException
	 */
	public String uploadFile(byte []  byteFile,String ext_file) throws MyException,IOException{
		//�����ֽ����ϴ��ļ�  
		StringBuffer sbPath=new StringBuffer();
        String[] strings = storageClient.upload_file(byteFile, ext_file, null);  
        for (String string : strings) {  
            sbPath.append("/"+string);  
        }
        // ȫ·��  
        System.out.println(sbPath);
        return sbPath.toString();
	}
	
	/**
	 * �ļ��ϴ�
	 * @param filepath �ļ�ȫ·��
	 * @param ext_file �ļ�������jpg��doc
	 * @return
	 * @throws MyException
	 * @throws IOException
	 */
	public String uploadFile(String filePath,String ext_file ) throws MyException,IOException{
		StringBuffer sbPath=new StringBuffer();
        String[] strings = storageClient.upload_file(filePath, ext_file, null);  
        for (String string : strings) {  
            sbPath.append("/"+string);  
        }
        // ȫ·��  
        System.out.println(sbPath);
        return sbPath.toString();
	}
	
   /**
    *  �ļ��ϴ�
    * @param filePath
    * @return
    * @throws MyException
    * @throws IOException
    */
	public String uploadFile(String filePath) throws MyException,IOException{
		if (filePath.contains(".")) { 
			String ext_file = filePath.substring(filePath.lastIndexOf(".") + 1); 
	        return uploadFile(filePath,ext_file);
	    } else { 
	        System.out.println("Fail to upload file, because the format of filename is illegal."); 
	        throw new MyException("Fail to upload file, because the format of filename is illegal.");
	    } 
	}
	
	/**
	 * �ļ��ϴ�
	 * @param filePath �����ļ�·��
	 * @param slave_prefix_name  ���ļ���ǰ׺
	 * @return
	 * @throws MyException
	 * @throws IOException
	 */
	public String[] uploadMasterAndSlaveFile(String filePath, String slave_prefix_name) throws MyException,IOException{
		String[] returnstrs=new String[2];
		if (filePath.contains(".")) { 
			String ext_file = filePath.substring(filePath.lastIndexOf(".") + 1); 
			//���ļ��ϴ�
		    String[] strings = storageClient.upload_file(filePath, ext_file, null);  
		    returnstrs[0]=strings[0];
		    returnstrs[1]=strings[1];    
		    System.out.println("���ļ�·��"+"/"+strings[0]+"/"+ strings[1]);
		    returnstrs[0]="/"+strings[0]+"/"+ strings[1];

		    //���ļ��ϴ�
	        String[] strings_slave = storageClient.upload_file(strings[0],strings[1], slave_prefix_name, filePath, ext_file, null);
	        System.out.println("���ļ�·��"+"/"+strings_slave[0]+"/"+ strings_slave[1]);
		    returnstrs[1]="/"+strings_slave[0]+"/"+ strings_slave[1];
		    
	        return returnstrs;
	    } else { 
	        System.out.println("Fail to upload file, because the format of filename is illegal."); 
	        throw new MyException("Fail to upload file, because the format of filename is illegal.");
	    } 
	}
	
	/**
	 * �ļ�����
	 * @return
	 * @throws MyException
	 * @throws IOException
	 */
	public byte[] downloadFile(String filepath) throws MyException,IOException{
		int index_first=filepath.indexOf("/");
		int index_second=filepath.indexOf("/", index_first+1);
		byte[] b = storageClient.download_file(filepath.substring(index_first+1, index_second), filepath.substring(index_second+1));               
        System.out.println(b); 
        return b;
	}
	
	/**
	 * �ļ�ɾ��
	 * @return int  0����ɾ���ɹ�  2ɾ��ʧ��
	 * @throws MyException
	 * @throws IOException
	 */
	public int deleteFile(String filepath) throws MyException,IOException{
		int index_first=filepath.indexOf("/");
		int index_second=filepath.indexOf("/", index_first+1);
		int i= storageClient.delete_file(filepath.substring(index_first+1, index_second), filepath.substring(index_second+1));      
        System.out.println( i==0 ? "ɾ���ɹ�" : "ɾ��ʧ��:"+i);
        return i;
	}
}
