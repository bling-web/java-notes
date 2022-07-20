package testMaven.com.cc.maven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.naming.InitialContext;

public class UnZip {

	public static void main(String[] args) throws IOException {
		String path="E:\\test.zip";
		String unZipPath="E:\\";
		if(init(path, unZipPath)) {
			unZip(path,unZipPath);
		}
	}
	
	private static boolean init(String path,String unZipPath) {
		// TODO Auto-generated method stub
		if(path==null||path.length()==0){
			throw new RuntimeException("输入的路径为空!");
		}
		if(!path.endsWith(".zip")) {
			throw new RuntimeException("该程序仅能解压以.zip结尾的文件!");
		}
		File file = new File(path);
		if(!file.exists()) {
			throw new RuntimeException("您输入的路径有误,找不到对应的文件,请检查后输入!");
		}
		return true;
	}

	/**
	 * 使用原有压缩文件名称进行解压
	 * @param path 原有压缩文件路径
	 * @param unZipPath 压缩后文件存放的路径
	 * @throws IOException
	 */
	public static void unZip(String path,String unZipPath) throws IOException {
		int before = path.lastIndexOf("\\");
		int after = path.lastIndexOf(".");
		String unZipFileName=path.substring(before+1,after);
		unZip(path,unZipPath,unZipFileName);
	}
	/**
	 * 使用自定义文件名称进行解压
	 * @param path
	 * @param unZipPath
	 * @param unZipFileName
	 * @throws IOException
	 */
	public static void unZip(String path,String unZipPath ,String unZipFileName) throws IOException {
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(new File(path)));
		ZipEntry entry = null; 
		while((entry=zipInputStream.getNextEntry())!=null) {
			File file = new File(unZipPath+unZipFileName+"/"+entry.getName());
			if(!file.exists()) {
				file.getParentFile().mkdirs();
				FileOutputStream outputStream = new FileOutputStream(file);
				byte[] help=new byte[1024];
				int len;
				while((len=zipInputStream.read(help))!=-1) {
					outputStream.write(help,0,len);
				}
				outputStream.close();
			}
			zipInputStream.closeEntry();
		}
		zipInputStream.close();
	}
}
