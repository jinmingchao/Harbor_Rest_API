package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.junit.Test;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient.ListImagesParam;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Image;

public class SyncRegistryData {

	static DefaultDockerClient resourceDocker = new DefaultDockerClient("http://192.9.210.89:2375");
	static DefaultDockerClient targetDocker = new DefaultDockerClient("http://192.168.204.160:2375");
	HttpClient client = new HttpClient();
	@Test
	public void imageListTest() throws DockerException, InterruptedException{
//		List<Image> resourcelistImages = resourceDocker.listImages(ListImagesParam.allImages());
//		for (Image image : resourcelistImages) {
//			System.out.println(image);
//		}
		List<Image> targetlistImages = targetDocker.listImages(ListImagesParam.allImages());
//		for (Image image : targetlistImages) {
//			targetDocker.removeImage(image.repoTags().get(0));
//			System.out.println(image.repoTags().get(0) + " remove success!");
//		}
		System.out.println("target:"+targetlistImages);
	}
	@Test
	public void bakImageList(){
		List<ImageBean> imagesList = getImagesList("1000");
		
		pullResourceRegistryImages(imagesList);
		
		System.out.println(successList);
		System.out.println(failList);
	}
	private static List<String> successList = new ArrayList<String>();
	private static List<String> failList = new ArrayList<String>();
	public void pullResourceRegistryImages(List<ImageBean> imagesList){
		
		for (ImageBean imageBean : imagesList) {
			List<String> tags = imageBean.getTags();
			for (String tag : tags) {
				String imageName = "agreeregistry:5000/"+imageBean.getName()+":"+tag;
				System.out.println("begin pull image : "+imageName +".....");
				boolean flag = pullImage(imageName);
				if(flag){
					successList.add(imageName);
				}else{
					failList.add(imageName);
				}
				System.out.println("end");
			}
		}
	}
	
	private boolean pullImage(String imageName){
		try {
			targetDocker.pull(imageName);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<ImageBean> getImagesList(String number){
        JsonHelper jsonHelper = new JsonHelper();
        String imagesJson = this.findImageList(number);
        Map<String, Object> imagesMap = (Map<String, Object>) jsonHelper.jsonToMap(imagesJson);
        List<String> imageslist = (List<String>) imagesMap.get("repositories");
        List<ImageBean> reqList=new ArrayList<ImageBean>();
        for (String imagename : imageslist)
        {
            String imageinfo = this.findImageinfo(imagename);
            ImageBean image = (ImageBean)jsonHelper.jsonToBean(imageinfo, ImageBean.class);
            if(image.getTags()!=null)
            reqList.add(image);
        }
        return reqList;
    }
	
	
	  /**
     * 镜像及镜像仓库
     */
    private static String ImagelistUrl="/v2/_catalog?n=%s";
    private static String ImageinfoUrl="/v2/%s/tags/list";
    private static String resourceRegistry = "http://192.9.210.89:5000";
    private static String targetRegistry = "http://192.9.210.89:5000";
	
	public String findImageList(String number){
        String url = resourceRegistry+String.format(ImagelistUrl, 1000);
        GetMethod method = new GetMethod(url);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
                new DefaultHttpMethodRetryHandler(3, false));
        String imageList="";
        try {
          int statusCode = client.executeMethod(method);
          if (statusCode != HttpStatus.SC_OK) {
            System.err.println("Method failed: " + method.getStatusLine());
          }
          byte[] responseBody = method.getResponseBody();
          imageList=new String(responseBody);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          method.releaseConnection();
        }  
       return imageList;
    }
	
	public String findImageinfo(String imagename){
        String url = resourceRegistry+String.format(ImageinfoUrl, imagename);
        GetMethod method = new GetMethod(url);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
                new DefaultHttpMethodRetryHandler(3, false));
        String imageinfoJson = null;
        try {
          int statusCode = client.executeMethod(method);
          if (statusCode != HttpStatus.SC_OK) {
            System.err.println("Method failed: " + method.getStatusLine());
          }
          byte[] responseBody = method.getResponseBody();
          imageinfoJson=new String(responseBody);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          method.releaseConnection();
        }  
       return imageinfoJson;
    }
}
