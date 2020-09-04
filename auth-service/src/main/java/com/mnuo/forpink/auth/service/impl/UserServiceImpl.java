package com.mnuo.forpink.auth.service.impl;

import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.mnuo.forpink.auth.common.ResponseType;
import com.mnuo.forpink.auth.common.UrlConstant;
import com.mnuo.forpink.auth.config.ClientEncodeConfig;
import com.mnuo.forpink.auth.config.ServerConfig;
import com.mnuo.forpink.auth.domain.Token;
import com.mnuo.forpink.auth.dto.LoginUserDto;
import com.mnuo.forpink.auth.module.Users;
import com.mnuo.forpink.auth.respository.UsersRespository;
import com.mnuo.forpink.auth.service.UserService;
import com.mnuo.forpink.auth.utils.Assert;
import com.mnuo.forpink.auth.utils.BeanUtils;
import com.mnuo.forpink.auth.utils.RedisUtil;
import com.mnuo.forpink.auth.vo.LoginUserVO;
import com.mnuo.forpink.auth.vo.Response;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

	@Autowired
	ClientEncodeConfig clientEncodeConfig;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	RedisUtil redisUtil;
	
	@Autowired
	UsersRespository usersRespository;
	@Autowired
	ServerConfig serverConfig;
	
	@Value("${server.servlet.context-path}")
	String serviceName;
	
	@Override
	public void addUser(@Valid Users userDTO) {
		
		
	}

	@Override
	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(@Valid Users userDTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Response findAllUserVO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response login(LoginUserDto loginUserDTO) {
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		params.add("client_id", clientEncodeConfig.getClientId());
		params.add("client_secret", clientEncodeConfig.getClientSecret());
		params.add("username", loginUserDTO.getAccount());
		params.add("password", loginUserDTO.getPassword());
		params.add("grant_type", clientEncodeConfig.getGrantType()[0]);
		
		Token token = null;
        try {
            //因为oauth2本身自带的登录接口是"/oauth/token"，并且返回的数据类型不能按我们想要的去返回
            //但是我的业务需求是，登录接口是"user/login"，由于我没研究过要怎么去修改oauth2内部的endpoint配置
            //所以这里我用restTemplate(HTTP客户端)进行一次转发到oauth2内部的登录接口，比较简单粗暴
        	token = restTemplate.postForObject(serverConfig.getUrl() + "/" + serviceName + UrlConstant.LOGIN_URL.getUrl(), params, Token.class);
        	LoginUserVO loginUserVo = redisUtil.get(token.getValue(), LoginUserVO.class);
        	if(loginUserVo != null){
        		//登录的时候，判断该用户是否已经登录过了
                //如果redis里面已经存在该用户已经登录过了的信息
                //我这边要刷新一遍token信息，不然，它会返回上一次还未过时的token信息给你
                //不便于做单点维护
        		
        		token = oauthRefreshToken(loginUserVo.getRefreshToken());
        		redisUtil.deleteCache(loginUserVo.getAccessToken());
        	}
        	//这里我拿到了登录成功后返回的token信息之后，我再进行一层封装，最后返回给前端的其实是LoginUserVO
        }catch (Exception e) {
        	log.error(e.getMessage(), e);
			Assert.throwException("username or password error");
		}
        LoginUserVO loginUserVo = new LoginUserVO();
        Users userPO = usersRespository.findByUserName(loginUserDTO.getAccount());
        BeanUtils.copyPropertiesIgnoreNull(userPO, loginUserVo);
        loginUserVo.setPassword(userPO.getPassWord());
        loginUserVo.setAccessToken(token.getValue());
        loginUserVo.setAccessTokenExpiresIn(token.getExpiresIn());
        loginUserVo.setAccessTokenExpiration(token.getExpiration());
        loginUserVo.setExpired(token.isExpired());
        loginUserVo.setScope(token.getScope());
        loginUserVo.setTokenType(token.getTokenType());
        loginUserVo.setRefreshToken(token.getRefreshToken().getValue());
        loginUserVo.setRefreshTokenExpiration(token.getRefreshToken().getExpiration());
        //存储登录的用户
        redisUtil.set(loginUserVo.getAccessToken(),loginUserVo,TimeUnit.HOURS.toSeconds(1));
        return Response.success(loginUserVo);
	}
	/**
     * @description oauth2客户端刷新token
     * @param refreshToken
     * @date 2019/03/05 14:27:22
     * @author Zhifeng.Zeng
     * @return
     */
    private Token oauthRefreshToken(String refreshToken) {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientEncodeConfig.getClientId());
		params.add("client_secret", clientEncodeConfig.getClientSecret());
		params.add("refresh_token", refreshToken);
		params.add("grant_type", clientEncodeConfig.getGrantType()[1]);
       
        Token token = null;
        try {
            token = restTemplate.postForObject(serverConfig.getUrl() + "/" + serviceName + UrlConstant.LOGIN_URL.getUrl(), params, Token.class);
        } catch (RestClientException e) {
            try {
            	log.error("oauthRefreshToken error: ", e);
                //此处应该用自定义异常去返回，在这里我就不去具体实现了
                throw new Exception(ResponseType.REFRESH_TOKEN_INVALID.getMessage());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return token;
    }



}
