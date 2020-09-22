package com.htngu.gtg.service.appservice;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public interface AppService {
    Map getPostById(long id);
    ArrayList<Map> getAllPost();
}
