package com.example.framework.comm.reporter.collector;

import com.example.framework.comm.annotation.ComponentInfo;
import com.example.framework.comm.reporter.AbstractCollector;
import com.example.framework.comm.reporter.InputData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/10
 * @since 1.0.0
 */
@Slf4j
public class ComponentCollector extends AbstractCollector implements ApplicationListener<ApplicationPreparedEvent> {
    public final static String ATTR_NAME="name";
    public final static String ATTR_VERSION="version";

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        this.reportData(this.collectData());
    }

    @Override
    public InputData collectData() {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, String>> components = new ArrayList<>();
        // 支持在接口、抽象类等对象上添加注解
        ClassPathScanningCandidateComponentProvider scanningCandidateComponentProvider = new ClassPathScanningCandidateComponentProvider(false) {
            @Override
            protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                boolean isCandidate = false;
                if (beanDefinition.getMetadata().isIndependent()) {
                    if (!beanDefinition.getMetadata().isAnnotation()) {
                        isCandidate = true;
                    }
                }
                return isCandidate;
            }
        };
        scanningCandidateComponentProvider.addIncludeFilter(new AnnotationTypeFilter(ComponentInfo.class));
        log.info("ComponentCollector scan start.");
        for (BeanDefinition beanDefinition : scanningCandidateComponentProvider.findCandidateComponents("com.example")) {
            try {
                Class<?> cl = Class.forName(beanDefinition.getBeanClassName());
                ComponentInfo componentInfo = cl.getAnnotation(ComponentInfo.class);
                components.add(new HashMap<String, String>() {{
                    put(ATTR_NAME, componentInfo.name());
                    put(ATTR_VERSION, componentInfo.version());
                }});
            } catch (ClassNotFoundException e) {
                log.warn("ClassNotFoundException for CaijjComponentInfo", e);
            }
        }
        log.info("ComponentCollector scan finished.");
        map.put("component", components);
        return InputData.builder().type(InputData.Type.COMPONENT).data(map).build();
    }

}