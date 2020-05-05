package com.esfak47.common.utils;

import com.esfak47.common.utils.id.IdGenerator;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

/**
 * @author tony
 * @date 2018/7/4
 */
public class IdGeneratorTest {


    @Test
    public void testGenerateId(){
        final String jdk = IdGenerator.generateIdWithProvider("jdk");
        System.out.println(jdk);
        Assert.assertNotNull(jdk);
        final String alternative = IdGenerator.generateIdWithProvider("alternative");
        Assert.assertNotNull(alternative);
        final String simple = IdGenerator.generateIdWithProvider("simple");
        Assert.assertNotNull(simple);

        final String eager = IdGenerator.generateIdWithProvider("eager");
        Assert.assertNotNull(eager);
        System.out.println(eager);

    }
    @Test
    public void testGenerateUUId(){
        final UUID jdk = IdGenerator.generateUUIDWithProvider("jdk");
        System.out.println(jdk);
        Assert.assertNotNull(jdk);
        final UUID alternative = IdGenerator.generateUUIDWithProvider("alternative");
        Assert.assertNotNull(alternative);
        final UUID simple = IdGenerator.generateUUIDWithProvider("simple");
        Assert.assertNotNull(simple);

        final UUID eager = IdGenerator.generateUUIDWithProvider("eager");
        Assert.assertNotNull(eager);
        System.out.println(eager);

    }
}
