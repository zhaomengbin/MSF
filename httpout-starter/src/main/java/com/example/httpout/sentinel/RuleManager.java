package com.example.httpout.sentinel;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈〉
 *
 * @author zhaomengbin
 * @create 2022/2/5
 * @since 1.0.0
 */
public class RuleManager {
    public static void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("okhttp:GET:http://localhost:8080/okhttp/back/999");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(8);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }
}