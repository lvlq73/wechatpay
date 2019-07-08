package com.wechat.entity;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "xml")
public class ReturnParams {

    public ReturnParams(){
    }

    public ReturnParams(String return_code, String return_msg){
        this.return_code = return_code;
        this.return_msg = return_msg;
    }

    @JacksonXmlCData
    public String return_code;
    @JacksonXmlCData
    public String return_msg;

}
