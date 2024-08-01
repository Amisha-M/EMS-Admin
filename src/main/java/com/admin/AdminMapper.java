package com.admin;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.admin.bo.AdminBO;
import com.admin.entity.AdminEntity;

@Mapper
public interface AdminMapper {

	AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    AdminBO toBO(AdminEntity entity);
    AdminEntity toEntity(AdminBO bo);
	
}
