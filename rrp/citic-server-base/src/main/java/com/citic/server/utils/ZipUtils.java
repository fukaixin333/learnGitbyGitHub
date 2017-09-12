package com.citic.server.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    public ZipUtils(){

    }

    /**
     * 压缩一个文件或者目录
     * @param zipFileName 压缩后的文件名及路径
     * @param inputFile 需要被压缩的文件路径
     * @throws Exception
     */
    public static void zip(String zipFileName,String inputFile)throws Exception{
        zip(zipFileName,new File(inputFile));
    }

    /**
     *
     * @param zipFileName 压缩后的文件名及路径
     * @param inputFile 要被压缩的文件的输入流
     * @throws Exception
     */
    public static void zip(String zipFileName,File inputFile)throws Exception{
        ZipOutputStream out=new ZipOutputStream(new FileOutputStream(zipFileName));
        zip(out,inputFile,"");
        System.out.println("zip done");
        out.close();
    }

    /**
     * 用于压缩整个目录或者单个文件
     * @param out 源文件的输出流
     * @param f 目标压缩文件的输入流
     * @param base a
     * @throws Exception
     */
    public static  void zip(ZipOutputStream out,File f,String base)throws Exception{
        System.out.println("Zipping  "+f.getName());
        if (f.isDirectory())
        {
            File[] fl=f.listFiles();
            out.putNextEntry(new ZipEntry(base+"/"));
            for (int i=0;i<fl.length ;i++ )
            {
                zip(out,fl[i],base);
            }
        }
        else
        {
            base=base.length()==0?"":base+"/";
            out.putNextEntry(new ZipEntry(base+f.getName()));
            FileInputStream in=new FileInputStream(f);
            int b;
            //将read()读取数据的方式修改为read(byte[] b)
            byte[] buf=new byte[4096];
            while ((b=in.read(buf)) != -1)
                out.write(buf, 0,  b);
            in.close();
        }
    }
    

    /**
     * 压缩一组文件
     * @param zipFileName
     * @param fileList
     * @param base
     * @throws Exception
     */
    public static void zip(String zipFileName,ArrayList fileList,String base)throws Exception{
        //压缩文件流
        ZipOutputStream out=new ZipOutputStream(new FileOutputStream(zipFileName));
       // out.putNextEntry(new ZipEntry(base+"/"));
        for(int i=0;i<fileList.size();i++){
            String filename = (String)fileList.get(i);
            System.out.println("add to zip:"+filename);
            File file = new File(filename);
            zip(out,file,base);
            //
        }
        out.close();
    }
    

    /**
     * 解压缩
     * @param zipFileName 压缩文件
     * @param outputDirectory 目标路径
     * @throws Exception
     */
    public static void unzip(String zipFileName,String outputDirectory)throws Exception{
        ZipInputStream in=new ZipInputStream(new FileInputStream(zipFileName));
        ZipEntry z;
        while ((z=in.getNextEntry() )!= null)
        {
            System.out.println("unziping "+z.getName());
            if (z.isDirectory())
            {
                String name=z.getName();
                name=name.substring(0,name.length()-1);
                File f=new File(outputDirectory+File.separator+name);
                f.mkdir();
                System.out.println("mkdir "+outputDirectory+File.separator+name);
            }
            else{
                File f=new File(outputDirectory+File.separator+z.getName());
                f.createNewFile();
                FileOutputStream out=new FileOutputStream(f);
                int b;
                while ((b=in.read()) != -1)
                    out.write(b);
                out.close();
            }
        }

        in.close();
    }

    public static void main(String[] args)
    {
        try{
          
        }
        catch(Exception e){e.printStackTrace(System.out);}
    }
}
