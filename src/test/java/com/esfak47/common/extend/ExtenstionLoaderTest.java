package com.esfak47.common.extend;

import com.esfak47.common.extension.URL;
import com.esfak47.common.extension.ExtensionFactory;
import com.esfak47.common.extension.ExtensionLoader;
import com.esfak47.common.job.JobManager;
import org.junit.Assert;
import org.junit.Test;
import com.esfak47.common.compiler.Compiler;

/**
 * @author tony
 * @date 2018/4/25
 */
public class ExtenstionLoaderTest {

    @Test
    public void test(){
        Compiler defaultExtension = ExtensionLoader.getExtensionLoader(Compiler.class)
            .getDefaultExtension();
        Assert.assertNotNull(defaultExtension);
        Assert.assertEquals(defaultExtension.getClass().getSimpleName(),"JavassistCompiler");
    }
    @Test
    public void test2(){
        Compiler defaultExtension = ExtensionLoader.getExtensionLoader(Compiler.class).getExtension("jdk");
        Assert.assertNotNull(defaultExtension);
        Assert.assertEquals(defaultExtension.getClass().getSimpleName(),"JdkCompiler");
    }
    @Test
    public void test3(){
        ExtensionFactory extensionFactory = ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getAdaptiveExtension();
        Assert.assertNotNull(extensionFactory);
    }

    @Test
    public void test4(){
        JobManager defaultExtension = ExtensionLoader.getExtensionLoader(JobManager.class).getDefaultExtension();
        Assert.assertNotNull(defaultExtension);
        Assert.assertEquals(defaultExtension.getClass().getSimpleName(),"DefaultJobManagerImpl");
    }

    @Test
    public void test5(){

        AdpativeTest defaultExtension = ExtensionLoader.getExtensionLoader(AdpativeTest.class).getAdaptiveExtension();

        String adaptive = defaultExtension.adaptive(
            URL.valueOf("extention://192.168.1.7:9090/com.alibaba.service1?name=impl1&param2=value2"));
        Assert.assertEquals("AdpativeTestImpl",adaptive);
        String adaptive2 = defaultExtension.adaptive(
            URL.valueOf("extention://192.168.1.7:9090/com.alibaba.service1?name=impl2&param2=value2"));
        Assert.assertEquals("AdpativeTestImpl2",adaptive2);
    }


    @Test
    public void test6(){

        AdpativeTest defaultExtension = ExtensionLoader.getExtensionLoader(AdpativeTest.class).getAdaptiveExtension();

        String adaptive = defaultExtension.method(URL.valueOf("impl1://192.168.1.7:9090/com.alibaba.service1?name=impl1&param2=value2"));
        Assert.assertEquals("AdpativeTestImpl",adaptive);
        String adaptive2 = defaultExtension.method(URL.valueOf("impl2://192.168.1.7:9090/com.alibaba.service1?name=impl1&param2=value2"));
        Assert.assertEquals("AdpativeTestImpl2",adaptive2);
    }
    @Test
    public void test7(){

        AdpativeTest defaultExtension = ExtensionLoader.getExtensionLoader(AdpativeTest.class).getAdaptiveExtension();

        String adaptive = defaultExtension.method(new Parameter("impl1"));
        Assert.assertEquals("AdpativeTestImpl",adaptive);
        String adaptive2 = defaultExtension.method(new Parameter("impl2"));
        Assert.assertEquals("AdpativeTestImpl2",adaptive2);
    }
    @Test
    public void test8(){

        AdpativeTest defaultExtension = ExtensionLoader.getExtensionLoader(AdpativeTest.class).getAdaptiveExtension();

        String adaptive = defaultExtension.method2(new Parameter("impl1"));
        Assert.assertEquals("AdpativeTestImpl",adaptive);
        String adaptive2 = defaultExtension.method2(new Parameter("impl2"));
        Assert.assertEquals("AdpativeTestImpl2",adaptive2);
    }
}
