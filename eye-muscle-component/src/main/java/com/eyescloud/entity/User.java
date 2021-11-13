package com.eyescloud.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 
 * </p>
 *
 * @author balabala
 * @since 2021-10-18
 */
@Getter
@Setter
@TableName("sys_user")
@ApiModel(value = "User对象", description = "")
public class User implements Serializable , UserDetails {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户的唯一标识")
    @TableId("id")
    private String id;

    @ApiModelProperty("用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty("用户密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("姓名")
    @TableField("realname")
    private String realname;

    @ApiModelProperty("手机号")
    @TableField("phone")
    private String phone;

    @ApiModelProperty("用户头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("生日")
    @TableField("birthday")
    private LocalDateTime birthday;

    @ApiModelProperty("性别")
    @TableField("sex")
    private String sex;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;


    @TableField(select = false)
    private List<String> authorities;

    @TableField(select = false)
    private boolean enabled;

    @TableField(select = false)
    private boolean accountNonExpired;

    @TableField(select = false)
    private boolean credentialsNonExpired;

    @TableField(select = false)
    private boolean accountNonLocked;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
