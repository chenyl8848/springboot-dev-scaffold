package com.cyl.scaffold.core.util;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.github.dozermapper.core.loader.api.TypeMappingOptions.mapEmptyString;
import static com.github.dozermapper.core.loader.api.TypeMappingOptions.mapNull;

/**
 * @author cyl
 * @date 2023-07-24 16:30
 * @description 对象拷贝工具
 */
// 将构造器私有化
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanUtil {

    protected static Mapper BEAN_MAPPER_BUILDER;

    static {
        BEAN_MAPPER_BUILDER = DozerBeanMapperBuilder.buildDefault();
    }

    /**
     * 对象属性拷贝
     *
     * @param source 源对象
     * @param target 目标对象
     * @param <T>
     * @param <S>
     * @return 拷贝后结果
     */
    public static <T, S> T copy(S source, T target) {
        Optional.ofNullable(source)
                .ifPresent(each -> BEAN_MAPPER_BUILDER.map(each, target));
        return target;
    }

    /**
     * 拷贝单个对象
     *
     * @param source 源对象
     * @param clazz  目标对象类型
     * @param <T>
     * @param <S>
     * @return 拷贝后对象
     */
    public static <T, S> T copy(S source, Class<T> clazz) {
        return Optional.ofNullable(source)
                .map(each -> BEAN_MAPPER_BUILDER.map(each, clazz))
                .orElse(null);
    }

    /**
     * 拷贝多个对象
     *
     * @param sources 源对象集合
     * @param clazz   目标对象类型
     * @param <T>
     * @param <S>
     * @return 拷贝后对象集合
     */
    public static <T, S> List<T> copyList(List<S> sources, Class<T> clazz) {
        return Optional.ofNullable(sources)
                .map(each -> {
                    ArrayList<T> targets = new ArrayList<>(each.size());
                    each.stream().forEach(item -> targets.add(BEAN_MAPPER_BUILDER.map(item, clazz)));
                    return targets;
                })
                .orElse(null);
    }

    /**
     * 拷贝多个对象
     *
     * @param sources 源对象集合
     * @param clazz   目标对象类型
     * @param <T>
     * @param <S>
     * @return 拷贝后对象集合
     */
    public static <T, S> Set<T> copySet(Set<S> sources, Class<T> clazz) {
        return Optional.ofNullable(sources)
                .map(each -> {
                    HashSet<T> targets = new HashSet<>(each.size());
                    each.stream().forEach(item -> targets.add(BEAN_MAPPER_BUILDER.map(item, clazz)));
                    return targets;
                })
                .orElse(null);
    }

    /**
     * 拷贝多个对象
     *
     * @param sources 源对象集合
     * @param clazz   目标对象类型
     * @param <T>
     * @param <S>
     * @return 拷贝后对象集合
     */
    public static <T, S> T[] copyArray(S[] sources, Class<T> clazz) {
        return Optional.ofNullable(sources)
                .map(each -> {
                    T[] targets = (T[]) Array.newInstance(clazz, sources.length);
                    for (int i = 0; i < targets.length; i++) {
                        targets[i] = BEAN_MAPPER_BUILDER.map(sources[i], clazz);
                    }
                    return targets;
                })
                .orElse(null);

    }

    /**
     * 拷贝非空非空字符串
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyIgnoreNullAndBlank(Object source, Object target) {
        DozerBeanMapperBuilder dozerBeanMapperBuilder = DozerBeanMapperBuilder.create();
        Mapper mapper = dozerBeanMapperBuilder.withMappingBuilders(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(source.getClass(), target.getClass(), mapNull(false), mapEmptyString(false));
            }
        }).build();
        mapper.map(source, target);
    }

    /**
     * 拷贝非空
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static void copyIgnoreNull(Object source, Object target) {
        DozerBeanMapperBuilder dozerBeanMapperBuilder = DozerBeanMapperBuilder.create();
        Mapper mapper = dozerBeanMapperBuilder.withMappingBuilders(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(source.getClass(), target.getClass(), mapNull(false));
            }
        }).build();
        mapper.map(source, target);
    }

}
