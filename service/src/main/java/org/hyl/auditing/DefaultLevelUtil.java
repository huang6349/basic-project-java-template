package org.hyl.auditing;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultLevelUtil<T extends AbstractLevelAuditingVM<T>> implements LevelUtil<T> {

    @Override
    public List<T> listToTree(List<T> list) {
        return listToTree(list, StringUtils.join(LevelUtil.ROOT, LevelUtil.SUFFIX));
    }

    @Override
    public List<T> listToTree(List<T> list, String level) {
        if (CollectionUtils.isEmpty(list)) {
            return Lists.newArrayList();
        }
        Multimap<String, T> multimap = ArrayListMultimap.create();
        List<T> root = Lists.newArrayList();
        list.forEach(t -> {
            multimap.put(t.getLevel(), t);
            if (StringUtils.join(LevelUtil.ROOT, LevelUtil.SUFFIX).equals(t.getLevel())) {
                root.add(t);
            }
        });
        transformTree(root, multimap, level);
        return root;
    }

    private void transformTree(List<T> list, Multimap<String, T> multimap, String level) {
        list.forEach(t -> {
            String nextLevel = LevelUtil.calculateLevel(level, t.getId());
            List<T> next = (List<T>) multimap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(next)) {
                t.setChild(next);
                transformTree(next, multimap, nextLevel);
            }
        });
    }
}
