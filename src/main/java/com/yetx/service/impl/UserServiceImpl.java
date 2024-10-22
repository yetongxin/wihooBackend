package com.yetx.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yetx.dao.*;
import com.yetx.dto.OtherUserDTO;
import com.yetx.dto.UserDTO;
import com.yetx.enums.AuthErrorEnum;
import com.yetx.enums.UserErrorEnum;
import com.yetx.exception.MyException;
import com.yetx.pojo.CollectAnswer;
import com.yetx.pojo.Question;
import com.yetx.pojo.User;
import com.yetx.pojo.UserFan;
import com.yetx.service.RedisService;
import com.yetx.service.UserService;
import com.yetx.utils.IdUtils;
import com.yetx.utils.RedisOperator;
import com.yetx.vo.*;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    private UserFanMapper userFanMapper;

    @Value("${wechat.appid}")
    private String appid;
    @Value("${wechat.appsecret}")
    private String appsecret;
    @Value("${avatar.space}")
    private String avatarSpace;
    @Value("${bgimg.space}")
    private String bgimgSpace;
    private String openid;
    private String session_key;

    public static String getRandomFileName() {

        SimpleDateFormat simpleDateFormat;

        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        Date date = new Date();

        String str = simpleDateFormat.format(date);

        Random random = new Random();

        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数

        return rannum + str;// 当前时间
    }
    @Override
    @Transactional
    public User register(String token, String nickname, String avatar) {
        String openid = redisService.findOpenidByToken(token);
        User user = userMapper.selectByOpenid(openid);
        //下载头像到本地
        URL url = null;
        OutputStream os = null;InputStream is=null;
        String PathToDB = "/" + user.getId() + "/face/" + getRandomFileName()+".jpg";
        String finalPath = avatarSpace + PathToDB ;
        try {
            url = new URL(avatar);
            // 打开连接
            URLConnection con = url.openConnection();
            // 输入流
            is = con.getInputStream();
            File finalFile = new File(finalPath);
            // 输出的文件流
            if (finalFile.getParentFile() == null || !finalFile.getParentFile().isDirectory()) {
                finalFile.getParentFile().mkdirs();//如果该用户是第一次上传，需要创建一个文件夹给他
            }
            os = new FileOutputStream(finalFile);
            IOUtils.copy(is,os);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PathToDB = "/images"+PathToDB;
        userMapper.updateNickNameAndAvatarByOpenid(openid,nickname,PathToDB);
        User res = userMapper.selectByOpenid(openid);
        return res;
    }
    @Override
    @Transactional
    public UserLoginStatusVO login(String code) {
        logger.info("code:{}",code);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
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

        logger.info("session_key={}",session_key);
        logger.info("openid={}",openid);

        User user = userMapper.selectByOpenid( openid );
        boolean first = true;
        if(user==null){
            //入库
            user = new User();
            user.setId(UUID.randomUUID().toString().replace("-",""));
            user.setFollowCounts(0);
            user.setFansCounts(0);
            user.setNickname("null_name");
            user.setAvatar("null_avatar");user.setBgimage("/bgimg/default.jpg");
            user.setLikeCounts(0);
            user.setOpenid(openid);
            user.setCollectCounts(0);

            userMapper.insert(user);
        }
        else {
            first = false;
            logger.info("用户已存在");
        }

        String skey = UUID.randomUUID().toString();
        JSONObject sessionObj = new JSONObject();
        sessionObj.put( "openid",openid );
        sessionObj.put( "sessionKey",session_key );
        //设置为1小时
        redisOperator.set("TOKEN:"+skey,sessionObj.toJSONString(),60*60);
        if(first)
            return new UserLoginStatusVO(skey,first,null);
        else{
            if(user.getNickname().equals("null_name"))
                return new UserLoginStatusVO(skey,true,null);
            else
                return new UserLoginStatusVO(skey,false,user);
        }

    }
//    @Override
//    @Transactional
//    public UserInfoVO login(String code) {
//        logger.info("code:{}",code);
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        String resultString = "";
//        CloseableHttpResponse response = null;
//
//        //String url="https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+appsecret+"&js_code="+code+"&grant_type=authorization_code";
//
//
//        String url="https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+appsecret+"&js_code="+code+"&grant_type=authorization_code";
//
//        try {
//            // 创建uri
//            URIBuilder builder = new URIBuilder(url);
//            URI uri = builder.build();
//
//            // 创建http GET请求
//            HttpGet httpGet = new HttpGet(uri);
//
//            // 执行请求
//            response = httpclient.execute(httpGet);
//            // 判断返回状态是否为200
//            if (response.getStatusLine().getStatusCode() == 200) {
//                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("获得response:"+resultString);
//        // 解析json
//        JSONObject jsonObject = (JSONObject) JSONObject.parse(resultString);
//        session_key = jsonObject.get("session_key")+"";
//        openid = jsonObject.get("openid")+"";
//
//        System.out.println("session_key=="+session_key);
//        System.out.println("openid=="+openid);
//
//
//
//        User user = userMapper.selectByOpenid( openid );
//        if(user==null){
//            //入库
//            user = new User();
//            user.setId(UUID.randomUUID().toString());
//            user.setFollowCounts(0);
//            user.setFansCounts(0);
//            user.setNickname("null_name");
//            user.setAvatar("null_avatar");
//            user.setLikeCounts(0);
//            user.setOpenid(openid);
//            user.setCollectCounts(0);
//
//            userMapper.insert(user);
//        }
//        else {
//            logger.info("用户已存在");
//        }
//        String skey = UUID.randomUUID().toString();
//        JSONObject sessionObj = new JSONObject();
//        sessionObj.put( "openid",openid );
//        sessionObj.put( "sessionKey",session_key );
//        //设置为1天
//        redisOperator.set("TOKEN:"+skey,sessionObj.toJSONString(),60*60*24);
//        return new UserInfoVO(skey,user.getNickname(),user.getAvatar(),user.getFollowCounts(),
//                user.getFansCounts(),user.getCollectCounts(),user.getLikeCounts());
//    }

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
    @Transactional
    public String updateAvatar(String token, MultipartFile[] files) {

        //定义一个要存储到磁盘的位置
        String fileSpace = avatarSpace;
        String openid = redisService.findOpenidByToken(token);
        User user = userMapper.selectByOpenid(openid);
        String PathToDB = "/" + user.getId() + "/face";
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
                    PathToDB = "/images"+PathToDB;//资源映射
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
    public String updateBgimg(String token, MultipartFile[] files) {
        //定义一个要存储到磁盘的位置
        String fileSpace = bgimgSpace;
        String openid = redisService.findOpenidByToken(token);
        User user = userMapper.selectByOpenid(openid);
        String PathToDB = "/" + user.getId() + "/bg";
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
                    PathToDB = "/bgimg"+PathToDB;//资源映射
                    user.setBgimage(PathToDB);
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
    public OtherUserVO findOtherUsrByUserId(String userId) {
        if(StringUtils.isEmpty(userId))
            throw new MyException(UserErrorEnum.USER_ID_NULL);
        User user = userMapper.selectByPrimaryKey(userId);
        if(user==null)
            throw new MyException(UserErrorEnum.USER_NOT_FOUND);

        OtherUserVO otherUserVO = new OtherUserVO(user);
        //otherUserVO.setOfArticles(articleMapper.selectOtherUserArticle(userId));
        //otherUserVO.setOfAnswers(answerMapper.selectByUserId(userId));
        return otherUserVO;
    }

    @Override
    public List<OtherUserDTO> findAllFollow(String token) {
        String openid = redisService.findOpenidByToken(token);
        if(StringUtils.isEmpty(openid))
            throw new MyException(AuthErrorEnum.OPENID_NOT_FIND);
        UserDTO userDTO = userMapper.selectFollowsByOpenid(openid);
        if(userDTO!=null)
            return userDTO.getFollowUsers();
        //List<>
        return null;
    }

    @Override
    public PageVO findAllArticle(String token, Integer staPage, Integer pageSize) {

        String openid = redisService.findOpenidByToken(token);
        if(StringUtils.isEmpty(openid))
            throw new MyException(UserErrorEnum.TOKEN_NOT_FIND);
        String userId = userMapper.selectUserIdByOpenId(openid);


        Page page = PageHelper.startPage(staPage,pageSize);
        //page.setCountColumn("DISTINCT a.id");
        List<ArticleVO> list = articleMapper.selectAllByUserIdPaged(userId);
        PageInfo pageInfo = new PageInfo(list);

        //返回前端需要的PageVO
        PageVO pageVO = new PageVO();
        pageVO.setCurData(pageInfo.getList());
        pageVO.setPageNum(pageInfo.getPages());
        pageVO.setCurPage(staPage);
        pageVO.setRecords(pageInfo.getTotal());

        return pageVO;
    }

    //TODO:修改为USER_ID
    @Override
    public PageVO findAllQuestion(String token, Integer staPage, Integer pageSize) {
        String openid = redisService.findOpenidByToken(token);
        String userId = userMapper.selectUserIdByOpenId(openid);
        PageHelper.startPage(staPage,pageSize);
        List<QuestionVO> list = questionMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(list);

        //返回前端需要的PageVO
        PageVO pageVO = new PageVO();
        pageVO.setCurData(pageInfo.getList());
        pageVO.setPageNum(pageInfo.getPages());
        pageVO.setCurPage(staPage);
        pageVO.setRecords(pageInfo.getTotal());
        return pageVO;
    }
//    @Override
//    public PageVO findAllQuestion(String token, Integer staPage, Integer pageSize) {
//
//        String openid = redisService.findOpenidByToken(token);
//        PageHelper.startPage(staPage,pageSize);
//        List<Question> list = questionMapper.selectByOpenid(openid);
//        PageInfo pageInfo = new PageInfo(list);
//
//        //返回前端需要的PageVO
//        PageVO pageVO = new PageVO();
//        pageVO.setCurData(pageInfo.getList());
//        pageVO.setPageNum(pageInfo.getPages());
//        pageVO.setCurPage(staPage);
//        pageVO.setRecords(pageInfo.getTotal());
//        return pageVO;
//    }
    @Override
    public PageVO findAllAnswer(String token, Integer staPage, Integer pageSize) {

        String openid = redisService.findOpenidByToken(token);
        String userId = userMapper.selectUserIdByOpenId(openid);
        PageHelper.startPage(staPage,pageSize);
        List<AnswerVO> list = answerMapper.selectByUserId(userId);
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
    public List<CollectAnswerVO> findAllCollectAnswer(String token){
        String openid = redisService.findOpenidByToken(token);
        if(StringUtils.isEmpty(openid))
            throw new MyException(AuthErrorEnum.TOKEN_NOT_FIND);
        String userId = userMapper.selectUserIdByOpenId(openid);
        List<AnswerVO> answerVOList = answerMapper.selectCollectAnswer(userId);
        List<CollectAnswerVO> collectAnswerVOs = new ArrayList<>();
        for(AnswerVO answerVO :answerVOList){
            Question question = questionMapper.selectByPrimaryKey(answerVO.getQuestionId());

            collectAnswerVOs.add(new CollectAnswerVO(question.getId(),question.getTitle(),answerVO));
        }
        return collectAnswerVOs;
    }

    @Override
    public List<QuestionVO> findAllFocusQuestion(String token) {
        String openid = redisService.findOpenidByToken(token);
        if(StringUtils.isEmpty(openid))
            throw new MyException(AuthErrorEnum.TOKEN_NOT_FIND);
        String userId = userMapper.selectUserIdByOpenId(openid);

        return questionMapper.selectFocusQuestion(userId);
    }

    @Override
    public Boolean handlefollowOther(String token, String otherUserId, Integer status){
        String openid = redisService.findOpenidByToken(token);
        if(StringUtils.isEmpty(openid))
            throw new MyException(AuthErrorEnum.TOKEN_NOT_FIND);

        String userId = userMapper.selectUserIdByOpenId(openid);
        boolean res = true;
        boolean isFollowed = userFanMapper.countIfFollow(userId, otherUserId) != 0;
        if(status==1){//想要关注
            if(!isFollowed){
                UserFan userFan = new UserFan(IdUtils.getNewId(),otherUserId,userId);
                userFanMapper.insert(userFan);
            }
            else
                res = false;
            //else doNothing
        }
        else{//想要取消关注
            if(isFollowed){
                userFanMapper.deleteUserFan(userId,otherUserId);
            }
            else
                res = false;
            //else doNothing
        }
        return res;
    }
    // 以下四个是他人页面需要用到的service
    @Override
    public PageVO findOtherArticle(String userId, Integer staPage, Integer pageSize) {

        if(StringUtils.isEmpty(userId))
            throw new MyException(UserErrorEnum.USER_ID_NULL);
        Page page = PageHelper.startPage(staPage,pageSize);
        List<ArticleVO> list = articleMapper.selectAllByUserIdPaged(userId);
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
    public PageVO findOtherQuestion(String userId, Integer staPage, Integer pageSize) {
        if(StringUtils.isEmpty(userId))
            throw new MyException(UserErrorEnum.USER_ID_NULL);
        PageHelper.startPage(staPage,pageSize);
        List<QuestionVO> list = questionMapper.selectByUserId(userId);
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
    public PageVO findOtherAnswer(String userId, Integer staPage, Integer pageSize) {
        if(StringUtils.isEmpty(userId))
            throw new MyException(UserErrorEnum.USER_ID_NULL);
        PageHelper.startPage(staPage,pageSize);
        List<AnswerVO> list = answerMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(list);

        //未来改成表连接
        List<AnswerVO> answerVOList = pageInfo.getList();
        List<CollectAnswerVO> collectAnswerList = new ArrayList<>();
        for(AnswerVO answerVO :answerVOList){
            Question question = questionMapper.selectByPrimaryKey(answerVO.getQuestionId());
            collectAnswerList.add(new CollectAnswerVO(question.getId(),question.getTitle(),answerVO));
        }
        //返回前端需要的PageVO
        PageVO pageVO = new PageVO();
        pageVO.setCurData(collectAnswerList);
        pageVO.setPageNum(pageInfo.getPages());
        pageVO.setCurPage(staPage);
        pageVO.setRecords(pageInfo.getTotal());
        return pageVO;
    }

    @Override
    public List<CollectAnswerVO> findOtherCollectAnswer(String userId) {
        if(StringUtils.isEmpty(userId))
            throw new MyException(UserErrorEnum.USER_ID_NULL);
        List<AnswerVO> answerVOList = answerMapper.selectCollectAnswer(userId);
        List<CollectAnswerVO> collectAnswerVOs = new ArrayList<>();
        for(AnswerVO answerVO :answerVOList){
            Question question = questionMapper.selectByPrimaryKey(answerVO.getQuestionId());
            collectAnswerVOs.add(new CollectAnswerVO(question.getId(),question.getTitle(),answerVO));
        }
        return collectAnswerVOs;
    }
}
