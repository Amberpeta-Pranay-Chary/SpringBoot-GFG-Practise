package com.example.testserver;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

public class ImageController {

    /*
        input will be an Id
        Output will be an Image corrsponds to Id from lorem picsum website.
     */
    @GetMapping(value="/image",produces= MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage(@RequestParam("id") Integer id,@RequestParam(value = "width",required = false,defaultValue = "200") Integer width,@RequestParam(value = "height",required = false,defaultValue = "300") Integer height)
    {
        String URL="https://picsum.photos/id/"+id+"/"+width+"/"+height;
        RestTemplate restTemplate=new RestTemplate();
        return restTemplate.getForObject(URL,byte[].class);
    }
}
