package com.htngu.gtg.service.appservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class AppServiceImp implements AppService{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Map getPostById(long id){

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("get_post_with_user_info");
        jdbcCall.addDeclaredParameter(new SqlParameter("id", Types.INTEGER));
        Map<String, String> callParams = new HashMap<String, String>();
        callParams.put("id", String.valueOf(id));
        Map<String, Object> outputMap = jdbcCall.execute(callParams);
        ArrayList arrayList = (ArrayList) outputMap.get("#result-set-1");
        return (Map) arrayList.get(0);
    }

    @Override
    public ArrayList<Map> getAllPost(){
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("get_all_post");
        Map<String, Object> outputMap = jdbcCall.execute();
        ArrayList arrayList = (ArrayList) outputMap.get("#result-set-1");
        return arrayList;
    }
}
