package com.annotation.smile.annotation;

import com.annotation.smile.pojo.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author smi1e
 * Date 2019/10/17 22:34
 * Description
 */
@Aspect
@Component
public class MyCacheAspect {

    @Autowired
    RedisTemplate redisTemplate;

    @Around("@annotation(com.annotation.smile.annotation.MyCache)")
    public void doCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        //TODO 获取方法注解里面key的内容
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = proceedingJoinPoint.getTarget().getClass().getMethod(signature.getName(), signature.getMethod().getParameterTypes());
        MyCache myCache = method.getAnnotation(MyCache.class);
        String key = myCache.key();
        //TODO  拿到方法参数名和参数值
        //创建解析器
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(key);
        // 设置解析上下文
        EvaluationContext context = new StandardEvaluationContext();
        Object[] args = proceedingJoinPoint.getArgs();
        String[] parameterNames = new DefaultParameterNameDiscoverer().getParameterNames(method);
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i],args[i]);
        }
        //TODO 解析表达式，生成最终的KEY
        String result = expression.getValue(context).toString();

        //增强
        Object cacheValue = redisTemplate.opsForValue().get(result);
        if (cacheValue != null) {
            System.out.println("---------缓存中读取数据---------");
            System.out.println(cacheValue);
            return;
        }

        User proceed = (User) proceedingJoinPoint.proceed();

        //增强
        System.out.println("---------数据库中读取数据---------");
        redisTemplate.opsForValue().set(proceed.getId(), proceed.getUserName());
        System.out.println(proceed.getId() + ":" + proceed.getUserName());

    }
}
