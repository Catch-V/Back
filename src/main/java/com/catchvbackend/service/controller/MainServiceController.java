package com.catchvbackend.service.controller;

import com.catchvbackend.service.SeviceRepository.Image.FaceData;
import com.catchvbackend.service.SeviceRepository.dao.FaceDataDaoJDBC;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/image")
public class MainServiceController {

    @Autowired
    private FaceDataDaoJDBC imageDaoJDBC;

    @PostMapping(value = "/api")
    public void uploadImage(@RequestParam("files") List<MultipartFile> faceDatalist,
                            @RequestParam("email") String userEmail){
        log.info("upload");
        log.info(""+userEmail);
        MultipartFile multipartFile;
        for(int i=0;i<faceDatalist.size();i++) {
            if(i==faceDatalist.size()-1){
                log.info(""+faceDatalist.get(i));
            }else {
                FaceData faceData = new FaceData();
                multipartFile = faceDatalist.get(i);
                try {
                    faceData.setName(multipartFile.getName());
                    faceData.setImage(multipartFile.getBytes());
                    faceData.setSize(multipartFile.getSize());
                    imageDaoJDBC.upload(faceData);
                } catch (IOException e) {
                    log.info("Exception" + e);
                }
            }
        }
    }

    @PostMapping(value = "/api/flask")
    public void flaskApi(@RequestParam("files") List<MultipartFile> faceDatalist,
                                 @RequestParam("email") String userEmail) {

        String url = "http://localhost:5000/user/"+userEmail;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, byte[]> params = new LinkedMultiValueMap<>();



        for(int i=0;i<faceDatalist.size();i++) {
            try {
                params.add("files", faceDatalist.get(i).getBytes());
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        HttpEntity<MultiValueMap<String, byte[]>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK){
            log.info("connect"+response);
        }
    }
}
