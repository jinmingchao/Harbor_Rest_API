

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.eclipse.core.runtime.Platform;


import cn.com.agree.ab.a4.server.lfc.cloudserver.bean.ImageBean;
import cn.com.agree.ab.a4.server.lfc.cloudserver.conf.CloudConf;
import cn.com.agree.ab.a4.server.lfc.cloudserver.conf.CloudResCode;
import cn.com.agree.ab.a4.server.lfc.cloudserver.conf.CloudType;
 
import com.github.dockerjava.api.command.BuildImageCmd;
import com.github.dockerjava.api.model.AuthConfig;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import com.github.dockerjava.core.command.PushImageResultCallback;

/**
 * 
 * @author guxuezheng
 * @上午10:00:19
 * @Description 镜像管理工具类
 */
public class CloudImagesUtils {
	AuthConfig authConfig = new AuthConfig().withRegistryAddress(CloudConf.registry_name);
	HttpClient client = new HttpClient();

	public String findImageList(String number) {
		String url = CloudConf.image_repertory_url + String.format(CloudType.ImagelistUrl, 1000);
		GetMethod method = new GetMethod(url);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		String imageList = "";
		try {
			int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}
			byte[] responseBody = method.getResponseBody();
			imageList = new String(responseBody);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return imageList;
	}

	public String findImageinfo(String imagename) {
		String url = CloudConf.image_repertory_url + String.format(CloudType.ImageinfoUrl, imagename);
		GetMethod method = new GetMethod(url);
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		String imageinfoJson = null;
		try {
			int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
			}
			byte[] responseBody = method.getResponseBody();
			imageinfoJson = new String(responseBody);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return imageinfoJson;
	}

	public List<ImageBean> getImagesList(String number) {
		try {
			// 1.number
			//   number是必传参数,没有报10001
			//   number取值:-1U[1,Integer.MAX_VALUE],不符合报10020
			//   其他错报10000
			if (number == null) {
				CloudConf.setResponse(CloudResCode.CODE_10001, CloudResCode.MESSAGE_10001);
				return null;
			}
			Integer number_1 = Integer.parseInt(number);
			if (number_1 < 1 && number_1 != -1) {
				CloudConf.setResponse(CloudResCode.CODE_10020, CloudResCode.MESSAGE_10020);
				return null;
			}
		} catch (Exception e) {
			CloudConf.setResponse(CloudResCode.CODE_10000, CloudResCode.MESSAGE_10000);
			return null;
		}
		JsonHelper jsonHelper = new JsonHelper();
		String imagesJson = this.findImageList(number);
		Map<String, Object> imagesMap = (Map<String, Object>) jsonHelper.jsonToMap(imagesJson);
		List<String> imageslist = (List<String>) imagesMap.get("repositories");
		List<ImageBean> reqList = new ArrayList<ImageBean>();
		for (String imagename : imageslist) {
			String imageinfo = this.findImageinfo(imagename);
			ImageBean image = (ImageBean) jsonHelper.jsonToBean(imageinfo, ImageBean.class);
			if (image.getTags() != null)
				reqList.add(image);
		}
		return reqList;

	}

	public Map<String, Object> listToMap(List<ImageBean> reqList, JsonHelper jsonHelper) {

		String json = jsonHelper.objectToJson(reqList);
		Map<String, Object> jsonToMap = (Map<String, Object>) jsonHelper.jsonToMap(json);
		return jsonToMap;
	}

	/**
	 * 构建镜像
	 * 
	 * @param sourcePath
	 * @return 构建镜像id
	 */
	public String buildImage(String sourcePath, String imageName_tag) {
		try {
			BuildImageCmd buildImageCmd = CloudConf.getDockerClient().buildImageCmd().withTag(imageName_tag);
			buildImageCmd.withDockerfile(new File(getSourceURL(sourcePath) + File.separator + CloudType.dockerfile));
			buildImageCmd.withBaseDirectory(new File(getSourceURL(sourcePath)));
			String imageId = buildImageCmd.withNoCache(true).exec(new BuildImageResultCallback()).awaitImageId();
			return imageId;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String getSourceURL(String sourcePath) {
		File f = new File(Platform.getConfigurationLocation().getURL().getFile());
		return f.getParentFile().getPath() + "/IMAGE_SOURCE/" + sourcePath;
	}

	public boolean pushImageToRegistry(String imageID, String imageName_tag) {
		String[] split = imageName_tag.split(":");
		if (split.length < 2) {
			return false;
		}
		String imageName = CloudConf.registry_name + split[0];
		CloudConf.getDockerClient().tagImageCmd(imageID, imageName, split[1]).exec();
		try {
			CloudConf.getDockerClient().pushImageCmd(imageName).withTag(split[1]).withAuthConfig(authConfig)
					.exec(new PushImageResultCallback()).awaitSuccess();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			// 清理镜像
			cleanImageOnHost(imageName_tag);
		}
	}

	private void cleanImageOnHost(String imageName_tag) {
		CloudConf.getDockerClient().removeImageCmd(CloudConf.registry_name + imageName_tag).exec();
		CloudConf.getDockerClient().removeImageCmd(imageName_tag).exec();
	}

	public boolean deleteImageOnRegistry(Map<String, Object> inArgMap) {
		String name_tag = (String) inArgMap.get("imageName_tag");
		// 1.name_tag
		// name_tag是必填参数,没有报10001
		// name_tag必须是Image:tag的格式,不符合报10002
		// 分别使用正则校验imageName/imageTag
		// imageName不合法报 10017
		// imageTag不合法报 10018
		// 其他错报10000
		try {
			ImageRegistryRegexUtils regex = new ImageRegistryRegexUtils();
			boolean res = false;
			if (!(res = regex.isMatchImageRegistryRex(name_tag)))
				return res;
		} catch (Exception e) {
			CloudConf.setResponse(CloudResCode.CODE_10000, CloudResCode.MESSAGE_10000);
			return false;
		}
		String[] split = name_tag.split(":");
		return deleteImageOnRegistry(split[0], split[1]);
	}

	public boolean deleteImageOnRegistry(String imageName, String imageTag) {
		String digest = getRegistryImageDigest(imageName, imageTag);
		if (digest == null)
			return false;
		return deleteImageBydigest(imageName, digest);
	}

	private String getRegistryImageDigest(String name, String tag) {
		String url = CloudConf.image_repertory_url + String.format(CloudType.image_manifests, name, tag);
		GetMethod method = new GetMethod(url);
		method.setRequestHeader("Accept", "application/vnd.docker.distribution.manifest.v2+json");
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
		try {
			int statusCode = client.executeMethod(method);
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
				return null;
			}
			String Digest = method.getResponseHeader("Docker-Content-Digest").getValue();
			System.out.println(Digest);
			return Digest;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			method.releaseConnection();
		}
	}

	private boolean deleteImageBydigest(String name, String digest) {
		DeleteMethod method = null;
		try {
			method = new DeleteMethod(buildRegistryManifestsURL(name, digest));
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler(3, false));
			int statusCode = client.executeMethod(method);
			if (statusCode != 202) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			method.releaseConnection();
		}
	}

	private String buildRegistryManifestsURL(String name, String tag) {
		return CloudConf.image_repertory_url + String.format(CloudType.image_manifests, name, tag);
	}
}
