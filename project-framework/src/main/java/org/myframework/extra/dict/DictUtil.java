package org.myframework.extra.dict;

import lombok.val;

import java.util.ArrayList;
import java.util.Comparator;

import static cn.hutool.core.annotation.AnnotationUtil.getAnnotation;
import static cn.hutool.core.collection.CollUtil.newArrayList;
import static cn.hutool.core.text.CharSequenceUtil.isEmpty;
import static cn.hutool.core.text.CharSequenceUtil.toSymbolCase;
import static cn.hutool.core.util.ObjectUtil.equal;
import static org.myframework.extra.dict.IsDefault.YES;

public class DictUtil {

    public static DictDefine parse(Class<?> clazz) {
        val dict = getAnnotation(clazz, Dict.class);
        val items = new ArrayList<ItemDefine>();
        newArrayList(clazz.getEnumConstants()).stream()
                .filter(enumConstant -> enumConstant instanceof EnumDict)
                .map(enumConstant -> (EnumDict<?>) enumConstant)
                .sorted(Comparator.comparing(EnumDict::getSort))
                .map(DictUtil::parse)
                .forEach(items::add);
        val define = new DictDefine();
        define.setName(dict.name());
        define.setCategory(isEmpty(dict.category()) ?
                toSymbolCase(clazz.getSimpleName(), '-') :
                dict.category());
        define.setItems(items);
        return define;
    }

    public static ItemDefine parse(EnumDict<?> enumDict) {
        val itemDefine = new ItemDefine();
        itemDefine.setLabel(enumDict.getLabel());
        itemDefine.setValue(enumDict.getValue());
        val isDefault = enumDict.getIsDefault();
        itemDefine.setIsDefault(equal(YES.getValue(), isDefault));
        return itemDefine;
    }
}
