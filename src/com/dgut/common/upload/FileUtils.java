package com.dgut.common.upload;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ServletContextAware;

import com.dgut.common.util.DateUtils;


public class FileUtils implements ServletContextAware{
	
	private static ServletContext ctx;
	public void setServletContext(ServletContext servletContext) {
		this.ctx = servletContext;
	}
	
	    //生成文件路径
	    private static DateUtils dateUtils = DateUtils.getDateInstance();
	    //文件路径+名称
	    /**
	     * 创建文件
	     * @param fileName  文件名称
	     * @param filecontent   文件内容
	     * @return  是否创建成功，成功则返回true
	     */
	    public  String saveFile(String path,String filecontent){
	    	String filePath;
	    	filePath = path+dateUtils.getNowString()+".txt";//文件路径+名称+文件类型
	        File file = new File(ctx.getRealPath(filePath));
	        try {
	        	
	            //如果文件不存在，则创建新的文件
	        	 if(!file.getParentFile().exists()) {  
	        		 file.getParentFile().mkdirs(); 
	        	 }
	        	 if(!file.exists()){
	        		 file.createNewFile();
	        	 }
	        	 writeFileContent(filePath, filecontent);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return filePath;
	    }
	    
	    /**
	     * 向文件中写入内容
	     * @param filepath 文件路径与名称
	     * @param newstr  写入的内容
	     * @return
	     * @throws IOException
	     */
	    public  boolean writeFileContent(String filepath,String newstr) throws IOException{
	        Boolean bool = false;
	        String filein = newstr+"\r\n";//新写入的行，换行
	        String temp  = "";
	        
	        FileInputStream fis = null;
	        InputStreamReader isr = null;
	        BufferedReader br = null;
	        FileOutputStream fos  = null;
	        PrintWriter pw = null;
	        try {
	            File file = new File(ctx.getRealPath(filepath));//文件路径(包括文件名称)
	            StringBuffer buffer = new StringBuffer();
	        
	            buffer.append(filein);
	            
	            fos = new FileOutputStream(file);
	            pw = new PrintWriter(fos);
	            pw.write(buffer.toString().toCharArray());
	            pw.flush();
	            bool = true;
	        } catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	        }finally {
	            //不要忘记关闭
	            if (pw != null) {
	                pw.close();
	            }
	            if (fos != null) {
	                fos.close();
	            }
	            if (br != null) {
	                br.close();
	            }
	            if (isr != null) {
	                isr.close();
	            }
	            if (fis != null) {
	                fis.close();
	            }
	        }
	        return bool;
	    }
	    
	    /**
	     * 删除文件
	     * @param fileName 文件名称
	     * @return
	     */
	    public  boolean delFile(String filePath){
	        Boolean bool = false;
	        File file  = new File(ctx.getRealPath(filePath));
	        try {
	            if(file.exists()){
	                file.delete();
	                bool = true;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return bool;
	    }
	    
	    
	    public  String readFile(String filePath){
	    	StringBuffer sb = new StringBuffer();
	    	BufferedReader buffer = null;
	    	String strLine = "";
	    	sb.delete(0, sb.length());
	    	File file = new File(ctx.getRealPath(filePath));
	    	try{
	    		buffer = new BufferedReader(new FileReader(file)); 
	    		while((strLine = buffer.readLine()) != null){
	    			sb = sb.append(strLine);
	    		}
	    		
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}finally{
	    		if(buffer != null){
	    		  try {
					buffer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    		}
	    	}
	    	return sb.toString();
	    }
	    

}
