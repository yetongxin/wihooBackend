package com.yetx.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yetx.dao.AnswerMapper;
import com.yetx.dao.ArticleMapper;
import com.yetx.dao.QuestionMapper;
import com.yetx.dao.UserMapper;
import com.yetx.dto.UserDTO;
import com.yetx.enums.AuthErrorEnum;
import com.yetx.exception.MyException;
import com.yetx.pojo.Answer;
import com.yetx.pojo.Article;
import com.yetx.pojo.Question;
import com.yetx.pojo.User;
import com.yetx.service.RedisService;
import com.yetx.service.UserService;
import com.yetx.utils.RedisOperator;
import com.yetx.vo.PageVO;
import com.yetx.vo.UserInfoVO;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private RedisOperator redisOperator;
    @Autowired
    private RedisService redisService;

    @Value("${wechat.appid}")
    private String appid;
    @Value("${wechat.appsecret}")
    private String appsecret;
    @Value("${avatar.space}")
    private String avatarSpace;
    private String openid;
    private String session_key;

    @Override
    public UserInfoVO login(String code, String nickname, String avatar) {
        logger.info("code:{}",code);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;

        //String url="https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+appsecret+"&js_code="+code+"&grant_type=authorization_code";


        String url="https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+appsecret+"&js_code="+code+"&grant_type=authorization_code";

        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("获得response:"+resultString);
        // 解析json
        JSONObject jsonObject = (JSONObject) JSONObject.parse(resultString);
        session_key = jsonObject.get("session_key")+"";
        openid = jsonObject.get("openid")+"";

        System.out.println("session_key=="+session_key);
        System.out.println("openid=="+openid);



        User user = userMapper.selectByOpenid( openid );
        if(user==null){
            //入库
            user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setFollowCounts(0);
            user.setFansCounts(0);
            user.setNickname(nickname);
            user.setAvatar(avatar);
            user.setLikeCounts(0);
            user.setOpenid(openid);
            user.setCollectCounts(0);

            userMapper.insert(user);
        }
        else {
            logger.info("用户已存在");
        }
        String skey = UUID.randomUUID().toString();
        JSONObject sessionObj = new JSONObject();
        sessionObj.put( "openid",openid );
        sessionObj.put( "sessionKey",session_key );
        //设置为1天
        redisOperator.set("TOKEN:"+skey,sessionObj.toJSONString(),60*60*24);
        return new UserInfoVO(skey,user.getNickname(),user.getAvatar(),user.getFollowCounts(),
                user.getFansCounts(),user.getCollectCounts(),user.getLikeCounts());
    }

    @Override
    public List<User> findAllUser() {
        //userMapper.selectByPrimaryKey("1a2b3c4d5e");
        for (int i=0;i<3;++i){
            System.out.println("hello");
        }
        return null;

    }

    @Override
    public User findByOpenid(String openid) {
        return userMapper.selectByOpenid(openid);
    }

    @Override
    public String updateNickname(String token, String nickname){
        String openid = redisService.findOpenidByToken(token);

        User user = userMapper.selectByOpenid(openid);
        user.setNickname(nickname);
        userMapper.updateByPrimaryKeySelective(user);
        return nickname;
    }

    @Override
    public String updateAvatar(String token, MultipartFile[] files) {

        //定义一个要存储到磁盘的位置
        String fileSpace = avatarSpace;
        String openid = redisService.findOpenidByToken(token);
        String PathToDB = "/" + openid + "/face";
        //接下来将文件写入服务器磁盘，并将文件的相对路径存入数据库中
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            if (files != null && files.length > 0) {//简单判断是否有文件传输进来
                String fileName = files[0].getOriginalFilename();

                if (!StringUtils.isEmpty(fileName)) {
                    String finalPath = fileSpace + PathToDB + "/" + fileName;
                    PathToDB = PathToDB + "/" + fileName;//将相对路径存储到数据库中,该路径需先加上文件名
                    File finalFile = new File(finalPath);
                    if (finalFile.getParentFile() == null || !finalFile.getParentFile().isDirectory()) {
                        finalFile.getParentFile().mkdirs();//如果该用户是第一次上传，需要创建一个文件夹给他
                    }
                    fileOutputStream = new FileOutputStream(finalFile);
                    inputStream = files[0].getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                    User user = userMapper.selectByOpenid(openid);
                    user.setAvatar(PathToDB);
                    userMapper.updateByPrimaryKeySelective(user);
                    return PathToDB;
                }

            } else {
                return "";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }


    @Override
    public UserDTO findAllFollow(String token) {
        String openid = redisService.findOpenidByToken(token);
        if(StringUtils.isEmpty(openid))
            throw new MyException(AuthErrorEnum.OPENID_NOT_FIND);
        UserDTO userDTO = userMapper.selectFollowsByOpenid(openid);
        //List<>
        return userDTO;
    }

    @Override
    public PageVO findAllArticle(String token, Integer staPage, Integer pageSize) {

        String openid = redisService.findOpenidByToken(token);
        PageHelper.startPage(staPage,pageSize);
        List<Article> list = articleMapper.selectByOpenid(openid);
        PageInfo pageInfo = new PageInfo(list);

        //返回前端需要的PageVO
        PageVO pageVO = new PageVO();
        pageVO.setCurData(pageInfo.getList());
        pageVO.setPageNum(pageInfo.getPages());
        pageVO.setCurPage(staPage);
        pageVO.setRecords(pageInfo.getTotal());

        return pageVO;
    }
    @Override
    public PageVO findAllQuestion(String token, Integer staPage, Integer pageSize) {

        String openid = redisService.findOpenidByToken(token);
        PageHelper.startPage(staPage,pageSize);
        List<Question> list = questionMapper.selectByOpenid(openid);
        PageInfo pageInfo = new PageInfo(list);

        //返回前端需要的PageVO
        PageVO pageVO = new PageVO();
        pageVO.setCurData(pageInfo.getList());
        pageVO.setPageNum(pageInfo.getPages());
        pageVO.setCurPage(staPage);
        pageVO.setRecords(pageInfo.getTotal());
        return pageVO;
    }
    @Override
    public PageVO findAllAnswer(String token, Integer staPage, Integer pageSize) {

        String openid = redisService.findOpenidByToken(token);
        PageHelper.startPage(staPage,pageSize);
        List<Answer> list = answerMapper.selectByOpenid(openid);
        PageInfo pageInfo = new PageInfo(list);

        //返回前端需要的PageVO
        PageVO pageVO = new PageVO();
        pageVO.setCurData(pageInfo.getList());
        pageVO.setPageNum(pageInfo.getPages());
        pageVO.setCurPage(staPage);
        pageVO.setRecords(pageInfo.getTotal());
        return pageVO;
    }
}
