package com.huihoo.bigdata.common.util;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
	
	private int k=1;//定义递归次数变量
	
	private void zip(String zipFileName,File inputFile) throws Exception{
		System.out.println("压缩中...");
		ZipOutputStream out=new ZipOutputStream(new FileOutputStream(zipFileName));
		BufferedOutputStream bo=new BufferedOutputStream(out);
		zip(out,inputFile,inputFile.getName(),bo);
		bo.close();
		out.close();//输出流关闭
		System.out.println("压缩完成");
	}
	
	private void zip(ZipOutputStream out,File f,String base,BufferedOutputStream bo) throws Exception{
		if(f.isDirectory()){
			File[] fl=f.listFiles();
			if(fl.length==0){
				out.putNextEntry(new ZipEntry(base+"/"));
				System.out.println(base+"/");
			}
			for(int i=0;i<fl.length;i++){
				zip(out,fl[i],base+"/"+fl[i].getName(),bo);//递归遍历子文件
			}
			System.out.println("第"+k+"次递归");
			k++;
		}else {
			out.putNextEntry(new ZipEntry(base)); //创建zip压缩进入点base
			System.out.println(base);
			//FileInputStream in=new FileInputStream(f)；
		}
	}
	
	public static void main(String[] args){
		zipDecompressing("D:/118.123.199.58-AppFront58.20150715.1436954700483.zip",
				"D:/test/");
	}
	
	/**
	 * 解压zip文件到指定目录下
	 * @param file1
	 * @param filePath
	 */
	private static void zipDecompressing(String file1,String filePath){
		
		try{
	        ZipInputStream zin=new ZipInputStream(new FileInputStream(file1));
	        BufferedInputStream bin=new BufferedInputStream(zin);
	        String parent=filePath;
	        File fout=null;
	        ZipEntry entry;
	        try{
	        	while((entry=zin.getNextEntry())!=null && !entry.isDirectory()){
	        		fout=new File(parent,entry.getName());
	        		if(!fout.exists()){
	        			(new File(fout.getParent())).mkdirs();
	        		}
	        		FileOutputStream out=new FileOutputStream(fout);
	        		BufferedOutputStream bout=new BufferedOutputStream(out);
	        		int b;
	        		while((b=bin.read())!=-1){
	        			bout.write(b);
	        		}
	        		bout.close();
	        		out.close();
	        		System.out.println(fout+"解压成功");
	        	}
	        	bin.close();
	        	zin.close();
	        	
	        	
	        }catch(Exception ex){
	        	ex.printStackTrace();
	        }
	        
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}
}
