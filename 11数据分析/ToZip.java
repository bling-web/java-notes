package testMaven.com.cc.maven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;



/**	
 * 递归压缩文件
 *
 */
public class ToZip {
	private ZipOutputStream stream=null;
	public static void main(String[] args) throws IOException {
		String path="E:\\test\\1\\test2.txt";
		String toZipPath="E:\\";
		ToZip zip = new ToZip();
		zip.toZip(path,toZipPath);
	}
	public void toZip(String path,String toZipPath) throws IOException {
		//判断输入
		if(path==null||path.length()==0) {
			throw new RuntimeException("输入路径为空");
		}
		int before=path.lastIndexOf("\\");
		//使用正则表达式判断是否压缩的是文件
		String reg="(\\.[a-z]{3,4})$";
		//将正则表达式封装成对象
		Pattern pattern = Pattern.compile(reg);
		//将规则与目标字符串进行匹配,并封装成对象
		Matcher matcher = pattern.matcher(path);
		//find()方法返回boolean,如果匹配到就返回true.
		if(matcher.find()) {
			int after=path.lastIndexOf(".");
			toZip(path, toZipPath,path.substring(before+1,after));
		}else {
			toZip(path, toZipPath,path.substring(before+1));
		}
	}
	public void toZip(String path,String toZipPath,String toZipName) throws IOException {
		//判断输入
		if(path==null||path.length()==0) {
			throw new RuntimeException("输入路径为空");
		}
		File file = new File(path);
		if(!file.exists()) {
			throw new RuntimeException("该路径下没有对应的文件!");
		}
		File zipPath = new File(toZipPath);
		if(!zipPath.exists()){
			zipPath.mkdirs();
		}
		if(!toZipPath.endsWith("\\"))
			toZipPath += "\\";
		//初始化
		stream=new ZipOutputStream(new FileOutputStream(toZipPath+toZipName+".zip"));
		//递归调用处理
		dirToZip(file,"");
		//关闭zip流
		stream.close();
	}
	
	private void dirToZip(File file,String base) throws IOException  {
		// TODO Auto-generated method stub
		FileInputStream inputStream=null;
		if(file.isFile()) {
			inputStream = new FileInputStream(file);
			//如果直接压缩的是文件,那么需要获取文件名称.
			base = base.length()==0 ? file.getName() : base;				
			stream.putNextEntry(new ZipEntry(base));
			byte[] temp=new byte[1024];
			int len=0;
			while((len=inputStream.read(temp))!=-1) {
				stream.write(temp,0,len);
			}
			stream.closeEntry();
			inputStream.close();
		}else {
			File[] listFiles = file.listFiles();
			for(int i=0;i<listFiles.length;i++) {
				File help = new File(file.getAbsolutePath()+"/"+listFiles[i].getName());
				//这个base就是压缩文件里面的层级目录,要一层一层递进,只要base按原有目录来,那么压缩后的文件就是原有的目录结构.
				if(listFiles[i].isFile())
					dirToZip(help,base+listFiles[i].getName());
				else
				    dirToZip(help,base+listFiles[i].getName()+"/");
			}
		}
	}
}
