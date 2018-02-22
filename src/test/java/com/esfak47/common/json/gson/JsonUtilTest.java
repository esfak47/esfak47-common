package com.esfak47.common.json.gson;

import com.esfak47.common.json.AbstractTypeRef;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Type;

/**
 * @author tony 2018/2/13
 */
public class JsonUtilTest {

    private JSON json;

    @Before
    public void setUp() throws Exception {
        json = new JSON();
        json.setName("json");
        json.setValue("value");
        json.setTransientFiled("transient");
        json.setChinese("中文");
        json.setNullField(null);

    }

    @Test
    public void toJson() {
        String jsonString = JsonUtil.toJson(json);
        System.out.println(jsonString);
        Assert.assertEquals("{\"name\":\"json\",\"value\":\"value\",\"chinese\":\"\\u4E2D\\u6587\"}",jsonString);

    }

    @Test
    public void toJson1() {
        String toJson = JsonUtil.toJson(json, "value","chinese");
        System.out.println(toJson);
        Assert.assertEquals("{\"name\":\"json\"}",toJson);
    }

    @Test
    public void toJson2() {
        String toJson = JsonUtil.toJson(json, false, "value");
        System.out.println(toJson);
        Assert.assertEquals("{\"name\":\"json\",\"chinese\":\"中文\"}",toJson);

    }

    @Test
    public void toJson3() {
        String toJson = JsonUtil.toJson(json, false,true, "value");
        System.out.println(toJson);
        Assert.assertEquals("{\"name\":\"json\",\"chinese\":\"中文\"}",toJson);
    }

    @Test
    public void fromJson() {
        JSON json = JsonUtil.fromJson("{\"name\":\"json\",\"chinese\":\"中文\"}", JSON.class);
        Assert.assertNotNull(json);
        Assert.assertEquals(json.getChinese(),"中文");
    }

    @Test
    public void fromJson1() {
        JSON json = JsonUtil.fromJson("{\"name\":\"json\",\"chinese\":\"中文\"}", (Type)JSON.class);
        Assert.assertNotNull(json);
        Assert.assertEquals(json.getChinese(),"中文");
    }



    private static class JSON {
        private String name;
        private String value;
        private transient String transientFiled;
        private String chinese;
        private String nullField;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTransientFiled() {
            return transientFiled;
        }

        public void setTransientFiled(String transientFiled) {
            this.transientFiled = transientFiled;
        }

        public String getChinese() {
            return chinese;
        }

        public void setChinese(String chinese) {
            this.chinese = chinese;
        }

        public String getNullField() {
            return nullField;
        }

        public void setNullField(String nullField) {
            this.nullField = nullField;
        }
    }
    private static class JSON2 {
        private String name;
        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}