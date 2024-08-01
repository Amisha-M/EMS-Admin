package com.admin;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.admin.bo.AdminBO;
import com.admin.entity.AdminEntity;

@Mapper(componentModel = "spring")
public interface AdminMapper {

	AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    AdminEntity toEntity(AdminBO adminBO);
    AdminBO toBO(AdminEntity adminEntity);
    
}
