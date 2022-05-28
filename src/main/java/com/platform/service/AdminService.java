package com.platform.service;

import com.platform.entity.Admin;

import java.util.List;

/**
 * @author by Hchier
 * @Date 2022/5/25 15:24
 */
public interface AdminService {
    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    Admin selectByPrimaryKey(Integer id);

    List<Admin> selectAll();

    int updateByPrimaryKey(Admin record);

    Admin selectByUsername(String username);
}
